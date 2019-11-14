package com.cba.provident.repository;

import com.cba.provident.repository.adapters.ApiListToDbConverter;
import com.cba.provident.repository.adapters.ApiToDbConverter;
import com.cba.provident.repository.adapters.DbListToModelConverter;
import com.cba.provident.repository.adapters.DbToModelConverter;
import com.cba.provident.repository.db.CustomerDao;
import com.cba.provident.repository.db.CustomerDatabase;
import com.cba.provident.repository.model.CustomerModel;
import com.cba.provident.repository.network.CustomerService;

import java.util.List;

import io.reactivex.Single;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomerRepository {

    private final CustomerService service;
    private final CustomerDao customerDao;
    private final DbListToModelConverter dbListToModelConverter;
    private final DbToModelConverter dbToModelConverter;
    private final ApiListToDbConverter apiListToDbConverter;
    private final ApiToDbConverter apiToDbConverter;

    public Single<List<CustomerModel>> getCustomers() {
        return service.getCustomers()
                .map(apiListToDbConverter)
                .flatMap(customerDbModelV1s -> {
                    customerDao.insertAll(customerDbModelV1s);
                    return customerDao.getAll();
                })
                .map(dbListToModelConverter);
    }

    public Single<CustomerModel> getCustomerDetails(int id) {
        return customerDao
                .getById(id)
                .flatMap(customerDbModel -> {
                    if (customerDbModel.getFirstName() != null) {
                        return Single.just(customerDbModel);
                    } else {
                        return service.getCustomerDetailsById(id)
                                .flatMap(customerApiModel -> {
                                    //This is required because the ID's are out of sync on the server
                                    //Realistically should be fixed on server side but hey ho
                                    customerApiModel.setId(id);
                                    return customerDao
                                            .update(apiToDbConverter.apply(customerApiModel))
                                            .map(integer -> id);
                                })
                                .flatMap(customerDao::getById);
                    }
                })
                .map(dbToModelConverter);
    }

}
