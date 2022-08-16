package com.zjh.ktwanandroid.presentation.webview

import com.zjh.ktwanandroid.app.base.BaseMVIVM
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

/**
 * @author zjh
 * 2022/6/16
 */
@HiltViewModel
class WebViewModel @Inject constructor(private val homeRepository: HomeRepository) : BaseMVIVM<WebUiState,WebViewIntent>() {
    val mUiState = getUiState().asStateFlow()

    //是否收藏
    var isCollect = false

    //收藏的Id
    var articleId = 0

    //标题
    var showTitle: String = ""

    //文章的网络访问路径
    var url: String = ""

    //需要收藏的类型 具体参数说明请看 CollectType 枚举类
    var collectType = 0

    private fun unCollectArticle(article: Article) = launch(true) {
        homeRepository.unCollectArticle(articleId = article.id)
        setData { it.copy(isCollect = false) }
    }

    private fun collectArticle(article: Article) = launch(true){
        homeRepository.collectArticle(articleId = article.id)
        setData { it.copy(isCollect = true) }
    }

    override fun createUiState(): WebUiState {
        return WebUiState(isCollect = isCollect)
    }

    override suspend fun handleIntent(sharedFlow: MutableSharedFlow<WebViewIntent>) {
        sharedFlow.collectLatest{
            when(it){
                is WebViewIntent.CollectArticle->{
                    collectArticle(it.article)
                }
                is WebViewIntent.UnCollectArticle->{
                    unCollectArticle(it.article)
                }
            }
        }
    }
}