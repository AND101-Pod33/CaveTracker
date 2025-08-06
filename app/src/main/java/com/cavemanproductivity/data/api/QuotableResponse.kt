package com.cavemanproductivity.data.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuotableResponse(
    @Json(name = "content") val content: String,
    @Json(name = "author") val author: String,
    @Json(name = "_id") val id: String? = null,
    @Json(name = "tags") val tags: List<String>? = null,
    @Json(name = "length") val length: Int? = null
)