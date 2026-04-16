package com.example.catgifapp

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CatApiService {

    @GET("images/search")
    suspend fun getCats(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("mime_types") mimeTypes: String,
        @Header("x-api-key") apiKey: String = "live_5vduqHtBgcwwzMoMFo6vQz4cmA3qXMDx6w04jnYscBxwnNeCFPme5NvNUVeYxMwI"
    ): List<CatResponse>
}