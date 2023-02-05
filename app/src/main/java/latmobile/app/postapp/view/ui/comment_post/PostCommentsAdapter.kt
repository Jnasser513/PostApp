package latmobile.app.postapp.view.ui.comment_post

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import latmobile.app.postapp.databinding.ItemPostCommentLeftBinding
import latmobile.app.postapp.databinding.ItemPostCommentRightBinding
import latmobile.app.postapp.domain.response.PostCommentsResponse
import latmobile.app.postapp.domain.response.PostResponse

class PostCommentsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private var commentsList: ArrayList<PostCommentsResponse> = ArrayList()
    var listFiltered: ArrayList<PostCommentsResponse> = ArrayList()

    private val RIGHT = 0
    private val LEFT = 1

    inner class PostCommentsRightViewHolder(private val binding: ItemPostCommentRightBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: PostCommentsResponse) {
            binding.comment = comment
            binding.executePendingBindings()
        }
    }

    inner class PostCommentsLeftViewHolder(private val binding: ItemPostCommentLeftBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: PostCommentsResponse) {
            binding.comment = comment
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            RIGHT -> {
                val binding = ItemPostCommentRightBinding.inflate(inflater, parent, false)
                PostCommentsRightViewHolder(binding)
            }
            else -> {
                val binding = ItemPostCommentLeftBinding.inflate(inflater, parent, false)
                PostCommentsLeftViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if ((listFiltered[position].id % 2) == 0) {
            RIGHT
        } else {
            LEFT
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = listFiltered[position]
        when (holder.itemViewType) {
            RIGHT -> {
                val vh1 = holder as PostCommentsRightViewHolder
                vh1.bind(item)
            }
            else -> {
                val vh2 = holder as PostCommentsLeftViewHolder
                vh2.bind(item)
            }
        }
    }

    override fun getItemCount() = listFiltered.size

    fun setData(list: List<PostCommentsResponse>) {
        commentsList = list as ArrayList<PostCommentsResponse>
        listFiltered = commentsList
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                listFiltered = if (charString.isEmpty()) commentsList else {
                    val filteredList = ArrayList<PostCommentsResponse>()
                    commentsList
                        .filter {
                            (it.body.lowercase().contains(constraint!!))
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
                    results.values as ArrayList<PostCommentsResponse>
                notifyDataSetChanged()
            }
        }
    }

}