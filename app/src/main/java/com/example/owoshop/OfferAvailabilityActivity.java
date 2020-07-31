package com.example.owoshop;

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

import com.example.model.Offers;
import com.example.model.Products;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class OfferAvailabilityActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference OffersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_availability);

        OffersRef = FirebaseDatabase.getInstance().getReference().child("Offers");

        recyclerView=findViewById(R.id.offer_availability_recyclerview_id);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Offers> options = new FirebaseRecyclerOptions.Builder<Offers>()
                .setQuery(OffersRef, Offers.class)
                .build();
        FirebaseRecyclerAdapter<Offers, OfferAvailabilityActivity.OfferViewHolder> adapter=
                new FirebaseRecyclerAdapter<Offers, OfferViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final OfferViewHolder holder, int position, @NonNull final Offers model) {
                        holder.txtOfferName.setText(model.getName());
                        holder.txtOfferStartDate.setText("Start Date: "+model.getStartdate());
                        holder.txtOfferEndDate.setText("End Date: "+model.getEnddate());
                        Picasso.get().load(model.getImage()).into(holder.imageView);
                        //here will be the code for the availability switch
                        holder.offerAvailability.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (!isChecked)
                                    Toast.makeText(OfferAvailabilityActivity.this, "This offer is now unavailable", Toast.LENGTH_SHORT).show();
                            }
                        });
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options[]=new CharSequence[]{"Yes","No"};
                                AlertDialog.Builder builder=new AlertDialog.Builder(holder.itemView.getContext());
                                builder.setTitle("Do you want to update this offer?");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        if (i==0)
                                        {
                                            Intent intent=new Intent(holder.itemView.getContext(), UpdateOfferActivity.class);
                                            intent.putExtra("Offers", (Serializable) model);
                                            holder.itemView.getContext().startActivity(intent);

                                        }
                                        else if(i==1)
                                        {
                                            Intent intent=new Intent(holder.itemView.getContext(), OfferAvailabilityActivity.class);
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
        public Switch offerAvailability;

        public OfferViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.offer_image_id);
            txtOfferName=(TextView)itemView.findViewById(R.id.offer_name_id);
            txtOfferStartDate=(TextView)itemView.findViewById(R.id.start_date);
            txtOfferEndDate=(TextView)itemView.findViewById(R.id.end_date);
            offerAvailability=(Switch)itemView.findViewById(R.id.offer_availability_switch);
        }
    }
}
