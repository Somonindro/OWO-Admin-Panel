package com.OwoDokan.owoshop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    static String[] segment;
    static int[] icons = {R.drawable.home1,R.drawable.home2,R.drawable.home3,R.drawable.home4,
            R.drawable.home5, R.drawable.home6, R.drawable.home7,R.drawable.home8};

    private HomeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView=findViewById(R.id.recyclerviewid);

        segment = getResources().getStringArray(R.array.home);


        adapter = new HomeAdapter(HomeActivity.this);

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager=new GridLayoutManager(this,2);

        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Do you want to close this application ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
