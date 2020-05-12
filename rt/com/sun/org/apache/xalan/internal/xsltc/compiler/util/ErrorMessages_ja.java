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
/*    */ public class ErrorMessages_ja
/*    */   extends ListResourceBundle
/*    */ {
/*    */   public Object[][] getContents() {
/* 96 */     return new Object[][] { { "MULTIPLE_STYLESHEET_ERR", "同じファイルに複数のスタイルシートが定義されています。" }, { "TEMPLATE_REDEF_ERR", "テンプレート''{0}''はこのスタイルシート内ですでに定義されています。" }, { "TEMPLATE_UNDEF_ERR", "テンプレート''{0}''はこのスタイルシート内で定義されていません。" }, { "VARIABLE_REDEF_ERR", "変数''{0}''は同じスコープ内で複数定義されています。" }, { "VARIABLE_UNDEF_ERR", "変数またはパラメータ''{0}''が未定義です。" }, { "CLASS_NOT_FOUND_ERR", "クラス''{0}''が見つかりません。" }, { "METHOD_NOT_FOUND_ERR", "外部メソッド''{0}''が見つかりません(publicである必要があります)。" }, { "ARGUMENT_CONVERSION_ERR", "メソッド''{0}''の呼出しの引数タイプまたは戻り型を変換できません" }, { "FILE_NOT_FOUND_ERR", "ファイルまたはURI ''{0}''が見つかりません。" }, { "INVALID_URI_ERR", "URI ''{0}''が無効です。" }, { "FILE_ACCESS_ERR", "ファイルまたはURI ''{0}''を開くことができません。" }, { "MISSING_ROOT_ERR", "<xsl:stylesheet>または<xsl:transform>の要素がありません。" }, { "NAMESPACE_UNDEF_ERR", "ネームスペースの接頭辞''{0}''は宣言されていません。" }, { "FUNCTION_RESOLVE_ERR", "関数''{0}''の呼出しを解決できません。" }, { "NEED_LITERAL_ERR", "''{0}''への引数はリテラル文字列である必要があります。" }, { "XPATH_PARSER_ERR", "XPath式''{0}''の解析中にエラーが発生しました。" }, { "REQUIRED_ATTR_ERR", "必須属性''{0}''がありません。" }, { "ILLEGAL_CHAR_ERR", "XPath式の文字''{0}''は無効です。" }, { "ILLEGAL_PI_ERR", "処理命令の名前''{0}''は無効です。" }, { "STRAY_ATTRIBUTE_ERR", "属性''{0}''が要素の外側にあります。" }, { "ILLEGAL_ATTRIBUTE_ERR", "不正な属性''{0}''です。" }, { "CIRCULAR_INCLUDE_ERR", "インポートまたはインクルードが循環しています。スタイルシート''{0}''はすでにロードされています。" }, { "RESULT_TREE_SORT_ERR", "結果ツリー・フラグメントはソートできません(<xsl:sort>要素は無視されます)。結果ツリーを作成するときにノードをソートする必要があります。" }, { "SYMBOLS_REDEF_ERR", "10進数フォーマット''{0}''はすでに定義されています。" }, { "XSL_VERSION_ERR", "XSLバージョン''{0}''はXSLTCによってサポートされていません。" }, { "CIRCULAR_VARIABLE_ERR", "''{0}''内の変数参照またはパラメータ参照が循環しています。" }, { "ILLEGAL_BINARY_OP_ERR", "2進数の式に対する不明な演算子です。" }, { "ILLEGAL_ARG_ERR", "関数呼出しの引数が不正です。" }, { "DOCUMENT_ARG_ERR", "document()関数の2番目の引数はノードセットである必要があります。" }, { "MISSING_WHEN_ERR", "<xsl:choose>内には少なくとも1つの<xsl:when>要素が必要です。" }, { "MULTIPLE_OTHERWISE_ERR", "<xsl:choose>内では1つの<xsl:otherwise>要素のみが許可されています。" }, { "STRAY_OTHERWISE_ERR", "<xsl:otherwise>は<xsl:choose>内でのみ使用できます。" }, { "STRAY_WHEN_ERR", "<xsl:when>は<xsl:choose>内でのみ使用できます。" }, { "WHEN_ELEMENT_ERR", "<xsl:choose>内では<xsl:when>と<xsl:otherwise>の要素のみが許可されます。" }, { "UNNAMED_ATTRIBSET_ERR", "<xsl:attribute-set>に'name'属性がありません。" }, { "ILLEGAL_CHILD_ERR", "子要素が不正です。" }, { "ILLEGAL_ELEM_NAME_ERR", "要素''{0}''を呼び出すことはできません" }, { "ILLEGAL_ATTR_NAME_ERR", "属性''{0}''を呼び出すことはできません" }, { "ILLEGAL_TEXT_NODE_ERR", "テキスト・データはトップレベルの<xsl:stylesheet>要素の外側にあります。" }, { "SAX_PARSER_CONFIG_ERR", "JAXPパーサーが正しく構成されていません" }, { "INTERNAL_ERR", "リカバリ不能なXSLTC内部エラー: ''{0}''" }, { "UNSUPPORTED_XSL_ERR", "XSL要素''{0}''はサポートされていません。" }, { "UNSUPPORTED_EXT_ERR", "XSLTC拡張''{0}''は認識されません。" }, { "MISSING_XSLT_URI_ERR", "入力ドキュメントはスタイルシートではありません(XSLのネームスペースはルート要素内で宣言されていません)。" }, { "MISSING_XSLT_TARGET_ERR", "スタイルシート・ターゲット''{0}''が見つかりませんでした。" }, { "ACCESSING_XSLT_TARGET_ERR", "accessExternalStylesheetプロパティで設定された制限により''{1}''アクセスが許可されていないため、スタイルシート・ターゲット''{0}''を読み取れませんでした。" }, { "NOT_IMPLEMENTED_ERR", "''{0}''が実装されていません。" }, { "NOT_STYLESHEET_ERR", "入力ドキュメントにXSLスタイルシートが含まれていません。" }, { "ELEMENT_PARSE_ERR", "要素''{0}''を解析できませんでした" }, { "KEY_USE_ATTR_ERR", "<key>のuse属性は、ノード、ノードセット、文字列または数値である必要があります。" }, { "OUTPUT_VERSION_ERR", "出力XMLドキュメントのバージョンは1.0である必要があります" }, { "ILLEGAL_RELAT_OP_ERR", "関係式の不明な演算子です" }, { "ATTRIBSET_UNDEF_ERR", "存在しない属性セット''{0}''を使用しようとしました。" }, { "ATTR_VAL_TEMPLATE_ERR", "属性値テンプレート''{0}''を解析できません。" }, { "UNKNOWN_SIG_TYPE_ERR", "クラス''{0}''の署名に不明なデータ型があります。" }, { "DATA_CONVERSION_ERR", "データ型''{0}''を''{1}''に変換できません。" }, { "NO_TRANSLET_CLASS_ERR", "このテンプレートには有効なtransletクラス定義が含まれていません。" }, { "NO_MAIN_TRANSLET_ERR", "このテンプレートには名前''{0}''を持つクラスが含まれていません。" }, { "TRANSLET_CLASS_ERR", "transletクラス''{0}''をロードできませんでした。" }, { "TRANSLET_OBJECT_ERR", "Transletクラスがロードされましたが、transletインスタンスを作成できません。" }, { "ERROR_LISTENER_NULL_ERR", "''{0}''のErrorListenerをnullに設定しようとしました" }, { "JAXP_UNKNOWN_SOURCE_ERR", "StreamSource、SAXSourceおよびDOMSourceのみがXSLTCによってサポートされています" }, { "JAXP_NO_SOURCE_ERR", "''{0}''に渡されたソース・オブジェクトにコンテンツがありません。" }, { "JAXP_COMPILE_ERR", "スタイルシートをコンパイルできませんでした" }, { "JAXP_INVALID_ATTR_ERR", "TransformerFactoryは属性''{0}''を認識しません。" }, { "JAXP_INVALID_ATTR_VALUE_ERR", "''{0}''属性に指定された値が正しくありません。" }, { "JAXP_SET_RESULT_ERR", "setResult()はstartDocument()よりも前に呼び出す必要があります。" }, { "JAXP_NO_TRANSLET_ERR", "トランスフォーマにはカプセル化されたtransletオブジェクトがありません。" }, { "JAXP_NO_HANDLER_ERR", "変換結果に対して定義済の出力ハンドラがありません。" }, { "JAXP_NO_RESULT_ERR", "''{0}''に渡された結果オブジェクトは無効です。" }, { "JAXP_UNKNOWN_PROP_ERR", "無効なトランスフォーマ・プロパティ''{0}''にアクセスしようとしました。" }, { "SAX2DOM_ADAPTER_ERR", "SAX2DOMアダプタ''{0}''を作成できませんでした。" }, { "XSLTC_SOURCE_ERR", "systemIdを設定せずにXSLTCSource.build()が呼び出されました。" }, { "ER_RESULT_NULL", "結果はnullにできません" }, { "JAXP_INVALID_SET_PARAM_VALUE", "パラメータ{0}は有効なJavaオブジェクトである必要があります" }, { "COMPILE_STDIN_ERR", "-iオプションは-oオプションとともに使用する必要があります。" }, { "COMPILE_USAGE_STR", "形式\n   java com.sun.org.apache.xalan.internal.xsltc.cmdline.Compile [-o <output>]\n      [-d <directory>] [-j <jarfile>] [-p <package>]\n      [-n] [-x] [-u] [-v] [-h] { <stylesheet> | -i }\n\nOPTIONS\n   -o <output>    名前<output>を生成済transletに\n                  割り当てる。デフォルトでは、translet名は\n                  <stylesheet>名に由来します。このオプションは\n                  複数のスタイルシートをコンパイルする場合は無視されます。\n   -d <directory> transletの宛先ディレクトリを指定する\n   -j <jarfile>   <jarfile>で指定される名前のjarファイルにtransletクラスを\n                  パッケージする\n   -p <package>   生成されるすべてのtransletクラスのパッケージ名\n                  接頭辞を指定する。\n   -n             テンプレートのインライン化を有効にする(平均してデフォルト動作の方が\n                  優れています)。\n   -x             追加のデバッグ・メッセージ出力をオンにする\n   -u             <stylesheet>引数をURLとして解釈する\n   -i             スタイルシートをstdinから読み込むことをコンパイラに強制する\n   -v             コンパイラのバージョンを出力する\n   -h             この使用方法の文を出力する\n" }, { "TRANSFORM_USAGE_STR", "形式 \n   java com.sun.org.apache.xalan.internal.xsltc.cmdline.Transform [-j <jarfile>]\n      [-x] [-n <iterations>] {-u <document_url> | <document>}\n      <class> [<param1>=<value1> ...]\n\n   translet <class>を使用して、<document>で指定される\n   XMLドキュメントを変換する。translet <class>は\n   ユーザーのCLASSPATH内か、オプションで指定された<jarfile>内にあります。\nOPTIONS\n   -j <jarfile>    transletをロードするjarfileを指定する\n   -x              追加のデバッグ・メッセージ出力をオンにする\n   -n <iterations> 変換を<iterations>回実行し、\n                   プロファイリング情報を表示する\n   -u <document_url> XML入力ドキュメントをURLとして指定する\n" }, { "STRAY_SORT_ERR", "<xsl:sort>は<xsl:for-each>または<xsl:apply-templates>の内部でのみ使用できます。" }, { "UNSUPPORTED_ENCODING", "出力エンコーディング''{0}''はこのJVMではサポートされていません。" }, { "SYNTAX_ERR", "''{0}''に構文エラーがあります。" }, { "CONSTRUCTOR_NOT_FOUND", "外部コンストラクタ''{0}''が見つかりません。" }, { "NO_JAVA_FUNCT_THIS_REF", "staticでないJava関数''{0}''の最初の引数は無効なオブジェクト参照です。" }, { "TYPE_CHECK_ERR", "式''{0}''のタイプの確認中にエラーが発生しました。" }, { "TYPE_CHECK_UNK_LOC_ERR", "不明な場所での式のタイプの確認中にエラーが発生しました。" }, { "ILLEGAL_CMDLINE_OPTION_ERR", "コマンド行オプション''{0}''は無効です。" }, { "CMDLINE_OPT_MISSING_ARG_ERR", "コマンド行オプション''{0}''に必須の引数がありません。" }, { "WARNING_PLUS_WRAPPED_MSG", "WARNING:  ''{0}''\n       :{1}" }, { "WARNING_MSG", "WARNING:  ''{0}''" }, { "FATAL_ERR_PLUS_WRAPPED_MSG", "FATAL ERROR:  ''{0}''\n           :{1}" }, { "FATAL_ERR_MSG", "FATAL ERROR:  ''{0}''" }, { "ERROR_PLUS_WRAPPED_MSG", "ERROR:  ''{0}''\n     :{1}" }, { "ERROR_MSG", "ERROR:  ''{0}''" }, { "TRANSFORM_WITH_TRANSLET_STR", "translet ''{0}''を使用して変換します " }, { "TRANSFORM_WITH_JAR_STR", "translet ''{0}''を使用してjarファイル''{1}''から変換します" }, { "COULD_NOT_CREATE_TRANS_FACT", "TransformerFactoryクラス''{0}''のインスタンスを作成できませんでした。" }, { "TRANSLET_NAME_JAVA_CONFLICT", "名前''{0}''にはJavaクラスの名前に許可されていない文字が含まれているため、transletクラスの名前として使用できませんでした。名前''{1}''がかわりに使用されます。" }, { "COMPILER_ERROR_KEY", "コンパイラ・エラー:" }, { "COMPILER_WARNING_KEY", "コンパイラの警告:" }, { "RUNTIME_ERROR_KEY", "Transletエラー:" }, { "INVALID_QNAME_ERR", "値が1つのQNameまたはQNameの空白文字区切りリストであることが必要な属性の値が''{0}''でした" }, { "INVALID_NCNAME_ERR", "値がNCNameであることが必要な属性の値が''{0}''でした" }, { "INVALID_METHOD_IN_OUTPUT", "<xsl:output>要素のメソッド属性の値が''{0}''でした。値は''xml''、''html''、''text''またはqname-but-not-ncnameのいずれかである必要があります" }, { "JAXP_GET_FEATURE_NULL_NAME", "機能名はTransformerFactory.getFeature(String name)内でnullにできません。" }, { "JAXP_SET_FEATURE_NULL_NAME", "機能名はTransformerFactory.setFeature(String name, boolean value)内でnullにできません。" }, { "JAXP_UNSUPPORTED_FEATURE", "機能''{0}''をこのTransformerFactoryに設定できません。" }, { "JAXP_SECUREPROCESSING_FEATURE", "FEATURE_SECURE_PROCESSING: セキュリティ・マネージャが存在するとき、機能をfalseに設定できません。" }, { "OUTLINE_ERR_TRY_CATCH", "内部XSLTCエラー: 生成されたバイト・コードは、try-catch-finallyブロックを含んでいるため、アウトライン化できません。" }, { "OUTLINE_ERR_UNBALANCED_MARKERS", "内部XSLTCエラー: OutlineableChunkStartマーカーとOutlineableChunkEndマーカーは、対になっており、かつ正しくネストされている必要があります。" }, { "OUTLINE_ERR_DELETED_TARGET", "内部XSLTCエラー: アウトライン化されたバイト・コードのブロックの一部であった命令は、元のメソッドの中でまだ参照されています。" }, { "OUTLINE_ERR_METHOD_TOO_BIG", "内部XSLTCエラー: トランスレット内のメソッドが、Java仮想マシンの制限(1メソッドの長さは最大64キロバイト)を超えています。一般的に、スタイルシート内のテンプレートのサイズが大き過ぎることが原因として考えられます。小さいサイズのテンプレートを使用して、スタイルシートを再構成してください。" }, { "DESERIALIZE_TEMPLATES_ERR", "Javaセキュリティが有効化されている場合、TemplatesImplのデシリアライズのサポートは無効化されます。これは、jdk.xml.enableTemplatesImplDeserializationシステム・プロパティをtrueに設定してオーバーライドできます。" } };
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\compile\\util\ErrorMessages_ja.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */