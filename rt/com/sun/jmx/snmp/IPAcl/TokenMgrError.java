/*     */ package com.sun.jmx.snmp.IPAcl;
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
/*     */ class TokenMgrError
/*     */   extends Error
/*     */ {
/*     */   private static final long serialVersionUID = -6373071623408870347L;
/*     */   static final int LEXICAL_ERROR = 0;
/*     */   static final int STATIC_LEXER_ERROR = 1;
/*     */   static final int INVALID_LEXICAL_STATE = 2;
/*     */   static final int LOOP_DETECTED = 3;
/*     */   int errorCode;
/*     */   
/*     */   protected static final String addEscapes(String paramString) {
/*  68 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/*  70 */     for (byte b = 0; b < paramString.length(); b++) {
/*  71 */       char c; switch (paramString.charAt(b)) {
/*     */         case '\000':
/*     */           break;
/*     */         
/*     */         case '\b':
/*  76 */           stringBuffer.append("\\b");
/*     */           break;
/*     */         case '\t':
/*  79 */           stringBuffer.append("\\t");
/*     */           break;
/*     */         case '\n':
/*  82 */           stringBuffer.append("\\n");
/*     */           break;
/*     */         case '\f':
/*  85 */           stringBuffer.append("\\f");
/*     */           break;
/*     */         case '\r':
/*  88 */           stringBuffer.append("\\r");
/*     */           break;
/*     */         case '"':
/*  91 */           stringBuffer.append("\\\"");
/*     */           break;
/*     */         case '\'':
/*  94 */           stringBuffer.append("\\'");
/*     */           break;
/*     */         case '\\':
/*  97 */           stringBuffer.append("\\\\");
/*     */           break;
/*     */         default:
/* 100 */           if ((c = paramString.charAt(b)) < ' ' || c > '~') {
/* 101 */             String str = "0000" + Integer.toString(c, 16);
/* 102 */             stringBuffer.append("\\u" + str.substring(str.length() - 4, str.length())); break;
/*     */           } 
/* 104 */           stringBuffer.append(c);
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 109 */     return stringBuffer.toString();
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
/*     */   private static final String LexicalError(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, String paramString, char paramChar) {
/* 125 */     return "Lexical error at line " + paramInt2 + ", column " + paramInt3 + ".  Encountered: " + (paramBoolean ? "<EOF> " : ("\"" + 
/*     */ 
/*     */       
/* 128 */       addEscapes(String.valueOf(paramChar)) + "\"" + " (" + paramChar + "), ")) + "after : \"" + 
/* 129 */       addEscapes(paramString) + "\"";
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
/*     */   public String getMessage() {
/* 142 */     return super.getMessage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TokenMgrError() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public TokenMgrError(String paramString, int paramInt) {
/* 153 */     super(paramString);
/* 154 */     this.errorCode = paramInt;
/*     */   }
/*     */   
/*     */   public TokenMgrError(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, String paramString, char paramChar, int paramInt4) {
/* 158 */     this(LexicalError(paramBoolean, paramInt1, paramInt2, paramInt3, paramString, paramChar), paramInt4);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\IPAcl\TokenMgrError.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */