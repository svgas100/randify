package de.sloth.spotiregx.lib.spotify.impl;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import javax.inject.Inject;

import de.sloth.spotiregx.lib.spotify.api.SpotifyAuthService;
import de.sloth.spotiregx.lib.spotify.api.SpotifyPlayService;
import de.sloth.spotiregx.lib.spotify.api.SpotifyRandomPlaybackService;
import de.sloth.spotiregx.lib.spotify.impl.util.PagingHelper;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Album;

public class SpotifyRandomPlaybackServiceImpl implements SpotifyRandomPlaybackService {

    private static final String PREFIX = SpotifyRandomPlaybackServiceImpl.class.getSimpleName();


    private final SpotifyAuthService mSpotifyAuthService;

    private final SpotifyPlayService mSpotifyPlayService;

    @Inject
    public SpotifyRandomPlaybackServiceImpl(SpotifyAuthService spotifyAuthService, SpotifyPlayService spotifyPlayService) {
        mSpotifyAuthService = spotifyAuthService;
        mSpotifyPlayService = spotifyPlayService;
    }

    @Override
    public String playRandomAlbumOfArtists(String artistsSpotifyUri, Predicate<Album> aFilter) {
        SpotifyService spotifyApi = mSpotifyAuthService.getSpotifyWebApi();

        List<Album> allEpisodes = new ArrayList<>();
        PagingHelper tHelper = new PagingHelper(spotifyApi);

        try {
            Collection<Album> tAlbums = tHelper.getAllArtistsAlbums(artistsSpotifyUri);
            tAlbums.stream().filter(aFilter).forEach(allEpisodes::add);
        } catch (SpotifyError spotifyError) {
            Log.e(PREFIX, "Failed to fetch albums from spotify", spotifyError);
        }

        Log.i(PREFIX, "Trying to play random album!");

        Album album = allEpisodes.get(new Random().nextInt(allEpisodes.size()));

        mSpotifyPlayService.playAlbum(album.id);

        Log.i(PREFIX, "Playing " + album.name);
        return album.name;
    }
}
