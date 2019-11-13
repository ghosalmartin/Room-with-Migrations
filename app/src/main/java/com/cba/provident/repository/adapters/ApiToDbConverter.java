package com.cba.provident.repository.adapters;

import com.cba.provident.repository.db.model.CustomerDbModel;
import com.cba.provident.repository.network.model.CustomerApiModel;

import io.reactivex.functions.Function;

public class ApiToDbConverter implements Function<CustomerApiModel, CustomerDbModel> {

    @Override
    public CustomerDbModel apply(CustomerApiModel customersApiModel) {
        return CustomerDbModel.builder()
                .id(customersApiModel.getId())
                .displayName(customersApiModel.getDisplayName())
                .status(customersApiModel.getStatus())
                .firstName(customersApiModel.getFirstName())
                .lastName(customersApiModel.getLastName())
                .newIssueAllowed(customersApiModel.isNewIssueAllowed())
                .latitude(customersApiModel.getLatitude())
                .longitude(customersApiModel.getLongitude())
                .preferredCollectionDay(customersApiModel.getPreferredCollectionDay())
                .preferredCollectionTime(customersApiModel.getPreferredCollectionTime())
                .build();
    }
}