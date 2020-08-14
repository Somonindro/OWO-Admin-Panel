package com.OwoDokan.owoshop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    static String[] segment;
    static int[] icons = {R.drawable.home1,R.drawable.home2,R.drawable.home3,R.drawable.home4,
            R.drawable.home5, R.drawable.home6, R.drawable.home7,R.drawable.home8,R.drawable.home9};

    public static int p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        DisplayMetrics displayMetrics = HomeActivity.this.getResources().getDisplayMetrics();
        p = displayMetrics.widthPixels;

        recyclerView=findViewById(R.id.recyclerviewid);

        segment = getResources().getStringArray(R.array.home);


        HomeAdapter adapter = new HomeAdapter(HomeActivity.this);

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager=new GridLayoutManager(this,2);

        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);

        View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.custom_exit_alert_dialog, null);

        Button yes = view.findViewById(R.id.leave);
        Button no = view.findViewById(R.id.not_leave);

        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
    }
}
