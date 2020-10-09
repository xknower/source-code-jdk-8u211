/*     */ package sun.rmi.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.lang.reflect.Method;
/*     */ import java.rmi.MarshalException;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.server.Operation;
/*     */ import java.rmi.server.RemoteCall;
/*     */ import java.rmi.server.RemoteObject;
/*     */ import java.rmi.server.RemoteRef;
/*     */ import java.security.AccessController;
/*     */ import sun.rmi.runtime.Log;
/*     */ import sun.rmi.transport.Connection;
/*     */ import sun.rmi.transport.LiveRef;
/*     */ import sun.rmi.transport.StreamRemoteCall;
/*     */ import sun.security.action.GetBooleanAction;
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
/*     */ 
/*     */ public class UnicastRef
/*     */   implements RemoteRef
/*     */ {
/*  59 */   public static final Log clientRefLog = Log.getLog("sun.rmi.client.ref", "transport", Util.logLevel);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   public static final Log clientCallLog = Log.getLog("sun.rmi.client.call", "RMI", (
/*  66 */       (Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("sun.rmi.client.logCalls"))).booleanValue());
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 8258372400816541186L;
/*     */ 
/*     */   
/*     */   protected LiveRef ref;
/*     */ 
/*     */ 
/*     */   
/*     */   public UnicastRef() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public UnicastRef(LiveRef paramLiveRef) {
/*  82 */     this.ref = paramLiveRef;
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
/*     */   public LiveRef getLiveRef() {
/*  94 */     return this.ref;
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
/*     */   public Object invoke(Remote paramRemote, Method paramMethod, Object[] paramArrayOfObject, long paramLong) throws Exception {
/* 121 */     if (clientRefLog.isLoggable(Log.VERBOSE)) {
/* 122 */       clientRefLog.log(Log.VERBOSE, "method: " + paramMethod);
/*     */     }
/*     */     
/* 125 */     if (clientCallLog.isLoggable(Log.VERBOSE)) {
/* 126 */       logClientCall(paramRemote, paramMethod);
/*     */     }
/*     */     
/* 129 */     Connection connection = this.ref.getChannel().newConnection();
/* 130 */     StreamRemoteCall streamRemoteCall = null;
/* 131 */     boolean bool1 = true;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     boolean bool2 = false;
/*     */     
/*     */     try {
/* 139 */       if (clientRefLog.isLoggable(Log.VERBOSE)) {
/* 140 */         clientRefLog.log(Log.VERBOSE, "opnum = " + paramLong);
/*     */       }
/*     */ 
/*     */       
/* 144 */       streamRemoteCall = new StreamRemoteCall(connection, this.ref.getObjID(), -1, paramLong);
/*     */ 
/*     */       
/*     */       try {
/* 148 */         ObjectOutput objectOutput = streamRemoteCall.getOutputStream();
/* 149 */         marshalCustomCallData(objectOutput);
/* 150 */         Class[] arrayOfClass = paramMethod.getParameterTypes();
/* 151 */         for (byte b = 0; b < arrayOfClass.length; b++) {
/* 152 */           marshalValue(arrayOfClass[b], paramArrayOfObject[b], objectOutput);
/*     */         }
/* 154 */       } catch (IOException iOException) {
/* 155 */         clientRefLog.log(Log.BRIEF, "IOException marshalling arguments: ", iOException);
/*     */         
/* 157 */         throw new MarshalException("error marshalling arguments", iOException);
/*     */       } 
/*     */ 
/*     */       
/* 161 */       streamRemoteCall.executeCall();
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
/*     */     }
/* 209 */     catch (RuntimeException runtimeException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 218 */       if (streamRemoteCall == null || streamRemoteCall
/* 219 */         .getServerException() != runtimeException)
/*     */       {
/* 221 */         bool1 = false;
/*     */       }
/* 223 */       throw runtimeException;
/*     */     }
/* 225 */     catch (RemoteException remoteException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 233 */       bool1 = false;
/* 234 */       throw remoteException;
/*     */     }
/* 236 */     catch (Error error) {
/*     */ 
/*     */ 
/*     */       
/* 240 */       bool1 = false;
/* 241 */       throw error;
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 248 */       if (!bool2) {
/* 249 */         if (clientRefLog.isLoggable(Log.BRIEF)) {
/* 250 */           clientRefLog.log(Log.BRIEF, "free connection (reuse = " + bool1 + ")");
/*     */         }
/*     */         
/* 253 */         this.ref.getChannel().free(connection, bool1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void marshalCustomCallData(ObjectOutput paramObjectOutput) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void marshalValue(Class<?> paramClass, Object paramObject, ObjectOutput paramObjectOutput) throws IOException {
/* 269 */     if (paramClass.isPrimitive()) {
/* 270 */       if (paramClass == int.class) {
/* 271 */         paramObjectOutput.writeInt(((Integer)paramObject).intValue());
/* 272 */       } else if (paramClass == boolean.class) {
/* 273 */         paramObjectOutput.writeBoolean(((Boolean)paramObject).booleanValue());
/* 274 */       } else if (paramClass == byte.class) {
/* 275 */         paramObjectOutput.writeByte(((Byte)paramObject).byteValue());
/* 276 */       } else if (paramClass == char.class) {
/* 277 */         paramObjectOutput.writeChar(((Character)paramObject).charValue());
/* 278 */       } else if (paramClass == short.class) {
/* 279 */         paramObjectOutput.writeShort(((Short)paramObject).shortValue());
/* 280 */       } else if (paramClass == long.class) {
/* 281 */         paramObjectOutput.writeLong(((Long)paramObject).longValue());
/* 282 */       } else if (paramClass == float.class) {
/* 283 */         paramObjectOutput.writeFloat(((Float)paramObject).floatValue());
/* 284 */       } else if (paramClass == double.class) {
/* 285 */         paramObjectOutput.writeDouble(((Double)paramObject).doubleValue());
/*     */       } else {
/* 287 */         throw new Error("Unrecognized primitive type: " + paramClass);
/*     */       } 
/*     */     } else {
/* 290 */       paramObjectOutput.writeObject(paramObject);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Object unmarshalValue(Class<?> paramClass, ObjectInput paramObjectInput) throws IOException, ClassNotFoundException {
/* 301 */     if (paramClass.isPrimitive()) {
/* 302 */       if (paramClass == int.class)
/* 303 */         return Integer.valueOf(paramObjectInput.readInt()); 
/* 304 */       if (paramClass == boolean.class)
/* 305 */         return Boolean.valueOf(paramObjectInput.readBoolean()); 
/* 306 */       if (paramClass == byte.class)
/* 307 */         return Byte.valueOf(paramObjectInput.readByte()); 
/* 308 */       if (paramClass == char.class)
/* 309 */         return Character.valueOf(paramObjectInput.readChar()); 
/* 310 */       if (paramClass == short.class)
/* 311 */         return Short.valueOf(paramObjectInput.readShort()); 
/* 312 */       if (paramClass == long.class)
/* 313 */         return Long.valueOf(paramObjectInput.readLong()); 
/* 314 */       if (paramClass == float.class)
/* 315 */         return Float.valueOf(paramObjectInput.readFloat()); 
/* 316 */       if (paramClass == double.class) {
/* 317 */         return Double.valueOf(paramObjectInput.readDouble());
/*     */       }
/* 319 */       throw new Error("Unrecognized primitive type: " + paramClass);
/*     */     } 
/*     */     
/* 322 */     return paramObjectInput.readObject();
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
/*     */   public RemoteCall newCall(RemoteObject paramRemoteObject, Operation[] paramArrayOfOperation, int paramInt, long paramLong) throws RemoteException {
/* 336 */     clientRefLog.log(Log.BRIEF, "get connection");
/*     */     
/* 338 */     Connection connection = this.ref.getChannel().newConnection();
/*     */     try {
/* 340 */       clientRefLog.log(Log.VERBOSE, "create call context");
/*     */ 
/*     */       
/* 343 */       if (clientCallLog.isLoggable(Log.VERBOSE)) {
/* 344 */         logClientCall(paramRemoteObject, paramArrayOfOperation[paramInt]);
/*     */       }
/*     */ 
/*     */       
/* 348 */       StreamRemoteCall streamRemoteCall = new StreamRemoteCall(connection, this.ref.getObjID(), paramInt, paramLong);
/*     */       try {
/* 350 */         marshalCustomCallData(streamRemoteCall.getOutputStream());
/* 351 */       } catch (IOException iOException) {
/* 352 */         throw new MarshalException("error marshaling custom call data");
/*     */       } 
/*     */       
/* 355 */       return streamRemoteCall;
/* 356 */     } catch (RemoteException remoteException) {
/* 357 */       this.ref.getChannel().free(connection, false);
/* 358 */       throw remoteException;
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
/*     */   public void invoke(RemoteCall paramRemoteCall) throws Exception {
/*     */     try {
/* 373 */       clientRefLog.log(Log.VERBOSE, "execute call");
/*     */       
/* 375 */       paramRemoteCall.executeCall();
/*     */     }
/* 377 */     catch (RemoteException remoteException) {
/*     */ 
/*     */ 
/*     */       
/* 381 */       clientRefLog.log(Log.BRIEF, "exception: ", remoteException);
/* 382 */       free(paramRemoteCall, false);
/* 383 */       throw remoteException;
/*     */     }
/* 385 */     catch (Error error) {
/*     */ 
/*     */ 
/*     */       
/* 389 */       clientRefLog.log(Log.BRIEF, "error: ", error);
/* 390 */       free(paramRemoteCall, false);
/* 391 */       throw error;
/*     */     }
/* 393 */     catch (RuntimeException runtimeException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 399 */       clientRefLog.log(Log.BRIEF, "exception: ", runtimeException);
/* 400 */       free(paramRemoteCall, false);
/* 401 */       throw runtimeException;
/*     */     }
/* 403 */     catch (Exception exception) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 408 */       clientRefLog.log(Log.BRIEF, "exception: ", exception);
/* 409 */       free(paramRemoteCall, true);
/*     */       
/* 411 */       throw exception;
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
/*     */   private void free(RemoteCall paramRemoteCall, boolean paramBoolean) throws RemoteException {
/* 426 */     Connection connection = ((StreamRemoteCall)paramRemoteCall).getConnection();
/* 427 */     this.ref.getChannel().free(connection, paramBoolean);
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
/*     */   public void done(RemoteCall paramRemoteCall) throws RemoteException {
/* 441 */     clientRefLog.log(Log.BRIEF, "free connection (reuse = true)");
/*     */ 
/*     */     
/* 444 */     free(paramRemoteCall, true);
/*     */     
/*     */     try {
/* 447 */       paramRemoteCall.done();
/* 448 */     } catch (IOException iOException) {}
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
/*     */   void logClientCall(Object paramObject1, Object paramObject2) {
/* 462 */     clientCallLog.log(Log.VERBOSE, "outbound call: " + this.ref + " : " + paramObject1
/* 463 */         .getClass().getName() + this.ref
/* 464 */         .getObjID().toString() + ": " + paramObject2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRefClass(ObjectOutput paramObjectOutput) {
/* 471 */     return "UnicastRef";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeExternal(ObjectOutput paramObjectOutput) throws IOException {
/* 478 */     this.ref.write(paramObjectOutput, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readExternal(ObjectInput paramObjectInput) throws IOException, ClassNotFoundException {
/* 489 */     this.ref = LiveRef.read(paramObjectInput, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String remoteToString() {
/* 497 */     return Util.getUnqualifiedName(getClass()) + " [liveRef: " + this.ref + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int remoteHashCode() {
/* 504 */     return this.ref.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remoteEquals(RemoteRef paramRemoteRef) {
/* 510 */     if (paramRemoteRef instanceof UnicastRef)
/* 511 */       return this.ref.remoteEquals(((UnicastRef)paramRemoteRef).ref); 
/* 512 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\rmi\server\UnicastRef.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */