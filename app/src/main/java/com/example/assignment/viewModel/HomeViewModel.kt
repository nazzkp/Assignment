package com.example.assignment.viewModel

import MedicineResponse
import Problem
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.model.DoseRepository
import com.example.assignment.model.dataClass.DrugItem
import com.example.orderit.Utils.Utilities
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: DoseRepository,
) : ViewModel() {

    private val _medicineData = MutableLiveData<List<DrugItem>?>()
    val medicineData: LiveData<List<DrugItem>?> = _medicineData

    private val _operationStatus = MutableStateFlow("")
    val operationStatus: StateFlow<String> = _operationStatus

    fun getDoseDetails() {
        viewModelScope.launch {
            try {
                if (Utilities.isInternetAvailable(context)) {
                    val response = repository.fetchDoseDetails()
                    _medicineData.postValue(makeDrugList(response!!))
                    launch(Dispatchers.IO) {
                        repository.insertProblems(makeDrugList(response))
                    }
                    if (response.problems.isEmpty()) {
                        _operationStatus.value = "NO DATA FOUND"
                    }
                } else {
                    launch(Dispatchers.IO) {
                        val response = repository.fetchDoseDetailsLocal()
                        _medicineData.postValue(response)
                        if (response.isEmpty()) {
                            _operationStatus.value = "NO DATA FOUND.TRY AFTER GETTING CONNECTION"
                        }
                    }
                }
            } catch (e: Exception) {
                _operationStatus.value = "SOME ERROR OCCURRED"
            }
        }
    }

    private fun makeDrugList(value: MedicineResponse): List<DrugItem> {

        val drugList = ArrayList<DrugItem>()

        for (problem in value.problems) {
            if (problem.details?.medications != null) {
                for (medications in problem.details.medications) {
                    for (medicationClass in medications.medicationClasses!!) {
                        for (drug in medicationClass.associatedDrugs!!) {
                            val drugItem = DrugItem()
                            drugItem.problem = problem.name
                            drugItem.className = medicationClass.className.toString()
                            drugItem.name = problem.name
                            drugItem.name = drug.name.toString()
                            drugItem.dose = drug.dose
                            drugItem.strength = drug.strength
                            drugList.add(drugItem)
                        }
                    }
                }
            }
        }
        return drugList
    }

    /**
     *With the provided api response, I noticed some inconsistencies
    that I believed could impact the overall functionality of the project.
    To ensure that my submission accurately reflects my skills and understanding,
    I made some adjustments to the response format.
    "https://run.mocky.io/v3/40ec0fdd-f824-41dc-be03-25f826a53c5f"
    This is the new api response link.
     **/

}