package com.example.todoapp.data.repository

import android.util.Log
import com.example.todoapp.data.models.Task
import com.example.todoapp.data.models.Urgency
import com.google.gson.Gson
import kotlin.random.Random

class Repository {
    //private val dao =
    private val tasks = mutableListOf<Task>()
    private var lastId = 0

    init {
        val count = Random.nextInt(5, 30)
        for (i in 0..count) {
            val urgency = Urgency.values()[Random.nextInt(0, 3)]
            val done = if (i % 2 == 0) Random.nextBoolean() else false
            val deadline = if (i % 2 == 1 && Random.nextBoolean()) 1628190449342 else null
            tasks.add(Task(id = i, description = "Random description", urgency = urgency,
                dateCreation = "18:09, 25 December", done = done, deadline = deadline, dateChanged = null))
            lastId = i + 1
        }
    }

    fun getLastId() = lastId

    fun getData(): List<Task> {
        Log.d("Tasks", Gson().toJson(tasks))
        return tasks
    }

    fun getNumDone(): Int {
        return tasks.filter { it.done }.size
    }

    fun update(newTask: Task) {
        for (i in tasks.indices) {
            if (tasks[i].id == newTask.id) {
                tasks[i] = newTask
                break
            }
        }
    }

    fun delete(task: Task) {
        tasks.remove(task)
    }

    fun add(task: Task) {
        tasks.add(task)
        lastId++
    }
}