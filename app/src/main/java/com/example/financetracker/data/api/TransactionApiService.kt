package com.example.financetracker.data.api

import com.example.financetracker.data.model.TransactionDto
import retrofit2.http.GET

interface TransactionApiService {

    @GET("3fda71b9-21e8-483f-bff7-131fc887daf0")
    suspend fun getTransactions(): List<TransactionDto>
}