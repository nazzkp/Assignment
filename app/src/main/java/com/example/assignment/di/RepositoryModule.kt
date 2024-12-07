package com.example.assignment.di

import com.example.assignment.data.repository.DoseRepositoryImpl
import com.example.assignment.model.DoseRepository
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
    abstract fun bindMyRepository(
        doseRepositoryImpl: DoseRepositoryImpl
    ): DoseRepository
}