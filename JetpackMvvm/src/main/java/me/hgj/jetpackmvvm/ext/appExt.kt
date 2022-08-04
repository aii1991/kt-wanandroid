package me.hgj.jetpackmvvm.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @author zjh
 * 2022/6/29
 */


fun AppCompatActivity.launchRepeatOnLifecycle(state: Lifecycle.State = Lifecycle.State.RESUMED, block:suspend (coroutineScope: CoroutineScope)->Unit){
    lifecycleScope.launch {
        repeatOnLifecycle(state){
            block(this)
        }
    }
}

fun Fragment.launchRepeatOnLifecycle(state:Lifecycle.State = Lifecycle.State.RESUMED, block:suspend (coroutineScope: CoroutineScope)->Unit){
    lifecycleScope.launch {
        repeatOnLifecycle(state){
            block(this)
        }
    }
}