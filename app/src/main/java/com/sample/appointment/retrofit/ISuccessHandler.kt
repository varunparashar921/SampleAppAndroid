package com.sample.appointment.retrofit

interface ISuccessHandler<T> {
    fun successResponse(requestCode: Int, mResponse: T)
}
