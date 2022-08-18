# BrickUI的实现

## 背景

通过Kotlin的语法糖来实现声明式UI，由于基于传统的View体系，是直接通过构造方法去实现的。
本质上，首要解决的问题就是通过addView，把整个ViewTree建立起来。

## 原始方案

既然主要是需要调用addView吧ViewGroup下的所有子View加入到父View中。
最直接的思路，就是给父View里塞入子View，然后让父View负责调用addView即可。
这种方式，在调用端的代码风格与Flutter比较类似。

#### 具体实现代码（伪代码）

```kotlin
fun Context.frameLayout(
    block: (Context.() -> List<View>)
) = FrameLayout(this).apply {
    block().forEach { 
        addView(it)
    }
}

fun Context.textView(
    text: Text,
) = TextView(this).apply {
    this.text = text
}
```

#### 调用端代码（伪代码）

```kotlin
brick {
    frameLayout(
        children = {
            listOf(
                frameLayout(
                    children = {
                        listOf(
                            textView("Hello"),
                            textView("Brick"),
                        )
                    }
                )
            )
        }
    )
}
```

### 问题

实现是实现了，但是总觉得哪里不对。由于kotlin和dart相比，没有通过操作符[]去创建列表的写法，那每嵌套一层，都需要通过listOf函数去构建一个子View列表，整个冗余代码特别的多。
那么有没有办法实现得更像Compose，让Kotlin的语法优势充分实现，从而简化调用端的代码，优化使用者的体验呢
那么我们先来看看理想的调用端代码的写法

```kotlin
brick {
    frameLayout {
        frameLayout {
            textView("Hello")
            textView("Brick")
        }
    }
}
```

果然，理想中的代码，比起上一个方案而言，代码简化了不少，基本没有冗余代码，就描述清楚了布局，可是怎么实现呢？
上一种方案，父View持有子View列表，可以通过遍历子View列表的方式去执行addView。
而上述方式，如果针对单子布局的父View还可以通过让block返回子View的方式实现，但对于多子布局就无能为力了，肯定还是要用listOf函数把子View包裹起来

### 解决办法

既然正常的思路行不通，何不反其道而行。让子View持有父View，然后调用addView不就可以了？

是的，理论上可以，但要注意1点，就是在最外层，就没有这个父View的引用，最外层的View的创建仅需要Context

```kotlin

import javax.swing.text.View

/**
 * 存在父View，则构建后让父View去进行add
 */
fun ViewGroup.frameLayout(
    block: ViewGroup.() -> Unit
) = FrameLayout(context).apply {
    block()
}.also {
    addView(it)
}

fun ViewGroup.textView(
    text: String
) = TextView(context).apply {
    this.text = text
}.also {
    addView(it)
}
```

好的，通过这个方式看起来是完美实现，但还需要注意一个问题，那就是在整个Brick的最外层，是没有一个ViewGroup作为入口的，但Brick的内部，每一个View的构造方法都依赖一个ViewGroup传进来一个上下文Context。
于是有两种方式解决这个问题：

- 为每个View构建方法额外增加一个Context为receiver的方法。
- 用一个包装类作为receiver，其中包含一个context和一个ViewGroup，在Brick外部构建时，通过context构建；在Brick内部构建时通过ViewGroup构建。这样就确保了Context始终是有的，在最外层没有ViewGroup传进来，子View就不调用父View的addView即可

```kotlin

/**
 * 最外层没有父View，仅通过Context传递给最外层View来构建
 */
fun Context.frameLayout(
    block: Context.() -> View
) = FrameLayout(this).apply {
    block()
}
```

第一种方式主要的问题是增加了sdk的修改成本。算上live类型的构造方法，那么每增加或修改1个参数，就要改动4处。
第二种方式主要的问题是引入了冗余的包装类，且构建ViewGroup类型的View时，还是需要返回包装类，不能返回其本身。

```kotlin
class BrickWrapper {
    private val mContext: Context

    var view: View?
        private set

    constructor(view: View) {
        mContext = view.context
        this.view = view
    }

    constructor(context: Context) {
        mContext = context
        view = null
    }

    /**
     * 保证两种情况都能拿到context
     */
    val context: Context
        get() = view?.context ?: mContext

    /**
     * 如果存在父View，就把子View add到父View；
     * 如果不存在父View，则把子View放到view中向上（Activity、Fragment等）暴露
     */
    fun addView(view: View) {
        if (this.view == null) {
            this.view = view
        } else {
            this.view?.run {
                if (this is ViewGroup && view.parent == null) {
                    addView(view)
                }
            }
        }
    }
}

fun BrickWrapper.frameLayout(
    block: ViewGroup.() -> Unit
): BrickWrapper = FrameLayout(context).apply {
    block()
}.also {
    addView(it)
}.run {
    BrickWrapper(this)
}

fun BrickWrapper.textView(
    text: String
) = TextView(context).apply {
    this.text = text
}.also {
    addView(it)
}
```

最开始BrickUI是通过以上第二种方式实现的，但本着提高开发体验的出发点，宁愿牺牲sdk端有限的修改成本，来提高调用端的使用体验，这样才能让这套框架让更多人接受。所以后续选择了前者。