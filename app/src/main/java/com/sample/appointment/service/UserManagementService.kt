package com.sample.appointment.service

import android.util.Log
import com.google.gson.JsonObject
import com.sample.appointment.model.BaseResponseModel
import com.sample.appointment.model.CreatedUserModel
import com.sample.appointment.model.UserModel
import com.sample.appointment.retrofit.*
import com.sample.appointment.views.MainActivity

class UserManagementService(private val mActivity: MainActivity) {

    fun requestUsersList(isProgressNeed: Boolean, success: (List<UserModel>) -> Unit, failure: (Error) -> Unit) {

        val queryMap = HashMap<String, Int>()
        queryMap[APIConstants.KEY_PAGE] = 1
        queryMap[APIConstants.KEY_PER_PAGE] = 10

        val getUsersRequest = ApiClient.getRetrofitInterface().getUsers(queryMap)

        WSClient<BaseResponseModel<UserModel>>().request(mActivity, 1, isProgressNeed, getUsersRequest,
            object : ISuccessHandler<BaseResponseModel<UserModel>> {
                override fun successResponse(requestCode: Int, mResponse: BaseResponseModel<UserModel>) {
                    if (mResponse.data != null && mResponse.data.isNotEmpty()) {
                        success(mResponse.data)
                    } else failure(Error("Users Nor available."))
                }
            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    Log.e("failureResponse", message)
                    failure(Error(message))
                }
            })
    }

    fun createNewUser(
        userName: String,
        jobName: String,
        success: (CreatedUserModel) -> Unit,
        failure: (Error) -> Unit
    ) {
        val requestObject = JsonObject()
        requestObject.addProperty(APIConstants.KEY_NAME, userName)
        requestObject.addProperty(APIConstants.KEY_JOB, jobName)

        val appointmentReq = ApiClient.getRetrofitInterface().addUser(requestObject)

        WSClient<CreatedUserModel>().request(mActivity, 1, true, appointmentReq,
            object : ISuccessHandler<CreatedUserModel> {
                override fun successResponse(requestCode: Int, mResponse: CreatedUserModel) {
                    success(mResponse)
                }
            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    Log.e("failureResponse", message)
                    failure(Error(message))
                }
            })

    }
}