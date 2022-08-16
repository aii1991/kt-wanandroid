package com.zjh.ktwanandroid.presentation.webview

import com.zjh.ktwanandroid.domain.model.Article

/**
 * @author zjh
 * 2022/8/8
 */
sealed class WebViewIntent {
    class CollectArticle(val article: Article, val position: Int=-1) : WebViewIntent()
    class UnCollectArticle(val article: Article, val position: Int=-1) : WebViewIntent()
}