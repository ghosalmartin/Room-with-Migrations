package com.cba.provident.repository.adapters;


import com.cba.provident.repository.db.model.CustomerDbModel;
import com.cba.provident.repository.network.model.CustomerApiModel;
import com.cba.provident.repository.network.model.CustomersApiModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApiListToDbConverter implements Function<CustomersApiModel, List<CustomerDbModel>> {

    private ApiToDbConverter apiToDbConverter;

    @Override
    public List<CustomerDbModel> apply(CustomersApiModel customersApiModel) {
        ArrayList<CustomerDbModel> customers = new ArrayList<>();

        for (CustomerApiModel customerApiModel : customersApiModel.getCustomers()) {
            customers.add(apiToDbConverter.apply(customerApiModel));
        }

        return customers;
    }
}
