package com.example.owoshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class ProductAvailabilityActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_availability);

        recyclerView=findViewById(R.id.product_availability_recyclerview_id);

        //here will be code for firebase recycler adapter and it will use product_availability_sample.xml to inflate
    }
}
