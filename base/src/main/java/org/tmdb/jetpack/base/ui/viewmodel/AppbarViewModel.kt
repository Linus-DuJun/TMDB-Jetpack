package org.tmdb.jetpack.base.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import org.tmdb.jetpack.base.core.utils.Event

class AppbarViewModel: ViewModel() {
    val currentNavController = MutableLiveData<Event<NavController>>()
}