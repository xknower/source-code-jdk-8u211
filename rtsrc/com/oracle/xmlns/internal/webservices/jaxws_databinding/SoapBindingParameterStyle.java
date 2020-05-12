/*    */ package com.oracle.xmlns.internal.webservices.jaxws_databinding;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlEnum;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlType(name = "soap-binding-parameter-style")
/*    */ @XmlEnum
/*    */ public enum SoapBindingParameterStyle
/*    */ {
/* 53 */   BARE,
/* 54 */   WRAPPED;
/*    */   
/*    */   public String value() {
/* 57 */     return name();
/*    */   }
/*    */   
/*    */   public static SoapBindingParameterStyle fromValue(String v) {
/* 61 */     return valueOf(v);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\oracle\xmlns\internal\webservices\jaxws_databinding\SoapBindingParameterStyle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */