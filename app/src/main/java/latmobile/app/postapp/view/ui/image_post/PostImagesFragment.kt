package latmobile.app.postapp.view.ui.image_post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import latmobile.app.postapp.databinding.PostImagesFragmentBinding
import latmobile.app.postapp.domain.response.PostImageResponse
import latmobile.app.postapp.domain.response.PostResponse
import latmobile.app.postapp.domain.response.UIStatus
import latmobile.app.postapp.presentation.PostViewModel
import latmobile.app.postapp.view.util.showShortToast

class PostImagesFragment: Fragment() {

    private var mBinding: PostImagesFragmentBinding? = null
    private val binding get() = mBinding!!

    private val viewModel: PostViewModel by activityViewModels()

    private val args: PostImagesFragmentArgs by navArgs()

    private val postImagesAdapter = PostImagesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = PostImagesFragmentBinding.inflate(inflater, container, false)

        viewModel.getPostImages(args.idpost)
        setUpRecyclerView()

        return binding.root
    }

    private fun setUpRecyclerView() {
        with(binding.recyclerview) {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = postImagesAdapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()

        setUpObservers()
    }

    private fun setUpListeners() {
        binding.btnReturn.setOnClickListener {
            val direction = PostImagesFragmentDirections
                .actionPostImagesFragmentToPostsFragment()
            findNavController().navigate(direction)
        }
    }

    private fun setUpObservers() {
        viewModel.statusGetPostImages.observe(viewLifecycleOwner) { status ->
            handleGetPostImageState(status)
        }
    }

    private fun handleGetPostImageState(status: UIStatus<List<PostImageResponse>>?) {
        when(status) {
            is UIStatus.EmptyList -> {
                endShowProgressBar()
                postImagesAdapter.submitList(status.data!!)
            }
            is UIStatus.Error -> {
                endShowProgressBar()
                requireContext().showShortToast(status.exception.message.toString())
            }
            is UIStatus.ErrorWithMessage -> {
                endShowProgressBar()
                requireContext().showShortToast(status.message)
            }
            is UIStatus.Loading -> {
                showProgressBar()
            }
            is UIStatus.Success -> {
                endShowProgressBar()
                postImagesAdapter.submitList(status.data!!)
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