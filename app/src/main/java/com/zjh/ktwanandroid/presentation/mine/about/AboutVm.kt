package com.zjh.ktwanandroid.presentation.mine.about

import com.zjh.ktwanandroid.app.network.NetworkApi
import com.zjh.ktwanandroid.domain.repository.MineRepository
import com.zjh.ktwanandroid.domain.usecase.MineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import javax.inject.Inject

/**
 * @author zjh
 * 2022/8/2
 */
@HiltViewModel
class AboutVm @Inject constructor(private val mineRepository: MineRepository) : BaseViewModel(){
    fun loginOut(){
        NetworkApi.INSTANCE.cookieJar.clear()
        mineRepository.clearUserInfo()
    }

    fun isLogin(): Boolean{
        return try {
            mineRepository.getUserInfo()
            true
        }catch (e:Exception){
            false
        }
    }
}