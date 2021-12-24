[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-f059dc9a6f8d3a56e377f745f24479a46679e63a5d9fe6f495e02850cd0d8118.svg)](https://classroom.github.com/online_ide?assignment_repo_id=6521930&assignment_repo_type=AssignmentRepo)
# jw08

## 代码

请**综合jw04-jw07所有要求**，完成一个完整的**图形化**网络对战游戏。所有提交的作业将统一展出，由各位同学相互打分。


## 报告

在此基础之上，请以《Developing a Java Game from Scratch》为题撰写文章一篇，内容涵盖但不限于：
- 开发目标（我写的游戏是个什么样的游戏，灵感来源是什么）；
- 设计理念（代码总体设计是什么？这样设计的好处是什么？）；
- 技术问题（通信效率、并发控制、输入输出等问题我是怎么解决和优化的，面向对象设计方法带来了什么好处，等）；
- 工程问题（如何采用各种设计方法、工程方法来提高开发效率和代码质量）；
- 课程感言（对课程形式、内容等方面提出具体的意见和建议）
- 等

请使用《中国科学》的[latex模板](http://scis.scichina.com/download/ssi-template.zip)进行排版（不少于6页），输出pdf文件提交。



灵感来源：小时候玩的游戏

# 设计过程
先构思游戏的基本机制和内容，设计基本的游戏框架(玩家、怪物、道具等类)和基本逻辑（胜利条件、战斗中的攻击和生命值机制等），在原始的UI下完成实现最原始的游戏版本

逐步从如下方面进行版本迭代，优化和改进游戏：
可玩性，游戏机制，各种道具，不同地图
保存功能，通过文件IO
BGM和UI
# 设计思路
面向对象，对现实的抽象




AsciiPanel的使用：这些方法只是修改二维数组。
以下均可以指定字体颜色(forground)和背景颜色(background)，和坐标
write函数
写char到指定位置 **char的值即为在图片中的下标**，用于UI显示：`public AsciiPanel write(char character, int x, int y, Color foreground, Color background) {` 
写string到屏幕居中位置（指定y）`public AsciiPanel writeCenter(String string, int y, Color foreground, Color background) `

写String到指定位置，用于写文字：`public AsciiPanel write(String string, int x, int y, Color foreground, Color background) {`：

clear函数，**char的值即为在图片中的下标**
可以`Clear the section of the screen with the specified character and whatever the specified foreground and background colors are.`，也可以clear整个屏幕。

UI优化：如何paint？
Main中JFrame.repaint() 调用了super.repaint()，然后调用其Component——即AsciiPanel的update或paint方法(AsciiPanel中二者功能等价),paint方法按二维数组来画
因此，搞一个独立的UI线程，每一帧控制UI刷新一次，其他线程只需调用AsciiPanel中的方法即可。


不能是：每个player线程独立监听键盘，应该监听完把event放临界区，这样才能保序

AsciiPanel中注解用的很好，学习了一下

明天早上的任务：
设计多个线程 实现游戏基本逻辑
f需要总的计时控制单元Timer？

游戏构思：三种武器，炸弹（红色3 2 1），弓箭（箭头），剑（周围一圈）
地图要有随机性！！！使用maze——generator？
怪：蛇？不止一格的 还有在周围防地雷/扔炸弹的BOSS
后续的多人网络对战 实现为组队闯关？或者双方自己给一定数量的金币用来放置不同的怪物守卫自己的家？这样的话是不是能做成塔防或者王者荣耀？


TODO都留着，展示设计思路！
修改：UIPainter 再加一个KeyListner

UI改进：



TODO:武器的自动转向
弓箭 碰到物体不是Nothing就自动消失
火球 碰到物体炸开成3x3
这三者怎么画？武器在repaint()中判断一下是否visible然后画即可。

Icon:
https://www.flaticon.com/
https://limezu.itch.io/rpg-arsenal
https://momentaryunicorn.itch.io/momentary-unicorns-evolving-bows-32x32

Timer:
https://www.baeldung.com/java-timer-and-timertask

JFrame:
https://www.javatpoint.com/java-jframe

多线程使用了：
synchronized method
synchronized block
reentrant clock

BOSS 占四个格子 在天上飞！周围一圈散射

音乐：
https://opengameart.org/content/4-chiptunes-adventure

Maven构建：
https://spring.io/guides/gs/maven/
https://maven.apache.org/users/index.html
https://newbedev.com/resources-and-config-loading-in-maven-project
https://www.codenong.com/cs106568531/

Boss:
向player发射？




单元测试：我深刻体会到了自己代码的不足，类中有大量的void方法，纯粹的side effect（只改变该类型对象的状态，无返回值）,难以测试效果；对于各种可能的异常情况都没有处理。还是一定程度上过程式编程的错误。   

要遵循SOLID设计原则。
类中的方法尽量不要是纯副作用！但大部分方法确实有副作用。因此叫做方法，而不叫函数。函数应该没有副作用。
那么如何测试：对于纯副作用的void方法，调用后看一看对象方法是否改变。


java unitTest
How to Generate UnitTest:https://code.visualstudio.com/docs/java/java-testing
UnitTest in Maven and Coverage Gutter:https://medium.com/@karlrombauts/setting-up-unit-testing-for-java-in-vs-code-with-maven-3dc75579122f
JaCoco:https://www.baeldung.com/jacoco


LATEX：
https://www.programmerall.com/article/83671358413/
https://www.cnblogs.com/li-minghao/p/10954487.html