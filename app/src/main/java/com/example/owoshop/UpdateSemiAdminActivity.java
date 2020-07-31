package com.example.owoshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.semi_admins;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class UpdateSemiAdminActivity extends AppCompatActivity {

    private TextView clickedAdminName, clickedAdminMobile;
    private ImageView admin_profile_pic;
    private Switch approveShop,maintainShop,addProducts,updateProducts,createOffers,maintainUsers,messaging;
    private Button updateAdminBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_semi_admin);

        admin_profile_pic = findViewById(R.id.clicked_admin_profileImage);

        clickedAdminName = (TextView)findViewById(R.id.clicked_admin_name);
        clickedAdminMobile = findViewById(R.id.clicked_admin_mobile);

        approveShop = (Switch)findViewById(R.id.clicked_approve_shop);
        maintainShop = (Switch)findViewById(R.id.clicked_maintain_shop);
        addProducts = (Switch)findViewById(R.id.clicked_add_products);
        updateProducts = (Switch)findViewById(R.id.clicked_update_products);
        createOffers = (Switch)findViewById(R.id.clicked_create_offers);
        maintainUsers = (Switch)findViewById(R.id.clicked_maintain_users);
        messaging = (Switch)findViewById(R.id.clicked_messaging);

        updateAdminBtn = (Button)findViewById(R.id.update_admin_btn);


        com.example.model.semi_admins semiAdmins = (semi_admins) getIntent().
                getSerializableExtra("Semi Admin");//Getting the required class from the SemiAdminActivity


        Picasso.get().load(semiAdmins.getProfileImage()).into(admin_profile_pic);

        clickedAdminName.setText(semiAdmins.getSemiAdminName());
        clickedAdminMobile.setText(semiAdmins.getPhone());


        approveShop.setChecked(semiAdmins.getApprove_shop());
        maintainShop.setChecked(semiAdmins.getMaintain_shops());
        addProducts.setChecked(semiAdmins.getAdd_products());
        updateProducts.setChecked(semiAdmins.getUpdate_products());
        createOffers.setChecked(semiAdmins.getCreate_offers());
        maintainUsers.setChecked(semiAdmins.getMaintain_users());


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
