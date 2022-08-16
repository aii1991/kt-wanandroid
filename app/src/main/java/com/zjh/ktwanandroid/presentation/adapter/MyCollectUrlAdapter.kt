package com.zjh.ktwanandroid.presentation.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zjh.ktwanandroid.R
import com.zjh.ktwanandroid.app.widget.collectview.CollectView
import com.zjh.ktwanandroid.domain.model.CollectUrl
import me.hgj.jetpackmvvm.ext.util.toHtml

/**
 * @author zjh
 * 2022/8/1
 */
class MyCollectUrlAdapter(data: ArrayList<CollectUrl>) : BaseQuickAdapter<CollectUrl, BaseViewHolder>(R.layout.item_collect_url,data) {

    private var collectAction: (item: CollectUrl, v: CollectView, position: Int) -> Unit =
        { _: CollectUrl, _: CollectView, _: Int -> }

    override fun convert(holder: BaseViewHolder, item: CollectUrl) {
        item.run {
            holder.setText(R.id.item_collecturl_name, name.toHtml())
            holder.setText(R.id.item_collecturl_link, link)
            holder.getView<CollectView>(R.id.item_collect_url_collect).isChecked = true
        }
        holder.getView<CollectView>(R.id.item_collect_url_collect)
            .setOnCollectViewClickListener(object : CollectView.OnCollectViewClickListener {
                override fun onClick(v: CollectView) {
                    collectAction.invoke(item, v, holder.adapterPosition)
                }
            })
    }

    fun setCollectClick(inputCollectAction: (item: CollectUrl, v: CollectView, position: Int) -> Unit) {
        this.collectAction = inputCollectAction
    }
}