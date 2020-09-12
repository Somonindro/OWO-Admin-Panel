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
import android.widget.Toast;

import com.OwoDokan.model.PendingShop;
import com.OwoDokan.viewHolder.PendingShopViewHolder;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ShopApprovalActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_approval);
        recyclerView=findViewById(R.id.shop_approval_recyclerviewid);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference pendingShopList = FirebaseDatabase.getInstance().getReference();

        Query query = pendingShopList.child("PendingShopRequest").orderByValue();

        FirebaseRecyclerOptions<PendingShop> options =
                new FirebaseRecyclerOptions.Builder<PendingShop>()
                        .setQuery(query, PendingShop.class).build();


        FirebaseRecyclerAdapter<PendingShop, PendingShopViewHolder> adapter
                = new FirebaseRecyclerAdapter<PendingShop, PendingShopViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull final PendingShopViewHolder holder, int position, @NonNull final PendingShop model) {

                Glide.with(ShopApprovalActivity.this).load(model.getShop_image_uri()).into(holder.shop_image);

                holder.shop_name.setText(model.getShop_owner_name());
                holder.mobile_number.setText(model.getShop_owner_mobile());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*
                        Intent intent = new Intent(ShopApprovalActivity.this, ProductDetailsActivity.class);//For giving product description to the user when clicks on a cart item
                        intent.putExtra("Products", model);
                        startActivity(intent);

                         */
                    }
                });
            }

            @NonNull
            @Override
            public PendingShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_pending_shop_item, parent, false);
                PendingShopViewHolder holder = new PendingShopViewHolder(view);
                return holder;
            }

            @Override
            public void onDataChanged() {
                /*
                if(getItemCount() == 0)
                {
                    empty_image.setVisibility(View.VISIBLE);
                    empty_text.setVisibility(View.VISIBLE);
                }

                 */
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


}
