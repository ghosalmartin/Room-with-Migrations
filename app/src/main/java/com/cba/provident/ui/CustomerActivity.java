package com.cba.provident.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.cba.provident.R;
import com.cba.provident.repository.CustomerRepository;
import com.cba.provident.repository.adapters.ApiListToDbConverter;
import com.cba.provident.repository.adapters.ApiToDbConverter;
import com.cba.provident.repository.adapters.DbListToModelConverter;
import com.cba.provident.repository.adapters.DbToModelConverter;
import com.cba.provident.repository.db.CustomerDatabase;
import com.cba.provident.repository.network.Network;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//Setting up di would of made this so much easier.
public class CustomerActivity extends AppCompatActivity implements CustomerView, CustomerClickListener {

    private CustomerPresenter viewModel;

    private CustomerAdapter customerAdapter = new CustomerAdapter(this);


    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomerDatabase customerDatabase = CustomerDatabase.getInstance(this);

        getLifecycle().addObserver(customerDatabase);

        DbToModelConverter dbToModelConverter = new DbToModelConverter();
        ApiToDbConverter apiToDbConverter = new ApiToDbConverter();

        CustomerRepository customerRepository = new CustomerRepository(
                Network.customerService,
                customerDatabase.getDao(),
                new DbListToModelConverter(dbToModelConverter),
                dbToModelConverter,
                new ApiListToDbConverter(apiToDbConverter),
                apiToDbConverter
        );

        setupDialog();

        RecyclerView recyclerView = findViewById(R.id.customer_recyclerview);
        recyclerView.setAdapter(customerAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        viewModel = new CustomerPresenter(
                this,
                customerRepository,
                new CustomerListModelToCustomerUIModelConverter(),
                new CustomerModelToCustomerDetailsUIModelConverter(),
                Schedulers.io(),
                AndroidSchedulers.mainThread(),
                null);

        getLifecycle().addObserver(viewModel);

        viewModel.getDataStream().observe(this, data -> customerAdapter.update(data));
        viewModel.getErrorStream().observe(this, error -> showError(error));
        viewModel.getCustomerSelectedStream().observe(this, customerDetailsUIModel -> CustomerDetailsActivity.show(this, customerDetailsUIModel));
    }

    private void setupDialog() {
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setMessage("Fetching Customers");
        dialog.setCancelable(false);
    }

    @Override
    public void showProgress() {
        dialog.show();
    }

    @Override
    public void hideProgress() {
        dialog.cancel();
    }

    @Override
    public void onPress(int customerId) {
        viewModel.customerSelected(customerId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_customers, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            viewModel.fetchData();
        } else {
            return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void showError(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Error");
        alertDialogBuilder.setMessage(message).setCancelable(false)
                .setPositiveButton("OK", (dialog, id) -> dialog.dismiss());

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}