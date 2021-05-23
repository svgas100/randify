package de.sloth.spotiregx.lib.spotify.impl;

import javax.inject.Inject;

import de.sloth.spotiregx.lib.spotify.api.SpotifyAuthService;
import de.sloth.spotiregx.lib.spotify.api.SpotifyPlayService;

public class SpotifyPlayServiceImpl implements SpotifyPlayService {

    private static final String SPOTIFY_ALBUM_PREFIX = "spotify:album:";

    private final SpotifyAuthService mSpotifyAuthService;

    @Inject
    public SpotifyPlayServiceImpl(SpotifyAuthService spotifyAuthService) {
        mSpotifyAuthService = spotifyAuthService;
    }

    @Override
    public void playAlbum(String albumUri) {
        mSpotifyAuthService.getSpotifyAppRemote().getPlayerApi().play(SPOTIFY_ALBUM_PREFIX + albumUri);
    }
}
