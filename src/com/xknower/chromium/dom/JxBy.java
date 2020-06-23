package com.xknower.chromium.dom;

import com.teamdev.jxbrowser.chromium.dom.By;

/**
 * 选择器
 *
 * @author xknower
 */
public final class JxBy {

    private By by;

    private JxBy(By by) {
        this.by = by;
    }

    public By toBy() {
        return by;
    }

    /**
     * ID 选择器
     *
     * @param id id
     * @return
     */
    public static JxBy id(String id) {
        return new JxBy(By.id(id));
    }

    /**
     * 类选择器
     *
     * @param className
     * @return
     */
    public static JxBy className(String className) {
        return new JxBy(By.className(className));
    }

    /**
     * 名称选择器选择器
     *
     * @param name
     * @return
     */
    public static JxBy name(String name) {
        return new JxBy(By.name(name));
    }

    /**
     * 标签选择器
     *
     * @param tagName
     * @return
     */
    public static JxBy tagName(String tagName) {
        return new JxBy(By.tagName(tagName));
    }

    /**
     * XPATH 规则匹配器
     *
     * @param xpath 表达式
     * @return
     */
    public static JxBy xpath(String xpath) {
        return new JxBy(By.xpath(xpath));
    }

    /**
     * CSS 选择器
     *
     * @param cssSelector
     * @return
     */
    public static JxBy cssSelector(String cssSelector) {
        return new JxBy(By.cssSelector(cssSelector));
    }
}
