/*     */ package sun.net.www.protocol.jar;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.security.Permission;
/*     */ import java.util.HashMap;
/*     */ import java.util.jar.JarFile;
/*     */ import sun.net.util.URLUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class JarFileFactory
/*     */   implements URLJarFile.URLJarFileCloseController
/*     */ {
/*  46 */   private static final HashMap<String, JarFile> fileCache = new HashMap<>();
/*     */ 
/*     */   
/*  49 */   private static final HashMap<JarFile, URL> urlCache = new HashMap<>();
/*     */   
/*  51 */   private static final JarFileFactory instance = new JarFileFactory();
/*     */ 
/*     */ 
/*     */   
/*     */   public static JarFileFactory getInstance() {
/*  56 */     return instance;
/*     */   }
/*     */   
/*     */   URLConnection getConnection(JarFile paramJarFile) throws IOException {
/*     */     URL uRL;
/*  61 */     synchronized (instance) {
/*  62 */       uRL = urlCache.get(paramJarFile);
/*     */     } 
/*  64 */     if (uRL != null) {
/*  65 */       return uRL.openConnection();
/*     */     }
/*  67 */     return null;
/*     */   }
/*     */   
/*     */   public JarFile get(URL paramURL) throws IOException {
/*  71 */     return get(paramURL, true);
/*     */   }
/*     */   JarFile get(URL paramURL, boolean paramBoolean) throws IOException {
/*     */     JarFile jarFile;
/*  75 */     if (paramURL.getProtocol().equalsIgnoreCase("file")) {
/*     */ 
/*     */       
/*  78 */       String str = paramURL.getHost();
/*  79 */       if (str != null && !str.equals("") && 
/*  80 */         !str.equalsIgnoreCase("localhost"))
/*     */       {
/*  82 */         paramURL = new URL("file", "", "//" + str + paramURL.getPath());
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  89 */     if (paramBoolean) {
/*  90 */       synchronized (instance) {
/*  91 */         jarFile = getCachedJarFile(paramURL);
/*     */       } 
/*  93 */       if (jarFile == null) {
/*  94 */         JarFile jarFile1 = URLJarFile.getJarFile(paramURL, this);
/*  95 */         synchronized (instance) {
/*  96 */           jarFile = getCachedJarFile(paramURL);
/*  97 */           if (jarFile == null) {
/*  98 */             fileCache.put(URLUtil.urlNoFragString(paramURL), jarFile1);
/*  99 */             urlCache.put(jarFile1, paramURL);
/* 100 */             jarFile = jarFile1;
/*     */           }
/* 102 */           else if (jarFile1 != null) {
/* 103 */             jarFile1.close();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 109 */       jarFile = URLJarFile.getJarFile(paramURL, this);
/*     */     } 
/* 111 */     if (jarFile == null) {
/* 112 */       throw new FileNotFoundException(paramURL.toString());
/*     */     }
/* 114 */     return jarFile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close(JarFile paramJarFile) {
/* 123 */     synchronized (instance) {
/* 124 */       URL uRL = urlCache.remove(paramJarFile);
/* 125 */       if (uRL != null)
/* 126 */         fileCache.remove(URLUtil.urlNoFragString(uRL)); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private JarFile getCachedJarFile(URL paramURL) {
/* 131 */     assert Thread.holdsLock(instance);
/* 132 */     JarFile jarFile = fileCache.get(URLUtil.urlNoFragString(paramURL));
/*     */ 
/*     */     
/* 135 */     if (jarFile != null) {
/* 136 */       Permission permission = getPermission(jarFile);
/* 137 */       if (permission != null) {
/* 138 */         SecurityManager securityManager = System.getSecurityManager();
/* 139 */         if (securityManager != null) {
/*     */           try {
/* 141 */             securityManager.checkPermission(permission);
/* 142 */           } catch (SecurityException securityException) {
/*     */ 
/*     */             
/* 145 */             if (permission instanceof java.io.FilePermission && permission
/* 146 */               .getActions().indexOf("read") != -1) {
/* 147 */               securityManager.checkRead(permission.getName());
/* 148 */             } else if (permission instanceof java.net.SocketPermission && permission
/*     */               
/* 150 */               .getActions().indexOf("connect") != -1) {
/* 151 */               securityManager.checkConnect(paramURL.getHost(), paramURL.getPort());
/*     */             } else {
/* 153 */               throw securityException;
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 159 */     return jarFile;
/*     */   }
/*     */   
/*     */   private Permission getPermission(JarFile paramJarFile) {
/*     */     try {
/* 164 */       URLConnection uRLConnection = getConnection(paramJarFile);
/* 165 */       if (uRLConnection != null)
/* 166 */         return uRLConnection.getPermission(); 
/* 167 */     } catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */     
/* 171 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\net\www\protocol\jar\JarFileFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */