package com.example.marvel.features.characterdetails.presentation.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.bumptech.glide.Glide
import com.example.marvel.R
import com.example.marvel.core.extensions.collectFlow
import com.example.marvel.core.extensions.collectSharedFlow
import com.example.marvel.databinding.FragmentCharacterDetailsBinding
import com.example.marvel.features.characterdetails.presentation.adapter.CategoryAdapter
import com.example.marvel.features.characterdetails.presentation.adapter.RelatedLinksAdapter
import com.example.marvel.features.characterdetails.presentation.viewmodel.CharacterDetailsState
import com.example.marvel.features.characterdetails.presentation.viewmodel.CharacterDetailsViewModel
import com.example.marvel.features.characterdetails.presentation.viewmodel.CharactersDetailsSideEffect
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {


    private val viewModel: CharacterDetailsViewModel by viewModels()
    lateinit var comicAdapter: CategoryAdapter
    lateinit var seriesAdapter: CategoryAdapter
    lateinit var storiesAdapter: CategoryAdapter
    lateinit var eventsAdapter: CategoryAdapter
    lateinit var linksAdapter: RelatedLinksAdapter
    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        initListeners()
        viewModel.getAllCategories()

    }

    private fun initListeners() {
        with(binding.detailToolbar) {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun initObservers() {
        prepareRecycleViewsObservers()
        prepareViewStatesObservers()
        prepareSideEffectsObservers()

    }

    private fun prepareViewStatesObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            collectFlow(viewModel.viewState) {
                prepareDetailsView(it)
                submitCategories(it)
            }
        }
    }

    fun prepareDetailsView(it: CharacterDetailsState) {
        with(binding) {
            tvRelatedLinksSection.isVisible =
                it.marvelCharacter.relatedLinks.isNotEmpty()
            rvRelatedLinks.isVisible = it.marvelCharacter.relatedLinks.isNotEmpty()
            tvTile.text = it.marvelCharacter.name
            tvDescription.text = it.marvelCharacter.description
            Glide.with(requireContext()).load(it.marvelCharacter.image).centerCrop()
                .placeholder(R.drawable.ic_place_holder)
                .into(ivCharacterImage)
            toolbarLayout.title = it.marvelCharacter.name
            rvRelatedLinks.isVisible = it.marvelCharacter.relatedLinks.isNotEmpty()
            tvRelatedLinksSection.isVisible = it.marvelCharacter.relatedLinks.isNotEmpty()
            tvDescription.isVisible = it.marvelCharacter.description.isNotEmpty()
            tvDescriptionSection.isVisible = it.marvelCharacter.description.isNotEmpty()
            tvTile.isVisible = it.marvelCharacter.name.isNotEmpty()
            tvTileSection.isVisible = it.marvelCharacter.name.isNotEmpty()

        }

    }

    fun prepareRecycleViewsObservers() {
        lifecycleScope.launch {
            collectFlow(comicAdapter.loadStateFlow) { loadState ->
                binding.pbLoading.isVisible =
                    (loadState.refresh is LoadState.Loading) || (loadState.append is LoadState.Loading)
                binding.rvComics.isVisible = comicAdapter.itemCount != 0
                binding.tvComicSection.isVisible = comicAdapter.itemCount != 0
            }
            collectFlow(seriesAdapter.loadStateFlow) { loadState ->
                binding.pbLoading.isVisible =
                    (loadState.refresh is LoadState.Loading) || (loadState.append is LoadState.Loading)
                binding.rvSeries.isVisible = seriesAdapter.itemCount != 0
                binding.tvSeriesSection.isVisible = seriesAdapter.itemCount != 0
            }
            collectFlow(storiesAdapter.loadStateFlow) { loadState ->
                binding.pbLoading.isVisible =
                    (loadState.refresh is LoadState.Loading) || (loadState.append is LoadState.Loading)
                binding.rvStories.isVisible = storiesAdapter.itemCount != 0
                binding.tvStoriesSection.isVisible = storiesAdapter.itemCount != 0
            }
            collectFlow(eventsAdapter.loadStateFlow) { loadState ->
                binding.pbLoading.isVisible =
                    (loadState.refresh is LoadState.Loading) || (loadState.append is LoadState.Loading)
                binding.rvEvents.isVisible = eventsAdapter.itemCount != 0
                binding.tvEventsSection.isVisible = eventsAdapter.itemCount != 0
            }
        }
    }

    fun submitCategories(it: CharacterDetailsState) {
        comicAdapter.submitData(lifecycle, it.comics ?: PagingData.empty())
        seriesAdapter.submitData(lifecycle, it.series ?: PagingData.empty())
        storiesAdapter.submitData(lifecycle, it.stories ?: PagingData.empty())
        eventsAdapter.submitData(lifecycle, it.events ?: PagingData.empty())
        linksAdapter.submitList(it.marvelCharacter.relatedLinks)
    }

    fun prepareSideEffectsObservers() {
        lifecycleScope.launch {
            collectSharedFlow(viewModel.sideEffect) {
                when (it) {
                    is CharactersDetailsSideEffect.OpenCategoryImages -> {
                        if (it.category.images.isNotEmpty())
                            findNavController().navigate(
                                CharacterDetailsFragmentDirections.actionCharacterDetailsFragmentToCategoryExpandedFragment(
                                    it.category
                                )
                            )
                    }

                    is CharactersDetailsSideEffect.OpenExternalLink -> {
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
                        startActivity(browserIntent)
                    }

                }
            }
        }
    }

    private fun initViews() {
        comicAdapter = CategoryAdapter {
            viewModel.openCategoryImages(it)
        }
        binding.rvComics.adapter = comicAdapter
        seriesAdapter = CategoryAdapter {
            viewModel.openCategoryImages(it)
        }
        binding.rvSeries.adapter = seriesAdapter

        storiesAdapter = CategoryAdapter {
            viewModel.openCategoryImages(it)
        }
        binding.rvStories.adapter = storiesAdapter

        eventsAdapter = CategoryAdapter {
            viewModel.openCategoryImages(it)
        }
        binding.rvEvents.adapter = eventsAdapter

        linksAdapter = RelatedLinksAdapter {
            viewModel.openExternalLink(it)
        }
        binding.rvRelatedLinks.adapter = linksAdapter
        binding.rvRelatedLinks.isNestedScrollingEnabled = false

    }

}