package com.moov.moovapp.model

import com.google.gson.annotations.SerializedName

/**
 * Api models
 */

data class ApiResponse(
    @SerializedName("data")
    var users: List<User>,

    val page: Int = 0,

    @SerializedName("per_page")
    val perPage: Int = 0,

    val total: Int = 0,

    @SerializedName("total_pages")
    val totalPages: Int = 0
)

data class User(
    val id: Long,
    val email: String,
    @SerializedName("first_name")
    val firsName: String,
    @SerializedName("last_name")
    val lastName: String,
    val avatar: String
) {

    fun getFullName(): String {
        return "$firsName $lastName"
    }
}
