# BrickUI中RecyclerView的实现

## 背景
RecyclerView是Android 5.0提出的新UI控件，可以用来代替传统的ListView、GridView等。其具备以下优点：

- 默认实现了ItemView的回收复用机制
- 默认实现局部更新
- 默认实现增删item动画效果，容易实现拖拽和侧滑删除
- 可以通过LayoutManager轻松实现或切换多种布局策略

虽然RecyclerView功能更强大，但使用起来，在大部分地方都比ListView等更简单。
即便如此，要支棱起来一个RecyclerView，还是要构建几个类，而且基本每一个列表都需要构建这两个类：

- RecyclerView.Adapter: 配置数据与item的关系，配置ViewType和ItemView映射关系
- RecyclerView.ViewHolder: 可回收的ItemView容器

## BrickUI的实现目标

因为开发过Flutter，了解到了Flutter要实现一个列表控件有多开心。于是BrickUI作为声明式UI框架，也希望通过这样的方式去实现
但最终实现下来，发现并不这么容易做到，因为两者理念不尽相同。

- RecyclerView的实现思路是onBindViewHolder时，拿到循环使用的ViewHolder模板，把item数据更新到这个ViewHolder
- Flutter的实现思路是控件渲染和代码实现的Widget是隔离的，具体渲染与WidgetTree并不相关，WidgetTree只提供某状态下的数据映射即可

BrickUI为了保证容易实现和兼容传统View，实际仍是基于传统View的方式实现，那就很难基于Flutter的思想去实现。

## 初步实现

那么先硬着头皮实现一次，声明函数如下

```kotlin
/**
 * 构造RecyclerView，构建方法较复杂，但可以充分利用RecyclerView的重用特性，性能高
 *
 * @param data 列表数据
 * @param diffCallback 区别回调，如果设置了，可以计算前后差别，对列表只更新有差异部分；否则数据变化时全量刷新
 * @param viewTypeBuilder 列表index与ViewType的映射关系。如果列表只存在一种ItemView可以不传
 * @param viewBuilder ViewType与View的映射关系，这里的View将作为复用模板。ItemView模板必须用recyclerItem包裹，否则会抛异常
 * @param dataBinder 绑定每一个Item的数据到ItemView模板。回调输入为数据列表，列表index和viewBuilder所创建的View模板
 *
 * @see BrickWrapper.recyclerItem viewBuilder传入的ItemView模板必须用recyclerItem包裹，否则会抛异常
 * @see BrickWrapper.simpleRecyclerView 构造方法更简单
 */
fun <T: RecyclerItemData> BrickWrapper.recyclerView(
    width: Int, height: Int,
    @AttrRes attr: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    fitsSystemWindows: Boolean = false,

    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(
        context, LinearLayoutManager.VERTICAL, false),
    itemDecoration: RecyclerView.ItemDecoration? = null,
    data: List<T>? = null,
    onClick: View.OnClickListener? = null,

    viewTypeBuilder: (Int) -> Int = { 0 },
    viewBuilder: (Int) -> View,
    dataBinder: (List<T>, Int, View) -> Unit,
)
```

相当于把onCreateViewHolder，onBindViewHolder，放到声明函数的属性中做外显实现。

- 优点
  - 性能与传统方式使用RecyclerView基本无异
  - viewBuilder中可以用声明式UI来实现ItemView
  - 如果只有1中ViewType，可以不实现viewTypeBuilder
- 缺点
  - 配置ItemView仍然太复杂
  - viewBuilder中的View不能立刻add到parent中，需要给它一个特殊的BrickWrapper包裹，即recyclerItem
  - dataBinder中需要通过原始的findViewById的方式去把data配置到view上，这样的实现和BrickUI的初心相违背

## 有办法简化吗？

如果ViewType并不太多，能否所有的ItemView都用同一个ViewHolder。
当更新这个ViewHolder时，隐藏这个ViewHolder所有的子View，如果当前ItemView没有创建，则通过声明函数创建ItemView后插入到这个ViewHolder中，则插入；
如果已插入了，通过id去从ViewHolder中find到View来进行相应操作

- 优点
  - 基本达到目标，和Flutter的声明函数类似了，只需要一个builder即可，不需要viewTypeBuilder、viewBuilder、dataBinder三剑客
  - ViewType不多的情况下，性能上还ok，RecyclerView和回收复用还是说得过去的，作为取舍，是可以接受的。
- 缺点
  - builder中的声明UI，每一个View都必须声明id，否则会出现bug，这就把坑挖到了业务端

## 那还有其他办法么？

OK，我们不能给业务端挖坑，如何找到更好的方式？还得做取舍。
既然不想通过id去拿已添加到ViewHolder的View，那么也不难，每次更新ViewHolder时删除所有子View即可。
这样，每次更新ViewHolder时就都是创建ItemView，这样完美契合BrickUI的实现思路，不再依赖id。
对每个ItemView都进行View创建，确实会造成很多临时对象的创建，会有一定的内存抖动，
但在低性能机器上（vivo x6 Android 5.1）跑起来，对比传统实现没有明显的体验下降。对比目前的Flutter和Compose还是有明显改善的。

具体声明函数如下：

```kotlin
/**
 * 构造RecyclerView，构建方法较简单，但基本无法利用RecyclerView的重用特性，滚动会造成内存都懂。谨慎使用
 *
 * @param data 列表数据
 * @param block 针对每一个Item的数据到创建ItemView。回调输入为数据列表，列表index
 *
 * @see BrickWrapper.recyclerView 性能开销更少
 */
fun <T> BrickWrapper.simpleStatelessRecyclerView(
    width: Int, height: Int,
    @AttrRes attr: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    fitsSystemWindows: Boolean = false,
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(
        context, LinearLayoutManager.VERTICAL, false
    ),
    itemDecoration: RecyclerView.ItemDecoration? = null,
    data: List<T>? = null,
    onClick: OnClickListener? = null,
    block: BrickWrapper.(List<T>, Int) -> Unit,
)
```

## 让列表动起来

以上声明函数名包含stateless，可见BrickUI并不希望业务端通过这个方式去构建一个数据会发生变化的列表，只是希望做一个最简单的实现。
但大部分列表，我们肯定是需要它能根据数据变化来组织列表，那么BrickUI也提供了基于LiveData的实现。声明函数如下：
```kotlin
/**
 * 构造RecyclerView，构建方法较简单，但基本无法利用RecyclerView的重用特性，滚动会造成内存都懂。谨慎使用
 *
 * @param data 列表数据
 * @param diffCallback 区别回调，如果设置了，可以计算前后差别，对列表只更新有差异部分；否则数据变化时全量刷新
 * @param block 针对每一个Item的数据到创建ItemView。回调输入为数据列表，列表index。不需要包裹recyclerItem。
 *
 * @see BrickWrapper.recyclerView 性能开销更少
 */
fun <T: RecyclerItemData> BrickWrapper.liveSimpleRecyclerView(
    width: Int, height: Int,
    @AttrRes attr: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    fitsSystemWindows: Boolean = false,
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(
        context, LinearLayoutManager.VERTICAL, false),
    itemDecoration: RecyclerView.ItemDecoration? = null,
    data: LiveData<List<T>>? = null,
    onClick: View.OnClickListener? = null,
    block: BrickWrapper.(List<T>, Int) -> Unit,
)
```

与simpleStatelessRecyclerView有区别的地方，除了data改成了一个LiveData，有个区别是数据的泛型T需要实现RecyclerItemData接口。
为什么呢？来看下RecyclerItemData的定义
```kotlin
interface RecyclerItemData {
    /**
     * 比较两个RecyclerItemData是否为同一个item（如对应同一个id）
     * 如果是同一项，则如果不相等，认为是同一项需要更新内容；否则认为不是同一项
     */
    fun areSameItem(out: Any?): Boolean
}
```

为什么需要实现这个接口呢，因为我们的数据更新是依赖于DiffUtil.Callback的，那么我们需要告诉代码，怎样的两个item是同一项，怎样的两个item内容是相同的。
这些判断影响着列表内容的更新动画。
怎样的两个item是同一项就是RecyclerItemData.areSameItem要实现的，怎样的两个item内容是相同的可以通过重写equals方法来实现。

这种实现方式最大的坑，在社区中显现出来的是，由于我们刷新前和刷新后的列表顺序可能出现变化，那么在刷新时可能会出现诡异的item移动动画。
目前只能通过对data先赋值为一个空表，在赋值到实际刷新出来的数据。但这样在数据排序没变化时，还是会闪一下。
两害相衡趋其轻，最后还是妥协了后者。

```java
public abstract static class Callback {
    /**
     * Returns the size of the old list.
     *
     * @return The size of the old list.
     */
    public abstract int getOldListSize();

    /**
     * Returns the size of the new list.
     *
     * @return The size of the new list.
     */
    public abstract int getNewListSize();

    /**
     * Called by the DiffUtil to decide whether two object represent the same Item.
     * <p>
     * For example, if your items have unique ids, this method should check their id equality.
     *
     * @param oldItemPosition The position of the item in the old list
     * @param newItemPosition The position of the item in the new list
     * @return True if the two items represent the same object or false if they are different.
     */
    public abstract boolean areItemsTheSame(int oldItemPosition, int newItemPosition);

    /**
     * Called by the DiffUtil when it wants to check whether two items have the same data.
     * DiffUtil uses this information to detect if the contents of an item has changed.
     * <p>
     * DiffUtil uses this method to check equality instead of {@link Object#equals(Object)}
     * so that you can change its behavior depending on your UI.
     * For example, if you are using DiffUtil with a
     * {@link RecyclerView.Adapter RecyclerView.Adapter}, you should
     * return whether the items' visual representations are the same.
     * <p>
     * This method is called only if {@link #areItemsTheSame(int, int)} returns
     * {@code true} for these items.
     *
     * @param oldItemPosition The position of the item in the old list
     * @param newItemPosition The position of the item in the new list which replaces the
     *                        oldItem
     * @return True if the contents of the items are the same or false if they are different.
     */
    public abstract boolean areContentsTheSame(int oldItemPosition, int newItemPosition);

    /**
     * When {@link #areItemsTheSame(int, int)} returns {@code true} for two items and
     * {@link #areContentsTheSame(int, int)} returns false for them, DiffUtil
     * calls this method to get a payload about the change.
     * <p>
     * For example, if you are using DiffUtil with {@link RecyclerView}, you can return the
     * particular field that changed in the item and your
     * {@link RecyclerView.ItemAnimator ItemAnimator} can use that
     * information to run the correct animation.
     * <p>
     * Default implementation returns {@code null}.
     *
     * @param oldItemPosition The position of the item in the old list
     * @param newItemPosition The position of the item in the new list
     *
     * @return A payload object that represents the change between the two items.
     */
    @Nullable
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return null;
    }
}
```