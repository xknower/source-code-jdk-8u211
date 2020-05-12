/*    */ package javax.swing.event;
/*    */ 
/*    */ import java.util.EventObject;
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
/*    */ public abstract class CaretEvent
/*    */   extends EventObject
/*    */ {
/*    */   public CaretEvent(Object paramObject) {
/* 53 */     super(paramObject);
/*    */   }
/*    */   
/*    */   public abstract int getDot();
/*    */   
/*    */   public abstract int getMark();
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\event\CaretEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */