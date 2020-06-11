package com.corona_ui.bluetoothlocator.remote

import com.api_request.LoginRequest
import com.api_request.RegisterRequest
import com.api_response.LoginResponse
import com.api_response.RegisterResponse
import com.google.gson.JsonObject
import com.model.APIResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PhpApiInterface {
    @FormUrlEncoded
    @POST("api_version/common_app_api.php")
    fun GetAndroidVersion(@Field("app_name") app_name : String,
                          @Field("request_type") request_type : String
    ): Call<JsonObject>


    @POST("api_mgi_alert/user_register.php")
    fun RegisterUser(
        @Body request: RegisterRequest
    ): Call<RegisterResponse>

    @POST("api_mgi_alert/check_login.php")
    fun UserLogin(
        @Body request: LoginRequest
    ):Call<LoginResponse>


}