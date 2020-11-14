package org.tmdb.jetpack.feature.shows.ui.view

import android.os.Bundle
import org.tmdb.jetpack.R
import org.tmdb.jetpack.base.ui.view.BaseFragment
import org.tmdb.jetpack.databinding.FragmentTvshowsBinding

class TvShowsFragment: BaseFragment<FragmentTvshowsBinding>() {

    override fun layoutId() = R.layout.fragment_tvshows

    override fun initBinding(binding: FragmentTvshowsBinding, state: Bundle?) {
        // No Binding yet
    }
}