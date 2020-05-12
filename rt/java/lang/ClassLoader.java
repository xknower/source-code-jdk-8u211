/*      */ package java.lang;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.net.URL;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.CodeSource;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.ProtectionDomain;
/*      */ import java.security.cert.Certificate;
/*      */ import java.util.Collections;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.Stack;
/*      */ import java.util.Vector;
/*      */ import java.util.WeakHashMap;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import sun.misc.CompoundEnumeration;
/*      */ import sun.misc.Launcher;
/*      */ import sun.misc.PerfCounter;
/*      */ import sun.misc.Resource;
/*      */ import sun.misc.URLClassPath;
/*      */ import sun.misc.VM;
/*      */ import sun.reflect.CallerSensitive;
/*      */ import sun.reflect.Reflection;
/*      */ import sun.reflect.misc.ReflectUtil;
/*      */ import sun.security.util.SecurityConstants;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class ClassLoader
/*      */ {
/*      */   private final ClassLoader parent;
/*      */   private final ConcurrentHashMap<String, Object> parallelLockMap;
/*      */   private final Map<String, Certificate[]> package2certs;
/*      */   
/*      */   static {
/*  182 */     registerNatives();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class ParallelLoaders
/*      */   {
/*  198 */     private static final Set<Class<? extends ClassLoader>> loaderTypes = Collections.newSetFromMap(new WeakHashMap<>());
/*      */     
/*      */     static {
/*  201 */       synchronized (loaderTypes) { loaderTypes.add(ClassLoader.class); }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static boolean register(Class<? extends ClassLoader> param1Class) {
/*  210 */       synchronized (loaderTypes) {
/*  211 */         if (loaderTypes.contains(param1Class.getSuperclass())) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  217 */           loaderTypes.add(param1Class);
/*  218 */           return true;
/*      */         } 
/*  220 */         return false;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static boolean isRegistered(Class<? extends ClassLoader> param1Class) {
/*  230 */       synchronized (loaderTypes) {
/*  231 */         return loaderTypes.contains(param1Class);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  246 */   private static final Certificate[] nocerts = new Certificate[0];
/*      */ 
/*      */ 
/*      */   
/*  250 */   private final Vector<Class<?>> classes = new Vector<>();
/*      */ 
/*      */ 
/*      */   
/*  254 */   private final ProtectionDomain defaultDomain = new ProtectionDomain(new CodeSource(null, (Certificate[])null), null, this, null);
/*      */ 
/*      */ 
/*      */   
/*      */   private final Set<ProtectionDomain> domains;
/*      */ 
/*      */ 
/*      */   
/*      */   void addClass(Class<?> paramClass) {
/*  263 */     this.classes.addElement(paramClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  269 */   private final HashMap<String, Package> packages = new HashMap<>(); private static ClassLoader scl; private static boolean sclSet;
/*      */   
/*      */   private static Void checkCreateClassLoader() {
/*  272 */     SecurityManager securityManager = System.getSecurityManager();
/*  273 */     if (securityManager != null) {
/*  274 */       securityManager.checkCreateClassLoader();
/*      */     }
/*  276 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ClassLoader(ClassLoader paramClassLoader) {
/*  316 */     this(checkCreateClassLoader(), paramClassLoader);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ClassLoader() {
/*  335 */     this(checkCreateClassLoader(), getSystemClassLoader());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Class<?> loadClass(String paramString) throws ClassNotFoundException {
/*  357 */     return loadClass(paramString, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Class<?> loadClass(String paramString, boolean paramBoolean) throws ClassNotFoundException {
/*  404 */     synchronized (getClassLoadingLock(paramString)) {
/*      */       
/*  406 */       Class<?> clazz = findLoadedClass(paramString);
/*  407 */       if (clazz == null) {
/*  408 */         long l = System.nanoTime();
/*      */         try {
/*  410 */           if (this.parent != null) {
/*  411 */             clazz = this.parent.loadClass(paramString, false);
/*      */           } else {
/*  413 */             clazz = findBootstrapClassOrNull(paramString);
/*      */           } 
/*  415 */         } catch (ClassNotFoundException classNotFoundException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  420 */         if (clazz == null) {
/*      */ 
/*      */           
/*  423 */           long l1 = System.nanoTime();
/*  424 */           clazz = findClass(paramString);
/*      */ 
/*      */           
/*  427 */           PerfCounter.getParentDelegationTime().addTime(l1 - l);
/*  428 */           PerfCounter.getFindClassTime().addElapsedTimeFrom(l1);
/*  429 */           PerfCounter.getFindClasses().increment();
/*      */         } 
/*      */       } 
/*  432 */       if (paramBoolean) {
/*  433 */         resolveClass(clazz);
/*      */       }
/*  435 */       return clazz;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object getClassLoadingLock(String paramString) {
/*  460 */     Object object = this;
/*  461 */     if (this.parallelLockMap != null) {
/*  462 */       Object object1 = new Object();
/*  463 */       object = this.parallelLockMap.putIfAbsent(paramString, object1);
/*  464 */       if (object == null) {
/*  465 */         object = object1;
/*      */       }
/*      */     } 
/*  468 */     return object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Class<?> loadClassInternal(String paramString) throws ClassNotFoundException {
/*  477 */     if (this.parallelLockMap == null) {
/*  478 */       synchronized (this) {
/*  479 */         return loadClass(paramString);
/*      */       } 
/*      */     }
/*  482 */     return loadClass(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkPackageAccess(Class<?> paramClass, ProtectionDomain paramProtectionDomain) {
/*  488 */     final SecurityManager sm = System.getSecurityManager();
/*  489 */     if (securityManager != null) {
/*  490 */       if (ReflectUtil.isNonPublicProxyClass(paramClass)) {
/*  491 */         for (Class<?> clazz : paramClass.getInterfaces()) {
/*  492 */           checkPackageAccess(clazz, paramProtectionDomain);
/*      */         }
/*      */         
/*      */         return;
/*      */       } 
/*  497 */       final String name = paramClass.getName();
/*  498 */       final int i = str.lastIndexOf('.');
/*  499 */       if (i != -1) {
/*  500 */         AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*      */               public Void run() {
/*  502 */                 sm.checkPackageAccess(name.substring(0, i));
/*  503 */                 return null;
/*      */               }
/*      */             }new AccessControlContext(new ProtectionDomain[] { paramProtectionDomain }));
/*      */       }
/*      */     } 
/*  508 */     this.domains.add(paramProtectionDomain);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Class<?> findClass(String paramString) throws ClassNotFoundException {
/*  530 */     throw new ClassNotFoundException(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected final Class<?> defineClass(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws ClassFormatError {
/*  578 */     return defineClass(null, paramArrayOfbyte, paramInt1, paramInt2, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final Class<?> defineClass(String paramString, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws ClassFormatError {
/*  642 */     return defineClass(paramString, paramArrayOfbyte, paramInt1, paramInt2, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ProtectionDomain preDefineClass(String paramString, ProtectionDomain paramProtectionDomain) {
/*  653 */     if (!checkName(paramString)) {
/*  654 */       throw new NoClassDefFoundError("IllegalName: " + paramString);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  659 */     if (paramString != null && paramString.startsWith("java.")) {
/*  660 */       throw new SecurityException("Prohibited package name: " + paramString
/*      */           
/*  662 */           .substring(0, paramString.lastIndexOf('.')));
/*      */     }
/*  664 */     if (paramProtectionDomain == null) {
/*  665 */       paramProtectionDomain = this.defaultDomain;
/*      */     }
/*      */     
/*  668 */     if (paramString != null) checkCerts(paramString, paramProtectionDomain.getCodeSource());
/*      */     
/*  670 */     return paramProtectionDomain;
/*      */   }
/*      */ 
/*      */   
/*      */   private String defineClassSourceLocation(ProtectionDomain paramProtectionDomain) {
/*  675 */     CodeSource codeSource = paramProtectionDomain.getCodeSource();
/*  676 */     String str = null;
/*  677 */     if (codeSource != null && codeSource.getLocation() != null) {
/*  678 */       str = codeSource.getLocation().toString();
/*      */     }
/*  680 */     return str;
/*      */   }
/*      */ 
/*      */   
/*      */   private void postDefineClass(Class<?> paramClass, ProtectionDomain paramProtectionDomain) {
/*  685 */     if (paramProtectionDomain.getCodeSource() != null) {
/*  686 */       Certificate[] arrayOfCertificate = paramProtectionDomain.getCodeSource().getCertificates();
/*  687 */       if (arrayOfCertificate != null) {
/*  688 */         setSigners(paramClass, (Object[])arrayOfCertificate);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final Class<?> defineClass(String paramString, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, ProtectionDomain paramProtectionDomain) throws ClassFormatError {
/*  761 */     paramProtectionDomain = preDefineClass(paramString, paramProtectionDomain);
/*  762 */     String str = defineClassSourceLocation(paramProtectionDomain);
/*  763 */     Class<?> clazz = defineClass1(paramString, paramArrayOfbyte, paramInt1, paramInt2, paramProtectionDomain, str);
/*  764 */     postDefineClass(clazz, paramProtectionDomain);
/*  765 */     return clazz;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final Class<?> defineClass(String paramString, ByteBuffer paramByteBuffer, ProtectionDomain paramProtectionDomain) throws ClassFormatError {
/*  834 */     int i = paramByteBuffer.remaining();
/*      */ 
/*      */     
/*  837 */     if (!paramByteBuffer.isDirect()) {
/*  838 */       if (paramByteBuffer.hasArray()) {
/*  839 */         return defineClass(paramString, paramByteBuffer.array(), paramByteBuffer
/*  840 */             .position() + paramByteBuffer.arrayOffset(), i, paramProtectionDomain);
/*      */       }
/*      */ 
/*      */       
/*  844 */       byte[] arrayOfByte = new byte[i];
/*  845 */       paramByteBuffer.get(arrayOfByte);
/*  846 */       return defineClass(paramString, arrayOfByte, 0, i, paramProtectionDomain);
/*      */     } 
/*      */ 
/*      */     
/*  850 */     paramProtectionDomain = preDefineClass(paramString, paramProtectionDomain);
/*  851 */     String str = defineClassSourceLocation(paramProtectionDomain);
/*  852 */     Class<?> clazz = defineClass2(paramString, paramByteBuffer, paramByteBuffer.position(), i, paramProtectionDomain, str);
/*  853 */     postDefineClass(clazz, paramProtectionDomain);
/*  854 */     return clazz;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkName(String paramString) {
/*  869 */     if (paramString == null || paramString.length() == 0)
/*  870 */       return true; 
/*  871 */     if (paramString.indexOf('/') != -1 || (
/*  872 */       !VM.allowArraySyntax() && paramString.charAt(0) == '['))
/*  873 */       return false; 
/*  874 */     return true;
/*      */   }
/*      */   
/*      */   private void checkCerts(String paramString, CodeSource paramCodeSource) {
/*  878 */     int i = paramString.lastIndexOf('.');
/*  879 */     String str = (i == -1) ? "" : paramString.substring(0, i);
/*      */     
/*  881 */     Certificate[] arrayOfCertificate1 = null;
/*  882 */     if (paramCodeSource != null) {
/*  883 */       arrayOfCertificate1 = paramCodeSource.getCertificates();
/*      */     }
/*  885 */     Certificate[] arrayOfCertificate2 = null;
/*  886 */     if (this.parallelLockMap == null) {
/*  887 */       synchronized (this) {
/*  888 */         arrayOfCertificate2 = this.package2certs.get(str);
/*  889 */         if (arrayOfCertificate2 == null) {
/*  890 */           this.package2certs.put(str, (arrayOfCertificate1 == null) ? nocerts : arrayOfCertificate1);
/*      */         }
/*      */       } 
/*      */     } else {
/*      */       
/*  895 */       arrayOfCertificate2 = ((ConcurrentHashMap<String, Certificate[]>)this.package2certs).putIfAbsent(str, (arrayOfCertificate1 == null) ? nocerts : arrayOfCertificate1);
/*      */     } 
/*  897 */     if (arrayOfCertificate2 != null && !compareCerts(arrayOfCertificate2, arrayOfCertificate1)) {
/*  898 */       throw new SecurityException("class \"" + paramString + "\"'s signer information does not match signer information of other classes in the same package");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean compareCerts(Certificate[] paramArrayOfCertificate1, Certificate[] paramArrayOfCertificate2) {
/*  911 */     if (paramArrayOfCertificate2 == null || paramArrayOfCertificate2.length == 0) {
/*  912 */       return (paramArrayOfCertificate1.length == 0);
/*      */     }
/*      */ 
/*      */     
/*  916 */     if (paramArrayOfCertificate2.length != paramArrayOfCertificate1.length) {
/*  917 */       return false;
/*      */     }
/*      */     
/*      */     byte b;
/*      */     
/*  922 */     for (b = 0; b < paramArrayOfCertificate2.length; b++) {
/*  923 */       boolean bool = false;
/*  924 */       for (byte b1 = 0; b1 < paramArrayOfCertificate1.length; b1++) {
/*  925 */         if (paramArrayOfCertificate2[b].equals(paramArrayOfCertificate1[b1])) {
/*  926 */           bool = true;
/*      */           break;
/*      */         } 
/*      */       } 
/*  930 */       if (!bool) return false;
/*      */     
/*      */     } 
/*      */     
/*  934 */     for (b = 0; b < paramArrayOfCertificate1.length; b++) {
/*  935 */       boolean bool = false;
/*  936 */       for (byte b1 = 0; b1 < paramArrayOfCertificate2.length; b1++) {
/*  937 */         if (paramArrayOfCertificate1[b].equals(paramArrayOfCertificate2[b1])) {
/*  938 */           bool = true;
/*      */           break;
/*      */         } 
/*      */       } 
/*  942 */       if (!bool) return false;
/*      */     
/*      */     } 
/*  945 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void resolveClass(Class<?> paramClass) {
/*  964 */     resolveClass0(paramClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final Class<?> findSystemClass(String paramString) throws ClassNotFoundException {
/*  994 */     ClassLoader classLoader = getSystemClassLoader();
/*  995 */     if (classLoader == null) {
/*  996 */       if (!checkName(paramString))
/*  997 */         throw new ClassNotFoundException(paramString); 
/*  998 */       Class<?> clazz = findBootstrapClass(paramString);
/*  999 */       if (clazz == null) {
/* 1000 */         throw new ClassNotFoundException(paramString);
/*      */       }
/* 1002 */       return clazz;
/*      */     } 
/* 1004 */     return classLoader.loadClass(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Class<?> findBootstrapClassOrNull(String paramString) {
/* 1013 */     if (!checkName(paramString)) return null;
/*      */     
/* 1015 */     return findBootstrapClass(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final Class<?> findLoadedClass(String paramString) {
/* 1036 */     if (!checkName(paramString))
/* 1037 */       return null; 
/* 1038 */     return findLoadedClass0(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void setSigners(Class<?> paramClass, Object[] paramArrayOfObject) {
/* 1056 */     paramClass.setSigners(paramArrayOfObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URL getResource(String paramString) {
/*      */     URL uRL;
/* 1090 */     if (this.parent != null) {
/* 1091 */       uRL = this.parent.getResource(paramString);
/*      */     } else {
/* 1093 */       uRL = getBootstrapResource(paramString);
/*      */     } 
/* 1095 */     if (uRL == null) {
/* 1096 */       uRL = findResource(paramString);
/*      */     }
/* 1098 */     return uRL;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Enumeration<URL> getResources(String paramString) throws IOException {
/* 1136 */     Enumeration[] arrayOfEnumeration = new Enumeration[2];
/* 1137 */     if (this.parent != null) {
/* 1138 */       arrayOfEnumeration[0] = this.parent.getResources(paramString);
/*      */     } else {
/* 1140 */       arrayOfEnumeration[0] = getBootstrapResources(paramString);
/*      */     } 
/* 1142 */     arrayOfEnumeration[1] = findResources(paramString);
/*      */     
/* 1144 */     return new CompoundEnumeration<>((Enumeration<URL>[])arrayOfEnumeration);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected URL findResource(String paramString) {
/* 1160 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Enumeration<URL> findResources(String paramString) throws IOException {
/* 1181 */     return Collections.emptyEnumeration();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CallerSensitive
/*      */   protected static boolean registerAsParallelCapable() {
/* 1204 */     Class<? extends ClassLoader> clazz = Reflection.getCallerClass().asSubclass(ClassLoader.class);
/* 1205 */     return ParallelLoaders.register(clazz);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static URL getSystemResource(String paramString) {
/* 1222 */     ClassLoader classLoader = getSystemClassLoader();
/* 1223 */     if (classLoader == null) {
/* 1224 */       return getBootstrapResource(paramString);
/*      */     }
/* 1226 */     return classLoader.getResource(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Enumeration<URL> getSystemResources(String paramString) throws IOException {
/* 1252 */     ClassLoader classLoader = getSystemClassLoader();
/* 1253 */     if (classLoader == null) {
/* 1254 */       return getBootstrapResources(paramString);
/*      */     }
/* 1256 */     return classLoader.getResources(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static URL getBootstrapResource(String paramString) {
/* 1263 */     URLClassPath uRLClassPath = getBootstrapClassPath();
/* 1264 */     Resource resource = uRLClassPath.getResource(paramString);
/* 1265 */     return (resource != null) ? resource.getURL() : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Enumeration<URL> getBootstrapResources(String paramString) throws IOException {
/* 1275 */     final Enumeration<Resource> e = getBootstrapClassPath().getResources(paramString);
/* 1276 */     return new Enumeration<URL>() {
/*      */         public URL nextElement() {
/* 1278 */           return ((Resource)e.nextElement()).getURL();
/*      */         }
/*      */         public boolean hasMoreElements() {
/* 1281 */           return e.hasMoreElements();
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   static URLClassPath getBootstrapClassPath() {
/* 1288 */     return Launcher.getBootstrapClassPath();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InputStream getResourceAsStream(String paramString) {
/* 1307 */     URL uRL = getResource(paramString);
/*      */     try {
/* 1309 */       return (uRL != null) ? uRL.openStream() : null;
/* 1310 */     } catch (IOException iOException) {
/* 1311 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static InputStream getSystemResourceAsStream(String paramString) {
/* 1329 */     URL uRL = getSystemResource(paramString);
/*      */     try {
/* 1331 */       return (uRL != null) ? uRL.openStream() : null;
/* 1332 */     } catch (IOException iOException) {
/* 1333 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CallerSensitive
/*      */   public final ClassLoader getParent() {
/* 1367 */     if (this.parent == null)
/* 1368 */       return null; 
/* 1369 */     SecurityManager securityManager = System.getSecurityManager();
/* 1370 */     if (securityManager != null)
/*      */     {
/*      */ 
/*      */       
/* 1374 */       checkClassLoaderPermission(this.parent, Reflection.getCallerClass());
/*      */     }
/* 1376 */     return this.parent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CallerSensitive
/*      */   public static ClassLoader getSystemClassLoader() {
/* 1436 */     initSystemClassLoader();
/* 1437 */     if (scl == null) {
/* 1438 */       return null;
/*      */     }
/* 1440 */     SecurityManager securityManager = System.getSecurityManager();
/* 1441 */     if (securityManager != null) {
/* 1442 */       checkClassLoaderPermission(scl, Reflection.getCallerClass());
/*      */     }
/* 1444 */     return scl;
/*      */   }
/*      */   
/*      */   private static synchronized void initSystemClassLoader() {
/* 1448 */     if (!sclSet) {
/* 1449 */       if (scl != null)
/* 1450 */         throw new IllegalStateException("recursive invocation"); 
/* 1451 */       Launcher launcher = Launcher.getLauncher();
/* 1452 */       if (launcher != null) {
/* 1453 */         Throwable throwable = null;
/* 1454 */         scl = launcher.getClassLoader();
/*      */         try {
/* 1456 */           scl = AccessController.<ClassLoader>doPrivileged(new SystemClassLoaderAction(scl));
/*      */         }
/* 1458 */         catch (PrivilegedActionException privilegedActionException) {
/* 1459 */           throwable = privilegedActionException.getCause();
/* 1460 */           if (throwable instanceof java.lang.reflect.InvocationTargetException) {
/* 1461 */             throwable = throwable.getCause();
/*      */           }
/*      */         } 
/* 1464 */         if (throwable != null) {
/* 1465 */           if (throwable instanceof Error) {
/* 1466 */             throw (Error)throwable;
/*      */           }
/*      */           
/* 1469 */           throw new Error(throwable);
/*      */         } 
/*      */       } 
/*      */       
/* 1473 */       sclSet = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isAncestor(ClassLoader paramClassLoader) {
/* 1480 */     ClassLoader classLoader = this;
/*      */     while (true) {
/* 1482 */       classLoader = classLoader.parent;
/* 1483 */       if (paramClassLoader == classLoader) {
/* 1484 */         return true;
/*      */       }
/* 1486 */       if (classLoader == null) {
/* 1487 */         return false;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean needsClassLoaderPermissionCheck(ClassLoader paramClassLoader1, ClassLoader paramClassLoader2) {
/* 1498 */     if (paramClassLoader1 == paramClassLoader2) {
/* 1499 */       return false;
/*      */     }
/* 1501 */     if (paramClassLoader1 == null) {
/* 1502 */       return false;
/*      */     }
/* 1504 */     return !paramClassLoader2.isAncestor(paramClassLoader1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static ClassLoader getClassLoader(Class<?> paramClass) {
/* 1510 */     if (paramClass == null) {
/* 1511 */       return null;
/*      */     }
/*      */     
/* 1514 */     return paramClass.getClassLoader0();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void checkClassLoaderPermission(ClassLoader paramClassLoader, Class<?> paramClass) {
/* 1523 */     SecurityManager securityManager = System.getSecurityManager();
/* 1524 */     if (securityManager != null) {
/*      */       
/* 1526 */       ClassLoader classLoader = getClassLoader(paramClass);
/* 1527 */       if (needsClassLoaderPermissionCheck(classLoader, paramClassLoader)) {
/* 1528 */         securityManager.checkPermission(SecurityConstants.GET_CLASSLOADER_PERMISSION);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Package definePackage(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, URL paramURL) throws IllegalArgumentException {
/* 1591 */     synchronized (this.packages) {
/* 1592 */       Package package_ = getPackage(paramString1);
/* 1593 */       if (package_ != null) {
/* 1594 */         throw new IllegalArgumentException(paramString1);
/*      */       }
/* 1596 */       package_ = new Package(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramURL, this);
/*      */ 
/*      */       
/* 1599 */       this.packages.put(paramString1, package_);
/* 1600 */       return package_;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Package getPackage(String paramString) {
/*      */     Package package_;
/* 1618 */     synchronized (this.packages) {
/* 1619 */       package_ = this.packages.get(paramString);
/*      */     } 
/* 1621 */     if (package_ == null) {
/* 1622 */       if (this.parent != null) {
/* 1623 */         package_ = this.parent.getPackage(paramString);
/*      */       } else {
/* 1625 */         package_ = Package.getSystemPackage(paramString);
/*      */       } 
/* 1627 */       if (package_ != null) {
/* 1628 */         synchronized (this.packages) {
/* 1629 */           Package package_1 = this.packages.get(paramString);
/* 1630 */           if (package_1 == null) {
/* 1631 */             this.packages.put(paramString, package_);
/*      */           } else {
/* 1633 */             package_ = package_1;
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/* 1638 */     return package_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Package[] getPackages() {
/*      */     HashMap<String, Package> hashMap;
/*      */     Package[] arrayOfPackage;
/* 1652 */     synchronized (this.packages) {
/* 1653 */       hashMap = new HashMap<>(this.packages);
/*      */     } 
/*      */     
/* 1656 */     if (this.parent != null) {
/* 1657 */       arrayOfPackage = this.parent.getPackages();
/*      */     } else {
/* 1659 */       arrayOfPackage = Package.getSystemPackages();
/*      */     } 
/* 1661 */     if (arrayOfPackage != null) {
/* 1662 */       for (byte b = 0; b < arrayOfPackage.length; b++) {
/* 1663 */         String str = arrayOfPackage[b].getName();
/* 1664 */         if (hashMap.get(str) == null) {
/* 1665 */           hashMap.put(str, arrayOfPackage[b]);
/*      */         }
/*      */       } 
/*      */     }
/* 1669 */     return (Package[])hashMap.values().toArray((Object[])new Package[hashMap.size()]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String findLibrary(String paramString) {
/* 1693 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class NativeLibrary
/*      */   {
/*      */     long handle;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int jniVersion;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final Class<?> fromClass;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     String name;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isBuiltin;
/*      */ 
/*      */ 
/*      */     
/*      */     boolean loaded;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public NativeLibrary(Class<?> param1Class, String param1String, boolean param1Boolean) {
/* 1732 */       this.name = param1String;
/* 1733 */       this.fromClass = param1Class;
/* 1734 */       this.isBuiltin = param1Boolean;
/*      */     }
/*      */     
/*      */     protected void finalize() {
/* 1738 */       synchronized (ClassLoader.loadedLibraryNames) {
/* 1739 */         if (this.fromClass.getClassLoader() != null && this.loaded) {
/*      */           
/* 1741 */           int i = ClassLoader.loadedLibraryNames.size();
/* 1742 */           for (byte b = 0; b < i; b++) {
/* 1743 */             if (this.name.equals(ClassLoader.loadedLibraryNames.elementAt(b))) {
/* 1744 */               ClassLoader.loadedLibraryNames.removeElementAt(b);
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/* 1749 */           ClassLoader.nativeLibraryContext.push(this);
/*      */           try {
/* 1751 */             unload(this.name, this.isBuiltin);
/*      */           } finally {
/* 1753 */             ClassLoader.nativeLibraryContext.pop();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     static Class<?> getFromClass() {
/* 1761 */       return (ClassLoader.nativeLibraryContext.peek()).fromClass;
/*      */     }
/*      */     native void load(String param1String, boolean param1Boolean);
/*      */     native long find(String param1String);
/*      */     native void unload(String param1String, boolean param1Boolean); }
/* 1766 */   private static Vector<String> loadedLibraryNames = new Vector<>();
/*      */ 
/*      */   
/* 1769 */   private static Vector<NativeLibrary> systemNativeLibraries = new Vector<>();
/*      */   private Vector<NativeLibrary> nativeLibraries;
/*      */   
/*      */   private ClassLoader(Void paramVoid, ClassLoader paramClassLoader) {
/* 1773 */     this.nativeLibraries = new Vector<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1978 */     this.defaultAssertionStatus = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1986 */     this.packageAssertionStatus = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1993 */     this.classAssertionStatus = null;
/*      */     this.parent = paramClassLoader;
/*      */     if (ParallelLoaders.isRegistered((Class)getClass())) {
/*      */       this.parallelLockMap = new ConcurrentHashMap<>();
/*      */       this.package2certs = (Map)new ConcurrentHashMap<>();
/*      */       this.domains = Collections.synchronizedSet(new HashSet<>());
/*      */       this.assertionLock = new Object();
/*      */     } else {
/*      */       this.parallelLockMap = null;
/*      */       this.package2certs = (Map)new Hashtable<>();
/*      */       this.domains = new HashSet<>();
/*      */       this.assertionLock = this;
/*      */     } 
/*      */   }
/*      */   private static Stack<NativeLibrary> nativeLibraryContext = new Stack<>(); private static String[] usr_paths; private static String[] sys_paths;
/*      */   final Object assertionLock;
/*      */   private boolean defaultAssertionStatus;
/*      */   
/* 2011 */   public void setDefaultAssertionStatus(boolean paramBoolean) { synchronized (this.assertionLock) {
/* 2012 */       if (this.classAssertionStatus == null) {
/* 2013 */         initializeJavaAssertionMaps();
/*      */       }
/* 2015 */       this.defaultAssertionStatus = paramBoolean;
/*      */     }  }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<String, Boolean> packageAssertionStatus;
/*      */ 
/*      */   
/*      */   Map<String, Boolean> classAssertionStatus;
/*      */ 
/*      */ 
/*      */   
/*      */   private static String[] initializePath(String paramString) {
/*      */     String str1 = System.getProperty(paramString, "");
/*      */     String str2 = File.pathSeparator;
/*      */     int i = str1.length();
/*      */     int j = str1.indexOf(str2);
/*      */     int m = 0;
/*      */     while (j >= 0) {
/*      */       m++;
/*      */       j = str1.indexOf(str2, j + 1);
/*      */     } 
/*      */     String[] arrayOfString = new String[m + 1];
/*      */     m = j = 0;
/*      */     int k = str1.indexOf(str2);
/*      */     while (k >= 0) {
/*      */       if (k - j > 0) {
/*      */         arrayOfString[m++] = str1.substring(j, k);
/*      */       } else if (k - j == 0) {
/*      */         arrayOfString[m++] = ".";
/*      */       } 
/*      */       j = k + 1;
/*      */       k = str1.indexOf(str2, j);
/*      */     } 
/*      */     arrayOfString[m] = str1.substring(j, i);
/*      */     return arrayOfString;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPackageAssertionStatus(String paramString, boolean paramBoolean) {
/* 2058 */     synchronized (this.assertionLock) {
/* 2059 */       if (this.packageAssertionStatus == null) {
/* 2060 */         initializeJavaAssertionMaps();
/*      */       }
/* 2062 */       this.packageAssertionStatus.put(paramString, Boolean.valueOf(paramBoolean));
/*      */     } 
/*      */   } static void loadLibrary(Class<?> paramClass, String paramString, boolean paramBoolean) { ClassLoader classLoader = (paramClass == null) ? null : paramClass.getClassLoader(); if (sys_paths == null) {
/*      */       usr_paths = initializePath("java.library.path"); sys_paths = initializePath("sun.boot.library.path");
/*      */     }  if (paramBoolean) {
/*      */       if (loadLibrary0(paramClass, new File(paramString)))
/*      */         return;  throw new UnsatisfiedLinkError("Can't load library: " + paramString);
/*      */     }  if (classLoader != null) {
/*      */       String str = classLoader.findLibrary(paramString); if (str != null) {
/*      */         File file = new File(str); if (!file.isAbsolute())
/*      */           throw new UnsatisfiedLinkError("ClassLoader.findLibrary failed to return an absolute path: " + str);  if (loadLibrary0(paramClass, file))
/*      */           return;  throw new UnsatisfiedLinkError("Can't load " + str);
/*      */       } 
/*      */     }  byte b; for (b = 0; b < sys_paths.length; b++) {
/*      */       File file = new File(sys_paths[b], System.mapLibraryName(paramString)); if (loadLibrary0(paramClass, file))
/*      */         return;  file = ClassLoaderHelper.mapAlternativeName(file); if (file != null && loadLibrary0(paramClass, file))
/*      */         return; 
/*      */     } 
/*      */     if (classLoader != null)
/*      */       for (b = 0; b < usr_paths.length; b++) {
/*      */         File file = new File(usr_paths[b], System.mapLibraryName(paramString));
/*      */         if (loadLibrary0(paramClass, file))
/*      */           return; 
/*      */         file = ClassLoaderHelper.mapAlternativeName(file);
/*      */         if (file != null && loadLibrary0(paramClass, file))
/*      */           return; 
/*      */       }  
/* 2089 */     throw new UnsatisfiedLinkError("no " + paramString + " in java.library.path"); } public void setClassAssertionStatus(String paramString, boolean paramBoolean) { synchronized (this.assertionLock) {
/* 2090 */       if (this.classAssertionStatus == null) {
/* 2091 */         initializeJavaAssertionMaps();
/*      */       }
/* 2093 */       this.classAssertionStatus.put(paramString, Boolean.valueOf(paramBoolean));
/*      */     }  }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearAssertionStatus() {
/* 2111 */     synchronized (this.assertionLock) {
/* 2112 */       this.classAssertionStatus = new HashMap<>();
/* 2113 */       this.packageAssertionStatus = new HashMap<>();
/* 2114 */       this.defaultAssertionStatus = false;
/*      */     } 
/*      */   } private static boolean loadLibrary0(Class<?> paramClass, final File file) { String str = findBuiltinLib(file.getName()); boolean bool = (str != null) ? true : false; if (!bool) { boolean bool1 = (AccessController.doPrivileged(new PrivilegedAction() {
/*      */             public Object run() { return file.exists() ? Boolean.TRUE : null; }
/*      */           }) != null) ? true : false; if (!bool1)
/*      */         return false;  try { str = file.getCanonicalPath(); }
/*      */       catch (IOException iOException) { return false; }
/*      */        }
/*      */      ClassLoader classLoader = (paramClass == null) ? null : paramClass.getClassLoader(); Vector<NativeLibrary> vector = (classLoader != null) ? classLoader.nativeLibraries : systemNativeLibraries; synchronized (vector) { int i = vector.size(); for (byte b = 0; b < i; b++) { NativeLibrary nativeLibrary = vector.elementAt(b); if (str.equals(nativeLibrary.name))
/*      */           return true;  }
/*      */        synchronized (loadedLibraryNames) { if (loadedLibraryNames.contains(str))
/*      */           throw new UnsatisfiedLinkError("Native Library " + str + " already loaded in another classloader");  int j = nativeLibraryContext.size(); for (byte b1 = 0; b1 < j; b1++) { NativeLibrary nativeLibrary1 = nativeLibraryContext.elementAt(b1); if (str.equals(nativeLibrary1.name)) { if (classLoader == nativeLibrary1.fromClass.getClassLoader())
/*      */               return true;  throw new UnsatisfiedLinkError("Native Library " + str + " is being loaded in another classloader"); }
/*      */            }
/*      */          NativeLibrary nativeLibrary = new NativeLibrary(paramClass, str, bool); nativeLibraryContext.push(nativeLibrary); try {
/*      */           nativeLibrary.load(str, bool);
/*      */         } finally {
/*      */           nativeLibraryContext.pop();
/*      */         }  if (nativeLibrary.loaded) {
/*      */           loadedLibraryNames.addElement(str); vector.addElement(nativeLibrary); return true;
/*      */         }  return false; }
/*      */        }
/*      */      } static long findNative(ClassLoader paramClassLoader, String paramString) { Vector<NativeLibrary> vector = (paramClassLoader != null) ? paramClassLoader.nativeLibraries : systemNativeLibraries; synchronized (vector) {
/*      */       int i = vector.size(); for (byte b = 0; b < i; b++) {
/*      */         NativeLibrary nativeLibrary = vector.elementAt(b); long l = nativeLibrary.find(paramString); if (l != 0L)
/*      */           return l; 
/*      */       } 
/* 2141 */     }  return 0L; } boolean desiredAssertionStatus(String paramString) { synchronized (this.assertionLock) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2146 */       Boolean bool = this.classAssertionStatus.get(paramString);
/* 2147 */       if (bool != null) {
/* 2148 */         return bool.booleanValue();
/*      */       }
/*      */       
/* 2151 */       int i = paramString.lastIndexOf(".");
/* 2152 */       if (i < 0) {
/* 2153 */         bool = this.packageAssertionStatus.get(null);
/* 2154 */         if (bool != null)
/* 2155 */           return bool.booleanValue(); 
/*      */       } 
/* 2157 */       while (i > 0) {
/* 2158 */         paramString = paramString.substring(0, i);
/* 2159 */         bool = this.packageAssertionStatus.get(paramString);
/* 2160 */         if (bool != null)
/* 2161 */           return bool.booleanValue(); 
/* 2162 */         i = paramString.lastIndexOf(".", i - 1);
/*      */       } 
/*      */ 
/*      */       
/* 2166 */       return this.defaultAssertionStatus;
/*      */     }  }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initializeJavaAssertionMaps() {
/* 2175 */     this.classAssertionStatus = new HashMap<>();
/* 2176 */     this.packageAssertionStatus = new HashMap<>();
/* 2177 */     AssertionStatusDirectives assertionStatusDirectives = retrieveDirectives();
/*      */     byte b;
/* 2179 */     for (b = 0; b < assertionStatusDirectives.classes.length; b++) {
/* 2180 */       this.classAssertionStatus.put(assertionStatusDirectives.classes[b], 
/* 2181 */           Boolean.valueOf(assertionStatusDirectives.classEnabled[b]));
/*      */     }
/* 2183 */     for (b = 0; b < assertionStatusDirectives.packages.length; b++) {
/* 2184 */       this.packageAssertionStatus.put(assertionStatusDirectives.packages[b], 
/* 2185 */           Boolean.valueOf(assertionStatusDirectives.packageEnabled[b]));
/*      */     }
/* 2187 */     this.defaultAssertionStatus = assertionStatusDirectives.deflt;
/*      */   }
/*      */   
/*      */   private static native void registerNatives();
/*      */   
/*      */   private native Class<?> defineClass0(String paramString, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, ProtectionDomain paramProtectionDomain);
/*      */   
/*      */   private native Class<?> defineClass1(String paramString1, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, ProtectionDomain paramProtectionDomain, String paramString2);
/*      */   
/*      */   private native Class<?> defineClass2(String paramString1, ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, ProtectionDomain paramProtectionDomain, String paramString2);
/*      */   
/*      */   private native void resolveClass0(Class<?> paramClass);
/*      */   
/*      */   private native Class<?> findBootstrapClass(String paramString);
/*      */   
/*      */   private final native Class<?> findLoadedClass0(String paramString);
/*      */   
/*      */   private static native String findBuiltinLib(String paramString);
/*      */   
/*      */   private static native AssertionStatusDirectives retrieveDirectives();
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\lang\ClassLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */