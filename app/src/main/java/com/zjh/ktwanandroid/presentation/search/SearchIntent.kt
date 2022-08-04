package com.zjh.ktwanandroid.presentation.search

/**
 * @author zjh
 * 2022/8/2
 */
sealed class SearchIntent {
    object LoadHistoryData: SearchIntent()
    data class AddToHistory(val name: String): SearchIntent()
    data class DeleteHistory(val name: String): SearchIntent()
    object ClearHistory: SearchIntent()
}