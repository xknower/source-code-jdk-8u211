/*     */ package sun.rmi.transport;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutput;
/*     */ import java.rmi.MarshalException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.UnexpectedException;
/*     */ import java.rmi.UnmarshalException;
/*     */ import java.rmi.dgc.DGC;
/*     */ import java.rmi.dgc.Lease;
/*     */ import java.rmi.dgc.VMID;
/*     */ import java.rmi.server.ObjID;
/*     */ import java.rmi.server.Operation;
/*     */ import java.rmi.server.RemoteCall;
/*     */ import java.rmi.server.RemoteRef;
/*     */ import java.rmi.server.RemoteStub;
/*     */ import java.rmi.server.UID;
/*     */ import java.security.AccessController;
/*     */ import sun.misc.ObjectInputFilter;
/*     */ import sun.rmi.transport.tcp.TCPConnection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DGCImpl_Stub
/*     */   extends RemoteStub
/*     */   implements DGC
/*     */ {
/*  47 */   private static final Operation[] operations = new Operation[] { new Operation("void clean(java.rmi.server.ObjID[], long, java.rmi.dgc.VMID, boolean)"), new Operation("java.rmi.dgc.Lease dirty(java.rmi.server.ObjID[], long, java.rmi.dgc.Lease)") };
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long interfaceHash = -669196253586618813L;
/*     */ 
/*     */ 
/*     */   
/*  55 */   private static int DGCCLIENT_MAX_DEPTH = 6;
/*     */ 
/*     */   
/*  58 */   private static int DGCCLIENT_MAX_ARRAY_SIZE = 10000;
/*     */ 
/*     */ 
/*     */   
/*     */   public DGCImpl_Stub() {}
/*     */ 
/*     */   
/*     */   public DGCImpl_Stub(RemoteRef paramRemoteRef) {
/*  66 */     super(paramRemoteRef);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clean(ObjID[] paramArrayOfObjID, long paramLong, VMID paramVMID, boolean paramBoolean) throws RemoteException {
/*     */     try {
/*  75 */       RemoteCall remoteCall = this.ref.newCall(this, operations, 0, -669196253586618813L);
/*     */       try {
/*  77 */         ObjectOutput objectOutput = remoteCall.getOutputStream();
/*  78 */         objectOutput.writeObject(paramArrayOfObjID);
/*  79 */         objectOutput.writeLong(paramLong);
/*  80 */         objectOutput.writeObject(paramVMID);
/*  81 */         objectOutput.writeBoolean(paramBoolean);
/*  82 */       } catch (IOException iOException) {
/*  83 */         throw new MarshalException("error marshalling arguments", iOException);
/*     */       } 
/*  85 */       this.ref.invoke(remoteCall);
/*  86 */       this.ref.done(remoteCall);
/*  87 */     } catch (RuntimeException runtimeException) {
/*  88 */       throw runtimeException;
/*  89 */     } catch (RemoteException remoteException) {
/*  90 */       throw remoteException;
/*  91 */     } catch (Exception exception) {
/*  92 */       throw new UnexpectedException("undeclared checked exception", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Lease dirty(ObjID[] paramArrayOfObjID, long paramLong, Lease paramLease) throws RemoteException {
/*     */     try {
/*     */       Lease lease;
/* 100 */       RemoteCall remoteCall = this.ref.newCall(this, operations, 1, -669196253586618813L);
/*     */       try {
/* 102 */         ObjectOutput objectOutput = remoteCall.getOutputStream();
/* 103 */         objectOutput.writeObject(paramArrayOfObjID);
/* 104 */         objectOutput.writeLong(paramLong);
/* 105 */         objectOutput.writeObject(paramLease);
/* 106 */       } catch (IOException iOException) {
/* 107 */         throw new MarshalException("error marshalling arguments", iOException);
/*     */       } 
/* 109 */       this.ref.invoke(remoteCall);
/*     */       
/* 111 */       Connection connection = ((StreamRemoteCall)remoteCall).getConnection();
/*     */       try {
/* 113 */         ObjectInput objectInput = remoteCall.getInputStream();
/*     */         
/* 115 */         if (objectInput instanceof ObjectInputStream) {
/*     */ 
/*     */ 
/*     */           
/* 119 */           ObjectInputStream objectInputStream = (ObjectInputStream)objectInput;
/* 120 */           AccessController.doPrivileged(() -> {
/*     */                 ObjectInputFilter.Config.setObjectInputFilter(paramObjectInputStream, DGCImpl_Stub::leaseFilter);
/*     */                 return null;
/*     */               });
/*     */         } 
/* 125 */         lease = (Lease)objectInput.readObject();
/* 126 */       } catch (IOException|ClassNotFoundException iOException) {
/* 127 */         if (connection instanceof TCPConnection)
/*     */         {
/* 129 */           ((TCPConnection)connection).getChannel().free(connection, false);
/*     */         }
/* 131 */         throw new UnmarshalException("error unmarshalling return", iOException);
/*     */       } finally {
/* 133 */         this.ref.done(remoteCall);
/*     */       } 
/* 135 */       return lease;
/* 136 */     } catch (RuntimeException runtimeException) {
/* 137 */       throw runtimeException;
/* 138 */     } catch (RemoteException remoteException) {
/* 139 */       throw remoteException;
/* 140 */     } catch (Exception exception) {
/* 141 */       throw new UnexpectedException("undeclared checked exception", exception);
/*     */     } 
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
/*     */   private static ObjectInputFilter.Status leaseFilter(ObjectInputFilter.FilterInfo paramFilterInfo) {
/* 157 */     if (paramFilterInfo.depth() > DGCCLIENT_MAX_DEPTH) {
/* 158 */       return ObjectInputFilter.Status.REJECTED;
/*     */     }
/* 160 */     Class<?> clazz = paramFilterInfo.serialClass();
/* 161 */     if (clazz != null) {
/* 162 */       while (clazz.isArray()) {
/* 163 */         if (paramFilterInfo.arrayLength() >= 0L && paramFilterInfo.arrayLength() > DGCCLIENT_MAX_ARRAY_SIZE) {
/* 164 */           return ObjectInputFilter.Status.REJECTED;
/*     */         }
/*     */         
/* 167 */         clazz = clazz.getComponentType();
/*     */       } 
/* 169 */       if (clazz.isPrimitive())
/*     */       {
/* 171 */         return ObjectInputFilter.Status.ALLOWED;
/*     */       }
/* 173 */       return (clazz == UID.class || clazz == VMID.class || clazz == Lease.class) ? ObjectInputFilter.Status.ALLOWED : ObjectInputFilter.Status.REJECTED;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 180 */     return ObjectInputFilter.Status.UNDECIDED;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\rmi\transport\DGCImpl_Stub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */