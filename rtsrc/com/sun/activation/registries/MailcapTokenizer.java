/*     */ package com.sun.activation.registries;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MailcapTokenizer
/*     */ {
/*     */   public static final int UNKNOWN_TOKEN = 0;
/*     */   public static final int START_TOKEN = 1;
/*     */   public static final int STRING_TOKEN = 2;
/*     */   public static final int EOI_TOKEN = 5;
/*     */   public static final int SLASH_TOKEN = 47;
/*     */   public static final int SEMICOLON_TOKEN = 59;
/*     */   public static final int EQUALS_TOKEN = 61;
/*     */   private String data;
/*     */   private int dataIndex;
/*     */   private int dataLength;
/*     */   private int currentToken;
/*     */   private String currentTokenValue;
/*     */   private boolean isAutoquoting;
/*     */   private char autoquoteChar;
/*     */   
/*     */   public MailcapTokenizer(String inputString) {
/*  48 */     this.data = inputString;
/*  49 */     this.dataIndex = 0;
/*  50 */     this.dataLength = inputString.length();
/*     */     
/*  52 */     this.currentToken = 1;
/*  53 */     this.currentTokenValue = "";
/*     */     
/*  55 */     this.isAutoquoting = false;
/*  56 */     this.autoquoteChar = ';';
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
/*     */   public void setIsAutoquoting(boolean value) {
/*  70 */     this.isAutoquoting = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentToken() {
/*  79 */     return this.currentToken;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String nameForToken(int token) {
/*  86 */     String name = "really unknown";
/*     */     
/*  88 */     switch (token) {
/*     */       case 0:
/*  90 */         name = "unknown";
/*     */         break;
/*     */       case 1:
/*  93 */         name = "start";
/*     */         break;
/*     */       case 2:
/*  96 */         name = "string";
/*     */         break;
/*     */       case 5:
/*  99 */         name = "EOI";
/*     */         break;
/*     */       case 47:
/* 102 */         name = "'/'";
/*     */         break;
/*     */       case 59:
/* 105 */         name = "';'";
/*     */         break;
/*     */       case 61:
/* 108 */         name = "'='";
/*     */         break;
/*     */     } 
/*     */     
/* 112 */     return name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCurrentTokenValue() {
/* 121 */     return this.currentTokenValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int nextToken() {
/* 129 */     if (this.dataIndex < this.dataLength) {
/*     */       
/* 131 */       while (this.dataIndex < this.dataLength && 
/* 132 */         isWhiteSpaceChar(this.data.charAt(this.dataIndex))) {
/* 133 */         this.dataIndex++;
/*     */       }
/*     */       
/* 136 */       if (this.dataIndex < this.dataLength) {
/*     */         
/* 138 */         char c = this.data.charAt(this.dataIndex);
/* 139 */         if (this.isAutoquoting) {
/* 140 */           if (c == ';' || c == '=') {
/* 141 */             this.currentToken = c;
/* 142 */             this.currentTokenValue = (new Character(c)).toString();
/* 143 */             this.dataIndex++;
/*     */           } else {
/* 145 */             processAutoquoteToken();
/*     */           }
/*     */         
/* 148 */         } else if (isStringTokenChar(c)) {
/* 149 */           processStringToken();
/* 150 */         } else if (c == '/' || c == ';' || c == '=') {
/* 151 */           this.currentToken = c;
/* 152 */           this.currentTokenValue = (new Character(c)).toString();
/* 153 */           this.dataIndex++;
/*     */         } else {
/* 155 */           this.currentToken = 0;
/* 156 */           this.currentTokenValue = (new Character(c)).toString();
/* 157 */           this.dataIndex++;
/*     */         } 
/*     */       } else {
/*     */         
/* 161 */         this.currentToken = 5;
/* 162 */         this.currentTokenValue = null;
/*     */       } 
/*     */     } else {
/* 165 */       this.currentToken = 5;
/* 166 */       this.currentTokenValue = null;
/*     */     } 
/*     */     
/* 169 */     return this.currentToken;
/*     */   }
/*     */ 
/*     */   
/*     */   private void processStringToken() {
/* 174 */     int initialIndex = this.dataIndex;
/*     */ 
/*     */     
/* 177 */     while (this.dataIndex < this.dataLength && 
/* 178 */       isStringTokenChar(this.data.charAt(this.dataIndex))) {
/* 179 */       this.dataIndex++;
/*     */     }
/*     */     
/* 182 */     this.currentToken = 2;
/* 183 */     this.currentTokenValue = this.data.substring(initialIndex, this.dataIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   private void processAutoquoteToken() {
/* 188 */     int initialIndex = this.dataIndex;
/*     */ 
/*     */ 
/*     */     
/* 192 */     boolean foundTerminator = false;
/* 193 */     while (this.dataIndex < this.dataLength && !foundTerminator) {
/* 194 */       char c = this.data.charAt(this.dataIndex);
/* 195 */       if (c != this.autoquoteChar) {
/* 196 */         this.dataIndex++; continue;
/*     */       } 
/* 198 */       foundTerminator = true;
/*     */     } 
/*     */ 
/*     */     
/* 202 */     this.currentToken = 2;
/* 203 */     this
/* 204 */       .currentTokenValue = fixEscapeSequences(this.data.substring(initialIndex, this.dataIndex));
/*     */   }
/*     */   
/*     */   private static boolean isSpecialChar(char c) {
/* 208 */     boolean lAnswer = false;
/*     */     
/* 210 */     switch (c) {
/*     */       case '"':
/*     */       case '(':
/*     */       case ')':
/*     */       case ',':
/*     */       case '/':
/*     */       case ':':
/*     */       case ';':
/*     */       case '<':
/*     */       case '=':
/*     */       case '>':
/*     */       case '?':
/*     */       case '@':
/*     */       case '[':
/*     */       case '\\':
/*     */       case ']':
/* 226 */         lAnswer = true;
/*     */         break;
/*     */     } 
/*     */     
/* 230 */     return lAnswer;
/*     */   }
/*     */   
/*     */   private static boolean isControlChar(char c) {
/* 234 */     return Character.isISOControl(c);
/*     */   }
/*     */   
/*     */   private static boolean isWhiteSpaceChar(char c) {
/* 238 */     return Character.isWhitespace(c);
/*     */   }
/*     */   
/*     */   private static boolean isStringTokenChar(char c) {
/* 242 */     return (!isSpecialChar(c) && !isControlChar(c) && !isWhiteSpaceChar(c));
/*     */   }
/*     */   
/*     */   private static String fixEscapeSequences(String inputString) {
/* 246 */     int inputLength = inputString.length();
/* 247 */     StringBuffer buffer = new StringBuffer();
/* 248 */     buffer.ensureCapacity(inputLength);
/*     */     
/* 250 */     for (int i = 0; i < inputLength; i++) {
/* 251 */       char currentChar = inputString.charAt(i);
/* 252 */       if (currentChar != '\\') {
/* 253 */         buffer.append(currentChar);
/*     */       }
/* 255 */       else if (i < inputLength - 1) {
/* 256 */         char nextChar = inputString.charAt(i + 1);
/* 257 */         buffer.append(nextChar);
/*     */ 
/*     */         
/* 260 */         i++;
/*     */       } else {
/* 262 */         buffer.append(currentChar);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 267 */     return buffer.toString();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\activation\registries\MailcapTokenizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */