/*     */ package javax.xml.parsers;
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
/*     */ public class FactoryConfigurationError
/*     */   extends Error
/*     */ {
/*     */   private static final long serialVersionUID = -827108682472263355L;
/*     */   private Exception exception;
/*     */   
/*     */   public FactoryConfigurationError() {
/*  53 */     this.exception = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FactoryConfigurationError(String msg) {
/*  64 */     super(msg);
/*  65 */     this.exception = null;
/*     */   }
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
/*     */   public FactoryConfigurationError(Exception e) {
/*  78 */     super(e.toString());
/*  79 */     this.exception = e;
/*     */   }
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
/*     */   public FactoryConfigurationError(Exception e, String msg) {
/*  92 */     super(msg);
/*  93 */     this.exception = e;
/*     */   }
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
/*     */   public String getMessage() {
/* 108 */     String message = super.getMessage();
/*     */     
/* 110 */     if (message == null && this.exception != null) {
/* 111 */       return this.exception.getMessage();
/*     */     }
/*     */     
/* 114 */     return message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Exception getException() {
/* 125 */     return this.exception;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Throwable getCause() {
/* 133 */     return this.exception;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\parsers\FactoryConfigurationError.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */