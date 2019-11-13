package com.cba.provident.repository.adapters;

import com.cba.provident.repository.db.model.CustomerDbModel;
import com.cba.provident.repository.model.CustomerModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;

public class DbListToModelConverter implements Function<List<CustomerDbModel>, List<CustomerModel>> {

    @Override
    public List<CustomerModel> apply(List<CustomerDbModel> customerDbModels) {
        ArrayList<CustomerModel> customers = new ArrayList<>();

        for (CustomerDbModel customerApiModel : customerDbModels) {
            customers.add(
                    CustomerModel.builder()
                            .id(customerApiModel.getId())
                            .displayName(customerApiModel.getDisplayName())
                            .status(customerApiModel.getStatus())
                            .build()
            );
        }

        return customers;
    }

}
