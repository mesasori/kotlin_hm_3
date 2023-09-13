package com.example.todoapp.ui

import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.App
import com.example.todoapp.R
import com.example.todoapp.data.TaskService
import com.example.todoapp.utils.TasksAdapter
import com.example.todoapp.utils.TasksDividerItemDecoration
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TasksFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var btn_newTask: FloatingActionButton

    //Recycler
    private lateinit var recyclerView: RecyclerView

    //Repo
    private val taskService: TaskService = TaskService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_newTask = view.findViewById(R.id.btn_newTask)
        btn_newTask.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_tasksFragment_to_newTaskFragment)
        }

        //RecyclerView init
        recyclerView = view.findViewById(R.id.recyclerView)
        val adapter = TasksAdapter()
        val layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        adapter.tasks = taskService.getData()

        //RecyclerView itemDecoration
        recyclerView.addItemDecoration(TasksDividerItemDecoration(
            ResourcesCompat.getDrawable(resources, R.drawable.recycler_view_divider, null)!!,
            bottomOffset = 8f.toPx.toInt(),
            topOffset = 8f.toPx.toInt()
            ))


    }

    val Number.toPx
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        )

    /*companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TasksFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }*/
}