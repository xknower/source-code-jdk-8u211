/*    */ package com.sun.xml.internal.bind.marshaller;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoEscapeHandler
/*    */   implements CharacterEscapeHandler
/*    */ {
/* 39 */   public static final NoEscapeHandler theInstance = new NoEscapeHandler();
/*    */ 
/*    */   
/*    */   public void escape(char[] ch, int start, int length, boolean isAttVal, Writer out) throws IOException {
/* 43 */     out.write(ch, start, length);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\marshaller\NoEscapeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */