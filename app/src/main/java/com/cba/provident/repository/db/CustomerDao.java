package com.cba.provident.repository.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cba.provident.repository.db.model.CustomerDbModel;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface CustomerDao {
    @Query("SELECT * FROM CustomerDbModel")
    Single<List<CustomerDbModel>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CustomerDbModel> customers);

    @Query("SELECT * FROM CustomerDbModel WHERE id =:id")
    Single<CustomerDbModel> getById(int id);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    Single<Integer> update(CustomerDbModel apply);
}