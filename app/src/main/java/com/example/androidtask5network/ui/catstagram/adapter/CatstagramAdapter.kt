package com.example.androidtask5network.ui.catstagram.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.androidtask5network.data.model.Cat
import com.example.androidtask5network.databinding.RecyclerCatItemBinding

class CatstagramAdapter :
    PagingDataAdapter<Cat, CatstagramViewHolder>(CatstagramViewHolder.CatstagramDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatstagramViewHolder {
        return CatstagramViewHolder(RecyclerCatItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CatstagramViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }
}
