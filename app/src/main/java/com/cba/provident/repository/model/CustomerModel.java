package com.cba.provident.repository.model;

import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class CustomerModel {
    private int id;
    private String name;
}
