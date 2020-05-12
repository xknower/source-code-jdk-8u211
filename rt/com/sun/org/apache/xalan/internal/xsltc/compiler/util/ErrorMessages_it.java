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
/*    */ public class ErrorMessages_it
/*    */   extends ListResourceBundle
/*    */ {
/*    */   public Object[][] getContents() {
/* 96 */     return new Object[][] { { "MULTIPLE_STYLESHEET_ERR", "Sono stati definiti più fogli di stile nello stesso file." }, { "TEMPLATE_REDEF_ERR", "Il modello ''{0}'' è già stato definito in questo foglio di stile." }, { "TEMPLATE_UNDEF_ERR", "Il modello ''{0}'' non è stato definito in questo foglio di stile." }, { "VARIABLE_REDEF_ERR", "La variabile ''{0}'' è stata definita più volte nello stesso ambito." }, { "VARIABLE_UNDEF_ERR", "Variabile o parametro ''{0}'' non definito." }, { "CLASS_NOT_FOUND_ERR", "Impossibile trovare la classe \"{0}\"." }, { "METHOD_NOT_FOUND_ERR", "Impossibile trovare il metodo esterno ''{0}'' (deve essere pubblico)." }, { "ARGUMENT_CONVERSION_ERR", "Impossibile convertire l''argomento o il tipo restituito in una chiamata per il metodo ''{0}''" }, { "FILE_NOT_FOUND_ERR", "File o URI ''{0}'' non trovato." }, { "INVALID_URI_ERR", "URI ''{0}'' non valido." }, { "FILE_ACCESS_ERR", "Impossibile aprire il file o l''URI ''{0}''." }, { "MISSING_ROOT_ERR", "È previsto un elemento <xsl:stylesheet> o <xsl:transform>." }, { "NAMESPACE_UNDEF_ERR", "Prefisso spazio di nomi ''{0}'' non dichiarato." }, { "FUNCTION_RESOLVE_ERR", "Impossibile risolvere la chiamata per la funzione ''{0}''." }, { "NEED_LITERAL_ERR", "L''argomento per ''{0}'' deve essere una stringa di valori." }, { "XPATH_PARSER_ERR", "Errore durante l''analisi dell''espressione XPath ''{0}''." }, { "REQUIRED_ATTR_ERR", "Attributo obbligatorio ''{0}'' mancante." }, { "ILLEGAL_CHAR_ERR", "Carattere ''{0}'' non valido nell''espressione XPath." }, { "ILLEGAL_PI_ERR", "Nome ''{0}'' non valido per l''istruzione di elaborazione." }, { "STRAY_ATTRIBUTE_ERR", "Attributo ''{0}'' al di fuori dell''elemento." }, { "ILLEGAL_ATTRIBUTE_ERR", "Attributo ''{0}'' non valido." }, { "CIRCULAR_INCLUDE_ERR", "Importazione/inclusione circolare. Il foglio di stile ''{0}'' è già stato caricato." }, { "RESULT_TREE_SORT_ERR", "Impossibile ordinare i frammenti della struttura di risultati (gli elementi <xsl:sort> verranno ignorati). È necessario ordinare i nodi quando si crea la struttura di risultati." }, { "SYMBOLS_REDEF_ERR", "Formattazione decimale ''{0}'' già definita." }, { "XSL_VERSION_ERR", "La versione XSL ''{0}'' non è supportata da XSLTC." }, { "CIRCULAR_VARIABLE_ERR", "Riferimento di variabile/parametro circolare in ''{0}''." }, { "ILLEGAL_BINARY_OP_ERR", "Operatore sconosciuto per l'espressione binaria." }, { "ILLEGAL_ARG_ERR", "Uno o più argomenti non validi per la chiamata della funzione." }, { "DOCUMENT_ARG_ERR", "Il secondo argomento per la funzione document() deve essere un set di nodi." }, { "MISSING_WHEN_ERR", "È richiesto almeno un elemento <xsl:when> in <xsl:choose>." }, { "MULTIPLE_OTHERWISE_ERR", "È consentito un solo elemento <xsl:otherwise> in <xsl:choose>." }, { "STRAY_OTHERWISE_ERR", "<xsl:otherwise> può essere utilizzato sono in <xsl:choose>." }, { "STRAY_WHEN_ERR", "<xsl:when> può essere utilizzato sono in <xsl:choose>." }, { "WHEN_ELEMENT_ERR", "Sono consentiti solo elementi <xsl:when> e <xsl:otherwise> in <xsl:choose>." }, { "UNNAMED_ATTRIBSET_ERR", "<xsl:attribute-set> mancante nell'attributo 'name'." }, { "ILLEGAL_CHILD_ERR", "Elemento figlio non valido." }, { "ILLEGAL_ELEM_NAME_ERR", "Impossibile richiamare un elemento ''{0}''" }, { "ILLEGAL_ATTR_NAME_ERR", "Impossibile richiamare un attributo ''{0}''" }, { "ILLEGAL_TEXT_NODE_ERR", "I dati di testo non rientrano nell'elemento <xsl:stylesheet> di livello superiore." }, { "SAX_PARSER_CONFIG_ERR", "Parser JAXP non configurato correttamente" }, { "INTERNAL_ERR", "Errore interno XSLTC irreversibile: ''{0}''" }, { "UNSUPPORTED_XSL_ERR", "Elemento XSL \"{0}\" non supportato." }, { "UNSUPPORTED_EXT_ERR", "Estensione XSLTC ''{0}'' non riconosciuta." }, { "MISSING_XSLT_URI_ERR", "Il documento di input non è un foglio di stile (spazio di nomi XSL non dichiarato nell'elemento radice)." }, { "MISSING_XSLT_TARGET_ERR", "Impossibile trovare la destinazione ''{0}'' del foglio di stile." }, { "ACCESSING_XSLT_TARGET_ERR", "Impossibile leggere la destinazione del foglio di stile ''{0}''. Accesso ''{1}'' non consentito a causa della limitazione definita dalla proprietà accessExternalStylesheet." }, { "NOT_IMPLEMENTED_ERR", "Non implementato: ''{0}''." }, { "NOT_STYLESHEET_ERR", "Il documento di input non contiene un foglio di stile XSL." }, { "ELEMENT_PARSE_ERR", "Impossibile analizzare l''elemento ''{0}''" }, { "KEY_USE_ATTR_ERR", "L'attributo di uso <key> deve essere un nodo, un set di nodi, una stringa o un numero." }, { "OUTPUT_VERSION_ERR", "La versione del documento XML di output deve essere 1.0" }, { "ILLEGAL_RELAT_OP_ERR", "Operatore sconosciuto per l'espressione relazionale" }, { "ATTRIBSET_UNDEF_ERR", "Tentativo di utilizzare un set di attributi ''{0}'' inesistente." }, { "ATTR_VAL_TEMPLATE_ERR", "Impossibile analizzare il modello di valore di attributo ''{0}''." }, { "UNKNOWN_SIG_TYPE_ERR", "Tipo di dati sconosciuto nella firma per la classe ''{0}''." }, { "DATA_CONVERSION_ERR", "Impossibile convertire il tipo di dati ''{0}'' in ''{1}''." }, { "NO_TRANSLET_CLASS_ERR", "Il modello non contiene una definizione di classe di translet valida." }, { "NO_MAIN_TRANSLET_ERR", "Il modello non contiene una classe denominata ''{0}''." }, { "TRANSLET_CLASS_ERR", "Impossibile caricare la classe di translet ''{0}''." }, { "TRANSLET_OBJECT_ERR", "La classe di translet è stata caricata, ma non è possibile creare l'istanza del translet." }, { "ERROR_LISTENER_NULL_ERR", "Tentativo di impostare ErrorListener per ''{0}'' su null" }, { "JAXP_UNKNOWN_SOURCE_ERR", "XSLTC supporta solo StreamSource, SAXSource e DOMSource." }, { "JAXP_NO_SOURCE_ERR", "L''oggetto di origine passato a ''{0}'' non ha contenuti." }, { "JAXP_COMPILE_ERR", "Impossibile compilare il foglio di stile" }, { "JAXP_INVALID_ATTR_ERR", "TransformerFactory non riconosce l''attributo ''{0}''." }, { "JAXP_INVALID_ATTR_VALUE_ERR", "Valore errato specificato per l''attributo ''{0}''." }, { "JAXP_SET_RESULT_ERR", "setResult() deve essere richiamato prima di startDocument()." }, { "JAXP_NO_TRANSLET_ERR", "Il trasformatore non contiene alcun oggetto incapsulato." }, { "JAXP_NO_HANDLER_ERR", "Nessun handler di output definito per il risultato della trasformazione." }, { "JAXP_NO_RESULT_ERR", "L''oggetto di risultato passato a ''{0}'' non è valido." }, { "JAXP_UNKNOWN_PROP_ERR", "Tentativo di accedere a una proprietà ''{0}'' del trasformatore non valida." }, { "SAX2DOM_ADAPTER_ERR", "Impossibile creare l''adattatore SAX2DOM ''{0}''." }, { "XSLTC_SOURCE_ERR", "XSLTCSource.build() richiamato senza che sia stato impostato systemId." }, { "ER_RESULT_NULL", "Il risultato non deve essere nullo" }, { "JAXP_INVALID_SET_PARAM_VALUE", "Il valore del parametro {0} deve essere un oggetto Java valido" }, { "COMPILE_STDIN_ERR", "L'opzione -i deve essere utilizzata con l'opzione -o." }, { "COMPILE_USAGE_STR", "RIEPILOGO\n   java com.sun.org.apache.xalan.internal.xsltc.cmdline.Compile [-o <output>]\n      [-d <directory>] [-j <file jar>] [-p <package>]\n      [-n] [-x] [-u] [-v] [-h] { <foglio di stile> | -i }\n\nOPZIONI\n   -o <output>    assegna l'<output> del nome al translet\n                  generato.  Per impostazione predefinita, il nome translet\n                  è derivato dal nome <foglio di stile>.  Questa opzione\n                  viene ignorata se si compilano più fogli di stile.\n   -d <directory> specifica una directory di destinazione per il translet\n   -j <file jar>   crea un package di classi di translet inserendolo in un file JAR con il\n                  nome specificato come <jarfile>\n   -p <package>   specifica un prefisso di nome package per tutte le\n                  classi di translet generate.\n   -n             abilita l'inserimento in linea dei modelli (in media, l'impostazione predefinita è\n                  la migliore).\n   -x             attiva l'output di altri messaggi di debug\n   -u             interpreta gli argomenti <stylesheet> come URL\n   -i             obbliga il compilatore a leggere il foglio di stile da stdin\n   -v             visualizza la versione del compilatore\n   -h             visualizza questa istruzione di uso\n" }, { "TRANSFORM_USAGE_STR", "RIPEILOGO\n   java com.sun.org.apache.xalan.internal.xsltc.cmdline.Transform [-j <file jar>]\n      [-x] [-n <iterazioni>] {-u <url_documento> | <documento>}\n      <classe> [<param1>=<valore1> ...]\n\n   utilizza la <classe> translet per trasformare un documento XML\n   specificato come <documento>. La <classe> di translet si trova nel\n   CLASSPATH dell'utente o nel <file jar> specificato facoltativamente.\\OPZIONI\n   -j <file jar>    specifica un file JAR dal quale caricare il translet\n   -x              attiva l'output di altri messaggi di debug\n   -n <iterazioni> esegue le <iterazioni> di trasformazione e\n                   visualizza le informazioni sui profili\n   -u <url_documento> specifica il documento di input XML come URL\n" }, { "STRAY_SORT_ERR", "<xsl:sort> può essere utilizzato sono in <xsl:for-each> o <xsl:apply-templates>." }, { "UNSUPPORTED_ENCODING", "La codifica di output ''{0}'' non è supportata in questa JVM." }, { "SYNTAX_ERR", "Errore di sintassi in ''{0}''." }, { "CONSTRUCTOR_NOT_FOUND", "Impossibile trovare il costruttore esterno ''{0}''." }, { "NO_JAVA_FUNCT_THIS_REF", "Il primo argomento per la funzione Java non statica ''{0}'' non è un riferimento di oggetto valido." }, { "TYPE_CHECK_ERR", "Errore durante il controllo del tipo dell''espressione ''{0}''." }, { "TYPE_CHECK_UNK_LOC_ERR", "Errore durante il controllo del tipo di un''espressione in una posizione sconosciuta." }, { "ILLEGAL_CMDLINE_OPTION_ERR", "L''opzione di riga di comando ''{0}'' non è valida." }, { "CMDLINE_OPT_MISSING_ARG_ERR", "Nell''opzione di riga di comando ''{0}'' manca un argomento obbligatorio." }, { "WARNING_PLUS_WRAPPED_MSG", "WARNING:  ''{0}''\n       :{1}" }, { "WARNING_MSG", "WARNING:  ''{0}''" }, { "FATAL_ERR_PLUS_WRAPPED_MSG", "FATAL ERROR:  ''{0}''\n           :{1}" }, { "FATAL_ERR_MSG", "FATAL ERROR:  ''{0}''" }, { "ERROR_PLUS_WRAPPED_MSG", "ERROR:  ''{0}''\n     :{1}" }, { "ERROR_MSG", "ERROR:  ''{0}''" }, { "TRANSFORM_WITH_TRANSLET_STR", "Trasformazione mediante il translet ''{0}'' " }, { "TRANSFORM_WITH_JAR_STR", "Trasformazione mediante il translet ''{0}'' del file jar ''{1}''" }, { "COULD_NOT_CREATE_TRANS_FACT", "Impossibile creare un''istanza della classe TransformerFactory ''{0}''." }, { "TRANSLET_NAME_JAVA_CONFLICT", "Impossibile utilizzare il nome ''{0}'' per la classe di translet poiché contiene caratteri non consentiti nel nome della classe Java. Verrà utilizzato il nome ''{1}''." }, { "COMPILER_ERROR_KEY", "Errori del compilatore:" }, { "COMPILER_WARNING_KEY", "Avvertenze del compilatore:" }, { "RUNTIME_ERROR_KEY", "Errori del translet:" }, { "INVALID_QNAME_ERR", "Un attributo il cui valore deve essere un QName o una lista separata da spazi di QName contiene il valore ''{0}''" }, { "INVALID_NCNAME_ERR", "Un attributo il cui valore deve essere un NCName contiene il valore ''{0}''" }, { "INVALID_METHOD_IN_OUTPUT", "L''attributo di metodo per un elemento <xsl:output> ha il valore ''{0}'', ma deve essere uno tra ''xml'', ''html'', ''text'' o qname-but-not-ncname" }, { "JAXP_GET_FEATURE_NULL_NAME", "Il nome funzione non può essere nullo in TransformerFactory.getFeature (nome stringa)." }, { "JAXP_SET_FEATURE_NULL_NAME", "Il nome funzione non può essere nullo in TransformerFactory.setFeature (nome stringa, valore booleano)." }, { "JAXP_UNSUPPORTED_FEATURE", "Impossibile impostare la funzione ''{0}'' in questo TransformerFactory." }, { "JAXP_SECUREPROCESSING_FEATURE", "FEATURE_SECURE_PROCESSING: impossibile impostare la funzione su false se è presente Security Manager." }, { "OUTLINE_ERR_TRY_CATCH", "Errore XSLTC interno: il bytecode generato contiene un blocco try-catch-finally e non può essere di tipo outlined." }, { "OUTLINE_ERR_UNBALANCED_MARKERS", "Errore XSLTC interno: gli indicatori OutlineableChunkStart e OutlineableChunkEnd devono essere bilanciati e nidificati correttamente." }, { "OUTLINE_ERR_DELETED_TARGET", "Errore XSLTC interno: a un'istruzione che faceva parte di un blocco di bytecode di tipo outlined viene ancora fatto riferimento nel metodo originale." }, { "OUTLINE_ERR_METHOD_TOO_BIG", "Errore XSLTC interno: un metodo nel translet supera la limitazione Java Virtual Machine relativa alla lunghezza per un metodo di 64 kilobyte. Ciò è generalmente causato dalle grandi dimensioni dei modelli in un foglio di stile. Provare a ristrutturare il foglio di stile per utilizzare modelli di dimensioni inferiori." }, { "DESERIALIZE_TEMPLATES_ERR", "Quando la sicurezza Java è abilitata, il supporto per la deserializzazione TemplatesImpl è disabilitato. È possibile ignorare questa condizione impostando su true la proprietà di sistema jdk.xml.enableTemplatesImplDeserialization." } };
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\compile\\util\ErrorMessages_it.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */