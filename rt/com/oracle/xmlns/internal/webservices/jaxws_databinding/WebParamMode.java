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
/*    */ 
/*    */ @XmlType(name = "web-param-mode")
/*    */ @XmlEnum
/*    */ public enum WebParamMode
/*    */ {
/* 54 */   IN,
/* 55 */   OUT,
/* 56 */   INOUT;
/*    */   
/*    */   public String value() {
/* 59 */     return name();
/*    */   }
/*    */   
/*    */   public static WebParamMode fromValue(String v) {
/* 63 */     return valueOf(v);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\oracle\xmlns\internal\webservices\jaxws_databinding\WebParamMode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */