package com.cba.provident.ui;


import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
class CustomerUIModel {
    private int id;
    private String name;
    private String status;
}
