package com.kikulabs.favmov.database;

import android.database.Cursor;

public interface LoadCallback {
    void preExecute();
    void postExecute(Cursor cursor);
}
