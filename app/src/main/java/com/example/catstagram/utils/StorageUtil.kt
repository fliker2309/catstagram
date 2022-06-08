package com.example.catstagram.utils

import android.os.Build

// Может лучше возвращать Boolean. Тогда у функции будет более очевидный интерфейс.
inline fun <T> sdk29AndUp(onSdk29: () -> T): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        onSdk29()
    } else null
}
