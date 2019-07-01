package com.sample.appointment.retrofit

import com.google.gson.JsonObject
import com.sample.appointment.model.BaseResponseModel
import com.sample.appointment.model.CreatedUserModel
import com.sample.appointment.model.UserModel
import retrofit2.Call
import retrofit2.http.*

interface RetrofitInterface {

    @GET("/api/users")
    fun getUsers(@QueryMap queryMap: Map<String, Int>): Call<BaseResponseModel<UserModel>>

    @Headers("Accept: application/json")
    @POST("/api/users")
    fun addUser(@Body bodyData: JsonObject): Call<CreatedUserModel>

}