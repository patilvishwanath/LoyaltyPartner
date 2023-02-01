package com.vp.loyaltypartner.data.remote

import com.vp.loyaltypartner.data.model.APIResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun getData(s: String): Response<APIResponse> = apiService.getApiResponse(searchString = s)
}