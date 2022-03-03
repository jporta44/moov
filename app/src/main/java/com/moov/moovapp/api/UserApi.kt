package com.moov.moovapp.api

import com.moov.moovapp.model.ApiResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("users")
    suspend fun getUsers(@Query("page") page: Int?): ApiResponse
}

object UserApi {
    private const val BASE_URL = "https://reqres.in/api/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val userService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }

    suspend fun getUsers(page: Int? = null): ApiResponse {
        return userService.getUsers(page)
    }
}