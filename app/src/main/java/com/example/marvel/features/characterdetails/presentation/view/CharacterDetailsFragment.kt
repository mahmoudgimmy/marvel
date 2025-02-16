package com.example.marvel.features.characterdetails.presentation.view

import android.content.Intent
import android.net.Uri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.bumptech.glide.Glide
import com.example.marvel.R
import com.example.marvel.core.extensions.collectFlow
import com.example.marvel.core.extensions.collectSharedFlow
import com.example.marvel.core.presentation.base.BaseFragment
import com.example.marvel.databinding.FragmentCharacterDetailsBinding
import com.example.marvel.features.characterdetails.presentation.adapter.CategoryAdapter
import com.example.marvel.features.characterdetails.presentation.adapter.RelatedLinksAdapter
import com.example.marvel.features.characterdetails.presentation.viewmodel.CharacterDetailsContract
import com.example.marvel.features.characterdetails.presentation.viewmodel.CharacterDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class CharacterDetailsFragment :
    BaseFragment<FragmentCharacterDetailsBinding>(FragmentCharacterDetailsBinding::inflate) {


    private val viewModel: CharacterDetailsViewModel by viewModels()
    lateinit var comicAdapter: CategoryAdapter
    lateinit var seriesAdapter: CategoryAdapter
    lateinit var storiesAdapter: CategoryAdapter
    lateinit var eventsAdapter: CategoryAdapter
    lateinit var linksAdapter: RelatedLinksAdapter

    override fun FragmentCharacterDetailsBinding.initializeUI() {
        comicAdapter = CategoryAdapter {
            viewModel.setEvent(CharacterDetailsContract.Event.OpenCategoryImages(it))
        }
        rvComics.adapter = comicAdapter
        seriesAdapter = CategoryAdapter {
            viewModel.setEvent(CharacterDetailsContract.Event.OpenCategoryImages(it))
        }
        rvSeries.adapter = seriesAdapter

        storiesAdapter = CategoryAdapter {
            viewModel.setEvent(CharacterDetailsContract.Event.OpenCategoryImages(it))
        }
        rvStories.adapter = storiesAdapter

        eventsAdapter = CategoryAdapter {
            viewModel.setEvent(CharacterDetailsContract.Event.OpenCategoryImages(it))
        }
        rvEvents.adapter = eventsAdapter

        linksAdapter = RelatedLinksAdapter {
            viewModel.setEvent(CharacterDetailsContract.Event.OpenExternalLink(it))
        }
        rvRelatedLinks.adapter = linksAdapter
        rvRelatedLinks.isNestedScrollingEnabled = false

        viewModel.setEvent(CharacterDetailsContract.Event.LoadCategories)
    }

    override fun FragmentCharacterDetailsBinding.registerListeners() {
        with(detailToolbar) {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun handleState() {
        collectFlow(viewModel.state) {
            prepareDetailsView(it)
            submitCategories(it)
        }
    }

    override fun handleSideEffect() {
        collectSharedFlow(viewModel.sideEffect) {
            when (it) {
                is CharacterDetailsContract.SideEffect.OpenCategoryImages -> {
                    if (it.category.images.isNotEmpty())
                        findNavController().navigate(
                            CharacterDetailsFragmentDirections.actionCharacterDetailsFragmentToCategoryExpandedFragment(
                                it.category
                            )
                        )
                }

                is CharacterDetailsContract.SideEffect.OpenExternalLink -> {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
                    startActivity(browserIntent)
                }

            }
        }
    }

    fun prepareDetailsView(it: CharacterDetailsContract.State) {
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

    fun submitCategories(it: CharacterDetailsContract.State) {
        comicAdapter.submitData(lifecycle, it.comics ?: PagingData.empty())
        seriesAdapter.submitData(lifecycle, it.series ?: PagingData.empty())
        storiesAdapter.submitData(lifecycle, it.stories ?: PagingData.empty())
        eventsAdapter.submitData(lifecycle, it.events ?: PagingData.empty())
        linksAdapter.submitList(it.marvelCharacter.relatedLinks)
    }

    override fun setUpObservers() {
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