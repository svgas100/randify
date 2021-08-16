package de.sloth.spotiregx.lib.spotify.impl.mapper;

import java.util.HashMap;

import de.sloth.spotiregx.lib.spotify.api.model.AlbumVO;
import de.sloth.spotiregx.lib.spotify.api.model.ArtistVO;

public final class AlbumMapper {

    private AlbumMapper(){
        super();
    }

    public static AlbumVO map(kaaes.spotify.webapi.android.models.Album sourceAlbum){
        AlbumVO targetAlbum = new AlbumVO();
        targetAlbum.setName(sourceAlbum.name);
        targetAlbum.setId(sourceAlbum.id);
        return targetAlbum;
        }
}
