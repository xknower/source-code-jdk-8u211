/*    */ package com.sun.org.apache.xalan.internal.xsltc.runtime.output;
/*    */ 
/*    */ import com.sun.org.apache.xalan.internal.utils.SecuritySupport;
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
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
/*    */ class WriterOutputBuffer
/*    */   implements OutputBuffer
/*    */ {
/*    */   private static final int KB = 1024;
/* 36 */   private static int BUFFER_SIZE = 4096;
/*    */   private Writer _writer;
/*    */   
/*    */   static {
/* 40 */     String osName = SecuritySupport.getSystemProperty("os.name");
/* 41 */     if (osName.equalsIgnoreCase("solaris")) {
/* 42 */       BUFFER_SIZE = 32768;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WriterOutputBuffer(Writer writer) {
/* 55 */     this._writer = new BufferedWriter(writer, BUFFER_SIZE);
/*    */   }
/*    */   
/*    */   public String close() {
/*    */     try {
/* 60 */       this._writer.flush();
/*    */     }
/* 62 */     catch (IOException e) {
/* 63 */       throw new RuntimeException(e.toString());
/*    */     } 
/* 65 */     return "";
/*    */   }
/*    */   
/*    */   public OutputBuffer append(String s) {
/*    */     try {
/* 70 */       this._writer.write(s);
/*    */     }
/* 72 */     catch (IOException e) {
/* 73 */       throw new RuntimeException(e.toString());
/*    */     } 
/* 75 */     return this;
/*    */   }
/*    */   
/*    */   public OutputBuffer append(char[] s, int from, int to) {
/*    */     try {
/* 80 */       this._writer.write(s, from, to);
/*    */     }
/* 82 */     catch (IOException e) {
/* 83 */       throw new RuntimeException(e.toString());
/*    */     } 
/* 85 */     return this;
/*    */   }
/*    */   
/*    */   public OutputBuffer append(char ch) {
/*    */     try {
/* 90 */       this._writer.write(ch);
/*    */     }
/* 92 */     catch (IOException e) {
/* 93 */       throw new RuntimeException(e.toString());
/*    */     } 
/* 95 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\runtime\output\WriterOutputBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */