package de.sloth.spotiregx.lib.spotify.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;
import com.spotify.sdk.android.auth.LoginActivity;

import javax.inject.Singleton;

import de.sloth.spotiregx.R;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;

public class SpotifyAuthServiceImpl implements SpotifyAuthServiceInternal {

    private static final String PREFIX = SpotifyAuthServiceImpl.class.getSimpleName();

    private static final String CLIENT_ID = "ae37e92226e24aae8251e4679ac6b13e";

    private static final String EXTRA_AUTH_RESPONSE = "EXTRA_AUTH_RESPONSE";

    private static final String RESPONSE_KEY = "response";

    /*
     * Stateful
     */
    private SpotifyAppRemote mSpotifyAppRemote;

    private SpotifyService mSpotifyService;

    @Override
    public Intent redirectForAuthorization(Activity activity) {
        Log.i(PREFIX, "Invoked spotify authorization.");

        Context context = activity.getApplicationContext();

        if (!SpotifyAppRemote.isSpotifyInstalled(context)) {
            Log.e(PREFIX, "Spotify not installed!");
            AuthorizationClient.openDownloadSpotifyActivity(activity);
        }

        String tRedirectSchema = context.getString(R.string.com_spotify_sdk_redirect_scheme);
        if (TextUtils.isEmpty(tRedirectSchema)) {
            Log.e(PREFIX, "Redirect schema not set!");
            throw new NullPointerException("Spotify redirect schema may not be empty or null.");
        }

        String tRedirectHost = context.getString(R.string.com_spotify_sdk_redirect_host);
        if (TextUtils.isEmpty(tRedirectHost)) {
            Log.e(PREFIX, "Redirect host not set!");
            throw new NullPointerException("Spotify redirect host may not be empty or null.");
        }

        String tRedirectUrl = tRedirectSchema + "://" + tRedirectHost;

        ConnectionParams connectionParams = new ConnectionParams.Builder(CLIENT_ID)
                .setAuthMethod(ConnectionParams.AuthMethod.APP_ID)
                .setRedirectUri(tRedirectUrl)
                .build();

        AuthorizationRequest request = getAuthenticationRequest(tRedirectUrl);


        // Connect to spotify remote app.
        SpotifyAppRemote.connect(context, connectionParams, new Connector.ConnectionListener() {

            @Override
            public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                mSpotifyAppRemote = spotifyAppRemote;
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(PREFIX, "Failed to connect to spotify app!", throwable);
            }
        });

        // Retrieve auth token.
        return AuthorizationClient.createLoginActivityIntent(activity, request);
    }

    @Override
    public boolean registerAuthentication(Intent data) {
        AuthorizationResponse response =  data.getBundleExtra(EXTRA_AUTH_RESPONSE).getParcelable(RESPONSE_KEY);
        if (response.getError() != null && response.getError().isEmpty()) {
            Log.e(PREFIX, "Something went wrong requesting the authorization token.");
            return false;
        }
        SpotifyApi mSpotifyWebApi = new SpotifyApi();
        String mAccessToken = response.getAccessToken();
        mSpotifyWebApi.setAccessToken(mAccessToken);
        mSpotifyService = mSpotifyWebApi.getService();
        Log.i(PREFIX, "Authorized!");
        return true;
    }

    @Override
    public SpotifyAppRemote getSpotifyAppRemote() {
        return mSpotifyAppRemote;
    }

    @Override
    public SpotifyService getSpotifyWebApi() {
        return mSpotifyService;
    }

    private AuthorizationRequest getAuthenticationRequest(String aRedirectUrl) {
        return new AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, aRedirectUrl)
                .setShowDialog(false)
                .setScopes(new String[]{"user-read-email"})
                .setCampaign("your-campaign-token")
                .build();
    }
}
