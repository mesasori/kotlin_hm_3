package com.example.todoapp

import android.app.Application
import com.example.todoapp.data.TaskService

class App: Application() {
    val taskService = TaskService()
}