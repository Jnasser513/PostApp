package latmobile.app.postapp.view.ui.image_post

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import latmobile.app.postapp.databinding.ItemPostImageBinding
import latmobile.app.postapp.domain.response.PostImageResponse
import java.lang.Exception
import java.net.URL
import java.util.concurrent.Executors

class PostImagesAdapter: ListAdapter<PostImageResponse, PostImagesAdapter.PostImagesViewHolder>(ITEM_COMPARATOR()) {

    inner class PostImagesViewHolder(private val binding: ItemPostImageBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(image: PostImageResponse) {
            chargeImage(image.thumbnailUrl, binding.image)
        }
    }

    class ITEM_COMPARATOR : DiffUtil.ItemCallback<PostImageResponse>() {
        override fun areItemsTheSame(oldItem: PostImageResponse, newItem: PostImageResponse) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PostImageResponse, newItem: PostImageResponse) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostImagesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPostImageBinding.inflate(inflater, parent, false)
        return PostImagesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostImagesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun chargeImage(url: String, image: ImageView) {
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        var image1: Bitmap?

        executor.execute {
            try {
                val stream = URL(url).openStream()
                image1 = BitmapFactory.decodeStream(stream)

                handler.post {
                    image.setImageBitmap(image1)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}