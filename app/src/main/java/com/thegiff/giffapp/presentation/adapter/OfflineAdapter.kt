package com.thegiff.giffapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thegiff.giffapp.databinding.SingleGifItemBinding
import com.thegiff.giffapp.presentation.model.GifEntityUi
import com.thegiff.giffapp.utils.animateBtn

class OfflineAdapter(
    private val onGifClickDelete: (GifEntityUi) -> Unit
) : RecyclerView.Adapter<OfflineAdapter.GifViewHolder>() {

    private var items: List<GifEntityUi> = emptyList()

    fun submitList(newItems: List<GifEntityUi>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SingleGifItemBinding.inflate(inflater, parent, false)
        return GifViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    inner class GifViewHolder(
        private val binding: SingleGifItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(gif: GifEntityUi) {

            Glide.with(binding.root)
                .asGif()
                .load(gif.url)
                .into(binding.imgSingleGif)

            binding.btnDelete.visibility = VISIBLE
            binding.btnDelete.setOnClickListener {
                binding.btnDelete.startAnimation(animateBtn(binding.root.context))
                onGifClickDelete(gif)
            }
        }
    }
}