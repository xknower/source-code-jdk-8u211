/*    */ package com.sun.corba.se.impl.copyobject;
/*    */ 
/*    */ import com.sun.corba.se.spi.copyobject.ObjectCopier;
/*    */ import com.sun.corba.se.spi.copyobject.ReflectiveCopyException;
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
/*    */ public class FallbackObjectCopierImpl
/*    */   implements ObjectCopier
/*    */ {
/*    */   private ObjectCopier first;
/*    */   private ObjectCopier second;
/*    */   
/*    */   public FallbackObjectCopierImpl(ObjectCopier paramObjectCopier1, ObjectCopier paramObjectCopier2) {
/* 42 */     this.first = paramObjectCopier1;
/* 43 */     this.second = paramObjectCopier2;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object copy(Object paramObject) throws ReflectiveCopyException {
/*    */     try {
/* 49 */       return this.first.copy(paramObject);
/* 50 */     } catch (ReflectiveCopyException reflectiveCopyException) {
/*    */       
/* 52 */       return this.second.copy(paramObject);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\copyobject\FallbackObjectCopierImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */