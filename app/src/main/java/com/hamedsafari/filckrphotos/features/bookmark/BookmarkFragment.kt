package com.hamedsafari.filckrphotos.features.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamedsafari.filckrphotos.MainApplication
import com.hamedsafari.filckrphotos.R
import com.hamedsafari.filckrphotos.databinding.FragmentBookmarkBinding
import com.hamedsafari.filckrphotos.features.search.adapter.PhotosAdapter
import com.hamedsafari.filckrphotos.utils.KEY_IMAGE_ID
import com.hamedsafari.filckrphotos.utils.KEY_IMAGE_TITLE
import com.hamedsafari.filckrphotos.utils.KEY_IMAGE_URL
import com.hamedsafari.filckrphotos.utils.KEY_THUMBNAIL_IMAGE_URL
import com.hamedsafari.filckrphotos.utils.collectLifecycleFlow

class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    private lateinit var photoAdapter: PhotosAdapter

    private val viewModel by viewModels<BookmarkViewModel> {
        val appContainer = (activity?.application as MainApplication).appContainer
        appContainer.bookmarkViewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {

        photoAdapter = PhotosAdapter {
            findNavController().navigate(
                R.id.action_to_DetailFragment,
                bundleOf(
                    KEY_IMAGE_ID to it.id,
                    KEY_IMAGE_TITLE to it.title,
                    KEY_IMAGE_URL to it.image_url,
                    KEY_THUMBNAIL_IMAGE_URL to it.thumbnail_url
                )
            )
        }

        binding.recyclerView.apply {
            adapter = photoAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    private fun setupObservers() {

        collectLifecycleFlow(viewModel.uiState) { state ->
            when (state) {
                is BookmarksUiState.Bookmarks -> {
                    binding.progress.isVisible = false
                    photoAdapter.submitList(state.photos)
                }
                BookmarksUiState.Empty -> {
                    binding.progress.isVisible = false
                }
                BookmarksUiState.Loading -> {
                    binding.progress.isVisible = true
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
