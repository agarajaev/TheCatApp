package com.thecat.app.data.network.response

import com.google.gson.annotations.SerializedName

data class BreedResponse(
    @SerializedName("name") val name: String?,
    @SerializedName("origin") val country: String?,
    @SerializedName("description") val desc: String?,
    @SerializedName("life_span") val age: String?
)