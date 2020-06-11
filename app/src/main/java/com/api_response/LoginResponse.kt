package com.api_response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


 data class LoginResponse(
     @SerializedName("status")
     @Expose
     var status: String,
//    var data:ArrayList<data>
     @SerializedName("email")
     @Expose
     var email: String,
     @SerializedName("name")
     @Expose
     var name:  String,
     @SerializedName("profile_url")
     @Expose
     var profile_url:  String,
     @SerializedName("fcm_token")
     @Expose
     var fcm_token:  String

)

 class data(
    var name: String ,
    var email: String,
    var fcm_token: String,
    var profile_url: String

)