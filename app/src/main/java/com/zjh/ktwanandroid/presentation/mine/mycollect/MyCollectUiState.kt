package com.zjh.ktwanandroid.presentation.mine.mycollect

import com.zjh.ktwanandroid.domain.model.CollectUrl

/**
 * @author zjh
 * 2022/7/29
 */
data class MyCollectUiState(val isLoading: Boolean = true,val collectUrlList:ArrayList<CollectUrl> = arrayListOf())
