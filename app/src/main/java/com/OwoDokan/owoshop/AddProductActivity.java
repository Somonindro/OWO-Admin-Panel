package com.OwoDokan.owoshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.OwoDokan.model.Products;
import com.OwoDokan.pagination.ItemAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddProductActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    String[] product;
    AddProductAdapter adapter;
    private ImageView back_to_home;
    private SearchView search_in_category;
    private TextView category_heading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        back_to_home = findViewById(R.id.back_to_home_arrow);
        search_in_category = findViewById(R.id.search_in_category);
        category_heading = findViewById(R.id.heading_category);

        int[] icons = {R.drawable.icon1, R.drawable.icon2, R.drawable.icon3, R.drawable.icon4, R.drawable.icon5, R.drawable.icon6,
                R.drawable.icon7, R.drawable.icon8, R.drawable.icon9, R.drawable.icon10, R.drawable.icon11, R.drawable.icon12,
                R.drawable.icon13, R.drawable.icon14, R.drawable.icon15, R.drawable.icon16, R.drawable.icon17, R.drawable.icon18,
                R.drawable.icon19, R.drawable.icon20, R.drawable.icon21, R.drawable.icon22, R.drawable.icon23, R.drawable.icon24,
                R.drawable.icon25, R.drawable.icon26, R.drawable.icon27, R.drawable.icon28, R.drawable.icon29, R.drawable.icon30,
                R.drawable.icon31, R.drawable.icon32, R.drawable.icon33, R.drawable.icon34, R.drawable.icon35, R.drawable.icon36,
                R.drawable.icon37, R.drawable.icon38, R.drawable.icon39, R.drawable.icon40, R.drawable.icon41};

        product = getResources().getStringArray(R.array.productname);

        List<String> product1 = new ArrayList<>();

        product1.addAll(Arrays.asList(product));

        List<Pair<String, Integer>> pairList = new ArrayList<Pair<String, Integer>>();

        for (int i = 0; i < product.length; i++) {
            pairList.add(new Pair<String, Integer>(product[i], icons[i]));
        }

        recyclerView = findViewById(R.id.add_product_recyclerview_id);

        //this is added now
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(AddProductActivity.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(AddProductActivity.this, SingleProductAddActivity.class);
                        intent.putExtra("category", AddProductAdapter.product1.get(position).first);
                        startActivity(intent);
                        finish();
                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );
        //ei prjonto

        adapter = new AddProductAdapter(pairList, AddProductActivity.this);

        recyclerView.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        back_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        search_in_category.setMaxWidth(250);

        search_in_category.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });

    }
}
