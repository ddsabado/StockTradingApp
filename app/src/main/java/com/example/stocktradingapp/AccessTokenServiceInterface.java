package com.example.stocktradingapp;

import com.example.stocktradingapp.token.TokenResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AccessTokenServiceInterface {

    @POST("token")
    @FormUrlEncoded
//    Call<TokenResponse> getToken(@Field("client_id") String client_id, @Field("client_secret") String client_secret, @Field("scope") String scope, @Field("grant_type") String grant_type);
    Call<TokenResponse> getToken(@Field("client_id") String client_id, @Field("client_secret") String client_secret, @Field("grant_type") String grant_type);
}
