package com.zjh.ktwanandroid.presentation.search

import com.zjh.ktwanandroid.app.base.BaseMVIVM
import com.zjh.ktwanandroid.domain.model.SearchData
import com.zjh.ktwanandroid.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

/**
 * @author zjh
 * 2022/7/25
 */
@HiltViewModel
class SearchVM @Inject constructor(private val searchRepository: SearchRepository): BaseMVIVM<SearchUiState, SearchIntent>(){
    val mUiState = getUiState().asStateFlow()

    private fun loadData(){
        launch {
            val hotData = searchRepository.getHotTagListData()
            val historyData = searchRepository.getHistoryListData()
            setData {
                it.copy(hotData = hotData, historyData = historyData)
            }
        }
    }

    private fun clearHistory() = launch {
        searchRepository.clearHistory()
        setData { it.copy(historyData = listOf()) }
    }

    private fun deleteHistory(name: String) = launch {
        searchRepository.deleteHistoryData(name)
        setData {
            it.copy(historyData = it.historyData.filter { searchData->
                searchData.name != name
            })
        }
    }

    private fun addToHistory(name: String) = launch {
        val addData = SearchData(name = name)
        searchRepository.addToHistory(addData)
        val cData = mUiState.value.historyData.subList(0,mUiState.value.historyData.size)
        if(cData.contains(addData)){ return@launch }
        val newData: MutableList<SearchData> = mutableListOf()
        newData.addAll(cData)
        newData.add(addData)
        setData {
            it.copy(historyData = newData)
        }
    }

    override fun createUiState(): SearchUiState {
        return SearchUiState()
    }

    override suspend fun handleIntent(sharedFlow: MutableSharedFlow<SearchIntent>) {
        sharedFlow.collectLatest {
            when(it){
                is SearchIntent.LoadHistoryData-> loadData()
                is SearchIntent.AddToHistory-> addToHistory(it.name)
                is SearchIntent.DeleteHistory-> deleteHistory(it.name)
                is SearchIntent.ClearHistory-> clearHistory()
            }
        }
    }
}