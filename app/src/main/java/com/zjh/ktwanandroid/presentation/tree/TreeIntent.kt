package com.zjh.ktwanandroid.presentation.tree

import com.zjh.ktwanandroid.domain.model.Article

/**
 * @author zjh
 * 2022/8/2
 */
sealed class TreeIntent {
    object LoadArticleHierarchyData: TreeIntent()
    object RefreshArticleHierarchyData: TreeIntent()
    object LoadArticleNavigationData: TreeIntent()
    object RefreshArticleNavigationData: TreeIntent()

    class CollectArticle(val article: Article, val position: Int) : TreeIntent()
    class UnCollectArticle(val article: Article, val position: Int) : TreeIntent()
}