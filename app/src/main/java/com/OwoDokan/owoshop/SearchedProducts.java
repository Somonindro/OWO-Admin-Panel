package com.OwoDokan.owoshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.OwoDokan.Network.RetrofitClient;
import com.OwoDokan.adapter.SearchedAdapter;
import com.OwoDokan.model.Products;
import com.OwoDokan.response.OwoApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchedProducts extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private List<Products> productsList;
    private SearchedAdapter adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_products);

        searchView = findViewById(R.id.search_product_view);
        recyclerView = findViewById(R.id.searched_products_recycler_view);
        progressBar = findViewById(R.id.searched_progressbar);

        searchView.onActionViewExpanded();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressBar.setVisibility(View.VISIBLE);
                getItem(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    private void getItem(String query) {
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchedProducts.this));

        Call<OwoApiResponse> call = RetrofitClient.getInstance().getApi().searchProduct(query);

        call.enqueue(new Callback<OwoApiResponse>() {
            @Override
            public void onResponse(Call<OwoApiResponse> call, Response<OwoApiResponse> response) {
                if(!response.body().error)
                {
                    productsList = response.body().products;
                    adapter = new SearchedAdapter(SearchedProducts.this, productsList);
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<OwoApiResponse> call, Throwable t) {

            }
        });
    }
}