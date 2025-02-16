package com.example.marvel.core.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding>(
    open val bindingInflater: (LayoutInflater) -> VB
) : Fragment() {
    // View Binding Instance
    private var _binding: VB? = null
    open val binding get() = checkNotNull(_binding)

    override fun onResume() {
        super.onResume()
        binding.registerListeners()
    }

    override fun onPause() {
        binding.unRegisterListeners()
        super.onPause()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.initializeUI()

        setUpObservers()

        handleState()

        handleSideEffect()
    }

    open fun VB.registerListeners() {}

    open fun VB.unRegisterListeners() {}

    open fun VB.initializeUI() {}

    open fun setUpObservers() {}

    open fun handleState() {}

    open fun handleSideEffect() {}

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
