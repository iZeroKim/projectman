package com.wasusi.projectman.network.requests

import com.google.gson.annotations.SerializedName

data class ContractorRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String
)
