import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class TaskData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val task: String,
    val priority: Int
)
