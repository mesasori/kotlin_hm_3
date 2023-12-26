package com.example.todoapp.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.data.models.Task
import com.example.todoapp.data.models.Urgency
import com.example.todoapp.databinding.FragmentNewTaskBinding
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.util.Calendar

class NewTaskFragment : Fragment() {
    private lateinit var binding: FragmentNewTaskBinding
    private val model by lazy { MainViewModel(requireContext()) }

    private var task: Task? = null
    private var newUrgency: Urgency = Urgency.NONE
    private var newDeadline: Long? = null
    private var newDescription: String? = null

    private lateinit var popupMenu : PopupMenu
    private lateinit var timePickerDialog : DatePickerDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentNewTaskBinding.inflate(layoutInflater)

        arguments?.let {
            val taskJSON = it.getString(TasksFragment.BUNDLE_KEY)
            if (taskJSON != null) {
                task = Gson().fromJson(taskJSON, Task::class.java)
                newUrgency = task!!.urgency
                newDeadline = task!!.deadline
                newDescription = task!!.description
            } else {
                newUrgency = Urgency.NONE
                newDeadline = null
                newDescription = null
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            model.getList(false)
        }
        createPopupMenu()
        setUpViews()
    }

    private fun setUpViews() {
        val myCalendar = Calendar.getInstance()
        timePickerDialog = DatePickerDialog(requireContext(),
            R.style.DatePickerStyle,
            { view, year, month, day ->
                binding.tvDate.visibility = View.VISIBLE
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                newDeadline = myCalendar.timeInMillis
                binding.tvDate.text = Task.deadlineToString(newDeadline)
            }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH))

        if (task == null) {
            binding.tvDate.visibility = View.GONE
            binding.tvDelete.setTextColor(resources.getColor(R.color.label_disable))
            binding.editTodo.setTextColor(AppCompatResources.getColorStateList(requireContext(),
                R.color.label_primary))
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



        timePickerDialog.setOnCancelListener {
            if(binding.tvDate.visibility == View.GONE){
                binding.switchCompat.isChecked = false
            }
        }

        if (task!!.deadline != null) {
            binding.tvDate.text = Task.deadlineToString(task!!.deadline)
            binding.switchCompat.isChecked = true
        } else binding.tvDate.visibility = View.GONE
    }

    private fun setUpListeners() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_newTaskFragment_to_tasksFragment)
        }

        binding.toolbar.menu[0].setOnMenuItemClickListener {
            if (task == null) saveTask()
            else updateTask()
            true
        }

        binding.chooseImportance.setOnClickListener {
            popupMenu.show()
        }

        binding.chooseDate.setOnClickListener {
            openDatePicker()
        }

        binding.switchCompat.setOnCheckedChangeListener { compoundButton, checked ->
            if (checked) openDatePicker()
            else {
                binding.tvDate.visibility = View.GONE
                newDeadline = null
            }
        }

        binding.tvDelete.setOnClickListener {
            if (task != null) {
                lifecycleScope.launch {
                    model.deleteItem(task!!)
                }
                findNavController().navigate(R.id.action_newTaskFragment_to_tasksFragment)
            }
            else Toast.makeText(context, resources.getText(R.string.delete_task), Toast.LENGTH_SHORT).show()


        }
    }

    private fun openDatePicker() {
        binding.switchCompat.isChecked = true
        timePickerDialog.show()
    }

    private fun saveTask() {
        if (binding.editTodo.text.toString() == "") Toast.makeText(context, resources.getText(R.string.empty_description), Toast.LENGTH_SHORT).show()
        else {
            lifecycleScope.launch {
                val newTask = Task(
                    model.getLastId(), binding.editTodo.text.toString(),
                    newUrgency, false, Calendar.getInstance().time.toString(), newDeadline, null
                )
                model.addItem(newTask)
                Log.d("NEW_TASK", newTask.id.toString())
            }
            findNavController().navigate(R.id.action_newTaskFragment_to_tasksFragment)

        }
    }

    private fun updateTask() {
        if (binding.editTodo.text.toString() == "") Toast.makeText(context, resources.getText(R.string.empty_description), Toast.LENGTH_SHORT).show()
        else {
            task!!.let {
                it.urgency = newUrgency
                it.deadline = newDeadline
                it.description = binding.editTodo.text.toString()
                it.dateChanged = Calendar.getInstance().time.toString()
            }
            lifecycleScope.launch {
                model.updateItem(task!!)
            }

            findNavController().navigate(R.id.action_newTaskFragment_to_tasksFragment)
        }
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