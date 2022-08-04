package me.hgj.jetpackmvvm.base.viewmodel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*
import me.hgj.jetpackmvvm.exception.ApiException
import kotlin.reflect.KProperty

/**
 * 作者　: hegaojian
 *
 * 时间　: 2019/12/12
 * 描述　: ViewModel的基类 使用ViewModel类，放弃AndroidViewModel，原因：用处不大 完全有其他方式获取Application上下文
 *
 */
open class BaseViewModel() : ViewModel() {

}



