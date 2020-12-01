package org.tmdb.jetpack.feature.movie.ui.item

import org.tmdb.jetpack.BR
import org.tmdb.jetpack.R
import org.tmdb.jetpack.base.ui.adapter.AbstractAdapterItem

data class MovieAdapterItem(
    val id: Int,
    val title: String,
) : AbstractAdapterItem() {
    override fun layoutId() = R.layout.item_movie

    override fun variableId() = BR.item
}