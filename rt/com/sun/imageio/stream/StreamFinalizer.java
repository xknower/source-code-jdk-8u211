/*    */ package com.sun.imageio.stream;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.imageio.stream.ImageInputStream;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StreamFinalizer
/*    */ {
/*    */   private ImageInputStream stream;
/*    */   
/*    */   public StreamFinalizer(ImageInputStream paramImageInputStream) {
/* 60 */     this.stream = paramImageInputStream;
/*    */   }
/*    */   
/*    */   protected void finalize() throws Throwable {
/*    */     
/* 65 */     try { this.stream.close(); }
/* 66 */     catch (IOException iOException) {  }
/*    */     finally
/* 68 */     { this.stream = null;
/* 69 */       super.finalize(); }
/*    */   
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\imageio\stream\StreamFinalizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */