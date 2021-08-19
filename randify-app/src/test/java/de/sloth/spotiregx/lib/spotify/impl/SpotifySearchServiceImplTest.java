package de.sloth.spotiregx.lib.spotify.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import de.sloth.spotiregx.lib.spotify.api.model.ArtistVO;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Pager;

@ExtendWith(MockitoExtension.class)
class SpotifySearchServiceImplTest {

    @Mock
    private SpotifyInternalApiAccessor mSpotifyApiAccessorMock;

    @InjectMocks
    private SpotifySearchServiceImpl unitUnderTest;

    @Test
    void testNullSearch(){
        List<ArtistVO> result = unitUnderTest.searchArtists(null);

        verifyNoInteractions(mSpotifyApiAccessorMock);
        assertTrue(result.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "t", "te"})
    void testSearchForStringsShort3(String param){
        List<ArtistVO> result = unitUnderTest.searchArtists(param);

        verifyNoInteractions(mSpotifyApiAccessorMock);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSearch(){
        SpotifyService spotifyServiceMock = mock(SpotifyService.class);
        when(mSpotifyApiAccessorMock.getSpotifyWebApi()).thenReturn(spotifyServiceMock);
        ArtistsPager artistsPagerMock = mock(ArtistsPager.class);
        when(spotifyServiceMock.searchArtists(any())).thenReturn(artistsPagerMock);
        Pager<Artist> artistPagerMock = mock(Pager.class);
        artistsPagerMock.artists = artistPagerMock;
        artistPagerMock.items = Arrays.asList(artistMock(1),artistMock(2));

        List<ArtistVO> result = unitUnderTest.searchArtists("searching");

        verify(spotifyServiceMock).searchArtists("searching");

        assertEquals(2, result.size());
        for(int i = 1; i <= result.size(); i++){
            ArtistVO resultArtist = result.get(i-1);
            assertEquals("uri" + i, resultArtist.getUri());
            assertEquals("id" + i, resultArtist.getId());
            assertEquals("href" + i, resultArtist.getHref());
            assertEquals("name" + i, resultArtist.getName());
            assertEquals("type" + i, resultArtist.getType());
            assertEquals(Map.of("key"+i,"value"+i), resultArtist.getExternalUrls());
        }
    }

    private Artist artistMock(int number){
        Artist artist = mock(Artist.class);
        artist.uri = "uri" + number;
        artist.id =  "id" + number;
        artist.href = "href" + number;
        artist.name = "name" + number;
        artist.type = "type" + number;
        artist.external_urls = Map.of("key"+number,"value"+number);
        return artist;
    }
}