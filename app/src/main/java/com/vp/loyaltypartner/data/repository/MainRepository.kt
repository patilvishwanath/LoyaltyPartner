package com.vp.loyaltypartner.data.repository

import com.vp.loyaltypartner.data.remote.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun getApiResponse(s: String) =  apiHelper.getData(s)
}