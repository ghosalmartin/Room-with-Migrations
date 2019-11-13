package com.cba.provident.repository.adapters;

import com.cba.provident.repository.db.model.CustomerDbModel;
import com.cba.provident.repository.model.CustomerModel;

import io.reactivex.functions.Function;

public class DbToModelConverter implements Function<CustomerDbModel, CustomerModel> {

    @Override
    public CustomerModel apply(CustomerDbModel customerDbModel) {
        return CustomerModel.builder()
                .id(customerDbModel.getId())
                .displayName(customerDbModel.getDisplayName())
                .status(customerDbModel.getStatus())
                .firstName(customerDbModel.getFirstName())
                .lastName(customerDbModel.getLastName())
                .newIssueAllowed(customerDbModel.getNewIssueAllowed())
                .latitude(customerDbModel.getLatitude())
                .longitude(customerDbModel.getLongitude())
                .preferredCollectionDay(customerDbModel.getPreferredCollectionDay())
                .preferredCollectionTime(customerDbModel.getPreferredCollectionTime())
                .build();
    }

}
