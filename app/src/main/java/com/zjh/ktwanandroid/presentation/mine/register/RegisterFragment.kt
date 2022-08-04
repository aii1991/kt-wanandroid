package com.zjh.ktwanandroid.presentation.mine.register

import android.os.Bundle
import android.text.InputType
import androidx.lifecycle.lifecycleScope
import com.zjh.ktwanandroid.app.base.BaseMVIFragment
import com.zjh.ktwanandroid.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import initClose
import kotlinx.coroutines.*
import me.hgj.jetpackmvvm.ext.nav
import registerTextChangedListener

@AndroidEntryPoint
class RegisterFragment : BaseMVIFragment<RegisterVM,FragmentRegisterBinding>() {
    override fun setupView(savedInstanceState: Bundle?) {
        mDatabind.includeToolbar.toolbar.initClose { nav().navigateUp() }
    }

    override fun bindListener(){
        mDatabind.apply {
            etUserName.registerTextChangedListener(onTextChanged = { charSequence, _, _, _ ->
                mViewModel.dispatchIntent(RegisterIntent.InputUserName(charSequence.toString()))
            })
            etPwd.registerTextChangedListener(onTextChanged = { charSequence, _, _, _ ->
                mViewModel.dispatchIntent(RegisterIntent.InputPwd(charSequence.toString()))
            })
            etPwdConfirm.registerTextChangedListener(onTextChanged = { charSequence, _, _, _ ->
                mViewModel.dispatchIntent(RegisterIntent.InputPwdConfirm(charSequence.toString()))
            })
            cbPwd.setOnCheckedChangeListener { _, isChecked ->
                mViewModel.dispatchIntent(RegisterIntent.ShowPwd(isChecked))
            }
            cbPwdConfirm.setOnCheckedChangeListener { _, isChecked ->
                mViewModel.dispatchIntent(RegisterIntent.ShowPwdConfirm(isChecked))
            }
            ivClearUserName.setOnClickListener{
                mViewModel.dispatchIntent(RegisterIntent.ClearUserName)
            }
            tvRegister.setOnClickListener {
                mViewModel.dispatchIntent(RegisterIntent.Register)
            }

        }

    }

    override suspend fun observeUiState(coroutineScope: CoroutineScope) {
        mViewModel.mUiState.collect{
            setUserName(it.userName)
            showPwd(it.isShowPwd)
            showConfirmPwd(it.isShowConfirmPwd)
            navUp(it.isRegisterSuccess)
        }
    }

    private fun setUserName(userName: String){
        mDatabind.apply {
            if(etUserName.text.toString() != userName){
                etUserName.setText(userName)
            }
        }
    }

    private fun showPwd(isShowPwd: Boolean){
        mDatabind.apply {
            etPwd.inputType = if(isShowPwd) InputType.TYPE_CLASS_TEXT or
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else InputType.TYPE_CLASS_TEXT or
                    InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
    }

    private fun showConfirmPwd(isShowPwd: Boolean){
        mDatabind.apply {
            etPwdConfirm.inputType = if(isShowPwd) InputType.TYPE_CLASS_TEXT or
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else InputType.TYPE_CLASS_TEXT or
                    InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
    }

    private fun navUp(isRegisterSuccess: Boolean){
        if(!isRegisterSuccess) return
        showToast("注册成功")
        lifecycleScope.launch() {
            withContext(Dispatchers.IO){
                delay(1000)
            }
            nav().navigateUp()
        }

    }

}