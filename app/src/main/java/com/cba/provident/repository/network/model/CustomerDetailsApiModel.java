package com.cba.provident.repository.network.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Builder(toBuilder = true)
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDetailsApiModel {
    int id;
    String displayName;
    String firstName;
    String lastName;
    String status;
    boolean newIssueAllowed;
    Float latitude;
    Float longitude;
    String preferredCollectionDay;
    Date preferredCollectionTime;
}
