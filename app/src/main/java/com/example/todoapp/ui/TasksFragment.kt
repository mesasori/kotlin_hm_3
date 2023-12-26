package com.example.todoapp.ui

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.models.Task
import com.example.todoapp.data.repository.Repository
import com.example.todoapp.databinding.FragmentTasksBinding
import com.example.todoapp.utils.TasksAdapter
import com.example.todoapp.utils.TasksDividerItemDecoration
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

class TasksFragment : Fragment() {

    private lateinit var binding: FragmentTasksBinding
    private lateinit var adapter: TasksAdapter
    private val model: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentTasksBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = binding.root

        setUpViewModel()
        setupViews()

        return root
    }

    private fun setUpViewModel() {
        model.getList(false)

        model.numberOfDone.observe(viewLifecycleOwner, Observer {
            binding.numberDone.text = "${resources.getText(R.string.done)} $it"
        })

        model.list.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })
    }

    private fun setupViews() {
        binding.btnNewTask.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_tasksFragment_to_newTaskFragment)
        }

        adapter = TasksAdapter(object : TasksAdapter.OwnItemClickListener {
            override fun onItemClick(task: Task) {
                val bundle = Bundle()
                bundle.putString(Companion.BUNDLE_KEY, Gson().toJson(task))
                findNavController().navigate(R.id.action_tasksFragment_to_newTaskFragment, bundle)
            }

            override fun onCheckClick(task: Task) {
                task.done = !task.done
                model.updateItem(task)
            }

        })
        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(TasksDividerItemDecoration(
            ResourcesCompat.getDrawable(resources, R.drawable.recycler_view_divider, null)!!,
            bottomOffset = 6f.toPx.toInt(),
            topOffset = 6f.toPx.toInt()
        ))


    }

    private val Number.toPx
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            Resources.getSystem().displayMetrics)

    companion object {
        const val BUNDLE_KEY = "object_task"
    }
}

/*private val touchCallback = object:
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val itemPosition = viewHolder.adapterPosition

            when (direction) {
                ItemTouchHelper.RIGHT -> {
                    Log.d("item to save", "$itemPosition")
                    val savedTask = adapter.mTaskList[itemPosition]
                    savedTask.done = !savedTask.done
                    adapter.updateRow(itemPosition, savedTask)
                    repository.update(itemPosition, savedTask)
                }

                ItemTouchHelper.LEFT -> {
                    Log.d("item to delete", "$itemPosition")
                    val deletedTask = adapter.mTaskList[itemPosition]
                    adapter.deleteRow(deletedTask)
                    repository.removeAt(itemPosition)
                    Snackbar.make(recyclerView, deletedTask.description, Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.undo), View.OnClickListener {
                            adapter.addRow(position = itemPosition, task = deletedTask)
                            repository.add(position = itemPosition, task = deletedTask)
                        })
                        .show()

                    //adapter.tasks = listOf()
                    //adapter.tasks = deleteTask(adapter.tasks, itemPosition)
                }
            }
        }

    }*/