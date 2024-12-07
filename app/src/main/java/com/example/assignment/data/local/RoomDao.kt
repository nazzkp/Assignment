package com.example.assignment.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.assignment.model.dataClass.DrugItem

@Dao
interface RoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProblems(problems: List<DrugItem>)

    @Query("SELECT * FROM Drug_Item")
    fun getAllData(): List<DrugItem>
}