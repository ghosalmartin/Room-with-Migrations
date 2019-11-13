package com.cba.provident.repository;

import com.cba.provident.repository.adapters.ApiToDbConverter;
import com.cba.provident.repository.adapters.ApiToModelConverter;
import com.cba.provident.repository.adapters.DbToModelConverter;
import com.cba.provident.repository.db.CustomerDatabase;
import com.cba.provident.repository.model.CustomerModel;
import com.cba.provident.repository.network.CustomerService;

import java.util.List;

import io.reactivex.Single;

public class CustomerRepository {

    private final CustomerService service;
    private final CustomerDatabase customerDatabase;
    private final ApiToModelConverter apiToModelConverter;
    private final DbToModelConverter dbToModelConverter;
    private final ApiToDbConverter apiToDbConverter;

    public CustomerRepository(CustomerService service,
                              CustomerDatabase customerDatabase,
                              ApiToModelConverter apiToModelConverter,
                              DbToModelConverter dbToModelConverter,
                              ApiToDbConverter apiToDbConverter) {
        this.service = service;
        this.customerDatabase = customerDatabase;
        this.apiToModelConverter = apiToModelConverter;
        this.dbToModelConverter = dbToModelConverter;
        this.apiToDbConverter = apiToDbConverter;
    }

    public Single<List<CustomerModel>> getCustomers() {
        return service.getCustomers()
                .map(apiToDbConverter)
                .flatMap(customerDbModelV1s -> {
                    customerDatabase.getDao().insertAll(customerDbModelV1s);
                    return customerDatabase.getDao().getAll();
                })
                .map(dbToModelConverter);
    }

}
