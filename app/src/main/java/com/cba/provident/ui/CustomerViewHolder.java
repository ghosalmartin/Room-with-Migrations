package com.cba.provident.ui;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cba.provident.R;

class CustomerViewHolder extends RecyclerView.ViewHolder {

    private TextView nameTextView;

    CustomerViewHolder(@NonNull LinearLayout itemView) {
        super(itemView);

        this.nameTextView = itemView.findViewById(R.id.row_name);
    }

    void bind(final CustomerUIModel customerUIModel, final CustomerClickListener clickListener) {
        nameTextView.setText(customerUIModel.getName());
        itemView.setOnClickListener(view -> clickListener.onPress(customerUIModel.getId()));
    }
}