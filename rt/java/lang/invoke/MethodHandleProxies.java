/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandleImpl;
/*     */ import java.lang.invoke.MethodHandleProxies;
/*     */ import java.lang.invoke.MethodHandleStatics;
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import sun.invoke.WrapperInstance;
/*     */ import sun.reflect.CallerSensitive;
/*     */ import sun.reflect.Reflection;
/*     */ import sun.reflect.misc.ReflectUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MethodHandleProxies
/*     */ {
/*     */   @CallerSensitive
/*     */   public static <T> T asInterfaceInstance(final Class<T> intfc, final MethodHandle target) {
/*     */     MethodHandle methodHandle;
/*     */     Object object;
/* 151 */     if (!intfc.isInterface() || !Modifier.isPublic(intfc.getModifiers())) {
/* 152 */       throw MethodHandleStatics.newIllegalArgumentException("not a public interface", intfc.getName());
/*     */     }
/* 154 */     if (System.getSecurityManager() != null) {
/* 155 */       Class<?> clazz = Reflection.getCallerClass();
/* 156 */       final ClassLoader loader = (clazz != null) ? clazz.getClassLoader() : null;
/* 157 */       ReflectUtil.checkProxyPackageAccess(classLoader1, new Class[] { intfc });
/* 158 */       methodHandle = (classLoader1 != null) ? bindCaller(target, clazz) : target;
/*     */     } else {
/* 160 */       methodHandle = target;
/*     */     } 
/* 162 */     ClassLoader classLoader = intfc.getClassLoader();
/* 163 */     if (classLoader == null) {
/* 164 */       final ClassLoader loader = Thread.currentThread().getContextClassLoader();
/* 165 */       classLoader = (classLoader1 != null) ? classLoader1 : ClassLoader.getSystemClassLoader();
/*     */     } 
/* 167 */     final Method[] methods = getSingleNameMethods(intfc);
/* 168 */     if (arrayOfMethod == null)
/* 169 */       throw MethodHandleStatics.newIllegalArgumentException("not a single-method interface", intfc.getName()); 
/* 170 */     final MethodHandle[] vaTargets = new MethodHandle[arrayOfMethod.length];
/* 171 */     for (byte b = 0; b < arrayOfMethod.length; b++) {
/* 172 */       object = arrayOfMethod[b];
/* 173 */       MethodType methodType = MethodType.methodType(object.getReturnType(), object.getParameterTypes());
/* 174 */       MethodHandle methodHandle1 = methodHandle.asType(methodType);
/* 175 */       methodHandle1 = methodHandle1.asType(methodHandle1.type().changeReturnType(Object.class));
/* 176 */       arrayOfMethodHandle[b] = methodHandle1.asSpreader(Object[].class, methodType.parameterCount());
/*     */     } 
/* 178 */     final InvocationHandler ih = new InvocationHandler() {
/*     */         private Object getArg(String param1String) {
/* 180 */           if (param1String == "getWrapperInstanceTarget") return target; 
/* 181 */           if (param1String == "getWrapperInstanceType") return intfc; 
/* 182 */           throw new AssertionError();
/*     */         }
/*     */         public Object invoke(Object param1Object, Method param1Method, Object[] param1ArrayOfObject) throws Throwable {
/* 185 */           for (byte b = 0; b < methods.length; b++) {
/* 186 */             if (param1Method.equals(methods[b]))
/* 187 */               return vaTargets[b].invokeExact(param1ArrayOfObject); 
/*     */           } 
/* 189 */           if (param1Method.getDeclaringClass() == WrapperInstance.class)
/* 190 */             return getArg(param1Method.getName()); 
/* 191 */           if (MethodHandleProxies.isObjectMethod(param1Method))
/* 192 */             return MethodHandleProxies.callObjectMethod(param1Object, param1Method, param1ArrayOfObject); 
/* 193 */           throw MethodHandleStatics.newInternalError("bad proxy method: " + param1Method);
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 198 */     if (System.getSecurityManager() != null) {
/*     */ 
/*     */       
/* 201 */       final ClassLoader loader = classLoader;
/* 202 */       object = AccessController.doPrivileged(new PrivilegedAction() {
/*     */             public Object run() {
/* 204 */               return Proxy.newProxyInstance(loader, new Class[] { this.val$intfc, WrapperInstance.class }, ih);
/*     */             }
/*     */           });
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 211 */       object = Proxy.newProxyInstance(classLoader, new Class[] { intfc, WrapperInstance.class }, invocationHandler);
/*     */     } 
/*     */ 
/*     */     
/* 215 */     return intfc.cast(object);
/*     */   }
/*     */   
/*     */   private static MethodHandle bindCaller(MethodHandle paramMethodHandle, Class<?> paramClass) {
/* 219 */     MethodHandle methodHandle = MethodHandleImpl.bindCaller(paramMethodHandle, paramClass);
/* 220 */     if (paramMethodHandle.isVarargsCollector()) {
/* 221 */       MethodType methodType = methodHandle.type();
/* 222 */       int i = methodType.parameterCount();
/* 223 */       return methodHandle.asVarargsCollector(methodType.parameterType(i - 1));
/*     */     } 
/* 225 */     return methodHandle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isWrapperInstance(Object paramObject) {
/* 235 */     return paramObject instanceof WrapperInstance;
/*     */   }
/*     */   
/*     */   private static WrapperInstance asWrapperInstance(Object paramObject) {
/*     */     try {
/* 240 */       if (paramObject != null)
/* 241 */         return (WrapperInstance)paramObject; 
/* 242 */     } catch (ClassCastException classCastException) {}
/*     */     
/* 244 */     throw MethodHandleStatics.newIllegalArgumentException("not a wrapper instance");
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
/*     */   public static MethodHandle wrapperInstanceTarget(Object paramObject) {
/* 258 */     return asWrapperInstance(paramObject).getWrapperInstanceTarget();
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
/*     */   public static Class<?> wrapperInstanceType(Object paramObject) {
/* 271 */     return asWrapperInstance(paramObject).getWrapperInstanceType();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isObjectMethod(Method paramMethod) {
/* 276 */     switch (paramMethod.getName()) {
/*     */       case "toString":
/* 278 */         return (paramMethod.getReturnType() == String.class && (paramMethod
/* 279 */           .getParameterTypes()).length == 0);
/*     */       case "hashCode":
/* 281 */         return (paramMethod.getReturnType() == int.class && (paramMethod
/* 282 */           .getParameterTypes()).length == 0);
/*     */       case "equals":
/* 284 */         return (paramMethod.getReturnType() == boolean.class && (paramMethod
/* 285 */           .getParameterTypes()).length == 1 && paramMethod
/* 286 */           .getParameterTypes()[0] == Object.class);
/*     */     } 
/* 288 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Object callObjectMethod(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) {
/* 293 */     assert isObjectMethod(paramMethod) : paramMethod;
/* 294 */     switch (paramMethod.getName()) {
/*     */       case "toString":
/* 296 */         return paramObject.getClass().getName() + "@" + Integer.toHexString(paramObject.hashCode());
/*     */       case "hashCode":
/* 298 */         return Integer.valueOf(System.identityHashCode(paramObject));
/*     */       case "equals":
/* 300 */         return Boolean.valueOf((paramObject == paramArrayOfObject[0]));
/*     */     } 
/* 302 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Method[] getSingleNameMethods(Class<?> paramClass) {
/* 307 */     ArrayList<Method> arrayList = new ArrayList();
/* 308 */     String str = null;
/* 309 */     for (Method method : paramClass.getMethods()) {
/* 310 */       if (!isObjectMethod(method) && 
/* 311 */         Modifier.isAbstract(method.getModifiers())) {
/* 312 */         String str1 = method.getName();
/* 313 */         if (str == null) {
/* 314 */           str = str1;
/* 315 */         } else if (!str.equals(str1)) {
/* 316 */           return null;
/* 317 */         }  arrayList.add(method);
/*     */       } 
/* 319 */     }  if (str == null) return null; 
/* 320 */     return arrayList.<Method>toArray(new Method[arrayList.size()]);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\lang\invoke\MethodHandleProxies.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */