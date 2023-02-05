package latmobile.app.postapp.view.ui.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import latmobile.app.postapp.databinding.ItemPostBinding
import latmobile.app.postapp.domain.response.PostResponse

class PostsAdapter(
    private val onImageClick: (Int) -> Unit,
    private val onCommentClick: (Int) -> Unit
) : RecyclerView.Adapter<PostsAdapter.PostsViewHolder>(), Filterable {

    private var postList: ArrayList<PostResponse> = ArrayList()
    var listFiltered: ArrayList<PostResponse> = ArrayList()

    inner class PostsViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: PostResponse) {
            binding.post = post
            binding.executePendingBindings()

            binding.btnImage.setOnClickListener {
                onImageClick(post.id)
            }

            binding.btnComments.setOnClickListener {
                onCommentClick(post.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPostBinding.inflate(inflater, parent, false)
        return PostsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val post = listFiltered[position]
        holder.bind(post)
    }

    override fun getItemCount() = listFiltered.size

    fun setData(list: List<PostResponse>) {
        postList = list as ArrayList<PostResponse>
        listFiltered = postList
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                listFiltered = if (charString.isEmpty()) postList else {
                    val filteredList = ArrayList<PostResponse>()
                    postList
                        .filter {
                            (it.title.lowercase().contains(constraint!!))
                        }
                        .forEach { filteredList.add(it) }
                    filteredList

                }
                return FilterResults().apply { values = listFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                listFiltered = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<PostResponse>
                notifyDataSetChanged()
            }
        }
    }

}