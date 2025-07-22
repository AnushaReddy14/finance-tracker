package com.example.financetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.data.repository.TransactionRepository
import com.example.financetracker.domain.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: TransactionRepository
) : ViewModel() {

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        getTransactions()
        refreshTransactions()
    }

    private fun getTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getTransactions()
                    .collect { list ->
                        _transactions.value = list
                    }
            } catch (e: Exception) {
                _error.value = e.message ?: "An unexpected error occurred"
            }
        }
    }

    fun refreshTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                repository.refreshTransactions()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to refresh transactions"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.insertTransaction(transaction)
        }
    }

    fun updateTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.updateTransaction(transaction)
        }
    }

    fun insertOrUpdateTransaction(transaction: Transaction) {
        viewModelScope.launch {
            try {
                repository.insertOrUpdateTransaction(transaction)
                _error.value = null // Clear any error after successful insert/update
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}