package com.couponsecure.couponsecure;


import com.couponsecure.couponsecure.ChainModels.ChainModel;
import com.couponsecure.couponsecure.ResponseModels.MessageResponse;
import com.couponsecure.couponsecure.ResponseModels.MiningResponseModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("chain")
    Call<ChainModel> getCouponChain();

    @GET("mine")
    Call<MiningResponseModel> mineBlock();

    @Headers("Accept: application/json")
    @POST("transactions/new")
    Call<MessageResponse> newTransaction(@Body RequestBody body);

}