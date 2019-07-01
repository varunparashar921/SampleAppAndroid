package com.sample.appointment.views.create_user

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sample.appointment.R
import com.sample.appointment.service.UserManagementService
import com.sample.appointment.utils.AlertUtils
import com.sample.appointment.utils.CommonUtils
import com.sample.appointment.views.MainActivity
import kotlinx.android.synthetic.main.add_user_fragment.*

class CreateUserFragment : Fragment() {

    private var rootView: View? = null
    private var isScreenFirstLoad = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.add_user_fragment, container, false)
        } else container?.removeView(rootView)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isScreenFirstLoad) {
            initView()
            isScreenFirstLoad = false
        }
    }

    private fun initView() {
        btCreateUser.setOnClickListener {
            CommonUtils.hideSoftKeyboard(activity)
            val userName = etUserName.text!!.trim().toString()
            val jobName = etJobName.text!!.trim().toString()

            when {
                TextUtils.isEmpty(userName) -> AlertUtils.showMessage(activity!!, "Please Enter User Name.") {}
                TextUtils.isEmpty(jobName) -> AlertUtils.showMessage(activity!!, "Please Enter Job Name.") {}
                else -> {
                    UserManagementService(activity as MainActivity).createNewUser(userName, jobName, success = {
                        AlertUtils.showMessage(activity!!, "User create successfully") {
                            (activity as MainActivity).onBackPressed()
                        }
                    }) {
                        AlertUtils.showMessage(activity!!, it.message!!) {
                            (activity as MainActivity).onBackPressed()
                        }
                    }
                }
            }

        }
    }
}