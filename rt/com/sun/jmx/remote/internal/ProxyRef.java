/*    */ package com.sun.jmx.remote.internal;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.lang.reflect.Method;
/*    */ import java.rmi.Remote;
/*    */ import java.rmi.RemoteException;
/*    */ import java.rmi.server.Operation;
/*    */ import java.rmi.server.RemoteCall;
/*    */ import java.rmi.server.RemoteObject;
/*    */ import java.rmi.server.RemoteRef;
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
/*    */ public class ProxyRef
/*    */   implements RemoteRef
/*    */ {
/*    */   private static final long serialVersionUID = -6503061366316814723L;
/*    */   protected RemoteRef ref;
/*    */   
/*    */   public ProxyRef(RemoteRef paramRemoteRef) {
/* 43 */     this.ref = paramRemoteRef;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readExternal(ObjectInput paramObjectInput) throws IOException, ClassNotFoundException {
/* 48 */     this.ref.readExternal(paramObjectInput);
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput paramObjectOutput) throws IOException {
/* 52 */     this.ref.writeExternal(paramObjectOutput);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public void invoke(RemoteCall paramRemoteCall) throws Exception {
/* 60 */     this.ref.invoke(paramRemoteCall);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object invoke(Remote paramRemote, Method paramMethod, Object[] paramArrayOfObject, long paramLong) throws Exception {
/* 65 */     return this.ref.invoke(paramRemote, paramMethod, paramArrayOfObject, paramLong);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public void done(RemoteCall paramRemoteCall) throws RemoteException {
/* 73 */     this.ref.done(paramRemoteCall);
/*    */   }
/*    */   
/*    */   public String getRefClass(ObjectOutput paramObjectOutput) {
/* 77 */     return this.ref.getRefClass(paramObjectOutput);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public RemoteCall newCall(RemoteObject paramRemoteObject, Operation[] paramArrayOfOperation, int paramInt, long paramLong) throws RemoteException {
/* 87 */     return this.ref.newCall(paramRemoteObject, paramArrayOfOperation, paramInt, paramLong);
/*    */   }
/*    */   
/*    */   public boolean remoteEquals(RemoteRef paramRemoteRef) {
/* 91 */     return this.ref.remoteEquals(paramRemoteRef);
/*    */   }
/*    */   
/*    */   public int remoteHashCode() {
/* 95 */     return this.ref.remoteHashCode();
/*    */   }
/*    */   
/*    */   public String remoteToString() {
/* 99 */     return this.ref.remoteToString();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\remote\internal\ProxyRef.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */