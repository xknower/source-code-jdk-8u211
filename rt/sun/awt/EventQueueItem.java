/*    */ package sun.awt;
/*    */ 
/*    */ import java.awt.AWTEvent;
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
/*    */ public class EventQueueItem
/*    */ {
/*    */   public AWTEvent event;
/*    */   public EventQueueItem next;
/*    */   
/*    */   public EventQueueItem(AWTEvent paramAWTEvent) {
/* 35 */     this.event = paramAWTEvent;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\EventQueueItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */