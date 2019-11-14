package com.cba.provident.repository.adapters;

import com.cba.provident.repository.db.model.CustomerDbModel;
import com.cba.provident.repository.network.model.CustomerApiModel;

import io.reactivex.functions.Function;

public class ApiToDbConverter implements Function<CustomerApiModel, CustomerDbModel> {

    @Override
    public CustomerDbModel apply(CustomerApiModel customerApiModel) {
        return CustomerDbModel.builder()
                .id(customerApiModel.getId())
                .displayName(customerApiModel.getDisplayName())
                .status(customerApiModel.getStatus())
                .firstName(customerApiModel.getFirstName())
                .lastName(customerApiModel.getLastName())
                .newIssueAllowed(customerApiModel.isNewIssueAllowed())
                .latitude(customerApiModel.getLatitude())
                .longitude(customerApiModel.getLongitude())
                .preferredCollectionDay(customerApiModel.getPreferredCollectionDay())
                .preferredCollectionTime(customerApiModel.getPreferredCollectionTime())
                .build();
    }
}