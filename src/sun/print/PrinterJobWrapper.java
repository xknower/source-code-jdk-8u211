/*    */ package sun.print;
/*    */ 
/*    */ import java.awt.print.PrinterJob;
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
/*    */ public class PrinterJobWrapper
/*    */   implements PrintRequestAttribute
/*    */ {
/*    */   private static final long serialVersionUID = -8792124426995707237L;
/*    */   private PrinterJob job;
/*    */   
/*    */   public PrinterJobWrapper(PrinterJob paramPrinterJob) {
/* 38 */     this.job = paramPrinterJob;
/*    */   }
/*    */   
/*    */   public PrinterJob getPrinterJob() {
/* 42 */     return this.job;
/*    */   }
/*    */   
/*    */   public final Class getCategory() {
/* 46 */     return PrinterJobWrapper.class;
/*    */   }
/*    */   
/*    */   public final String getName() {
/* 50 */     return "printerjob-wrapper";
/*    */   }
/*    */   
/*    */   public String toString() {
/* 54 */     return "printerjob-wrapper: " + this.job.toString();
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 58 */     return this.job.hashCode();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\print\PrinterJobWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */