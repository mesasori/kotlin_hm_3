package com.example.todoapp.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.data.models.Task
import com.example.todoapp.data.models.Urgency
import com.example.todoapp.databinding.FragmentNewTaskBinding
import com.google.gson.Gson

class NewTaskFragment : Fragment() {
    private lateinit var binding: FragmentNewTaskBinding
    private val model: MainViewModel by activityViewModels()
    private var task: Task? = null
    private var newUrgency = Urgency.NONE

    private lateinit var popupMenu : PopupMenu
    private lateinit var timePickerDialog : DatePickerDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentNewTaskBinding.inflate(layoutInflater)

        arguments?.let {
            val taskJSON = it.getString(TasksFragment.BUNDLE_KEY)
            if (taskJSON != null) task = Gson().fromJson(taskJSON, Task::class.java)
        }

        Log.d("BUNDLE", task.toString())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = binding.root

        createPopupMenu()
        setUpViews()

        return root
    }

    private fun setUpViews() {
        if (task == null) {
            binding.tvDate.visibility = View.GONE
            binding.tvDelete.setTextColor(resources.getColor(R.color.label_disable))
        }
        else taskView()

        setUpListeners()
    }

    private fun taskView() {
        binding.tvDate.visibility = View.VISIBLE
        binding.tvDelete.setTextColor(resources.getColor(R.color.red))

        TextViewCompat.setCompoundDrawableTintList(
            binding.tvDelete, AppCompatResources.getColorStateList(
                requireContext(),
                R.color.red
            )
        )

        binding.editTodo.setText(task!!.description)
        binding.editTodo.setTextColor(AppCompatResources.getColorStateList(
            requireContext(),
            R.color.label_primary
        ))

        when (task!!.urgency) {
            Urgency.NONE -> binding.tvUrgency.text = resources.getText(R.string.none)
            Urgency.LOW -> binding.tvUrgency.text = resources.getText(R.string.low)
            Urgency.HIGH -> {
                binding.tvUrgency.setTextColor(AppCompatResources.getColorStateList(
                    requireContext(),
                        R.color.red))

                binding.tvUrgency.text = resources.getText(R.string.high)
            }
        }

        if (task!!.deadline != null) {
            binding.tvDate.text = task!!.deadline
            binding.switchCompat.performClick()
        }
    }

    private fun setUpListeners() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_newTaskFragment_to_tasksFragment)
        }

        binding.toolbar.menu[0].setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener {
            if (task == null) saveNewTask()
            else updateTask()
        })

        binding.chooseImportance.setOnClickListener {
            popupMenu.show()
        }
    }

    private fun saveNewTask() {
        //TODO
    }

    private fun updateTask() {
        //TODO
    }

    private fun createPopupMenu() {
        popupMenu = PopupMenu(context, binding.tvUrgency)
        popupMenu.menuInflater.inflate(R.menu.popup_urgency_menu, popupMenu.menu)

        //example
        val highElement: MenuItem = popupMenu.menu.getItem(2)
        val s = SpannableString("!! Высокая")
        s.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.red)), 0, s.length, 0)
        highElement.title = s

        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.item_none -> {
                    binding.tvUrgency.text = resources.getText(R.string.none)
                    binding.tvUrgency.setTextColor(ContextCompat.getColor(requireContext(),
                        R.color.label_tertiary
                    ))
                    newUrgency = Urgency.NONE
                    return@setOnMenuItemClickListener true
                }
                R.id.item_low -> {
                    binding.tvUrgency.text = resources.getText(R.string.low)
                    binding.tvUrgency.setTextColor(ContextCompat.getColor(requireContext(),
                        R.color.label_tertiary
                    ))
                    newUrgency = Urgency.LOW
                    return@setOnMenuItemClickListener true
                }
                R.id.item_high -> {
                    binding.tvUrgency.text = resources.getText(R.string.high)
                    binding.tvUrgency.setTextColor(ContextCompat.getColor(requireContext(),
                        R.color.red
                    ))
                    newUrgency = Urgency.HIGH
                    return@setOnMenuItemClickListener true
                }
            }
            return@setOnMenuItemClickListener false
        }
    }


}