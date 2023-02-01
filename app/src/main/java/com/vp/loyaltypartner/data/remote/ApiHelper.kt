package com.vp.loyaltypartner.data.remote

import com.vp.loyaltypartner.data.model.APIResponse
import com.vp.loyaltypartner.data.model.User
import retrofit2.Response

interface ApiHelper {

   suspend fun getUsers(): Response<List<User>>
   suspend fun getData(s: String): Response<APIResponse>

}