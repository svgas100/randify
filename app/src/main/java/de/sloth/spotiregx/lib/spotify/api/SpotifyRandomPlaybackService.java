package de.sloth.spotiregx.lib.spotify.api;

import java.util.function.Predicate;

import de.sloth.spotiregx.lib.spotify.api.model.AlbumVO;


public interface SpotifyRandomPlaybackService {

    String playRandomAlbumOfArtists(String artistsSpotifyUri, Predicate<AlbumVO> aFilter);
}
