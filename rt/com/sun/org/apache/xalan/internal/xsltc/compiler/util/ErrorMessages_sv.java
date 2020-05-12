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
/*    */ public class ErrorMessages_sv
/*    */   extends ListResourceBundle
/*    */ {
/*    */   public Object[][] getContents() {
/* 96 */     return new Object[][] { { "MULTIPLE_STYLESHEET_ERR", "Fler än en formatmall har definierats i samma fil." }, { "TEMPLATE_REDEF_ERR", "Mallen ''{0}'' har redan definierats i denna formatmall." }, { "TEMPLATE_UNDEF_ERR", "Mallen ''{0}'' har inte definierats i denna formatmall." }, { "VARIABLE_REDEF_ERR", "Variabeln ''{0}'' har definierats flera gånger i samma omfattning." }, { "VARIABLE_UNDEF_ERR", "Variabeln eller parametern ''{0}'' har inte definierats." }, { "CLASS_NOT_FOUND_ERR", "Hittar inte klassen ''{0}''." }, { "METHOD_NOT_FOUND_ERR", "Hittar inte den externa metoden ''{0}'' (måste vara allmän)." }, { "ARGUMENT_CONVERSION_ERR", "Kan inte konvertera argument/returtyp vid anrop till metoden ''{0}''" }, { "FILE_NOT_FOUND_ERR", "Fil eller URI ''{0}'' hittades inte." }, { "INVALID_URI_ERR", "Ogiltig URI ''{0}''." }, { "FILE_ACCESS_ERR", "Kan inte öppna filen eller URI ''{0}''." }, { "MISSING_ROOT_ERR", "Förväntade <xsl:stylesheet>- eller <xsl:transform>-element." }, { "NAMESPACE_UNDEF_ERR", "Namnrymdsprefixet ''{0}'' har inte deklarerats." }, { "FUNCTION_RESOLVE_ERR", "Kan inte matcha anrop till funktionen ''{0}''." }, { "NEED_LITERAL_ERR", "Argument till ''{0}'' måste vara en litteral sträng." }, { "XPATH_PARSER_ERR", "Fel vid tolkning av XPath-uttrycket ''{0}''." }, { "REQUIRED_ATTR_ERR", "Det obligatoriska attributet ''{0}'' saknas." }, { "ILLEGAL_CHAR_ERR", "Otillåtet tecken ''{0}'' i XPath-uttrycket." }, { "ILLEGAL_PI_ERR", "''{0}'' är ett otillåtet namn i bearbetningsinstruktion." }, { "STRAY_ATTRIBUTE_ERR", "Attributet ''{0}'' finns utanför elementet." }, { "ILLEGAL_ATTRIBUTE_ERR", "''{0}'' är ett otillåtet attribut." }, { "CIRCULAR_INCLUDE_ERR", "Cirkulär import/include. Formatmallen ''{0}'' har redan laddats." }, { "RESULT_TREE_SORT_ERR", "Resultatträdfragment kan inte sorteras (<xsl:sort>-element ignoreras). Du måste sortera noderna när resultatträdet skapas." }, { "SYMBOLS_REDEF_ERR", "Decimalformateringen ''{0}'' har redan definierats." }, { "XSL_VERSION_ERR", "XSL-versionen ''{0}'' understöds inte i XSLTC." }, { "CIRCULAR_VARIABLE_ERR", "Cirkulär variabel-/parameterreferens i ''{0}''." }, { "ILLEGAL_BINARY_OP_ERR", "Okänd operator för binärt uttryck." }, { "ILLEGAL_ARG_ERR", "Otillåtna argument för funktionsanrop." }, { "DOCUMENT_ARG_ERR", "Andra argumentet för document()-funktion måste vara en noduppsättning." }, { "MISSING_WHEN_ERR", "Minst ett <xsl:when>-element krävs i <xsl:choose>." }, { "MULTIPLE_OTHERWISE_ERR", "Endast ett <xsl:otherwise>-element är tillåtet i <xsl:choose>." }, { "STRAY_OTHERWISE_ERR", "<xsl:otherwise> används endast inom <xsl:choose>." }, { "STRAY_WHEN_ERR", "<xsl:when> används endast inom <xsl:choose>." }, { "WHEN_ELEMENT_ERR", "Endast <xsl:when>- och <xsl:otherwise>-element är tillåtna i <xsl:choose>." }, { "UNNAMED_ATTRIBSET_ERR", "<xsl:attribute-set> saknar 'name'-attribut." }, { "ILLEGAL_CHILD_ERR", "Otillåtet underordnat element." }, { "ILLEGAL_ELEM_NAME_ERR", "Du kan inte anropa elementet ''{0}''" }, { "ILLEGAL_ATTR_NAME_ERR", "Du kan inte anropa attributet ''{0}''" }, { "ILLEGAL_TEXT_NODE_ERR", "Textdata utanför toppnivåelementet <xsl:stylesheet>." }, { "SAX_PARSER_CONFIG_ERR", "JAXP-parser har inte konfigurerats korrekt" }, { "INTERNAL_ERR", "Oåterkalleligt internt XSLTC-fel: ''{0}''" }, { "UNSUPPORTED_XSL_ERR", "XSL-elementet ''{0}'' stöds inte." }, { "UNSUPPORTED_EXT_ERR", "XSLTC-tillägget ''{0}'' är okänt." }, { "MISSING_XSLT_URI_ERR", "Indatadokumentet är ingen formatmall (XSL-namnrymden har inte deklarerats i rotelementet)." }, { "MISSING_XSLT_TARGET_ERR", "Hittade inte formatmallen ''{0}''." }, { "ACCESSING_XSLT_TARGET_ERR", "Kunde inte läsa formatmallen ''{0}'', eftersom ''{1}''-åtkomst inte tillåts på grund av begränsning som anges av egenskapen accessExternalStylesheet." }, { "NOT_IMPLEMENTED_ERR", "Inte implementerad: ''{0}''." }, { "NOT_STYLESHEET_ERR", "Indatadokumentet innehåller ingen XSL-formatmall." }, { "ELEMENT_PARSE_ERR", "Kunde inte tolka elementet ''{0}''" }, { "KEY_USE_ATTR_ERR", "use-attribut för <key> måste vara node, node-set, string eller number." }, { "OUTPUT_VERSION_ERR", "XML-dokumentets utdataversion måste vara 1.0" }, { "ILLEGAL_RELAT_OP_ERR", "Okänd operator för relationsuttryck" }, { "ATTRIBSET_UNDEF_ERR", "Försöker använda en icke-befintlig attributuppsättning ''{0}''." }, { "ATTR_VAL_TEMPLATE_ERR", "Kan inte tolka attributvärdemallen ''{0}''." }, { "UNKNOWN_SIG_TYPE_ERR", "Okänd datatyp i signaturen för klassen ''{0}''." }, { "DATA_CONVERSION_ERR", "Kan inte konvertera datatyp ''{0}'' till ''{1}''." }, { "NO_TRANSLET_CLASS_ERR", "Templates innehåller inte någon giltig klassdefinition för translet." }, { "NO_MAIN_TRANSLET_ERR", "Templates innehåller inte någon klass med namnet {0}." }, { "TRANSLET_CLASS_ERR", "Kunde inte ladda translet-klassen ''{0}''." }, { "TRANSLET_OBJECT_ERR", "Translet-klassen har laddats, men kan inte skapa instans av translet." }, { "ERROR_LISTENER_NULL_ERR", "Försöker ställa in ErrorListener för ''{0}'' på null" }, { "JAXP_UNKNOWN_SOURCE_ERR", "Endast StreamSource, SAXSource och DOMSource stöds av XSLTC" }, { "JAXP_NO_SOURCE_ERR", "Source-objektet som överfördes till ''{0}'' saknar innehåll." }, { "JAXP_COMPILE_ERR", "Kunde inte kompilera formatmall" }, { "JAXP_INVALID_ATTR_ERR", "TransformerFactory känner inte igen attributet ''{0}''." }, { "JAXP_INVALID_ATTR_VALUE_ERR", "Fel värde har angetts för attributet ''{0}''." }, { "JAXP_SET_RESULT_ERR", "setResult() måste anropas före startDocument()." }, { "JAXP_NO_TRANSLET_ERR", "Transformer saknar inkapslat objekt för translet." }, { "JAXP_NO_HANDLER_ERR", "Det finns ingen definierad utdatahanterare för transformeringsresultat." }, { "JAXP_NO_RESULT_ERR", "Result-objekt som överfördes till ''{0}'' är ogiltigt." }, { "JAXP_UNKNOWN_PROP_ERR", "Försöker få åtkomst till ogiltig Transformer-egenskap, ''{0}''." }, { "SAX2DOM_ADAPTER_ERR", "Kunde inte skapa SAX2DOM-adapter: ''{0}''." }, { "XSLTC_SOURCE_ERR", "XSLTCSource.build() anropades utan angivet systemId." }, { "ER_RESULT_NULL", "Result borde inte vara null" }, { "JAXP_INVALID_SET_PARAM_VALUE", "Parametervärdet för {0} måste vara giltigt Java-objekt" }, { "COMPILE_STDIN_ERR", "Alternativet -i måste användas med alternativet -o." }, { "COMPILE_USAGE_STR", "SYNOPSIS\n   java com.sun.org.apache.xalan.internal.xsltc.cmdline.Compile [-o <utdata>]\n      [-d <katalog>] [-j <jarfile>] [-p <paket>]\n      [-n] [-x] [-u] [-v] [-h] { <formatmall> | -i }\n\nALTERNATIV\n   -o <utdata>    tilldelar namnet <utdata> till genererad\n                  translet. Som standard tas namnet på translet\n                  från namnet på <formatmallen>. Alternativet\n                  ignoreras vid kompilering av flera formatmallar.\n   -d <katalog> anger en destinationskatalog för translet\n   -j <jarfile>   paketerar transletklasserna i en jar-fil med\n                  namnet <jarfile>\n   -p <paket>   anger ett paketnamnprefix för alla genererade\n                  transletklasser.\n   -n             aktiverar mallinfogning (ger ett bättre genomsnittligt\n                  standardbeteende).\n   -x             ger ytterligare felsökningsmeddelanden\n   -u             tolkar argument i <formatmall> som URL:er\n   -i             tvingar kompilatorn att läsa formatmallen från stdin\n   -v             skriver ut kompilatorns versionsnummer\n   -h             skriver ut denna syntaxsats\n" }, { "TRANSFORM_USAGE_STR", "SYNOPSIS \n   java com.sun.org.apache.xalan.internal.xsltc.cmdline.Transform [-j <jarfile>]\n      [-x] [-n <iterationer>] {-u <dokument_url> | <dokument>}\n      <klass> [<param1>=<värde1> ...]\n\n   använder translet <klass> vid transformering av XML-dokument \n   angivna som <dokument>. Translet-<klass> finns antingen i\n   användarens CLASSPATH eller i valfritt angiven <jarfile>.\nALTERNATIV\n   -j <jarfile>    anger en jar-fil varifrån translet laddas\n   -x              ger ytterligare felsökningsmeddelanden\n   -n <iterationer> kör <iterations>-tider vid transformering och\n                   visar profileringsinformation\n   -u <dokument_url> anger XML-indatadokument som URL\n" }, { "STRAY_SORT_ERR", "<xsl:sort> kan användas endast i <xsl:for-each> eller <xsl:apply-templates>." }, { "UNSUPPORTED_ENCODING", "Utdatakodning ''{0}'' understöds inte i JVM." }, { "SYNTAX_ERR", "Syntaxfel i ''{0}''." }, { "CONSTRUCTOR_NOT_FOUND", "Hittar inte den externa konstruktorn ''{0}''." }, { "NO_JAVA_FUNCT_THIS_REF", "Det första argumentet för den icke-statiska Java-funktionen ''{0}'' är inte någon giltig objektreferens." }, { "TYPE_CHECK_ERR", "Fel vid kontroll av typ av uttrycket ''{0}''." }, { "TYPE_CHECK_UNK_LOC_ERR", "Fel vid kontroll av typ av ett uttryck på okänd plats." }, { "ILLEGAL_CMDLINE_OPTION_ERR", "Ogiltigt kommandoradsalternativ: ''{0}''." }, { "CMDLINE_OPT_MISSING_ARG_ERR", "Kommandoradsalternativet ''{0}'' saknar obligatoriskt argument." }, { "WARNING_PLUS_WRAPPED_MSG", "WARNING:  ''{0}''\n       :{1}" }, { "WARNING_MSG", "WARNING:  ''{0}''" }, { "FATAL_ERR_PLUS_WRAPPED_MSG", "FATAL ERROR:  ''{0}''\n           :{1}" }, { "FATAL_ERR_MSG", "FATAL ERROR:  ''{0}''" }, { "ERROR_PLUS_WRAPPED_MSG", "ERROR:  ''{0}''\n     :{1}" }, { "ERROR_MSG", "ERROR:  ''{0}''" }, { "TRANSFORM_WITH_TRANSLET_STR", "Transformering via translet ''{0}'' " }, { "TRANSFORM_WITH_JAR_STR", "Transformering via translet ''{0}'' från jar-filen ''{1}''" }, { "COULD_NOT_CREATE_TRANS_FACT", "Kunde inte skapa en instans av TransformerFactory-klassen ''{0}''." }, { "TRANSLET_NAME_JAVA_CONFLICT", "''{0}'' kunde inte användas som namn på transletklassen eftersom det innehåller otillåtna tecken för Java-klassnamn. Namnet ''{1}'' användes istället." }, { "COMPILER_ERROR_KEY", "Kompileringsfel:" }, { "COMPILER_WARNING_KEY", "Kompileringsvarningar:" }, { "RUNTIME_ERROR_KEY", "Transletfel:" }, { "INVALID_QNAME_ERR", "Ett attribut vars värde måste vara ett QName eller en blankteckenavgränsad lista med QNames hade värdet ''{0}''" }, { "INVALID_NCNAME_ERR", "Ett attribut vars värde måste vara ett NCName hade värdet ''{0}''" }, { "INVALID_METHOD_IN_OUTPUT", "Metodattributet för ett <xsl:output>-element hade värdet ''{0}''. Endast något av följande värden kan användas: ''xml'', ''html'', ''text'' eller qname-but-not-ncname i XML" }, { "JAXP_GET_FEATURE_NULL_NAME", "Funktionsnamnet kan inte vara null i TransformerFactory.getFeature(namn på sträng)." }, { "JAXP_SET_FEATURE_NULL_NAME", "Funktionsnamnet kan inte vara null i TransformerFactory.setFeature(namn på sträng, booleskt värde)." }, { "JAXP_UNSUPPORTED_FEATURE", "Kan inte ställa in funktionen ''{0}'' i denna TransformerFactory." }, { "JAXP_SECUREPROCESSING_FEATURE", "FEATURE_SECURE_PROCESSING: Funktionen kan inte anges till false om säkerhetshanteraren används." }, { "OUTLINE_ERR_TRY_CATCH", "Internt XSLTC-fel: den genererade bytekoden innehåller ett try-catch-finally-block och kan inte göras till en disposition." }, { "OUTLINE_ERR_UNBALANCED_MARKERS", "Internt XSLTC-fel: markörerna OutlineableChunkStart och OutlineableChunkEnd måste vara balanserade och korrekt kapslade." }, { "OUTLINE_ERR_DELETED_TARGET", "Internt XSLTC-fel: originalmetoden refererar fortfarande till en instruktion som var en del av ett bytekodsblock som gjordes till en disposition." }, { "OUTLINE_ERR_METHOD_TOO_BIG", "Internt XSLTC-fel: en metod i transleten överstiger Java Virtual Machines längdbegränsning för en metod på 64 kilobytes.  Det här orsakas vanligen av mycket stora mallar i en formatmall. Försök att omstrukturera formatmallen att använda mindre mallar." }, { "DESERIALIZE_TEMPLATES_ERR", "När Java-säkerheten är aktiverad är stödet för avserialisering av TemplatesImpl avaktiverat. Du kan åsidosätta det här genom att ställa in systemegenskapen jdk.xml.enableTemplatesImplDeserialization till sant." } };
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\compile\\util\ErrorMessages_sv.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */