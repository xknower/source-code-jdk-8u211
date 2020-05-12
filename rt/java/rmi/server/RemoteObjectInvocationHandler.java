/*     */ package java.rmi.server;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.UnexpectedException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import sun.rmi.server.Util;
/*     */ import sun.rmi.server.WeakClassHashMap;
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
/*     */ public class RemoteObjectInvocationHandler
/*     */   extends RemoteObject
/*     */   implements InvocationHandler
/*     */ {
/*     */   private static final long serialVersionUID = 2L;
/*     */   private static final boolean allowFinalizeInvocation;
/*     */   
/*     */   static {
/*  64 */     final String propName = "sun.rmi.server.invocationhandler.allowFinalizeInvocation";
/*  65 */     String str2 = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */         {
/*     */           public String run()
/*     */           {
/*  69 */             return System.getProperty(propName);
/*     */           }
/*     */         });
/*  72 */     if ("".equals(str2)) {
/*  73 */       allowFinalizeInvocation = true;
/*     */     } else {
/*  75 */       allowFinalizeInvocation = Boolean.parseBoolean(str2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   private static final MethodToHash_Maps methodToHash_Maps = new MethodToHash_Maps();
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
/*     */   public RemoteObjectInvocationHandler(RemoteRef paramRemoteRef) {
/*  95 */     super(paramRemoteRef);
/*  96 */     if (paramRemoteRef == null) {
/*  97 */       throw new NullPointerException();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object invoke(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) throws Throwable {
/* 165 */     if (!Proxy.isProxyClass(paramObject.getClass())) {
/* 166 */       throw new IllegalArgumentException("not a proxy");
/*     */     }
/*     */     
/* 169 */     if (Proxy.getInvocationHandler(paramObject) != this) {
/* 170 */       throw new IllegalArgumentException("handler mismatch");
/*     */     }
/*     */     
/* 173 */     if (paramMethod.getDeclaringClass() == Object.class)
/* 174 */       return invokeObjectMethod(paramObject, paramMethod, paramArrayOfObject); 
/* 175 */     if ("finalize".equals(paramMethod.getName()) && paramMethod.getParameterCount() == 0 && !allowFinalizeInvocation)
/*     */     {
/* 177 */       return null;
/*     */     }
/* 179 */     return invokeRemoteMethod(paramObject, paramMethod, paramArrayOfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object invokeObjectMethod(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) {
/* 190 */     String str = paramMethod.getName();
/*     */     
/* 192 */     if (str.equals("hashCode")) {
/* 193 */       return Integer.valueOf(hashCode());
/*     */     }
/* 195 */     if (str.equals("equals")) {
/* 196 */       Object object = paramArrayOfObject[0];
/*     */       InvocationHandler invocationHandler;
/* 198 */       return 
/* 199 */         Boolean.valueOf((paramObject == object || (object != null && 
/*     */           
/* 201 */           Proxy.isProxyClass(object.getClass()) && 
/* 202 */           invocationHandler = Proxy.getInvocationHandler(object) instanceof RemoteObjectInvocationHandler && 
/* 203 */           equals(invocationHandler))));
/*     */     } 
/* 205 */     if (str.equals("toString")) {
/* 206 */       return proxyToString(paramObject);
/*     */     }
/*     */     
/* 209 */     throw new IllegalArgumentException("unexpected Object method: " + paramMethod);
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
/*     */   private Object invokeRemoteMethod(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) throws Exception {
/*     */     try {
/* 223 */       if (!(paramObject instanceof Remote)) {
/* 224 */         throw new IllegalArgumentException("proxy not Remote instance");
/*     */       }
/*     */       
/* 227 */       return this.ref.invoke((Remote)paramObject, paramMethod, paramArrayOfObject, 
/* 228 */           getMethodHash(paramMethod));
/* 229 */     } catch (Exception exception) {
/* 230 */       if (!(exception instanceof RuntimeException)) {
/* 231 */         Class<?> clazz1 = paramObject.getClass();
/*     */         try {
/* 233 */           paramMethod = clazz1.getMethod(paramMethod.getName(), paramMethod
/* 234 */               .getParameterTypes());
/* 235 */         } catch (NoSuchMethodException noSuchMethodException) {
/* 236 */           throw (IllegalArgumentException)(new IllegalArgumentException())
/* 237 */             .initCause(noSuchMethodException);
/*     */         } 
/* 239 */         Class<?> clazz2 = exception.getClass();
/* 240 */         for (Class<?> clazz : paramMethod.getExceptionTypes()) {
/* 241 */           if (clazz.isAssignableFrom(clazz2)) {
/* 242 */             throw exception;
/*     */           }
/*     */         } 
/* 245 */         exception = new UnexpectedException("unexpected exception", exception);
/*     */       } 
/* 247 */       throw exception;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String proxyToString(Object paramObject) {
/* 256 */     Class[] arrayOfClass = paramObject.getClass().getInterfaces();
/* 257 */     if (arrayOfClass.length == 0) {
/* 258 */       return "Proxy[" + this + "]";
/*     */     }
/* 260 */     String str = arrayOfClass[0].getName();
/* 261 */     if (str.equals("java.rmi.Remote") && arrayOfClass.length > 1) {
/* 262 */       str = arrayOfClass[1].getName();
/*     */     }
/* 264 */     int i = str.lastIndexOf('.');
/* 265 */     if (i >= 0) {
/* 266 */       str = str.substring(i + 1);
/*     */     }
/* 268 */     return "Proxy[" + str + "," + this + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObjectNoData() throws InvalidObjectException {
/* 275 */     throw new InvalidObjectException("no data in stream; class: " + 
/* 276 */         getClass().getName());
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
/*     */   private static long getMethodHash(Method paramMethod) {
/* 290 */     return ((Long)methodToHash_Maps.get(paramMethod.getDeclaringClass()).get(paramMethod)).longValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class MethodToHash_Maps
/*     */     extends WeakClassHashMap<Map<Method, Long>>
/*     */   {
/*     */     protected Map<Method, Long> computeValue(Class<?> param1Class) {
/* 303 */       return new WeakHashMap<Method, Long>() {
/*     */           public synchronized Long get(Object param2Object) {
/* 305 */             Long long_ = super.get(param2Object);
/* 306 */             if (long_ == null) {
/* 307 */               Method method = (Method)param2Object;
/* 308 */               long_ = Long.valueOf(Util.computeMethodHash(method));
/* 309 */               put(method, long_);
/*     */             } 
/* 311 */             return long_;
/*     */           }
/*     */         };
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\rmi\server\RemoteObjectInvocationHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */