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
/*    */ public class ErrorMessages_fr
/*    */   extends ListResourceBundle
/*    */ {
/*    */   public Object[][] getContents() {
/* 96 */     return new Object[][] { { "MULTIPLE_STYLESHEET_ERR", "Plusieurs feuilles de style définies dans le même fichier." }, { "TEMPLATE_REDEF_ERR", "Modèle ''{0}'' déjà défini dans cette feuille de style." }, { "TEMPLATE_UNDEF_ERR", "Modèle ''{0}'' non défini dans cette feuille de style." }, { "VARIABLE_REDEF_ERR", "Plusieurs variables ''{0}'' définies dans la même portée." }, { "VARIABLE_UNDEF_ERR", "La variable ou le paramètre ''{0}'' n''est pas défini." }, { "CLASS_NOT_FOUND_ERR", "Impossible de trouver la classe ''{0}''." }, { "METHOD_NOT_FOUND_ERR", "Méthode externe ''{0}'' introuvable (elle doit être \"public\")." }, { "ARGUMENT_CONVERSION_ERR", "Impossible de convertir le type de retour/d''argument dans l''appel de la méthode ''{0}''" }, { "FILE_NOT_FOUND_ERR", "Fichier ou URI ''{0}'' introuvable." }, { "INVALID_URI_ERR", "URI ''{0}'' non valide." }, { "FILE_ACCESS_ERR", "Impossible d''ouvrir le fichier ou l''URI ''{0}''." }, { "MISSING_ROOT_ERR", "Elément <xsl:stylesheet> ou <xsl:transform> attendu." }, { "NAMESPACE_UNDEF_ERR", "Le préfixe de l''espace de noms ''{0}'' n''a pas été déclaré." }, { "FUNCTION_RESOLVE_ERR", "Impossible de résoudre l''appel de la fonction ''{0}''." }, { "NEED_LITERAL_ERR", "L''argument pour ''{0}'' doit être une chaîne littérale." }, { "XPATH_PARSER_ERR", "Erreur lors de l''analyse de l''expression XPath ''{0}''." }, { "REQUIRED_ATTR_ERR", "Attribut ''{0}'' obligatoire manquant." }, { "ILLEGAL_CHAR_ERR", "Caractère ''{0}'' non admis dans l''expression XPath." }, { "ILLEGAL_PI_ERR", "Nom ''{0}'' non admis pour l''instruction de traitement." }, { "STRAY_ATTRIBUTE_ERR", "Attribut ''{0}'' à l''extérieur de l''élément." }, { "ILLEGAL_ATTRIBUTE_ERR", "Attribut ''{0}'' non admis." }, { "CIRCULAR_INCLUDE_ERR", "Opération import/include circulaire. La feuille de style ''{0}'' est déjà chargée." }, { "RESULT_TREE_SORT_ERR", "Les fragments de l'arborescence de résultats ne peuvent pas être triés (les éléments <xsl:sort> ne sont pas pris en compte). Vous devez trier les noeuds lorsque vous créez l'arborescence de résultats." }, { "SYMBOLS_REDEF_ERR", "Le formatage décimal ''{0}'' est déjà défini." }, { "XSL_VERSION_ERR", "La version XSL ''{0}'' n''est pas prise en charge par XSLTC." }, { "CIRCULAR_VARIABLE_ERR", "Référence de paramètre/variable circulaire dans ''{0}''." }, { "ILLEGAL_BINARY_OP_ERR", "Opérateur inconnu pour l'expression binaire." }, { "ILLEGAL_ARG_ERR", "Arguments non admis pour l'appel de la fonction." }, { "DOCUMENT_ARG_ERR", "Le deuxième argument de la fonction document() doit être un jeu de noeuds." }, { "MISSING_WHEN_ERR", "Au moins un élément <xsl:when> est obligatoire dans <xsl:choose>." }, { "MULTIPLE_OTHERWISE_ERR", "Un seul élément <xsl:otherwise> est autorisé dans <xsl:choose>." }, { "STRAY_OTHERWISE_ERR", "<xsl:otherwise> ne peut être utilisé que dans <xsl:choose>." }, { "STRAY_WHEN_ERR", "<xsl:when> ne peut être utilisé que dans <xsl:choose>." }, { "WHEN_ELEMENT_ERR", "Seuls les éléments <xsl:when> et <xsl:otherwise> sont autorisés dans <xsl:choose>." }, { "UNNAMED_ATTRIBSET_ERR", "Attribut \"name\" manquant dans <xsl:attribute-set>." }, { "ILLEGAL_CHILD_ERR", "Elément enfant non admis." }, { "ILLEGAL_ELEM_NAME_ERR", "Vous ne pouvez pas appeler un élément ''{0}''" }, { "ILLEGAL_ATTR_NAME_ERR", "Vous ne pouvez pas appeler un attribut ''{0}''" }, { "ILLEGAL_TEXT_NODE_ERR", "Données texte en dehors de l'élément <xsl:stylesheet> de niveau supérieur." }, { "SAX_PARSER_CONFIG_ERR", "L'analyseur JAXP n'est pas configuré correctement" }, { "INTERNAL_ERR", "Erreur interne XSLTC irrécupérable : ''{0}''" }, { "UNSUPPORTED_XSL_ERR", "Elément ''{0}'' XSL non pris en charge." }, { "UNSUPPORTED_EXT_ERR", "Extension ''{0}'' XSLTC non reconnue." }, { "MISSING_XSLT_URI_ERR", "Le document d'entrée n'est pas une feuille de style (l'espace de noms XSL n'est pas déclaré dans l'élément racine)." }, { "MISSING_XSLT_TARGET_ERR", "Cible de feuille de style ''{0}'' introuvable." }, { "ACCESSING_XSLT_TARGET_ERR", "Impossible de lire la cible de feuille de style ''{0}'' car l''accès à ''{1}'' n''est pas autorisé en raison d''une restriction définie par la propriété accessExternalStylesheet." }, { "NOT_IMPLEMENTED_ERR", "Non implémenté : ''{0}''." }, { "NOT_STYLESHEET_ERR", "Le document d'entrée ne contient pas de feuille de style XSL." }, { "ELEMENT_PARSE_ERR", "Impossible d''analyser l''élément ''{0}''" }, { "KEY_USE_ATTR_ERR", "L'attribut \"use\" de <key> doit être node, node-set, string ou number." }, { "OUTPUT_VERSION_ERR", "La version du document XML de sortie doit être 1.0" }, { "ILLEGAL_RELAT_OP_ERR", "Opérateur inconnu pour l'expression relationnelle" }, { "ATTRIBSET_UNDEF_ERR", "Tentative d''utilisation de l''ensemble d''attributs non existant ''{0}''." }, { "ATTR_VAL_TEMPLATE_ERR", "Impossible d''analyser le modèle de valeur d''attribut ''{0}''." }, { "UNKNOWN_SIG_TYPE_ERR", "Type de données inconnu dans la signature pour la classe ''{0}''." }, { "DATA_CONVERSION_ERR", "Impossible de convertir le type de données ''{0}'' en ''{1}''." }, { "NO_TRANSLET_CLASS_ERR", "Cette classe Templates ne contient pas de définition de classe de translet valide." }, { "NO_MAIN_TRANSLET_ERR", "Cette classe Termplates ne contient pas de classe portant le nom ''{0}''." }, { "TRANSLET_CLASS_ERR", "Impossible de charger la classe de translet ''{0}''." }, { "TRANSLET_OBJECT_ERR", "Classe de translet chargée, mais impossible de créer une instance de translet." }, { "ERROR_LISTENER_NULL_ERR", "Tentative de définition d''ErrorListener sur NULL pour ''{0}''" }, { "JAXP_UNKNOWN_SOURCE_ERR", "Seuls StreamSource, SAXSource et DOMSource sont pris en charge par XSLTC" }, { "JAXP_NO_SOURCE_ERR", "L''objet Source transmis à ''{0}'' n''a pas de contenu." }, { "JAXP_COMPILE_ERR", "Impossible de compiler la feuille de style" }, { "JAXP_INVALID_ATTR_ERR", "TransformerFactory ne reconnaît pas l''attribut ''{0}''." }, { "JAXP_INVALID_ATTR_VALUE_ERR", "La valeur indiquée pour l''attribut ''{0}'' est incorrecte." }, { "JAXP_SET_RESULT_ERR", "setResult() doit être appelé avant startDocument()." }, { "JAXP_NO_TRANSLET_ERR", "La classe Transformer ne contient pas d'objet translet encapsulé." }, { "JAXP_NO_HANDLER_ERR", "Aucun gestionnaire de sortie défini pour le résultat de la transformation." }, { "JAXP_NO_RESULT_ERR", "L''objet de résultat transmis à ''{0}'' n''est pas valide." }, { "JAXP_UNKNOWN_PROP_ERR", "Tentative d''accès à la propriété Transformer non valide ''{0}''." }, { "SAX2DOM_ADAPTER_ERR", "Impossible de créer l''adaptateur SAX2DOM : ''{0}''." }, { "XSLTC_SOURCE_ERR", "XSLTCSource.build() appelé sans que l'ID système soit défini." }, { "ER_RESULT_NULL", "Le résultat ne doit pas être NULL" }, { "JAXP_INVALID_SET_PARAM_VALUE", "La valeur du paramètre {0} doit être un objet Java valide" }, { "COMPILE_STDIN_ERR", "L'option -i doit être utilisée avec l'option -o." }, { "COMPILE_USAGE_STR", "SYNTAXE\n   java com.sun.org.apache.xalan.internal.xsltc.cmdline.Compile [-o <output>]\n      [-d <directory>] [-j <jarfile>] [-p <package>]\n      [-n] [-x] [-u] [-v] [-h] { <stylesheet> | -i }\n\nOPTIONS\n   -o <output>    attribue le nom <output> au\n                  translet généré. Par défaut, le nom du translet est\n                  dérivé du nom <stylesheet>. Cette option\n                  n'est pas prise en compte lors de la compilation de plusieurs feuilles de style.\n   -d <directory> indique un répertoire de destination pour le translet\n   -j <jarfile>   package les classes de translet dans un fichier JAR portant le\n                  nom spécifié comme <jarfile>\n   -p <package>   indique un préfixe de nom de package pour toutes les\n                  classes de translet générées.\n   -n             active le mode INLINE du modèle (comportement par défaut amélioré\n                  en moyenne).\n   -x             active la sortie de messages de débogage supplémentaires\n   -u             interprète les arguments <stylesheet> comme des URL\n   -i             force le compilateur à lire la feuille de style à partir de STDIN\n   -v             affiche la version du compilateur\n   -h             affiche cette instruction de syntaxe\n" }, { "TRANSFORM_USAGE_STR", "SYNTAXE \n   java com.sun.org.apache.xalan.internal.xsltc.cmdline.Transform [-j <jarfile>]\n      [-x] [-n <iterations>] {-u <document_url> | <document>}\n      <class> [<param1>=<value1> ...]\n\n   utilise le translet <class> pour transformer un document XML\n   spécifié comme <document>. Le translet <class> est soit dans\n   la variable d'environnement CLASSPATH de l'utilisateur, soit dans un fichier <jarfile> indiqué en option.\nOPTIONS\n   -j <jarfile>    indique un fichier JAR à partir duquel charger le translet\n   -x              active la sortie de messages de débogage supplémentaires\n   -n <iterations> exécute la transformation <iterations> fois et\n                   affiche les informations de profilage\n   -u <document_url> spécifie le document d'entrée XML comme URL\n" }, { "STRAY_SORT_ERR", "<xsl:sort> peut uniquement être utilisé dans <xsl:for-each> ou <xsl:apply-templates>." }, { "UNSUPPORTED_ENCODING", "L''encodage de sortie ''{0}'' n''est pas pris en charge sur cette Java Virtual Machine (JVM)." }, { "SYNTAX_ERR", "Erreur de syntaxe dans ''{0}''." }, { "CONSTRUCTOR_NOT_FOUND", "Constructeur ''{0}'' externe introuvable." }, { "NO_JAVA_FUNCT_THIS_REF", "Le premier argument pour la fonction Java ''{0}'' non static n''est pas une référence d''objet valide." }, { "TYPE_CHECK_ERR", "Erreur lors de la vérification du type de l''expression ''{0}''." }, { "TYPE_CHECK_UNK_LOC_ERR", "Erreur lors de la vérification du type d'expression à un emplacement inconnu." }, { "ILLEGAL_CMDLINE_OPTION_ERR", "L''option de ligne de commande ''{0}'' n''est pas valide." }, { "CMDLINE_OPT_MISSING_ARG_ERR", "Argument obligatoire manquant dans l''option de ligne de commande ''{0}''." }, { "WARNING_PLUS_WRAPPED_MSG", "WARNING:  ''{0}''\n       :{1}" }, { "WARNING_MSG", "WARNING:  ''{0}''" }, { "FATAL_ERR_PLUS_WRAPPED_MSG", "FATAL ERROR:  ''{0}''\n           :{1}" }, { "FATAL_ERR_MSG", "FATAL ERROR:  ''{0}''" }, { "ERROR_PLUS_WRAPPED_MSG", "ERROR:  ''{0}''\n     :{1}" }, { "ERROR_MSG", "ERROR:  ''{0}''" }, { "TRANSFORM_WITH_TRANSLET_STR", "Transformation à l''aide du translet ''{0}'' " }, { "TRANSFORM_WITH_JAR_STR", "Transformation à l''aide du translet ''{0}'' dans le fichier JAR ''{1}''" }, { "COULD_NOT_CREATE_TRANS_FACT", "Impossible de créer une instance de la classe TransformerFactory ''{0}''." }, { "TRANSLET_NAME_JAVA_CONFLICT", "Impossible d''utiliser le nom ''{0}'' comme nom de classe de translet car il contient des caractères non autorisés dans le nom de la classe Java. Le nom ''{1}'' a été utilisé à la place." }, { "COMPILER_ERROR_KEY", "Erreurs de compilateur :" }, { "COMPILER_WARNING_KEY", "Avertissements de compilateur :" }, { "RUNTIME_ERROR_KEY", "Erreurs de translet :" }, { "INVALID_QNAME_ERR", "Un attribut dont la valeur doit être un QName ou une liste de QNames séparés par des espaces avait la valeur ''{0}''" }, { "INVALID_NCNAME_ERR", "Un attribut dont la valeur doit être un NCName avait la valeur ''{0}''" }, { "INVALID_METHOD_IN_OUTPUT", "L''attribut \"method\" d''un élément <xsl:output> avait la valeur ''{0}''. La valeur doit être l''une des suivantes : ''xml'', ''html'', ''text'' ou qname-but-not-ncname" }, { "JAXP_GET_FEATURE_NULL_NAME", "Le nom de la fonctionnalité ne peut pas être NULL dans TransformerFactory.getFeature (chaîne pour le nom)." }, { "JAXP_SET_FEATURE_NULL_NAME", "Le nom de la fonctionnalité ne peut pas être NULL dans TransformerFactory.setFeature (chaîne pour le nom, valeur booléenne)." }, { "JAXP_UNSUPPORTED_FEATURE", "Impossible de définir la fonctionnalité ''{0}'' sur cette propriété TransformerFactory." }, { "JAXP_SECUREPROCESSING_FEATURE", "FEATURE_SECURE_PROCESSING : impossible de définir la fonctionnalité sur False en présence du gestionnaire de sécurité." }, { "OUTLINE_ERR_TRY_CATCH", "Erreur XSLTC interne : le code exécutable généré contient un bloc try-catch-finally et ne peut pas être délimité." }, { "OUTLINE_ERR_UNBALANCED_MARKERS", "Erreur XSLTC interne : les marqueurs OutlineableChunkStart et OutlineableChunkEnd doivent être équilibrés et correctement imbriqués." }, { "OUTLINE_ERR_DELETED_TARGET", "Erreur XSLTC interne : une instruction ayant fait partie d'un bloc de code exécutable délimité est toujours référencée dans la méthode d'origine." }, { "OUTLINE_ERR_METHOD_TOO_BIG", "Erreur XSLTC interne : une méthode dans le translet dépasse la limite de la JVM concernant la longueur d'une méthode de 64 kilo-octets. En général, ceci est dû à de très grands modèles dans une feuille de style. Essayez de restructurer la feuille de style pour utiliser des modèles plus petits." }, { "DESERIALIZE_TEMPLATES_ERR", "Lorsque la sécurité Java est activée, la prise en charge de la désérialisation de TemplatesImpl est désactivée. La définition de la propriété système jdk.xml.enableTemplatesImplDeserialization sur True permet de remplacer ce paramètre." } };
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\compile\\util\ErrorMessages_fr.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */