package com.cba.provident.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cba.provident.R;

import java.util.ArrayList;
import java.util.List;

class CustomerAdapter extends RecyclerView.Adapter<CustomerViewHolder> {

    private CustomerClickListener clickListener;

    CustomerAdapter(CustomerClickListener clickListener) {
        this.clickListener = clickListener;
    }

    private ArrayList<CustomerUIModel> data = new ArrayList<>();

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomerViewHolder((LinearLayout)LayoutInflater.from(parent.getContext()).inflate(
                R.layout.customer_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        holder.bind(data.get(position), clickListener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    void update(List<CustomerUIModel> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }
}
