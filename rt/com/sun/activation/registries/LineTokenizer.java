/*     */ package com.sun.activation.registries;
/*     */ 
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class LineTokenizer
/*     */ {
/*     */   private int currentPosition;
/*     */   private int maxPosition;
/*     */   private String str;
/* 218 */   private Vector stack = new Vector();
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String singles = "=";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LineTokenizer(String str) {
/* 228 */     this.currentPosition = 0;
/* 229 */     this.str = str;
/* 230 */     this.maxPosition = str.length();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void skipWhiteSpace() {
/* 237 */     while (this.currentPosition < this.maxPosition && 
/* 238 */       Character.isWhitespace(this.str.charAt(this.currentPosition))) {
/* 239 */       this.currentPosition++;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasMoreTokens() {
/* 250 */     if (this.stack.size() > 0)
/* 251 */       return true; 
/* 252 */     skipWhiteSpace();
/* 253 */     return (this.currentPosition < this.maxPosition);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String nextToken() {
/* 264 */     int size = this.stack.size();
/* 265 */     if (size > 0) {
/* 266 */       String t = this.stack.elementAt(size - 1);
/* 267 */       this.stack.removeElementAt(size - 1);
/* 268 */       return t;
/*     */     } 
/* 270 */     skipWhiteSpace();
/*     */     
/* 272 */     if (this.currentPosition >= this.maxPosition) {
/* 273 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/* 276 */     int start = this.currentPosition;
/* 277 */     char c = this.str.charAt(start);
/* 278 */     if (c == '"') {
/* 279 */       this.currentPosition++;
/* 280 */       boolean filter = false;
/* 281 */       while (this.currentPosition < this.maxPosition) {
/* 282 */         c = this.str.charAt(this.currentPosition++);
/* 283 */         if (c == '\\') {
/* 284 */           this.currentPosition++;
/* 285 */           filter = true; continue;
/* 286 */         }  if (c == '"') {
/*     */           String s;
/*     */           
/* 289 */           if (filter) {
/* 290 */             StringBuffer sb = new StringBuffer();
/* 291 */             for (int i = start + 1; i < this.currentPosition - 1; i++) {
/* 292 */               c = this.str.charAt(i);
/* 293 */               if (c != '\\')
/* 294 */                 sb.append(c); 
/*     */             } 
/* 296 */             s = sb.toString();
/*     */           } else {
/* 298 */             s = this.str.substring(start + 1, this.currentPosition - 1);
/* 299 */           }  return s;
/*     */         } 
/*     */       } 
/* 302 */     } else if ("=".indexOf(c) >= 0) {
/* 303 */       this.currentPosition++;
/*     */     } else {
/* 305 */       while (this.currentPosition < this.maxPosition && "="
/* 306 */         .indexOf(this.str.charAt(this.currentPosition)) < 0 && 
/* 307 */         !Character.isWhitespace(this.str.charAt(this.currentPosition))) {
/* 308 */         this.currentPosition++;
/*     */       }
/*     */     } 
/* 311 */     return this.str.substring(start, this.currentPosition);
/*     */   }
/*     */   
/*     */   public void pushToken(String token) {
/* 315 */     this.stack.addElement(token);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\activation\registries\LineTokenizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */