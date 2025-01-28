package com.thegiff.giffapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View.INVISIBLE
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thegiff.giffapp.databinding.SingleGifItemBinding
import com.thegiff.giffapp.presentation.model.GifEntityUi
import com.thegiff.giffapp.utils.animateBtn

class GifAdapter(
    private val onGifClick: (GifEntityUi) -> Unit
) : PagingDataAdapter<GifEntityUi, GifAdapter.GifViewHolder>(GIF_COMPARATOR) {

    companion object {
        private val GIF_COMPARATOR = object : DiffUtil.ItemCallback<GifEntityUi>() {
            override fun areItemsTheSame(oldItem: GifEntityUi, newItem: GifEntityUi): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GifEntityUi, newItem: GifEntityUi): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val gif = getItem(position)
        gif?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val binding = SingleGifItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GifViewHolder(binding)
    }

    inner class GifViewHolder(
        private val binding: SingleGifItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(gif: GifEntityUi) {
            Glide.with(binding.root)
                .asGif()
                .load(gif.url)
                .into(binding.imgSingleGif)

            binding.btnDelete.visibility = INVISIBLE
            binding.root.setOnClickListener {
                binding.root.startAnimation(animateBtn(binding.root.context))
                onGifClick(gif)
            }
        }
    }
}