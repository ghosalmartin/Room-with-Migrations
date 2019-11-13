package com.cba.provident.repository.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cba.provident.repository.db.model.CustomerDbModel;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface CustomerDao {
    @Query("SELECT * FROM CustomerDbModel")
    Single<List<CustomerDbModel>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CustomerDbModel> customers);
}