package com.wasusi.projectman.network

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private lateinit var apiService: ApiService

    fun getApiService(): ApiService{
        //Create it if noot created yet
        if(!::apiService.isInitialized){
            val retrofit = Retrofit.Builder().baseUrl(Consts.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            apiService = retrofit.create(ApiService::class.java)
        }
        return apiService
    }
}
