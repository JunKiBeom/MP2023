package com.techtown.mp2023

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase:RoomDatabase() {
    abstract fun TodoDao(): TodoDao
    companion object{
        @Volatile
        private var Instance:TodoDatabase? = null
        fun getInstance(context: Context):TodoDatabase{
            return Instance?: synchronized(this) {
                Instance?: Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java, "todo_database"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}