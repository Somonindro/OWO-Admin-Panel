package com.OwoDokan.owoshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.OwoDokan.model.PendingShop;
import com.bumptech.glide.Glide;

import java.util.Objects;

public class PendingShopDetails extends AppCompatActivity {

    private TextView shop_name, shop_keeper_name, shop_keeper_mobile, shop_address,
            shop_service_mobile, req_category_1, req_category_2, req_category_3;

    private CheckBox checkbox1, checkbox2, checkBox3;

    private ImageView shop_image, shop_keeper_nid, shop_trade_license;

    private Button see_in_map, approve_shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_shop_details);

        PendingShop pendingShop = (PendingShop) getIntent().getSerializableExtra("PendingShop");

        shop_name = findViewById(R.id.shop_name);
        shop_keeper_name = findViewById(R.id.shop_owner_name);
        shop_keeper_mobile = findViewById(R.id.shop_owner_mobile);
        shop_address = findViewById(R.id.shop_address);
        shop_service_mobile = findViewById(R.id.shop_service_mobile);
        req_category_1 = findViewById(R.id.requested_category_1);
        req_category_2 = findViewById(R.id.requested_category_2);
        req_category_3 = findViewById(R.id.requested_category_3);


        checkbox1 = findViewById(R.id.category_1_check_box);
        checkbox2 = findViewById(R.id.category_2_check_box);
        checkBox3 = findViewById(R.id.category_3_check_box);

        shop_image = findViewById(R.id.shop_image);
        shop_keeper_nid = findViewById(R.id.shop_owner_nid);
        shop_trade_license = findViewById(R.id.shop_trade_licence);

        see_in_map = findViewById(R.id.shop_address_in_map);
        approve_shop = findViewById(R.id.approve_shop);

        shop_name.setText(pendingShop.getShop_name());
        shop_keeper_name.setText(pendingShop.getShop_owner_name());
        shop_keeper_mobile.setText(pendingShop.getShop_owner_mobile());
        shop_service_mobile.setText(pendingShop.getShop_service_mobile());
        shop_address.setText(pendingShop.getShop_address());

        Glide.with(getApplicationContext()).load(pendingShop.getShop_image_uri()).into(shop_image);
        Glide.with(getApplicationContext()).load(pendingShop.getShop_keeper_nid_front_uri()).into(shop_keeper_nid);
        Glide.with(getApplicationContext()).load(pendingShop.getTrade_license_uri()).into(shop_trade_license);

        int size = pendingShop.getHaveAccess().size();

        if(size == 1)
        {
            req_category_1.setText(pendingShop.getHaveAccess().get(0));
            checkbox2.setVisibility(View.GONE);
            checkBox3.setVisibility(View.GONE);
        }
        else if(size == 2)
        {
            req_category_1.setText(pendingShop.getHaveAccess().get(0));
            req_category_2.setText(pendingShop.getHaveAccess().get(1));
            checkBox3.setVisibility(View.GONE);
        }
        else
        {
            req_category_1.setText(pendingShop.getHaveAccess().get(0));
            req_category_2.setText(pendingShop.getHaveAccess().get(1));
            req_category_3.setText(pendingShop.getHaveAccess().get(2));
        }
    }
}