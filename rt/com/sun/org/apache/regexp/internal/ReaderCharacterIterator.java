/*     */ package com.sun.org.apache.regexp.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ReaderCharacterIterator
/*     */   implements CharacterIterator
/*     */ {
/*     */   private final Reader reader;
/*     */   private final StringBuffer buff;
/*     */   private boolean closed;
/*     */   
/*     */   public ReaderCharacterIterator(Reader reader) {
/*  45 */     this.reader = reader;
/*  46 */     this.buff = new StringBuffer(512);
/*  47 */     this.closed = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String substring(int beginIndex, int endIndex) {
/*     */     try {
/*  55 */       ensure(endIndex);
/*  56 */       return this.buff.toString().substring(beginIndex, endIndex);
/*     */     }
/*  58 */     catch (IOException e) {
/*     */       
/*  60 */       throw new StringIndexOutOfBoundsException(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String substring(int beginIndex) {
/*     */     try {
/*  69 */       readAll();
/*  70 */       return this.buff.toString().substring(beginIndex);
/*     */     }
/*  72 */     catch (IOException e) {
/*     */       
/*  74 */       throw new StringIndexOutOfBoundsException(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char charAt(int pos) {
/*     */     try {
/*  83 */       ensure(pos);
/*  84 */       return this.buff.charAt(pos);
/*     */     }
/*  86 */     catch (IOException e) {
/*     */       
/*  88 */       throw new StringIndexOutOfBoundsException(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEnd(int pos) {
/*  95 */     if (this.buff.length() > pos)
/*     */     {
/*  97 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 103 */       ensure(pos);
/* 104 */       return (this.buff.length() <= pos);
/*     */     }
/* 106 */     catch (IOException e) {
/*     */       
/* 108 */       throw new StringIndexOutOfBoundsException(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int read(int n) throws IOException {
/* 116 */     if (this.closed)
/*     */     {
/* 118 */       return 0;
/*     */     }
/*     */     
/* 121 */     char[] c = new char[n];
/* 122 */     int count = 0;
/* 123 */     int read = 0;
/*     */ 
/*     */     
/*     */     do {
/* 127 */       read = this.reader.read(c);
/* 128 */       if (read < 0) {
/*     */         
/* 130 */         this.closed = true;
/*     */         break;
/*     */       } 
/* 133 */       count += read;
/* 134 */       this.buff.append(c, 0, read);
/*     */     }
/* 136 */     while (count < n);
/*     */     
/* 138 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readAll() throws IOException {
/* 144 */     while (!this.closed)
/*     */     {
/* 146 */       read(1000);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void ensure(int idx) throws IOException {
/* 153 */     if (this.closed) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 158 */     if (idx < this.buff.length()) {
/*     */       return;
/*     */     }
/*     */     
/* 162 */     read(idx + 1 - this.buff.length());
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\regexp\internal\ReaderCharacterIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */