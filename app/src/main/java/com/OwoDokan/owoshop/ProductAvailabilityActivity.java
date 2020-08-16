package com.OwoDokan.owoshop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.OwoDokan.model.Products;
import com.OwoDokan.pagination.ItemAdapter;
import com.OwoDokan.pagination.ItemViewModel;

public class ProductAvailabilityActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_availability);

        recyclerView = findViewById(R.id.product_availability_recyclerview_id);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ItemViewModel itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        final ItemAdapter adapter = new ItemAdapter(this);

        itemViewModel.itemPagedList.observe(this, new Observer<PagedList<Products>>() {
            @Override
            public void onChanged(@Nullable PagedList<Products> items) {
                adapter.submitList(items);
            }
        });

        recyclerView.setAdapter(adapter);
    }

}
