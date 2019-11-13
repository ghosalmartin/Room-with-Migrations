package com.cba.provident.repository.adapters;

import com.cba.provident.repository.model.CustomerModel;
import com.cba.provident.repository.network.model.CustomerApiModel;
import com.cba.provident.repository.network.model.CustomersApiModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;

public class ApiToModelConverter implements Function<CustomersApiModel, List<CustomerModel>> {

    @Override
    public List<CustomerModel> apply(CustomersApiModel customersApiModel) {
        ArrayList<CustomerModel> customers = new ArrayList<>();

        for (CustomerApiModel customerApiModel : customersApiModel.getCustomers()) {
            customers.add(
                    CustomerModel.builder()
                            .id(customerApiModel.getId())
                            .name(customerApiModel.getDisplayName())
                            .build()
            );
        }

        return customers;
    }
}
