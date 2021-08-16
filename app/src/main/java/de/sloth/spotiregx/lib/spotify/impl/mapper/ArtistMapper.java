package de.sloth.spotiregx.lib.spotify.impl.mapper;

import java.util.HashMap;

import de.sloth.spotiregx.lib.spotify.api.model.ArtistVO;

public final class ArtistMapper {

    private ArtistMapper(){
        super();
    }

    public static ArtistVO map(kaaes.spotify.webapi.android.models.Artist sourceArtist){
        ArtistVO targetArtist = new ArtistVO();
        targetArtist.setId(sourceArtist.id);
        targetArtist.setName(sourceArtist.name);
        targetArtist.setHref(sourceArtist.href);
        targetArtist.setType(sourceArtist.type);
        targetArtist.setUri(sourceArtist.uri);
        targetArtist.setExternal_urls(new HashMap<>(sourceArtist.external_urls));
        return targetArtist;
        }
}
