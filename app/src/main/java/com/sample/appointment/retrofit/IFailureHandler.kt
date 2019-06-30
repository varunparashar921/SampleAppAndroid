package com.sample.appointment.retrofit

interface IFailureHandler {
    fun failureResponse(requestCode: Int, message: String)
}
