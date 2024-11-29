# Change LOG

### 0.2.43

- [fix] liveTabLayout的data参数改为LiveData类型
- [fix] 修复设置阴影的blur较小，offset较大时，内容被裁剪的问题

### 0.2.40

- [fix] 修复loopPager嵌套在RecyclerView或ViewPager中时，进行自动动画翻页时的几个经典bug，如翻页动画丢失，动画卡在一半
- [fix] 修复liveLoopPager闪退问题，liveLoopPager的duration改为LiveData类型

### 0.2.36

- [feature] 为DrawableKTX增加简单的缓存机制，调用方需要设置cacheKey以使其生效，并需要保证cacheKey不变且唯一

### 0.2.35

- [improve] loop-pager在onStop时，停止轮播，onStart恢复轮播

### 0.2.34

- [feature] loop-pager提供live支持
- [feature] loop-pager支持设置滚动动画时长
- [feature] glideImage提供live支持

### 0.2.31

- [feature] 添加nestedScrollableChild，帮助解决ViewPager2等的嵌套导致的滑动冲突问题
- [improve] 为viewPager添加以Context为接收者的函数

### 0.2.30

- [feature] 添加brick函数用于嵌入普通View，废弃view函数
- [improve] 为ViewKTX中的函数均添加以Context为接收者的函数
- [feature] 封装loop-pager组件，提供轮播图和pager指示器的实现 （基于[CircleIndicator](https://github.com/ongakuer/CircleIndicator)实现）

### 0.2.23

- improve: 对于LiveData<Boolean>添加了toVisibility()扩展函数，inverse扩展属性，并重载了not操作符

### 0.2.22

- improve: 为资源相关扩展函数添加类型注解
- improve: RecyclerView,SmartRefresh等添加Receiver为Context的扩展函数

### 0.2.21

- improve: runAnimator和live组件允许传入LifecycleOwner，避免在Fragment使用时造成内存泄露

### 0.2.20

- improve: runAnimator增加生命周期监听并自动取消动画

### 0.2.19

- feature: 提供更易调用的`LiveData.bind(context, block)`和`LiveData.bindNotNull(context, block)`方法，方便开发者绑定LiveData到View。
- improve: 优化`LiveData.bind(context, block)`和`LiveData.bindNotNull(context, block)`方法的实现，如果value无变化，不会重复调用`block`方法
> **注意**，以上两个方法所接收的Context必须是FragmentActivity一类的`LifecycleOwner`，否则绑定无法生效

### 0.2.17

- feature: 统一为ViewGroup构造的View添加`margin`参数

### 0.2.16

- Improve: 移除`Fragment.fragmentPager()`方法，改用参数`parentFragment`参数来实现此功能

### 0.2.15

- Improve: `smartRefresh`提到单独module：com.github.robin8yeung.BrickUI:brick-ui-smart-refresh。**（注意：用到smartRefresh的话必须引入此依赖）**
- feature: 添加`Fragment.fragmentPager()`方法，来构造基于Fragment生命周期的fragmentPager
- feature: 统一增加`foreground`属性（系统版本23以上才支持）
- feature: 提供`livePlaceHolder()`构造占位View

### 0.2.14

- Improve: 为LiveData增加combine扩展函数，方便合并2个LiveData的值来处理UI状态

### 0.2.13

- Improve: 允许RecyclerView类控件接受非RecyclerItemData类型的数据，但仍然强烈建议传入RecyclerItemData类型的数据，以使用DiffUtil来更新RecyclerView

### 0.2.12

- New: 增加CoordinatorLayout相关的支持

### 0.2.11

- New: 增加TabLayout相关的支持

### 0.2.7

- Improve: 补充LiveLayoutKTX中几个Context的扩展布局函数

### 0.2.6

- Improve: 对几种Widget引入泛型，避免丢类型

### 0.2.5

- New: 增加sourceJar打包，避免使用时无法链接到源码

### 0.2.1

- New: 首次正式发布版本
