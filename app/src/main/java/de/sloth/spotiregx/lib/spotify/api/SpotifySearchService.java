package de.sloth.spotiregx.lib.spotify.api;

import java.util.Collection;

import kaaes.spotify.webapi.android.models.Artist;

public interface SpotifySearchService {

    Collection<Artist> searchArtists(String searchRequest);
}
