package com.example.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.task.databinding.FragmentEditBinding

class EditFragment : Fragment() {
    private lateinit var binding: FragmentEditBinding
    private val viewModel: UserViewModel by activityViewModels()
    private var position = -1 // Initialize to -1 to handle invalid positions


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set up the spinner with priority levels
        val priorityLevels = arrayOf(0,1,2)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, priorityLevels)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.prioritySpinner.adapter = adapter

        arguments?.let {
            val task = it.getString("task") ?: ""
            position = it.getInt("position", -1)
            val priority = it.getInt("priority", 0)
            binding.taskEditText.setText(task)
            binding.prioritySpinner.setSelection(priority)
        }

        binding.saveButton.setOnClickListener {
            val updatedTask = binding.taskEditText.text.toString()
            val selectedPriority = binding.prioritySpinner.selectedItemPosition

            if (updatedTask.isNotBlank() && position != -1) {
                val updatedTaskData = TaskData(selectedPriority, updatedTask, position)
                viewModel.updateTaskData(updatedTaskData)
                Toast.makeText(context, "Task updated", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else {
                Toast.makeText(context, "Please fill in the task", Toast.LENGTH_SHORT).show()
            }
        }

    }
}