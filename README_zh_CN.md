Dotted Progress Bar  fork from [igortrncic](https://github.com/igortrncic/dotted-progress-bar).

=======
![dotted-progress-bar](https://github.com/fangler/dotted-progress-bar/raw/master/progress.gif)

使用方法
-----
activeDot/inactiveDot 可以使用一个drawable 或者一个颜色值

```xml
<com.trncic.library.DottedProgressBar
    android:id="@+id/progress"
    android:layout_width="match_parent"
    android:layout_height="0dp"
    android:padding="30dp"
    app:activeDot="@drawable/active_dot"
    app:dotSize="29dp"
    app:inactiveDot="@drawable/inactive_dot"
    app:jumpingSpeed="670"
    app:spacing="15dp" />
```
```java
DottedProgressBar progressBar = new DottedProgressBar(context);
progressBar.startProgress();
progressBar.stopProgress();
```


限制
-----------
* layout_height 是 paddingTop, paddingBottom and dotSize 之和，所以设置它是无效的。

修改日志
---------
* 重构了代码，增加构造函数用初始化，用来支持自己的项目。
* 想要查看原始的项目，在 [igortrncic这里](https://github.com/igortrncic/dotted-progress-bar).
* 本项目仅供学习使用！

* **1.0.0**
    * 第一次提交

License
-------

    Copyright 2015 Igor Trncic

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and

    limitations under the License.
