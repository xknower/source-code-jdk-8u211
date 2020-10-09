/*    */ package com.sun.xml.internal.fastinfoset.util;
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
/*    */ public class CharArrayString
/*    */   extends CharArray
/*    */ {
/*    */   protected String _s;
/*    */   
/*    */   public CharArrayString(String s) {
/* 34 */     this(s, true);
/*    */   }
/*    */   
/*    */   public CharArrayString(String s, boolean createArray) {
/* 38 */     this._s = s;
/* 39 */     if (createArray) {
/* 40 */       this.ch = this._s.toCharArray();
/* 41 */       this.start = 0;
/* 42 */       this.length = this.ch.length;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String toString() {
/* 47 */     return this._s;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 51 */     return this._s.hashCode();
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 55 */     if (this == obj) {
/* 56 */       return true;
/*    */     }
/* 58 */     if (obj instanceof CharArrayString) {
/* 59 */       CharArrayString chas = (CharArrayString)obj;
/* 60 */       return this._s.equals(chas._s);
/* 61 */     }  if (obj instanceof CharArray) {
/* 62 */       CharArray cha = (CharArray)obj;
/* 63 */       if (this.length == cha.length) {
/* 64 */         int n = this.length;
/* 65 */         int i = this.start;
/* 66 */         int j = cha.start;
/* 67 */         while (n-- != 0) {
/* 68 */           if (this.ch[i++] != cha.ch[j++])
/* 69 */             return false; 
/*    */         } 
/* 71 */         return true;
/*    */       } 
/*    */     } 
/* 74 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\fastinfose\\util\CharArrayString.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */