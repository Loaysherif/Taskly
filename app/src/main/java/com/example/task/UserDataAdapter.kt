import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task.databinding.ItemLayoutBinding
class UserDataAdapter(
    private var taskDataList: MutableList<TaskData>,
    private val onItemEdit: (TaskData) -> Unit,
    private val onItemDelete: (TaskData) -> Unit,
    ) : RecyclerView.Adapter<UserDataAdapter.UserDataViewHolder>() {
    inner class UserDataViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(taskData: TaskData) {
            Log.d("UserDataAdapter", "Binding data: $taskData")

            binding.taskData

            binding.editTask.setOnClickListener {
                Log.d("UserDataAdapter", "Edit button clicked for user: $taskData")
                onItemEdit(taskData)

            }

            binding.deleteTask.setOnClickListener {
                val user = taskDataList[adapterPosition]
                Log.d("UserDataAdapter", "Delete button clicked for user: $user")
                onItemDelete(user)
            }
            Log.d("UserDataAdapter", "Priority Indicator: ${binding.priorityIndicator}")
            when (taskData.priority) {
                0 -> binding.priorityIndicator.setBackgroundColor(Color.RED) // Urgent
                1 -> binding.priorityIndicator.setBackgroundColor(Color.YELLOW) // Important
                2 -> binding.priorityIndicator.setBackgroundColor(Color.GREEN) // Normal
            }
//            when (taskData.priority) {
//                0 -> binding.priorityIndicator.setBackgroundColor(
//                    ContextCompat.getColor(binding.root.context,
//                    R.color.urgentColor)) // Urgent
//                1 -> binding.priorityIndicator.setBackgroundColor(
//                    ContextCompat.getColor(binding.root.context,
//                    R.color.importantColor)) // Important
//                2 -> binding.priorityIndicator.setBackgroundColor(
//                    ContextCompat.getColor(binding.root.context,
//                    R.color.normalColor)) // Normal
//            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDataViewHolder {
        Log.d("UserDataAdapter", "onCreateViewHolder called")
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserDataViewHolder, position: Int) {
        Log.d("UserDataAdapter", "onBindViewHolder called for position: $position")
        holder.bindData(taskDataList[position])

    }

    override fun getItemCount(): Int = taskDataList.size

    fun updateData(newUserList: MutableList<TaskData>) {
        taskDataList = newUserList ?: mutableListOf()
        notifyDataSetChanged()
    }


}