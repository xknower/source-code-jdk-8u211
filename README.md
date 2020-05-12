# JAVA 源码研究

> 使用版本 [jdk-8u211] [环境 IDEA]

> 构建步骤

## 1、创建项目结构

rt/        // rt.jar 反编译源码
rtsrc/     // JDK 源码
src/       // 项目源码

## 2、导入源码

> import jdk source of src.zip
> import jdk source of decompile rt.jar
> import jdk source of other source

> rtsrc copy(8204) and replace(7388) to rt , then rt copy (13964) and not replace (7718) to rtsrc
> new add (330) of rtsrc copy(8204) and replace(7388) to rt
> modified (7388) of rtsrc copy(8204) and replace(7388) to rt
> then new add (5240) rt copy (13964) and not replace (7718) to rtsrc

> fix decompile source and compile test

> 设置 rtsrc/ 、src/ 参与编译
> 设置 IDEA 编译缓存
> 设置 IDEA Sourcepath 为 rtsrc/ (修改JDK注释时, 行数不能更改)

## 3、配置构建 JDK -> [IDEA 、jdk-8u211]

> jre\lib
charsets.jar
deploy.jar
javaws.jar
jce.jar
jfr.jar
jfxswt.jar
jsse.jar
management-agent.jar
plugin.jar
resources.jar
rt.jar

> jre\lib\ext
access-bridge-64.jar
cldrdata.jar
dnsns.jar
jaccess.jar
jfxrt.jar
localedata.jar
nashorn.jar
sunec.jar
sunjce_provider.jar
sunmsca.pi.jar
sunpkcs11.jar
zipfs.jar

>
tools.jar 包 lib\tools.jar

>
sun.awt.UNIXToolkit
// http://www.docjar.com/html/api/sun/awt/UNIXToolkit.java.html

sun.font.FontConfigManager
// http://www.docjar.com/html/api/sun/font/FontConfigManager.java.html

## 4、src.zip 、rt.jar 包源码分析

> 更改源码并编译后, 解压 rt.jar 并替换修改的 class 文件, 重新打包并替换 jre/lib/rt.jar 文件
> !操作前请注意备份
