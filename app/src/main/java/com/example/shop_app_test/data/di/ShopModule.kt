package com.example.shop_app_test.data.di

import com.example.shop_app_test.data.remote.ShopApiService
import com.example.shop_app_test.data.repository.ShopRepositoryImpl
import com.example.shop_app_test.data.room.ProductDao
import com.example.shop_app_test.domain.repository.ShopRepository
import com.example.shop_app_test.domain.usecase.ShopUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ShopModule {

    @Provides
    fun provideApiService(retrofit: Retrofit):ShopApiService{
        return retrofit.create(ShopApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(apiService: ShopApiService, productDao: ProductDao):ShopRepository{
        return ShopRepositoryImpl(apiService, productDao)
    }

    @Provides
    @Singleton
    fun provideUseCase(repository: ShopRepository): ShopUseCase {
        return ShopUseCase(repository)
    }
}