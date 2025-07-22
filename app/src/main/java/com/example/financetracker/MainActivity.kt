package com.example.financetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.financetracker.presentation.screens.AddEditTransactionScreen
import com.example.financetracker.presentation.screens.TransactionListScreen
import com.example.financetracker.presentation.viewmodel.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.example.financetracker.ui.FinanceTrackerTheme
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: TransactionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinanceTrackerTheme {
                val navController = rememberNavController()

                NavHost(navController, startDestination = "transaction_list") {
                    composable("transaction_list") {
                        TransactionListScreen(
                            viewModel = viewModel,
                            onAddClick = {
                                navController.navigate("add_edit_transaction")
                            }
                        )
                    }
                    composable("add_edit_transaction") {
                        AddEditTransactionScreen(
                            onSave = { transaction ->
                                viewModel.insertOrUpdateTransaction(transaction)
                                navController.popBackStack() // Go back to list after save
                            }
                        )
                    }
                }
            }
        }
    }
}
