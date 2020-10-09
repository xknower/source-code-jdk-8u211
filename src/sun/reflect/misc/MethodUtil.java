/*     */ package sun.reflect.misc;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.security.AccessController;
/*     */ import java.security.AllPermission;
/*     */ import java.security.CodeSource;
/*     */ import java.security.PermissionCollection;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.security.SecureClassLoader;
/*     */ import java.security.cert.Certificate;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import sun.misc.IOUtils;
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
/*     */ public final class MethodUtil
/*     */   extends SecureClassLoader
/*     */ {
/*     */   private static final String MISC_PKG = "sun.reflect.misc.";
/*     */   private static final String TRAMPOLINE = "sun.reflect.misc.Trampoline";
/*  81 */   private static final Method bounce = getTrampoline();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Method getMethod(Class<?> paramClass, String paramString, Class<?>[] paramArrayOfClass) throws NoSuchMethodException {
/*  89 */     ReflectUtil.checkPackageAccess(paramClass);
/*  90 */     return paramClass.getMethod(paramString, paramArrayOfClass);
/*     */   }
/*     */   
/*     */   public static Method[] getMethods(Class<?> paramClass) {
/*  94 */     ReflectUtil.checkPackageAccess(paramClass);
/*  95 */     return paramClass.getMethods();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Method[] getPublicMethods(Class<?> paramClass) {
/* 106 */     if (System.getSecurityManager() == null) {
/* 107 */       return paramClass.getMethods();
/*     */     }
/* 109 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 110 */     while (paramClass != null) {
/* 111 */       boolean bool = getInternalPublicMethods(paramClass, (Map)hashMap);
/* 112 */       if (bool) {
/*     */         break;
/*     */       }
/* 115 */       getInterfaceMethods(paramClass, (Map)hashMap);
/* 116 */       paramClass = paramClass.getSuperclass();
/*     */     } 
/* 118 */     return (Method[])hashMap.values().toArray((Object[])new Method[hashMap.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void getInterfaceMethods(Class<?> paramClass, Map<Signature, Method> paramMap) {
/* 126 */     Class[] arrayOfClass = paramClass.getInterfaces();
/* 127 */     for (byte b = 0; b < arrayOfClass.length; b++) {
/* 128 */       Class<?> clazz = arrayOfClass[b];
/* 129 */       boolean bool = getInternalPublicMethods(clazz, paramMap);
/* 130 */       if (!bool) {
/* 131 */         getInterfaceMethods(clazz, paramMap);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean getInternalPublicMethods(Class<?> paramClass, Map<Signature, Method> paramMap) {
/* 142 */     Method[] arrayOfMethod = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 149 */       if (!Modifier.isPublic(paramClass.getModifiers())) {
/* 150 */         return false;
/*     */       }
/* 152 */       if (!ReflectUtil.isPackageAccessible(paramClass)) {
/* 153 */         return false;
/*     */       }
/*     */       
/* 156 */       arrayOfMethod = paramClass.getMethods();
/* 157 */     } catch (SecurityException securityException) {
/* 158 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 167 */     boolean bool = true; byte b;
/* 168 */     for (b = 0; b < arrayOfMethod.length; b++) {
/* 169 */       Class<?> clazz = arrayOfMethod[b].getDeclaringClass();
/* 170 */       if (!Modifier.isPublic(clazz.getModifiers())) {
/* 171 */         bool = false;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 176 */     if (bool) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 181 */       for (b = 0; b < arrayOfMethod.length; b++) {
/* 182 */         addMethod(paramMap, arrayOfMethod[b]);
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 189 */       for (b = 0; b < arrayOfMethod.length; b++) {
/* 190 */         Class<?> clazz = arrayOfMethod[b].getDeclaringClass();
/* 191 */         if (paramClass.equals(clazz)) {
/* 192 */           addMethod(paramMap, arrayOfMethod[b]);
/*     */         }
/*     */       } 
/*     */     } 
/* 196 */     return bool;
/*     */   }
/*     */   
/*     */   private static void addMethod(Map<Signature, Method> paramMap, Method paramMethod) {
/* 200 */     Signature signature = new Signature(paramMethod);
/* 201 */     if (!paramMap.containsKey(signature)) {
/* 202 */       paramMap.put(signature, paramMethod);
/* 203 */     } else if (!paramMethod.getDeclaringClass().isInterface()) {
/*     */ 
/*     */ 
/*     */       
/* 207 */       Method method = paramMap.get(signature);
/* 208 */       if (method.getDeclaringClass().isInterface()) {
/* 209 */         paramMap.put(signature, paramMethod);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Signature
/*     */   {
/*     */     private String methodName;
/*     */     
/*     */     private Class<?>[] argClasses;
/*     */     
/* 222 */     private volatile int hashCode = 0;
/*     */     
/*     */     Signature(Method param1Method) {
/* 225 */       this.methodName = param1Method.getName();
/* 226 */       this.argClasses = param1Method.getParameterTypes();
/*     */     }
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 230 */       if (this == param1Object) {
/* 231 */         return true;
/*     */       }
/* 233 */       Signature signature = (Signature)param1Object;
/* 234 */       if (!this.methodName.equals(signature.methodName)) {
/* 235 */         return false;
/*     */       }
/* 237 */       if (this.argClasses.length != signature.argClasses.length) {
/* 238 */         return false;
/*     */       }
/* 240 */       for (byte b = 0; b < this.argClasses.length; b++) {
/* 241 */         if (this.argClasses[b] != signature.argClasses[b]) {
/* 242 */           return false;
/*     */         }
/*     */       } 
/* 245 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 253 */       if (this.hashCode == 0) {
/* 254 */         int i = 17;
/* 255 */         i = 37 * i + this.methodName.hashCode();
/* 256 */         if (this.argClasses != null) {
/* 257 */           for (byte b = 0; b < this.argClasses.length; b++)
/*     */           {
/* 259 */             i = 37 * i + ((this.argClasses[b] == null) ? 0 : this.argClasses[b].hashCode());
/*     */           }
/*     */         }
/* 262 */         this.hashCode = i;
/*     */       } 
/* 264 */       return this.hashCode;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object invoke(Method paramMethod, Object paramObject, Object[] paramArrayOfObject) throws InvocationTargetException, IllegalAccessException {
/*     */     try {
/* 275 */       return bounce.invoke(null, new Object[] { paramMethod, paramObject, paramArrayOfObject });
/* 276 */     } catch (InvocationTargetException invocationTargetException) {
/* 277 */       Throwable throwable = invocationTargetException.getCause();
/*     */       
/* 279 */       if (throwable instanceof InvocationTargetException)
/* 280 */         throw (InvocationTargetException)throwable; 
/* 281 */       if (throwable instanceof IllegalAccessException)
/* 282 */         throw (IllegalAccessException)throwable; 
/* 283 */       if (throwable instanceof RuntimeException)
/* 284 */         throw (RuntimeException)throwable; 
/* 285 */       if (throwable instanceof Error) {
/* 286 */         throw (Error)throwable;
/*     */       }
/* 288 */       throw new Error("Unexpected invocation error", throwable);
/*     */     }
/* 290 */     catch (IllegalAccessException illegalAccessException) {
/*     */       
/* 292 */       throw new Error("Unexpected invocation error", illegalAccessException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Method getTrampoline() {
/*     */     try {
/* 298 */       return AccessController.<Method>doPrivileged(new PrivilegedExceptionAction<Method>()
/*     */           {
/*     */             public Method run() throws Exception {
/* 301 */               Class clazz = MethodUtil.getTrampolineClass();
/* 302 */               Class[] arrayOfClass = { Method.class, Object.class, Object[].class };
/*     */ 
/*     */               
/* 305 */               Method method = clazz.getDeclaredMethod("invoke", arrayOfClass);
/* 306 */               method.setAccessible(true);
/* 307 */               return method;
/*     */             }
/*     */           });
/* 310 */     } catch (Exception exception) {
/* 311 */       throw new InternalError("bouncer cannot be found", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized Class<?> loadClass(String paramString, boolean paramBoolean) throws ClassNotFoundException {
/* 320 */     ReflectUtil.checkPackageAccess(paramString);
/* 321 */     Class<?> clazz = findLoadedClass(paramString);
/* 322 */     if (clazz == null) {
/*     */       try {
/* 324 */         clazz = findClass(paramString);
/* 325 */       } catch (ClassNotFoundException classNotFoundException) {}
/*     */ 
/*     */       
/* 328 */       if (clazz == null) {
/* 329 */         clazz = getParent().loadClass(paramString);
/*     */       }
/*     */     } 
/* 332 */     if (paramBoolean) {
/* 333 */       resolveClass(clazz);
/*     */     }
/* 335 */     return clazz;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Class<?> findClass(String paramString) throws ClassNotFoundException {
/* 342 */     if (!paramString.startsWith("sun.reflect.misc.")) {
/* 343 */       throw new ClassNotFoundException(paramString);
/*     */     }
/* 345 */     String str = paramString.replace('.', '/').concat(".class");
/* 346 */     URL uRL = getResource(str);
/* 347 */     if (uRL != null) {
/*     */       try {
/* 349 */         return defineClass(paramString, uRL);
/* 350 */       } catch (IOException iOException) {
/* 351 */         throw new ClassNotFoundException(paramString, iOException);
/*     */       } 
/*     */     }
/* 354 */     throw new ClassNotFoundException(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Class<?> defineClass(String paramString, URL paramURL) throws IOException {
/* 363 */     byte[] arrayOfByte = getBytes(paramURL);
/* 364 */     CodeSource codeSource = new CodeSource(null, (Certificate[])null);
/* 365 */     if (!paramString.equals("sun.reflect.misc.Trampoline")) {
/* 366 */       throw new IOException("MethodUtil: bad name " + paramString);
/*     */     }
/* 368 */     return defineClass(paramString, arrayOfByte, 0, arrayOfByte.length, codeSource);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] getBytes(URL paramURL) throws IOException {
/*     */     byte[] arrayOfByte;
/* 376 */     URLConnection uRLConnection = paramURL.openConnection();
/* 377 */     if (uRLConnection instanceof HttpURLConnection) {
/* 378 */       HttpURLConnection httpURLConnection = (HttpURLConnection)uRLConnection;
/* 379 */       int j = httpURLConnection.getResponseCode();
/* 380 */       if (j >= 400) {
/* 381 */         throw new IOException("open HTTP connection failed.");
/*     */       }
/*     */     } 
/* 384 */     int i = uRLConnection.getContentLength();
/* 385 */     BufferedInputStream bufferedInputStream = new BufferedInputStream(uRLConnection.getInputStream());
/*     */ 
/*     */     
/*     */     try {
/* 389 */       arrayOfByte = IOUtils.readFully(bufferedInputStream, i, true);
/*     */     } finally {
/* 391 */       bufferedInputStream.close();
/*     */     } 
/* 393 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected PermissionCollection getPermissions(CodeSource paramCodeSource) {
/* 399 */     PermissionCollection permissionCollection = super.getPermissions(paramCodeSource);
/* 400 */     permissionCollection.add(new AllPermission());
/* 401 */     return permissionCollection;
/*     */   }
/*     */   
/*     */   private static Class<?> getTrampolineClass() {
/*     */     try {
/* 406 */       return Class.forName("sun.reflect.misc.Trampoline", true, new MethodUtil());
/* 407 */     } catch (ClassNotFoundException classNotFoundException) {
/*     */       
/* 409 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\reflect\misc\MethodUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */