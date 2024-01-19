package com.gallardf.pokedex.utils.extensions

fun <T> List<T>.mergeWith(second: List<T>): List<T> {
    val list = ArrayList(this)
    list.addAll(second)
    return list
}