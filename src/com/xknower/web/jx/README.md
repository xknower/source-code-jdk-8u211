# Web 应用开发 - 内嵌浏览器内核(Webkit)实现应用开发

-- 内嵌浏览器内核

> swing + jxbrowser

> https://jxbrowser-support.teamdev.com/javadoc/7.7/index.html

> https://jxbrowser-support.teamdev.com/docs/quickstart/hello-world.html
> https://jxbrowser-support.teamdev.com/docs/
> https://jxbrowser-support.teamdev.com/docs/guides/

// 6.x
> https://jxbrowser.support.teamdev.com/support/solutions
// 7.x
> https://jxbrowser-support.teamdev.com/docs/guides/architecture.html

## 文献资料

### 01 简介

### 02 浏览器引擎 Browser Engine

Creating Browser           创建浏览器
Creating Incognito Browser 创建隐身浏览器
Storing User Data          存储用户数据
Disposing Browser          配置浏览器
Browser Preferences        浏览器首选项
Restoring Browser          恢复浏览器
Render Process Events      渲染过程事件
Render Process ID          渲染进程ID
Getting Frame IDs          获取框架ID
Getting Product Version    获取产品版本
Finding Text               查找文字
Clearing Cache             清除缓存
Forwarding Key Events      发送键盘事件
Forwarding Mouse Events    发送鼠标事件

### 03 载入内容 Loading Content

Loading URL                              加载网页
Loading URL with POST                    用POST加载URL
Loading HTML                             加载HTML
Loading HTML from JAR                    从JAR加载HTML
Getting HTML                             获取HTML
Getting Selected HTML                    获取选定的HTML
Loading Events                           加载事件
Loading & Waiting                        加载与等待
Displaying PDF                           显示PDF
Network Events                           网络事件
Handling Resources Loading               处理资源加载
Enabling/Disabling Backspace Navigation  启用/禁用退格导航
Handling SSL Certificate Errors          处理SSL证书错误
SSL Certificate Verifier                 SSL证书验证器
Navigation History                       导航历史
User-Agent                               用户代理
WebSockets                               Web套接字
Handling Loading                         处理装载
Modifying POST/PUT/PATCH Upload Data     修改POST / PUT / PATCH上传数据
HTML5 Local & Session storages           HTML5本地和会话存储
Accessing HTTP response data             访问HTTP响应数据
HTTP Server Whitelist                    HTTP服务器白名单
Custom Protocol Handler                  自定义协议处理程序
ActiveX

### 04 音频视频 Audio & Video

MP3/MP4/H.264             MP3 / MP4 / H.264
Web Camera & Microphone   网络摄像头和麦克风
Full Screen Video         全屏视频
Muting Audio              静音
HTML5 Video               HTML5视频

### 05 弹出窗口 Pop-ups

About Pop-ups           关于弹出窗口
Handling Pop-ups Swing  处理弹出窗口
Handling Pop-ups JavaFX 处理弹出窗口

### 06 对话方块 Dialogs

JavaScript Dialogs            JavaScript对话框
File Download                 档案下载
File Upload                   上传文件
Select SSL Certificate        选择SSL证书
Select Custom SSL Certificate 选择自定义SSL证书
Before Unload                 卸载前
Color Chooser                 颜色选择器

### 07 代理 Proxy

Working with Proxy    使用代理
System Proxy Settings 系统代理设置

### 08 认证方式 Authentication

Handling Proxy Authentication                    处理代理身份验证
Handling Basic, Digest and NTLM Authentication   处理基本，摘要和NTLM身份验证

### 09 浏览器视图 Browser View

Lightweight or Heavyweight   轻量级或重量级
Using JxBrowser in Swing     在Swing中使用JxBrowser
Using JxBrowser in JavaFX    在JavaFX中使用JxBrowser
Using JxBrowser in SWT       在SWT中使用JxBrowser
Custom CSS Cursors           自定义CSS光标
Title Events                 标题事件
Status Events                状态事件
Keyboard & Mouse Events      键盘和鼠标事件
Handling Keyboard Events     处理键盘事件
Handling Mouse Events        处理鼠标事件
Editor Commands              编辑器命令
Drag & Drop                  拖放
Content scaling              内容缩放
Context Menu                 上下文菜单
JMenuBar                     JMenuBar
JInternalFrame
JTabbedPane
JPanel
Accelerated Lightweight Rendering  加速轻量级渲染
Transparent Background             透明背景
Shortcuts                          快捷键

### 10 JavaScript Java桥 JavaScript Java Bridge

Calling JavaScript from Java 从Java调用JavaScript
Calling Java from JavaScript 从JavaScript调用Java
Console Messages             控制台消息
Working with JSON            使用JSON
Working with jQuery          使用jQuery
Working with ScriptContext   使用ScriptContext
Sending Form Data to Java    将表单数据发送到Java
Working with Arrays          使用数组
@JSAccessible

### 11 插件 Plugins

About Plugins    关于插件
Plugins Manager  插件管理器
Adobe Flash
Microsoft Silverlight
Java Applet

### 12 打印 Printing

Printing Web Page          打印网页
Printing Web Page to PDF   将网页打印为PDF
Print Settings             打印配置
Cancel Printing            取消打印

### 13 缓存 Cookies

About Cookies               关于缓存
Cookie Storage              Cookie存储
Accepting/Rejecting Cookies 接受/拒绝Cookie
Cookie Encryption           Cookie加密

### 14 保存网页 Saving Web Page

Saving Web Page to File/Files   将网页保存到文件
Saving Web Page to PNG Image    将网页保存为PNG图像

### 15 缩放 Zoom

Zooming Web Page        缩放网页
Receiving Zoom Events   接收缩放事件
Enabling/Disabling Zoom 启用/禁用缩放

### 16 整合 Integration

JxBrowser & Selenium

### 17 DOM

Working with Document        处理文件
Finding Elements             寻找元素
Element Attributes           元素属性
Creating Element & Text Node 创建元素和文本节点
Setting Node Value           设置节点值
Select & Option Elements     选择和选项元素
Selecting CheckBox           选择复选框
Getting Selected Text        获取所选文本
Simulating Click             模拟点击
DOM Events                   DOM事件
XPath                        XPath
Query Selector               查询选择器
Working with Form            使用表格
Scrolling Document           滚动文件
Finding Node at Point        在结点上找到节点
Getting Element Bounds       获取元素边界
Injecting CSS                注入CSS
Listening to the content changes 监听内容更改
Simulating DOM Events            模拟DOM事件

### 18 部署中 Deploying

Java Application
Java Web Start
Java Applet
OSGi
Citrix

### 19 Chromium

Chromium Version          版本
Chromium Switches         开关
Chromium Language         语言
Chromium Binaries         二进制
Chromium Extensions       扩展
Chromium Updates Calendar 更新日历
Remote Debugging Port     远程调试端口
Chromium API Keys         API密钥
Geolocation               地理位置
Voice Recognition         语音识别
Open Source Components' Licenses   开源组件的许可证
Sandbox                            沙盒
Chromium 64-bit on Windows         Windows上的Chromium 64位

### 20 拼写检查程序 Spell Checker

Configuring Spell Checker      配置拼写检查器
Spell Check Events             拼写检查事件
Context Menu Suggestions Swing 上下文菜单

### 21 调试 Debugging

Logging              日志记录
Debugging on Mac     在Mac上调试
Debugging on Windows 在Windows上调试
Debugging on Linux   在Linux上调试

### 22 为什么选择JxBrowser Why JxBrowser

JxBrowser vs JavaFX WebView

### 23 提示与技巧 Tips & Tricks

Runnig JxBrowser Demo Locally       Runnig JxBrowser本地演示
Disabling Swipe Navigation          禁用滑动导航
Detecting Unresponsive Web Page     检测无响应的网页
Starting X-server in Headless Linux 在Headless Linux中启动X服务器

### 24 迁移指南 Migration Guides

Migrating from 4.x to 5.0    从4.x迁移到5.0
Migrating from 5.4.3 to 6.0  从5.4.3迁移到6.0
Migrating from 6.0.2 to 6.1  从6.0.2迁移到6.1
Migrating from 6.1.1 to 6.2  从6.1.1迁移到6.2
Migrating from 6.2 to 6.3    从6.2迁移到6.3

### 25 常问问题 FAQ

'IPC process exited. Exit code: 127' on Linux  'IPC流程已退出。退出代码：在Linux上为127'
