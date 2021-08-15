package de.sloth.spotiregx.lib.spotify.api;

import java.util.function.Predicate;

import kaaes.spotify.webapi.android.models.Album;

public interface SpotifyRandomPlaybackService {

    String playRandomAlbumOfArtists(String artistsSpotifyUri, Predicate<Album> aFilter);
}
