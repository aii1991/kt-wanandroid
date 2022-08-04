package com.zjh.ktwanandroid.app.base

import androidx.lifecycle.viewModelScope
import com.zjh.ktwanandroid.data.datasource.remote.model.bean.ApiResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.exception.ApiException

/**
 * @author zjh
 * 2022/6/23
 */
abstract class BaseMVIVM<UiState,Intent> : BaseViewModel() {
    private val _shareFlowIntent = MutableSharedFlow<Intent>()
    private val _rootUiState: MutableStateFlow<RootUiState> = MutableStateFlow(RootUiState())
    val mRootUiState: StateFlow<RootUiState> = _rootUiState.asStateFlow()
    private val mHandleException = CoroutineExceptionHandler{ _, e->
        run {
            when (e) {
                is ApiException.ReqErrException -> {
                    updateRootUiState{it.copy(errMsg = e.msg, errCode = e.code, isShowLoading = false)}
                }
                else ->{
                    updateRootUiState{it.copy(errMsg = e.message.toString(), errCode = -1, isShowLoading = false)}
                }
            }
        }
    }

    private val _uiState = lazy { MutableStateFlow(createUiState()) }

    init {
        viewModelScope.launch(mHandleException) {
            handleIntent(_shareFlowIntent)
        }
    }

    /**
     * 分发意图
     * @param action
     */
    fun dispatchIntent(action: Intent){
        viewModelScope.launch(mHandleException) {
            _shareFlowIntent.emit(action)
        }
    }

    fun launch(isShowLoading: Boolean = false, onStart:()->Unit = {}, onCompletion:()->Unit={}, block:suspend ()-> Unit){
        updateRootUiState { RootUiState() }
        onStart.invoke()
        viewModelScope.launch(mHandleException) {
            withContext(Dispatchers.IO){
                if(isShowLoading){ updateRootUiState { it.copy(isShowLoading = true) } }
                try {
                    block.invoke()
                }finally {
                    onCompletion.invoke()
                }
                updateRootUiState { it.copy(isShowLoading = false) }
            }
        }
    }

    protected fun getUiState(): MutableStateFlow<UiState> {
        return _uiState.value
    }

    protected fun setData(block:(uiState: UiState)->UiState){
        updateUiState { block(it)}
    }

    private fun updateUiState(block:(uiState :UiState)->UiState){
        getUiState().update {
            block(it)
        }
    }

    fun updateRootUiState(block:(rootUiState:RootUiState)->RootUiState){
        _rootUiState.update { block(it) }
    }

    abstract fun createUiState(): UiState

    abstract suspend fun handleIntent(sharedFlow: MutableSharedFlow<Intent>)
}

data class RootUiState(val isShowLoading: Boolean = false,
                       val errMsg:String = "",
                       val errCode: Int = 0)
