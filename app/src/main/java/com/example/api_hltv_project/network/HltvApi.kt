package com.example.api_hltv_project.network



import com.example.api_hltv_project.data.Player
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

const val BASE_URL =  "https://hltv-api.vercel.app"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface HltvApiService {
    @GET("api/players.json")
    suspend fun getPlayers(): List<Player>
}

object HltvApi {
    val retrofitService: HltvApiService by lazy {
        retrofit.create(HltvApiService::class.java)
    }
}