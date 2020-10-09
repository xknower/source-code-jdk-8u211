/*    */ package com.sun.xml.internal.ws.util;
/*    */ 
/*    */ import java.io.FilterInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoCloseInputStream
/*    */   extends FilterInputStream
/*    */ {
/*    */   public NoCloseInputStream(InputStream is) {
/* 39 */     super(is);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void close() throws IOException {}
/*    */ 
/*    */   
/*    */   public void doClose() throws IOException {
/* 48 */     super.close();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\w\\util\NoCloseInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */