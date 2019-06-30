package com.sample.appointment.retrofit

import com.sample.appointment.model.BaseResponseModel
import com.sample.appointment.model.UserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RetrofitInterface {

    @GET("/api/users")
    fun getUsers(@QueryMap queryMap: Map<String, Int>): Call<BaseResponseModel<UserModel>>

}