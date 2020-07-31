package com.example.owoshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

public class DummyPendingShopActivity extends AppCompatActivity {

    private ImageView shopImage,ownerNID,ownerTradeLicence;
    private Button createShopButton;
    private EditText shopName,shopAddress,shopServiceMobile,ownerName,ownerMobile,pass;
    private Switch availability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_pending_shop);

        shopImage=(ImageView)findViewById(R.id.shop_image);
        ownerNID=(ImageView)findViewById(R.id.shop_owner_nid);
        ownerTradeLicence=(ImageView)findViewById(R.id.shop_trade_licence);
        createShopButton=(Button)findViewById(R.id.create_shop_btn);
        shopName=(EditText)findViewById(R.id.shop_name);
        shopAddress=(EditText)findViewById(R.id.shop_address);
        shopServiceMobile=(EditText)findViewById(R.id.shop_service_mobile);
        ownerName=(EditText)findViewById(R.id.shop_owner_name);
        ownerMobile=(EditText)findViewById(R.id.shop_owner_mobile);
        pass=(EditText)findViewById(R.id.password);
        availability=(Switch)findViewById(R.id.shop_availability_switch);

        createShopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
