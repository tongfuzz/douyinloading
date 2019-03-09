# 仿抖音加载进度条 

最近在一个android学习交流群中看到有同学问仿抖音进度条加载怎么实现，刚好自己在学习自定义view，就尝试实现了一下，效果如下图，在此记录一下实现过程，如有不足，欢迎指正交流。
  
  
  <div align=center>
 <img width="300" height="500" src="https://raw.githubusercontent.com/tongfuzz/douyinloading/master/app/screenshots/haha.gif"/>
 </div>
 
### 效果分析
首先我们来分析这个进度条的效果，可以看到刚开始进度条是固定长度的深灰色的，然后慢慢向两端延伸并且颜色渐变成透明色，到达屏幕两边时完全透明，如此循环，在这个过程中有两个要点：

>* 1 颜色渐变，深灰色>透明色
* 2 长度向外延伸，由中间向两侧延伸，至屏幕边界

首先我们来分析颜色渐变，项目中我们常用的颜色表示方式有两种，一种是不带透明度，一种是带透明度，如下所示
<img src="https://raw.githubusercontent.com/tongfuzz/douyinloading/master/app/screenshots/color.png" />



 
 
 