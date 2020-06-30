package com.thecat.app.data.network.response

import com.google.gson.annotations.SerializedName

data class CatResponse(
    @SerializedName("id") val id: String?,
    @SerializedName("url") val imageUrl: String?,
    @SerializedName("breeds") val breeds: List<BreedResponse>?
)