package com.app.exceedjobs.api;

import com.app.exceedjobs.model.MessageModel;
import com.app.exceedjobs.model.UserModel;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {

    @GET("user_login/{phone}")
    Call<List<UserModel>> login(@Path("phone") String phoneNumber);

    @Headers("Content-Type: application/json")
    @POST("user_register")
    Call<Void> signup(@Body HashMap<String,String> signupCredentials);

    @GET("listmessage")
    Call<List<MessageModel>> getMessages();
}
