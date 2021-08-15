package de.sloth.spotiregx.lib.db.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import de.sloth.spotiregx.lib.db.model.bo.Artist;
import de.sloth.spotiregx.lib.db.model.dao.ArtistDAO;

@Database(entities = {Artist.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ArtistDAO artistDAO();
}