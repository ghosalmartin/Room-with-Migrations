package com.cba.provident.ui;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

import com.cba.provident.repository.CustomerRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;

class CustomerPresenter implements LifecycleObserver {

    private CustomerView view;
    private CustomerRepository repository;
    private CustomerModelToCustomerUIModelConverter converter;
    private MutableLiveData<List<CustomerUIModel>> dataStream = new MutableLiveData<>();
    private MutableLiveData<String> errorStream = new MutableLiveData<>();

    private Disposable disposable;

    CustomerPresenter(
            CustomerView view,
            CustomerRepository repository,
            CustomerModelToCustomerUIModelConverter converter) {
        this.view = view;
        this.repository = repository;
        this.converter = converter;
    }

    LiveData<List<CustomerUIModel>> getDataStream() {
        return dataStream;
    }

    LiveData<String> getErrorStream() {
        return errorStream;
    }

    void fetchData() {
        view.showProgress();
        if (disposable != null) disposable.dispose();
        disposable = repository.getCustomers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(converter)
                .subscribe(new BiConsumer<List<CustomerUIModel>, Throwable>() {
                    @Override
                    public void accept(List<CustomerUIModel> customerUIModels, Throwable throwable) {
                        view.hideProgress();
                        if (customerUIModels != null) dataStream.postValue(customerUIModels);
                        if (throwable != null) errorStream.postValue(throwable.getMessage());
                    }
                });

    }

    void customerSelected(int customerId) {

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
