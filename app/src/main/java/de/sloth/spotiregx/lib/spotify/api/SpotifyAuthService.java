package de.sloth.spotiregx.lib.spotify.api;

import android.app.Activity;
import android.content.Intent;

import com.spotify.android.appremote.api.SpotifyAppRemote;

import kaaes.spotify.webapi.android.SpotifyService;

public interface SpotifyAuthService {

    void redirectForAuthorization(Activity activity);

    boolean registerAuthentication(int resultCode, Intent data);

    // TODO This third party apis shouldn't be exposed to the activity
    // Either implement the api on my own or build a proper wrapper.
    // Currently HILT doesn't support to expose a service under multiple interfaces which sadly
    // leads to this version. (SG)
    
    SpotifyAppRemote getSpotifyAppRemote();

    SpotifyService getSpotifyWebApi();
}
