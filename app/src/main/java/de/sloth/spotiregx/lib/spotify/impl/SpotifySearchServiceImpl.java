package de.sloth.spotiregx.lib.spotify.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import de.sloth.spotiregx.lib.spotify.api.SpotifyAuthService;
import de.sloth.spotiregx.lib.spotify.api.SpotifySearchService;
import de.sloth.spotiregx.lib.spotify.api.model.ArtistVO;
import de.sloth.spotiregx.lib.spotify.impl.mapper.ArtistMapper;
import kaaes.spotify.webapi.android.SpotifyService;

public class SpotifySearchServiceImpl implements SpotifySearchService {

    private final SpotifyInternalApiAccessor mSpotifyApiAccessor;

    @Inject
    public SpotifySearchServiceImpl(SpotifyInternalApiAccessor spotifyApiAccessor){
        mSpotifyApiAccessor = spotifyApiAccessor;
    }

    // TODO make own data type.
    @Override
    public List<ArtistVO> searchArtists(String searchRequest){
        if(searchRequest == null || searchRequest.length() < 1){
            return Collections.emptyList();
        }
        SpotifyService tAuthService = mSpotifyApiAccessor.getSpotifyWebApi();
        return tAuthService.searchArtists(searchRequest).artists.items.stream().map(ArtistMapper::map).collect(Collectors.toList());
    }

}
