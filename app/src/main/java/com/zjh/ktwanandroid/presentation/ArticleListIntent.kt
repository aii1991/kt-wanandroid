package com.zjh.ktwanandroid.presentation

import com.zjh.ktwanandroid.domain.model.Article

/**
 * @author zjh
 * 2022/8/2
 */
sealed class ArticleListIntent {
    object LoadData: ArticleListIntent()
    object RefreshData: ArticleListIntent()
    class CollectArticle(val article: Article, val position: Int) : ArticleListIntent()
    class UnCollectArticle(val article: Article, val position: Int) : ArticleListIntent()
}