/*     */ package com.sun.org.apache.xerces.internal.util;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.utils.SecuritySupport;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Locale;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.ResourceBundle;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DatatypeMessageFormatter
/*     */ {
/*     */   private static final String BASE_NAME = "com.sun.org.apache.xerces.internal.impl.msg.DatatypeMessages";
/*     */   
/*     */   public static String formatMessage(Locale locale, String key, Object[] arguments) throws MissingResourceException {
/*     */     String msg;
/*  57 */     ResourceBundle resourceBundle = null;
/*  58 */     if (locale != null) {
/*     */       
/*  60 */       resourceBundle = SecuritySupport.getResourceBundle("com.sun.org.apache.xerces.internal.impl.msg.DatatypeMessages", locale);
/*     */     }
/*     */     else {
/*     */       
/*  64 */       resourceBundle = SecuritySupport.getResourceBundle("com.sun.org.apache.xerces.internal.impl.msg.DatatypeMessages");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  70 */       msg = resourceBundle.getString(key);
/*  71 */       if (arguments != null) {
/*     */         try {
/*  73 */           msg = MessageFormat.format(msg, arguments);
/*     */         }
/*  75 */         catch (Exception e) {
/*  76 */           msg = resourceBundle.getString("FormatFailed");
/*  77 */           msg = msg + " " + resourceBundle.getString(key);
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */     }
/*  83 */     catch (MissingResourceException e) {
/*  84 */       msg = resourceBundle.getString("BadMessageKey");
/*  85 */       throw new MissingResourceException(key, msg, key);
/*     */     } 
/*     */ 
/*     */     
/*  89 */     if (msg == null) {
/*  90 */       msg = key;
/*  91 */       if (arguments.length > 0) {
/*  92 */         StringBuffer str = new StringBuffer(msg);
/*  93 */         str.append('?');
/*  94 */         for (int i = 0; i < arguments.length; i++) {
/*  95 */           if (i > 0) {
/*  96 */             str.append('&');
/*     */           }
/*  98 */           str.append(String.valueOf(arguments[i]));
/*     */         } 
/*     */       } 
/*     */     } 
/* 102 */     return msg;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\interna\\util\DatatypeMessageFormatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */