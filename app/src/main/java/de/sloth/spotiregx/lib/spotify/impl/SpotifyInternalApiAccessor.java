package de.sloth.spotiregx.lib.spotify.impl;

import com.spotify.android.appremote.api.SpotifyAppRemote;

import de.sloth.spotiregx.lib.spotify.api.SpotifyAuthService;
import kaaes.spotify.webapi.android.SpotifyService;

public interface SpotifyInternalApiAccessor {

    SpotifyAppRemote getSpotifyAppRemote();

    SpotifyService getSpotifyWebApi();
}
