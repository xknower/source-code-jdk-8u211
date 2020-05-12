/*    */ package com.sun.corba.se.spi.copyobject;
/*    */ 
/*    */ import com.sun.corba.se.impl.copyobject.FallbackObjectCopierImpl;
/*    */ import com.sun.corba.se.impl.copyobject.JavaStreamObjectCopierImpl;
/*    */ import com.sun.corba.se.impl.copyobject.ORBStreamObjectCopierImpl;
/*    */ import com.sun.corba.se.impl.copyobject.ReferenceObjectCopierImpl;
/*    */ import com.sun.corba.se.spi.orb.ORB;
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
/*    */ public abstract class CopyobjectDefaults
/*    */ {
/*    */   public static ObjectCopierFactory makeORBStreamObjectCopierFactory(final ORB orb) {
/* 46 */     return new ObjectCopierFactory()
/*    */       {
/*    */         public ObjectCopier make() {
/* 49 */           return new ORBStreamObjectCopierImpl(orb);
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   public static ObjectCopierFactory makeJavaStreamObjectCopierFactory(final ORB orb) {
/* 56 */     return new ObjectCopierFactory()
/*    */       {
/*    */         public ObjectCopier make() {
/* 59 */           return new JavaStreamObjectCopierImpl(orb);
/*    */         }
/*    */       };
/*    */   }
/*    */   
/* 64 */   private static final ObjectCopier referenceObjectCopier = new ReferenceObjectCopierImpl();
/*    */   
/* 66 */   private static ObjectCopierFactory referenceObjectCopierFactory = new ObjectCopierFactory()
/*    */     {
/*    */       public ObjectCopier make()
/*    */       {
/* 70 */         return CopyobjectDefaults.referenceObjectCopier;
/*    */       }
/*    */     };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ObjectCopierFactory getReferenceObjectCopierFactory() {
/* 79 */     return referenceObjectCopierFactory;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ObjectCopierFactory makeFallbackObjectCopierFactory(final ObjectCopierFactory f1, final ObjectCopierFactory f2) {
/* 91 */     return new ObjectCopierFactory()
/*    */       {
/*    */         public ObjectCopier make() {
/* 94 */           ObjectCopier objectCopier1 = f1.make();
/* 95 */           ObjectCopier objectCopier2 = f2.make();
/* 96 */           return new FallbackObjectCopierImpl(objectCopier1, objectCopier2);
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\copyobject\CopyobjectDefaults.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */