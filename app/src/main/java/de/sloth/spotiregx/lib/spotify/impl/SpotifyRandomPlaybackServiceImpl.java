package de.sloth.spotiregx.lib.spotify.impl;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

import javax.inject.Inject;

import de.sloth.spotiregx.lib.spotify.api.SpotifyAuthService;
import de.sloth.spotiregx.lib.spotify.api.SpotifyPlayService;
import de.sloth.spotiregx.lib.spotify.api.SpotifyRandomPlaybackService;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.Pager;
import retrofit.RetrofitError;

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
        List<Album> allEpisodes = new ArrayList<>();
        try {
            Collection<Album> tAlbums = getAllArtistsAlbums(artistsSpotifyUri);
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

    private Collection<Album> getAllArtistsAlbums(String artistId) throws SpotifyError {

        int offset = 0;
        Collection<Album> tResultCollection = new ArrayList<>();
        Pager<Album> albumPager;

        Map<String, Object> tOptions = new HashMap<>();
        tOptions.put(SpotifyService.LIMIT, 50);

        try {
            do {
                tOptions.put(SpotifyService.OFFSET, offset);

                String tArtistId;
                if(artistId.contains("spotify:artist:")){
                    tArtistId = artistId.replace("spotify:artist:", "");
                }else{
                    tArtistId = artistId;
                }

                albumPager = mSpotifyAuthService.getSpotifyWebApi().getArtistAlbums(tArtistId, tOptions);

                tResultCollection.addAll(albumPager.items);

                offset += albumPager.limit;

            } while (albumPager.next != null);
        } catch (RetrofitError error) {
            throw SpotifyError.fromRetrofitError(error);
        }

        return tResultCollection;
    }
}
