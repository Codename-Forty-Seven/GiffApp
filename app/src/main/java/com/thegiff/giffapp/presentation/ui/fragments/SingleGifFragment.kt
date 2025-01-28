package com.thegiff.giffapp.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.thegiff.giffapp.R
import com.thegiff.giffapp.databinding.FragmentSingleGifBinding
import com.thegiff.giffapp.presentation.adapter.SingleGifAdapter
import com.thegiff.giffapp.presentation.viewModel.GifViewModel

class SingleGifFragment : Fragment() {
    private var _singleBinding: FragmentSingleGifBinding? = null
    private val singleBinding get() = _singleBinding!!

    private val viewModel: GifViewModel by activityViewModels()
    private lateinit var adapter: SingleGifAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCachedGifs()

        viewModel.cachedGifs.observe(viewLifecycleOwner) { cachedGifs ->
            adapter.submitList(cachedGifs)
            viewModel.selectedGif.value?.let { selectedGif ->
                val startIndex = cachedGifs.indexOfFirst { it.id == selectedGif.id }
                if (startIndex != -1) {
                    singleBinding.viewPager.setCurrentItem(startIndex, false)
                }
            }
        }

        setupViewPager()
    }

    private fun setupViewPager() =with(singleBinding){
        adapter = SingleGifAdapter()
        viewPager.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _singleBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_single_gif, container, false)
        return singleBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _singleBinding = null
    }
}