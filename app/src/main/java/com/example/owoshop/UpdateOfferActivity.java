package com.example.owoshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.model.Offers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UpdateOfferActivity extends AppCompatActivity {

    private EditText nameUpdate,startDateUpdate,endDateUpdate;
    private Button updateButton, deleteButton;
    private ImageView imageUpdate, start_date_update_picker, end_date_update_picker, back_from_home;
    private ProgressDialog loadingbar;
    private int state = 0;
    private final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_offer);

        nameUpdate = (EditText)findViewById(R.id.offer_name_update);
        startDateUpdate  =(EditText)findViewById(R.id.start_date_update);
        endDateUpdate = (EditText)findViewById(R.id.end_date_update);
        start_date_update_picker = findViewById(R.id.start_date_update_picker);
        end_date_update_picker = findViewById(R.id.end_date_update_picker);

        updateButton = (Button)findViewById(R.id.update_offers_button);
        imageUpdate = (ImageView)findViewById(R.id.offer_image_update);
        deleteButton = findViewById(R.id.delete_offers_button);
        loadingbar = new ProgressDialog(this);
        back_from_home = findViewById(R.id.back_to_home);


        back_from_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }

        };


        final com.example.model.Offers offers = (Offers) getIntent().getSerializableExtra("Offers");

        Picasso.get().load(offers.getImage()).into(imageUpdate);
        nameUpdate.setText(offers.getName());
        startDateUpdate.setText(offers.getStartdate());
        endDateUpdate.setText(offers.getEnddate());

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Offers");


        start_date_update_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                state = 1;

                new DatePickerDialog(UpdateOfferActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });


        end_date_update_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                state = 2;

                new DatePickerDialog(UpdateOfferActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingbar.setTitle("Update Offer");
                loadingbar.setMessage("Please wait while we are updating the offer...");
                loadingbar.setCanceledOnTouchOutside(false);
                loadingbar.show();

                offers.setName(nameUpdate.getText().toString());
                offers.setStartdate(startDateUpdate.getText().toString());
                offers.setEnddate(endDateUpdate.getText().toString());

                reference.child(offers.getOfferid()).setValue(offers).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            Toast.makeText(UpdateOfferActivity.this, "Offer updated successfully.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UpdateOfferActivity.this, AvilableOffersActivity.class);
                            loadingbar.dismiss();
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }

                        else
                        {
                            Toast.makeText(UpdateOfferActivity.this, "Can not update offer's data"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                        }
                    }
                });

            }
        });



        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                loadingbar.setTitle("Delete Offer");
                loadingbar.setMessage("Please wait while we deleting the offer...");
                loadingbar.setCanceledOnTouchOutside(false);
                loadingbar.show();

                reference.child(offers.getOfferid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            Toast.makeText(UpdateOfferActivity.this, "Offer removed successfully", Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();

                            Intent intent = new Intent(UpdateOfferActivity.this, AvilableOffersActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }

                        else
                        {
                            Toast.makeText(UpdateOfferActivity.this, "Can not delete offer", Toast.LENGTH_SHORT).show();
                            Toast.makeText(UpdateOfferActivity.this, "Error: "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                        }
                    }
                });
            }
        });

    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy";

        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if(state == 1)
            startDateUpdate.setText(sdf.format(myCalendar.getTime()));
        else if(state == 2)
            endDateUpdate.setText(sdf.format(myCalendar.getTime()));
    }



}
