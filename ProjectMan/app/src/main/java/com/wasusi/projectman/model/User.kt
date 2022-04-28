package com.wasusi.projectman.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var email: String,
    @SerializedName("email")
    var name: String
)
