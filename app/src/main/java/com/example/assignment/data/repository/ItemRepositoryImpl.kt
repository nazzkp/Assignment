package com.example.assignment.data.repository

import MedicineResponse
import android.app.Application
import androidx.lifecycle.LiveData
import com.example.assignment.data.local.RoomDao
import com.example.assignment.data.remote.ApiService
import com.example.assignment.model.DoseRepository
import com.example.assignment.model.dataClass.DrugItem
import javax.inject.Inject

class DoseRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val dao: RoomDao,
    private val appContext: Application
): DoseRepository {
    override suspend fun insertProblems(problems: List<DrugItem>) {
        dao.insertProblems(problems)
    }

    override suspend fun fetchDoseDetails(): MedicineResponse? {
        val response = api.fetchDoseDetails()
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    override suspend fun fetchDoseDetailsLocal(): List<DrugItem> {
      return  dao.getAllData()
    }
}