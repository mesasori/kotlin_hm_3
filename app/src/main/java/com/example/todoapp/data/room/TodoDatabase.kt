package com.example.todoapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TodoEntity::class], version = 1)
abstract class TodoDatabase: RoomDatabase() {
    abstract val listDao: TodoDao

    companion object {
        fun create(context: Context) = Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            "todoList_database"
        ).fallbackToDestructiveMigration().build()
    }
}