package com.example.androidtask5network.presetnation.ui.mainfragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.androidtask5network.R
import com.example.androidtask5network.feature_catlist.data.model.Cat
import com.example.androidtask5network.databinding.RecyclerCatItemBinding

class CatsAdapter(private val cardListener: (Cat) -> Unit) :
    PagingDataAdapter<Cat, CatsAdapter.CatsViewHolder>(CatDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatsViewHolder {
        val viewHolder =
            CatsViewHolder(RecyclerCatItemBinding.inflate(LayoutInflater.from(parent.context)))
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition
            getItem(position)?.let { position -> cardListener(position) }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: CatsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class CatsViewHolder(private val binding: RecyclerCatItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cat: Cat) {
            with(binding) {
                mainIV.load(cat.url) {
                    placeholder(R.drawable.ic_placeholder)
                    error(R.drawable.ic_error)
                    crossfade(true)
                    crossfade(500)
                    transformations(RoundedCornersTransformation(20f))
                }
            }
        }
    }

    private object CatDiffItemCallback : DiffUtil.ItemCallback<Cat>() {
        override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean {
            return oldItem.id == newItem.id && oldItem.url == newItem.url
        }
    }
}
