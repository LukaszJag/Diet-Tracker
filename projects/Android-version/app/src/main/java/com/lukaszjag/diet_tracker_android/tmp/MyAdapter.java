package com.lukaszjag.diet_tracker_android.tmp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lukaszjag.diet_tracker_android.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<MyItem> itemList;

    public MyAdapter() {
        this.itemList = new ArrayList<>();
    }

    // --- CUSTOM METHODS TO MANAGE DATA ---

    // ADD an object
    public void addItem(MyItem item) {
        itemList.add(item);
        // Notify the adapter that an item was inserted at the very end
        notifyItemInserted(itemList.size() - 1);
    }

    // GET an object
    public MyItem getItem(int position) {
        if (position >= 0 && position < itemList.size()) {
            return itemList.get(position);
        }
        return null;
    }

    // SET (Update) an object
    public void setItem(int position, MyItem newItem) {
        if (position >= 0 && position < itemList.size()) {
            itemList.set(position, newItem);
            // Notify the adapter that a specific item changed
            notifyItemChanged(position);
        }
    }

    // DELETE an object
    public void deleteItem(int position) {
        if (position >= 0 && position < itemList.size()) {
            itemList.remove(position);
            // Notify the adapter to animate removal and shift remaining items
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, itemList.size());
        }
    }

    // --- RECYCLERVIEW OVERRIDE METHODS ---

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyItem currentItem = itemList.get(position);

        holder.tv1.setText(currentItem.getString1());
        holder.tv2.setText(currentItem.getString2());
        holder.tv3.setText(currentItem.getString3());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // ViewHolder class maps the views inside item_layout.xml
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv1, tv2, tv3;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.textView1);
            tv2 = itemView.findViewById(R.id.textView2);
            tv3 = itemView.findViewById(R.id.textView3);
        }
    }
}