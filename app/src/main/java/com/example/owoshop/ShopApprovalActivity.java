package com.example.owoshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class ShopApprovalActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_approval);

        getSupportActionBar().hide();

        recyclerView=findViewById(R.id.shop_approval_recyclerviewid);

        //here will be code for firebase recycler adapter and it will use shop_approval_sample.xml to inflate
        //when we click any single shop to approve, it will go to SingleShopApprovalActivity.class
    }
}
