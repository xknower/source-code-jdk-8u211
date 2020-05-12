/*     */ package sun.misc;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.net.URLClassLoader;
/*     */ import java.net.URLStreamHandler;
/*     */ import java.net.URLStreamHandlerFactory;
/*     */ import java.nio.file.Paths;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.CodeSource;
/*     */ import java.security.PermissionCollection;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.security.ProtectionDomain;
/*     */ import java.security.cert.Certificate;
/*     */ import java.util.HashSet;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import sun.net.www.ParseUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Launcher
/*     */ {
/*  53 */   private static URLStreamHandlerFactory factory = new Factory();
/*  54 */   private static Launcher launcher = new Launcher();
/*     */   
/*  56 */   private static String bootClassPath = System.getProperty("sun.boot.class.path");
/*     */   
/*     */   public static Launcher getLauncher() {
/*  59 */     return launcher;
/*     */   }
/*     */   
/*     */   private ClassLoader loader;
/*     */   private static URLStreamHandler fileHandler;
/*     */   
/*     */   public Launcher() {
/*     */     ExtClassLoader extClassLoader;
/*     */     try {
/*  68 */       extClassLoader = ExtClassLoader.getExtClassLoader();
/*  69 */     } catch (IOException iOException) {
/*  70 */       throw new InternalError("Could not create extension class loader", iOException);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  76 */       this.loader = AppClassLoader.getAppClassLoader(extClassLoader);
/*  77 */     } catch (IOException iOException) {
/*  78 */       throw new InternalError("Could not create application class loader", iOException);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  83 */     Thread.currentThread().setContextClassLoader(this.loader);
/*     */ 
/*     */     
/*  86 */     String str = System.getProperty("java.security.manager");
/*  87 */     if (str != null) {
/*  88 */       SecurityManager securityManager = null;
/*  89 */       if ("".equals(str) || "default".equals(str)) {
/*  90 */         securityManager = new SecurityManager();
/*     */       } else {
/*     */         
/*  93 */         try { securityManager = (SecurityManager)this.loader.loadClass(str).newInstance(); }
/*  94 */         catch (IllegalAccessException illegalAccessException) {  }
/*  95 */         catch (InstantiationException instantiationException) {  }
/*  96 */         catch (ClassNotFoundException classNotFoundException) {  }
/*  97 */         catch (ClassCastException classCastException) {}
/*     */       } 
/*     */       
/* 100 */       if (securityManager != null) {
/* 101 */         System.setSecurityManager(securityManager);
/*     */       } else {
/* 103 */         throw new InternalError("Could not create SecurityManager: " + str);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassLoader getClassLoader() {
/* 113 */     return this.loader;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class ExtClassLoader
/*     */     extends URLClassLoader
/*     */   {
/*     */     static {
/* 122 */       ClassLoader.registerAsParallelCapable();
/*     */     }
/* 124 */     private static volatile ExtClassLoader instance = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static ExtClassLoader getExtClassLoader() throws IOException {
/* 132 */       if (instance == null) {
/* 133 */         synchronized (ExtClassLoader.class) {
/* 134 */           if (instance == null) {
/* 135 */             instance = createExtClassLoader();
/*     */           }
/*     */         } 
/*     */       }
/* 139 */       return instance;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static ExtClassLoader createExtClassLoader() throws IOException {
/*     */       try {
/* 148 */         return AccessController.<ExtClassLoader>doPrivileged(new PrivilegedExceptionAction<ExtClassLoader>()
/*     */             {
/*     */               public Launcher.ExtClassLoader run() throws IOException {
/* 151 */                 File[] arrayOfFile = Launcher.ExtClassLoader.getExtDirs();
/* 152 */                 int i = arrayOfFile.length;
/* 153 */                 for (byte b = 0; b < i; b++) {
/* 154 */                   MetaIndex.registerDirectory(arrayOfFile[b]);
/*     */                 }
/* 156 */                 return new Launcher.ExtClassLoader(arrayOfFile);
/*     */               }
/*     */             });
/* 159 */       } catch (PrivilegedActionException privilegedActionException) {
/* 160 */         throw (IOException)privilegedActionException.getException();
/*     */       } 
/*     */     }
/*     */     
/*     */     void addExtURL(URL param1URL) {
/* 165 */       addURL(param1URL);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ExtClassLoader(File[] param1ArrayOfFile) throws IOException {
/* 172 */       super(getExtURLs(param1ArrayOfFile), (ClassLoader)null, Launcher.factory);
/* 173 */       SharedSecrets.getJavaNetAccess()
/* 174 */         .getURLClassPath(this).initLookupCache(this);
/*     */     }
/*     */     private static File[] getExtDirs() {
/*     */       File[] arrayOfFile;
/* 178 */       String str = System.getProperty("java.ext.dirs");
/*     */       
/* 180 */       if (str != null) {
/* 181 */         StringTokenizer stringTokenizer = new StringTokenizer(str, File.pathSeparator);
/*     */         
/* 183 */         int i = stringTokenizer.countTokens();
/* 184 */         arrayOfFile = new File[i];
/* 185 */         for (byte b = 0; b < i; b++) {
/* 186 */           arrayOfFile[b] = new File(stringTokenizer.nextToken());
/*     */         }
/*     */       } else {
/* 189 */         arrayOfFile = new File[0];
/*     */       } 
/* 191 */       return arrayOfFile;
/*     */     }
/*     */     
/*     */     private static URL[] getExtURLs(File[] param1ArrayOfFile) throws IOException {
/* 195 */       Vector<URL> vector = new Vector();
/* 196 */       for (byte b = 0; b < param1ArrayOfFile.length; b++) {
/* 197 */         String[] arrayOfString = param1ArrayOfFile[b].list();
/* 198 */         if (arrayOfString != null) {
/* 199 */           for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
/* 200 */             if (!arrayOfString[b1].equals("meta-index")) {
/* 201 */               File file = new File(param1ArrayOfFile[b], arrayOfString[b1]);
/* 202 */               vector.add(Launcher.getFileURL(file));
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/* 207 */       URL[] arrayOfURL = new URL[vector.size()];
/* 208 */       vector.copyInto((Object[])arrayOfURL);
/* 209 */       return arrayOfURL;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String findLibrary(String param1String) {
/* 220 */       param1String = System.mapLibraryName(param1String);
/* 221 */       URL[] arrayOfURL = getURLs();
/* 222 */       File file = null;
/* 223 */       for (byte b = 0; b < arrayOfURL.length; b++) {
/*     */         URI uRI;
/*     */ 
/*     */         
/*     */         try {
/* 228 */           uRI = arrayOfURL[b].toURI();
/* 229 */         } catch (URISyntaxException uRISyntaxException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 235 */         File file1 = Paths.get(uRI).toFile().getParentFile();
/* 236 */         if (file1 != null && !file1.equals(file)) {
/*     */ 
/*     */           
/* 239 */           String str = VM.getSavedProperty("os.arch");
/* 240 */           if (str != null) {
/* 241 */             File file3 = new File(new File(file1, str), param1String);
/* 242 */             if (file3.exists()) {
/* 243 */               return file3.getAbsolutePath();
/*     */             }
/*     */           } 
/*     */           
/* 247 */           File file2 = new File(file1, param1String);
/* 248 */           if (file2.exists()) {
/* 249 */             return file2.getAbsolutePath();
/*     */           }
/*     */         } 
/* 252 */         file = file1;
/*     */       } 
/* 254 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static AccessControlContext getContext(File[] param1ArrayOfFile) throws IOException {
/* 260 */       PathPermissions pathPermissions = new PathPermissions(param1ArrayOfFile);
/*     */ 
/*     */ 
/*     */       
/* 264 */       ProtectionDomain protectionDomain = new ProtectionDomain(new CodeSource(pathPermissions.getCodeBase(), (Certificate[])null), pathPermissions);
/*     */ 
/*     */ 
/*     */       
/* 268 */       return new AccessControlContext(new ProtectionDomain[] { protectionDomain });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class AppClassLoader
/*     */     extends URLClassLoader
/*     */   {
/*     */     final URLClassPath ucp;
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 282 */       ClassLoader.registerAsParallelCapable();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public static ClassLoader getAppClassLoader(final ClassLoader extcl) throws IOException {
/* 288 */       final String s = System.getProperty("java.class.path");
/* 289 */       final File[] path = (str == null) ? new File[0] : Launcher.getClassPath(str);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 298 */       return AccessController.<ClassLoader>doPrivileged((PrivilegedAction)new PrivilegedAction<AppClassLoader>()
/*     */           {
/*     */             public Launcher.AppClassLoader run()
/*     */             {
/* 302 */               URL[] arrayOfURL = (s == null) ? new URL[0] : Launcher.pathToURLs(path);
/* 303 */               return new Launcher.AppClassLoader(arrayOfURL, extcl);
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     AppClassLoader(URL[] param1ArrayOfURL, ClassLoader param1ClassLoader) {
/* 314 */       super(param1ArrayOfURL, param1ClassLoader, Launcher.factory);
/* 315 */       this.ucp = SharedSecrets.getJavaNetAccess().getURLClassPath(this);
/* 316 */       this.ucp.initLookupCache(this);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Class<?> loadClass(String param1String, boolean param1Boolean) throws ClassNotFoundException {
/* 325 */       int i = param1String.lastIndexOf('.');
/* 326 */       if (i != -1) {
/* 327 */         SecurityManager securityManager = System.getSecurityManager();
/* 328 */         if (securityManager != null) {
/* 329 */           securityManager.checkPackageAccess(param1String.substring(0, i));
/*     */         }
/*     */       } 
/*     */       
/* 333 */       if (this.ucp.knownToNotExist(param1String)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 339 */         Class<?> clazz = findLoadedClass(param1String);
/* 340 */         if (clazz != null) {
/* 341 */           if (param1Boolean) {
/* 342 */             resolveClass(clazz);
/*     */           }
/* 344 */           return clazz;
/*     */         } 
/* 346 */         throw new ClassNotFoundException(param1String);
/*     */       } 
/*     */       
/* 349 */       return super.loadClass(param1String, param1Boolean);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected PermissionCollection getPermissions(CodeSource param1CodeSource) {
/* 357 */       PermissionCollection permissionCollection = super.getPermissions(param1CodeSource);
/* 358 */       permissionCollection.add(new RuntimePermission("exitVM"));
/* 359 */       return permissionCollection;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void appendToClassPathForInstrumentation(String param1String) {
/* 369 */       assert Thread.holdsLock(this);
/*     */ 
/*     */       
/* 372 */       addURL(Launcher.getFileURL(new File(param1String)));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static AccessControlContext getContext(File[] param1ArrayOfFile) throws MalformedURLException {
/* 385 */       PathPermissions pathPermissions = new PathPermissions(param1ArrayOfFile);
/*     */ 
/*     */ 
/*     */       
/* 389 */       ProtectionDomain protectionDomain = new ProtectionDomain(new CodeSource(pathPermissions.getCodeBase(), (Certificate[])null), pathPermissions);
/*     */ 
/*     */ 
/*     */       
/* 393 */       return new AccessControlContext(new ProtectionDomain[] { protectionDomain });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class BootClassPathHolder
/*     */   {
/*     */     static final URLClassPath bcp;
/*     */     
/*     */     static {
/*     */       URL[] arrayOfURL;
/* 404 */       if (Launcher.bootClassPath != null) {
/* 405 */         arrayOfURL = AccessController.<URL[]>doPrivileged((PrivilegedAction)new PrivilegedAction<URL[]>()
/*     */             {
/*     */               public URL[] run() {
/* 408 */                 File[] arrayOfFile = Launcher.getClassPath(Launcher.bootClassPath);
/* 409 */                 int i = arrayOfFile.length;
/* 410 */                 HashSet<File> hashSet = new HashSet();
/* 411 */                 for (byte b = 0; b < i; b++) {
/* 412 */                   File file = arrayOfFile[b];
/*     */ 
/*     */                   
/* 415 */                   if (!file.isDirectory()) {
/* 416 */                     file = file.getParentFile();
/*     */                   }
/* 418 */                   if (file != null && hashSet.add(file)) {
/* 419 */                     MetaIndex.registerDirectory(file);
/*     */                   }
/*     */                 } 
/* 422 */                 return Launcher.pathToURLs(arrayOfFile);
/*     */               }
/*     */             });
/*     */       } else {
/*     */         
/* 427 */         arrayOfURL = new URL[0];
/*     */       } 
/* 429 */       bcp = new URLClassPath(arrayOfURL, Launcher.factory, null);
/* 430 */       bcp.initLookupCache(null);
/*     */     }
/*     */   }
/*     */   
/*     */   public static URLClassPath getBootstrapClassPath() {
/* 435 */     return BootClassPathHolder.bcp;
/*     */   }
/*     */   
/*     */   private static URL[] pathToURLs(File[] paramArrayOfFile) {
/* 439 */     URL[] arrayOfURL = new URL[paramArrayOfFile.length];
/* 440 */     for (byte b = 0; b < paramArrayOfFile.length; b++) {
/* 441 */       arrayOfURL[b] = getFileURL(paramArrayOfFile[b]);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 447 */     return arrayOfURL;
/*     */   }
/*     */   
/*     */   private static File[] getClassPath(String paramString) {
/*     */     File[] arrayOfFile;
/* 452 */     if (paramString != null) {
/* 453 */       byte b1 = 0, b2 = 1;
/* 454 */       int i = 0, j = 0;
/*     */       
/* 456 */       while ((i = paramString.indexOf(File.pathSeparator, j)) != -1) {
/* 457 */         b2++;
/* 458 */         j = i + 1;
/*     */       } 
/* 460 */       arrayOfFile = new File[b2];
/* 461 */       j = i = 0;
/*     */       
/* 463 */       while ((i = paramString.indexOf(File.pathSeparator, j)) != -1) {
/* 464 */         if (i - j > 0) {
/* 465 */           arrayOfFile[b1++] = new File(paramString.substring(j, i));
/*     */         } else {
/*     */           
/* 468 */           arrayOfFile[b1++] = new File(".");
/*     */         } 
/* 470 */         j = i + 1;
/*     */       } 
/*     */       
/* 473 */       if (j < paramString.length()) {
/* 474 */         arrayOfFile[b1++] = new File(paramString.substring(j));
/*     */       } else {
/* 476 */         arrayOfFile[b1++] = new File(".");
/*     */       } 
/*     */       
/* 479 */       if (b1 != b2) {
/* 480 */         File[] arrayOfFile1 = new File[b1];
/* 481 */         System.arraycopy(arrayOfFile, 0, arrayOfFile1, 0, b1);
/* 482 */         arrayOfFile = arrayOfFile1;
/*     */       } 
/*     */     } else {
/* 485 */       arrayOfFile = new File[0];
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 491 */     return arrayOfFile;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static URL getFileURL(File paramFile) {
/*     */     try {
/* 498 */       paramFile = paramFile.getCanonicalFile();
/* 499 */     } catch (IOException iOException) {}
/*     */     
/*     */     try {
/* 502 */       return ParseUtil.fileToEncodedURL(paramFile);
/* 503 */     } catch (MalformedURLException malformedURLException) {
/*     */       
/* 505 */       throw new InternalError(malformedURLException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class Factory
/*     */     implements URLStreamHandlerFactory {
/*     */     private Factory() {}
/*     */     
/* 513 */     private static String PREFIX = "sun.net.www.protocol";
/*     */     
/*     */     public URLStreamHandler createURLStreamHandler(String param1String) {
/* 516 */       String str = PREFIX + "." + param1String + ".Handler";
/*     */       try {
/* 518 */         Class<?> clazz = Class.forName(str);
/* 519 */         return (URLStreamHandler)clazz.newInstance();
/* 520 */       } catch (ReflectiveOperationException reflectiveOperationException) {
/* 521 */         throw new InternalError("could not load " + param1String + "system protocol handler", reflectiveOperationException);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\misc\Launcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */