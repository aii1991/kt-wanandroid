package com.zjh.ktwanandroid.data.datasource.remote

import com.zjh.ktwanandroid.data.datasource.remote.model.bean.ApiPagerResponse
import com.zjh.ktwanandroid.data.datasource.remote.model.bean.ApiResponse
import com.zjh.ktwanandroid.domain.model.*
import retrofit2.http.*

/**
 * @author zjh
 * 2022/5/31
 */
interface RemoteDataSource {

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") pwd: String
    ): ApiResponse<UserInfo>

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("username") username: String, @Field("password") pwd: String, @Field(
            "repassword"
        ) rpwd: String
    ): ApiResponse<Any?>

    /**
     * 获取当前账户的个人积分
     */
    @GET("lg/coin/userinfo/json")
    suspend fun getIntegral(): ApiResponse<Integral>


    /**
     * 获取置顶文章集合数据
     */
    @GET("article/top/json")
    suspend fun getTopArticleList(): ApiResponse<List<Article>>

    /**
     * 获取首页文章数据
     */
    @GET("article/list/{page}/json")
    suspend fun getArticleList(@Path("page") pageNo: Int): ApiResponse<ApiPagerResponse<List<Article>>>

    /**
     * 获取banner数据
     */
    @GET("banner/json")
    suspend fun getBanner(): ApiResponse<List<Banner>>

    /**
     * 收藏文章
     */
    @POST("lg/collect/{id}/json")
    suspend fun collect(@Path("id") id: Int): ApiResponse<Any?>

    /**
     * 取消收藏文章
     */
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun unCollect(@Path("id") id: Int): ApiResponse<Any?>

    /**
     * 项目分类标题
     */
    @GET("project/tree/json")
    suspend fun getProjectArticleCategories(): ApiResponse<List<ArticleCategory>>

    /**
     * 根据分类id获取项目数据
     */
    @GET("project/list/{page}/json")
    suspend fun getProjectArticleListByCategoryId(
        @Path("page") pageNo: Int,
        @Query("cid") cid: Int
    ): ApiResponse<ApiPagerResponse<List<Article>>>

    /**
     * 获取最新项目数据
     */
    @GET("article/listproject/{page}/json")
    suspend fun getLastProjectArticleList(@Path("page") pageNo: Int): ApiResponse<ApiPagerResponse<List<Article>>>

    /**
     * 公众号分类
     */
    @GET("wxarticle/chapters/json")
    suspend fun getPublicNumberArticleCategories(): ApiResponse<List<ArticleCategory>>

    /**
     * 获取公众号数据
     */
    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun getPublicNumberArticleListByCategoryId(
        @Path("page") pageNo: Int,
        @Path("id") id: Int
    ): ApiResponse<ApiPagerResponse<List<Article>>>

    /**
     * 广场列表数据
     */
    @GET("user_article/list/{page}/json")
    suspend fun getSquareArticleList(@Path("page") page: Int): ApiResponse<ApiPagerResponse<List<Article>>>

    /**
     * 每日一问列表数据
     */
    @GET("wenda/list/{page}/json")
    suspend fun getAskArticleList(@Path("page") page: Int): ApiResponse<ApiPagerResponse<List<Article>>>

    /**
     * 获取体系数据
     */
    @GET("tree/json")
    suspend fun getHierarchyData(): ApiResponse<List<ArticleHierarchy>>

    /**
     * 知识体系下的文章数据
     */
    @GET("article/list/{page}/json")
    suspend fun getHierarchyArticleList(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): ApiResponse<ApiPagerResponse<List<Article>>>

    /**
     * 获取导航数据
     */
    @GET("navi/json")
    suspend fun getArticleNavigationData(): ApiResponse<List<ArticleNavigation>>

    /**
     * 获取热门搜索数据
     */
    @GET("hotkey/json")
    suspend fun getSearchHotTagListData(): ApiResponse<List<SearchData>>

    /**
     * 根据关键词搜索数据
     */
    @POST("article/query/{page}/json")
    suspend fun getArticleListByKey(
        @Path("page") pageNo: Int,
        @Query("k") searchKey: String
    ): ApiResponse<ApiPagerResponse<List<Article>>>

    /**
     * 添加文章
     */
    @POST("lg/user_article/add/json")
    @FormUrlEncoded
    suspend fun addArticle(
        @Field("title") title: String,
        @Field("link") content: String
    ): ApiResponse<Any?>

    /**
     * 获取收藏文章数据
     */
    @GET("lg/collect/list/{page}/json")
    suspend fun getMyCollectArticleList(@Path("page") pageNo: Int): ApiResponse<ApiPagerResponse<List<CollectArticle>>>

    /**
     * 获取收藏网址数据
     */
    @GET("lg/collect/usertools/json")
    suspend fun getMyCollectUrlList(): ApiResponse<ArrayList<CollectUrl>>
}