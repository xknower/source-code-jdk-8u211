/*    */ package sun.tracing;
/*    */ 
/*    */ import com.sun.tracing.ProbeName;
/*    */ import com.sun.tracing.Provider;
/*    */ import java.io.PrintStream;
/*    */ import java.lang.reflect.Method;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class PrintStreamProvider
/*    */   extends ProviderSkeleton
/*    */ {
/*    */   private PrintStream stream;
/*    */   private String providerName;
/*    */   
/*    */   protected ProbeSkeleton createProbe(Method paramMethod) {
/* 67 */     String str = getAnnotationString(paramMethod, (Class)ProbeName.class, paramMethod.getName());
/* 68 */     return new PrintStreamProbe(this, str, paramMethod.getParameterTypes());
/*    */   }
/*    */   
/*    */   PrintStreamProvider(Class<? extends Provider> paramClass, PrintStream paramPrintStream) {
/* 72 */     super(paramClass);
/* 73 */     this.stream = paramPrintStream;
/* 74 */     this.providerName = getProviderName();
/*    */   }
/*    */   
/*    */   PrintStream getStream() {
/* 78 */     return this.stream;
/*    */   }
/*    */   
/*    */   String getName() {
/* 82 */     return this.providerName;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\tracing\PrintStreamProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */