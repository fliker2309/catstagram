package com.example.androidtask5network.data

import com.example.androidtask5network.data.model.Cat
import com.example.androidtask5network.data.network.model.CatResponse

internal fun CatResponse.toCat() : Cat {
    return Cat(
        id = id,
        url = imageUrl
    )
}
