package com.zjh.ktwanandroid.presentation.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zjh.ktwanandroid.R
import com.zjh.ktwanandroid.app.util.ColorUtil
import com.zjh.ktwanandroid.domain.model.SearchData

/**
 * @author zjh
 * 2022/7/25
 */
class SearchHotAdapter(data: ArrayList<SearchData>) : BaseQuickAdapter<SearchData, BaseViewHolder>(R.layout.item_search_hot, data){
    override fun convert(holder: BaseViewHolder, item: SearchData) {
        holder.setText(R.id.flow_tag, item.name)
        holder.setTextColor(R.id.flow_tag, ColorUtil.randomColor())
    }
}