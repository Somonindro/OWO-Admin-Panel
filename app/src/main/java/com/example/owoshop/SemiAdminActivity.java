package com.example.owoshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.model.semi_admins;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SemiAdminActivity extends AppCompatActivity {

    private RecyclerView adminsList;
    private DatabaseReference adminsRef;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semi_admin);

        getSupportActionBar().hide();

        shimmerFrameLayout = findViewById(R.id.shimmer_animation);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();

        adminsRef= FirebaseDatabase.getInstance().getReference().child("Semi Admins");
        adminsList = findViewById(R.id.semi_admin_recyclerviewid);
        adminsList.setHasFixedSize(true);
        adminsList.setLayoutManager(new LinearLayoutManager(this));

        adminsList.setVisibility(View.GONE);

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<semi_admins> options=
                new FirebaseRecyclerOptions.Builder<semi_admins>()
                        .setQuery(adminsRef,semi_admins.class).build();

        FirebaseRecyclerAdapter<semi_admins,SemiAdminViewHolder> adapter=
                new FirebaseRecyclerAdapter<semi_admins, SemiAdminViewHolder>(options) {

                    @Override
                    public void onDataChanged() {
                        super.onDataChanged();
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull final SemiAdminViewHolder holder, int position, @NonNull semi_admins model) {

                        holder.Phone.setText(model.getPhone());
                        if (!model.getApprove_shop()) holder.Approve_shop.setText("No");
                        if (!model.getMaintain_shops()) holder.Maintain_shops.setText("No");
                        if (!model.getAdd_products()) holder.Add_products.setText("No");
                        if (!model.getUpdate_products()) holder.Update_products.setText("No");
                        if (!model.getCreate_offers()) holder.Create_offers.setText("No");
                        if (!model.getMaintain_users()) holder.Maintain_users.setText("No");
                        if (!model.getMessaging()) holder.Messaging.setText("No");

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(holder.itemView.getContext(), UpdateSemiAdminActivity.class);
                                holder.itemView.getContext().startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public SemiAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.semiadmin_sample,parent,false);
                        return new SemiAdminViewHolder(view);
                    }
                };


        adminsList.setVisibility(View.VISIBLE);
        adminsList.setAdapter(adapter);
        adapter.startListening();

    }

    public static class SemiAdminViewHolder extends RecyclerView.ViewHolder{

        public TextView Phone,Approve_shop, Maintain_shops,
                Add_products, Update_products, Create_offers, Maintain_users, Messaging;
        public SemiAdminViewHolder(@NonNull View itemView) {
            super(itemView);

            Phone=itemView.findViewById(R.id.semi_admin_phone);
            Approve_shop=itemView.findViewById(R.id.approve_shop_switch);
            Maintain_shops=itemView.findViewById(R.id.maintain_shop_switch);
            Add_products=itemView.findViewById(R.id.add_product_switch);
            Update_products=itemView.findViewById(R.id.update_products_switch);
            Create_offers=itemView.findViewById(R.id.create_offers_switch);
            Maintain_users=itemView.findViewById(R.id.maintain_users_switch);
            Messaging=itemView.findViewById(R.id.messaging_switch);

        }
    }

}
