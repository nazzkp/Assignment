package com.example.assignment.data.remote

import MedicineResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("v3/40ec0fdd-f824-41dc-be03-25f826a53c5f")
    suspend fun fetchDoseDetails(): Response<MedicineResponse?>
}