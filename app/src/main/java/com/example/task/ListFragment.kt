package com.example.task

import UserDataAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
                val position = viewModel.taskDataList.value?.indexOf(userItem) ?: -1
                findNavController().navigate(
                    R.id.editFragment,
                    bundleOf(
                        "task" to userItem.task, // Pass task text
                        "id" to userItem.id, // Pass task id
                        "priority" to userItem.priority // Pass task priority

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
        } else {
            binding.emptyTextView.visibility = View.GONE
            binding.mylist.visibility = View.VISIBLE
        }
    }
}
