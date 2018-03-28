# MVVM
MVVM学习总结：
在学习MVVM之前，我们先简单总结一下MVC与MVP
https://blog.csdn.net/chun_long/article/details/52086565
MVC框架：
![mvc框架](https://github.com/flyingtercel/MVVM/blob/master/mvvm/src/main/res/drawable/mvc.png)
```
    M-Model : 业务逻辑和实体模型(biz/bean)
    V-View : 布局文件(XML)
    C-Controller : 控制器(Activity)
这个也是初学者最常用的框架，该框架虽然也是把代码逻辑和UI层分离，但是View层能做的事情还是很少的，很多对于页面的呈现还是交由C实现，这样会导致项目中C的代码臃肿，如果项目小，代码臃肿点还是能接受的，但是随着项目的不断迭代，代码量的增加，你就会没办法忍受该框架开发的项目，这时MVP框架就应运而生。
```
MVP框架：
![mvp框架](https://github.com/flyingtercel/MVVM/blob/master/mvvm/src/main/res/drawable/mvp.png)
```
   M-Model : 业务逻辑和实体模型(biz/bean)
   V-View : 布局文件(XML)和Activity
   P-Presenter : 完成View和Model的交互
   MVP框架相对于MVC框架做了较大的改变，将Activity当做View使用，代替MVC框架中的C的是P，对比MVC和MVP的模型图可以发现变化最大的是View层和Model层不在直接通信，所有交互的工作都交由Presenter层来解决。既然两者都通过Presenter来通信，为了复用和可拓展性，MVP框架基于接口设计的理念大家自然就可以理解其用意。
   但MVP框架也有不足之处:
   1.接口过多，一定程度影响了编码效率。
   2.业务逻辑抽象到Presenter中，较为复杂的界面Activity代码量依然会很多。
   3.导致Presenter的代码量过大。
```
MVVM框架：
![mvvm框架](https://github.com/flyingtercel/MVVM/blob/master/mvvm/src/main/res/drawable/mvvm.png)
```
    M-Model : 实体模型(biz/bean)
    V-View : 布局文件(XML)
    VM-ViewModel : DataBinding所在之处，对外暴露出公共属性，View和Model的绑定器
    对比MVP和MVVM模型图可以看出，他们之间区别主要体现在以下两点：
    1.    可重用性。你可以把一些视图逻辑放在一个ViewModel里面，让很多View重用这段视图逻辑。 在Android中，布局里可以进行一个视图逻辑，并且Model发生变化，View也随着发生变化。
    2.    低耦合。以前Activity、Fragment中需要把数据填充到View，还要进行一些视图逻辑。现在这些都可在布局中完成（具体代码请看后面） 甚至都不需要再Activity、Fragment去findViewById（）。这时候Activity、Fragment只需要做好的逻辑处理就可以了。
```
一：在build.gradle中添加如下依赖
```
    android {
        ...
        dataBinding {
            enabled true
        }
```
下面来比较一下布局与之前大家常用的格式的区别：
```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">
   <data>
       <variable name="user" type="com.example.User"/>
   </data>
   <LinearLayout
       android:orientation="vertical"
       android:layout_width="match_parent"
       android:layout_height="match_parent">
       <TextView android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:text="@{user.firstName}"/>
       <TextView android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:text="@{user.lastName}"/>
   </LinearLayout>
</layout>
```
1:先将根布局设置为layout
2:在布局里引入modle中的数据类
```
    <variable name="user" type="com.example.User"/>（还有一种写法将在后面代码中介绍）
```
3:设置布局属性值：通过@{user.name}语法；
```
    <TextView android:layout_width="wrap_content"
              android:layout_height="wrap_content"
              android:text="@{user.firstName}"/>
```
4:数据实体类的创建：
```
public class User01 extends BaseObservable{
    private String name;
    private String pass;
    public User01(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }
    @Bindable
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
    @Bindable
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
        notifyPropertyChanged(BR.pass);
    }
}
```
在Activity中进行数据的绑定：
```
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       MainActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
       User user = new User("Test", "User");
       binding.setUser(user);
    }
    //or
    MainActivityBinding binding = MainActivityBinding.inflate(getLayoutInflater());

```
如果你使用的是ListView或者RecyclerView去显示界面，这时候在Items布局中使用Data Binding，在Adapter中你可以这样获取binding：
```
    ListItemBinding binding = ListItemBinding.inflate(layoutInflater, viewGroup, false);
    //or
    ListItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item, viewGroup, false);
```
接下来就是事件的实现，在我们以往的使用中对于事件的实现都是android:onClick或者在代码中使用View.setOnClickListener（）来实现点击事件，在这里将有一种新的实现方式：
要将事件分配给它的处理程序，使用一个正常的绑定表达式，以值作为调用的方法名称。例如：你的数据对象有两种方法
```
    public class MyHandlers {
        public void onClickFriend(View view) { ... }
        public void onClickEnemy(View view) { ... }
    }
```
绑定表达式可以为视图指定单击事件监听器
```
    <?xml version="1.0" encoding="utf-8"?>
    <layout xmlns:android="http://schemas.android.com/apk/res/android">
       <data>
           <variable name="handlers" type="com.example.Handlers"/>
           <variable name="user" type="com.example.User"/>
       </data>
       <LinearLayout
           android:orientation="vertical"
           android:layout_width="match_parent"
           android:layout_height="match_parent">
           <TextView android:layout_width="wrap_content"
               android:layout_height="wrap_content"
               android:text="@{user.firstName}"
               android:onClick="@{user.isFriend ?handlers.onClickFriend :handlers.onClickEnemy}"/>
           <TextView android:layout_width="wrap_content"
               android:layout_height="wrap_content"
               android:text="@{user.lastName}"
               android:onClick="@{user.isFriend ?handlers.onClickFriend :handlers.onClickEnemy}"/>
       </LinearLayout>
    </layout>
```
由于DataBinding对布局使用改动比较大，下面主要讲解一下布局：
①在布局中import导入
```
    <data>
        <import type="android.view.View"/>
    </data>
```
之后在你的布局中通过View控件特性进行对其实现类隐藏和显示操作
```
    <TextView
       android:text="@{user.lastName}"
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"
       android:visibility="@{user.isAdult ? View.VISIBLE : View.GONE}"/>
```
可能有人会问如果我导入的类与已导入的类的名字冲突怎么办？那么接下来就会解决这个问题！
```
    <import type="android.view.View"/>
    <import type="com.example.real.estate.View"
            alias="Vista"/>
```
这里的“alias”属性就是别名的意思，你可以采用别名的方式解决这个问题。

②当你在布局中引用的变量是一个List集合，需将集合的左”<”使用转义字符输入，如下（想要了解转义字符具体表达形式，请自行查询）：
```
    <data>
        <import type="com.example.User"/>
        <import type="java.util.List"/>
        <variable name="user" type="User"/>
        <variable name="userList" type="List&lt;User>"/>
    </data>
```
③类型转换
```
    <TextView
       android:text="@{((User)(user.connection)).lastName}"
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"/>
```
④当导入的类中存在静态属性和方法时，你也是可以在布局中直接使用
```
    <data>
        <import type="com.example.MyStringUtils"/>
        <variable name="user" type="com.example.User"/>
    </data>
    …
    <TextView
       android:text="@{MyStringUtils.capitalize(user.lastName)}"
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"/>
```
⑤属性Variables：
在数据元素中可以使用任意数量的变量元素。每个可变元素描述一个属性，该属性可以设置在布局文件中的绑定表达式中使用的布局上：
```
    <data>
        <import type="android.graphics.drawable.Drawable"/>
        <variable name="user"  type="com.example.User"/>
        <variable name="image" type="Drawable"/>
        <variable name="note"  type="String"/>
    </data>
```
⑥自定义Binding类的类名：
```
    <data class="CustomBinding"></data> 在app_package/databinding下生成CustomBinding；
    <data class=".CustomBinding"></data> 在app_package下生成CustomBinding；
    <dataclass="com.example.CustomBinding"></data> 明确指定包名和类名。
```
⑦include使用
```
    <?xml version="1.0" encoding="utf-8"?>
    <layout xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:bind="http://schemas.android.com/apk/res-auto">
       <data>
           <variable name="user" type="com.example.User"/>
       </data>
       <LinearLayout
           android:orientation="vertical"
           android:layout_width="match_parent"
           android:layout_height="match_parent">
           <include layout="@layout/name"
               bind:user="@{user}"/>
           <include layout="@layout/contact"
               bind:user="@{user}"/>
       </LinearLayout>
    </layout>
```
⑧DataBinding数据绑定不支持包括合并元素的直接子元素，例如下面的写法是不被允许的：
```
    <?xml version="1.0" encoding="utf-8"?>
    <layout xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:bind="http://schemas.android.com/apk/res-auto">
       <data>
           <variable name="user" type="com.example.User"/>
       </data>
       <merge>
           <include layout="@layout/name"
               bind:user="@{user}"/>
           <include layout="@layout/contact"
               bind:user="@{user}"/>
       </merge>
    </layout>
```
注意：name.xml 和 contact.xml都必须包含 <variable name="user" ../>

⑨DataBinding支持的表达式有：
```
    数学表达式： + - / *%
    字符串拼接 +
    逻辑表达式&& ||
    位操作符 & | ^
    一元操作符 + - ! ~
    位移操作符 >>>>> <<
    比较操作符 == >< >= <=
    instanceof
    分组操作符 ()
    字面量 -character, String, numeric, null
    强转、方法调用
    字段访问
    数组访问 []
    三元操作符 ?
    聚合判断（Null Coalescing Operator）语法 ‘??’
```
例如：
```
    ①
    android:text="@{String.valueOf(index + 1)}"
    android:visibility="@{age &lt; 13 ? View.GONE : View.VISIBLE}"
    android:transitionName='@{"image_" + id}'
    ②
    android:text="@{user.displayName ?? user.lastName}"<br/>

     ③
    上面代码的意思是如果displayName为null，则显示lastName，否则显示displayName；
    android:text="@{user.displayName != null ? user.displayName : user.lastName}"
```
集合Collections
```
    <data>
        <import type="android.util.SparseArray"/>
        <import type="java.util.Map"/>
        <import type="java.util.List"/>
        <variable name="list" type="List&lt;String>"/>
        <variable name="sparse" type="SparseArray&lt;String>"/>
        <variable name="map" type="Map&lt;String, String>"/>
        <variable name="index" type="int"/>
        <variable name="key" type="String"/>
    </data>
    …
    android:text="@{list[index]}"
    …
    android:text="@{sparse[index]}"
    …
    android:text="@{map[key]}"
```
    ①String literals（字符串常量）
    当使用单引号围绕属性值时，在表达式中使用双引号是很容易的：
```
    android:text='@{map["firstName"]}'
```
    ②也可以使用双引号来环绕属性值。当你这样做的时候，String literals要么用&quot；或反引号（`）：
```
    android:text="@{map[`firstName`}"
    android:text="@{map[&quot;firstName&quot;]}"
```
Resources资源：
在DataBinding语法中，可以把resource作为其中的一部分：
```
android:padding="@{large? @dimen/largePadding : @dimen/smallPadding}"
除了支持dimen，还支持color、string、drawable、anim等。
```
注意：对mipmap图片资源支持还是有问题，目前只支持drawable。

一些资源要求需明确的类型赋值：
![资源文件的引入](https://github.com/flyingtercel/MVVM/blob/master/mvvm/src/main/res/drawable/res.png)
<br/>
ObservableFields:

```
private static class User {
   public final ObservableField<String> firstName = new ObservableField<>();
   public final ObservableField<String> lastName = new ObservableField<>();
   public final ObservableInt age = new ObservableInt();
}
在代码中设置数据：
user.firstName.set("Google");
int age = user.age.get();
```
Observable Collections
```
    ObservableArrayMap<String, Object> user = new ObservableArrayMap<>();
    user.put("firstName", "Google");
    user.put("lastName", "Inc.");
    user.put("age", 17);
    布局中使用：

    <data>
        <import type="android.databinding.ObservableMap"/>
        <variable name="user" type="ObservableMap&lt;String, Object>"/>
    </data>
    …
    <TextView
       android:text='@{user["lastName"]}'
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"/>
    <TextView
       android:text='@{String.valueOf(1 + (Integer)user["age"])}'
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"/>
```
如果集合的key是Integer，可以使用ObservableArrayList代替ObservableArrayMap：
```
    ObservableArrayList<Object> user = new ObservableArrayList<>();
    user.add("Google");
    user.add("Inc.");
    user.add(17);


    在布局中使用：

    <data>
        <import type="android.databinding.ObservableList"/>
        <import type="com.example.my.app.Fields"/>
        <variable name="user" type="ObservableList&lt;Object>"/>
    </data>
    …
    <TextView
       android:text='@{user[Fields.LAST_NAME]}'
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"/>
    <TextView
       android:text='@{String.valueOf(1 + (Integer)user[Fields.AGE])}'
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"/>
```
适配器的绑定：
```
        <data>
            <variable
                name="adapter"
                type="CBeanAdapter"/>
        </data>
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical">
            <ListView
                app:adapter="@{adapter}"
                android:layout_width="match_parent"
                android:layout_height="match_parent"></ListView>
        </LinearLayout>
```



