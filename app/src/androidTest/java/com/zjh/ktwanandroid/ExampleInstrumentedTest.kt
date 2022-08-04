package com.zjh.ktwanandroid

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import me.hgj.jetpackmvvm.util.LogUtils

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.ktandroid", appContext.packageName)
    }



    @Test
    fun testFlow(){
        val stateFlow = MutableStateFlow(UiState())
        val _uiState = stateFlow.asStateFlow()
        runBlocking {
            val job1 = launch {
                stateFlow.collect{
                    LogUtils.debugInfo(it.toString())
                }
            }


            val job2 = launch {
                stateFlow.update {
                    _uiState.value.copy(isChange = true)
                }
            }

            val job3 = launch {
                stateFlow.update {
                    _uiState.value.copy(isRefresh = true)
                }
            }

            joinAll(job1,job2,job3)
        }

    }
}

data class UiState(val isChange:Boolean=false,val isRefresh:Boolean=false)