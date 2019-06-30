package com.sample.appointment.views.users_list

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.sample.appointment.R
import com.sample.appointment.model.UserModel
import kotlinx.android.synthetic.main.item_user.view.*
import java.util.*

class UserListAdapter(private val mContext: Context) : RecyclerView.Adapter<UserListAdapter.UserItemHolder>() {

    private val userModelList = ArrayList<UserModel>()

    fun resetList(userModelList: List<UserModel>) {
        this.userModelList.clear()
        this.userModelList.addAll(userModelList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item_user, parent, false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: UserItemHolder, position: Int) {
        val userModel = userModelList[position]
        holder.itemView.tvUserName.text = "${userModel.firstName} ${userModel.lastName}"
        holder.itemView.tvUserEmailAddress.text = userModel.email
        Glide.with(mContext).load(userModel.avatar).into(holder.itemView.profile_image)
    }

    override fun getItemCount() = userModelList.size

    inner class UserItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
