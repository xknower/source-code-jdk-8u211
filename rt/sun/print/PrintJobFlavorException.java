/*    */ package sun.print;
/*    */ 
/*    */ import javax.print.DocFlavor;
/*    */ import javax.print.FlavorException;
/*    */ import javax.print.PrintException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class PrintJobFlavorException
/*    */   extends PrintException
/*    */   implements FlavorException
/*    */ {
/*    */   private DocFlavor flavor;
/*    */   
/*    */   PrintJobFlavorException(String paramString, DocFlavor paramDocFlavor) {
/* 39 */     super(paramString);
/* 40 */     this.flavor = paramDocFlavor;
/*    */   }
/*    */   
/*    */   public DocFlavor[] getUnsupportedFlavors() {
/* 44 */     return new DocFlavor[] { this.flavor };
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\print\PrintJobFlavorException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */