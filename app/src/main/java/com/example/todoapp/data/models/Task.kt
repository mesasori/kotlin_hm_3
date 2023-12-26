package com.example.todoapp.data.models

import com.google.gson.Gson

data class Task(
    val id: Int,
    var description: String,
    var urgency: Urgency,
    var done: Boolean,
    val dataCreation: String,
    var deadline: String?,
    var dataChanged: String?
) {

    override fun toString(): String {
        return Gson().toJson(this)
    }

    override fun equals(other: Any?): Boolean {
        if (other is Task) {
            return id == other.id && description == other.description &&
                    urgency == other.urgency && deadline == other.deadline &&
                    done == other.done && dataCreation == other.dataCreation && dataChanged == other.dataChanged
        }
        return false
    }
}

enum class Urgency {
    NONE, LOW, HIGH
}