package de.sloth.spotiregx.lib.spotify.impl;

import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

import de.sloth.spotiregx.lib.spotify.api.SpotifyAuthService;
import de.sloth.spotiregx.lib.spotify.api.SpotifySearchService;
import de.sloth.spotiregx.lib.spotify.impl.util.PagingHelper;
import kaaes.spotify.webapi.android.models.Artist;

public class SpotifySearchServiceImpl implements SpotifySearchService {

    private final SpotifyAuthService mSpotifyAuthService;

    @Inject
    public SpotifySearchServiceImpl(SpotifyAuthService spotifyAuthService){
        mSpotifyAuthService = spotifyAuthService;
    }

    // TODO make own data type.
    @Override
    public Collection<Artist> searchArtists(String searchRequest){
        if(searchRequest == null || searchRequest.length() < 1){
            return Collections.emptyList();
        }

        PagingHelper tHelper = new PagingHelper(mSpotifyAuthService.getSpotifyWebApi());
        return tHelper.searchArtists(searchRequest);
    }

}
