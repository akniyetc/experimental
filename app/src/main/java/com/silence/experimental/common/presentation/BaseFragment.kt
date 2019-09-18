package com.silence.experimental.common.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.silence.experimental.ExperimentalApplication
import com.silence.experimental.common.di.AppComponent
import javax.inject.Inject

abstract class BaseFragment: Fragment() {

    abstract val layoutId: Int

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val appComponent: AppComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as ExperimentalApplication).appComponent
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(layoutId, container, false)
    }

    protected fun showMessage(message: String,  view: View) =
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}