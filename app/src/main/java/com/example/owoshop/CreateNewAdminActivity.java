package com.example.owoshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class CreateNewAdminActivity extends AppCompatActivity {

    private Switch approveShop,maintainShop,addProducts,updateProducts,createOffers,maintainUsers,messaging;
    private EditText newAdminName,newAdminPhone,newAdminPin;
    private Button createNewAdminBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_admin);

        getSupportActionBar().hide();

        approveShop=(Switch)findViewById(R.id.approve_shop);
        maintainShop=(Switch)findViewById(R.id.maintain_shop);
        addProducts=(Switch)findViewById(R.id.add_products);
        updateProducts=(Switch)findViewById(R.id.update_products);
        createOffers=(Switch)findViewById(R.id.create_offers);
        maintainUsers=(Switch)findViewById(R.id.maintain_users);
        messaging=(Switch)findViewById(R.id.messaging);
        newAdminName=(EditText)findViewById(R.id.new_admin_name);
        newAdminPhone=(EditText)findViewById(R.id.new_admin_phone);
        newAdminPin=(EditText)findViewById(R.id.new_admin_pin);
        createNewAdminBtn=(Button)findViewById(R.id.create_new_admin_btn);

        createNewAdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CreateNewAdminActivity.this, "Semi-admin created successfully.", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(CreateNewAdminActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

    }
}
