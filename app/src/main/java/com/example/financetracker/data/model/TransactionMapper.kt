package com.example.financetracker.data.model

import com.example.financetracker.data.db.TransactionEntity
import com.example.financetracker.domain.model.Transaction

// DTO -> Domain
fun TransactionDto.toDomain(): Transaction {
    return Transaction(
        id = id,
        merchant = merchant,
        amount = amount,
        category = category,
        date = date
    )
}

// Entity -> Domain
fun TransactionEntity.toDomain(): Transaction {
    return Transaction(
        id = id,
        merchant = merchant,
        amount = amount,
        category = category,
        date = date
    )
}

// Domain -> Entity
fun TransactionDto.toEntity(): TransactionEntity {
    return TransactionEntity(
        id = this.id,
        merchant = this.merchant,
        amount = this.amount,
        category = this.category,
        date = this.date
    )
}
