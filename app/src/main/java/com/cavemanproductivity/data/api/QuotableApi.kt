package com.cavemanproductivity.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuotableApi {
    @GET("random")
    suspend fun getRandomQuote(
        @Query("minLength") minLength: Int? = null,
        @Query("maxLength") maxLength: Int? = null,
        @Query("tags") tags: String? = null
    ): Response<QuotableResponse>

    companion object {
        const val BASE_URL = "https://api.quotable.io/"
    }
}