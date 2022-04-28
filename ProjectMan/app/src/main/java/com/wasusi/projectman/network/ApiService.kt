package com.wasusi.projectman.network

import com.wasusi.projectman.model.Contractor
import com.wasusi.projectman.model.Project
import com.wasusi.projectman.network.requests.ContractorRequest
import com.wasusi.projectman.network.requests.CreateProjectRequest
import com.wasusi.projectman.network.requests.LoginRequest
import com.wasusi.projectman.network.requests.RegisterRequest
import com.wasusi.projectman.network.responses.LoginResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST(Consts.LOGIN_URL)
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST(Consts.REGISTER_URL)
    fun register(@Body registerRequest: RegisterRequest): Call<LoginResponse>

    @GET(Consts.PROJECTS_URL)
    fun getProjects(@Header("Authorization") token: String): Call<List<Project>>

    @POST(Consts.PROJECTS_URL)
    fun postProject(@Header("Authorization") token: String, @Body createProjectRequest: CreateProjectRequest): Call<List<Project>>

    @GET(Consts.CONTRACTORS_URL)
    fun getContractors(@Header("Authorization") token: String): Call<List<Contractor>>

    @POST(Consts.CONTRACTORS_URL)
    fun postContractor(@Header("Authorization") token: String, @Body contractorRequest: ContractorRequest): Call<List<Contractor>>
}
