/*     */ package javax.xml.bind.helpers;
/*     */ 
/*     */ import java.text.MessageFormat;
/*     */ import javax.xml.bind.ValidationEvent;
/*     */ import javax.xml.bind.ValidationEventLocator;
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
/*     */ public class ValidationEventImpl
/*     */   implements ValidationEvent
/*     */ {
/*     */   private int severity;
/*     */   private String message;
/*     */   private Throwable linkedException;
/*     */   private ValidationEventLocator locator;
/*     */   
/*     */   public ValidationEventImpl(int _severity, String _message, ValidationEventLocator _locator) {
/*  64 */     this(_severity, _message, _locator, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValidationEventImpl(int _severity, String _message, ValidationEventLocator _locator, Throwable _linkedException) {
/*  83 */     setSeverity(_severity);
/*  84 */     this.message = _message;
/*  85 */     this.locator = _locator;
/*  86 */     this.linkedException = _linkedException;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSeverity() {
/*  95 */     return this.severity;
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
/*     */   public void setSeverity(int _severity) {
/* 108 */     if (_severity != 0 && _severity != 1 && _severity != 2)
/*     */     {
/*     */       
/* 111 */       throw new IllegalArgumentException(
/* 112 */           Messages.format("ValidationEventImpl.IllegalSeverity"));
/*     */     }
/*     */     
/* 115 */     this.severity = _severity;
/*     */   }
/*     */   
/*     */   public String getMessage() {
/* 119 */     return this.message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMessage(String _message) {
/* 127 */     this.message = _message;
/*     */   }
/*     */   
/*     */   public Throwable getLinkedException() {
/* 131 */     return this.linkedException;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLinkedException(Throwable _linkedException) {
/* 139 */     this.linkedException = _linkedException;
/*     */   }
/*     */   
/*     */   public ValidationEventLocator getLocator() {
/* 143 */     return this.locator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLocator(ValidationEventLocator _locator) {
/* 151 */     this.locator = _locator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*     */     String s;
/* 162 */     switch (getSeverity()) { case 0:
/* 163 */         s = "WARNING"; break;
/* 164 */       case 1: s = "ERROR"; break;
/* 165 */       case 2: s = "FATAL_ERROR"; break;
/* 166 */       default: s = String.valueOf(getSeverity()); break; }
/*     */     
/* 168 */     return MessageFormat.format("[severity={0},message={1},locator={2}]", new Object[] { s, 
/*     */ 
/*     */           
/* 171 */           getMessage(), 
/* 172 */           getLocator() });
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\bind\helpers\ValidationEventImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */