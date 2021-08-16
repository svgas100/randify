package de.sloth.spotiregx.main;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.ListFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import de.sloth.spotiregx.R;
import de.sloth.spotiregx.lib.db.api.DatabaseService;
import de.sloth.spotiregx.lib.db.model.bo.Artist;
import de.sloth.spotiregx.lib.spotify.api.SpotifyRandomPlaybackService;
import de.sloth.spotiregx.lib.util.StableArrayAdapter;

@AndroidEntryPoint
public class ArtistsListFragment extends ListFragment {

    private static final String PREFIX = ArtistsListFragment.class.getSimpleName();

    @Inject
    protected DatabaseService mDatabaseService;

    @Inject
    protected SpotifyRandomPlaybackService mSpotifyRandomPlaybackService;

    private ListView mListView;

    private ArrayAdapter<Pair<String, String>> mArrayAdapter;

    private ArrayList<Pair<String, String>> mArray;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.artists_list_fragment,
                container, false);
        mListView = rootView.findViewById(android.R.id.list);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDatabaseService.performDatabaseAction(getContext(), appDatabase -> {
            List<Artist> artistList = appDatabase.artistDAO().getAll();
            mArray = artistList.stream().map(artist -> new Pair<>(artist.artistName, artist.artistUri)).collect(Collectors.toCollection(ArrayList::new));
            mArrayAdapter = new StableArrayAdapter(getActivity(), R.layout.row, mArray);
            mListView.setAdapter(mArrayAdapter);
            mArrayAdapter.notifyDataSetChanged();
        });

        mListView.setOnItemLongClickListener((parent, view1, position, id) -> {
                getActivity().runOnUiThread(() -> showDialog(mArrayAdapter.getItem(position)));
                return true;
        });
    }

    void showDialog(Pair<String,String> artistToDelete) {
        DialogFragment newFragment = new ArtistDeleteDialogFragment(artistToDelete,mArrayAdapter,mArray);
        newFragment.show(getActivity().getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        @SuppressWarnings("unchecked")
        Pair<String, String> tPair = (Pair<String, String>) l.getItemAtPosition(position);

        CompletableFuture<String> tResult = null;
        // TODO dirty hack.
        if (position == 0) {
            tResult = CompletableFuture.supplyAsync(() -> mSpotifyRandomPlaybackService.playRandomAlbumOfArtists(tPair.second, album -> Character.isDigit(album.getName().charAt(0))));
        } else if (position == 1) {
            tResult = CompletableFuture.supplyAsync(() -> mSpotifyRandomPlaybackService.playRandomAlbumOfArtists(tPair.second, album -> album.getName().startsWith("Folge")));
        } else {
            tResult = CompletableFuture.supplyAsync(() -> mSpotifyRandomPlaybackService.playRandomAlbumOfArtists(tPair.second, album -> true));
        }

        if (tResult == null) {
            return;
        }

        CompletableFuture<String> finalTResult = tResult;
        tResult.thenAcceptAsync((String s) -> {
            try {
                final String message = finalTResult.get();

                getActivity().runOnUiThread(() -> Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show());
            } catch (ExecutionException e) {
                Log.e(PREFIX, "Request execution failed!", e);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Log.e(PREFIX, "Thread was interrupted during request!", e);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mDatabaseService.performDatabaseAction(getContext(), appDatabase -> {
            List<Artist> artistList = appDatabase.artistDAO().getAll();

            if (artistList.size() != mArrayAdapter.getCount()) {
                mArray.clear();
                mArray.addAll(artistList.stream().map(artist -> new Pair<>(artist.artistName, artist.artistUri)).collect(Collectors.toCollection(ArrayList::new)));
                getActivity().runOnUiThread(mArrayAdapter::notifyDataSetChanged);
            }
        });
    }
}
