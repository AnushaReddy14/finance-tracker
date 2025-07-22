package com.example.financetracker.data.model

data class TransactionDto(
    val id: String,
    val merchant: String,
    val amount: Double,
    val category: String,
    val date: String
)
