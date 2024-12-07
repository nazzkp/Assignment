package com.example.assignment.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.assignment.dB.AssignmentDB
import com.example.assignment.data.local.RoomDao
import com.example.assignment.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://run.mocky.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AssignmentDB =
        Room.databaseBuilder(app, AssignmentDB::class.java, "my_database").build()

    @Provides
    fun provideMyDao(database: AssignmentDB): RoomDao = database.myDao()

    @Provides
    fun provideAppContext(@ApplicationContext context: Context): Context = context
}