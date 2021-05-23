package de.sloth.spotiregx.lib.db.api;

import android.content.Context;

import java.util.function.Consumer;

import de.sloth.spotiregx.lib.db.model.AppDatabase;

public interface DatabaseService {

    void performDatabaseAction(Context context, Consumer<AppDatabase> databaseAction);
}
