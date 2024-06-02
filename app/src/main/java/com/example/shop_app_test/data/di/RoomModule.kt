package com.example.shop_app_test.data.di

import android.content.Context
import androidx.room.Room
import com.example.shop_app_test.data.room.AppDB
import com.example.shop_app_test.data.room.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDB {
        return Room.databaseBuilder(context, AppDB::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideProductDao(appDatabase: AppDB): ProductDao {
        return appDatabase.productDao()
    }

}