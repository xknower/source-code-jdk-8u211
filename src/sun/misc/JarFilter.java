/*    */ package sun.misc;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FilenameFilter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JarFilter
/*    */   implements FilenameFilter
/*    */ {
/*    */   public boolean accept(File paramFile, String paramString) {
/* 42 */     String str = paramString.toLowerCase();
/* 43 */     return (str.endsWith(".jar") || str.endsWith(".zip"));
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\misc\JarFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */