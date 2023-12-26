package com.example.todoapp.utils

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.models.Task
import com.example.todoapp.data.models.Urgency

class TasksAdapter(
    val onItemListener: OwnItemClickListener
): RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    var mTaskList = mutableListOf<Task>()

    fun setData(data: List<Task>) {
        val callback = DiffUtilCallbackImpl(
            oldItems = mTaskList,
            newItems = data
        )
        val diffResult = DiffUtil.calculateDiff(callback)
        mTaskList.clear()
        mTaskList.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val checkBox: CheckBox = itemView.findViewById(R.id.task_checkBox)
        private val taskInfo: ImageView = itemView.findViewById(R.id.btn_taskInfo)
        private val taskUrgency: ImageView = itemView.findViewById(R.id.iv_taskUrgency)
        private val taskDescription: TextView = itemView.findViewById(R.id.tv_taskDescription)
        private val taskDeadline: TextView = itemView.findViewById(R.id.tv_taskDeadline)

        fun onBind(task: Task, position: Int) {
            if (position == 0) itemView.background = ResourcesCompat.getDrawable(itemView.resources, R.drawable.rounded_top_task, null)
            if (position == mTaskList.size - 1) itemView.background = ResourcesCompat.getDrawable(itemView.resources, R.drawable.rounded_bottom_task, null)
            //Description
            val context = itemView.context
            taskDescription.text = task.description

            //Deadline
            if (task.deadline != null) {
                taskDeadline.text = Task.deadlineToString(task.deadline)
                taskDeadline.visibility = View.VISIBLE
            } else taskDeadline.visibility = View.GONE

            //Task status
            when (task.done) {
                true -> taskDone(context, checkBox, taskUrgency, taskDescription)
                false -> taskNotDone(context, checkBox, taskUrgency, taskDescription, task)
            }

            checkBox.setOnClickListener {
                when(task.done) {
                    true -> taskNotDone(context, checkBox, taskUrgency, taskDescription, task)

                    false -> taskDone(context, checkBox, taskUrgency, taskDescription)
                }
                onItemListener.onCheckClick(task)
            }

            taskInfo.setOnClickListener {
                Toast.makeText(context, "Creation date: " + task.dateCreation, Toast.LENGTH_SHORT).show()
            }

            itemView.setOnClickListener {
                onItemListener.onItemClick(task)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            layoutInflater.inflate(
                R.layout.task_view,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = mTaskList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(mTaskList[position], position)
    }

    private fun taskDone(
        context: Context,
        checkBox: CheckBox,
        taskUrgency: ImageView,
        taskDescription: TextView
    ) {
        //CheckBox stage
        checkBox.isChecked = true
        checkBox.buttonTintList = AppCompatResources.getColorStateList(context,
            R.color.green)

        //Description state
        taskDescription.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        taskDescription.setTextColor(AppCompatResources.getColorStateList(
            context, R.color.label_tertiary))

        //Urgency
        taskUrgency.visibility = View.GONE
    }

    private fun taskNotDone(
        context: Context,
        checkBox: CheckBox,
        taskUrgency: ImageView,
        taskDescription: TextView,
        task: Task
    ) {
        //Description state
        taskDescription.paintFlags = Paint.ANTI_ALIAS_FLAG
        taskDescription.setTextColor(AppCompatResources.getColorStateList(
            context, R.color.label_primary))

        //CheckBox
        checkBox.isChecked = false
        checkBox.buttonTintList = AppCompatResources.getColorStateList(context,
            R.color.support_separator)

        //Urgency
        when (task.urgency) {
            Urgency.NONE -> {
                taskUrgency.visibility = View.GONE
                checkBox.buttonTintList = AppCompatResources.getColorStateList(context,
                    R.color.support_separator)
            }

            Urgency.LOW -> {
                taskUrgency.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_low))
                taskUrgency.visibility = View.VISIBLE
                checkBox.buttonTintList = AppCompatResources.getColorStateList(context,
                    R.color.support_separator)
            }

            Urgency.HIGH -> {
                taskUrgency.setImageDrawable(AppCompatResources.getDrawable(context,
                    R.drawable.ic_urgency))
                taskUrgency.visibility = View.VISIBLE
                checkBox.buttonTintList = AppCompatResources.getColorStateList(context,
                    R.color.red)
            }
        }
    }
    interface OwnItemClickListener {
        fun onItemClick(task: Task)

        fun onCheckClick(task: Task)
    }
}