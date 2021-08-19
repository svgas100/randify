package de.sloth.spotiregx.lib.spotify.api;

import android.app.Activity;
import android.content.Intent;

public interface SpotifyAuthService{

    Intent redirectForAuthorization(Activity activity);

    boolean registerAuthentication(Intent data);
}
