package com.example.diytag;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductViewAdapter extends RecyclerView.Adapter<ProductViewAdapter.ProductViewHolder> {

    private List<Product> productList;
    private Context context;
    private OnItemClickListener listener;

    public ProductViewAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_view, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.productDescription.setText(product.getDescription());
        SpannableString spannableString = new SpannableString("₱" + product.getAmount());
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.black)), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.productAmount.setText(spannableString);

        Picasso.get().load("https://diy-pos.000webhostapp.com/" + product.getImageUrl())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
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

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        public TextView productDescription;
        public TextView productAmount;
        public ImageView productImage;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productDescription = itemView.findViewById(R.id.prdctname);
            productAmount = itemView.findViewById(R.id.prdctprice);
            productImage = itemView.findViewById(R.id.prdctView);
        }
    }

    public void filterList(List<Product> filteredList) {
        productList = filteredList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
