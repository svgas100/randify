package de.sloth.spotiregx.lib.spotify.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.spotify.android.appremote.api.PlayerApi;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SpotifyPlayServiceImplTest {

    @Mock
    private SpotifyInternalApiAccessor spotifyInternalApiAccessorMock;

    @Mock
    private PlayerApi playerApiMock;

    @Mock
    private SpotifyAppRemote appRemoteMock;

    @InjectMocks
    private SpotifyPlayServiceImpl unitUnderTest;

    @BeforeEach
    public void setup() {
        when(appRemoteMock.getPlayerApi()).thenReturn(playerApiMock);
        when(spotifyInternalApiAccessorMock.getSpotifyAppRemote()).thenReturn(appRemoteMock);
    }

    @Test
    void testPlay() {
        unitUnderTest.playAlbum("test:album");

        verify(spotifyInternalApiAccessorMock).getSpotifyAppRemote();
        verify(appRemoteMock).getPlayerApi();
        verify(playerApiMock).play("spotify:album:test:album");
    }
}