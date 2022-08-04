package com.zjh.ktwanandroid.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.zjh.ktwanandroid.R
import com.zjh.ktwanandroid.app.base.BasePagingAdapter
import com.zjh.ktwanandroid.app.widget.collectView.CollectView
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.domain.model.enums.ArticleType
import me.hgj.jetpackmvvm.ext.util.toHtml

/**
 * @author zjh
 * 2022/6/1
 */
class ArticleAdapter(private val itemClickAction:(item: Article?)->Unit = { _: Article? ->},
                     private val collectAction: (item: Article?, v: CollectView,layoutPosition: Int) -> Unit = { _: Article?, _: CollectView,_: Int -> }) : BasePagingAdapter<Article>(
    differCallback
) {
    companion object {
        val differCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemLayout(position: Int): Int{
        val data = getItem(position)
        var resIdRes = R.layout.item_article_project
        data?.run{
            resIdRes = if(data.articleType == ArticleType.PROJECT.type){
                R.layout.item_article_project
            }else{
                if(articleType == ArticleType.MY_COLLECT.type && (envelopePic != null && "" != envelopePic)){
                    R.layout.item_article_project
                }else{
                    R.layout.item_article
                }
            }
        }
        return resIdRes
    }

    override fun onItemClick(data: Article?) {
        itemClickAction.invoke(data)
    }

    override fun bindData(helper: ItemHelper, data: Article?) {
        data?.let{
            when(it.articleType){
                ArticleType.PROJECT.type->{
                    this.bindArticleProjectData(helper, data)
                }
                else -> {
                    if(it.articleType == ArticleType.MY_COLLECT.type && (it.envelopePic != null && "" != it.envelopePic)){
                        this.bindArticleProjectData(helper, data)
                    }else{
                        this.bindArticleData(helper, data)
                    }
                }
            }
        }

    }


    fun getDataItem(position: Int): Article? {
        return getItem(position)
    }

    private fun bindArticleData(helper: ItemHelper, data: Article?){
        data?.run {
            helper.setText(R.id.item_home_author, author?.ifEmpty { shareUser })
            helper.setText(R.id.item_home_content,title?.toHtml())
            helper.setText(R.id.item_home_type2,"$superChapterName ·$chapterName".toHtml())
            helper.setText(R.id.item_home_date,niceDate)
            helper.getView<CollectView>(R.id.item_home_collect).isChecked = collect
            helper.isVisible(R.id.item_home_new, !fresh)
            helper.isVisible(R.id.item_home_top, type != 1)
            tags?.let {
                if(it.isNotEmpty()){
                    helper.isVisible(R.id.item_home_type1, false)
                    helper.setText(R.id.item_home_type1, it[0].name)
                }else{
                    helper.isVisible(R.id.item_home_type1, true)
                }
            }
            when (articleType) {
                ArticleType.TREE_SQUARE.type -> {
                    helper.isVisible(R.id.item_home_top,false)
                    helper.isVisible(R.id.item_home_type1, false)
                }
                ArticleType.TREE_ASK.type -> {
                    helper.isVisible(R.id.item_home_new, false)
                }
                ArticleType.TREE_HIERARCHY.type -> {
                    helper.isVisible(R.id.item_home_new, false)
                    helper.isVisible(R.id.item_home_top,false)
                    helper.isVisible(R.id.item_home_type1, false)
                }
                ArticleType.SEARCH.type ->{
                    helper.isVisible(R.id.item_home_new, false)
                    helper.isVisible(R.id.item_home_top,false)
                    helper.isVisible(R.id.item_home_type1, false)
                }
                ArticleType.MY_COLLECT.type ->{
                    helper.isVisible(R.id.item_home_new, false)
                    helper.isVisible(R.id.item_home_top,false)
                    helper.isVisible(R.id.item_home_type1, false)
                    helper.setText(R.id.item_home_type2,"$chapterName".toHtml())
                    helper.getView<CollectView>(R.id.item_home_collect).isChecked = true
                }
            }

            helper.getView<CollectView>(R.id.item_home_collect).setOnCollectViewClickListener(object : CollectView.OnCollectViewClickListener{
                override fun onClick(v: CollectView) {
                    collectAction.invoke(data, v, helper.getCurrLayoutPosition())
                }
            })
        }
    }

    private fun bindArticleProjectData(helper: ItemHelper, data: Article?){
        data?.run{
            helper.setText(R.id.item_project_author, author?.ifEmpty { shareUser })
            helper.setText(R.id.item_project_title, title?.toHtml())
            helper.setText(R.id.item_project_type, "$superChapterName·$chapterName".toHtml())
            helper.setText(R.id.item_project_date, niceDate)
            helper.getView<CollectView>(R.id.item_project_collect).isChecked = collect
            tags?.let {
                if(it.isNotEmpty()){
                    helper.isVisible(R.id.item_project_type1, false)
                    helper.setText(R.id.item_project_type1, it[0].name)
                }else{
                    helper.isVisible(R.id.item_project_type1,true)
                }
            }
            helper.getView<CollectView>(R.id.item_project_collect).setOnCollectViewClickListener(object : CollectView.OnCollectViewClickListener{
                override fun onClick(v: CollectView) {
                    collectAction.invoke(data, v, helper.getCurrLayoutPosition())
                }
            })
            envelopePic?.let { helper.loadImg(R.id.item_project_imageview, it) }
        }
    }
}