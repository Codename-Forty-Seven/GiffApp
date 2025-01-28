package com.thegiff.giffapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thegiff.giffapp.databinding.SingleImageItemBinding
import com.thegiff.giffapp.presentation.model.GifEntityUi

class SingleGifAdapter : RecyclerView.Adapter<SingleGifAdapter.SingleGifViewHolder>() {

    private val gifs = mutableListOf<GifEntityUi>()

    fun submitList(newGifs: List<GifEntityUi>) {
        gifs.clear()
        gifs.addAll(newGifs)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleGifViewHolder {
        val binding = SingleImageItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SingleGifViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SingleGifViewHolder, position: Int) {
        holder.bind(gifs[position])
    }

    override fun getItemCount(): Int = gifs.size

    inner class SingleGifViewHolder(private val binding: SingleImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(gif: GifEntityUi) {
            Glide.with(binding.root.context)
                .load(gif.url)
                .into(binding.imgSingle)
        }
    }
}