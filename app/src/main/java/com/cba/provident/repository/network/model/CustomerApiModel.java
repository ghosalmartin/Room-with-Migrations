package com.cba.provident.repository.network.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerApiModel {
    private int id;
    private String displayName;
    private String firstName;
    private String lastName;
    private String status;
    private boolean newIssueAllowed;
    private Float latitude;
    private Float longitude;
    private String preferredCollectionDay;
    private Date preferredCollectionTime;
}
