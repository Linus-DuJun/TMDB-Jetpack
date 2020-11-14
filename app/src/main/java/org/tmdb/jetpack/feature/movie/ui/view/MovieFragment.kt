package org.tmdb.jetpack.feature.movie.ui.view

import android.os.Bundle
import org.tmdb.jetpack.R
import org.tmdb.jetpack.base.ui.view.BaseFragment
import org.tmdb.jetpack.databinding.FragmentMovieBinding

class MovieFragment: BaseFragment<FragmentMovieBinding>() {

    override fun layoutId() = R.layout.fragment_movie

    override fun initBinding(binding: FragmentMovieBinding, state: Bundle?) {
        // No Binding
    }
}