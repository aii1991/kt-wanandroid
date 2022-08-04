package com.zjh.ktwanandroid.presentation.search

import com.zjh.ktwanandroid.domain.model.SearchData

/**
 * @author zjh
 * 2022/7/25
 */
data class SearchUiState(val searchText:String="",
                         val hotData: List<SearchData> = listOf(),
                         val historyData: List<SearchData> = listOf())
