package latmobile.app.postapp.framework.databasemanager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import latmobile.app.postapp.framework.databasemanager.dao.PostImageDao
import latmobile.app.postapp.framework.databasemanager.entity.PostImageEntity

@Database(entities = [PostImageEntity::class], version = 1)
abstract class PostDatabase: RoomDatabase() {
    abstract fun postImageDao(): PostImageDao

    companion object {
        @Volatile
        private var INSTANCE: PostDatabase? = null
        fun getDatabase(context: Context) = INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context,
                PostDatabase::class.java,
                "post_db"
            ).fallbackToDestructiveMigration().build()

            INSTANCE = instance
            instance
        }
    }

}