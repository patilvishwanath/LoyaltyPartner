package com.vp.loyaltypartner.data.remote

import com.vp.loyaltypartner.data.model.APIResponse
import com.vp.loyaltypartner.data.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUsers(): Response<List<User>>
    @GET("api/")
    suspend fun getApiResponse(@Query("key")key:String = "33134379-fa21b280cae539d660c7f77e9",
    @Query ("q") searchString : String,
    @Query ("image_type") imageType : String = "photo") : Response<APIResponse>
}