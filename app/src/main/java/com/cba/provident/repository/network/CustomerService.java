package com.cba.provident.repository.network;

import com.cba.provident.repository.network.model.CustomerDetailsApiModel;
import com.cba.provident.repository.network.model.CustomersApiModel;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface CustomerService {
    @GET("customers.js")
    Single<CustomersApiModel> getCustomers();

    @GET("customers/{id}.js")
    Single<CustomerDetailsApiModel> getCustomerDetailsById(int id);
}