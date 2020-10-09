package com.xknower.chromium.dom;

import com.teamdev.jxbrowser.chromium.dom.DOMNodeType;

/**
 * DOM 结点类型
 *
 * @author xknower
 */
public enum JxDOMNodeType {

    // 元素结点
    ElementNode(1L),
    // 属性结点
    AttributeNode(2L),
    // 文本结点
    TextNode(3L),
    //
    CDataSectionNode(4L),
    //
    EntityReferenceNode(5L),
    //
    EntityNode(6L),
    //
    ProcessingInstructionsNode(7L),
    // 注释结点
    CommentNode(8L),
    // DOM 结点
    DocumentNode(9L),
    // DOM 类型结点
    DocumentTypeNode(10L),
    //
    DocumentFragmentNode(11L),
    //
    NotationNode(12L),
    //
    XPathNamespaceNode(13L),
    //
    ShadowRootNode(14L);

    private final long value;

    JxDOMNodeType(long value) {
        this.value = value;
    }

    public static JxDOMNodeType Value(long value) {
        for (JxDOMNodeType v : values()) {
            if (v.value == value) {
                return v;
            }
        }
        return null;
    }

    public long Value() {
        return this.value;
    }

    public DOMNodeType domNodeType() {
        return DOMNodeType.valueOf(this.value);
    }

    public static JxDOMNodeType domNodeType(DOMNodeType type) {
        switch (type) {
            case ElementNode:
                return JxDOMNodeType.ElementNode;
            case AttributeNode:
                return JxDOMNodeType.AttributeNode;
            case TextNode:
                return JxDOMNodeType.TextNode;
            case CDataSectionNode:
                return JxDOMNodeType.CDataSectionNode;
            case EntityReferenceNode:
                return JxDOMNodeType.EntityReferenceNode;
            case EntityNode:
                return JxDOMNodeType.EntityNode;
            case ProcessingInstructionsNode:
                return JxDOMNodeType.ProcessingInstructionsNode;
            case CommentNode:
                return JxDOMNodeType.CommentNode;
            case DocumentNode:
                return JxDOMNodeType.DocumentNode;
            case DocumentTypeNode:
                return JxDOMNodeType.DocumentTypeNode;
            case DocumentFragmentNode:
                return JxDOMNodeType.DocumentFragmentNode;
            case NotationNode:
                return JxDOMNodeType.NotationNode;
            case XPathNamespaceNode:
                return JxDOMNodeType.XPathNamespaceNode;
            case ShadowRootNode:
                return JxDOMNodeType.ShadowRootNode;
            default:
                throw new IllegalArgumentException("Unsupported value: " + type);
        }
    }

}
