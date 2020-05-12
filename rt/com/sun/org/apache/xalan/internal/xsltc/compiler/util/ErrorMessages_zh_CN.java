/*    */ package com.sun.org.apache.xalan.internal.xsltc.compiler.util;
/*    */ 
/*    */ import java.util.ListResourceBundle;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ErrorMessages_zh_CN
/*    */   extends ListResourceBundle
/*    */ {
/*    */   public Object[][] getContents() {
/* 96 */     return new Object[][] { { "MULTIPLE_STYLESHEET_ERR", "同一文件中定义了多个样式表。" }, { "TEMPLATE_REDEF_ERR", "已在此样式表中定义模板 ''{0}''。" }, { "TEMPLATE_UNDEF_ERR", "未在此样式表中定义模板 ''{0}''。" }, { "VARIABLE_REDEF_ERR", "同一作用域中多次定义了变量 ''{0}''。" }, { "VARIABLE_UNDEF_ERR", "未定义变量或参数 ''{0}''。" }, { "CLASS_NOT_FOUND_ERR", "找不到类 ''{0}''。" }, { "METHOD_NOT_FOUND_ERR", "找不到外部方法 ''{0}'' (必须为 public)。" }, { "ARGUMENT_CONVERSION_ERR", "无法在调用方法 ''{0}'' 时转换参数/返回类型" }, { "FILE_NOT_FOUND_ERR", "找不到文件或 URI ''{0}''。" }, { "INVALID_URI_ERR", "URI ''{0}'' 无效。" }, { "FILE_ACCESS_ERR", "无法打开文件或 URI ''{0}''。" }, { "MISSING_ROOT_ERR", "需要 <xsl:stylesheet> 或 <xsl:transform> 元素。" }, { "NAMESPACE_UNDEF_ERR", "未声明名称空间前缀 ''{0}''。" }, { "FUNCTION_RESOLVE_ERR", "无法解析对函数 ''{0}'' 的调用。" }, { "NEED_LITERAL_ERR", "''{0}'' 的参数必须是文字字符串。" }, { "XPATH_PARSER_ERR", "解析 XPath 表达式 ''{0}'' 时出错。" }, { "REQUIRED_ATTR_ERR", "缺少所需属性 ''{0}''。" }, { "ILLEGAL_CHAR_ERR", "XPath 表达式中的字符 ''{0}'' 非法。" }, { "ILLEGAL_PI_ERR", "processing instruction 的名称 ''{0}'' 非法。" }, { "STRAY_ATTRIBUTE_ERR", "属性 ''{0}'' 在元素外部。" }, { "ILLEGAL_ATTRIBUTE_ERR", "属性 ''{0}'' 非法。" }, { "CIRCULAR_INCLUDE_ERR", "循环 import/include。已加载样式表 ''{0}''。" }, { "RESULT_TREE_SORT_ERR", "无法对结果树片段排序 (忽略 <xsl:sort> 元素)。必须在创建结果树时对节点进行排序。" }, { "SYMBOLS_REDEF_ERR", "已定义十进制格式 ''{0}''。" }, { "XSL_VERSION_ERR", "XSLTC 不支持 XSL 版本 ''{0}''。" }, { "CIRCULAR_VARIABLE_ERR", "''{0}'' 中存在循环变量/参数引用。" }, { "ILLEGAL_BINARY_OP_ERR", "二进制表达式的运算符未知。" }, { "ILLEGAL_ARG_ERR", "函数调用的参数非法。" }, { "DOCUMENT_ARG_ERR", "document() 函数的第二个参数必须是节点集。" }, { "MISSING_WHEN_ERR", "<xsl:choose> 中至少需要一个 <xsl:when> 元素。" }, { "MULTIPLE_OTHERWISE_ERR", "<xsl:choose> 中仅允许使用一个 <xsl:otherwise> 元素。" }, { "STRAY_OTHERWISE_ERR", "<xsl:otherwise> 只能在 <xsl:choose> 中使用。" }, { "STRAY_WHEN_ERR", "<xsl:when> 只能在 <xsl:choose> 中使用。" }, { "WHEN_ELEMENT_ERR", "<xsl:choose> 中仅允许使用 <xsl:when> 和 <xsl:otherwise> 元素。" }, { "UNNAMED_ATTRIBSET_ERR", "<xsl:attribute-set> 缺少 'name' 属性。" }, { "ILLEGAL_CHILD_ERR", "子元素非法。" }, { "ILLEGAL_ELEM_NAME_ERR", "无法调用元素 ''{0}''" }, { "ILLEGAL_ATTR_NAME_ERR", "无法调用属性 ''{0}''" }, { "ILLEGAL_TEXT_NODE_ERR", "文本数据位于顶级 <xsl:stylesheet> 元素外部。" }, { "SAX_PARSER_CONFIG_ERR", "JAXP 解析器未正确配置" }, { "INTERNAL_ERR", "不可恢复的 XSLTC 内部错误: ''{0}''" }, { "UNSUPPORTED_XSL_ERR", "XSL 元素 ''{0}'' 不受支持。" }, { "UNSUPPORTED_EXT_ERR", "XSLTC 扩展 ''{0}'' 无法识别。" }, { "MISSING_XSLT_URI_ERR", "输入文档不是样式表 (未在根元素中声明 XSL 名称空间)。" }, { "MISSING_XSLT_TARGET_ERR", "找不到样式表目标 ''{0}''。" }, { "ACCESSING_XSLT_TARGET_ERR", "由于 accessExternalStylesheet 属性设置的限制而不允许 ''{1}'' 访问, 因此无法读取样式表目标 ''{0}''。" }, { "NOT_IMPLEMENTED_ERR", "未实现: ''{0}''。" }, { "NOT_STYLESHEET_ERR", "输入文档不包含 XSL 样式表。" }, { "ELEMENT_PARSE_ERR", "无法解析元素 ''{0}''" }, { "KEY_USE_ATTR_ERR", "<key> 的 use 属性必须是 node, node-set, string 或 number。" }, { "OUTPUT_VERSION_ERR", "输出 XML 文档版本应为 1.0" }, { "ILLEGAL_RELAT_OP_ERR", "关系表达式的运算符未知" }, { "ATTRIBSET_UNDEF_ERR", "尝试使用不存在的属性集 ''{0}''。" }, { "ATTR_VAL_TEMPLATE_ERR", "无法解析属性值模板 ''{0}''。" }, { "UNKNOWN_SIG_TYPE_ERR", "类 ''{0}'' 的签名中的数据类型未知。" }, { "DATA_CONVERSION_ERR", "无法将数据类型 ''{0}'' 转换为 ''{1}''。" }, { "NO_TRANSLET_CLASS_ERR", "此 Templates 不包含有效的 translet 类定义。" }, { "NO_MAIN_TRANSLET_ERR", "此 Templates 不包含名为 ''{0}'' 的类。" }, { "TRANSLET_CLASS_ERR", "无法加载 translet 类 ''{0}''。" }, { "TRANSLET_OBJECT_ERR", "已加载 Translet 类, 但无法创建 translet 实例。" }, { "ERROR_LISTENER_NULL_ERR", "尝试将 ''{0}'' 的 ErrorListener 设置为空值" }, { "JAXP_UNKNOWN_SOURCE_ERR", "XSLTC 仅支持 StreamSource, SAXSource 和 DOMSource" }, { "JAXP_NO_SOURCE_ERR", "传递到 ''{0}'' 的 Source 对象不包含任何内容。" }, { "JAXP_COMPILE_ERR", "无法编译样式表" }, { "JAXP_INVALID_ATTR_ERR", "TransformerFactory 无法识别属性 ''{0}''。" }, { "JAXP_INVALID_ATTR_VALUE_ERR", "为 ''{0}'' 属性指定的值不正确。" }, { "JAXP_SET_RESULT_ERR", "必须先调用 setResult(), 再调用 startDocument()。" }, { "JAXP_NO_TRANSLET_ERR", "Transformer 没有内嵌的 translet 对象。" }, { "JAXP_NO_HANDLER_ERR", "转换结果没有定义的输出处理程序。" }, { "JAXP_NO_RESULT_ERR", "传递到 ''{0}'' 的 Result 对象无效。" }, { "JAXP_UNKNOWN_PROP_ERR", "尝试访问无效的 Transformer 属性 ''{0}''。" }, { "SAX2DOM_ADAPTER_ERR", "无法创建 SAX2DOM 适配器: ''{0}''。" }, { "XSLTC_SOURCE_ERR", "调用 XSLTCSource.build() 时未设置 systemId。" }, { "ER_RESULT_NULL", "Result 不能为空值" }, { "JAXP_INVALID_SET_PARAM_VALUE", "参数 {0} 的值必须是有效 Java 对象" }, { "COMPILE_STDIN_ERR", "-i 选项必须与 -o 选项一起使用。" }, { "COMPILE_USAGE_STR", "提要\n   java com.sun.org.apache.xalan.internal.xsltc.cmdline.Compile [-o <输出>]\n      [-d <目录>] [-j <jarfile>] [-p <程序包>]\n      [-n] [-x] [-u] [-v] [-h] { <样式表> | -i }\n\n选项\n   -o <输出>    为生成的 translet 分配\n                  名称 <输出>。默认情况下, translet 名称\n                  派生自 <样式表> 名称。如果要编译多个样式表, \n                  则忽略此选项。\n   -d <目录> 指定 translet 的目标目录\n   -j <jarfile>   将 translet 类打包到具有 <jarfile>\n                  指定的名称的 jar 文件中\n   -p <程序包>   为生成的所有 translet 类\n                  指定程序包名称前缀。\n   -n             启用模板内嵌 (默认行为\n                  通常可提供较好的性能)。\n   -x             启用其他调试消息输出\n   -u             将 <样式表> 参数解释为 URL\n   -i             强制编译器从 stdin 读取样式表\n   -v             输出编译器的版本\n   -h             输出此用法语句\n" }, { "TRANSFORM_USAGE_STR", "提要\n   java com.sun.org.apache.xalan.internal.xsltc.cmdline.Transform [-j <jarfile>]\n      [-x] [-n <迭代数>] {-u <document_url> | <文档>}\n      <类> [<param1>=<value1> ...]\n\n   使用 translet <类> 转换\n   <文档> 指定的 XML 文档。translet <类> 位于\n   用户的 CLASSPATH 或选择性指定的 <jarfile> 中。\n选项\n   -j <jarfile>    指定要从中加载 translet 的 jarfile\n   -x              启用其他调试消息输出\n   -n <迭代数> 运行 <迭代数> 次转换并\n                   显示配置文件信息\n   -u <document_url> 以 URL 形式指定 XML 输入文档\n" }, { "STRAY_SORT_ERR", "<xsl:sort> 只能在 <xsl:for-each> 或 <xsl:apply-templates> 中使用。" }, { "UNSUPPORTED_ENCODING", "此 JVM 中不支持输出编码 ''{0}''。" }, { "SYNTAX_ERR", "''{0}'' 中的语法错误。" }, { "CONSTRUCTOR_NOT_FOUND", "找不到外部构造器 ''{0}''。" }, { "NO_JAVA_FUNCT_THIS_REF", "非 static Java 函数 ''{0}'' 的第一个参数不是有效的对象引用。" }, { "TYPE_CHECK_ERR", "检查表达式 ''{0}'' 的类型时出错。" }, { "TYPE_CHECK_UNK_LOC_ERR", "检查未知位置的表达式类型时出错。" }, { "ILLEGAL_CMDLINE_OPTION_ERR", "命令行选项 ''{0}'' 无效。" }, { "CMDLINE_OPT_MISSING_ARG_ERR", "命令行选项 ''{0}'' 缺少所需参数。" }, { "WARNING_PLUS_WRAPPED_MSG", "WARNING:  ''{0}''\n       :{1}" }, { "WARNING_MSG", "WARNING:  ''{0}''" }, { "FATAL_ERR_PLUS_WRAPPED_MSG", "FATAL ERROR:  ''{0}''\n           :{1}" }, { "FATAL_ERR_MSG", "FATAL ERROR:  ''{0}''" }, { "ERROR_PLUS_WRAPPED_MSG", "ERROR:  ''{0}''\n     :{1}" }, { "ERROR_MSG", "ERROR:  ''{0}''" }, { "TRANSFORM_WITH_TRANSLET_STR", "使用 translet ''{0}'' 进行转换 " }, { "TRANSFORM_WITH_JAR_STR", "使用 translet ''{0}'' 从 jar 文件 ''{1}'' 进行转换" }, { "COULD_NOT_CREATE_TRANS_FACT", "无法创建 TransformerFactory 类 ''{0}'' 的实例。" }, { "TRANSLET_NAME_JAVA_CONFLICT", "名称 ''{0}'' 包含不允许在 Java 类名中使用的字符, 因此无法将此名称用作 translet 类的名称。已改用名称 ''{1}''。" }, { "COMPILER_ERROR_KEY", "编译器错误:" }, { "COMPILER_WARNING_KEY", "编译器警告:" }, { "RUNTIME_ERROR_KEY", "Translet 错误:" }, { "INVALID_QNAME_ERR", "其值必须为 QName 或由空格分隔的 QName 列表的属性具有值 ''{0}''" }, { "INVALID_NCNAME_ERR", "其值必须为 NCName 的属性具有值 ''{0}''" }, { "INVALID_METHOD_IN_OUTPUT", "<xsl:output> 元素的 method 属性具有值 ''{0}''。该值必须是 ''xml'', ''html'', ''text'' 或 qname-but-not-ncname 之一" }, { "JAXP_GET_FEATURE_NULL_NAME", "TransformerFactory.getFeature(String name) 中的功能名称不能为空值。" }, { "JAXP_SET_FEATURE_NULL_NAME", "TransformerFactory.setFeature(String name, boolean value) 中的功能名称不能为空值。" }, { "JAXP_UNSUPPORTED_FEATURE", "无法对此 TransformerFactory 设置功能 ''{0}''。" }, { "JAXP_SECUREPROCESSING_FEATURE", "FEATURE_SECURE_PROCESSING: 存在 Security Manager 时, 无法将此功能设置为“假”。" }, { "OUTLINE_ERR_TRY_CATCH", "内部 XSLTC 错误: 生成的字节代码包含 try-catch-finally 块, 无法进行 Outlined。" }, { "OUTLINE_ERR_UNBALANCED_MARKERS", "内部 XSLTC 错误: OutlineableChunkStart 和 OutlineableChunkEnd 标记必须配对并且正确嵌套。" }, { "OUTLINE_ERR_DELETED_TARGET", "内部 XSLTC 错误: 属于已进行 Outlined 的字节代码块的指令在原始方法中仍被引用。" }, { "OUTLINE_ERR_METHOD_TOO_BIG", "内部 XSLTC 错误: translet 中的方法超过了 Java 虚拟机的方法长度限制 64 KB。这通常是由于样式表中的模板非常大造成的。请尝试使用较小的模板重新构建样式表。" }, { "DESERIALIZE_TEMPLATES_ERR", "启用了 Java 安全时, 将禁用对反序列化 TemplatesImpl 的支持。可以通过将 jdk.xml.enableTemplatesImplDeserialization 系统属性设置为“真”来覆盖此设置。" } };
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\compile\\util\ErrorMessages_zh_CN.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */