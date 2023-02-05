package latmobile.app.postapp.view.ui.posts

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import latmobile.app.postapp.databinding.PostsFragmentBinding
import latmobile.app.postapp.domain.response.PostResponse
import latmobile.app.postapp.domain.response.UIStatus
import latmobile.app.postapp.presentation.PostViewModel
import latmobile.app.postapp.view.util.showShortToast

class PostsFragment: Fragment() {

    private var mBinding: PostsFragmentBinding? = null
    private val binding get() = mBinding!!

    private val viewModel: PostViewModel by activityViewModels()

    private lateinit var postAdapter: PostsAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity?.finish()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = PostsFragmentBinding.inflate(inflater, container, false)

        viewModel.getPosts()
        setUpRecyclerView()

        return binding.root
    }

    private fun setUpRecyclerView() {
        postAdapter = PostsAdapter({ imageClick ->
            val direction = PostsFragmentDirections
                .actionPostsFragmentToPostImagesFragment(imageClick)
            findNavController().navigate(direction)
        }, { commentClick ->

        })

        with(binding.recyclerview) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = postAdapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
        setUpObservers()
    }

    private fun setUpListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getPosts()
            binding.searchView.setQuery("", false)
            binding.swipeRefresh.isRefreshing = false
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // on below line we are checking
                // if query exist or not.
                postAdapter.filter.filter(query?.lowercase())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // if query text is change in that case we
                // are filtering our adapter with
                // new text on below line.
                postAdapter.filter.filter(newText?.lowercase())
                return false
            }
        })
    }

    private fun setUpObservers() {
        viewModel.statusGetPosts.observe(viewLifecycleOwner) { status ->
            handleGetPostState(status)
        }
    }

    private fun handleGetPostState(status: UIStatus<List<PostResponse>>?) {
        when(status) {
            is UIStatus.EmptyList -> {
                //it can show an empty list view
                endShowProgressBar()
                postAdapter.setData(status.data!!)
            }
            is UIStatus.Error -> {
                //It can capture any exception and make an action with that
                endShowProgressBar()
                requireContext().showShortToast(status.exception.message.toString())
            }
            is UIStatus.ErrorWithMessage -> {
                endShowProgressBar()
                requireContext().showShortToast(status.message)
            }
            is UIStatus.Loading -> showProgressBar()
            is UIStatus.Success -> {
                endShowProgressBar()
                postAdapter.setData(status.data!!)
            }
        }
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun endShowProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

}