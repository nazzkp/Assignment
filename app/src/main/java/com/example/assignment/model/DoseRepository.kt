package com.example.assignment.model

import MedicineResponse
import androidx.lifecycle.LiveData
import com.example.assignment.model.dataClass.DrugItem

interface DoseRepository {
    suspend fun insertProblems(problems : List<DrugItem>)
    suspend fun fetchDoseDetails() : MedicineResponse?
    suspend fun fetchDoseDetailsLocal() : List<DrugItem>
}