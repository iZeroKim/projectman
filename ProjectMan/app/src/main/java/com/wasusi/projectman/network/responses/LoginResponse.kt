package com.wasusi.projectman.network.responses

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("token")
    val token: Token,
    @SerializedName("user")
    val userData: UserData
)
