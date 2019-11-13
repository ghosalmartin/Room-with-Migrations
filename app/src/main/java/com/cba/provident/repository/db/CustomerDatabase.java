package com.cba.provident.repository.db;

import android.content.Context;

import androidx.lifecycle.LifecycleObserver;
import androidx.room.Room;

public class CustomerDatabase implements LifecycleObserver {

    private static CustomerDatabase single_instance = null;

    private AppDatabase db;

    private CustomerDatabase(Context context) {
        this.db = initDb(context.getApplicationContext());
    }

    public static CustomerDatabase getInstance(Context context) {
        if (single_instance == null)
            single_instance = new CustomerDatabase(context);

        return single_instance;
    }

    private AppDatabase initDb(Context applicationContext) {
        return Room
                .databaseBuilder(applicationContext, AppDatabase.class, "customer-data")
                .addMigrations(AppDatabase.MIGRATION_1_2)
                .addMigrations(AppDatabase.MIGRATION_2_3)
                .build();
    }

    public CustomerDao getDao() {
        return db.customerDao();
    }
}