package com.zjh.ktwanandroid.app.widget.loadcallback


import com.kingja.loadsir.callback.Callback
import com.zjh.ktwanandroid.R


class EmptyCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_empty
    }

}