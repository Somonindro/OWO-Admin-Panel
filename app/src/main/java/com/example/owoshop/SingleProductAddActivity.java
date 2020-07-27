package com.example.owoshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SingleProductAddActivity extends AppCompatActivity {

    private String CategoryName;
    private Button AddNewProductButton;
    private ImageView InputProductImage;
    private EditText InputProductName,InputProductDescription,InputProductPrice,InputProductDiscount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product_add);

        getSupportActionBar().hide();

        CategoryName=getIntent().getExtras().get("category").toString();
        AddNewProductButton=(Button)findViewById(R.id.add_new_product);
        InputProductImage=(ImageView) findViewById(R.id.select_product_image);
        InputProductName=(EditText)findViewById(R.id.product_name);
        InputProductDescription=(EditText)findViewById(R.id.product_description);
        InputProductDiscount=(EditText)findViewById(R.id.product_discount);
        InputProductPrice=(EditText)findViewById(R.id.product_price);

        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });
        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SingleProductAddActivity.this, "Product added successfully.", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(SingleProductAddActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

    }

    private void OpenGallery() {
        //here will be code for select image from gallery
    }
}
