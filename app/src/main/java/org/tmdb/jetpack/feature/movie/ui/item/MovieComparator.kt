package org.tmdb.jetpack.feature.movie.ui.item

import androidx.recyclerview.widget.DiffUtil

object MovieComparator : DiffUtil.ItemCallback<MovieAdapterItem>() {
    override fun areItemsTheSame(oldItem: MovieAdapterItem, newItem: MovieAdapterItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieAdapterItem, newItem: MovieAdapterItem): Boolean {
        return oldItem.title == newItem.title
    }

}