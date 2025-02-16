package com.example.marvel.features.characterslist.presentation.view

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.example.marvel.core.extensions.collectFlow
import com.example.marvel.core.extensions.collectSharedFlow
import com.example.marvel.core.presentation.base.BaseFragment
import com.example.marvel.databinding.FragmentCharactersListBinding
import com.example.marvel.features.characterslist.presentation.adapter.CharactersAdapter
import com.example.marvel.features.characterslist.presentation.viewmodel.CharacterContract
import com.example.marvel.features.characterslist.presentation.viewmodel.CharacterContract.Event
import com.example.marvel.features.characterslist.presentation.viewmodel.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class CharactersListFragment :
    BaseFragment<FragmentCharactersListBinding>(FragmentCharactersListBinding::inflate) {
    private val viewModel: CharactersViewModel by viewModels()
    lateinit var pagingAdapter: CharactersAdapter

    override fun FragmentCharactersListBinding.initializeUI() {
        pagingAdapter = CharactersAdapter {
            viewModel.setEvent(Event.OpenCharacterDetails(it))
        }
        rvCharacters.adapter = pagingAdapter
        viewModel.setEvent(Event.LoadCharacters)
    }

    override fun handleState() {
        collectFlow(viewModel.state) {
            pagingAdapter.submitData(lifecycle, it.characters ?: PagingData.empty())
        }
    }

    override fun handleSideEffect() {
        collectSharedFlow(viewModel.sideEffect) {
            when (it) {
                is CharacterContract.SideEffect.OpenCharacterDetails -> {
                    findNavController().navigate(
                        CharactersListFragmentDirections.actionCharactersListFragmentToCharacterDetailsFragment(
                            it.character
                        )
                    )
                }
            }

        }
    }

    override fun setUpObservers() {
        collectFlow(pagingAdapter.loadStateFlow) { loadState ->
            binding.pbLoading.isVisible =
                (loadState.refresh is LoadState.Loading) || (loadState.append is LoadState.Loading)
        }
    }

}