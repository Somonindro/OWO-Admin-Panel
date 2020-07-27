package com.example.owoshop;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.xyz>{

    private List<Pair<String,Integer>> segment1,segment2 = new ArrayList<Pair<String, Integer>>();
    private Context context;

    public HomeAdapter(List<Pair<String,Integer>> segment1,Context context) {
        this.segment1 = segment1;
        this.context = context;
        segment2.addAll(segment1);
    }

    @NonNull
    @Override
    public xyz onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.home_sample,viewGroup,false);
        return new  xyz(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final xyz holder, final int position) {

        holder.imageView.setImageResource(segment1.get(position).second);
        holder.textView.setText(segment1.get(position).first);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, segment1.get(position).first+position, Toast.LENGTH_SHORT).show();
                if (position==0)
                {
                    Intent intent=new Intent(holder.itemView.getContext(), CreateNewAdminActivity.class);
                    holder.itemView.getContext().startActivity(intent);
                }
                else if (position==1)
                {
                    Intent intent=new Intent(holder.itemView.getContext(), SemiAdminActivity.class);
                    holder.itemView.getContext().startActivity(intent);
                }
                else if (position==2)
                {
                    Intent intent=new Intent(holder.itemView.getContext(), ShopApprovalActivity.class);
                    holder.itemView.getContext().startActivity(intent);
                }
                else if (position==3)
                {
                    CharSequence options[]=new CharSequence[]{"Management","Orders","Messages"};
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setTitle("SHOPS");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            if (i==0)
                            {
                                Intent intent=new Intent(holder.itemView.getContext(), ManagementActivity.class);
                                holder.itemView.getContext().startActivity(intent);

                            }
                            else if(i==1)
                            {

                            }
                            else if(i==2)
                            {

                            }
                        }
                    });
                    builder.show();

                }
                else if (position==4)
                {
                    CharSequence options[]=new CharSequence[]{"Add Products","Product Availability","Update Products"};
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setTitle("PRODUCTS");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            if (i==0)
                            {
                                Intent intent=new Intent(holder.itemView.getContext(), AddProductActivity.class);
                                holder.itemView.getContext().startActivity(intent);
                            }
                            else if(i==1)
                            {

                            }
                            else if(i==2)
                            {

                            }
                        }
                    });
                    builder.show();

                }
                else if (position==5)
                {
                    Intent intent=new Intent(holder.itemView.getContext(), OffersActivity.class);
                    holder.itemView.getContext().startActivity(intent);
                }
                else if (position==6)
                {

                }
                else if (position==7)
                {

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return segment1.size();
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
