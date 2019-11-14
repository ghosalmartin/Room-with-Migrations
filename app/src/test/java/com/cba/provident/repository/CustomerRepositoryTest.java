package com.cba.provident.repository;

import com.cba.provident.repository.adapters.ApiListToDbConverter;
import com.cba.provident.repository.adapters.ApiToDbConverter;
import com.cba.provident.repository.adapters.DbListToModelConverter;
import com.cba.provident.repository.adapters.DbToModelConverter;
import com.cba.provident.repository.db.CustomerDao;
import com.cba.provident.repository.db.model.CustomerDbModel;
import com.cba.provident.repository.model.CustomerModel;
import com.cba.provident.repository.network.CustomerService;
import com.cba.provident.repository.network.model.CustomerApiModel;
import com.cba.provident.repository.network.model.CustomersApiModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Single;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerRepositoryTest {

    @Mock
    private CustomerService mockCustomerService;

    @Mock
    private CustomerDao mockCustomerDao;

    @Mock
    private DbListToModelConverter mockDbListToModelConverter;

    @Mock
    private DbToModelConverter mockDbToModelConverter;

    @Mock
    private ApiListToDbConverter mockApiListToDbConverter;

    @Mock
    private ApiToDbConverter mockApiToDbConverter;

    @Mock
    private CustomersApiModel customerApiModels;

    @Mock
    private CustomerApiModel customerApiModel;

    @Mock
    private CustomerDbModel customerDbModel;

    @Mock
    private List<CustomerDbModel> customerDbModels;

    @Mock
    private List<CustomerModel> customerModels;

    @Mock
    private CustomerModel customerModel;

    @InjectMocks
    private CustomerRepository repository;

    private int id = 10;

    @Test
    public void getCustomersInsertsAllIntoDbGetsThemAndMapsThemToModel() {
        //Given
        when(mockCustomerService.getCustomers()).thenReturn(Single.just(customerApiModels));
        when(mockApiListToDbConverter.apply(customerApiModels)).thenReturn(customerDbModels);
        when(mockCustomerDao.getAll()).thenReturn(Single.just(customerDbModels));
        when(mockDbListToModelConverter.apply(customerDbModels)).thenReturn(customerModels);

        //When
        List<CustomerModel> customerModels = repository.getCustomers().blockingGet();

        //Then
        verify(mockCustomerService).getCustomers();
        verify(mockApiListToDbConverter).apply(customerApiModels);
        verify(mockCustomerDao).insertAll(customerDbModels);
        verify(mockCustomerDao).getAll();
        verify(mockDbListToModelConverter).apply(customerDbModels);
        assertEquals(this.customerModels, customerModels);
    }

    @Test
    public void getCustomerDetailsFetchesCustomerFromDbWhenDbReturnsOneWithNonNullFirstName() {
        //Given
        when(customerDbModel.getFirstName()).thenReturn("first_name");
        when(mockCustomerDao.getById(id)).thenReturn(Single.just(customerDbModel));
        when(mockDbToModelConverter.apply(customerDbModel)).thenReturn(customerModel);

        //When
        CustomerModel customerModel = repository.getCustomerDetails(id).blockingGet();

        //Then
        verify(mockCustomerDao).getById(id);
        assertEquals(this.customerModel, customerModel);
        verifyZeroInteractions(mockCustomerService);
    }

    @Test
    public void getCustomerDetailsFetchesCustomerFromServiceWhenDbReturnsOneWithNullFirstName() {
        //Given
        when(customerDbModel.getFirstName()).thenReturn(null);
        when(mockCustomerDao.getById(id)).thenReturn(Single.just(customerDbModel));
        when(mockCustomerService.getCustomerDetailsById(id)).thenReturn(Single.just(customerApiModel));
        when(mockApiToDbConverter.apply(customerApiModel)).thenReturn(customerDbModel);
        when(mockCustomerDao.update(customerDbModel)).thenReturn(Single.just(0));
        when(mockCustomerDao.getById(id)).thenReturn(Single.just(customerDbModel));
        when(mockDbToModelConverter.apply(customerDbModel)).thenReturn(customerModel);

        //When
        CustomerModel customerModel = repository.getCustomerDetails(id).blockingGet();

        //Then
        verify(mockCustomerDao, times(2)).getById(id);
        verify(mockCustomerService).getCustomerDetailsById(id);
        verify(customerApiModel).setId(id);
        verify(mockApiToDbConverter).apply(customerApiModel);
        verify(mockCustomerDao).update(customerDbModel);
        verify(mockDbToModelConverter).apply(customerDbModel);
        assertEquals(this.customerModel, customerModel);
    }

}
