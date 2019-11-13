package com.cba.provident.repository.db.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
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

    @ColumnInfo(name = "firstName")
    private String firstName;

    @ColumnInfo(name = "lastName")
    private String lastName;

    @ColumnInfo(name = "newIssueAllowed")
    private Boolean newIssueAllowed;

    @ColumnInfo(name = "latitude")
    private Float latitude;

    @ColumnInfo(name = "longitude")
    private Float longitude;

    @ColumnInfo(name = "preferredCollectionDay")
    private String preferredCollectionDay;

    @ColumnInfo(name = "preferredCollectionTime")
    private Date preferredCollectionTime;

}