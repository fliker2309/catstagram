package com.example.androidtask5network.presetnation.ui.catstagram.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.androidtask5network.R
import com.example.androidtask5network.data.model.Cat
import com.example.androidtask5network.databinding.RecyclerCatItemBinding

class CatstagramViewHolder(
     val binding: RecyclerCatItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(cat: Cat) {
        with(binding) {
            profileIV.load(R.drawable.kat) {
                placeholder(R.drawable.kat)
                error(R.drawable.kat)
                crossfade(true)
                crossfade(500)
            }
            mainIV.load(cat.url) {
                placeholder(R.drawable.kat)
                error(R.drawable.ic_error)
                crossfade(true)
                crossfade(500)
                transformations(RoundedCornersTransformation(20f))
            }
            profileNameTV.text = cat.id
        }
    }

    object CatstagramDiffItemCallback : DiffUtil.ItemCallback<Cat>() {
        override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean {
            return oldItem.id == newItem.id && oldItem.url == newItem.url
        }
    }
}
