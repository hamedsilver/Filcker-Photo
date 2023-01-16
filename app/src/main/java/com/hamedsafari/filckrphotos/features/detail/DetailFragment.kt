package com.hamedsafari.filckrphotos.features.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hamedsafari.filckrphotos.MainApplication
import com.hamedsafari.filckrphotos.R
import com.hamedsafari.filckrphotos.databinding.FragmentDetailBinding
import com.hamedsafari.filckrphotos.domain.Photo
import com.hamedsafari.filckrphotos.utils.ImageLoader
import com.hamedsafari.filckrphotos.utils.KEY_IMAGE_ID
import com.hamedsafari.filckrphotos.utils.KEY_IMAGE_URL
import com.hamedsafari.filckrphotos.utils.collectLifecycleFlow

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var id: String
    private lateinit var imageUrl: String

    private val viewModel by viewModels<DetailViewModel> {
        val appContainer = (activity?.application as MainApplication).appContainer
        id = arguments?.getString(KEY_IMAGE_ID).orEmpty()
        appContainer.detailViewModelFactory(id)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageUrl = arguments?.getString(KEY_IMAGE_URL).orEmpty()
        ImageLoader.loadImage(binding.image, imageUrl)
        setupObservers()
    }

    private fun setupObservers() {

        collectLifecycleFlow(viewModel.uiState) { state ->
            when (state) {
                is BookmarkUiState.Ideal -> {
                    binding.likeButton.setImageResource(
                        if (state.isBookmarked)
                            R.drawable.ic_baseline_favorite_24
                        else R.drawable.ic_baseline_favorite_border_24
                    )

                    binding.likeButton.setOnClickListener {
                        viewModel.toggleBookmark(
                            Photo(
                                id = id, image_url = imageUrl, title = "",
                                thumbnail_url = ""
                            ), state.isBookmarked
                        )
                    }
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}