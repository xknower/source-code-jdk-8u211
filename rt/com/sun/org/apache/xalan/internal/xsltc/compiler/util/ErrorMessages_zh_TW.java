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
/*    */ public class ErrorMessages_zh_TW
/*    */   extends ListResourceBundle
/*    */ {
/*    */   public Object[][] getContents() {
/* 96 */     return new Object[][] { { "MULTIPLE_STYLESHEET_ERR", "相同檔案中定義了超過一個樣式表。" }, { "TEMPLATE_REDEF_ERR", "樣板 ''{0}'' 已經定義在此樣式表中。" }, { "TEMPLATE_UNDEF_ERR", "樣板 ''{0}'' 未定義在此樣式表中。" }, { "VARIABLE_REDEF_ERR", "變數 ''{0}'' 在相同範圍中定義多次。" }, { "VARIABLE_UNDEF_ERR", "變數或參數 ''{0}'' 未定義。" }, { "CLASS_NOT_FOUND_ERR", "找不到類別 ''{0}''。" }, { "METHOD_NOT_FOUND_ERR", "找不到外部方法 ''{0}'' (必須為公用)。" }, { "ARGUMENT_CONVERSION_ERR", "無法轉換呼叫方法 ''{0}'' 中的引數/傳回類型" }, { "FILE_NOT_FOUND_ERR", "找不到檔案或 URI ''{0}''。" }, { "INVALID_URI_ERR", "無效的 URI ''{0}''。" }, { "FILE_ACCESS_ERR", "無法開啟檔案或 URI ''{0}''。" }, { "MISSING_ROOT_ERR", "預期 <xsl:stylesheet> 或 <xsl:transform> 元素。" }, { "NAMESPACE_UNDEF_ERR", "未宣告命名空間前置碼 ''{0}''。" }, { "FUNCTION_RESOLVE_ERR", "無法解析函數 ''{0}'' 的呼叫。" }, { "NEED_LITERAL_ERR", "''{0}'' 的引數必須是文字字串。" }, { "XPATH_PARSER_ERR", "剖析 XPath 表示式 ''{0}'' 時發生錯誤。" }, { "REQUIRED_ATTR_ERR", "遺漏必要的屬性 ''{0}''。" }, { "ILLEGAL_CHAR_ERR", "XPath 表示式中無效的字元 ''{0}''。" }, { "ILLEGAL_PI_ERR", "處理指示的無效名稱 ''{0}''。" }, { "STRAY_ATTRIBUTE_ERR", "屬性 ''{0}'' 在元素之外。" }, { "ILLEGAL_ATTRIBUTE_ERR", "無效的屬性 ''{0}''。" }, { "CIRCULAR_INCLUDE_ERR", "循環匯入/包含。已經載入樣式表 ''{0}''。" }, { "RESULT_TREE_SORT_ERR", "無法排序 Result-tree 片段 (忽略 <xsl:sort> 元素)。建立結果樹狀結構時，必須排序節點。" }, { "SYMBOLS_REDEF_ERR", "已經定義十進位格式 ''{0}''。" }, { "XSL_VERSION_ERR", "XSLTC 不支援 XSL 版本 ''{0}''。" }, { "CIRCULAR_VARIABLE_ERR", "在 ''{0}'' 中有循環變數/參數參照。" }, { "ILLEGAL_BINARY_OP_ERR", "二進位表示式不明的運算子。" }, { "ILLEGAL_ARG_ERR", "函數呼叫無效的引數。" }, { "DOCUMENT_ARG_ERR", "document() 函數的第二個引數必須是 node-set。" }, { "MISSING_WHEN_ERR", "在 <xsl:choose> 中至少需要一個 <xsl:when> 元素。" }, { "MULTIPLE_OTHERWISE_ERR", "在 <xsl:choose> 中只允許一個 <xsl:otherwise> 元素。" }, { "STRAY_OTHERWISE_ERR", "<xsl:otherwise> 只能在 <xsl:choose> 內使用。" }, { "STRAY_WHEN_ERR", "<xsl:when> 只能在 <xsl:choose> 內使用。" }, { "WHEN_ELEMENT_ERR", "在 <xsl:choose> 中只允許 <xsl:when> 與 <xsl:otherwise> 元素。" }, { "UNNAMED_ATTRIBSET_ERR", "<xsl:attribute-set> 遺漏 'name' 屬性。" }, { "ILLEGAL_CHILD_ERR", "無效的子項元素。" }, { "ILLEGAL_ELEM_NAME_ERR", "您無法呼叫元素 ''{0}''" }, { "ILLEGAL_ATTR_NAME_ERR", "您無法呼叫屬性 ''{0}''" }, { "ILLEGAL_TEXT_NODE_ERR", "最上層 <xsl:stylesheet> 元素之外的文字資料。" }, { "SAX_PARSER_CONFIG_ERR", "未正確設定 JAXP 剖析器" }, { "INTERNAL_ERR", "無法復原的 XSLTC-internal 錯誤: ''{0}''" }, { "UNSUPPORTED_XSL_ERR", "不支援的 XSL 元素 ''{0}''。" }, { "UNSUPPORTED_EXT_ERR", "無法辨識的 XSLTC 擴充套件 ''{0}''。" }, { "MISSING_XSLT_URI_ERR", "輸入文件不是樣式表 (根元素中未宣告 XSL 命名空間)。" }, { "MISSING_XSLT_TARGET_ERR", "找不到樣式表目標 ''{0}''。" }, { "ACCESSING_XSLT_TARGET_ERR", "無法讀取樣式表目標 ''{0}''，因為 accessExternalStylesheet 屬性設定的限制，所以不允許 ''{1}'' 存取。" }, { "NOT_IMPLEMENTED_ERR", "未實行: ''{0}''。" }, { "NOT_STYLESHEET_ERR", "輸入文件未包含 XSL 樣式表。" }, { "ELEMENT_PARSE_ERR", "無法剖析元素 ''{0}''" }, { "KEY_USE_ATTR_ERR", "<key> 的使用屬性必須是節點、node-set、字串或數字。" }, { "OUTPUT_VERSION_ERR", "輸出 XML 文件版本應為 1.0" }, { "ILLEGAL_RELAT_OP_ERR", "關聯表示式的運算子不明" }, { "ATTRIBSET_UNDEF_ERR", "嘗試使用不存在的屬性集 ''{0}''。" }, { "ATTR_VAL_TEMPLATE_ERR", "無法剖析屬性值樣板 ''{0}''。" }, { "UNKNOWN_SIG_TYPE_ERR", "類別 ''{0}'' 簽章有不明的 data-type。" }, { "DATA_CONVERSION_ERR", "無法轉換 data-type ''{0}'' 為 ''{1}''。" }, { "NO_TRANSLET_CLASS_ERR", "此樣板未包含有效的 translet 類別定義。" }, { "NO_MAIN_TRANSLET_ERR", "此樣板未包含名稱為 ''{0}'' 的類別。" }, { "TRANSLET_CLASS_ERR", "無法載入 translet 類別 ''{0}''。" }, { "TRANSLET_OBJECT_ERR", "已載入 translet 類別，但無法建立 translet 執行處理。" }, { "ERROR_LISTENER_NULL_ERR", "嘗試將 ''{0}'' 的 ErrorListener 設定為空值" }, { "JAXP_UNKNOWN_SOURCE_ERR", "XSLTC 僅支援 StreamSource、SAXSource 與 DOMSource" }, { "JAXP_NO_SOURCE_ERR", "傳送至 ''{0}'' 的來源物件沒有內容。" }, { "JAXP_COMPILE_ERR", "無法編譯樣式表" }, { "JAXP_INVALID_ATTR_ERR", "TransformerFactory 無法辨識屬性 ''{0}''。" }, { "JAXP_INVALID_ATTR_VALUE_ERR", "為 ''{0}'' 屬性指定的值不正確。" }, { "JAXP_SET_RESULT_ERR", "呼叫 startDocument() 之前，必須先呼叫 setResult()。" }, { "JAXP_NO_TRANSLET_ERR", "轉換器沒有封裝的 translet 物件。" }, { "JAXP_NO_HANDLER_ERR", "轉換結果沒有定義的輸出處理程式。" }, { "JAXP_NO_RESULT_ERR", "傳送至 ''{0}'' 的結果物件無效。" }, { "JAXP_UNKNOWN_PROP_ERR", "嘗試存取無效的轉換器屬性 ''{0}''。" }, { "SAX2DOM_ADAPTER_ERR", "無法建立 SAX2DOM 轉接器: ''{0}''。" }, { "XSLTC_SOURCE_ERR", "未設定 systemId 而呼叫 XSLTCSource.build()。" }, { "ER_RESULT_NULL", "結果不應為空值" }, { "JAXP_INVALID_SET_PARAM_VALUE", "參數 {0} 的值必須是有效的 Java 物件" }, { "COMPILE_STDIN_ERR", "-i 選項必須與 -o 選項一起使用。" }, { "COMPILE_USAGE_STR", "概要\n   java com.sun.org.apache.xalan.internal.xsltc.cmdline.Compile [-o <output>]\n      [-d <directory>] [-j <jarfile>] [-p <package>]\n      [-n] [-x] [-u] [-v] [-h] { <stylesheet> | -i }\n\n選項\n   -o <output>    指派名稱 <output> 至產生的\n                  translet。根據預設，translet 名稱\n                  衍生自 <stylesheet> 名稱。若編譯\n                  多個樣式表，將忽略此選項。\n   -d <directory> 指定 translet 的目的地目錄\n   -j <jarfile>   封裝 translet 類別成為 jar 檔案，\n                  名稱指定為 <jarfile>\n   -p <package>   指定所有產生的 translet 類別的套裝程式\n                  名稱前置碼。\n   -n             啟用樣板內嵌 (預設行為一般而言\n                  較佳)。\n   -x             開啟額外的除錯訊息輸出\n   -u             解譯 <stylesheet> 引數為 URL\n   -i             強制編譯器從 stdin 讀取樣式表\n   -v             列印編譯器版本\n   -h             列印此用法敘述\n" }, { "TRANSFORM_USAGE_STR", "概要 \n   java com.sun.org.apache.xalan.internal.xsltc.cmdline.Transform [-j <jarfile>]\n      [-x] [-n <iterations>] {-u <document_url> | <document>}\n      <class> [<param1>=<value1> ...]\n\n   使用 translet <class> 轉換指定為 <document> \n   的 XML 文件。translet <class> 位於\n   使用者的類別路徑，或是在選擇性指定的 <jarfile> 中。\n選項\n   -j <jarfile>    指定載入 translet 的來源 jarfile\n   -x              開啟額外的除錯訊息輸出\n   -n <iterations> 執行轉換 <iterations> 次數與\n                   顯示分析資訊\n   -u <document_url> 指定 XML 輸入文件為 URL\n" }, { "STRAY_SORT_ERR", "<xsl:sort> 只能在 <xsl:for-each> 或 <xsl:apply-templates> 中使用。" }, { "UNSUPPORTED_ENCODING", "此 JVM 不支援輸出編碼 ''{0}''。" }, { "SYNTAX_ERR", "''{0}'' 中的語法錯誤。" }, { "CONSTRUCTOR_NOT_FOUND", "找不到外部建構子 ''{0}''。" }, { "NO_JAVA_FUNCT_THIS_REF", "非靜態 Java 函數 ''{0}'' 的第一個引數不是有效的物件參照。" }, { "TYPE_CHECK_ERR", "檢查表示式 ''{0}'' 的類型時發生錯誤。" }, { "TYPE_CHECK_UNK_LOC_ERR", "檢查位於不明位置表示式的類型時發生錯誤。" }, { "ILLEGAL_CMDLINE_OPTION_ERR", "命令行選項 ''{0}'' 無效。" }, { "CMDLINE_OPT_MISSING_ARG_ERR", "命令行選項 ''{0}'' 遺漏必要的引數。" }, { "WARNING_PLUS_WRAPPED_MSG", "WARNING:  ''{0}''\n       :{1}" }, { "WARNING_MSG", "WARNING:  ''{0}''" }, { "FATAL_ERR_PLUS_WRAPPED_MSG", "FATAL ERROR:  ''{0}''\n           :{1}" }, { "FATAL_ERR_MSG", "FATAL ERROR:  ''{0}''" }, { "ERROR_PLUS_WRAPPED_MSG", "ERROR:  ''{0}''\n     :{1}" }, { "ERROR_MSG", "ERROR:  ''{0}''" }, { "TRANSFORM_WITH_TRANSLET_STR", "使用 translet ''{0}'' 轉換" }, { "TRANSFORM_WITH_JAR_STR", "使用來自 jar 檔案 ''{1}'' 的 translet ''{0}'' 轉換" }, { "COULD_NOT_CREATE_TRANS_FACT", "無法建立 TransformerFactory 類別 ''{0}'' 的執行處理。" }, { "TRANSLET_NAME_JAVA_CONFLICT", "名稱 ''{0}'' 無法作為 translet 類別的名稱，因為它包含 Java 類別名稱不允許的字元。請改用名稱 ''{1}''。" }, { "COMPILER_ERROR_KEY", "編譯器錯誤:" }, { "COMPILER_WARNING_KEY", "編譯器警告:" }, { "RUNTIME_ERROR_KEY", "Translet 錯誤:" }, { "INVALID_QNAME_ERR", "值必須為 QName 或使用空格加以區隔的 QNames 清單的屬性，具有值 ''{0}''" }, { "INVALID_NCNAME_ERR", "值必須為 NCName 的屬性，具有值 ''{0}''" }, { "INVALID_METHOD_IN_OUTPUT", "<xsl:output> 元素的方法屬性具有值 ''{0}''。此值必須是 ''xml''、''html''、''text'' 或 qname-but-not-ncname 其中之一" }, { "JAXP_GET_FEATURE_NULL_NAME", "TransformerFactory.getFeature(字串名稱) 中的功能名稱不可為空值。" }, { "JAXP_SET_FEATURE_NULL_NAME", "TransformerFactory.setFeature(字串名稱, 布林值) 中的功能名稱不可為空值。" }, { "JAXP_UNSUPPORTED_FEATURE", "無法在此 TransformerFactory 上設定功能 ''{0}''。" }, { "JAXP_SECUREPROCESSING_FEATURE", "FEATURE_SECURE_PROCESSING: 安全管理程式存在時，無法將功能設為偽。" }, { "OUTLINE_ERR_TRY_CATCH", "內部 XSLTC 錯誤:  產生的位元組碼包含 try-catch-finally 區塊，無法加以 outlined." }, { "OUTLINE_ERR_UNBALANCED_MARKERS", "內部 XSLTC 錯誤:  OutlineableChunkStart 和 OutlineableChunkEnd 標記必須成對出現，並使用正確的巢狀結構。" }, { "OUTLINE_ERR_DELETED_TARGET", "內部 XSLTC 錯誤:  原始方法中仍然參照屬於 outlined 位元組碼區塊一部分的指示。" }, { "OUTLINE_ERR_METHOD_TOO_BIG", "內部 XSLTC 錯誤:  translet 中的方法超過 Java 虛擬機器對於方法長度 64 KB 的限制。這通常是因為樣式表中有非常大的樣板。請嘗試重新組織您的樣式表以使用較小的樣板。" }, { "DESERIALIZE_TEMPLATES_ERR", "啟用 Java 安全時，會停用還原序列化 TemplatesImpl 的支援。將 jdk.xml.enableTemplatesImplDeserialization 系統屬性設為真即可覆寫此設定。" } };
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\compile\\util\ErrorMessages_zh_TW.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */