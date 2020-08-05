package com.OwoDokan.owoshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.OwoDokan.model.Products;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ProductAvailabilityActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference ProductsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_availability);

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        recyclerView=findViewById(R.id.product_availability_recyclerview_id);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //here will be code for firebase recycler adapter and it will use product_availability_sample.xml to inflate
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(ProductsRef, Products.class)
                .build();
        FirebaseRecyclerAdapter<Products,ProductViewHolder> adapter=
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final ProductViewHolder holder, int position, @NonNull final Products model) {
                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductPrice.setText("Price = "+ model.getPrice()+"Tk."+"(Discount "+model.getDiscount()+"Tk.)");
                        Picasso.get().load(model.getImage()).into(holder.imageView);
                        //here will be the code for the availability switch
                        holder.productAvailability.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (!isChecked)
                                    Toast.makeText(ProductAvailabilityActivity.this, "This product is now unavailable", Toast.LENGTH_SHORT).show();
                            }
                        });
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options[]=new CharSequence[]{"Yes","No"};
                                AlertDialog.Builder builder=new AlertDialog.Builder(holder.itemView.getContext());
                                builder.setTitle("Do you want to update this product?");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        if (i==0)
                                        {
                                            Intent intent=new Intent(holder.itemView.getContext(), UpdateProductActivity.class);
                                            intent.putExtra("Products",model);
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
                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_availability_sample,parent,false);
                        return new ProductViewHolder(view);
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{

        public TextView txtProductName,txtProductDescription,txtProductPrice;
        public ImageView imageView;
        public Switch productAvailability;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.product_image);
            txtProductName=(TextView)itemView.findViewById(R.id.product_name);
            txtProductDescription=(TextView)itemView.findViewById(R.id.product_description);
            txtProductPrice=(TextView)itemView.findViewById(R.id.product_price);
            productAvailability=(Switch)itemView.findViewById(R.id.product_availability_switch);
        }
    }
}
