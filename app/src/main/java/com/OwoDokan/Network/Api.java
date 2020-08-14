package com.OwoDokan.Network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface Api {

    @FormUrlEncoded
    @POST("createProduct")
    Call<ResponseBody> createProduct(
            @Field("product_id") String product_id,
            @Field("product_image") String product_image,
            @Field("product_name") String product_name,
            @Field("product_category") String product_category,
            @Field("product_price") String product_price,
            @Field("product_discount") String product_discount,
            @Field("product_quantity") String product_quantity,
            @Field("product_description") String product_description,
            @Field("product_date") String product_date,
            @Field("product_time") String product_time
    );

}