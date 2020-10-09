/*     */ package sun.rmi.transport;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.rmi.MarshalException;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.UnmarshalException;
/*     */ import java.rmi.dgc.Lease;
/*     */ import java.rmi.dgc.VMID;
/*     */ import java.rmi.server.ObjID;
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
/*     */ public final class DGCImpl_Skel
/*     */   implements Skeleton
/*     */ {
/*  35 */   private static final Operation[] operations = new Operation[] { new Operation("void clean(java.rmi.server.ObjID[], long, java.rmi.dgc.VMID, boolean)"), new Operation("java.rmi.dgc.Lease dirty(java.rmi.server.ObjID[], long, java.rmi.dgc.Lease)") };
/*     */ 
/*     */   
/*     */   private static final long interfaceHash = -669196253586618813L;
/*     */ 
/*     */ 
/*     */   
/*     */   public Operation[] getOperations() {
/*  43 */     return (Operation[])operations.clone(); } public void dispatch(Remote paramRemote, RemoteCall paramRemoteCall, int paramInt, long paramLong) throws Exception { ObjID[] arrayOfObjID; long l;
/*     */     VMID vMID;
/*     */     Lease lease1;
/*     */     boolean bool;
/*     */     Lease lease2;
/*  48 */     if (paramLong != -669196253586618813L) {
/*  49 */       throw new SkeletonMismatchException("interface hash mismatch");
/*     */     }
/*  51 */     DGCImpl dGCImpl = (DGCImpl)paramRemote;
/*  52 */     switch (paramInt) {
/*     */       
/*     */       case 0:
/*     */         
/*     */         try {
/*     */ 
/*     */ 
/*     */           
/*  60 */           ObjectInput objectInput = paramRemoteCall.getInputStream();
/*  61 */           arrayOfObjID = (ObjID[])objectInput.readObject();
/*  62 */           l = objectInput.readLong();
/*  63 */           vMID = (VMID)objectInput.readObject();
/*  64 */           bool = objectInput.readBoolean();
/*  65 */         } catch (IOException iOException) {
/*  66 */           throw new UnmarshalException("error unmarshalling arguments", iOException);
/*  67 */         } catch (ClassNotFoundException classNotFoundException) {
/*  68 */           throw new UnmarshalException("error unmarshalling arguments", classNotFoundException);
/*     */         } finally {
/*  70 */           paramRemoteCall.releaseInputStream();
/*     */         } 
/*  72 */         dGCImpl.clean(arrayOfObjID, l, vMID, bool);
/*     */         try {
/*  74 */           paramRemoteCall.getResultStream(true);
/*  75 */         } catch (IOException iOException) {
/*  76 */           throw new MarshalException("error marshalling return", iOException);
/*     */         } 
/*     */         return;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/*     */         try {
/*  87 */           ObjectInput objectInput = paramRemoteCall.getInputStream();
/*  88 */           arrayOfObjID = (ObjID[])objectInput.readObject();
/*  89 */           l = objectInput.readLong();
/*  90 */           lease1 = (Lease)objectInput.readObject();
/*  91 */         } catch (IOException iOException) {
/*  92 */           throw new UnmarshalException("error unmarshalling arguments", iOException);
/*  93 */         } catch (ClassNotFoundException classNotFoundException) {
/*  94 */           throw new UnmarshalException("error unmarshalling arguments", classNotFoundException);
/*     */         } finally {
/*  96 */           paramRemoteCall.releaseInputStream();
/*     */         } 
/*  98 */         lease2 = dGCImpl.dirty(arrayOfObjID, l, lease1);
/*     */         try {
/* 100 */           ObjectOutput objectOutput = paramRemoteCall.getResultStream(true);
/* 101 */           objectOutput.writeObject(lease2);
/* 102 */         } catch (IOException iOException) {
/* 103 */           throw new MarshalException("error marshalling return", iOException);
/*     */         } 
/*     */         return;
/*     */     } 
/*     */ 
/*     */     
/* 109 */     throw new UnmarshalException("invalid method number"); }
/*     */ 
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\rmi\transport\DGCImpl_Skel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */