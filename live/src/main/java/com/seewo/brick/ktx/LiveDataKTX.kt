package com.seewo.brick.ktx

import android.content.Context
import android.os.Looper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.seewo.brick.live.params.StaticData

/**
 * set方法用于对于MutableLiveData类型的LiveData设置value，并保证不会因为线程问题抛异常
 * get方法用于取非空值，简化代码。需调用者保证value不为null，否则请用value属性
 */
var <T> LiveData<T>.data: T
    set(value) {
        if (this is MutableLiveData<T>) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                this.value = value
            } else {
                postValue(value)
            }
        }
    }
    get() = value!!

fun <I, O> LiveData<I>.map(mapper: (I) -> O) =
    Transformations.map(this) {
        mapper(it)
    }

fun <I1, I2, O> LiveData<I1>.combine(liveData: LiveData<I2>, block: (I1?, I2?) -> O): LiveData<O> =
    MediatorLiveData<O>().apply {
        addSource(this@combine) { value = block(it, liveData.value) }
        addSource(liveData) { value = block(this@combine.value, it) }
    }

val <T> T.live: MutableLiveData<T>
    get() = MutableLiveData(this)

val <T> T?.static: LiveData<T>
    get() = StaticData(this)

fun <T> LiveData<T>.bind(lifecycleOwner: LifecycleOwner, binder: (T?) -> Unit) {
    if (this is StaticData) {
        binder(value)
    } else {
        observe(lifecycleOwner) {
            binder(it)
        }
    }
}

fun <T> LiveData<T>.bindNotNull(lifecycleOwner: LifecycleOwner, binder: (T) -> Unit) =
    bind(lifecycleOwner) {
        it ?: return@bind
        binder(it)
    }

fun <T> LiveData<T>.bind(context: Context, binder: (T?) -> Unit) {
    context.inMyLifecycle {
        if (this@bind is StaticData) {
            binder(value)
        } else {
            observe(this) {
                binder(it)
            }
        }
    }
}

fun <T> LiveData<T>.bindNotNull(context: Context, binder: (T) -> Unit) =
    bind(context) {
        it ?: return@bind
        binder(it)
    }
