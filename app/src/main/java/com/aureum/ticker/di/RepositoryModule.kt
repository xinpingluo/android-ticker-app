package com.aureum.ticker.di

import com.aureum.ticker.data.repository.TickerRepository
import com.aureum.ticker.data.repository.TickerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTickerRepository(impl: TickerRepositoryImpl): TickerRepository
}
