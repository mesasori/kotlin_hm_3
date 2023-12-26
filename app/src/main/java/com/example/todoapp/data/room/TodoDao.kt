package com.example.todoapp.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todoList")
    fun getAllFlow(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todoList")
    suspend fun getAll(): List<TodoEntity>

    @Query("SELECT * FROM todoList WHERE id=:id")
    suspend fun getItem(id: String): TodoEntity

    @Query("SELECT * FROM todoList WHERE is_done=1")
    fun getDoneAll(): Flow<List<TodoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todoEntity: TodoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<TodoEntity>)

    @Update
    suspend fun update(todoEntity: TodoEntity)

    @Delete
    suspend fun delete(entity: TodoEntity)

}