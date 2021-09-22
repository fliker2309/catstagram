package com.example.androidtask5network.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidtask5network.R
import com.example.androidtask5network.data.entity.Cat
import com.example.androidtask5network.databinding.RecyclerItemBinding

class CatAdapter : RecyclerView.Adapter<CatAdapter.CatsViewHolder>() {

    var catoes: List<Cat> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerItemBinding.inflate(inflater, parent, false)
        return CatsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatsViewHolder, position: Int) {
        val cat = catoes[position]
        with(holder.binding) {
            Glide.with(catImage.context)
                .load(cat.photo)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(catImage)
        }
    }

    override fun getItemCount(): Int = catoes.size

    class CatsViewHolder(val binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}