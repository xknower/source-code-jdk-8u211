/*     */ package com.sun.org.apache.xerces.internal.impl.xs;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SchemaSymbols
/*     */ {
/*  41 */   public static final String URI_XSI = "http://www.w3.org/2001/XMLSchema-instance".intern();
/*  42 */   public static final String XSI_SCHEMALOCATION = "schemaLocation".intern();
/*  43 */   public static final String XSI_NONAMESPACESCHEMALOCATION = "noNamespaceSchemaLocation".intern();
/*  44 */   public static final String XSI_TYPE = "type".intern();
/*  45 */   public static final String XSI_NIL = "nil".intern();
/*     */ 
/*     */   
/*  48 */   public static final String URI_SCHEMAFORSCHEMA = "http://www.w3.org/2001/XMLSchema".intern();
/*     */ 
/*     */   
/*  51 */   public static final String ELT_ALL = "all".intern();
/*  52 */   public static final String ELT_ANNOTATION = "annotation".intern();
/*  53 */   public static final String ELT_ANY = "any".intern();
/*  54 */   public static final String ELT_ANYATTRIBUTE = "anyAttribute".intern();
/*  55 */   public static final String ELT_APPINFO = "appinfo".intern();
/*  56 */   public static final String ELT_ATTRIBUTE = "attribute".intern();
/*  57 */   public static final String ELT_ATTRIBUTEGROUP = "attributeGroup".intern();
/*  58 */   public static final String ELT_CHOICE = "choice".intern();
/*  59 */   public static final String ELT_COMPLEXCONTENT = "complexContent".intern();
/*  60 */   public static final String ELT_COMPLEXTYPE = "complexType".intern();
/*  61 */   public static final String ELT_DOCUMENTATION = "documentation".intern();
/*  62 */   public static final String ELT_ELEMENT = "element".intern();
/*  63 */   public static final String ELT_ENUMERATION = "enumeration".intern();
/*  64 */   public static final String ELT_EXTENSION = "extension".intern();
/*  65 */   public static final String ELT_FIELD = "field".intern();
/*  66 */   public static final String ELT_FRACTIONDIGITS = "fractionDigits".intern();
/*  67 */   public static final String ELT_GROUP = "group".intern();
/*  68 */   public static final String ELT_IMPORT = "import".intern();
/*  69 */   public static final String ELT_INCLUDE = "include".intern();
/*  70 */   public static final String ELT_KEY = "key".intern();
/*  71 */   public static final String ELT_KEYREF = "keyref".intern();
/*  72 */   public static final String ELT_LENGTH = "length".intern();
/*  73 */   public static final String ELT_LIST = "list".intern();
/*  74 */   public static final String ELT_MAXEXCLUSIVE = "maxExclusive".intern();
/*  75 */   public static final String ELT_MAXINCLUSIVE = "maxInclusive".intern();
/*  76 */   public static final String ELT_MAXLENGTH = "maxLength".intern();
/*  77 */   public static final String ELT_MINEXCLUSIVE = "minExclusive".intern();
/*  78 */   public static final String ELT_MININCLUSIVE = "minInclusive".intern();
/*  79 */   public static final String ELT_MINLENGTH = "minLength".intern();
/*  80 */   public static final String ELT_NOTATION = "notation".intern();
/*  81 */   public static final String ELT_PATTERN = "pattern".intern();
/*  82 */   public static final String ELT_REDEFINE = "redefine".intern();
/*  83 */   public static final String ELT_RESTRICTION = "restriction".intern();
/*  84 */   public static final String ELT_SCHEMA = "schema".intern();
/*  85 */   public static final String ELT_SELECTOR = "selector".intern();
/*  86 */   public static final String ELT_SEQUENCE = "sequence".intern();
/*  87 */   public static final String ELT_SIMPLECONTENT = "simpleContent".intern();
/*  88 */   public static final String ELT_SIMPLETYPE = "simpleType".intern();
/*  89 */   public static final String ELT_TOTALDIGITS = "totalDigits".intern();
/*  90 */   public static final String ELT_UNION = "union".intern();
/*  91 */   public static final String ELT_UNIQUE = "unique".intern();
/*  92 */   public static final String ELT_WHITESPACE = "whiteSpace".intern();
/*     */ 
/*     */   
/*  95 */   public static final String ATT_ABSTRACT = "abstract".intern();
/*  96 */   public static final String ATT_ATTRIBUTEFORMDEFAULT = "attributeFormDefault".intern();
/*  97 */   public static final String ATT_BASE = "base".intern();
/*  98 */   public static final String ATT_BLOCK = "block".intern();
/*  99 */   public static final String ATT_BLOCKDEFAULT = "blockDefault".intern();
/* 100 */   public static final String ATT_DEFAULT = "default".intern();
/* 101 */   public static final String ATT_ELEMENTFORMDEFAULT = "elementFormDefault".intern();
/* 102 */   public static final String ATT_FINAL = "final".intern();
/* 103 */   public static final String ATT_FINALDEFAULT = "finalDefault".intern();
/* 104 */   public static final String ATT_FIXED = "fixed".intern();
/* 105 */   public static final String ATT_FORM = "form".intern();
/* 106 */   public static final String ATT_ID = "id".intern();
/* 107 */   public static final String ATT_ITEMTYPE = "itemType".intern();
/* 108 */   public static final String ATT_MAXOCCURS = "maxOccurs".intern();
/* 109 */   public static final String ATT_MEMBERTYPES = "memberTypes".intern();
/* 110 */   public static final String ATT_MINOCCURS = "minOccurs".intern();
/* 111 */   public static final String ATT_MIXED = "mixed".intern();
/* 112 */   public static final String ATT_NAME = "name".intern();
/* 113 */   public static final String ATT_NAMESPACE = "namespace".intern();
/* 114 */   public static final String ATT_NILLABLE = "nillable".intern();
/* 115 */   public static final String ATT_PROCESSCONTENTS = "processContents".intern();
/* 116 */   public static final String ATT_REF = "ref".intern();
/* 117 */   public static final String ATT_REFER = "refer".intern();
/* 118 */   public static final String ATT_SCHEMALOCATION = "schemaLocation".intern();
/* 119 */   public static final String ATT_SOURCE = "source".intern();
/* 120 */   public static final String ATT_SUBSTITUTIONGROUP = "substitutionGroup".intern();
/* 121 */   public static final String ATT_SYSTEM = "system".intern();
/* 122 */   public static final String ATT_PUBLIC = "public".intern();
/* 123 */   public static final String ATT_TARGETNAMESPACE = "targetNamespace".intern();
/* 124 */   public static final String ATT_TYPE = "type".intern();
/* 125 */   public static final String ATT_USE = "use".intern();
/* 126 */   public static final String ATT_VALUE = "value".intern();
/* 127 */   public static final String ATT_VERSION = "version".intern();
/* 128 */   public static final String ATT_XML_LANG = "xml:lang".intern();
/* 129 */   public static final String ATT_XPATH = "xpath".intern();
/*     */   public static final String ATTVAL_TWOPOUNDANY = "##any";
/*     */   public static final String ATTVAL_TWOPOUNDLOCAL = "##local";
/*     */   public static final String ATTVAL_TWOPOUNDOTHER = "##other";
/*     */   public static final String ATTVAL_TWOPOUNDTARGETNS = "##targetNamespace";
/*     */   public static final String ATTVAL_POUNDALL = "#all";
/*     */   public static final String ATTVAL_FALSE_0 = "0";
/*     */   public static final String ATTVAL_TRUE_1 = "1";
/*     */   public static final String ATTVAL_ANYSIMPLETYPE = "anySimpleType";
/*     */   public static final String ATTVAL_ANYTYPE = "anyType";
/*     */   public static final String ATTVAL_ANYURI = "anyURI";
/*     */   public static final String ATTVAL_BASE64BINARY = "base64Binary";
/*     */   public static final String ATTVAL_BOOLEAN = "boolean";
/*     */   public static final String ATTVAL_BYTE = "byte";
/*     */   public static final String ATTVAL_COLLAPSE = "collapse";
/*     */   public static final String ATTVAL_DATE = "date";
/*     */   public static final String ATTVAL_DATETIME = "dateTime";
/*     */   public static final String ATTVAL_DAY = "gDay";
/*     */   public static final String ATTVAL_DECIMAL = "decimal";
/*     */   public static final String ATTVAL_DOUBLE = "double";
/*     */   public static final String ATTVAL_DURATION = "duration";
/*     */   public static final String ATTVAL_ENTITY = "ENTITY";
/*     */   public static final String ATTVAL_ENTITIES = "ENTITIES";
/*     */   public static final String ATTVAL_EXTENSION = "extension";
/*     */   public static final String ATTVAL_FALSE = "false";
/*     */   public static final String ATTVAL_FLOAT = "float";
/*     */   public static final String ATTVAL_HEXBINARY = "hexBinary";
/*     */   public static final String ATTVAL_ID = "ID";
/*     */   public static final String ATTVAL_IDREF = "IDREF";
/*     */   public static final String ATTVAL_IDREFS = "IDREFS";
/*     */   public static final String ATTVAL_INT = "int";
/*     */   public static final String ATTVAL_INTEGER = "integer";
/*     */   public static final String ATTVAL_LANGUAGE = "language";
/*     */   public static final String ATTVAL_LAX = "lax";
/*     */   public static final String ATTVAL_LIST = "list";
/*     */   public static final String ATTVAL_LONG = "long";
/*     */   public static final String ATTVAL_NAME = "Name";
/*     */   public static final String ATTVAL_NEGATIVEINTEGER = "negativeInteger";
/*     */   public static final String ATTVAL_MONTH = "gMonth";
/*     */   public static final String ATTVAL_MONTHDAY = "gMonthDay";
/*     */   public static final String ATTVAL_NCNAME = "NCName";
/*     */   public static final String ATTVAL_NMTOKEN = "NMTOKEN";
/*     */   public static final String ATTVAL_NMTOKENS = "NMTOKENS";
/*     */   public static final String ATTVAL_NONNEGATIVEINTEGER = "nonNegativeInteger";
/*     */   public static final String ATTVAL_NONPOSITIVEINTEGER = "nonPositiveInteger";
/*     */   public static final String ATTVAL_NORMALIZEDSTRING = "normalizedString";
/*     */   public static final String ATTVAL_NOTATION = "NOTATION";
/*     */   public static final String ATTVAL_OPTIONAL = "optional";
/*     */   public static final String ATTVAL_POSITIVEINTEGER = "positiveInteger";
/*     */   public static final String ATTVAL_PRESERVE = "preserve";
/*     */   public static final String ATTVAL_PROHIBITED = "prohibited";
/*     */   public static final String ATTVAL_QNAME = "QName";
/*     */   public static final String ATTVAL_QUALIFIED = "qualified";
/*     */   public static final String ATTVAL_REPLACE = "replace";
/*     */   public static final String ATTVAL_REQUIRED = "required";
/*     */   public static final String ATTVAL_RESTRICTION = "restriction";
/*     */   public static final String ATTVAL_SHORT = "short";
/*     */   public static final String ATTVAL_SKIP = "skip";
/*     */   public static final String ATTVAL_STRICT = "strict";
/*     */   public static final String ATTVAL_STRING = "string";
/*     */   public static final String ATTVAL_SUBSTITUTION = "substitution";
/*     */   public static final String ATTVAL_TIME = "time";
/*     */   public static final String ATTVAL_TOKEN = "token";
/*     */   public static final String ATTVAL_TRUE = "true";
/*     */   public static final String ATTVAL_UNBOUNDED = "unbounded";
/*     */   public static final String ATTVAL_UNION = "union";
/*     */   public static final String ATTVAL_UNQUALIFIED = "unqualified";
/*     */   public static final String ATTVAL_UNSIGNEDBYTE = "unsignedByte";
/*     */   public static final String ATTVAL_UNSIGNEDINT = "unsignedInt";
/*     */   public static final String ATTVAL_UNSIGNEDLONG = "unsignedLong";
/*     */   public static final String ATTVAL_UNSIGNEDSHORT = "unsignedShort";
/*     */   public static final String ATTVAL_YEAR = "gYear";
/*     */   public static final String ATTVAL_YEARMONTH = "gYearMonth";
/*     */   public static final short FORM_UNQUALIFIED = 0;
/*     */   public static final short FORM_QUALIFIED = 1;
/*     */   public static final short USE_OPTIONAL = 0;
/*     */   public static final short USE_REQUIRED = 1;
/*     */   public static final short USE_PROHIBITED = 2;
/*     */   public static final int OCCURRENCE_UNBOUNDED = -1;
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\impl\xs\SchemaSymbols.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */