package com.example.owoshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class OffersActivity extends AppCompatActivity {

    private String StartDate,EndDate,OfferName,saveCurrentDate,saveCurrentTime,OfferRandomKey,downloadImageUrl;

    private ImageView createOfferImage, offer_start_date_picker, offer_end_date_picker;
    private EditText offerName,offerStartDate,offerEndDate;
    private Button createOfferBtn;

    private static final int GalleryPick=1;
    private Uri ImageUri;
    private StorageReference OfferImagesRef;
    private DatabaseReference OffersRef;
    private ProgressDialog loadingbar;
    private int state = 0;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        OfferImagesRef= FirebaseStorage.getInstance().getReference().child("OfferImage");
        OffersRef= FirebaseDatabase.getInstance().getReference().child("Offers");

        createOfferImage=(ImageView)findViewById(R.id.create_offers_image);
        offerName=(EditText)findViewById(R.id.offer_name);
        offerStartDate=(EditText)findViewById(R.id.offer_start_date);
        offerEndDate=(EditText)findViewById(R.id.offer_end_date);
        createOfferBtn=(Button)findViewById(R.id.create_offer_btn);
        offer_start_date_picker = findViewById(R.id.start_date_picker);
        offer_end_date_picker = findViewById(R.id.end_date_picker);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        offer_start_date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(OffersActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                state = 1;

            }
        });


        offer_end_date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(OffersActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                state = 2;

            }
        });

        loadingbar=new ProgressDialog(this);

        createOfferImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });

        createOfferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateOfferData();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if(state == 1)
            offerStartDate.setText(sdf.format(myCalendar.getTime()));
        else if(state == 2)
            offerEndDate.setText(sdf.format(myCalendar.getTime()));
    }




    private void OpenGallery() {
        Intent galleryIntent=new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GalleryPick && resultCode==RESULT_OK && data!=null)
        {
            ImageUri=data.getData();
            createOfferImage.setImageURI(ImageUri);
        }
    }

    private void ValidateOfferData() {

        OfferName=offerName.getText().toString();
        StartDate=offerStartDate.getText().toString();
        EndDate=offerEndDate.getText().toString();

        if(ImageUri==null)
        {
            Toast.makeText(this, "Offer image is mandatory...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(OfferName))
        {
            Toast.makeText(this, "Please write offer name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(StartDate))
        {
            Toast.makeText(this, "Please write offer start date...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(EndDate))
        {
            Toast.makeText(this, "Please write offer end date...", Toast.LENGTH_SHORT).show();
        }
        else {
            StoreOfferInformation();
        }
    }

    private void StoreOfferInformation() {

        loadingbar.setTitle("Add New Offer");
        loadingbar.setMessage("Please wait, we are adding the new offer.");
        loadingbar.setCanceledOnTouchOutside(false);
        loadingbar.show();
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        OfferRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath=OfferImagesRef.child(ImageUri.getLastPathSegment() + OfferRandomKey +".jpg");

        final UploadTask uploadTask=filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message=e.toString();
                Toast.makeText(OffersActivity.this, "Error : "+message, Toast.LENGTH_SHORT).show();
                loadingbar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(OffersActivity.this, "Offer Image uploaded successfully", Toast.LENGTH_SHORT).show();
                Task<Uri> urlTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful())
                        {
                            throw  task.getException();
                        }
                        downloadImageUrl=filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful())
                        {
                            downloadImageUrl=task.getResult().toString();
                            Toast.makeText(OffersActivity.this, "Got the Offer image url Successfully", Toast.LENGTH_SHORT).show();
                            SaveOfferInfoToDatabase();
                        }
                    }
                });
            }
        });
    }

    private void SaveOfferInfoToDatabase() {
        HashMap<String,Object> offerMap=new HashMap<>();
        offerMap.put("offerid",OfferRandomKey);
        offerMap.put("date",saveCurrentDate);
        offerMap.put("time",saveCurrentTime);
        offerMap.put("name",OfferName);
        offerMap.put("image",downloadImageUrl);
        offerMap.put("startdate",StartDate);
        offerMap.put("enddate",EndDate);

        OffersRef.child(OfferRandomKey).updateChildren(offerMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Intent intent=new Intent(OffersActivity.this, HomeActivity.class);
                            startActivity(intent);

                            loadingbar.dismiss();
                            Toast.makeText(OffersActivity.this, "Offer is added successfully...", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            loadingbar.dismiss();
                            String message=task.getException().toString();
                            Toast.makeText(OffersActivity.this, "Error : "+message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
