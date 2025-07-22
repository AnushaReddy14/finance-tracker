package com.example.financetracker.presentation.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.financetracker.domain.model.Transaction
import java.util.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import android.widget.DatePicker

@Composable
fun AddEditTransactionScreen(
    initialTransaction: Transaction? = null,
    onSave: (Transaction) -> Unit
) {
    var merchant by remember { mutableStateOf(initialTransaction?.merchant ?: "") }
    var amount by remember { mutableStateOf(initialTransaction?.amount?.toString() ?: "") }
    var category by remember { mutableStateOf(initialTransaction?.category ?: "") }
    var date by remember { mutableStateOf(initialTransaction?.date ?: "") }

    val context = LocalContext.current

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            // Combine date with default time
            val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
            val dateTime = LocalDateTime.of(selectedDate, LocalTime.of(14, 10, 0)) // 2:10 PM

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            date = dateTime.format(formatter)
        },
        LocalDate.now().year,
        LocalDate.now().monthValue - 1,
        LocalDate.now().dayOfMonth
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        TextField(
            value = merchant,
            onValueChange = { merchant = it },
            label = { Text("Merchant") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Category") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() }
                .padding(vertical = 8.dp)
        ) {
            TextField(
                value = date,
                onValueChange = {},
                label = { Text("Date") },
                enabled = false, // Disable editing
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (merchant.isNotBlank() && amount.isNotBlank() && category.isNotBlank() && date.isNotBlank()) {
                    val transaction = Transaction(
                        id = initialTransaction?.id ?: UUID.randomUUID().toString(),
                        merchant = merchant,
                        amount = amount.toDoubleOrNull() ?: 0.0,
                        category = category,
                        date = date
                    )
                    onSave(transaction)
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Save Transaction")
        }
    }
}
