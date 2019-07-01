package com.sample.appointment.views.users_list

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sample.appointment.R
import com.sample.appointment.service.UserManagementService
import com.sample.appointment.views.MainActivity
import com.sample.appointment.views.create_user.CreateUserFragment
import kotlinx.android.synthetic.main.users_fragment.*

class UsersFragment : Fragment() {

    private var rootView: View? = null
    private var isScreenFirstLoad = true
    private lateinit var adapter: UserListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.users_fragment, container, false)
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
        adapter = UserListAdapter(activity as Context)
        rvAppointments.layoutManager = LinearLayoutManager(activity)
        rvAppointments.adapter = adapter
        getAllInfo(true)
        srl.setOnRefreshListener { getAllInfo(false) }
        btCreateNewUser.setOnClickListener {
            (activity as MainActivity).push(CreateUserFragment(), true)
        }
    }

    private fun getAllInfo(isShowProgress: Boolean) {
        UserManagementService(activity as MainActivity).requestUsersList(isShowProgress, success = { usersList ->
            srl.isRefreshing = false
            adapter.resetList(usersList)
            manageEmptyView(false)
        }) { error ->
            srl.isRefreshing = false
            manageEmptyView(true, error.message!!)
        }
    }

    private fun manageEmptyView(isEmptyView: Boolean, message: String = "") {
        if (isEmptyView) {
            rvAppointments.visibility = View.GONE
            tvEmptyView.visibility = View.VISIBLE
            tvEmptyView.text = message
        } else {
            rvAppointments.visibility = View.VISIBLE
            tvEmptyView.visibility = View.GONE
        }
    }
}