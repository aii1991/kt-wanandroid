package com.zjh.ktwanandroid.app.skin

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.google.android.material.textfield.TextInputLayout
import com.zjh.ktwanandroid.app.skin.view.*
import skin.support.app.SkinLayoutInflater

/**
 * @author zjh
 * 2022/8/8
 */
class SkinMaterialViewInflater: SkinLayoutInflater {
    override fun createView(context: Context, name: String?, attrs: AttributeSet): View? {
        return when(name){
            "androidx.coordinatorlayout.widget.CoordinatorLayout"-> SkinMaterialCoordinatorLayout(context,attrs)
            "com.google.android.material.appbar.AppBarLayout"-> SkinMaterialAppBarLayout(context,attrs)
            "com.google.android.material.tabs.TabLayout"-> SkinMaterialTabLayout(context,attrs)
            "com.google.android.material.textfield.TextInputLayout"-> TextInputLayout(context,attrs)
            "com.google.android.material.textfield.TextInputEditText"-> TextInputLayout(context,attrs)
            "com.google.android.material.navigation.NavigationView"-> SkinMaterialNavigationView(context,attrs)
            "com.google.android.material.floatingactionbutton.FloatingActionButton"-> SkinMaterialFloatingActionButton(context, attrs)
            "com.google.android.material.bottomnavigation.BottomNavigationView"-> SkinMaterialBottomNavigationView(context,attrs)
            "com.google.android.material.appbar.CollapsingToolbarLayout"-> SkinMaterialCollapsingToolbarLayout(context,attrs)
            else -> null
        }
    }
}