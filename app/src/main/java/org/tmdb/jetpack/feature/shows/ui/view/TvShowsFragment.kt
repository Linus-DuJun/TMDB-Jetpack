package org.tmdb.jetpack.feature.shows.ui.view

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.tmdb.jetpack.R
import org.tmdb.jetpack.base.ui.view.BaseFragment
import org.tmdb.jetpack.databinding.FragmentTvshowsBinding

@AndroidEntryPoint
class TvShowsFragment: BaseFragment<FragmentTvshowsBinding>() {

    override fun layoutId() = R.layout.fragment_tvshows

    override fun initBinding(binding: FragmentTvshowsBinding, state: Bundle?) {
        // No Binding yet
    }
}