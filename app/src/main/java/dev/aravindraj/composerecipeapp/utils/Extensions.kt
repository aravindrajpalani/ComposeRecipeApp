package dev.aravindraj.composerecipeapp.utils

fun String?.capitalizeFirstLetter(): String {
    return this?.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase() else it.toString()
    } ?: ""
}

fun Double?.toIntIfWhole(): Number? {
    return this?.let {
        if (it % 1.0 == 0.0) it.toInt() else it
    }
}