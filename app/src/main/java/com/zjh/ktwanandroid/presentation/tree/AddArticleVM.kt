package com.zjh.ktwanandroid.presentation.tree

import com.zjh.ktwanandroid.app.base.BaseMVIVM
import com.zjh.ktwanandroid.domain.repository.TreeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import me.hgj.jetpackmvvm.exception.ApiException
import javax.inject.Inject

/**
 * @author zjh
 * 2022/7/27
 */
@HiltViewModel
class AddArticleVM @Inject constructor(private val treeRepository: TreeRepository) : BaseMVIVM<AddArticleUiState, AddArticleIntent>() {
    val mUiState = getUiState().asStateFlow()
    val userInfo by lazy {
        try {
            treeRepository.getUserInfo()
        }catch (e: ApiException.NotLoginException){
            updateRootUiState { it.copy(errMsg = e.msg, errCode = e.code) }
            null
        }

    }

    private fun addArticle(){
        val title = mUiState.value.title
        val link = mUiState.value.link
        if(title.isEmpty()){
            updateRootUiState { it.copy(isShowLoading = false, errMsg = "标题不能为空") }
            return
        }
        if(link.isEmpty()){
            updateRootUiState { it.copy(isShowLoading = false, errMsg = "分享链接不能为空") }
            return
        }

        launch(true) {
            treeRepository.addArticle(title, link)
            setData {
                it.copy(isSuccess = true)
            }
        }
    }

    override fun createUiState(): AddArticleUiState {
        return AddArticleUiState()
    }

    override suspend fun handleIntent(sharedFlow: MutableSharedFlow<AddArticleIntent>) {
        sharedFlow.collectLatest {
            when(it){
                is AddArticleIntent.AddArticle-> addArticle()
                is AddArticleIntent.InputTitle->{
                    setData { uiState ->
                        uiState.copy(title = it.text)
                    }
                }
                is AddArticleIntent.InputLink->{
                    setData { uiState ->
                        uiState.copy(link = it.text)
                    }
                }
            }
        }
    }


}