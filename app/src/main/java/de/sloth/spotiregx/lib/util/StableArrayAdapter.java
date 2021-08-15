package de.sloth.spotiregx.lib.util;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import de.sloth.spotiregx.R;

public class StableArrayAdapter extends ArrayAdapter<Pair<String, String>> {

    private final LayoutInflater mInflater;

    private final int mResource;

    public StableArrayAdapter(Context context, @LayoutRes int textViewResourceId, List<Pair<String, String>> list) {
        super(context, textViewResourceId, list);

        mInflater = LayoutInflater.from(context);
        mResource = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View view;
        final TextView text;

        if (convertView == null) {
            view = mInflater.inflate(mResource, parent, false);
        } else {
            view = convertView;
        }

        text = (TextView) view.findViewById(R.id.rowText);
        text.setText(getItem(position).first);
        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
