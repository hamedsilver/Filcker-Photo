package com.hamedsafari.filckrphotos.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch


fun <T> Fragment.collectLifecycleFlow(flow: Flow<T>, collect: FlowCollector<T>) {
    // The block passed to repeatOnLifecycle is executed when the lifecycle
    // is at least STARTED and is cancelled when the lifecycle is STOPPED.
    // It automatically restarts the block when the lifecycle is STARTED again.
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            // Safely collect from locationFlow when the lifecycle is STARTED
            // and stops collection when the lifecycle is STOPPED
            flow.collect(collect)
        }
    }
}