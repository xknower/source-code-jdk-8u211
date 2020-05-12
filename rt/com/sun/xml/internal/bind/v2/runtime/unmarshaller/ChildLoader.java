/*    */ package com.sun.xml.internal.bind.v2.runtime.unmarshaller;
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
/*    */ public final class ChildLoader
/*    */ {
/*    */   public final Loader loader;
/*    */   public final Receiver receiver;
/*    */   
/*    */   public ChildLoader(Loader loader, Receiver receiver) {
/* 40 */     assert loader != null;
/* 41 */     this.loader = loader;
/* 42 */     this.receiver = receiver;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\runtim\\unmarshaller\ChildLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */