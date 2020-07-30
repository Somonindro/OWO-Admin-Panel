package com.example.owoshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class UpdateSemiAdminActivity extends AppCompatActivity {

    private TextView clickedAdminName,clickedAdminPin;
    private Switch approveShop,maintainShop,addProducts,updateProducts,createOffers,maintainUsers,messaging;
    private Button updateAdminBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_semi_admin);


        approveShop=(Switch)findViewById(R.id.clicked_approve_shop);
        maintainShop=(Switch)findViewById(R.id.clicked_maintain_shop);
        addProducts=(Switch)findViewById(R.id.clicked_add_products);
        updateProducts=(Switch)findViewById(R.id.clicked_update_products);
        createOffers=(Switch)findViewById(R.id.clicked_create_offers);
        maintainUsers=(Switch)findViewById(R.id.clicked_maintain_users);
        messaging=(Switch)findViewById(R.id.clicked_messaging);
        clickedAdminName=(TextView)findViewById(R.id.clicked_admin_name);
        clickedAdminPin=(TextView)findViewById(R.id.clicked_admin_pin);
        updateAdminBtn=(Button)findViewById(R.id.update_admin_btn);

        updateAdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdateSemiAdminActivity.this, "Semi-admin updated successfully.", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(UpdateSemiAdminActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

    }
}
