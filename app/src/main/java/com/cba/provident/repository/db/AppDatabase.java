package com.cba.provident.repository.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cba.provident.repository.db.model.CustomerDbModel;

@Database(entities = {CustomerDbModel.class}, version = 3, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract CustomerDao customerDao();

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE CustomerDbModel ADD COLUMN status VARCHAR");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE CustomerDbModel ADD COLUMN firstName TEXT");
            database.execSQL("ALTER TABLE CustomerDbModel ADD COLUMN lastName TEXT");
            database.execSQL("ALTER TABLE CustomerDbModel ADD COLUMN newIssueAllowed INTEGER");
            database.execSQL("ALTER TABLE CustomerDbModel ADD COLUMN latitude REAL");
            database.execSQL("ALTER TABLE CustomerDbModel ADD COLUMN longitude REAL");
            database.execSQL("ALTER TABLE CustomerDbModel ADD COLUMN preferredCollectionDay TEXT");
            database.execSQL("ALTER TABLE CustomerDbModel ADD COLUMN preferredCollectionTime INTEGER");
        }
    };

}