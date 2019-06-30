package com.sample.appointment.retrofit

import android.content.Context
import com.sample.appointment.utils.CommonUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.util.HashMap

class WSClient<T> {

    init { if (requestQue == null) requestQue = HashMap() }

    companion object { private var requestQue: HashMap<Int, String>? = null }

    fun request(
        mContext: Context,
        requestId: Int,
        showProgress: Boolean,
        call: Call<T>,
        mSuccessHandler: ISuccessHandler<T>?,
        mFailureHandler: IFailureHandler?
    ) {
        if (showProgress) setUpProgressDialog(mContext, requestId)
        if (!CommonUtils.checkInternetConnection(mContext)) {
            if (showProgress) dismissProgressDialog(requestId)
            mFailureHandler?.failureResponse(requestId, "No Internet Connection.")
        } else {
            call.enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (showProgress) dismissProgressDialog(requestId)
                    mSuccessHandler?.successResponse(requestId, response.body()!!)
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    if (showProgress) dismissProgressDialog(requestId)
                    mFailureHandler?.failureResponse(requestId, "" + t.message)
                    call.cancel()
                }
            })
        }
    }

    private fun setUpProgressDialog(mContext: Context, requestId: Int) {
        requestQue!![requestId] = "Deployed"
        CommonUtils.showProgress(mContext)
    }

    private fun dismissProgressDialog(requestId: Int) {
        if (requestQue!!.containsKey(requestId)) requestQue!!.remove(requestId)
        if (requestQue!!.size <= 0) CommonUtils.hideProgress()
    }
}