package org.tmdb.jetpack.feature.people.ui.view

import android.os.Bundle
import org.tmdb.jetpack.R
import org.tmdb.jetpack.base.ui.view.BaseFragment
import org.tmdb.jetpack.databinding.FragmentPeopleBinding

class PeopleFragment: BaseFragment<FragmentPeopleBinding>() {

    override fun layoutId() = R.layout.fragment_people
    override fun initBinding(binding: FragmentPeopleBinding, state: Bundle?) {
        // No Binding yet
    }
}