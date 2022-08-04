package com.zjh.ktwanandroid.presentation.mine.login

import android.os.Bundle
import android.text.InputType
import com.zjh.ktwanandroid.R
import com.zjh.ktwanandroid.app.base.BaseMVIFragment
import com.zjh.ktwanandroid.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import initClose
import kotlinx.coroutines.CoroutineScope
import me.hgj.jetpackmvvm.ext.nav
import registerTextChangedListener

@AndroidEntryPoint
class LoginFragment : BaseMVIFragment<LoginVM, FragmentLoginBinding>() {

    override fun setupView(savedInstanceState: Bundle?) {
        mDatabind.includeToolbar.toolbar.initClose { nav().navigateUp() }
    }

    override fun bindListener(){
        mDatabind.apply {
            etUserName.registerTextChangedListener(onTextChanged = { charSequence, _, _, _ ->
                mViewModel.dispatchIntent(LoginIntent.InputUserName(userName = charSequence.toString()))
            })
            etPwd.registerTextChangedListener(onTextChanged = { charSequence, _, _, _ ->
                mViewModel.dispatchIntent(LoginIntent.InputPwd(pwd = charSequence.toString()))
            })
            cbPwd.setOnCheckedChangeListener { _, isChecked ->
                mViewModel.dispatchIntent(LoginIntent.ShowPwd(isShow = isChecked))
            }
            ivClearUserName.setOnClickListener{
                mViewModel.dispatchIntent(LoginIntent.ClearUserName)
            }
            tvSignIn.setOnClickListener {
                mViewModel.dispatchIntent(LoginIntent.Login)
            }
            tvRegister.setOnClickListener {
                nav().navigate(R.id.registerFragment)
            }
        }
    }

    override suspend fun observeUiState(coroutineScope: CoroutineScope){
        mViewModel.mUiState.collect {
            it.apply {
                setUserName(userName)
                showPwd(isShowPwd)
                navUp(isLoginSuccess)
            }
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

    private fun navUp(isLoginSuccess: Boolean){
        if(!isLoginSuccess) return
        nav().navigateUp()
    }

}