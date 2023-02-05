package latmobile.app.postapp.view.ui.comment_post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import latmobile.app.postapp.databinding.PostCommentsFragmentBinding
import latmobile.app.postapp.presentation.PostViewModel

class PostCommentsFragment: Fragment() {

    private var mBinding: PostCommentsFragmentBinding? = null
    private val binding get() = mBinding!!

    private val viewModel: PostViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = PostCommentsFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

}