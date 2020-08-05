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
import android.widget.ImageView;
import android.widget.TextView;

import com.OwoDokan.model.Offers;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class AvilableOffersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference OffersRef;
    private ImageView back_to_home;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avilable_offers);


        shimmerFrameLayout = findViewById(R.id.shimmer_avilable_offer);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();

        OffersRef = FirebaseDatabase.getInstance().getReference().child("Offers");

        back_to_home = findViewById(R.id.back_to_home);


        back_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AvilableOffersActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });


        recyclerView=findViewById(R.id.offer_availability_recyclerview_id);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Offers> options = new FirebaseRecyclerOptions.Builder<Offers>()
                .setQuery(OffersRef, Offers.class).build();

        FirebaseRecyclerAdapter<Offers, AvilableOffersActivity.OfferViewHolder> adapter=
                new FirebaseRecyclerAdapter<Offers, OfferViewHolder>(options) {

                    @Override
                    public void onDataChanged() {
                        super.onDataChanged();
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull final OfferViewHolder holder, int position, @NonNull final Offers model) {

                        holder.txtOfferName.setText(model.getName());
                        holder.txtOfferStartDate.setText("Start Date: "+model.getStartdate());
                        holder.txtOfferEndDate.setText("End Date: "+model.getEnddate());

                        Picasso.get().load(model.getImage()).into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options[]=new CharSequence[]{"Yes","No"};
                                AlertDialog.Builder builder=new AlertDialog.Builder(holder.itemView.getContext());
                                builder.setTitle("Update offer?");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        if (i==0)
                                        {
                                            Intent intent=new Intent(holder.itemView.getContext(), UpdateOfferActivity.class);
                                            intent.putExtra("Offers",model);
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
                        });
                    }

                    @NonNull
                    @Override
                    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_availability_sample,parent,false);
                        return new OfferViewHolder(view);
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static class OfferViewHolder extends RecyclerView.ViewHolder{

        public TextView txtOfferName,txtOfferStartDate,txtOfferEndDate;
        public ImageView imageView;

        public OfferViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.offer_image_id);
            txtOfferName = (TextView)itemView.findViewById(R.id.offer_name_id);
            txtOfferStartDate = (TextView)itemView.findViewById(R.id.start_date);
            txtOfferEndDate = (TextView)itemView.findViewById(R.id.end_date);
        }
    }
}
