/*    */ package java.io;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NotSerializableException
/*    */   extends ObjectStreamException
/*    */ {
/*    */   private static final long serialVersionUID = 2906642554793891381L;
/*    */   
/*    */   public NotSerializableException(String paramString) {
/* 46 */     super(paramString);
/*    */   }
/*    */   
/*    */   public NotSerializableException() {}
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\io\NotSerializableException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */