
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.task.TaskData
import com.example.task.databinding.ItemLayoutBinding

class UserDataAdapter(
    private var taskDataList: List<TaskData>,
    private val onItemEdit: (TaskData) -> Unit,
    private val onItemDelete: (TaskData) -> Unit,
) : RecyclerView.Adapter<UserDataAdapter.UserDataViewHolder>() {

    inner class UserDataViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(taskData: TaskData) {
            binding.taskData = taskData

            binding.editTask.setOnClickListener {
                onItemEdit(taskData)
            }

            binding.deleteTask.setOnClickListener {
                onItemDelete(taskData)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDataViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserDataViewHolder, position: Int) {
        holder.bindData(taskDataList[position])
    }

    override fun getItemCount(): Int = taskDataList.size

    // Use DiffUtil to update the list efficiently
    fun updateData(newTaskList: List<TaskData>) {
        val diffCallback = TaskDiffCallback(taskDataList, newTaskList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        taskDataList = newTaskList
        diffResult.dispatchUpdatesTo(this)
    }
}

