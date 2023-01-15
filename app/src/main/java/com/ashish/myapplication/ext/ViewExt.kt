package com.ashish.myapplication.ext

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.ashish.myapplication.utils.SingleEvent
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(message: String, timeLength: Int) {
    Snackbar.make(this, message, timeLength).show()
}

fun View.setupSnackbar(
    lifecycleOwner: LifecycleOwner,
    snackbarEvent: LiveData<SingleEvent<Any>>,
    timeLength: Int) {
    snackbarEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let {
            when (it) {
                is String -> {
                    showSnackbar(it, timeLength)
                }
                is Int -> {
                    showSnackbar(this.context.getString(it), timeLength)
                }
                else -> {
                }
            }

        }
    })
}