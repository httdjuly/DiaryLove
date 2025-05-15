package com.example.diaryloveproject.api;

import com.example.diaryloveproject.model.DiaryNote;
import com.example.diaryloveproject.model.Emoji;
import com.example.diaryloveproject.model.Signup;
import com.example.diaryloveproject.model.SignupRequest;
import com.example.diaryloveproject.model.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @FormUrlEncoded
    @POST("/api/auth/signin")
    Call<User> login(
            @Field("email") String email,
            @Field("password") String password
    );
    @POST("/signup")
    Call<Signup> signup(@Body SignupRequest signupRequest);
    @FormUrlEncoded
    @POST("/signup/verify-otp")
    Call<Signup> verifyOtp(
            @Field("email") String email,
            @Field("otp") String otp,
            @Field("username") String username,
            @Field("password") String password
    );
    @FormUrlEncoded
    @POST("/signup/resend-otp")
    Call<Map<String, Object>> resendOtp(@Field("email") String email);
    @FormUrlEncoded
    @POST("/forgot-password")
    Call<Map<String, Object>> forgotPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("/forgot-password/verify-otp")
    Call<Map<String, Object>> verifyPasswordResetOtp(
            @Field("email") String email,
            @Field("otp") String otp);

    @FormUrlEncoded
    @POST("/forgot-password/reset-password")
    Call<Map<String, Object>> resetPassword(
            @Field("email") String email,
            @Field("newPassword") String newPassword);

    @GET("/api/emojis")
    Call<List<Emoji>> getAllEmojis();

    @GET("/api/emojis/category")
    Call<List<Emoji>> getEmojisByCategory(@Query("category") String category);

    @POST("/api/diary-notes")
    Call<Map<String, String>> createOrUpdateNote(@Body DiaryNote diaryNote);
    @GET("/api/diary-notes/dates")
    Call<List<DiaryNote>> getNotesByUser(@Query("userId") Long userId);


}
