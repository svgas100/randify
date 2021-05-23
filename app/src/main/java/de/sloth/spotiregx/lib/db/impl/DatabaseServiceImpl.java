package de.sloth.spotiregx.lib.db.impl;

import android.content.Context;

import androidx.room.Room;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import de.sloth.spotiregx.lib.db.api.DatabaseService;
import de.sloth.spotiregx.lib.db.model.AppDatabase;

public class DatabaseServiceImpl implements DatabaseService {


    public static final String SPOTIEX_DB = "spotiex-db";

    public void performDatabaseAction(Context context, Consumer<AppDatabase> databaseAction) {
        AppDatabase database = Room.databaseBuilder(context,
                AppDatabase.class, SPOTIEX_DB).build();

        CompletableFuture.runAsync(() ->
                databaseAction.accept(database));
    }
}
