package com.cba.provident.repository.network;

import com.cba.provident.repository.network.model.CustomerApiModel;
import com.cba.provident.repository.network.model.CustomersApiModel;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CustomerService {
    @GET("customers.js")
    Single<CustomersApiModel> getCustomers();

    @GET("customers/{id}.js")
    Single<CustomerApiModel> getCustomerDetailsById(@Path("id") int id);
}