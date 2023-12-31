package com.example.todoapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.todoapp.data.models.Task

class DiffUtilCallbackImpl(
    private val oldItems: List<Task>,
    private val newItems: List<Task>
): DiffUtil.Callback() {
    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return oldItem.done == newItem.done && oldItem.urgency == newItem.urgency &&
                oldItem.deadline == newItem.deadline && oldItem.description == newItem.description
    }
}