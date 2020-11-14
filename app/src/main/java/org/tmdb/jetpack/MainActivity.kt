package org.tmdb.jetpack

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import org.tmdb.jetpack.base.core.extensions.setupWithNavController
import org.tmdb.jetpack.base.ui.viewmodel.AppbarViewModel
import org.tmdb.jetpack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val appbarViewModel by viewModels<AppbarViewModel>()
    private lateinit var dataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        if (savedInstanceState == null) {
            setupBottomNavigationView()
        } // Else, need to wait for onRestoreInstanceState
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationView has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        val navGraphIds = listOf(
            R.navigation.nav_movie,
            R.navigation.nav_shows,
            R.navigation.nav_people
        )
        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )
        controller.observe(this, Observer {
            it.addOnDestinationChangedListener { controller, destination, arguments ->
                when(destination.id) {
                    R.id.movieFragment,
                    R.id.tvShowsFragment,
                    R.id.peopleFragment -> bottomNavigationView.visibility = View.VISIBLE
                    else -> bottomNavigationView.visibility = View.GONE
                }
            }
        })
    }
}