package com.OwoDokan.owoshop;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.xyz>{

    private Context context;

    public HomeAdapter(Context context) {
        this.context = context;
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

        holder.imageView.setImageResource(HomeActivity.icons[position]);
        holder.textView.setText(HomeActivity.segment[position]);

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
                    CharSequence options[]=new CharSequence[]{"Shop Management","Shop Orders","Shop Messages"};
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
                    CharSequence options[]=new CharSequence[]{"Add Products","Product Availability"};
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
                                Intent intent=new Intent(holder.itemView.getContext(), ProductAvailabilityActivity.class);
                                holder.itemView.getContext().startActivity(intent);
                            }
                        }
                    });
                    builder.show();

                }
                else if (position==5)
                {
                    CharSequence options[]=new CharSequence[]{"Add Offers","Offer Availability"};
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setTitle("OFFERS");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            if (i==0)
                            {
                                Intent intent=new Intent(holder.itemView.getContext(), CreateOffersActivity.class);
                                holder.itemView.getContext().startActivity(intent);
                            }
                            else if(i==1)
                            {
                                Intent intent=new Intent(holder.itemView.getContext(), AvilableOffersActivity.class);
                                holder.itemView.getContext().startActivity(intent);
                            }
                        }
                    });
                    builder.show();
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
        return HomeActivity.segment.length;
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

