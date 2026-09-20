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

public class MyAdapterProduct extends RecyclerView.Adapter<MyAdapterProduct.MyViewHolder> {

    //<editor-fold desc="Global variables">
    private List<Product> productList;
    //</editor-fold>

    //<editor-fold desc="Constructors">
    public MyAdapterProduct() {
        this.productList = new ArrayList<>();
    }
    //</editor-fold>

    // --- CUSTOM METHODS TO MANAGE DATA ---

    //<editor-fold desc="ADD, GET, SET, DELETE - object">
    // ADD an object
    public void addItem(Product product) {
        productList.add(product);
        // Notify the adapter that an item was inserted at the very end
        notifyItemInserted(productList.size() - 1);
    }

    // GET an object
    public Product getItem(int position) {
        if (position >= 0 && position < productList.size()) {
            return productList.get(position);
        }
        return null;
    }

    // SET (Update) an object
    public void setItem(int position, Product product) {
        if (position >= 0 && position < productList.size()) {
            productList.set(position, product);
            // Notify the adapter that a specific item changed
            notifyItemChanged(position);
        }
    }

    // DELETE an object
    public void deleteItem(int position) {
        if (position >= 0 && position < productList.size()) {
            productList.remove(position);
            // Notify the adapter to animate removal and shift remaining items
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, productList.size());
        }
    }
    //</editor-fold>

    // --- RECYCLERVIEW OVERRIDE METHODS ---

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product currentItem = productList.get(position);

        holder.tv1.setText(String.valueOf(currentItem.getProductName()));
        holder.tv2.setText(String.valueOf(currentItem.getProductMeasureOfProductWeightToCalculateMacro()));
        holder.tv3.setText(String.valueOf(currentItem.getProductMacroForItsSetMeasure().getKcal()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // ViewHolder class maps the views inside item_layout.xml
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv1, tv2, tv3;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.productNameTextView);
            tv2 = itemView.findViewById(R.id.amountOfProductTextView);
            tv3 = itemView.findViewById(R.id.kcalConsumeTextView);
        }
    }
}