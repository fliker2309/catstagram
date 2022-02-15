package com.example.androidtask5network.presetnation.ui.catstagram.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.androidtask5network.R
import com.example.androidtask5network.data.model.Cat
import com.example.androidtask5network.databinding.RecyclerCatItemBinding

class CatstagramAdapter(
    private val actionListener: CatActionListener
) :
    PagingDataAdapter<Cat, CatstagramViewHolder>(CatstagramViewHolder.CatstagramDiffItemCallback),
    View.OnClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatstagramViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerCatItemBinding.inflate(inflater, parent, false)

        binding.downloadBtn.setOnClickListener(this)
        binding.likeBtn.setOnClickListener(this)

        return CatstagramViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatstagramViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            likeBtn.tag = item
            downloadBtn.tag = item
        }
        item?.let { holder.bind(it) }
    }

    override fun onClick(v: View) {
        val cat = v.tag as Cat
        when (v.id) {
            R.id.likeBtn -> {
                actionListener.onCatLike(cat)
            }
            R.id.downloadBtn -> {
                actionListener.onCatDownload(cat)
            }
        }
    }
}
