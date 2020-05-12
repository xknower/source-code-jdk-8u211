/*    */ package javax.swing.tree;
/*    */ 
/*    */ import javax.swing.event.TreeExpansionEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExpandVetoException
/*    */   extends Exception
/*    */ {
/*    */   protected TreeExpansionEvent event;
/*    */   
/*    */   public ExpandVetoException(TreeExpansionEvent paramTreeExpansionEvent) {
/* 50 */     this(paramTreeExpansionEvent, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ExpandVetoException(TreeExpansionEvent paramTreeExpansionEvent, String paramString) {
/* 60 */     super(paramString);
/* 61 */     this.event = paramTreeExpansionEvent;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\tree\ExpandVetoException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */