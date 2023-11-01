# Change LOG

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
