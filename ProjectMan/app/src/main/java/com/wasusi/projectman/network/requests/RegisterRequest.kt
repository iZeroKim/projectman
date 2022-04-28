package com.wasusi.projectman.network.requests

data class RegisterRequest(
    val email: String,
    val name: String,
    val password: String,
    val password_confirmation: String,
    val phone_number: String
)
