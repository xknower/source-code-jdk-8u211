/*     */ package com.sun.org.apache.xml.internal.serializer.utils;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.utils.SecuritySupport;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.ListResourceBundle;
/*     */ import java.util.Locale;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Messages
/*     */ {
/* 100 */   private final Locale m_locale = Locale.getDefault();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ListResourceBundle m_resourceBundle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String m_resourceBundleName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Messages(String resourceBundle) {
/* 124 */     this.m_resourceBundleName = resourceBundle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Locale getLocale() {
/* 136 */     return this.m_locale;
/*     */   }
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
/*     */   public final String createMessage(String msgKey, Object[] args) {
/* 152 */     if (this.m_resourceBundle == null) {
/* 153 */       this.m_resourceBundle = SecuritySupport.getResourceBundle(this.m_resourceBundleName);
/*     */     }
/* 155 */     if (this.m_resourceBundle != null)
/*     */     {
/* 157 */       return createMsg(this.m_resourceBundle, msgKey, args);
/*     */     }
/*     */     
/* 160 */     return "Could not load the resource bundles: " + this.m_resourceBundleName;
/*     */   }
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
/*     */   private final String createMsg(ListResourceBundle fResourceBundle, String msgKey, Object[] args) {
/* 183 */     String fmsg = null;
/* 184 */     boolean throwex = false;
/* 185 */     String msg = null;
/*     */     
/* 187 */     if (msgKey != null) {
/* 188 */       msg = fResourceBundle.getString(msgKey);
/*     */     } else {
/* 190 */       msgKey = "";
/*     */     } 
/* 192 */     if (msg == null) {
/*     */       
/* 194 */       throwex = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 202 */         msg = MessageFormat.format("BAD_MSGKEY", new Object[] { msgKey, this.m_resourceBundleName });
/*     */ 
/*     */       
/*     */       }
/* 206 */       catch (Exception e) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 211 */         msg = "The message key '" + msgKey + "' is not in the message class '" + this.m_resourceBundleName + "'";
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 218 */     else if (args != null) {
/*     */       
/*     */       try
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 225 */         int n = args.length;
/*     */         
/* 227 */         for (int i = 0; i < n; i++) {
/*     */           
/* 229 */           if (null == args[i]) {
/* 230 */             args[i] = "";
/*     */           }
/*     */         } 
/* 233 */         fmsg = MessageFormat.format(msg, args);
/*     */       
/*     */       }
/* 236 */       catch (Exception e)
/*     */       {
/* 238 */         throwex = true;
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 243 */           fmsg = MessageFormat.format("BAD_MSGFORMAT", new Object[] { msgKey, this.m_resourceBundleName });
/*     */ 
/*     */           
/* 246 */           fmsg = fmsg + " " + msg;
/*     */         }
/* 248 */         catch (Exception formatfailed) {
/*     */ 
/*     */ 
/*     */           
/* 252 */           fmsg = "The format of message '" + msgKey + "' in message class '" + this.m_resourceBundleName + "' failed.";
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 262 */       fmsg = msg;
/*     */     } 
/* 264 */     if (throwex)
/*     */     {
/* 266 */       throw new RuntimeException(fmsg);
/*     */     }
/*     */     
/* 269 */     return fmsg;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xml\internal\serialize\\utils\Messages.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */