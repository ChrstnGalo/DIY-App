package com.example.diytag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.ViewHolder> {
    private Context mContext;
    private List<Sales> mSalesList;
    private ApiService apiServiceReceiptAndDate;

    public TransactionListAdapter(Context context, List<Sales> salesList, ApiService apiService) {
        mContext = context;
        mSalesList = salesList;
        apiServiceReceiptAndDate = apiService;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTextView;
        public TextView descriptionTextView;
        public TextView QtyTextView;
        public TextView AmountTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            QtyTextView = itemView.findViewById(R.id.qtyTextView);
            AmountTextView = itemView.findViewById(R.id.amountTextView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.balance_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sales currentSale = mSalesList.get(position);
        holder.dateTextView.setText(currentSale.getDate());
        holder.descriptionTextView.setText(currentSale.getDescription());
        holder.QtyTextView.setText(currentSale.getQty());
        String amountWithPesoSign = "₱" + currentSale.getTotal();
        holder.AmountTextView.setText(amountWithPesoSign);
    }

    @Override
    public int getItemCount() {
        return mSalesList.size();
    }

    public double calculateTotalAmount() {
        double totalAmount = 0;
        for (Sales sale : mSalesList) {
            totalAmount += Double.parseDouble(sale.getTotal());
        }
        return totalAmount;
    }
}
