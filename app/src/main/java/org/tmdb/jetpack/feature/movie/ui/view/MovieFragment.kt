package org.tmdb.jetpack.feature.movie.ui.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import org.tmdb.jetpack.R
import org.tmdb.jetpack.base.ui.adapter.UniversalPagingAdapter
import org.tmdb.jetpack.base.ui.view.BaseFragment
import org.tmdb.jetpack.databinding.FragmentMovieBinding
import org.tmdb.jetpack.feature.movie.ui.item.MovieAdapterItem
import org.tmdb.jetpack.feature.movie.ui.item.MovieComparator
import org.tmdb.jetpack.feature.movie.ui.viewmodel.MovieViewModel
import retrofit2.Retrofit
import javax.inject.Inject

@AndroidEntryPoint
class MovieFragment: BaseFragment<FragmentMovieBinding>() {

    @Inject
    lateinit var retrofit: Retrofit

    private lateinit var viewModel: MovieViewModel
    private lateinit var adapter: UniversalPagingAdapter<MovieAdapterItem>
    private lateinit var adapterObserver: RecyclerView.AdapterDataObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun layoutId() = R.layout.fragment_movie

    override fun initBinding(binding: FragmentMovieBinding, state: Bundle?) {
        // No Binding
    }


    private fun initViews() {
        initToolbar()
        initAdapter()
        initSwipeRefresh()
    }

    private fun initToolbar() {
        val toolbar = requireView().findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle(R.string.title_movie)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
    }

    private fun initAdapter() {
        val recycler = requireView().findViewById<RecyclerView>(R.id.recycler)
        adapter = UniversalPagingAdapter(MovieComparator).apply {
            adapterObserver = object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    recycler.scrollToPosition(positionStart)
                }
            }
            registerAdapterDataObserver(adapterObserver)
        }
        recycler.adapter = adapter
        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi::class)
            viewModel.movieItems.collectLatest { adapter.submitData(it) }
        }
    }

    private fun initSwipeRefresh() {
        val refresher = requireView().findViewById<SwipeRefreshLayout>(R.id.swipe_refresh)
        refresher.setOnRefreshListener { adapter.refresh() }
    }
}