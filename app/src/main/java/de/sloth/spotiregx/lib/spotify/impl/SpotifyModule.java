package de.sloth.spotiregx.lib.spotify.impl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import de.sloth.spotiregx.lib.spotify.api.SpotifyAuthService;
import de.sloth.spotiregx.lib.spotify.api.SpotifyPlayService;
import de.sloth.spotiregx.lib.spotify.api.SpotifyRandomPlaybackService;
import de.sloth.spotiregx.lib.spotify.api.SpotifySearchService;

@Module
@InstallIn(SingletonComponent.class)
public abstract class SpotifyModule {

    @Provides
    @Singleton
    static SpotifyAuthService bindSpotifyAuthService(){
        return new SpotifyAuthServiceImpl();
    }

    @Binds
    @Singleton
    public abstract SpotifyPlayService bindSpotifyPlayService(SpotifyPlayServiceImpl spotifyPlayService);

    @Binds
    @Singleton
    public abstract SpotifyRandomPlaybackService bindSpotifyRandomPlaybackService(SpotifyRandomPlaybackServiceImpl spotifyRandomPlaybackService);

    @Binds
    @Singleton
    public abstract SpotifySearchService bindSpotifySearchService(SpotifySearchServiceImpl spotifySearchService);
}
