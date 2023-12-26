package com.example.todoapp.data.repository

import android.util.Log
import com.example.todoapp.data.models.Task
import com.example.todoapp.data.models.Urgency
import com.example.todoapp.data.room.TodoDatabase
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.random.Random

class Repository(db: TodoDatabase) {

    private val dao = db.listDao
    private var lastId = -1

    suspend fun getData(): List<Task> {
        val list = dao.getAllFlow().map { it.fromEntity() }
        if (lastId == -1) {
            for (i in list) lastId = maxOf(lastId, i.id)
            lastId++
        }
        return list
    }


    suspend fun getDoneData(): List<Task> =
        dao.getDoneAll().map { it.fromEntity() }


    suspend fun getNumDone(): Int {
        return dao.getDoneAll().size
    }

    suspend fun update(newTask: Task) {
        dao.update(newTask.fromTaskToEntity())
    }

    suspend fun add(task: Task) {
        lastId = task.id + 1
        dao.insert(task.fromTaskToEntity())
    }

    suspend fun delete(task: Task) {
        dao.delete(task.fromTaskToEntity())
    }
}