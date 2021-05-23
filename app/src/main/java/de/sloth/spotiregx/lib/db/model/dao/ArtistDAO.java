package de.sloth.spotiregx.lib.db.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import de.sloth.spotiregx.lib.db.model.bo.Artist;

@Dao
public interface ArtistDAO {

    @Query("SELECT * FROM artist")
    List<Artist> getAll();

    @Query("SELECT * FROM artist WHERE artistUri IN (:artistUris)")
    List<Artist> loadAllByIds(String[] artistUris);

    @Query("SELECT * FROM artist WHERE artistUri = :artistUri LIMIT 1")
    Artist findById(String artistUri);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Artist... artists);

    @Delete
    void delete(Artist artist);
}
