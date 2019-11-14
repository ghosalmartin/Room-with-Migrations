package com.cba.provident.ui;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.cba.provident.repository.CustomerRepository;
import com.cba.provident.repository.model.CustomerModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerPresenterTest {


    @Rule
    public InstantTaskExecutorRule taskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private CustomerView mockView;

    @Mock
    private CustomerRepository mockRepository;

    @Mock
    private CustomerListModelToCustomerUIModelConverter mockListConverter;

    @Mock
    private CustomerModelToCustomerDetailsUIModelConverter mockDetailsConverter;

    @Mock
    private Disposable disposable;

    @Mock
    private List<CustomerUIModel> customerUIModels;

    @Mock
    private CustomerModel customerModel;

    @Mock
    private List<CustomerModel> customerModels;

    @Mock
    private CustomerDetailsUIModel customerDetailsUIModel;

    private CustomerPresenter presenter;

    private String errorMessage = "error";

    private int id = 10;

    @Before
    public void setup() {
        presenter = new CustomerPresenter(
                mockView,
                mockRepository,
                mockListConverter,
                mockDetailsConverter,
                Schedulers.trampoline(),
                Schedulers.trampoline(),
                disposable
        );
    }

    @Test
    public void fetchDataCalledProgressIsHandledAndCustomersAreReturned() {
        //Given
        when(mockRepository.getCustomers()).thenReturn(Single.just(customerModels));
        when(mockListConverter.apply(customerModels)).thenReturn(customerUIModels);

        //When
        presenter.fetchData();

        //Then
        verify(mockView).showProgress();
        verify(mockRepository).getCustomers();
        verify(mockView).hideProgress();
        assertEquals(customerUIModels, presenter.getDataStream().getValue());
        assertNull(presenter.getErrorStream().getValue());
    }

    @Test
    public void fetchDataCalledProgressIsHandledPostsToErrorStreamWhenError() {
        //Given
        when(mockRepository.getCustomers()).thenReturn(Single.error(new IllegalArgumentException(errorMessage)));

        //When
        presenter.fetchData();

        //Then
        verify(mockView).showProgress();
        verify(mockRepository).getCustomers();
        verify(mockView).hideProgress();
        assertEquals(errorMessage, presenter.getErrorStream().getValue());
        assertNull(presenter.getDataStream().getValue());
    }

    @Test
    public void customerSelectedGetCustomerDetailsAndPostsToCustomerSelectedStream() {
        //Given
        when(mockRepository.getCustomerDetails(id)).thenReturn(Single.just(customerModel));
        when(mockDetailsConverter.apply(customerModel)).thenReturn(customerDetailsUIModel);

        //When
        presenter.customerSelected(id);

        //Then
        verify(mockView).showProgress();
        verify(mockRepository).getCustomerDetails(id);
        verify(mockView).hideProgress();
        assertEquals(customerDetailsUIModel, presenter.getCustomerSelectedStream().getValue());
        assertNull(presenter.getErrorStream().getValue());
    }

    @Test
    public void customerSelectedGetCustomerDetailsErrorsPostsToErrorStream() {
        //Given
        when(mockRepository.getCustomerDetails(id)).thenReturn(Single.error(new IllegalArgumentException(errorMessage)));

        //When
        presenter.customerSelected(id);

        //Then
        verify(mockView).showProgress();
        verify(mockRepository).getCustomerDetails(id);
        verify(mockView).hideProgress();
        assertEquals(errorMessage, presenter.getErrorStream().getValue());
        assertNull(presenter.getCustomerSelectedStream().getValue());
    }

    @Test
    public void onResumeFetchesData() {
        //Given
        when(mockRepository.getCustomers()).thenReturn(Single.just(customerModels));
        when(mockListConverter.apply(customerModels)).thenReturn(customerUIModels);

        //When
        presenter.onResume();

        //Then
        verify(mockView).showProgress();
        verify(mockRepository).getCustomers();
        verify(mockView).hideProgress();
        assertEquals(customerUIModels, presenter.getDataStream().getValue());
        assertNull(presenter.getErrorStream().getValue());
    }

    @Test
    public void onPauseDisposesDisposable() {
        //When
        presenter.onPause();

        //Then
        verify(disposable).dispose();
    }

}