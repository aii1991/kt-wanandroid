package com.zjh.ktwanandroid.presentation.tree

import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.getActionButton
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.zjh.ktwanandroid.R
import com.zjh.ktwanandroid.app.base.BaseMVIFragment
import com.zjh.ktwanandroid.app.util.SettingUtil
import com.zjh.ktwanandroid.databinding.FragmentAddArticleBinding
import dagger.hilt.android.AndroidEntryPoint
import initClose
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import me.hgj.jetpackmvvm.ext.nav
import registerTextChangedListener

@AndroidEntryPoint
class AddArticleFragment : BaseMVIFragment<AddArticleVM,FragmentAddArticleBinding>() {
    override fun setupView(savedInstanceState: Bundle?) {
        mDatabind.includeToolbar.toolbar.run {
            initClose("分享文章") {
                nav().navigateUp()
            }
            inflateMenu(R.menu.share_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.share_guize -> {
                        activity?.let { activity ->
                            MaterialDialog(activity, BottomSheet())
                                .lifecycleOwner(viewLifecycleOwner)
                                .show {
                                    title(text = "温馨提示")
                                    customView(
                                        R.layout.customview,
                                        scrollable = true,
                                        horizontalPadding = true
                                    )
                                    positiveButton(text = "知道了")
                                    cornerRadius(16f)
                                    getActionButton(WhichButton.POSITIVE).updateTextColor(
                                        SettingUtil.getColor(activity)
                                    )
                                    getActionButton(WhichButton.NEGATIVE).updateTextColor(
                                        SettingUtil.getColor(activity)
                                    )
                                }
                        }

                    }
                }
                true
            }
        }
        mDatabind.shareUsername.text = mViewModel.userInfo?.nickname
        mDatabind.shareTitle.registerTextChangedListener(onTextChanged={
                charSequence: CharSequence?, _: Int, _: Int, _: Int->
            mViewModel.dispatchIntent(AddArticleIntent.InputTitle(charSequence.toString()))
        })
        mDatabind.shareUrl.registerTextChangedListener(onTextChanged={
                charSequence: CharSequence?, _: Int, _: Int, _: Int->
            mViewModel.dispatchIntent(AddArticleIntent.InputLink(charSequence.toString()))
        })
        mDatabind.shareSubmit.setOnClickListener {
            mViewModel.dispatchIntent(AddArticleIntent.AddArticle)
        }
    }

    override suspend fun observeUiState(coroutineScope: CoroutineScope) {
        mViewModel.mUiState.collectLatest {
            if (it.isSuccess){
                showToast("分享成功")
                mDatabind.shareTitle.setText("")
                mDatabind.shareUrl.setText("")
            }
        }
    }

}