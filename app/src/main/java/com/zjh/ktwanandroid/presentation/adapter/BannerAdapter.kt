package com.zjh.ktwanandroid.presentation.adapter

import android.view.View
import android.widget.ImageView
import coil.load
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder
import com.zjh.ktwanandroid.R
import com.zjh.ktwanandroid.domain.model.Banner
import me.hgj.jetpackmvvm.util.LogUtils

/**
 * @author zjh
 * 2022/6/14
 */
class BannerAdapter() : BaseBannerAdapter<Banner, BannerAdapter.BannerViewHolder>(){

    override fun createViewHolder(itemView: View, viewType: Int): BannerViewHolder {
       return BannerViewHolder(view = itemView)
    }

    override fun onBind(holder: BannerViewHolder, data: Banner, position: Int, pageSize: Int) {
        holder.bindData(data,position,pageSize)
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.banner_item_home
    }

    inner class BannerViewHolder(view: View) : BaseViewHolder<Banner>(view){
        override fun bindData(data: Banner, position: Int, pageSize: Int) {
            itemView.findViewById<ImageView>(R.id.banner_home_img).load(data.imagePath)
        }
    }
}