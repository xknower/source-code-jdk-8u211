/*    */ package com.sun.org.apache.xml.internal.security.keys.keyresolver;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class KeyResolverException
/*    */   extends XMLSecurityException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public KeyResolverException() {}
/*    */   
/*    */   public KeyResolverException(String paramString) {
/* 48 */     super(paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public KeyResolverException(String paramString, Object[] paramArrayOfObject) {
/* 58 */     super(paramString, paramArrayOfObject);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public KeyResolverException(String paramString, Exception paramException) {
/* 68 */     super(paramString, paramException);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public KeyResolverException(String paramString, Object[] paramArrayOfObject, Exception paramException) {
/* 79 */     super(paramString, paramArrayOfObject, paramException);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xml\internal\security\keys\keyresolver\KeyResolverException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */