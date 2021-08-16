package de.sloth.spotiregx.lib.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public abstract class TextViewArrayAdapter<T> extends ArrayAdapter<T> {

    private final LayoutInflater mInflater;

    private final int mResource;

    protected TextViewArrayAdapter(Context context, @LayoutRes int textViewResourceId, List<T> list) {
        super(context, textViewResourceId, list);

        mInflater = LayoutInflater.from(context);
        mResource = textViewResourceId;
    }

    protected abstract String listEntryToString(T entry);

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = mInflater.inflate(mResource, parent, false);
        } else {
            view = convertView;
        }

        TextView text = (TextView) view;
        text.setText(listEntryToString(getItem(position)));
        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
