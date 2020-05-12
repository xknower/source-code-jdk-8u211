/*    */ package com.sun.xml.internal.ws.config.management.policy;
/*    */ 
/*    */ import com.sun.xml.internal.ws.policy.spi.PrefixMapper;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ManagementPrefixMapper
/*    */   implements PrefixMapper
/*    */ {
/* 41 */   private static final Map<String, String> prefixMap = new HashMap<>();
/*    */   
/*    */   static {
/* 44 */     prefixMap.put("http://java.sun.com/xml/ns/metro/management", "sunman");
/*    */   }
/*    */   
/*    */   public Map<String, String> getPrefixMap() {
/* 48 */     return prefixMap;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\config\management\policy\ManagementPrefixMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */