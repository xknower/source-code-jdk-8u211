/*    */ package com.sun.xml.internal.org.jvnet.staxex;
/*    */ 
/*    */ import java.io.Closeable;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.URL;
/*    */ import javax.activation.DataHandler;
/*    */ import javax.activation.DataSource;
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
/*    */ public abstract class StreamingDataHandler
/*    */   extends DataHandler
/*    */   implements Closeable
/*    */ {
/*    */   public StreamingDataHandler(Object o, String s) {
/* 56 */     super(o, s);
/*    */   }
/*    */   
/*    */   public StreamingDataHandler(URL url) {
/* 60 */     super(url);
/*    */   }
/*    */   
/*    */   public StreamingDataHandler(DataSource dataSource) {
/* 64 */     super(dataSource);
/*    */   }
/*    */   
/*    */   public abstract InputStream readOnce() throws IOException;
/*    */   
/*    */   public abstract void moveTo(File paramFile) throws IOException;
/*    */   
/*    */   public abstract void close() throws IOException;
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\org\jvnet\staxex\StreamingDataHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */