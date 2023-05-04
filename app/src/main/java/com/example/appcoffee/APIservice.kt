package com.example.appcoffee

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIservice {
    @GET
    suspend fun getDogsByBreeds(@Url url:String):Response<DogResponse>
}