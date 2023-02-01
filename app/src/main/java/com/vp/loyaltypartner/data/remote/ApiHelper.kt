package com.vp.loyaltypartner.data.remote

import com.vp.loyaltypartner.data.model.APIResponse
import retrofit2.Response

interface ApiHelper {
   suspend fun getData(s: String): Response<APIResponse>

}