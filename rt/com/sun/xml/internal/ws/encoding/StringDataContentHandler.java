/*     */ package com.sun.xml.internal.ws.encoding;
/*     */ 
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.nio.charset.Charset;
/*     */ import javax.activation.ActivationDataFlavor;
/*     */ import javax.activation.DataContentHandler;
/*     */ import javax.activation.DataSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StringDataContentHandler
/*     */   implements DataContentHandler
/*     */ {
/*  43 */   private static final ActivationDataFlavor myDF = new ActivationDataFlavor(String.class, "text/plain", "Text String");
/*     */ 
/*     */   
/*     */   protected ActivationDataFlavor getDF() {
/*  47 */     return myDF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataFlavor[] getTransferDataFlavors() {
/*  56 */     return new DataFlavor[] { getDF() };
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
/*     */   public Object getTransferData(DataFlavor df, DataSource ds) throws IOException {
/*  70 */     if (getDF().equals(df)) {
/*  71 */       return getContent(ds);
/*     */     }
/*  73 */     return null;
/*     */   }
/*     */   public Object getContent(DataSource ds) throws IOException {
/*     */     InputStreamReader is;
/*  77 */     String enc = null;
/*     */ 
/*     */     
/*     */     try {
/*  81 */       enc = getCharset(ds.getContentType());
/*  82 */       is = new InputStreamReader(ds.getInputStream(), enc);
/*  83 */     } catch (IllegalArgumentException iex) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  92 */       throw new UnsupportedEncodingException(enc);
/*     */     } 
/*     */     
/*     */     try {
/*  96 */       int pos = 0;
/*     */       
/*  98 */       char[] buf = new char[1024];
/*     */       int count;
/* 100 */       while ((count = is.read(buf, pos, buf.length - pos)) != -1) {
/* 101 */         pos += count;
/* 102 */         if (pos >= buf.length) {
/* 103 */           int size = buf.length;
/* 104 */           if (size < 262144) {
/* 105 */             size += size;
/*     */           } else {
/* 107 */             size += 262144;
/* 108 */           }  char[] tbuf = new char[size];
/* 109 */           System.arraycopy(buf, 0, tbuf, 0, pos);
/* 110 */           buf = tbuf;
/*     */         } 
/*     */       } 
/* 113 */       return new String(buf, 0, pos);
/*     */     } finally {
/*     */       try {
/* 116 */         is.close();
/* 117 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(Object obj, String type, OutputStream os) throws IOException {
/*     */     OutputStreamWriter osw;
/* 128 */     if (!(obj instanceof String)) {
/* 129 */       throw new IOException("\"" + getDF().getMimeType() + "\" DataContentHandler requires String object, was given object of type " + obj
/*     */           
/* 131 */           .getClass().toString());
/*     */     }
/* 133 */     String enc = null;
/*     */ 
/*     */     
/*     */     try {
/* 137 */       enc = getCharset(type);
/* 138 */       osw = new OutputStreamWriter(os, enc);
/* 139 */     } catch (IllegalArgumentException iex) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 148 */       throw new UnsupportedEncodingException(enc);
/*     */     } 
/*     */     
/* 151 */     String s = (String)obj;
/* 152 */     osw.write(s, 0, s.length());
/* 153 */     osw.flush();
/*     */   }
/*     */   
/*     */   private String getCharset(String type) {
/*     */     try {
/* 158 */       ContentType ct = new ContentType(type);
/* 159 */       String charset = ct.getParameter("charset");
/* 160 */       if (charset == null)
/*     */       {
/* 162 */         charset = "us-ascii";
/*     */       }
/* 164 */       return Charset.forName(charset).name();
/*     */     }
/* 166 */     catch (Exception ex) {
/* 167 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\encoding\StringDataContentHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */