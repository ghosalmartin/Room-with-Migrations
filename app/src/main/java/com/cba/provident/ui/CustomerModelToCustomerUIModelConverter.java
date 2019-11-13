package com.cba.provident.ui;

import com.cba.provident.repository.model.CustomerModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;

public class CustomerModelToCustomerUIModelConverter implements Function<List<CustomerModel>, List<CustomerUIModel>> {

    @Override
    public List<CustomerUIModel> apply(List<CustomerModel> customerModels) {
        ArrayList<CustomerUIModel> customerUIModels = new ArrayList<>();

        for (CustomerModel customerModel : customerModels) {
            customerUIModels.add(
                    new CustomerUIModel(
                            customerModel.getId(),
                            customerModel.getName(),
                            customerModel.getStatus()
                    )
            );
        }

        return customerUIModels;
    }
}
