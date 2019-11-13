package com.cba.provident.repository.db;

import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
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
                .build();
    }

    public CustomerDao getDao(){
        return db.customerDao();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy() {
        db.close();
    }
}