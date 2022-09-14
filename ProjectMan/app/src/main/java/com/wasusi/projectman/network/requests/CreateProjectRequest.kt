package com.wasusi.projectman.network.requests

data class CreateProjectRequest(
    val admin_id: Int,
    val description: String,
    val name: String,
    val total_cost: Int
)
