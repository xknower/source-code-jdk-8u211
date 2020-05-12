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
/*     */ class ParseException
/*     */   extends Exception
/*     */ {
/*     */   private static final long serialVersionUID = -3695190720704845876L;
/*     */   protected boolean specialConstructor;
/*     */   public Token currentToken;
/*     */   public int[][] expectedTokenSequences;
/*     */   public String[] tokenImage;
/*     */   protected String eol;
/*     */   
/*     */   public ParseException(Token paramToken, int[][] paramArrayOfint, String[] paramArrayOfString) {
/*  58 */     super("");
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
/* 165 */     this.eol = System.getProperty("line.separator", "\n"); this.specialConstructor = true; this.currentToken = paramToken; this.expectedTokenSequences = paramArrayOfint; this.tokenImage = paramArrayOfString; } public ParseException() { this.eol = System.getProperty("line.separator", "\n"); this.specialConstructor = false; } public ParseException(String paramString) { super(paramString); this.eol = System.getProperty("line.separator", "\n");
/*     */     this.specialConstructor = false; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String add_escapes(String paramString) {
/* 173 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/* 175 */     for (byte b = 0; b < paramString.length(); b++) {
/* 176 */       char c; switch (paramString.charAt(b)) {
/*     */         case '\000':
/*     */           break;
/*     */         
/*     */         case '\b':
/* 181 */           stringBuffer.append("\\b");
/*     */           break;
/*     */         case '\t':
/* 184 */           stringBuffer.append("\\t");
/*     */           break;
/*     */         case '\n':
/* 187 */           stringBuffer.append("\\n");
/*     */           break;
/*     */         case '\f':
/* 190 */           stringBuffer.append("\\f");
/*     */           break;
/*     */         case '\r':
/* 193 */           stringBuffer.append("\\r");
/*     */           break;
/*     */         case '"':
/* 196 */           stringBuffer.append("\\\"");
/*     */           break;
/*     */         case '\'':
/* 199 */           stringBuffer.append("\\'");
/*     */           break;
/*     */         case '\\':
/* 202 */           stringBuffer.append("\\\\");
/*     */           break;
/*     */         default:
/* 205 */           if ((c = paramString.charAt(b)) < ' ' || c > '~') {
/* 206 */             String str = "0000" + Integer.toString(c, 16);
/* 207 */             stringBuffer.append("\\u" + str.substring(str.length() - 4, str.length())); break;
/*     */           } 
/* 209 */           stringBuffer.append(c);
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 214 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   public String getMessage() {
/*     */     if (!this.specialConstructor)
/*     */       return super.getMessage(); 
/*     */     String str1 = "";
/*     */     int i = 0;
/*     */     for (byte b1 = 0; b1 < this.expectedTokenSequences.length; b1++) {
/*     */       if (i < (this.expectedTokenSequences[b1]).length)
/*     */         i = (this.expectedTokenSequences[b1]).length; 
/*     */       for (byte b = 0; b < (this.expectedTokenSequences[b1]).length; b++)
/*     */         str1 = str1 + this.tokenImage[this.expectedTokenSequences[b1][b]] + " "; 
/*     */       if (this.expectedTokenSequences[b1][(this.expectedTokenSequences[b1]).length - 1] != 0)
/*     */         str1 = str1 + "..."; 
/*     */       str1 = str1 + this.eol + "    ";
/*     */     } 
/*     */     String str2 = "Encountered \"";
/*     */     Token token = this.currentToken.next;
/*     */     for (byte b2 = 0; b2 < i; b2++) {
/*     */       if (b2 != 0)
/*     */         str2 = str2 + " "; 
/*     */       if (token.kind == 0) {
/*     */         str2 = str2 + this.tokenImage[0];
/*     */         break;
/*     */       } 
/*     */       str2 = str2 + add_escapes(token.image);
/*     */       token = token.next;
/*     */     } 
/*     */     str2 = str2 + "\" at line " + this.currentToken.next.beginLine + ", column " + this.currentToken.next.beginColumn + "." + this.eol;
/*     */     if (this.expectedTokenSequences.length == 1) {
/*     */       str2 = str2 + "Was expecting:" + this.eol + "    ";
/*     */     } else {
/*     */       str2 = str2 + "Was expecting one of:" + this.eol + "    ";
/*     */     } 
/*     */     str2 = str2 + str1;
/*     */     return str2;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\IPAcl\ParseException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */