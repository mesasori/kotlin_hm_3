package com.example.todoapp.data.repository

import android.util.Log
import com.example.todoapp.data.models.Task
import com.example.todoapp.data.models.Urgency
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlin.random.Random

class Repository {
    //private val dao =
    private val tasks = mutableListOf<Task>()

    init {
        val count = Random.nextInt(5, 30)
        for (i in 0..count) {
            val urgency = Urgency.values()[Random.nextInt(0, 3)]
            val done = if (i % 2 == 0) Random.nextBoolean() else false
            val deadline = if (i % 2 == 1 && Random.nextBoolean()) "${i + 1} декабря" else null
            tasks.add(Task(id = i, description = "Random description", urgency = urgency,
                dataCreation = "18:09, 25 December", done = done, deadline = deadline, dataChanged = null))
        }
    }

    fun getData(): List<Task> {
        Log.d("Tasks", Gson().toJson(tasks))
        return tasks
    }

    fun getNumDone(): Int {
        return tasks.filter { it.done }.size
    }

    fun update(newTask: Task) {
        tasks[newTask.id] = newTask
    }

    fun add(task: Task) {
        tasks.add(task)
    }
}