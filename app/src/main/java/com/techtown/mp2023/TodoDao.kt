package com.techtown.mp2023

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos")
    fun getAll():Array<Todo>

    @Insert
    fun insert(vararg todo: Todo)

    @Delete
    fun delete(vararg todo:Todo)

    @Update
    fun update(vararg todo:Todo)
}