/*    */ package sun.print;
/*    */ 
/*    */ import javax.print.attribute.Attribute;
/*    */ import javax.print.attribute.PrintRequestAttribute;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DialogOnTop
/*    */   implements PrintRequestAttribute
/*    */ {
/*    */   private static final long serialVersionUID = -1901909867156076547L;
/*    */   long id;
/*    */   
/*    */   public DialogOnTop() {}
/*    */   
/*    */   public DialogOnTop(long paramLong) {
/* 46 */     this.id = paramLong;
/*    */   }
/*    */   
/*    */   public final Class<? extends Attribute> getCategory() {
/* 50 */     return (Class)DialogOnTop.class;
/*    */   }
/*    */   
/*    */   public long getID() {
/* 54 */     return this.id;
/*    */   }
/*    */   
/*    */   public final String getName() {
/* 58 */     return "dialog-on-top";
/*    */   }
/*    */   
/*    */   public String toString() {
/* 62 */     return "dialog-on-top";
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\print\DialogOnTop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */