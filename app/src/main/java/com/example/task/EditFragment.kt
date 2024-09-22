package com.example.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.task.databinding.FragmentEditBinding

class EditFragment : Fragment() {
    private lateinit var binding: FragmentEditBinding
    private val viewModel: UserViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.let {
            val task = it.getString("task") ?: ""

            binding.taskEditText.setText(task)
        }

        binding.saveButton.setOnClickListener {
            val updatedTask = binding.taskEditText.text.toString()
            val userId = arguments?.getInt("id") ?: return@setOnClickListener


            if (updatedTask.isNotBlank() ) {
                val updatedTaskData = TaskData( task =  updatedTask, id =userId)
                viewModel.updateTaskData(updatedTaskData)
                Toast.makeText(context, "Task updated", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else {
                Toast.makeText(context, "Please fill in the task", Toast.LENGTH_SHORT).show()
            }
        }

    }
}