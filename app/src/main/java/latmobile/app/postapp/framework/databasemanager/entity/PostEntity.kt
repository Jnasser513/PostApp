package latmobile.app.postapp.framework.databasemanager.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post")
data class PostEntity(
    @PrimaryKey
    val id: Int,
)