package com.example.owoshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.model.Offers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class UpdateOfferActivity extends AppCompatActivity {

    private EditText nameUpdate,startDateUpdate,endDateUpdate;
    private Button updateButton;
    private ImageView imageUpdate;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_offer);

        nameUpdate=(EditText)findViewById(R.id.offer_name_update);
        startDateUpdate=(EditText)findViewById(R.id.start_date_update);
        endDateUpdate=(EditText)findViewById(R.id.end_date_update);

        updateButton=(Button)findViewById(R.id.update_offers_button);
        imageUpdate=(ImageView)findViewById(R.id.offer_image_update);
        loadingbar=new ProgressDialog(this);

        final com.example.model.Offers offers = (Offers) getIntent().
                getSerializableExtra("Offers");

        Picasso.get().load(offers.getImage()).into(imageUpdate);
        nameUpdate.setText(offers.getName());
        startDateUpdate.setText(offers.getStartdate());
        endDateUpdate.setText(offers.getEnddate());

        imageUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdateOfferActivity.this, "Offer Image can not be changed.", Toast.LENGTH_SHORT).show();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingbar.setTitle("Update Offer");
                loadingbar.setMessage("Please wait, we are updating the particular offer.");
                loadingbar.setCanceledOnTouchOutside(false);
                loadingbar.show();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Offers");

                offers.setName(nameUpdate.getText().toString());
                offers.setStartdate(startDateUpdate.getText().toString());
                offers.setEnddate(endDateUpdate.getText().toString());

                reference.child(offers.getOfferid()).setValue(offers).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            Toast.makeText(UpdateOfferActivity.this, "Offer information updated successfully.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UpdateOfferActivity.this,HomeActivity.class);
                            loadingbar.dismiss();
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }

                        else
                        {
                            Toast.makeText(UpdateOfferActivity.this, "Can not update offer's data"+task.getException().toString(), Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                        }
                    }
                });

            }
        });

    }
}
