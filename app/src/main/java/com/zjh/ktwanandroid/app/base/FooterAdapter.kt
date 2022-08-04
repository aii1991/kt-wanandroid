package com.zjh.ktwanandroid.app.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zjh.ktwanandroid.databinding.PagingFooterItemBinding
import me.hgj.jetpackmvvm.util.LogUtils

/**
 * @author zjh
 * 2022/6/8
 */
class FooterAdapter(private val retry:()->Unit) :
    LoadStateAdapter<FooterAdapter.FooterViewHolder>() {

    class FooterViewHolder(binding : PagingFooterItemBinding) : RecyclerView.ViewHolder(binding.root){
        var pagingBinding = binding
    }

    override fun onBindViewHolder(holder: FooterViewHolder, loadState: LoadState) {
        LogUtils.debugInfo("loadState===>$loadState")
        holder.pagingBinding.progressBar.isVisible = loadState is LoadState.Loading
        holder.pagingBinding.btRetry.isVisible = loadState is LoadState.Error
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterViewHolder {
        val binding = PagingFooterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.btRetry.setOnClickListener{
            retry()
        }
        return FooterViewHolder(binding)
    }
}