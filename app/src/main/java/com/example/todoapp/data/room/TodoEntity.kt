package com.example.todoapp.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoapp.data.models.Urgency

@Entity(tableName = "todoList")
data class TodoEntity (
    @PrimaryKey val id: Int,
    var description: String,
    var urgency: Urgency,
    @ColumnInfo(name = "is_done") var done: Boolean,
    @ColumnInfo(name = "date_creation") val dateCreation: String,
    var deadline: Long?,
    @ColumnInfo(name = "date_changed") var dateChanged: String?
)