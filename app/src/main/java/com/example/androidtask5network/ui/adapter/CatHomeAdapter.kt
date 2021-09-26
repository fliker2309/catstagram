package com.example.androidtask5network.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.androidtask5network.R
import com.example.androidtask5network.data.model.Cat
import com.example.androidtask5network.databinding.RecyclerItemBinding

class CatHomeAdapter : PagingDataAdapter<Cat, CatHomeAdapter.CatsViewHolder>(CatDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerItemBinding.inflate(inflater, parent, false)
        return CatsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class CatsViewHolder(private val binding: RecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cat: Cat?) {
            with(binding) {
                catImage.load(cat?.urlToImage) {
                    placeholder(R.drawable.ic_placeholder)
                    error(R.drawable.ic_placeholder)
                    //transformations(CircleCropTransformation())
                }
            }
        }
    }

    private object CatDiffItemCallback : DiffUtil.ItemCallback<Cat>() {
        override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean {
            return oldItem.id == newItem.id && oldItem.urlToImage == newItem.urlToImage
        }

    }


}