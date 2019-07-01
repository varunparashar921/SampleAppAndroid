package com.sample.appointment.model

import com.google.gson.annotations.SerializedName

data class CreatedUserModel(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("job")
    val job: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null
)