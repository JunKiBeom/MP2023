package com.techtown.mp2023

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class Todo (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val task: String
)