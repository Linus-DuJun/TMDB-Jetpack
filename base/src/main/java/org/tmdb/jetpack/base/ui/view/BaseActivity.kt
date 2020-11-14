package org.tmdb.jetpack.base.ui.view

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    protected abstract fun layoutId(): Int

}