package de.sloth.spotiregx.lib.spotify.impl.util;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Pager;
import retrofit.RetrofitError;

/**
 * @deprecated This is deprecated as it directly access the spotify web api.
 */
@Deprecated
public class PagingHelper {

    private final SpotifyService spotifyService;
    private final Map<String, Object> options;

    public PagingHelper(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
        options = new HashMap<>();
        options.put(SpotifyService.LIMIT, 50);
    }

    public Collection<Album> getAllArtistsAlbums(String artistId) throws SpotifyError {

        int offset = 0;
        Collection<Album> tResultCollection = new ArrayList<>();
        Pager<Album> albumPager;

        Map<String, Object> tOptions = new HashMap<>(options);

        try {
            do {
                tOptions.put(SpotifyService.OFFSET, offset);

                String tArtistId;
                if(artistId.contains("spotify:artist:")){
                    tArtistId = artistId.replace("spotify:artist:", "");
                }else{
                    tArtistId = artistId;
                }

                albumPager = spotifyService.getArtistAlbums(tArtistId, tOptions);

                tResultCollection.addAll(albumPager.items);

                offset += albumPager.limit;

            } while (albumPager.next != null);
        } catch (RetrofitError error) {
            throw SpotifyError.fromRetrofitError(error);
        }

        return tResultCollection;
    }

    public Collection<Artist> searchArtists(String artistId) {
        ArtistsPager artistsPager = spotifyService.searchArtists(artistId);
        return artistsPager.artists.items;
    }
}
