package de.sloth.spotiregx.lib.spotify.impl;

import javax.inject.Inject;

import de.sloth.spotiregx.lib.spotify.api.SpotifyPlayService;

public class SpotifyPlayServiceImpl implements SpotifyPlayService {

    private static final String SPOTIFY_ALBUM_PREFIX = "spotify:album:";

    private final SpotifyInternalApiAccessor mSpotifyApiAccessor;

    @Inject
    public SpotifyPlayServiceImpl(SpotifyInternalApiAccessor spotifyApiAccessor) {
        mSpotifyApiAccessor = spotifyApiAccessor;
    }

    @Override
    public void playAlbum(String albumUri) {
        mSpotifyApiAccessor.getSpotifyAppRemote().getPlayerApi().play(SPOTIFY_ALBUM_PREFIX + albumUri);
    }
}
