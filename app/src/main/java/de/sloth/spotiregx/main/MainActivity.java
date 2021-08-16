package de.sloth.spotiregx.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import de.sloth.spotiregx.R;
import de.sloth.spotiregx.lib.spotify.api.SpotifyAuthService;
import de.sloth.spotiregx.search.SearchActivity;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {


    @Inject
    protected SpotifyAuthService mSpotifyAuthService;

    private View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mView = findViewById(R.id.artists_list_fragment);
        mView.setEnabled(false);
        mSpotifyAuthService.redirectForAuthorization(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mSpotifyAuthService.registerAuthentication(resultCode, data)){
            mView.setEnabled(true);
        }else{
            mSpotifyAuthService.redirectForAuthorization(this);
        }
    }

    @SuppressWarnings("java:S1172")
    public void onAdd(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}
