package com.cba.provident.repository.adapters;

import com.cba.provident.repository.db.model.CustomerDbModel;
import com.cba.provident.repository.model.CustomerModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;

public class DbToModelConverter implements Function<List<CustomerDbModel>, List<CustomerModel>> {

    @Override
    public List<CustomerModel> apply(List<CustomerDbModel> customerDbModels) {
        ArrayList<CustomerModel> customers = new ArrayList<>();

        for (CustomerDbModel customerApiModel : customerDbModels) {
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
