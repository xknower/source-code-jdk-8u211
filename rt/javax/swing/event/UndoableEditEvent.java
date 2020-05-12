/*    */ package javax.swing.event;
/*    */ 
/*    */ import java.util.EventObject;
/*    */ import javax.swing.undo.UndoableEdit;
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
/*    */ public class UndoableEditEvent
/*    */   extends EventObject
/*    */ {
/*    */   private UndoableEdit myEdit;
/*    */   
/*    */   public UndoableEditEvent(Object paramObject, UndoableEdit paramUndoableEdit) {
/* 55 */     super(paramObject);
/* 56 */     this.myEdit = paramUndoableEdit;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public UndoableEdit getEdit() {
/* 65 */     return this.myEdit;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\event\UndoableEditEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */