/*    */ package sun.applet;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AppletThreadGroup
/*    */   extends ThreadGroup
/*    */ {
/*    */   public AppletThreadGroup(String paramString) {
/* 43 */     this(Thread.currentThread().getThreadGroup(), paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AppletThreadGroup(ThreadGroup paramThreadGroup, String paramString) {
/* 61 */     super(paramThreadGroup, paramString);
/* 62 */     setMaxPriority(4);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\applet\AppletThreadGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */