package com.example.owoshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class OffersActivity extends AppCompatActivity {

    private ImageView createOfferImage;
    private EditText offerName,offerStartDate,offerEndDate;
    private Button createOfferBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        createOfferImage=(ImageView)findViewById(R.id.create_offers_image);
        offerName=(EditText)findViewById(R.id.offer_name);
        offerStartDate=(EditText)findViewById(R.id.offer_start_date);
        offerEndDate=(EditText)findViewById(R.id.offer_end_date);
        createOfferBtn=(Button)findViewById(R.id.create_offer_btn);

        createOfferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OffersActivity.this, "Offer created successfully.", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(OffersActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
