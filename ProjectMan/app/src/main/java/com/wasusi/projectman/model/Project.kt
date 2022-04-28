package com.wasusi.projectman.model

data class Project(
    val admin_id: Int,
    val completed_at: Any,
    val contractors: List<Contractor>?,
    val created_at: String,
    val description: String,
    val id: Int,
    val is_complete: Int,
    val name: String,
    val total_cost: Int,
    val updated_at: String
)
