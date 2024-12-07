package com.example.assignment.model.dataClass

import androidx.room.Entity

@Entity(tableName = "Drug_Item",
    primaryKeys = ["problem", "className", "name"])
data class DrugItem(
    var problem: String = "",
    var className: String = "",
    var name: String = "",
    var dose: String? = null,
    var strength: String? = null
)
