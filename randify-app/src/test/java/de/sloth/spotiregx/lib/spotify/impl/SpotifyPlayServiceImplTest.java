package de.sloth.spotiregx.lib.spotify.impl;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.spotify.android.appremote.api.PlayerApi;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import de.sloth.spotiregx.lib.spotify.api.SpotifyPlayService;


@ExtendWith(MockitoExtension.class)
class SpotifyPlayServiceImplTest {

    private final SpotifyInternalApiAccessor spotifyInternalApiAccessorMock = mock(SpotifyInternalApiAccessor.class);
    private final PlayerApi playerApiMock = mock(PlayerApi.class);
    private final SpotifyAppRemote appRemoteMock = mock(SpotifyAppRemote.class);

    private SpotifyPlayService unitUnderTest;

    @BeforeEach
    public void setup() {
        when(appRemoteMock.getPlayerApi()).thenReturn(playerApiMock);
        when(spotifyInternalApiAccessorMock.getSpotifyAppRemote()).thenReturn(appRemoteMock);
        unitUnderTest = new SpotifyPlayServiceImpl(spotifyInternalApiAccessorMock);

    }

    @Test
    void testPlay() {
        unitUnderTest.playAlbum("test:album");

        verify(spotifyInternalApiAccessorMock).getSpotifyAppRemote();
        verify(appRemoteMock).getPlayerApi();
        verify(playerApiMock).play("spotify:album:test:album");
    }
}