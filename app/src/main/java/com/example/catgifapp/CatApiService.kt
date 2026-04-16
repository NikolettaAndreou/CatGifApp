package com.example.catgifapp

import retrofit2.http.GET
import retrofit2.http.Query

interface CatApiService {

    @GET("images/search")
    suspend fun getCats(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("mime_types") mimeTypes: String
    ): List<CatResponse>
}