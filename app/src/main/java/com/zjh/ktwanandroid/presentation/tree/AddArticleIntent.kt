package com.zjh.ktwanandroid.presentation.tree

/**
 * @author zjh
 * 2022/8/2
 */
sealed class AddArticleIntent {
    object AddArticle: AddArticleIntent()
    data class InputTitle(val text:String): AddArticleIntent()
    data class InputLink(val text:String): AddArticleIntent()
}