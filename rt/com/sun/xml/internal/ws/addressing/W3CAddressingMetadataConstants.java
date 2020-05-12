/*    */ package com.sun.xml.internal.ws.addressing;
/*    */ 
/*    */ import javax.xml.namespace.QName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class W3CAddressingMetadataConstants
/*    */ {
/*    */   public static final String WSAM_NAMESPACE_NAME = "http://www.w3.org/2007/05/addressing/metadata";
/*    */   public static final String WSAM_PREFIX_NAME = "wsam";
/* 37 */   public static final QName WSAM_ACTION_QNAME = new QName("http://www.w3.org/2007/05/addressing/metadata", "Action", "wsam");
/*    */   
/*    */   public static final String WSAM_ADDRESSING_ASSERTION_NAME = "Addressing";
/*    */   
/*    */   public static final String WSAM_ANONYMOUS_NESTED_ASSERTION_NAME = "AnonymousResponses";
/*    */   public static final String WSAM_NONANONYMOUS_NESTED_ASSERTION_NAME = "NonAnonymousResponses";
/* 43 */   public static final QName WSAM_ADDRESSING_ASSERTION = new QName("http://www.w3.org/2007/05/addressing/metadata", "Addressing", "wsam");
/*    */ 
/*    */   
/* 46 */   public static final QName WSAM_ANONYMOUS_NESTED_ASSERTION = new QName("http://www.w3.org/2007/05/addressing/metadata", "AnonymousResponses", "wsam");
/*    */ 
/*    */   
/* 49 */   public static final QName WSAM_NONANONYMOUS_NESTED_ASSERTION = new QName("http://www.w3.org/2007/05/addressing/metadata", "NonAnonymousResponses", "wsam");
/*    */   public static final String WSAM_WSDLI_ATTRIBUTE_NAMESPACE = "http://www.w3.org/ns/wsdl-instance";
/*    */   public static final String WSAM_WSDLI_ATTRIBUTE_PREFIX = "wsdli";
/*    */   public static final String WSAM_WSDLI_ATTRIBUTE_LOCALNAME = "wsdlLocation";
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\addressing\W3CAddressingMetadataConstants.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */