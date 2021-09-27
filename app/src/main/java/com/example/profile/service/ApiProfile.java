package com.example.profile.service;

import com.example.profile.model.ResponseData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiProfile {
    @FormUrlEncoded
    @POST("insert.php")
    Call<ResponseData> addData(
            @Field("nama") String nama,
            @Field("email") String email,
            @Field("noHp") String noHp
    );

    @FormUrlEncoded
    @POST("update.php")
    Call<ResponseData> editData(
            @Field("id") String id, @Field("nama") String nama,
            @Field("email") String email,
            @Field("noHp") String noHp
    );

    @FormUrlEncoded
    @POST("delete.php")
    Call<ResponseData> deleteData(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("search.php")
    Call<ResponseData> searchData(
            @Field("search") String keyword
    );

    @GET("getdata.php")
    Call<ResponseData> getData();
}
