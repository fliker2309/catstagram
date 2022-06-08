package com.example.androidtask5network.feature_catlist.data

import com.example.androidtask5network.feature_catlist.data.model.Cat
import com.example.androidtask5network.feature_catlist.data.network.model.CatResponse

internal fun CatResponse.toCat(): Cat {
    return Cat(
        id = id,
        url = imageUrl
    )
}
