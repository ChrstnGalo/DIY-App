package com.example.diytag;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<DiscountProduct> productList;
    private Context context;
    private OnItemClickListener listener;

    public RecyclerViewAdapter(List<DiscountProduct> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DiscountProduct product = productList.get(position);

        holder.productName.setText(product.getDescription());
        String originalPrice = "₱" + product.getAmount(); // Assuming this is how you get the price
        SpannableString spannableString = new SpannableString(originalPrice);
        spannableString.setSpan(new StrikethroughSpan(), 0, originalPrice.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.productPrice.setText(spannableString);

        double discountedPrice = Double.parseDouble(product.getDiscounted_price()); // Assuming this is how you get the discounted price
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String formattedDiscountedPrice = decimalFormat.format(discountedPrice);

        // Set the formatted price to the TextView
        holder.productDiscount.setText("₱" + formattedDiscountedPrice);

        Picasso.get().load("https://diy-pos.000webhostapp.com/" + product.getImage())
                .into(holder.productImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(product);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productName, productPrice, productDiscount;
        ImageView productImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.productname);
            productPrice = itemView.findViewById(R.id.productprice);
            productImage = itemView.findViewById(R.id.promoView);
            productDiscount = itemView.findViewById(R.id.discountprice);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DiscountProduct product);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void filterList(List<DiscountProduct> filteredList) {
        productList = filteredList;
        notifyDataSetChanged();
    }
}
