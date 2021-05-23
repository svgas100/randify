package de.sloth.spotiregx.search;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class ArtistSaveDialogFragment extends DialogFragment {

    private final Artist mArtistToSave;

    @Inject
    protected DatabaseService mDatabaseService;

    public ArtistSaveDialogFragment(Artist artistToSave) {
        this.mArtistToSave = artistToSave;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you want to add '" + mArtistToSave.artistName + "'?")
                .setPositiveButton(R.string.add, (dialog, id) ->
                    mDatabaseService.performDatabaseAction(getContext(), appDatabase -> {
                        appDatabase.artistDAO().insertAll(mArtistToSave);
                        dismiss();
                    })
                )
                .setNegativeButton(R.string.cancel, (dialog, id) -> dismiss());
        return builder.create();
    }
}
