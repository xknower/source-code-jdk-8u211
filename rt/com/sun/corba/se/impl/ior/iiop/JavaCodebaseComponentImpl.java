/*    */ package com.sun.corba.se.impl.ior.iiop;
/*    */ 
/*    */ import com.sun.corba.se.spi.ior.TaggedComponentBase;
/*    */ import com.sun.corba.se.spi.ior.iiop.JavaCodebaseComponent;
/*    */ import org.omg.CORBA_2_3.portable.OutputStream;
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
/*    */ public class JavaCodebaseComponentImpl
/*    */   extends TaggedComponentBase
/*    */   implements JavaCodebaseComponent
/*    */ {
/*    */   private String URLs;
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 46 */     if (paramObject == null) {
/* 47 */       return false;
/*    */     }
/* 49 */     if (!(paramObject instanceof JavaCodebaseComponentImpl)) {
/* 50 */       return false;
/*    */     }
/* 52 */     JavaCodebaseComponentImpl javaCodebaseComponentImpl = (JavaCodebaseComponentImpl)paramObject;
/*    */     
/* 54 */     return this.URLs.equals(javaCodebaseComponentImpl.getURLs());
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 59 */     return this.URLs.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 64 */     return "JavaCodebaseComponentImpl[URLs=" + this.URLs + "]";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getURLs() {
/* 69 */     return this.URLs;
/*    */   }
/*    */ 
/*    */   
/*    */   public JavaCodebaseComponentImpl(String paramString) {
/* 74 */     this.URLs = paramString;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeContents(OutputStream paramOutputStream) {
/* 79 */     paramOutputStream.write_string(this.URLs);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 84 */     return 25;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\ior\iiop\JavaCodebaseComponentImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */