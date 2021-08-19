package de.sloth.spotiregx.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import de.sloth.spotiregx.R;
import de.sloth.spotiregx.lib.spotify.api.SpotifyAuthService;
import de.sloth.spotiregx.search.SearchActivity;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {


    @Inject
    protected SpotifyAuthService mSpotifyAuthService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton mFloatingButton = findViewById(R.id.floating_action_button);
        mFloatingButton.setOnClickListener(this::onAdd);
        final View mView = findViewById(R.id.artists_list_fragment);
        mView.setEnabled(false);
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        mSpotifyAuthService.registerAuthentication(data);
                        mView.setEnabled(true);
                    }
                    // error handling
                });

        Intent intent = mSpotifyAuthService.redirectForAuthorization(this);
        someActivityResultLauncher.launch(intent);
    }

    private void onAdd(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}

