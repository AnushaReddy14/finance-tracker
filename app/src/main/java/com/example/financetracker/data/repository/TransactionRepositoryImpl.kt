package com.example.financetracker.data.repository

import com.example.financetracker.data.api.TransactionApiService
import com.example.financetracker.data.db.TransactionDao
import com.example.financetracker.data.model.toDomain
import com.example.financetracker.data.model.toEntity
import com.example.financetracker.domain.model.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepositoryImpl(
    private val api: TransactionApiService,
    private val dao: TransactionDao
) : TransactionRepository {

    override fun getTransactions(): Flow<List<Transaction>> {
        return dao.getAllTransactionsFlow().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun refreshTransactions() {
        // Fetch from API
        //val transactionsFromApi = api.getTransactions() // List<TransactionDto>

        // Convert to domain model
        val transactions = api.getTransactions().map {
           it.toEntity()
        }

//        // Convert to entity
//        val entities = transactions.map { it.toEntity() }

        // Save to db
        dao.clearAll()
        dao.insertTransactions(transactions)
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        dao.insertTransaction(transaction.toEntity())
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        dao.updateTransaction(transaction.toEntity())
    }

    override suspend fun insertOrUpdateTransaction(transaction: Transaction) {
        val entity = transaction.toEntity()
        dao.insertTransaction(entity)
    }
}