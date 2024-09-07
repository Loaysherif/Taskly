package com.example.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.task.databinding.FragmentAddBinding

class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    private val viewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set up the spinner with priority levels
        val priorityLevels = arrayOf(0, 1, 2)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, priorityLevels)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.prioritySpinner.adapter = adapter


        binding.saveButton.setOnClickListener {
            val taskText = binding.taskEditText.text.toString()
            val selectedPriority = binding.prioritySpinner.selectedItemPosition

            if (taskText.isNotBlank()) {
                val newTask = TaskData(taskText, selectedPriority)
                viewModel.addTaskData(newTask)
                Toast.makeText(context, "Task added", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else {
                Toast.makeText(context, "Please fill in the task", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
