package latmobile.app.postapp.framework.databasemanager.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import latmobile.app.postapp.framework.databasemanager.entity.PostImageEntity

@Dao
interface PostImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPostImages(images: List<PostImageEntity>)

    @Query("SELECT * FROM post_table WHERE albumId = :idpost")
    fun searchImages(idpost: Int): List<PostImageEntity>

}