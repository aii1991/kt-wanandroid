package com.zjh.ktwanandroid.presentation.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zjh.ktwanandroid.R
import com.zjh.ktwanandroid.app.util.ColorUtil
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.domain.model.ArticleCategory
import me.hgj.jetpackmvvm.ext.util.toHtml

/**
 * @author zjh
 * 2022/7/20
 */
class NavigationChildAdapter(data: MutableList<Article>): BaseQuickAdapter<Article,BaseViewHolder>(
    R.layout.item_hierarchy_child,data) {
    override fun convert(holder: BaseViewHolder, item: Article) {
        holder.setText(R.id.flow_tag, item.title?.toHtml())
        holder.setTextColor(R.id.flow_tag, ColorUtil.randomColor())
    }
}