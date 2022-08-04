package com.zjh.ktwanandroid.presentation.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zjh.ktwanandroid.R
import com.zjh.ktwanandroid.app.util.ColorUtil
import com.zjh.ktwanandroid.domain.model.ArticleCategory
import me.hgj.jetpackmvvm.ext.util.toHtml

/**
 * @author zjh
 * 2022/7/20
 */
class HierarchyChildAdapter(data: MutableList<ArticleCategory>): BaseQuickAdapter<ArticleCategory,BaseViewHolder>(
    R.layout.item_hierarchy_child,data) {
    override fun convert(holder: BaseViewHolder, item: ArticleCategory) {
        holder.setText(R.id.flow_tag, item.name.toHtml())
        holder.setTextColor(R.id.flow_tag, ColorUtil.randomColor())
    }
}