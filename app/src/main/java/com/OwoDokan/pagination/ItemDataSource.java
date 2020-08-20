package com.OwoDokan.pagination;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.OwoDokan.Network.RetrofitClient;
import com.OwoDokan.model.Products;
import com.OwoDokan.response.OwoApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDataSource extends PageKeyedDataSource<Integer, Products> {

    private static final int FIRST_PAGE = 0;

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Products> callback) {

        RetrofitClient.getInstance()
                .getApi()
                .getAnswers(FIRST_PAGE)
                .enqueue(new Callback<OwoApiResponse>() {
                    @Override
                    public void onResponse(Call<OwoApiResponse> call, Response<OwoApiResponse> response) {

                        if(response.body() != null){

                            callback.onResult(response.body().products, null, FIRST_PAGE+1);//(First page +1) is representing next page

                        }
                    }

                    @Override
                    public void onFailure(Call<OwoApiResponse> call, Throwable t) {

                    }
                });

    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Products> callback) {

        RetrofitClient.getInstance()
                .getApi()
                .getAnswers(params.key)
                .enqueue(new Callback<OwoApiResponse>() {
                    @Override
                    public void onResponse(Call<OwoApiResponse> call, Response<OwoApiResponse> response) {

                        Log.d("Amimul", "Called");

                        if(response.body() != null){
                            Integer key = (params.key > 0) ? params.key - 1 : null;
                            callback.onResult(response.body().products, key);
                        }
                    }

                    @Override
                    public void onFailure(Call<OwoApiResponse> call, Throwable t) {

                    }
                });

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Products> callback) {

        RetrofitClient.getInstance()
                .getApi()
                .getAnswers(params.key)
                .enqueue(new Callback<OwoApiResponse>() {
                    @Override
                    public void onResponse(Call<OwoApiResponse> call, Response<OwoApiResponse> response) {

                        if(response.body() != null){
                            callback.onResult(response.body().products, params.key+1);
                        }

                    }

                    @Override
                    public void onFailure(Call<OwoApiResponse> call, Throwable t) {

                    }
                });


    }
}
