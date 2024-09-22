package com.example.task

import UserDataAdapter
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task.databinding.FragmentListBinding

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private val viewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val adapter = UserDataAdapter(
            viewModel.taskDataList.value ?: mutableListOf(),
            { userItem ->
                findNavController().navigate(
                    R.id.editFragment,
                    bundleOf(
                        "task" to userItem.task, // Pass task text
                        "id" to userItem.id, // Pass task id

                    )
                )
            },

            // Handle item deletion
            { userItem ->
                viewModel.deleteTaskData(userItem)
            }
        )

        binding.mylist.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }

        // Observe data from ViewModel
        viewModel.taskDataList.observe(viewLifecycleOwner) { taskList ->
            adapter.updateData(taskList)
            checkIfListIsEmpty()
        }

        // Handle FAB click to navigate to AddFragment
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        // Check if list is empty initially
        checkIfListIsEmpty()
    }

    private fun checkIfListIsEmpty() {
        if (viewModel.taskDataList.value.isNullOrEmpty()) {
            binding.emptyTextView.visibility = View.VISIBLE
            binding.mylist.visibility = View.GONE

            // Set text color based on the theme
            val currentNightMode = AppCompatDelegate.getDefaultNightMode()
            if (currentNightMode == AppCompatDelegate.MODE_NIGHT_YES || currentNightMode == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM && isDarkTheme()) {
                binding.emptyTextView.setTextColor(Color.WHITE) // White text for dark theme
            } else {
                binding.emptyTextView.setTextColor(Color.BLACK) // Black text for light theme
            }
        } else {
            binding.emptyTextView.visibility = View.GONE
            binding.mylist.visibility = View.VISIBLE
        }
    }

    // Helper function to check if the current theme is dark
    private fun isDarkTheme(): Boolean {
        return (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }
}
