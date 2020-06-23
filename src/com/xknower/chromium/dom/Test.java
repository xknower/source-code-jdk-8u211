package com.xknower.chromium.dom;

import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("input common: ");
        String js = scanner.next();
        System.out.println(js);
    }
}

// 动态编译脚本实现, 运行环境提供上下文环境
// -> 输入动态执行字符串代码
// -> 编译执行
// https://www.iteye.com/blog/dengminhui-574216
// https://blog.csdn.net/u013410747/article/details/51791394
// https://www.cnblogs.com/andysd/p/10081443.html
