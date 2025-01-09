package com.example.marvel.features.characterdetails.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel.databinding.FragmentCategoryExpandedBinding
import com.example.marvel.features.characterdetails.presentation.adapter.CategoryExtendedAdapter
import com.example.marvel.R

class CategoryExpandedFragment : DialogFragment() {
    private var _binding: FragmentCategoryExpandedBinding? = null
    private val binding get() = _binding!!
    lateinit var categoryExpandedAdapter: CategoryExtendedAdapter

    private val args: CategoryExpandedFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryExpandedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogTheme_transparent)
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val width = ViewGroup.LayoutParams.MATCH_PARENT;
            val height = ViewGroup.LayoutParams.MATCH_PARENT;
            it.window?.setLayout(width, height);
        }

    }

    private fun initViews() {
        binding.ivClose.setOnClickListener {
            findNavController().popBackStack()
        }
        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val position =
                        (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() + 1
                    binding.tvCurrentItem.text =
                        "$position/" + (recyclerView.adapter?.itemCount
                            ?: 0).toString()
                }
            }
        }
        binding.rvImages.addOnScrollListener(scrollListener)
        LinearSnapHelper().attachToRecyclerView(binding.rvImages);
        categoryExpandedAdapter = CategoryExtendedAdapter()
        binding.rvImages.adapter = categoryExpandedAdapter
        categoryExpandedAdapter.submitList(args.category.images)
        binding.tvCurrentItem.text = "1/" + (binding.rvImages.adapter?.itemCount ?: 0).toString()
        binding.tvCategoryName.text = args.category.title
    }

}