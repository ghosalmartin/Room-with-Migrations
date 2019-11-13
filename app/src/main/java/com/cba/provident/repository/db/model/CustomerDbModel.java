package com.cba.provident.repository.db.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CustomerDbModel {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "displayName")
    private String displayName;

    @ColumnInfo(name = "status")
    private String status;
}