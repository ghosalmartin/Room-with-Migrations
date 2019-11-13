package com.cba.provident.repository;

import com.cba.provident.repository.adapters.ApiListToDbConverter;
import com.cba.provident.repository.adapters.ApiToDbConverter;
import com.cba.provident.repository.adapters.DbListToModelConverter;
import com.cba.provident.repository.adapters.DbToModelConverter;
import com.cba.provident.repository.db.CustomerDatabase;
import com.cba.provident.repository.model.CustomerModel;
import com.cba.provident.repository.network.CustomerService;

import java.util.List;

import io.reactivex.Single;

public class CustomerRepository {

    private final CustomerService service;
    private final CustomerDatabase customerDatabase;
    private final DbListToModelConverter dbListToModelConverter;
    private final DbToModelConverter dbToModelConverter;
    private final ApiListToDbConverter apiListToDbConverter;
    private final ApiToDbConverter apiToDbConverter;

    public CustomerRepository(CustomerService service,
                              CustomerDatabase customerDatabase,
                              DbListToModelConverter dbListToModelConverter,
                              ApiListToDbConverter apiListToDbConverter,
                              DbToModelConverter dbToModelConverter,
                              ApiToDbConverter apiToDbConverter) {
        this.service = service;
        this.customerDatabase = customerDatabase;
        this.dbListToModelConverter = dbListToModelConverter;
        this.apiListToDbConverter = apiListToDbConverter;
        this.dbToModelConverter = dbToModelConverter;
        this.apiToDbConverter = apiToDbConverter;
    }

    public Single<List<CustomerModel>> getCustomers() {
        return service.getCustomers()
                .map(apiListToDbConverter)
                .flatMap(customerDbModelV1s -> {
                    customerDatabase.getDao().insertAll(customerDbModelV1s);
                    return customerDatabase.getDao().getAll();
                })
                .map(dbListToModelConverter);
    }

    public Single<CustomerModel> getCustomerDetails(int id) {
        return customerDatabase.getDao()
                .getById(id)
                .flatMap(customerDbModel -> {
                    if (customerDbModel.getFirstName() != null) {
                        return Single.just(customerDbModel);
                    } else {
                        return service.getCustomerDetailsById(id)
                                .flatMap(customerApiModel -> {
                                    //This is required because the ID's are out of sync on the server
                                    customerApiModel.setId(id);
                                    return customerDatabase
                                            .getDao()
                                            .update(apiToDbConverter.apply(customerApiModel))
                                            .map(integer -> id);
                                })
                                .flatMap(updatedId -> customerDatabase.getDao().getById(updatedId));
                    }
                })
                .map(dbToModelConverter);
    }

}
