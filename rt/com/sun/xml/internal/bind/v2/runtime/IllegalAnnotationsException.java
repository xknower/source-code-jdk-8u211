/*    */ package com.sun.xml.internal.bind.v2.runtime;
/*    */ 
/*    */ import com.sun.xml.internal.bind.v2.model.core.ErrorHandler;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.JAXBException;
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
/*    */ public class IllegalAnnotationsException
/*    */   extends JAXBException
/*    */ {
/*    */   private final List<IllegalAnnotationException> errors;
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public IllegalAnnotationsException(List<IllegalAnnotationException> errors) {
/* 53 */     super(errors.size() + " counts of IllegalAnnotationExceptions");
/* 54 */     assert !errors.isEmpty() : "there must be at least one error";
/* 55 */     this.errors = Collections.unmodifiableList(new ArrayList<>(errors));
/*    */   }
/*    */   
/*    */   public String toString() {
/* 59 */     StringBuilder sb = new StringBuilder(super.toString());
/* 60 */     sb.append('\n');
/*    */     
/* 62 */     for (IllegalAnnotationException error : this.errors) {
/* 63 */       sb.append(error.toString()).append('\n');
/*    */     }
/* 65 */     return sb.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<IllegalAnnotationException> getErrors() {
/* 76 */     return this.errors;
/*    */   }
/*    */   
/*    */   public static class Builder implements ErrorHandler {
/* 80 */     private final List<IllegalAnnotationException> list = new ArrayList<>();
/*    */     public void error(IllegalAnnotationException e) {
/* 82 */       this.list.add(e);
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public void check() throws IllegalAnnotationsException {
/* 89 */       if (this.list.isEmpty())
/*    */         return; 
/* 91 */       throw new IllegalAnnotationsException(this.list);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\runtime\IllegalAnnotationsException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */