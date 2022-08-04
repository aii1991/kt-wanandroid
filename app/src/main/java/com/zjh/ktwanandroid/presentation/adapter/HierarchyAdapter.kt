package com.zjh.ktwanandroid.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.zjh.ktwanandroid.R
import com.zjh.ktwanandroid.domain.model.ArticleHierarchy
import me.hgj.jetpackmvvm.ext.util.toHtml

/**
 * @author zjh
 * 2022/7/20
 */
class HierarchyAdapter(data: MutableList<ArticleHierarchy>) : BaseQuickAdapter<ArticleHierarchy, BaseViewHolder>(R.layout.item_hierarchy,data) {
    var onItemClick:(data: ArticleHierarchy, view: View, position: Int) -> Unit =
        { _: ArticleHierarchy, _: View, _: Int -> }

    override fun convert(holder: BaseViewHolder, item: ArticleHierarchy) {
        holder.setText(R.id.item_system_title, item.name.toHtml())
        holder.getView<RecyclerView>(R.id.item_system_rv).run {
            val flexboxLayoutManager: FlexboxLayoutManager by lazy {
                FlexboxLayoutManager(context).apply {
                    //方向 主轴为水平方向，起点在左端
                    flexDirection = FlexDirection.ROW
                    //左对齐
                    justifyContent = JustifyContent.FLEX_START
                }
            }
            layoutManager = flexboxLayoutManager
            setHasFixedSize(true)
            setItemViewCacheSize(200)
            isNestedScrollingEnabled = false
            adapter = HierarchyChildAdapter(item.children).apply {
                setOnItemClickListener { _, view, position ->
                    onItemClick(item, view, position)
                }
            }
        }
    }
}