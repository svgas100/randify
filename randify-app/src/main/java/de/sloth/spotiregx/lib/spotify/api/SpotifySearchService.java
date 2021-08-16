package de.sloth.spotiregx.lib.spotify.api;

import java.util.List;

import de.sloth.spotiregx.lib.spotify.api.model.ArtistVO;

public interface SpotifySearchService {

    List<ArtistVO> searchArtists(String searchRequest);
}
