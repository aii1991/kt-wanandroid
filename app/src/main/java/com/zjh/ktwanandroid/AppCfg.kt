package com.zjh.ktwanandroid

import androidx.paging.PagingConfig

/**
 * @author zjh
 * 2022/5/31
 */
object AppCfg {
    const val SERVER_URL = "https://wanandroid.com/"
    private const val PAGE_SIZE = 20
    val pagingCfg = PagingConfig(
        pageSize = PAGE_SIZE,
        prefetchDistance = 1,
        initialLoadSize = PAGE_SIZE,
        enablePlaceholders = true,
        maxSize = PAGE_SIZE * 3
    )
}