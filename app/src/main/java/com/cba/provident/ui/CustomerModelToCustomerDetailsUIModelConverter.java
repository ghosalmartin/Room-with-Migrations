package com.cba.provident.ui;

import com.cba.provident.repository.model.CustomerModel;

import io.reactivex.functions.Function;

public class CustomerModelToCustomerDetailsUIModelConverter implements Function<CustomerModel, CustomerDetailsUIModel> {

    @Override
    public CustomerDetailsUIModel apply(CustomerModel customerModel) {
        return CustomerDetailsUIModel.builder()
                .id(customerModel.getId())
                .displayName(customerModel.getDisplayName())
                .status(customerModel.getStatus())
                .firstName(customerModel.getFirstName())
                .lastName(customerModel.getLastName())
                .newIssueAllowed(customerModel.isNewIssueAllowed())
                .latitude(customerModel.getLatitude())
                .longitude(customerModel.getLongitude())
                .preferredCollectionDay(customerModel.getPreferredCollectionDay())
                .preferredCollectionTime(customerModel.getPreferredCollectionTime())
                .build();
    }

}
