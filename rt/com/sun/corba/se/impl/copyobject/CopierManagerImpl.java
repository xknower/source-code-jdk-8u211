/*    */ package com.sun.corba.se.impl.copyobject;
/*    */ 
/*    */ import com.sun.corba.se.impl.orbutil.DenseIntMapImpl;
/*    */ import com.sun.corba.se.spi.copyobject.CopierManager;
/*    */ import com.sun.corba.se.spi.copyobject.ObjectCopierFactory;
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
/*    */ public class CopierManagerImpl
/*    */   implements CopierManager
/*    */ {
/*    */   private int defaultId;
/*    */   private DenseIntMapImpl map;
/*    */   private ORB orb;
/*    */   
/*    */   public CopierManagerImpl(ORB paramORB) {
/* 44 */     this.defaultId = 0;
/* 45 */     this.map = new DenseIntMapImpl();
/* 46 */     this.orb = paramORB;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setDefaultId(int paramInt) {
/* 51 */     this.defaultId = paramInt;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDefaultId() {
/* 56 */     return this.defaultId;
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectCopierFactory getObjectCopierFactory(int paramInt) {
/* 61 */     return (ObjectCopierFactory)this.map.get(paramInt);
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectCopierFactory getDefaultObjectCopierFactory() {
/* 66 */     return (ObjectCopierFactory)this.map.get(this.defaultId);
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerObjectCopierFactory(ObjectCopierFactory paramObjectCopierFactory, int paramInt) {
/* 71 */     this.map.set(paramInt, paramObjectCopierFactory);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\copyobject\CopierManagerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */