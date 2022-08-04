package com.zjh.ktwanandroid.data.pagging.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.zjh.ktwanandroid.data.datasource.local.AppDatabase
import com.zjh.ktwanandroid.data.datasource.local.entity.ArticleEntity
import com.zjh.ktwanandroid.data.datasource.local.entity.RemoteKeyEntity
import com.zjh.ktwanandroid.data.datasource.remote.RemoteDataSource
import com.zjh.ktwanandroid.data.datasource.remote.model.bean.ApiPagerResponse
import com.zjh.ktwanandroid.data.datasource.remote.model.bean.ApiResponse
import com.zjh.ktwanandroid.data.mapper.toEntity
import com.zjh.ktwanandroid.domain.model.enums.ArticleType
import com.zjh.ktwanandroid.domain.model.toArticle
import me.hgj.jetpackmvvm.network.AppException
import me.hgj.jetpackmvvm.util.LogUtils
import java.io.IOException

/**
 * @author zjh
 * 2022/5/31
 */
@OptIn(ExperimentalPagingApi::class)
class ArticleRemoteMediator(
    private val articleType: ArticleType,
    private val categoryId: Int = 0,
    private val query: String = "",
    private val database: AppDatabase,
    private val remoteDataSource: RemoteDataSource
) : RemoteMediator<Int,ArticleEntity>(){
    private val articleDao = database.articleDao
    private val remoteKeyDao = database.remoteKeyDao

    override suspend fun load(loadType: LoadType, state: PagingState<Int, ArticleEntity>): MediatorResult {
        /*
       1.LoadType.REFRESH：首次访问 或者调用 PagingDataAdapter.refresh() 触发
       2.LoadType.PREPEND：在当前列表头部添加数据的时候时触发，实际在项目中基本很少会用到直接返回 MediatorResult.Success(endOfPaginationReached = true) ，参数 endOfPaginationReached 表示没有数据了不在加载
       3.LoadType.APPEND：加载更多时触发，这里获取下一页的 key, 如果 key 不存在，表示已经没有更多数据，直接返回 MediatorResult.Success(endOfPaginationReached = true) 不会在进行网络和数据库的访问
        */
        try {
            val loadKey = when(loadType){
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    //使用remoteKey来获取下一个或上一个页面。
                    val remoteKeys = state.lastItemOrNull()?.id?.let {
                        database.remoteKeyDao.remoteKeysArticleId(articleId = it, articleType = articleType.type, categoryId = categoryId)
                    }
                    //remoteKeys = null则代表在数据库中还没有数据
                    val nextKey = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    nextKey
                }
            }

            val page = loadKey ?: 0
            LogUtils.debugInfo("load page=>$page,cid=>${categoryId}")
            val response = when(articleType){
                ArticleType.HOME -> remoteDataSource.getArticleList(page)
                ArticleType.PROJECT-> if(categoryId == 0){
                    remoteDataSource.getLastProjectArticleList(page)
                }else{
                    remoteDataSource.getProjectArticleListByCategoryId(page,categoryId)
                }
                ArticleType.TREE_SQUARE-> remoteDataSource.getSquareArticleList(page)
                ArticleType.TREE_HIERARCHY-> remoteDataSource.getHierarchyArticleList(page,categoryId)
                ArticleType.TREE_ASK-> remoteDataSource.getAskArticleList(page)
                ArticleType.PUBLIC_NUMBER-> remoteDataSource.getPublicNumberArticleListByCategoryId(page,categoryId)
                ArticleType.SEARCH-> remoteDataSource.getArticleListByKey(page,query)
                ArticleType.MY_COLLECT-> remoteDataSource.getMyCollectArticleList(page).let {
                    val data = ApiPagerResponse(
                        it.data.datas.map { d->d.toArticle() },
                        it.data.curPage,
                        it.data.offset,
                        it.data.over,
                        it.data.pageCount,
                        it.data.size,
                        it.data.total
                    )
                    val rsp = ApiResponse(it.errorCode,it.errorMsg,data)
                    rsp
                }
            }
            if(!response.isSucces()){
                return MediatorResult.Error(AppException(response.errorCode,response.errorMsg))
            }

            var rspDataList = response.data.datas
            val endOfPaginationReached = rspDataList.isEmpty()
            database.withTransaction {
                if(loadType == LoadType.REFRESH){
                    remoteKeyDao.clearRemoteKeys(articleType = articleType.type, categoryId = categoryId)
                    articleDao.clearArticle(articleType = articleType.type, cId = categoryId)
                }
                val prevKey = if(page == 0) null else page - 1
                val nextKey = if(endOfPaginationReached) null else page + 1

                rspDataList = rspDataList.map{
                    it.articleType = articleType.type
                    it.categoryId = categoryId
                    it
                }

                val remoteKeys = rspDataList.map {
                    RemoteKeyEntity(articleId = it.id, articleType = it.articleType, prevKey = prevKey, nextKey = nextKey, categoryId = categoryId)
                }
                remoteKeyDao.insertAll(remoteKey = remoteKeys)
                articleDao.insertAll(rspDataList.map {
                    it.toEntity()
                })
            }
            return MediatorResult.Success(endOfPaginationReached)

        }catch (e: IOException){
            return MediatorResult.Error(e)
        }
    }
}