/*     */ package javax.tools;
/*     */ 
/*     */ import java.io.Writer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Locale;
/*     */ import java.util.concurrent.Callable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface DocumentationTool
/*     */   extends Tool, OptionChecker
/*     */ {
/*     */   DocumentationTask getTask(Writer paramWriter, JavaFileManager paramJavaFileManager, DiagnosticListener<? super JavaFileObject> paramDiagnosticListener, Class<?> paramClass, Iterable<String> paramIterable, Iterable<? extends JavaFileObject> paramIterable1);
/*     */   
/*     */   StandardJavaFileManager getStandardFileManager(DiagnosticListener<? super JavaFileObject> paramDiagnosticListener, Locale paramLocale, Charset paramCharset);
/*     */   
/*     */   public static interface DocumentationTask
/*     */     extends Callable<Boolean>
/*     */   {
/*     */     void setLocale(Locale param1Locale);
/*     */     
/*     */     Boolean call();
/*     */   }
/*     */   
/*     */   public enum Location
/*     */     implements JavaFileManager.Location
/*     */   {
/* 159 */     DOCUMENTATION_OUTPUT,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 164 */     DOCLET_PATH,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 169 */     TAGLET_PATH;
/*     */     public String getName() {
/* 171 */       return name();
/*     */     }
/*     */     
/*     */     public boolean isOutputLocation() {
/*     */       // Byte code:
/*     */       //   0: getstatic javax/tools/DocumentationTool$1.$SwitchMap$javax$tools$DocumentationTool$Location : [I
/*     */       //   3: aload_0
/*     */       //   4: invokevirtual ordinal : ()I
/*     */       //   7: iaload
/*     */       //   8: lookupswitch default -> 30, 1 -> 28
/*     */       //   28: iconst_1
/*     */       //   29: ireturn
/*     */       //   30: iconst_0
/*     */       //   31: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #174	-> 0
/*     */       //   #176	-> 28
/*     */       //   #178	-> 30
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\tools\DocumentationTool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */