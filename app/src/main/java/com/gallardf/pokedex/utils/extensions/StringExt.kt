package com.gallardf.pokedex.utils.extensions

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun String.capitalizeFirstChar(): String {
    return replaceFirstChar {
        it.uppercaseChar()
    }
}

fun String.encodeUrl() = URLEncoder.encode(this, StandardCharsets.UTF_8.toString())