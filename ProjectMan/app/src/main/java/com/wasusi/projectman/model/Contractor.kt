package com.wasusi.projectman.model

data class Contractor(
    val created_at: String,
    val email: String,
    val id: Int,
    val name: String,
    val pivot: Pivot,
    val updated_at: String
)
