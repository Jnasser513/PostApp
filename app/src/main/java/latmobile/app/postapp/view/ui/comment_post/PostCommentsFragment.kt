package latmobile.app.postapp.view.ui.comment_post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import latmobile.app.postapp.databinding.PostCommentsFragmentBinding
import latmobile.app.postapp.domain.response.PostCommentsResponse
import latmobile.app.postapp.domain.response.UIStatus
import latmobile.app.postapp.presentation.PostViewModel
import latmobile.app.postapp.view.util.showShortToast

class PostCommentsFragment: Fragment() {

    private var mBinding: PostCommentsFragmentBinding? = null
    private val binding get() = mBinding!!

    private val viewModel: PostViewModel by activityViewModels()

    private val args: PostCommentsFragmentArgs by navArgs()

    private lateinit var postCommentsAdapter: PostCommentsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = PostCommentsFragmentBinding.inflate(inflater, container, false)

        viewModel.getPostComments(args.idpost)
        setUpRecyclerView()

        return binding.root
    }

    private fun setUpRecyclerView() {
        postCommentsAdapter = PostCommentsAdapter()

        with(binding.recyclerview) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = postCommentsAdapter
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
                postCommentsAdapter.filter.filter(query?.lowercase())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // if query text is change in that case we
                // are filtering our adapter with
                // new text on below line.
                postCommentsAdapter.filter.filter(newText?.lowercase())
                return false
            }
        })
    }

    private fun setUpObservers() {
        viewModel.statusGetPostComments.observe(viewLifecycleOwner) { status ->
            handleCommentsStatus(status)
        }
    }

    private fun handleCommentsStatus(status: UIStatus<List<PostCommentsResponse>>?) {
        when(status) {
            is UIStatus.EmptyList -> {
                //it can show an empty list view
                endShowProgressBar()
                postCommentsAdapter.setData(status.data!!)
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
                postCommentsAdapter.setData(status.data!!)
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