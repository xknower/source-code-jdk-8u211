/*     */ package sun.rmi.registry;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.rmi.AccessException;
/*     */ import java.rmi.AlreadyBoundException;
/*     */ import java.rmi.MarshalException;
/*     */ import java.rmi.NotBoundException;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.UnexpectedException;
/*     */ import java.rmi.UnmarshalException;
/*     */ import java.rmi.registry.Registry;
/*     */ import java.rmi.server.Operation;
/*     */ import java.rmi.server.RemoteCall;
/*     */ import java.rmi.server.RemoteRef;
/*     */ import java.rmi.server.RemoteStub;
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
/*     */ public final class RegistryImpl_Stub
/*     */   extends RemoteStub
/*     */   implements Registry, Remote
/*     */ {
/*  35 */   private static final Operation[] operations = new Operation[] { new Operation("void bind(java.lang.String, java.rmi.Remote)"), new Operation("java.lang.String list()[]"), new Operation("java.rmi.Remote lookup(java.lang.String)"), new Operation("void rebind(java.lang.String, java.rmi.Remote)"), new Operation("void unbind(java.lang.String)") };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long interfaceHash = 4905912898345647071L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegistryImpl_Stub() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegistryImpl_Stub(RemoteRef paramRemoteRef) {
/*  51 */     super(paramRemoteRef);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind(String paramString, Remote paramRemote) throws AccessException, AlreadyBoundException, RemoteException {
/*     */     try {
/*  60 */       RemoteCall remoteCall = this.ref.newCall(this, operations, 0, 4905912898345647071L);
/*     */       try {
/*  62 */         ObjectOutput objectOutput = remoteCall.getOutputStream();
/*  63 */         objectOutput.writeObject(paramString);
/*  64 */         objectOutput.writeObject(paramRemote);
/*  65 */       } catch (IOException iOException) {
/*  66 */         throw new MarshalException("error marshalling arguments", iOException);
/*     */       } 
/*  68 */       this.ref.invoke(remoteCall);
/*  69 */       this.ref.done(remoteCall);
/*  70 */     } catch (RuntimeException runtimeException) {
/*  71 */       throw runtimeException;
/*  72 */     } catch (RemoteException remoteException) {
/*  73 */       throw remoteException;
/*  74 */     } catch (AlreadyBoundException alreadyBoundException) {
/*  75 */       throw alreadyBoundException;
/*  76 */     } catch (Exception exception) {
/*  77 */       throw new UnexpectedException("undeclared checked exception", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] list() throws AccessException, RemoteException {
/*     */     try {
/*     */       String[] arrayOfString;
/*  85 */       RemoteCall remoteCall = this.ref.newCall(this, operations, 1, 4905912898345647071L);
/*  86 */       this.ref.invoke(remoteCall);
/*     */       
/*     */       try {
/*  89 */         ObjectInput objectInput = remoteCall.getInputStream();
/*  90 */         arrayOfString = (String[])objectInput.readObject();
/*  91 */       } catch (IOException iOException) {
/*  92 */         throw new UnmarshalException("error unmarshalling return", iOException);
/*  93 */       } catch (ClassNotFoundException classNotFoundException) {
/*  94 */         throw new UnmarshalException("error unmarshalling return", classNotFoundException);
/*     */       } finally {
/*  96 */         this.ref.done(remoteCall);
/*     */       } 
/*  98 */       return arrayOfString;
/*  99 */     } catch (RuntimeException runtimeException) {
/* 100 */       throw runtimeException;
/* 101 */     } catch (RemoteException remoteException) {
/* 102 */       throw remoteException;
/* 103 */     } catch (Exception exception) {
/* 104 */       throw new UnexpectedException("undeclared checked exception", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Remote lookup(String paramString) throws AccessException, NotBoundException, RemoteException {
/*     */     try {
/*     */       Remote remote;
/* 112 */       RemoteCall remoteCall = this.ref.newCall(this, operations, 2, 4905912898345647071L);
/*     */       try {
/* 114 */         ObjectOutput objectOutput = remoteCall.getOutputStream();
/* 115 */         objectOutput.writeObject(paramString);
/* 116 */       } catch (IOException iOException) {
/* 117 */         throw new MarshalException("error marshalling arguments", iOException);
/*     */       } 
/* 119 */       this.ref.invoke(remoteCall);
/*     */       
/*     */       try {
/* 122 */         ObjectInput objectInput = remoteCall.getInputStream();
/* 123 */         remote = (Remote)objectInput.readObject();
/* 124 */       } catch (IOException iOException) {
/* 125 */         throw new UnmarshalException("error unmarshalling return", iOException);
/* 126 */       } catch (ClassNotFoundException classNotFoundException) {
/* 127 */         throw new UnmarshalException("error unmarshalling return", classNotFoundException);
/*     */       } finally {
/* 129 */         this.ref.done(remoteCall);
/*     */       } 
/* 131 */       return remote;
/* 132 */     } catch (RuntimeException runtimeException) {
/* 133 */       throw runtimeException;
/* 134 */     } catch (RemoteException remoteException) {
/* 135 */       throw remoteException;
/* 136 */     } catch (NotBoundException notBoundException) {
/* 137 */       throw notBoundException;
/* 138 */     } catch (Exception exception) {
/* 139 */       throw new UnexpectedException("undeclared checked exception", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void rebind(String paramString, Remote paramRemote) throws AccessException, RemoteException {
/*     */     try {
/* 147 */       RemoteCall remoteCall = this.ref.newCall(this, operations, 3, 4905912898345647071L);
/*     */       try {
/* 149 */         ObjectOutput objectOutput = remoteCall.getOutputStream();
/* 150 */         objectOutput.writeObject(paramString);
/* 151 */         objectOutput.writeObject(paramRemote);
/* 152 */       } catch (IOException iOException) {
/* 153 */         throw new MarshalException("error marshalling arguments", iOException);
/*     */       } 
/* 155 */       this.ref.invoke(remoteCall);
/* 156 */       this.ref.done(remoteCall);
/* 157 */     } catch (RuntimeException runtimeException) {
/* 158 */       throw runtimeException;
/* 159 */     } catch (RemoteException remoteException) {
/* 160 */       throw remoteException;
/* 161 */     } catch (Exception exception) {
/* 162 */       throw new UnexpectedException("undeclared checked exception", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void unbind(String paramString) throws AccessException, NotBoundException, RemoteException {
/*     */     try {
/* 170 */       RemoteCall remoteCall = this.ref.newCall(this, operations, 4, 4905912898345647071L);
/*     */       try {
/* 172 */         ObjectOutput objectOutput = remoteCall.getOutputStream();
/* 173 */         objectOutput.writeObject(paramString);
/* 174 */       } catch (IOException iOException) {
/* 175 */         throw new MarshalException("error marshalling arguments", iOException);
/*     */       } 
/* 177 */       this.ref.invoke(remoteCall);
/* 178 */       this.ref.done(remoteCall);
/* 179 */     } catch (RuntimeException runtimeException) {
/* 180 */       throw runtimeException;
/* 181 */     } catch (RemoteException remoteException) {
/* 182 */       throw remoteException;
/* 183 */     } catch (NotBoundException notBoundException) {
/* 184 */       throw notBoundException;
/* 185 */     } catch (Exception exception) {
/* 186 */       throw new UnexpectedException("undeclared checked exception", exception);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\rmi\registry\RegistryImpl_Stub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */