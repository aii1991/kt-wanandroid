package com.zjh.ktwanandroid.presentation.tree

import com.zjh.ktwanandroid.domain.model.ArticleHierarchy
import com.zjh.ktwanandroid.domain.model.ArticleNavigation

/**
 * @author zjh
 * 2022/7/20
 */
data class TreeUiState(val articleHierarchyListData:List<ArticleHierarchy> = listOf(), val articleNavigationListData:List<ArticleNavigation> = listOf())
