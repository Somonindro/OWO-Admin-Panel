package com.OwoDokan.pagination;

import androidx.annotation.NonNull;
import androidx.paging.ItemKeyedDataSource;
import androidx.paging.PageKeyedDataSource;

import com.OwoDokan.Network.RetrofitClient;
import com.OwoDokan.model.Products;
import com.OwoDokan.response.OwoApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDataSource extends PageKeyedDataSource<Integer, Products> {

    private static final int FIRST_PAGE = 1;

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Products> callback) {

        RetrofitClient.getInstance()
                .getApi()
                .getAnswers(FIRST_PAGE)
                .enqueue(new Callback<OwoApiResponse>() {
                    @Override
                    public void onResponse(Call<OwoApiResponse> call, Response<OwoApiResponse> response) {

                        if(response.body() != null){

                            callback.onResult(response.body().products, null, FIRST_PAGE + 1);

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

                        if(response.body() != null){
                            Integer key = (params.key > 1) ? params.key - 1 : null;
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
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Integer key = params.key + 1 ;
                            callback.onResult(response.body().products, key);
                        }

                    }

                    @Override
                    public void onFailure(Call<OwoApiResponse> call, Throwable t) {

                    }
                });


    }
}