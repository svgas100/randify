package de.sloth.spotiregx.lib.db.impl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import de.sloth.spotiregx.lib.db.api.DatabaseService;

@Module
@InstallIn(SingletonComponent.class)
public abstract class DatabaseModule {

    @Provides
    @Singleton
    static DatabaseService bindDatabaseService(){
        return new DatabaseServiceImpl();
    }
}
