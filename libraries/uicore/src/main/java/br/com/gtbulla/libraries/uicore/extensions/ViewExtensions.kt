package br.com.gtbulla.libraries.uicore.extensions

import android.view.LayoutInflater
import android.view.View

fun View.getInflater(): LayoutInflater {
    return LayoutInflater.from(context)
}
