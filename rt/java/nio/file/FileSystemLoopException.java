/*    */ package java.nio.file;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FileSystemLoopException
/*    */   extends FileSystemException
/*    */ {
/*    */   private static final long serialVersionUID = 4843039591949217617L;
/*    */   
/*    */   public FileSystemLoopException(String paramString) {
/* 48 */     super(paramString);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\nio\file\FileSystemLoopException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */