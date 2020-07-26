package com.example.owoshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class SemiAdminActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semi_admin);

        getSupportActionBar().hide();

        recyclerView=findViewById(R.id.semi_admin_recyclerviewid);

        //here will be code for firebase recycler adapter and it will use semiadmin_sample.xml to inflate

        //when we click any semi-admin, it will go to UpdateSemiAdminActivity.class
    }
}
