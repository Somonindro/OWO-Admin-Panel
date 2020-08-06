package com.OwoDokan.owoshop;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AddProductAdapter extends RecyclerView.Adapter<AddProductAdapter.xyz>{
    private List<Pair<String,Integer>> product1,product2 = new ArrayList<Pair<String, Integer>>();
    private Context context;

    public AddProductAdapter(List<Pair<String,Integer>> product1,Context context) {
        this.product1 = product1;
        this.context = context;
        product2.addAll(product1);
    }

    @NonNull
    @Override
    public xyz onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.add_product_sample,viewGroup,false);
        return new  xyz(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final xyz holder, final int position) {

        holder.imageView.setImageResource(product1.get(position).second);
        holder.textView.setText(product1.get(position).first);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, product1.get(position).first, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(holder.itemView.getContext(), SingleProductAddActivity.class);
                intent.putExtra("category",product1.get(position).first);
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return product1.size();
    }

    public void filter(String text) {

        product1.clear();

        if(text.isEmpty()){
            product1.addAll(product2);
        }

        else{
            text = text.toLowerCase();

            for(Pair<String,Integer> item: product2){
                if(item.first.toLowerCase().contains(text)){
                    product1.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class xyz extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public xyz(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageviewid);
            textView=itemView.findViewById(R.id.textviewid);
        }

    }
}
