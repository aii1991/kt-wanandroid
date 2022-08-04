package com.zjh.ktwanandroid.app.base

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.zjh.ktwanandroid.R


/**
 * @date：2021/5/17
 * @author fuusy
 * @instruction：Paging3Adapter的公共类，主要减少adapter的冗余代码。
 */
abstract class BasePagingAdapter<T : Any>(diffCallback: DiffUtil.ItemCallback<T>) :
    PagingDataAdapter<T, RecyclerView.ViewHolder>(diffCallback) {

    var headerViewCount: Int = 0

    companion object {
        private const val TAG = "BasePagingAdapter"
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position) ?: return
        (holder as BasePagingAdapter<*>.BaseViewHolder).bindNormalData(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder = BaseViewHolder(parent, viewType)
        //Item的点击事件
        holder.itemView.setOnClickListener {
            onItemClick(getItem(holder.layoutPosition - headerViewCount))
        }
        return holder
    }

    override fun getItemViewType(position: Int): Int {
        return getItemLayout(position)
    }

    /**
     * 子类获取layout
     */
    protected abstract fun getItemLayout(position: Int): Int

    /**
     * itemView的点击事件，子类实现
     */
    protected abstract fun onItemClick(data: T?)

    /**
     * 子类绑定数据
     */
    protected abstract fun bindData(helper: ItemHelper, data: T?)


    inner class BaseViewHolder(parent: ViewGroup, layout: Int) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(layout, parent, false)
    ) {
        private val helper: ItemHelper = ItemHelper(this)

        fun bindNormalData(item: Any?) {
            bindData(helper, item as T)
        }
    }


    /**
     * ItemView的辅助类
     */
    class ItemHelper(private val holder: BasePagingAdapter<*>.BaseViewHolder) {
        private val itemView = holder.itemView
        private val viewCache = SparseArray<View>()

        private fun <T: View> findViewById(@IdRes viewId: Int): T {
            var view = viewCache.get(viewId)
            if (view == null) {
                view = itemView.findViewById(viewId)
                if (view == null) {
                    throw NullPointerException("$viewId can not find this item！")
                }
                viewCache.put(viewId, view)
            }
            return view as T
        }

        fun getCurrLayoutPosition(): Int{
            return holder.layoutPosition
        }

        fun <T: View> getView(@IdRes viewId: Int): T{
            return findViewById<T>(viewId)
        }

        /**
         * TextView设置内容
         */
        fun setText(@IdRes viewId: Int, text: CharSequence?): ItemHelper {
            (findViewById<TextView>(viewId)).text = text
            return this
        }

        /**
         * 是否显示View
         * @param viewId
         * @param visible 是否显示 true=是 false=否
         * @param holdWH 隐藏时View宽高是否还有宽高 true=有 false=否
         */
        fun isVisible(@IdRes viewId: Int, visible: Boolean,holdWH: Boolean = false){
            findViewById<View>(viewId).visibility = when {
                visible -> View.VISIBLE
                holdWH -> View.INVISIBLE
                else -> View.GONE
            }
        }

        /**
         * Coil加载图片
         */
        fun loadImg(@IdRes viewId: Int, url: String) {
            val imageView: ImageView = findViewById(viewId)
            imageView.load(url) {
                placeholder(R.drawable.img_placeholder)
            }
        }
    }


}