/*     */ package java.rmi.activation;
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
/*     */ public class ActivationException
/*     */   extends Exception
/*     */ {
/*     */   public Throwable detail;
/*     */   private static final long serialVersionUID = -4320118837291406071L;
/*     */   
/*     */   public ActivationException() {
/*  65 */     initCause(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActivationException(String paramString) {
/*  75 */     super(paramString);
/*  76 */     initCause(null);
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
/*     */   public ActivationException(String paramString, Throwable paramThrowable) {
/*  88 */     super(paramString);
/*  89 */     initCause(null);
/*  90 */     this.detail = paramThrowable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessage() {
/* 100 */     if (this.detail == null) {
/* 101 */       return super.getMessage();
/*     */     }
/* 103 */     return super.getMessage() + "; nested exception is: \n\t" + this.detail
/*     */       
/* 105 */       .toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Throwable getCause() {
/* 116 */     return this.detail;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\rmi\activation\ActivationException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */