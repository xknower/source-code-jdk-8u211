/*    */ package sun.dc.path;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathException
/*    */   extends Exception
/*    */ {
/*    */   public static final String BAD_PATH_endPath = "endPath: bad path";
/*    */   public static final String BAD_PATH_useProxy = "useProxy: bad path";
/*    */   public static final String DUMMY = "";
/*    */   
/*    */   public PathException() {}
/*    */   
/*    */   public PathException(String paramString) {
/* 34 */     super(paramString);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\dc\path\PathException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */