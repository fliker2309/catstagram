package com.example.androidtask5network.presetnation.ui.catstagram.adapter

import com.example.androidtask5network.data.model.Cat

interface CatActionListener {

    fun onCatDownload(cat: Cat)

    fun onCatLike(cat: Cat)
}
