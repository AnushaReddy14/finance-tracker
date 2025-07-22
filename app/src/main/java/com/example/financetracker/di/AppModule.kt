package com.example.financetracker.di

import android.content.Context
import androidx.room.Room
import com.example.financetracker.data.api.RetrofitInstance
import com.example.financetracker.data.api.TransactionApiService
import com.example.financetracker.data.db.TransactionDao
import com.example.financetracker.data.db.TransactionDatabase
import com.example.financetracker.data.repository.TransactionRepository
import com.example.financetracker.data.repository.TransactionRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTransactionApiService(): TransactionApiService {
        return RetrofitInstance.api
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): TransactionDatabase {
        return Room.databaseBuilder(
            appContext,
            TransactionDatabase::class.java,
            "transaction_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTransactionDao(db: TransactionDatabase): TransactionDao {
        return db.transactionDao()
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(
        api: TransactionApiService,
        dao: TransactionDao
    ): TransactionRepository {
        return TransactionRepositoryImpl(api, dao)
    }
}
