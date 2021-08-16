package de.sloth.spotiregx.lib.spotify.impl;

import com.spotify.android.appremote.api.SpotifyAppRemote;

import kaaes.spotify.webapi.android.SpotifyService;

public interface SpotifyInternalApiAccessor {

    SpotifyAppRemote getSpotifyAppRemote();

    SpotifyService getSpotifyWebApi();
}
