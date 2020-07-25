package com.example.owoshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    String[] segment;
    HomeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().hide();

        recyclerView=findViewById(R.id.recyclerviewid);
        int[] icons = {R.drawable.home1,R.drawable.home2,R.drawable.home3,R.drawable.home4,
                R.drawable.home5,R.drawable.home6,R.drawable.home7,R.drawable.home8};
        segment = getResources().getStringArray(R.array.home);

        List<String> segment1=new ArrayList<>();

        segment1.addAll(Arrays.asList(segment));

        List<Pair<String,Integer>> pairList=new ArrayList<Pair<String, Integer>>();
        for (int i=0;i<segment.length;i++)
        {
            pairList.add(new Pair<String, Integer>(segment[i],icons[i]));
        }

        adapter = new HomeAdapter(pairList,HomeActivity.this);

        recyclerView.setAdapter(adapter);

        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

    }
}
