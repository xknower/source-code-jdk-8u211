/*     */ package sun.rmi.registry;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.rmi.MarshalException;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.UnmarshalException;
/*     */ import java.rmi.server.Operation;
/*     */ import java.rmi.server.RemoteCall;
/*     */ import java.rmi.server.Skeleton;
/*     */ import java.rmi.server.SkeletonMismatchException;
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
/*     */ public final class RegistryImpl_Skel
/*     */   implements Skeleton
/*     */ {
/*  45 */   private static final Operation[] operations = new Operation[] { new Operation("void bind(java.lang.String, java.rmi.Remote)"), new Operation("java.lang.String list()[]"), new Operation("java.rmi.Remote lookup(java.lang.String)"), new Operation("void rebind(java.lang.String, java.rmi.Remote)"), new Operation("void unbind(java.lang.String)") };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long interfaceHash = 4905912898345647071L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Operation[] getOperations() {
/*  56 */     return (Operation[])operations.clone();
/*     */   }
/*     */   public void dispatch(Remote paramRemote, RemoteCall paramRemoteCall, int paramInt, long paramLong) throws Exception {
/*     */     String str2, arrayOfString[], str1;
/*     */     Remote remote;
/*  61 */     if (paramInt < 0) {
/*  62 */       if (paramLong == 7583982177005850366L) {
/*  63 */         paramInt = 0;
/*  64 */       } else if (paramLong == 2571371476350237748L) {
/*  65 */         paramInt = 1;
/*  66 */       } else if (paramLong == -7538657168040752697L) {
/*  67 */         paramInt = 2;
/*  68 */       } else if (paramLong == -8381844669958460146L) {
/*  69 */         paramInt = 3;
/*  70 */       } else if (paramLong == 7305022919901907578L) {
/*  71 */         paramInt = 4;
/*     */       } else {
/*  73 */         throw new UnmarshalException("invalid method hash");
/*     */       }
/*     */     
/*  76 */     } else if (paramLong != 4905912898345647071L) {
/*  77 */       throw new SkeletonMismatchException("interface hash mismatch");
/*     */     } 
/*     */     
/*  80 */     RegistryImpl registryImpl = (RegistryImpl)paramRemote;
/*  81 */     switch (paramInt) {
/*     */ 
/*     */       
/*     */       case 0:
/*  85 */         RegistryImpl.checkAccess("Registry.bind");
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/*  90 */           ObjectInput objectInput = paramRemoteCall.getInputStream();
/*  91 */           str2 = (String)objectInput.readObject();
/*  92 */           remote = (Remote)objectInput.readObject();
/*  93 */         } catch (IOException|ClassNotFoundException iOException) {
/*  94 */           throw new UnmarshalException("error unmarshalling arguments", iOException);
/*     */         } finally {
/*  96 */           paramRemoteCall.releaseInputStream();
/*     */         } 
/*  98 */         registryImpl.bind(str2, remote);
/*     */         try {
/* 100 */           paramRemoteCall.getResultStream(true);
/* 101 */         } catch (IOException iOException) {
/* 102 */           throw new MarshalException("error marshalling return", iOException);
/*     */         } 
/*     */         return;
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 109 */         paramRemoteCall.releaseInputStream();
/* 110 */         arrayOfString = registryImpl.list();
/*     */         try {
/* 112 */           ObjectOutput objectOutput = paramRemoteCall.getResultStream(true);
/* 113 */           objectOutput.writeObject(arrayOfString);
/* 114 */         } catch (IOException iOException) {
/* 115 */           throw new MarshalException("error marshalling return", iOException);
/*     */         } 
/*     */         return;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 2:
/*     */         try {
/* 124 */           ObjectInput objectInput = paramRemoteCall.getInputStream();
/* 125 */           str1 = (String)objectInput.readObject();
/* 126 */         } catch (IOException|ClassNotFoundException iOException) {
/* 127 */           throw new UnmarshalException("error unmarshalling arguments", iOException);
/*     */         } finally {
/* 129 */           paramRemoteCall.releaseInputStream();
/*     */         } 
/* 131 */         remote = registryImpl.lookup(str1);
/*     */         try {
/* 133 */           ObjectOutput objectOutput = paramRemoteCall.getResultStream(true);
/* 134 */           objectOutput.writeObject(remote);
/* 135 */         } catch (IOException iOException) {
/* 136 */           throw new MarshalException("error marshalling return", iOException);
/*     */         } 
/*     */         return;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 3:
/* 144 */         RegistryImpl.checkAccess("Registry.rebind");
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 149 */           ObjectInput objectInput = paramRemoteCall.getInputStream();
/* 150 */           str1 = (String)objectInput.readObject();
/* 151 */           remote = (Remote)objectInput.readObject();
/* 152 */         } catch (IOException|ClassNotFoundException iOException) {
/* 153 */           throw new UnmarshalException("error unmarshalling arguments", iOException);
/*     */         } finally {
/* 155 */           paramRemoteCall.releaseInputStream();
/*     */         } 
/* 157 */         registryImpl.rebind(str1, remote);
/*     */         try {
/* 159 */           paramRemoteCall.getResultStream(true);
/* 160 */         } catch (IOException iOException) {
/* 161 */           throw new MarshalException("error marshalling return", iOException);
/*     */         } 
/*     */         return;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 4:
/* 169 */         RegistryImpl.checkAccess("Registry.unbind");
/*     */ 
/*     */         
/*     */         try {
/* 173 */           ObjectInput objectInput = paramRemoteCall.getInputStream();
/* 174 */           str1 = (String)objectInput.readObject();
/* 175 */         } catch (IOException|ClassNotFoundException iOException) {
/* 176 */           throw new UnmarshalException("error unmarshalling arguments", iOException);
/*     */         } finally {
/* 178 */           paramRemoteCall.releaseInputStream();
/*     */         } 
/* 180 */         registryImpl.unbind(str1);
/*     */         try {
/* 182 */           paramRemoteCall.getResultStream(true);
/* 183 */         } catch (IOException iOException) {
/* 184 */           throw new MarshalException("error marshalling return", iOException);
/*     */         } 
/*     */         return;
/*     */     } 
/*     */ 
/*     */     
/* 190 */     throw new UnmarshalException("invalid method number");
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\rmi\registry\RegistryImpl_Skel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */