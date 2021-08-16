package de.sloth.spotiregx.lib.spotify.api;

import android.app.Activity;
import android.content.Intent;

public interface SpotifyAuthService{

    void redirectForAuthorization(Activity activity);

    boolean registerAuthentication(int resultCode, Intent data);
}
