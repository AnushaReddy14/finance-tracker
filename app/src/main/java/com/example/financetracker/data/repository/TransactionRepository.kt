package com.example.financetracker.data.repository

import com.example.financetracker.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    // Get transactions from DB then update from API
    fun getTransactions(): Flow<List<Transaction>>

    // Refresh data from API
    suspend fun refreshTransactions()

    suspend fun insertTransaction(transaction: Transaction)
    suspend fun updateTransaction(transaction: Transaction)

    suspend fun insertOrUpdateTransaction(transaction: Transaction)

}