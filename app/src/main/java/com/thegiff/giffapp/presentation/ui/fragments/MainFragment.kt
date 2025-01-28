package com.thegiff.giffapp.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.thegiff.giffapp.R
import com.thegiff.giffapp.databinding.FragmentMainBinding
import com.thegiff.giffapp.presentation.adapter.GifAdapter
import com.thegiff.giffapp.presentation.adapter.OfflineAdapter
import com.thegiff.giffapp.presentation.model.GifEntityUi
import com.thegiff.giffapp.presentation.viewModel.GifViewModel
import com.thegiff.giffapp.presentation.viewModel.InternetViewModel
import com.thegiff.giffapp.utils.Constants.main_fragment_tag
import com.thegiff.giffapp.utils.animateBtn
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GifViewModel by activityViewModels()
    private val internetViewModel: InternetViewModel by activityViewModels()
    private lateinit var adapter: GifAdapter
    private lateinit var offlineAdapter: OfflineAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOfflineAdapter()
        setOnlineAdapter()

        lifecycleScope.launch {
            viewModel.gifs.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }

        viewModel.cachedGifs.observe(viewLifecycleOwner) { gifList ->
            if (gifList != null)
                offlineAdapter.submitList(gifList)
        }

        lifecycleScope.launch {
            viewModel.gifs.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }

        internetViewModel.isConnectToInternet.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected != null && !isConnected) {
                binding.llSearch.visibility = INVISIBLE
                binding.rvGifs.visibility = INVISIBLE
                binding.tvNoInternet.visibility = VISIBLE
                binding.rvNoInternet.visibility = VISIBLE
                viewModel.getCachedGifs()
            } else {
                binding.tvNoInternet.visibility = INVISIBLE
                binding.rvNoInternet.visibility = INVISIBLE
                binding.llSearch.visibility = VISIBLE
                binding.rvGifs.visibility = VISIBLE

                lifecycleScope.launch {
                    viewModel.gifs.collectLatest { pagingData ->
                        adapter.submitData(pagingData)
                    }
                }
            }
        }

        binding.btnSearch.setOnClickListener {
            binding.btnSearch.startAnimation(animateBtn(requireContext()))
            val query = binding.edTxtSearch.text.toString().trim()
            if (query.isNotEmpty()) {
                viewModel.searchGifs(query)
            }
        }
    }

    private fun setOnlineAdapter() = with(binding) {
        if (rvGifs.adapter == null) {
            Log.d(main_fragment_tag, "setOnlineAdapter()")
            adapter = GifAdapter(
                onGifClick = { gif -> navigateToDetails(gif) }
            )
            rvGifs.layoutManager = LinearLayoutManager(requireContext())
            rvGifs.adapter = adapter
        }
    }

    private fun setOfflineAdapter() = with(binding) {
        if (rvNoInternet.adapter == null) {
            Log.d(main_fragment_tag, "setOfflineAdapter()")
            offlineAdapter = OfflineAdapter(onGifClickDelete = {
                viewModel.deleteGif(it.id)
            })
            rvNoInternet.layoutManager = LinearLayoutManager(requireContext())
            rvNoInternet.adapter = offlineAdapter
        }
    }

    private fun navigateToDetails(gif: GifEntityUi) {
        Log.d(main_fragment_tag, "navigateToDetails(gif: ${Gson().toJson(gif)})")
        viewModel.selectGif(gif)
        findNavController().navigate(R.id.action_mainFragment_to_singleGifFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}