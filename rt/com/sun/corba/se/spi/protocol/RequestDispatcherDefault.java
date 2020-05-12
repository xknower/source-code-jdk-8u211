/*     */ package com.sun.corba.se.spi.protocol;
/*     */ 
/*     */ import com.sun.corba.se.impl.protocol.BootstrapServerRequestDispatcher;
/*     */ import com.sun.corba.se.impl.protocol.CorbaClientRequestDispatcherImpl;
/*     */ import com.sun.corba.se.impl.protocol.CorbaServerRequestDispatcherImpl;
/*     */ import com.sun.corba.se.impl.protocol.FullServantCacheLocalCRDImpl;
/*     */ import com.sun.corba.se.impl.protocol.INSServerRequestDispatcher;
/*     */ import com.sun.corba.se.impl.protocol.InfoOnlyServantCacheLocalCRDImpl;
/*     */ import com.sun.corba.se.impl.protocol.JIDLLocalCRDImpl;
/*     */ import com.sun.corba.se.impl.protocol.MinimalServantCacheLocalCRDImpl;
/*     */ import com.sun.corba.se.impl.protocol.POALocalCRDImpl;
/*     */ import com.sun.corba.se.pept.protocol.ClientRequestDispatcher;
/*     */ import com.sun.corba.se.spi.ior.IOR;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RequestDispatcherDefault
/*     */ {
/*     */   public static ClientRequestDispatcher makeClientRequestDispatcher() {
/*  53 */     return new CorbaClientRequestDispatcherImpl();
/*     */   }
/*     */ 
/*     */   
/*     */   public static CorbaServerRequestDispatcher makeServerRequestDispatcher(ORB paramORB) {
/*  58 */     return new CorbaServerRequestDispatcherImpl(paramORB);
/*     */   }
/*     */ 
/*     */   
/*     */   public static CorbaServerRequestDispatcher makeBootstrapServerRequestDispatcher(ORB paramORB) {
/*  63 */     return new BootstrapServerRequestDispatcher(paramORB);
/*     */   }
/*     */ 
/*     */   
/*     */   public static CorbaServerRequestDispatcher makeINSServerRequestDispatcher(ORB paramORB) {
/*  68 */     return new INSServerRequestDispatcher(paramORB);
/*     */   }
/*     */ 
/*     */   
/*     */   public static LocalClientRequestDispatcherFactory makeMinimalServantCacheLocalClientRequestDispatcherFactory(final ORB orb) {
/*  73 */     return new LocalClientRequestDispatcherFactory() {
/*     */         public LocalClientRequestDispatcher create(int param1Int, IOR param1IOR) {
/*  75 */           return new MinimalServantCacheLocalCRDImpl(orb, param1Int, param1IOR);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static LocalClientRequestDispatcherFactory makeInfoOnlyServantCacheLocalClientRequestDispatcherFactory(final ORB orb) {
/*  82 */     return new LocalClientRequestDispatcherFactory() {
/*     */         public LocalClientRequestDispatcher create(int param1Int, IOR param1IOR) {
/*  84 */           return new InfoOnlyServantCacheLocalCRDImpl(orb, param1Int, param1IOR);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static LocalClientRequestDispatcherFactory makeFullServantCacheLocalClientRequestDispatcherFactory(final ORB orb) {
/*  91 */     return new LocalClientRequestDispatcherFactory() {
/*     */         public LocalClientRequestDispatcher create(int param1Int, IOR param1IOR) {
/*  93 */           return new FullServantCacheLocalCRDImpl(orb, param1Int, param1IOR);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static LocalClientRequestDispatcherFactory makeJIDLLocalClientRequestDispatcherFactory(final ORB orb) {
/* 100 */     return new LocalClientRequestDispatcherFactory() {
/*     */         public LocalClientRequestDispatcher create(int param1Int, IOR param1IOR) {
/* 102 */           return new JIDLLocalCRDImpl(orb, param1Int, param1IOR);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static LocalClientRequestDispatcherFactory makePOALocalClientRequestDispatcherFactory(final ORB orb) {
/* 109 */     return new LocalClientRequestDispatcherFactory() {
/*     */         public LocalClientRequestDispatcher create(int param1Int, IOR param1IOR) {
/* 111 */           return new POALocalCRDImpl(orb, param1Int, param1IOR);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\protocol\RequestDispatcherDefault.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */