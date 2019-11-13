package com.cba.provident.repository.adapters;


import com.cba.provident.repository.db.model.CustomerDbModel;
import com.cba.provident.repository.network.model.CustomerApiModel;
import com.cba.provident.repository.network.model.CustomersApiModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;

public class ApiListToDbConverter implements Function<CustomersApiModel, List<CustomerDbModel>> {

    @Override
    public List<CustomerDbModel> apply(CustomersApiModel customersApiModel) {
        ArrayList<CustomerDbModel> customers = new ArrayList<>();

        for (CustomerApiModel customerApiModel : customersApiModel.getCustomers()) {
            customers.add(
                    CustomerDbModel.builder().id(
                            customerApiModel.getId())
                            .displayName(customerApiModel.getDisplayName())
                            .status(customerApiModel.getStatus()).build()
            );
        }

        return customers;
    }
}
