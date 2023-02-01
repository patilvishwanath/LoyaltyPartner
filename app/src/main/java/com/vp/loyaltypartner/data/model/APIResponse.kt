package com.vp.loyaltypartner.data.model


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class APIResponse (

@Json(name = "total") var total : Int?= null,
@Json(name = "totalHits") var totalHits : Int?= null,
@Json(name = "hits") var hits: List<Hits>

):Parcelable