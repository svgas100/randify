package de.sloth.spotiregx.lib.db.model.bo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Artist {

    @PrimaryKey
    @NonNull
    public String artistUri;

    @ColumnInfo(name = "artist_name")
    public String artistName;
}
