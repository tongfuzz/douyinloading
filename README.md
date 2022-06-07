# 仿抖音加载进度条 

最近在一个android学习交流群中看到有同学问仿抖音进度条加载怎么实现，刚好自己在学习自定义view，就尝试实现了一下，效果如下图，在此记录一下实现过程，如有不足，欢迎指正交流。
  
  
  <div align=center>
 <img width="300" height="500" src="app/screenshots/haha.gif"/>
 </div>
 
### 效果分析
首先我们来分析这个进度条的效果，可以看到刚开始进度条是固定长度的深灰色的，然后慢慢向两端延伸并且颜色渐变成透明色，到达屏幕两边时完全透明，如此循环，在这个过程中有两个要点：

>* 1 长度向外延伸，由中间向两侧延伸，至屏幕边界
>* 2 颜色渐变，深灰色>透明色

首先我们来分析长度向外延伸至屏幕边界，首先我们可以获取到整个view的高度，我们可以在view中心位置上画一个固定初始宽度和高度的灰色的进度条（其实就是一条灰色的线），然后定时向两边增加固定的宽度，然后再次重新绘制这个view，直到进度条的宽度大于view的宽度时，我们再次将进度条的宽度设置为初始宽度。接着执行此步骤


接着我们来分析颜色渐变，项目中我们常用的颜色表示方式有两种，一种是不带透明度，一种是带透明度，如下所示

<img src="https://raw.githubusercontent.com/tongfuzz/douyinloading/master/app/screenshots/color.png" />

如果我们直接改变颜色的值，由于颜色值不易控制，改变颜色值的过程中很容易出现我们不需要的颜色，所以我们可以换一种思路，通过改变颜色的透明度来达到我们的效果，我们将最开始的深灰色，通过不断加大它的透明度，最后使其变为透明色不可见，颜色的透明度由两位16进制数来表示，为ff时表明不透明，为00时表示全透明，如下图

<img src="https://raw.githubusercontent.com/tongfuzz/douyinloading/master/app/screenshots/color2.png" />

我们知道两位16进制数表示的范围0-255，在进度条为初始宽度时设置透明度为255，随着进度条宽度逐渐增大，我们将透明度由255逐渐减小，最后变为接近全透明0，好了，废话不多说，开始上代码。

---

首先我们创建一个类LoadingView继承View类，并重写它的构造方法，最后一个构造方法我们一般用不到，删除就行了，让前两个构造方法调用第三个构造方法，以保证每次都会调用到第三个构造方法，如下图

<img src="https://raw.githubusercontent.com/tongfuzz/douyinloading/master/app/screenshots/view2.png" />

接下来我们给view设置一个默认最小宽度和高度，以及进度条的初始宽度，初始颜色 并创建画笔

<img src="https://raw.githubusercontent.com/tongfuzz/douyinloading/master/app/screenshots/view3.png" />

然后就是自定义view三部曲了，首先我们重写onMeasure方法来测量view的宽高，具体实现如下图，（每一步的说明已经写的很详细不再叙述，如果对MeasureSpec不是很了解的同学可以参考此文[MeasureSpec详解](https://segmentfault.com/a/1190000007948959)）

<img src="https://raw.githubusercontent.com/tongfuzz/douyinloading/master/app/screenshots/view4.png" />

onSizeChanged方法会在view的大小发生改变时调用，所以获取完view的宽高后我们重写onSizeChanged方法将宽高赋值给全局变量，并设置画笔高度为view的高度，如下图

<img src="https://raw.githubusercontent.com/tongfuzz/douyinloading/master/app/screenshots/view5.png" />

接着我们重写onDraw方法开始view的绘制，具体过程如下，首先保证进度条宽度不能大于view的宽度，然后根据进度条宽度占比来计算颜色的透明度，最后给画笔设置为此颜色，然后用canvas来画一条从view的中心点开始向左右画一条宽度为进度条二分之一的线，(关于canvas的使用方法不了解的同学可以参考此文[Canvas使用详解](http://www.gcssloop.com/customview/Canvas_BasicGraphics)) 如下图

<img src="https://raw.githubusercontent.com/tongfuzz/douyinloading/master/app/screenshots/view6.png" />

最后调用 invalidate()方法来不断刷新重新绘制，由于在重新绘制的过程中进度条宽度不断改变，这样就形成了进度条不断延伸的效果，效果如下

 <div align=center>
 <img width="300" height="500" src="https://raw.githubusercontent.com/tongfuzz/douyinloading/master/app/screenshots/hehe.gif"/>
 </div>

至此基本效果已经实现了，可能有的同学会说你这个最小宽度每次都要去修改，我想修改个颜色，修改个宽度，每次还要到源码中去修改，太麻烦，那么接下来我们就通过自定义属性，将一些经常更改的东西通过属性来提供（关于自定义属性，可以参考[自定义属性](https://blog.csdn.net/liujian8654562/article/details/80389077)）

---

首先我们在values文件夹下创建attrs.xml文件，然后在其中定义相应的属性

<img src="https://raw.githubusercontent.com/tongfuzz/douyinloading/master/app/screenshots/view7.png" />

然后我们在view的构造函数中获取自定义属性值，并赋值给相关常量，并且根据正则表达式来判断值传入的是否正确，

<img src="https://raw.githubusercontent.com/tongfuzz/douyinloading/master/app/screenshots/view8.png" />

接下来我们在xml布局中添加这些属性

<img src="https://raw.githubusercontent.com/tongfuzz/douyinloading/master/app/screenshots/view9.png" />

然后运行项目，发现我们更改的属性已经生效

 <div align=center>
 <img width="300" height="500" src="https://raw.githubusercontent.com/tongfuzz/douyinloading/master/app/screenshots/123.gif"/>
 </div>

接下来我们来更改动画的执行时间，动画执行时间我们使用handler 来定时发送消息，并在接收到消息后再次发送，以达到循环执行的目的,然后对外提供方法更改重绘周期

<img src="https://raw.githubusercontent.com/tongfuzz/douyinloading/master/app/screenshots/view10.png" />

我们通过findviewbyid找到此view，并设置间隔时间为100毫秒测试一下效果

 <div align=center>
 <img width="300" height="500" src="https://raw.githubusercontent.com/tongfuzz/douyinloading/master/app/screenshots/1234.gif"/>
 </div>

至此仿抖音loadingview基本就完成了，android小白，初次写博客，如有不足的地方望多多指教 本人邮箱suntongf@126.com  项目地址[Demo地址](https://github.com/tongfuzz/douyinloading)















 
 
 
