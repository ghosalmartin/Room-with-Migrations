package com.cba.provident.ui;


import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
class CustomerDetailsUIModel implements Serializable {
    private int id;
    private String displayName;
    private String firstName;
    private String lastName;
    private String status;
    private Boolean newIssueAllowed;
    private Float latitude;
    private Float longitude;
    private String preferredCollectionDay;
    private Date preferredCollectionTime;
}
