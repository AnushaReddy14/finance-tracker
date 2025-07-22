package com.example.financetracker.domain.model

data class Transaction(
    val id: String,
    val merchant: String,
    val amount: Double,
    val category: String,
    val date: String
)

