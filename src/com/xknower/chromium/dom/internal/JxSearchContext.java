package com.xknower.chromium.dom.internal;

import com.teamdev.jxbrowser.chromium.XPathResult;
import com.teamdev.jxbrowser.chromium.XPathResultType;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.SearchContext;
import com.xknower.chromium.dom.JxBy;

import java.util.List;

/**
 * SearchContext
 *
 * @author xknower
 */
public class JxSearchContext {

    private SearchContext searchContext;

    protected JxSearchContext(SearchContext searchContext) {
        this.searchContext = searchContext;
    }

    public static JxSearchContext instance(SearchContext searchContext) {
        return new JxSearchContext(searchContext);
    }

    //

    /**
     * 查找 DOMElement (返回查询到的所有元素, 查询不到返回数量为零的集合)
     *
     * @param jxBy 查找规则
     * @return DOMElement
     */
    public List<DOMElement> findElements(JxBy jxBy) {
        return searchContext.findElements(jxBy.toBy());
    }

    /**
     * 查找 DOMElement (返回查询到的一个元素, 查询不到返回空)
     *
     * @param jxBy 查找规则
     * @return DOMElement
     */
    public DOMElement findElement(JxBy jxBy) {
        return searchContext.findElement(jxBy.toBy());
    }

    public XPathResult evaluate(String by) {
        return searchContext.evaluate(by);
    }

    public XPathResult evaluate(String expression, XPathResultType xPathResultType) {
        return searchContext.evaluate(expression, xPathResultType);
    }
}
