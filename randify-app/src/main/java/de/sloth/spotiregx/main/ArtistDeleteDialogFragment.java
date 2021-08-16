package de.sloth.spotiregx.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import de.sloth.spotiregx.R;
import de.sloth.spotiregx.lib.db.api.DatabaseService;
import de.sloth.spotiregx.lib.db.model.bo.Artist;

@AndroidEntryPoint
public class ArtistDeleteDialogFragment extends DialogFragment {

    private final Pair<String,String> mArtistToDelete;

    private final List<Pair<String, String>> mArray;

    private final ArrayAdapter<Pair<String, String>> mArrayAdapter;

    @Inject
    protected DatabaseService mDatabaseService;

    public ArtistDeleteDialogFragment(Pair<String,String> artistToDelete, ArrayAdapter<Pair<String, String>> arrayAdapter, List<Pair<String, String>> array) {
        this.mArtistToDelete = artistToDelete;
        this.mArrayAdapter = arrayAdapter;
        this.mArray = array;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you really want to remove the artist '" + mArtistToDelete.first + "'?")
                .setPositiveButton(R.string.confirm, (dialog, id) -> mDatabaseService.performDatabaseAction(getContext(), appDatabase -> {
                    Artist deleteArtist = appDatabase.artistDAO().findById(mArtistToDelete.second);
                    appDatabase.artistDAO().delete(deleteArtist);
                    List<Artist> artistList = appDatabase.artistDAO().getAll();
                    mArray.clear();
                    mArray.addAll(artistList.stream().map(artist -> new Pair<>(artist.artistName, artist.artistUri)).collect(Collectors.toCollection(ArrayList::new)));
                    getActivity().runOnUiThread(mArrayAdapter::notifyDataSetChanged);
                    dismiss();
                }))
                .setNegativeButton(R.string.cancel, (dialog, id) -> dismiss());
        return builder.create();
    }
}
