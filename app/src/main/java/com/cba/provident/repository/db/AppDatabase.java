package com.cba.provident.repository.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.cba.provident.repository.db.model.CustomerDbModel;

@Database(entities = {CustomerDbModel.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CustomerDao customerDao();
}