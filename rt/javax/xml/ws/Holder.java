/*    */ package javax.xml.ws;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Holder<T>
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 2623699057546497185L;
/*    */   public T value;
/*    */   
/*    */   public Holder() {}
/*    */   
/*    */   public Holder(T value) {
/* 56 */     this.value = value;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\ws\Holder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */