package com.cba.provident.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cba.provident.R;

public class CustomerDetailsActivity extends AppCompatActivity {

    private static final String MODEL_KEY = "MODEL_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);
        CustomerDetailsUIModel model = (CustomerDetailsUIModel) getIntent().getSerializableExtra(MODEL_KEY);

        ((TextView) findViewById(R.id.boom)).setText(String.valueOf(model.getId()));
        ((TextView) findViewById(R.id.display_name)).setText(model.getDisplayName());
        ((TextView) findViewById(R.id.first_name)).setText(model.getFirstName());
        ((TextView) findViewById(R.id.last_name)).setText(model.getLastName());
        ((TextView) findViewById(R.id.status)).setText(model.getStatus());
        ((TextView) findViewById(R.id.new_issue_allowed)).setText(model.getNewIssueAllowed().toString());
        ((TextView) findViewById(R.id.latitude)).setText(String.valueOf(model.getLatitude()));
        ((TextView) findViewById(R.id.longitude)).setText(String.valueOf(model.getLongitude()));
        ((TextView) findViewById(R.id.preferred_collection_day)).setText(model.getPreferredCollectionDay());
        ((TextView) findViewById(R.id.preferred_collection_time)).setText(model.getPreferredCollectionTime().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_customers, menu);
        return true;
    }

    public static void show(Context context, CustomerDetailsUIModel model) {
        Intent intent = new Intent(context, CustomerDetailsActivity.class);
        intent.putExtra(MODEL_KEY, model);
        context.startActivity(intent);
    }
}