package com.OwoDokan.pagination;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.OwoDokan.model.Products;
import com.OwoDokan.owoshop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends PagedListAdapter<Products, ItemAdapter.ItemViewHolder>{

    private Context mCtx;
    public static List<Products> productsList = new ArrayList<>();


    public ItemAdapter(Context mCtx) {
        super(DIFF_CALLBACK);
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.product_availability_sample, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        Products item = getItem(position);
        productsList.add(item);

        if (item != null) {

            Picasso.get()
                    .load(item.getProduct_image())
                    .into(holder.imageView);

            holder.txtProductName.setText(item.getProduct_name());
            holder.txtProductPrice.setText(item.getProduct_price());

        } else {
            Toast.makeText(mCtx, "Item is null", Toast.LENGTH_LONG).show();
        }

    }

    private static DiffUtil.ItemCallback<Products> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Products>() {
                @Override
                public boolean areItemsTheSame(Products oldItem, Products newItem) {
                    return oldItem.getProduct_id() == newItem.getProduct_id();
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(Products oldItem, Products newItem) {
                    return oldItem.equals(newItem);
                }
            };

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView txtProductName, txtProductPrice;
        public ImageView imageView;

        public ItemViewHolder(View itemView) {
            super(itemView);

            imageView=(ImageView)itemView.findViewById(R.id.product_image);
            txtProductName=(TextView)itemView.findViewById(R.id.product_name);
            txtProductPrice=(TextView)itemView.findViewById(R.id.product_price);
        }

    }
    
}