package de.sloth.spotiregx.search;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import de.sloth.spotiregx.R;
import de.sloth.spotiregx.lib.db.model.bo.Artist;
import de.sloth.spotiregx.lib.spotify.api.SpotifySearchService;
import de.sloth.spotiregx.lib.spotify.api.model.ArtistVO;
import de.sloth.spotiregx.lib.util.TextChangedListener;
import de.sloth.spotiregx.lib.util.TextViewArrayAdapter;

@AndroidEntryPoint
public class SearchActivity extends AppCompatActivity {

    private static final String PREFIX = SearchActivity.class.getSimpleName();

    private static final Object LOCK = new Object();

    @Inject
    protected SpotifySearchService mSpotifySearchService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // init layout.
        setContentView(R.layout.activity_search);

        // init search result list.
        ListView searchResultList = findViewById(android.R.id.list);
        TextViewArrayAdapter<Artist> adapter = new TextViewArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, new ArrayList<>()) {
            @Override
            protected String listEntryToString(Artist entry) {
                return entry.artistName;
            }
        };

        searchResultList.setOnItemClickListener(
                (parent, view, position, id) -> runOnUiThread(() -> showDialog((Artist) searchResultList.getItemAtPosition(position))
                ));

        searchResultList.setAdapter(adapter);

        // init search bar - fill result list view.
        EditText searchBar = findViewById(R.id.search_artist);
        searchBar.addTextChangedListener(new TextChangedListener(500, s -> {
            Collection<ArtistVO> tResult = mSpotifySearchService.searchArtists(s.toString());

            runOnUiThread(() -> {
                synchronized (LOCK) {
                    adapter.clear();
                    adapter.addAll(tResult.stream().map(a -> {
                        Artist artist = new Artist();
                        artist.artistUri = a.getUri();
                        artist.artistName = a.getName();
                        return artist;
                    }).collect(Collectors.toCollection(ArrayList::new)));
                    adapter.notifyDataSetChanged();
                }
            });

            Log.i(PREFIX, s.toString());
        }));
    }

    void showDialog(Artist artistToSave) {
        DialogFragment newFragment = new ArtistSaveDialogFragment(artistToSave);
        newFragment.show(getSupportFragmentManager(), "dialog");
    }
}
