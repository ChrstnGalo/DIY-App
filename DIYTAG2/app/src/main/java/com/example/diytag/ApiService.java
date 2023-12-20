package com.example.diytag;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiService {
    @GET("users")
    Call<List<User>> getUsers();

    @GET("get_products.php")
    Call<List<Product>> getProducts();

    @FormUrlEncoded
    @POST("update_balance.php") // Palitan ang URL na ito ng tamang endpoint ng iyong server
    Call<String> updateBalance(
            @Field("user_id") int user_id,
            @Field("new_balance") double new_balance
    );

    @GET("check_updates.php") // Palitan ng tamang URL at endpoint
    Call<ServerResponse> checkForUpdates(
            @Query("userId") int userId,
            @Query("currentUsername") String currentUsername,
            @Query("currentEmail") String currentEmail,
            @Query("currentPassword") String currentPassword,
            @Query("currentImageUrl") String currentImageUrl,
            @Query("currentGender") String currentGender
    );

    @GET("get_UsernameImage.php")
    Call<UsernameImage> getUserDetails(@Query("user_id") int userId);

    @GET("get_myProfile.php")
    Call<MyProfile> getUserProfile(@Query("user_id") int userId);

    @FormUrlEncoded
    @POST("getReceiptandDate.php") // Palitan ito sa tamang endpoint URL mo
    Call<List<Sales>> getUserSales(
            @Field("user_id") int userId // Ito ay para sa pagpasa ng user_id
    );

    @GET("get_discounted_products.php") // Ilagay ang tama at kumpletong URL ng iyong PHP script
    Call<List<DiscountProduct>> getDiscountedProducts();

    @GET("get_users_balance.php")
    Call<String> getUserBalance(@Query("user_id") int user_id);

    @GET("get_rfid_num.php")
    Call<MyRfid> getUserRfid(@Query("user_id") int userId);

    @FormUrlEncoded
    @POST("updateUserPin.php")
    Call<Void> updateUserPin(
            @Field("user_id") int userId,
            @Field("pin_num") int pinNum
    );
}






