package com.example.assignment.dB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.assignment.data.local.RoomDao
import com.example.assignment.model.dataClass.DrugItem

@Database(entities = [DrugItem::class], version = 1, exportSchema = false)
abstract class AssignmentDB : RoomDatabase() {
    abstract fun myDao(): RoomDao
}