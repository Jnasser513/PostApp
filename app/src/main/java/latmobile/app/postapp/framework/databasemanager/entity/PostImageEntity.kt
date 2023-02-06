package latmobile.app.postapp.framework.databasemanager.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post_table")
data class PostImageEntity(
    @PrimaryKey
    val id: Int,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)