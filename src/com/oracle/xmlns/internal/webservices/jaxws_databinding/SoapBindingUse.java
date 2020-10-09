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
/*    */ @XmlType(name = "soap-binding-use")
/*    */ @XmlEnum
/*    */ public enum SoapBindingUse
/*    */ {
/* 53 */   LITERAL,
/* 54 */   ENCODED;
/*    */   
/*    */   public String value() {
/* 57 */     return name();
/*    */   }
/*    */   
/*    */   public static SoapBindingUse fromValue(String v) {
/* 61 */     return valueOf(v);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\oracle\xmlns\internal\webservices\jaxws_databinding\SoapBindingUse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */