package org.tmdb.jetpack.feature.movie.ui.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
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
//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)
//        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
    }

    private fun initAdapter() {
        val recycler = requireView().findViewById<RecyclerView>(R.id.recycler)
        adapter = UniversalPagingAdapter(MovieComparator)
        recycler.adapter = adapter
        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi::class)
            adapter.loadStateFlow.collectLatest {
                swipe_refresh?.isRefreshing = it.refresh == LoadState.Loading
            }
        }
        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi::class)
            viewModel.movieItems.collectLatest { adapter.submitData(it) }
        }
        lifecycleScope.launchWhenCreated {
            @OptIn(FlowPreview::class)
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh } // Only emit when REFRESH LoadState for RemoteMediator changes
                .filter { it.refresh is LoadState.NotLoading } // Only react to cases where Remote REFRESH completes. i.e NotLoading
                .collectLatest { recycler.scrollToPosition(0) }
        }
    }

    private fun initSwipeRefresh() {
        swipe_refresh.setOnRefreshListener { adapter.refresh() }
    }
}