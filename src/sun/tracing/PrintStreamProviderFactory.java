/*    */ package sun.tracing;
/*    */ 
/*    */ import com.sun.tracing.ProviderFactory;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrintStreamProviderFactory
/*    */   extends ProviderFactory
/*    */ {
/*    */   private PrintStream stream;
/*    */   
/*    */   public PrintStreamProviderFactory(PrintStream paramPrintStream) {
/* 51 */     this.stream = paramPrintStream;
/*    */   }
/*    */   
/*    */   public <T extends com.sun.tracing.Provider> T createProvider(Class<T> paramClass) {
/* 55 */     PrintStreamProvider printStreamProvider = new PrintStreamProvider(paramClass, this.stream);
/* 56 */     printStreamProvider.init();
/* 57 */     return printStreamProvider.newProxyInstance();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\tracing\PrintStreamProviderFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */