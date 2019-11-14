package com.cba.provident.ui;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

import com.cba.provident.repository.CustomerRepository;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class CustomerPresenter implements LifecycleObserver {

    private CustomerView view;
    private CustomerRepository repository;
    private CustomerListModelToCustomerUIModelConverter converter;
    private CustomerModelToCustomerDetailsUIModelConverter detailsConverter;
    private Scheduler io;
    private Scheduler mainThread;

    private final MutableLiveData<List<CustomerUIModel>> dataStream = new MutableLiveData<>();
    private final MutableLiveData<String> errorStream = new MutableLiveData<>();
    private final MutableLiveData<CustomerDetailsUIModel> customerSelectedStream = new MutableLiveData<>();

    private Disposable disposable;

    LiveData<List<CustomerUIModel>> getDataStream() {
        return dataStream;
    }

    LiveData<String> getErrorStream() {
        return errorStream;
    }

    LiveData<CustomerDetailsUIModel> getCustomerSelectedStream() {
        return customerSelectedStream;
    }

    void fetchData() {
        view.showProgress();
        if (disposable != null) disposable.dispose();
        disposable = repository.getCustomers()
                .subscribeOn(io)
                .observeOn(mainThread)
                .map(converter)
                .subscribe((customerUIModels, throwable) -> {
                    view.hideProgress();
                    if (customerUIModels != null) dataStream.postValue(customerUIModels);
                    if (throwable != null) errorStream.postValue(throwable.getMessage());
                });

    }

    void customerSelected(int customerId) {
        view.showProgress();
        if (disposable != null) disposable.dispose();
        disposable = repository.getCustomerDetails(customerId)
                .subscribeOn(io)
                .observeOn(mainThread)
                .map(detailsConverter)
                .subscribe((customerUIModel, throwable) -> {
                    view.hideProgress();
                    if (customerUIModel != null) customerSelectedStream.postValue(customerUIModel);
                    if (throwable != null) errorStream.postValue(throwable.getMessage());
                });
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume() {
        fetchData();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause() {
        disposable.dispose();
    }
}
