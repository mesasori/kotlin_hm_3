package com.example.todoapp.data.models

import com.example.todoapp.data.room.TodoEntity
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Date

data class Task(
    val id: Int,
    var description: String,
    var urgency: Urgency,
    var done: Boolean,
    val dateCreation: String,
    var deadline: Long?,
    var dateChanged: String?
) {

    override fun toString(): String {
        return Gson().toJson(this)
    }

    override fun equals(other: Any?): Boolean {
        if (other is Task) {
            return id == other.id && description == other.description &&
                    urgency == other.urgency && deadline == other.deadline &&
                    done == other.done && dateCreation == other.dateCreation && dateChanged == other.dateChanged
        }
        return false
    }

    fun fromTaskToEntity(): TodoEntity = TodoEntity(
        id, description, urgency, done, dateCreation, deadline, dateChanged
    )

    companion object {
        fun deadlineToString(deadline: Long?): String? {
            if(deadline != null) {
                val date = Date(deadline)
                return SimpleDateFormat("dd MMMM YYYY").format(date)
            }
            return null
        }
    }
}

enum class Urgency {
    NONE, LOW, HIGH
}