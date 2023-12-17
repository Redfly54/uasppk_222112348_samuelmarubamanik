package com.example.uasppk_3si1_samuelmarubamanik_34;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthService {
    @POST("/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("register")
    Call<User> register(@Body User user);

    @POST("/change-password")
    Call<Void> changePassword(@Body ChangePasswordRequest changePasswordRequest);

    @GET("/profile")
    Call<UserProfile> getUserProfile();

    @POST("/edit-profile")
    Call<UserDto> editProfile(@Body UserDto userDto);
}
