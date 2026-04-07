package com.example.catgifapp

import retrofit2.http.GET
import retrofit2.http.Query

interface CatApiService {

    @GET("v1/images/search")
    suspend fun getCats(
        @Query("mime_types") mime: String = "gif",
        @Query("order") order: String = "random",
        @Query("limit") limit: Int = 1
    ): List<CatResponse>
}