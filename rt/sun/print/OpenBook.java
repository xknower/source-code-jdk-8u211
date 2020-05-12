/*    */ package sun.print;
/*    */ 
/*    */ import java.awt.print.PageFormat;
/*    */ import java.awt.print.Pageable;
/*    */ import java.awt.print.Printable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class OpenBook
/*    */   implements Pageable
/*    */ {
/*    */   private PageFormat mFormat;
/*    */   private Printable mPainter;
/*    */   
/*    */   OpenBook(PageFormat paramPageFormat, Printable paramPrintable) {
/* 64 */     this.mFormat = paramPageFormat;
/* 65 */     this.mPainter = paramPrintable;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getNumberOfPages() {
/* 72 */     return -1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PageFormat getPageFormat(int paramInt) {
/* 82 */     return this.mFormat;
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
/*    */   public Printable getPrintable(int paramInt) throws IndexOutOfBoundsException {
/* 95 */     return this.mPainter;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\print\OpenBook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */