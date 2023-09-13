package com.example.todoapp.data.models

import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Date

data class TaskItem(
    var id: String,
    var description: String,
    var urgency: Urgency,
    var deadline: Long?,
    var done: Boolean,
    var dataCreation: Long,
    var dataChanged: String?
) {
    fun deadlineToString(): String? {
        if (deadline != null) {
            val date = Date(deadline!!)
            val format = SimpleDateFormat("dd MM YYYY")
            return format.format(date)
        }
        return null
    }

    override fun toString(): String {
        return Gson().toJson(this)
    }

    override fun equals(other: Any?): Boolean {
        if (other is TaskItem) {
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