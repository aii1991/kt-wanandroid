import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder
import com.zjh.ktwanandroid.App
import com.zjh.ktwanandroid.R
import com.zjh.ktwanandroid.app.base.BasePagingAdapter
import com.zjh.ktwanandroid.app.util.SettingUtil
import com.zjh.ktwanandroid.app.widget.loadcallback.EmptyCallback
import com.zjh.ktwanandroid.app.widget.loadcallback.ErrorCallback
import com.zjh.ktwanandroid.app.widget.loadcallback.LoadingCallback
import com.zjh.ktwanandroid.app.widget.magicindicator.ScaleTransitionPagerTitleView
import com.zjh.ktwanandroid.presentation.home.HomeFragment
import com.zjh.ktwanandroid.presentation.mine.MimeFragment
import com.zjh.ktwanandroid.presentation.project.ProjectFragment
import com.zjh.ktwanandroid.presentation.publicnumber.PublicNumberFragment
import com.zjh.ktwanandroid.presentation.tree.TreeFragment
import jp.wasabeef.recyclerview.animators.BaseItemAnimator
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import me.hgj.jetpackmvvm.ext.util.toHtml
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

/**
 * 初始化普通的toolbar 只设置标题
 */
fun Toolbar.init(titleStr: String = ""): Toolbar {
    setBackgroundColor(SettingUtil.getColor(App.instance))
    title = titleStr
    return this
}

/**
 * 初始化有返回键的toolbar
 */
fun Toolbar.initClose(
    titleStr: String = "",
    backImg: Int = R.drawable.ic_back,
    onBack: (toolbar: Toolbar) -> Unit
): Toolbar {
    setBackgroundColor(SettingUtil.getColor(App.instance))
    title = titleStr.toHtml()
    setNavigationIcon(backImg)
    setNavigationOnClickListener { onBack.invoke(this) }
    return this
}

fun ViewPager2.initMain(fragment: Fragment): ViewPager2 {
    //是否可滑动
    this.isUserInputEnabled = false
    this.offscreenPageLimit = 5
    //设置适配器
    adapter = object : FragmentStateAdapter(fragment) {
        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> {
                    return HomeFragment()
                }
                1 -> {
                    return ProjectFragment()
                }
                2 -> {
                    return TreeFragment()
                }
                3 -> {
                    return PublicNumberFragment()
                }
                4 -> {
                    return MimeFragment()
                }
                else -> {
                    return HomeFragment()
                }
            }
        }
        override fun getItemCount() = 5
    }
    return this
}

fun MagicIndicator.bindViewPager2(
    viewPager: ViewPager2,
    mStringList: List<String> = arrayListOf(),
    action: (index: Int) -> Unit = {}) {
    val commonNavigator = CommonNavigator(App.instance)
    commonNavigator.adapter = object : CommonNavigatorAdapter() {

        override fun getCount(): Int {
            return  mStringList.size
        }
        override fun getTitleView(context: Context, index: Int): IPagerTitleView {
            return ScaleTransitionPagerTitleView(App.instance).apply {
                //设置文本
                text = mStringList[index].toHtml()
                //字体大小
                textSize = 17f
                //未选中颜色
                normalColor = Color.WHITE
                //选中颜色
                selectedColor = Color.WHITE
                //点击事件
                setOnClickListener {
                    viewPager.currentItem = index
                    action.invoke(index)
                }
            }
        }
        override fun getIndicator(context: Context): IPagerIndicator {
            return LinePagerIndicator(context).apply {
                mode = LinePagerIndicator.MODE_EXACTLY
                //线条的宽高度
                lineHeight = UIUtil.dip2px(App.instance, 3.0).toFloat()
                lineWidth = UIUtil.dip2px(App.instance, 30.0).toFloat()
                //线条的圆角
                roundRadius = UIUtil.dip2px(App.instance, 6.0).toFloat()
                startInterpolator = AccelerateInterpolator()
                endInterpolator = DecelerateInterpolator(2.0f)
                //线条的颜色
                setColors(Color.WHITE)
            }
        }
    }
    this.navigator = commonNavigator

    viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            this@bindViewPager2.onPageSelected(position)
            action.invoke(position)
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            this@bindViewPager2.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            this@bindViewPager2.onPageScrollStateChanged(state)
        }
    })
}


fun ViewPager2.init(
    fragment: Fragment,
    fragments: ArrayList<Fragment>,
    isUserInputEnabled: Boolean = true
): ViewPager2 {
    //是否可滑动
    this.isUserInputEnabled = isUserInputEnabled
    //设置适配器
    adapter = object : FragmentStateAdapter(fragment) {
        override fun createFragment(position: Int) = fragments[position]
        override fun getItemCount() = fragments.size
    }
    return this
}

fun BottomNavigationView.init(navigationItemSelectedAction: (Int) -> Unit): BottomNavigationView {
    itemIconTintList = SettingUtil.getColorStateList(SettingUtil.getColor(App.instance))
    itemTextColor = SettingUtil.getColorStateList(App.instance)
    setOnItemSelectedListener {
        navigationItemSelectedAction.invoke(it.itemId)
        true
    }
    return this
}

/**
 * 拦截BottomNavigation长按事件 防止长按时出现Toast ---- 追求完美的大屌群友提的bug
 * @receiver BottomNavigationViewEx
 * @param ids IntArray
 */
fun BottomNavigationView.interceptLongClick(vararg ids:Int) {
    val bottomNavigationMenuView = this.getChildAt(0) as ViewGroup
    for (index in ids.indices){
        val child = bottomNavigationMenuView.getChildAt(index)
        val view = child.findViewById<View>(ids[index])
        view?.setOnLongClickListener {
            true
        }
    }

}

fun SwipeRecyclerView.init(layoutManger: RecyclerView.LayoutManager, bindAdapter: BasePagingAdapter<*>,footerAdapter: LoadStateAdapter<out RecyclerView.ViewHolder>,isScroll:Boolean = true):SwipeRecyclerView{
    layoutManager = layoutManger
    setHasFixedSize(true)
    val stateAdapter = bindAdapter.withLoadStateFooter(footerAdapter)
    adapter = stateAdapter
//    setItemAnimator()
    isNestedScrollingEnabled = isScroll
    return this
}

fun SwipeRecyclerView.init(
    layoutManger: RecyclerView.LayoutManager,
    bindAdapter: RecyclerView.Adapter<*>,
    isScroll: Boolean = true
): SwipeRecyclerView {
    layoutManager = layoutManger
    setHasFixedSize(true)
    adapter = bindAdapter
    setItemAnimator()
    isNestedScrollingEnabled = isScroll
    return this
}

fun SwipeRecyclerView.setItemAnimator(animator: BaseItemAnimator = ScaleInAnimator(BounceInterpolator())){
    itemAnimator = animator
    itemAnimator?.let {
        it.addDuration = 300
        it.removeDuration = 300
        it.moveDuration = 300
        it.changeDuration = 300
    }
}

fun <T,VH : BaseViewHolder<T>> BannerViewPager<T,VH>.init(baseBannerAdapter: BaseBannerAdapter<T,VH>,lifecycleOwner: LifecycleOwner,clickPageAction:(itemData: T)->Unit):BannerViewPager<T,VH>{
    adapter = baseBannerAdapter
    setLifecycleRegistry(lifecycleOwner.lifecycle)
    setOnPageClickListener {
        clickPageAction.invoke(data[it])
    }
    create()
    return this
}

//设置适配器的列表动画
fun BaseQuickAdapter<*, *>.setAdapterAnimation(mode: Int) {
    //等于0，关闭列表动画 否则开启
    if (mode == 0) {
        this.animationEnable = false
    } else {
        this.animationEnable = true
        this.setAnimationWithDefault(BaseQuickAdapter.AnimationType.values()[mode - 1])
    }
}


fun RecyclerView.initFloatBtn(floatBtn: FloatingActionButton) {
    //监听recyclerview滑动到顶部的时候，需要把向上返回顶部的按钮隐藏
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        @SuppressLint("RestrictedApi")
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (!canScrollVertically(-1)) {
                floatBtn.visibility = View.INVISIBLE
            }
        }
    })
    floatBtn.backgroundTintList = SettingUtil.getOneColorStateList(App.instance)
    floatBtn.setOnClickListener {
        val layoutManager = layoutManager as LinearLayoutManager
        //如果当前recyclerview 最后一个视图位置的索引大于等于40，则迅速返回顶部，否则带有滚动动画效果返回到顶部
        if (layoutManager.findLastVisibleItemPosition() >= 40) {
            scrollToPosition(0)//没有动画迅速返回到顶部(马上)
        } else {
            smoothScrollToPosition(0)//有滚动动画返回到顶部(有点慢)
        }
    }
}

fun SwipeRefreshLayout.initColorScheme(){
    setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent,R.color.chinaHoliDay)
}

//初始化 SwipeRefreshLayout
fun SwipeRefreshLayout.init(onRefreshListener: () -> Unit) {
    this.run {
        setOnRefreshListener {
            onRefreshListener.invoke()
        }
        //设置主题颜色
        initColorScheme()
    }
}

fun TextView.registerTextChangedListener(beforeTextChanged: (charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) -> Unit = { _, _, _, _ ->},
                                           onTextChanged: (charSequence: CharSequence?, p1: Int, p2: Int, p3: Int)->Unit = { _, _, _, _ ->},
                                           afterTextChanged: (editable: Editable?)-> Unit = { _->}){
    addTextChangedListener(object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            beforeTextChanged(p0, p1, p2, p3)
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
           onTextChanged(p0, p1, p2, p3)
        }

        override fun afterTextChanged(p0: Editable?) {
            afterTextChanged(p0)
        }

    })
}

fun loadServiceInit(view: View, callback: () -> Unit): LoadService<Any> {
    val loadSir = LoadSir.getDefault().register(view) {
        //点击重试时触发的操作
        callback.invoke()
    }
    loadSir.showSuccess()
    SettingUtil.setLoadingColor(SettingUtil.getColor(App.instance), loadSir)
    return loadSir
}

/**
 * 作者　: hegaojian
 * 时间　: 2020/2/20
 * 描述　:项目中自定义类的拓展函数
 */
fun LoadService<*>.setErrorText(message: String) {
    if (message.isNotEmpty()) {
        this.setCallBack(ErrorCallback::class.java) { _, view ->
            view.findViewById<TextView>(R.id.error_text).text = message
        }
    }
}

/**
 * 设置错误布局
 * @param message 错误布局显示的提示内容
 */
fun LoadService<*>.showError(message: String = "") {
    this.setErrorText(message)
    this.showCallback(ErrorCallback::class.java)
}

/**
 * 设置空布局
 */
fun LoadService<*>.showEmpty() {
    this.showCallback(EmptyCallback::class.java)
}

/**
 * 设置加载中
 */
fun LoadService<*>.showLoading() {
    this.showCallback(LoadingCallback::class.java)
}


/**
 * 隐藏软键盘
 */
fun hideSoftKeyboard(activity: Activity?) {
    activity?.let { act ->
        val view = act.currentFocus
        view?.let {
            val inputMethodManager =
                act.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}