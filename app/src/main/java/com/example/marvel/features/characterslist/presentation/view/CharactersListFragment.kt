package com.example.marvel.features.characterslist.presentation.view

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
import com.example.marvel.core.extensions.collectFlow
import com.example.marvel.core.extensions.collectSharedFlow
import com.example.marvel.databinding.FragmentCharactersListBinding
import com.example.marvel.features.characterslist.presentation.adapter.CharactersAdapter
import com.example.marvel.features.characterslist.presentation.viewmodel.CharacterContract
import com.example.marvel.features.characterslist.presentation.viewmodel.CharacterContract.Event
import com.example.marvel.features.characterslist.presentation.viewmodel.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class CharactersListFragment : Fragment() {
    private val viewModel: CharactersViewModel by viewModels()
    lateinit var pagingAdapter: CharactersAdapter
    private var _binding: FragmentCharactersListBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharactersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        viewModel.setEvent(Event.LoadCharacters)
    }

    private fun initObservers() {
        lifecycleScope.apply {
            launch {
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
            launch {
                collectFlow(viewModel.state) {
                    pagingAdapter.submitData(lifecycle, it.characters ?: PagingData.empty())
                }
            }
            launch {
                collectFlow(pagingAdapter.loadStateFlow) { loadState ->
                    binding.pbLoading.isVisible =
                        (loadState.refresh is LoadState.Loading) || (loadState.append is LoadState.Loading)
                }
            }
        }

    }

    private fun initViews() {
        pagingAdapter = CharactersAdapter {
            viewModel.setEvent(Event.OpenCharacterDetails(it))
        }
        binding.rvCharacters.adapter = pagingAdapter
    }

}