/*    */ package java.awt;
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
/*    */ interface EventFilter
/*    */ {
/*    */   FilterAction acceptEvent(AWTEvent paramAWTEvent);
/*    */   
/*    */   public enum FilterAction
/*    */   {
/* 40 */     ACCEPT,
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 46 */     REJECT,
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
/* 57 */     ACCEPT_IMMEDIATELY;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\EventFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */