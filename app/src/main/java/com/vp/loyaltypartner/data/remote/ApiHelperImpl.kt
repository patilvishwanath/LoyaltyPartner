package com.vp.loyaltypartner.data.remote

import com.vp.loyaltypartner.data.model.APIResponse
import com.vp.loyaltypartner.data.model.User
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getUsers(): Response<List<User>> = apiService.getUsers()
    override suspend fun getData(s: String): Response<APIResponse> = apiService.getApiResponse(searchString = s)


}