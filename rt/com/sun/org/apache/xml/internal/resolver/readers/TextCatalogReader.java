/*     */ package com.sun.org.apache.xml.internal.resolver.readers;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.resolver.Catalog;
/*     */ import com.sun.org.apache.xml.internal.resolver.CatalogEntry;
/*     */ import com.sun.org.apache.xml.internal.resolver.CatalogException;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.util.Stack;
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
/*     */ public class TextCatalogReader
/*     */   implements CatalogReader
/*     */ {
/*  52 */   protected InputStream catfile = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   protected int[] stack = new int[3];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   protected Stack tokenStack = new Stack();
/*     */ 
/*     */   
/*  67 */   protected int top = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean caseSensitive = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCaseSensitive(boolean isCaseSensitive) {
/*  78 */     this.caseSensitive = isCaseSensitive;
/*     */   }
/*     */   
/*     */   public boolean getCaseSensitive() {
/*  82 */     return this.caseSensitive;
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
/*     */   public void readCatalog(Catalog catalog, String fileUrl) throws MalformedURLException, IOException {
/*  97 */     URL catURL = null;
/*     */     
/*     */     try {
/* 100 */       catURL = new URL(fileUrl);
/* 101 */     } catch (MalformedURLException e) {
/* 102 */       catURL = new URL("file:///" + fileUrl);
/*     */     } 
/*     */     
/* 105 */     URLConnection urlCon = catURL.openConnection();
/*     */     try {
/* 107 */       readCatalog(catalog, urlCon.getInputStream());
/* 108 */     } catch (FileNotFoundException e) {
/* 109 */       (catalog.getCatalogManager()).debug.message(1, "Failed to load catalog, file not found", catURL
/* 110 */           .toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void readCatalog(Catalog catalog, InputStream is) throws MalformedURLException, IOException {
/* 117 */     this.catfile = is;
/*     */     
/* 119 */     if (this.catfile == null) {
/*     */       return;
/*     */     }
/*     */     
/* 123 */     Vector<String> unknownEntry = null;
/*     */     
/*     */     try {
/*     */       while (true) {
/* 127 */         String token = nextToken();
/*     */         
/* 129 */         if (token == null) {
/* 130 */           if (unknownEntry != null) {
/* 131 */             catalog.unknownEntry(unknownEntry);
/* 132 */             unknownEntry = null;
/*     */           } 
/* 134 */           this.catfile.close();
/* 135 */           this.catfile = null;
/*     */           
/*     */           return;
/*     */         } 
/* 139 */         String entryToken = null;
/* 140 */         if (this.caseSensitive) {
/* 141 */           entryToken = token;
/*     */         } else {
/* 143 */           entryToken = token.toUpperCase();
/*     */         } 
/*     */         
/*     */         try {
/* 147 */           int type = CatalogEntry.getEntryType(entryToken);
/* 148 */           int numArgs = CatalogEntry.getEntryArgCount(type);
/* 149 */           Vector<String> args = new Vector();
/*     */           
/* 151 */           if (unknownEntry != null) {
/* 152 */             catalog.unknownEntry(unknownEntry);
/* 153 */             unknownEntry = null;
/*     */           } 
/*     */           
/* 156 */           for (int count = 0; count < numArgs; count++) {
/* 157 */             args.addElement(nextToken());
/*     */           }
/*     */           
/* 160 */           catalog.addEntry(new CatalogEntry(entryToken, args));
/* 161 */         } catch (CatalogException cex) {
/* 162 */           if (cex.getExceptionType() == 3) {
/* 163 */             if (unknownEntry == null) {
/* 164 */               unknownEntry = new Vector();
/*     */             }
/* 166 */             unknownEntry.addElement(token); continue;
/* 167 */           }  if (cex.getExceptionType() == 2) {
/* 168 */             (catalog.getCatalogManager()).debug.message(1, "Invalid catalog entry", token);
/* 169 */             unknownEntry = null; continue;
/* 170 */           }  if (cex.getExceptionType() == 8) {
/* 171 */             (catalog.getCatalogManager()).debug.message(1, cex.getMessage());
/*     */           }
/*     */         } 
/*     */       } 
/* 175 */     } catch (CatalogException cex2) {
/* 176 */       if (cex2.getExceptionType() == 8) {
/* 177 */         (catalog.getCatalogManager()).debug.message(1, cex2.getMessage());
/*     */       }
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() {
/* 188 */     if (this.catfile != null) {
/*     */       try {
/* 190 */         this.catfile.close();
/* 191 */       } catch (IOException iOException) {}
/*     */     }
/*     */ 
/*     */     
/* 195 */     this.catfile = null;
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
/*     */   protected String nextToken() throws IOException, CatalogException {
/*     */     int nextch;
/* 210 */     String token = "";
/*     */ 
/*     */     
/* 213 */     if (!this.tokenStack.empty()) {
/* 214 */       return this.tokenStack.pop();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 220 */       ch = this.catfile.read();
/* 221 */       while (ch <= 32) {
/* 222 */         ch = this.catfile.read();
/* 223 */         if (ch < 0) {
/* 224 */           return null;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 229 */       nextch = this.catfile.read();
/* 230 */       if (nextch < 0) {
/* 231 */         return null;
/*     */       }
/*     */       
/* 234 */       if (ch == 45 && nextch == 45) {
/*     */         
/* 236 */         ch = 32;
/* 237 */         nextch = nextChar();
/* 238 */         while ((ch != 45 || nextch != 45) && nextch > 0) {
/* 239 */           ch = nextch;
/* 240 */           nextch = nextChar();
/*     */         } 
/*     */         
/* 243 */         if (nextch < 0) {
/* 244 */           throw new CatalogException(8, "Unterminated comment in catalog file; EOF treated as end-of-comment.");
/*     */         }
/*     */         
/*     */         continue;
/*     */       } 
/*     */       break;
/*     */     } 
/* 251 */     this.stack[++this.top] = nextch;
/* 252 */     this.stack[++this.top] = ch;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 257 */     int ch = nextChar();
/* 258 */     if (ch == 34 || ch == 39) {
/* 259 */       int quote = ch;
/* 260 */       while ((ch = nextChar()) != quote) {
/* 261 */         char[] chararr = new char[1];
/* 262 */         chararr[0] = (char)ch;
/* 263 */         String s = new String(chararr);
/* 264 */         token = token.concat(s);
/*     */       } 
/* 266 */       return token;
/*     */     } 
/*     */ 
/*     */     
/* 270 */     while (ch > 32) {
/* 271 */       nextch = nextChar();
/* 272 */       if (ch == 45 && nextch == 45) {
/* 273 */         this.stack[++this.top] = ch;
/* 274 */         this.stack[++this.top] = nextch;
/* 275 */         return token;
/*     */       } 
/* 277 */       char[] chararr = new char[1];
/* 278 */       chararr[0] = (char)ch;
/* 279 */       String s = new String(chararr);
/* 280 */       token = token.concat(s);
/* 281 */       ch = nextch;
/*     */     } 
/*     */     
/* 284 */     return token;
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
/*     */   protected int nextChar() throws IOException {
/* 297 */     if (this.top < 0) {
/* 298 */       return this.catfile.read();
/*     */     }
/* 300 */     return this.stack[this.top--];
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xml\internal\resolver\readers\TextCatalogReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */