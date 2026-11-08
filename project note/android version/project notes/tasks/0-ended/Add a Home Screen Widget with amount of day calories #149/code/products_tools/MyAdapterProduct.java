package com.lukaszjag.diet_tracker_android.tools.products_tools;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lukaszjag.diet_tracker_android.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyAdapterProduct extends RecyclerView.Adapter<MyAdapterProduct.MyViewHolder> {

    private List<Product> productList;
    private OnProductClickListener onProductClickListener;

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public MyAdapterProduct() {
        this.productList = new ArrayList<>();
    }

    public void setOnProductClickListener(OnProductClickListener listener) {
        this.onProductClickListener = listener;
    }

    public void addItem(Product product) {
        productList.add(product);
        notifyItemInserted(productList.size() - 1);
    }

    public Product getItem(int position) {
        if (position >= 0 && position < productList.size()) {
            return productList.get(position);
        }
        return null;
    }

    public void setItem(int position, Product product) {
        if (position >= 0 && position < productList.size()) {
            productList.set(position, product);
            notifyItemChanged(position);
        }
    }

    public void deleteItem(int position) {
        if (position >= 0 && position < productList.size()) {
            productList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, productList.size());
        }
    }

    public void clearItems() {
        int size = productList.size();
        productList.clear();
        notifyItemRangeRemoved(0, size);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product currentItem = productList.get(position);

        holder.tv1.setText(currentItem.getProductName());

        // Format product amount to 2 decimal places
        holder.tv2.setText(String.format(Locale.getDefault(), "%.2f g", currentItem.getProductMeasureOfProductWeightToCalculateMacro()));

        // Format consumed kcal to 2 decimal places
        holder.tv3.setText(String.format(Locale.getDefault(), "Kcal: %.2f", currentItem.getConsumedKcal()));

        if (holder.mealNameTv != null) {
            holder.mealNameTv.setText(currentItem.getMealName() != null ? currentItem.getMealName() : "N/A");
        }

        // Format consumed protein, fat, and carbs to 2 decimal places
        if (holder.proteinTv != null) {
            holder.proteinTv.setText(String.format(Locale.getDefault(), "P: %.2fg", currentItem.getConsumedProtein()));
        }
        if (holder.fatTv != null) {
            holder.fatTv.setText(String.format(Locale.getDefault(), "F: %.2fg", currentItem.getConsumedFat()));
        }
        if (holder.carbsTv != null) {
            holder.carbsTv.setText(String.format(Locale.getDefault(), "C: %.2fg", currentItem.getConsumedCarbs()));
        }

        holder.itemView.setOnClickListener(v -> {
            if (onProductClickListener != null) {
                onProductClickListener.onProductClick(currentItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv1, tv2, tv3, mealNameTv, proteinTv, fatTv, carbsTv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.productNameTextView);
            tv2 = itemView.findViewById(R.id.amountOfProductTextView);
            tv3 = itemView.findViewById(R.id.kcalConsumeTextView);
            mealNameTv = itemView.findViewById(R.id.mealNameTextView);
            proteinTv = itemView.findViewById(R.id.proteinConsumeTextView);
            fatTv = itemView.findViewById(R.id.fatConsumeTextView);
            carbsTv = itemView.findViewById(R.id.carbsConsumeTextView);
        }
    }
}