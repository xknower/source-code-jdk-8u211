/*     */ package org.w3c.dom.bootstrap;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import org.w3c.dom.DOMImplementation;
/*     */ import org.w3c.dom.DOMImplementationList;
/*     */ import org.w3c.dom.DOMImplementationSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DOMImplementationRegistry
/*     */ {
/*     */   public static final String PROPERTY = "org.w3c.dom.DOMImplementationSourceList";
/*     */   private static final int DEFAULT_LINE_LENGTH = 80;
/*     */   private Vector sources;
/*     */   private static final String FALLBACK_CLASS = "com.sun.org.apache.xerces.internal.dom.DOMXSImplementationSourceImpl";
/*     */   private static final String DEFAULT_PACKAGE = "com.sun.org.apache.xerces.internal.dom";
/*     */   
/*     */   private DOMImplementationRegistry(Vector srcs) {
/* 114 */     this.sources = srcs;
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
/*     */   public static DOMImplementationRegistry newInstance() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException {
/* 150 */     Vector<DOMImplementationSource> sources = new Vector();
/*     */     
/* 152 */     ClassLoader classLoader = getClassLoader();
/*     */     
/* 154 */     String p = getSystemProperty("org.w3c.dom.DOMImplementationSourceList");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 159 */     if (p == null) {
/* 160 */       p = getServiceValue(classLoader);
/*     */     }
/* 162 */     if (p == null)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 167 */       p = "com.sun.org.apache.xerces.internal.dom.DOMXSImplementationSourceImpl";
/*     */     }
/* 169 */     if (p != null) {
/* 170 */       StringTokenizer st = new StringTokenizer(p);
/* 171 */       while (st.hasMoreTokens()) {
/* 172 */         String sourceName = st.nextToken();
/*     */         
/* 174 */         boolean internal = false;
/* 175 */         if (System.getSecurityManager() != null && 
/* 176 */           sourceName != null && sourceName.startsWith("com.sun.org.apache.xerces.internal.dom")) {
/* 177 */           internal = true;
/*     */         }
/*     */         
/* 180 */         Class<?> sourceClass = null;
/* 181 */         if (classLoader != null && !internal) {
/* 182 */           sourceClass = classLoader.loadClass(sourceName);
/*     */         } else {
/* 184 */           sourceClass = Class.forName(sourceName);
/*     */         } 
/*     */         
/* 187 */         DOMImplementationSource source = (DOMImplementationSource)sourceClass.newInstance();
/* 188 */         sources.addElement(source);
/*     */       } 
/*     */     } 
/* 191 */     return new DOMImplementationRegistry(sources);
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
/*     */   public DOMImplementation getDOMImplementation(String features) {
/* 207 */     int size = this.sources.size();
/* 208 */     String name = null;
/* 209 */     for (int i = 0; i < size; i++) {
/*     */       
/* 211 */       DOMImplementationSource source = this.sources.elementAt(i);
/* 212 */       DOMImplementation impl = source.getDOMImplementation(features);
/* 213 */       if (impl != null) {
/* 214 */         return impl;
/*     */       }
/*     */     } 
/* 217 */     return null;
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
/*     */   public DOMImplementationList getDOMImplementationList(String features) {
/* 232 */     final Vector<DOMImplementation> implementations = new Vector();
/* 233 */     int size = this.sources.size();
/* 234 */     for (int i = 0; i < size; i++) {
/*     */       
/* 236 */       DOMImplementationSource source = this.sources.elementAt(i);
/*     */       
/* 238 */       DOMImplementationList impls = source.getDOMImplementationList(features);
/* 239 */       for (int j = 0; j < impls.getLength(); j++) {
/* 240 */         DOMImplementation impl = impls.item(j);
/* 241 */         implementations.addElement(impl);
/*     */       } 
/*     */     } 
/* 244 */     return new DOMImplementationList() {
/*     */         public DOMImplementation item(int index) {
/* 246 */           if (index >= 0 && index < implementations.size()) {
/*     */             try {
/* 248 */               return implementations
/* 249 */                 .elementAt(index);
/* 250 */             } catch (ArrayIndexOutOfBoundsException e) {
/* 251 */               return null;
/*     */             } 
/*     */           }
/* 254 */           return null;
/*     */         }
/*     */         
/*     */         public int getLength() {
/* 258 */           return implementations.size();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSource(DOMImplementationSource s) {
/* 269 */     if (s == null) {
/* 270 */       throw new NullPointerException();
/*     */     }
/* 272 */     if (!this.sources.contains(s)) {
/* 273 */       this.sources.addElement(s);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ClassLoader getClassLoader() {
/*     */     try {
/* 285 */       ClassLoader contextClassLoader = getContextClassLoader();
/*     */       
/* 287 */       if (contextClassLoader != null) {
/* 288 */         return contextClassLoader;
/*     */       }
/* 290 */     } catch (Exception e) {
/*     */ 
/*     */       
/* 293 */       return DOMImplementationRegistry.class.getClassLoader();
/*     */     } 
/* 295 */     return DOMImplementationRegistry.class.getClassLoader();
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
/*     */   private static String getServiceValue(ClassLoader classLoader) {
/* 307 */     String serviceId = "META-INF/services/org.w3c.dom.DOMImplementationSourceList";
/*     */     
/*     */     try {
/* 310 */       InputStream is = getResourceAsStream(classLoader, serviceId);
/*     */       
/* 312 */       if (is != null) {
/*     */         BufferedReader rd;
/*     */         try {
/* 315 */           rd = new BufferedReader(new InputStreamReader(is, "UTF-8"), 80);
/*     */         
/*     */         }
/* 318 */         catch (UnsupportedEncodingException e) {
/* 319 */           rd = new BufferedReader(new InputStreamReader(is), 80);
/*     */         } 
/*     */ 
/*     */         
/* 323 */         String serviceValue = rd.readLine();
/* 324 */         rd.close();
/* 325 */         if (serviceValue != null && serviceValue.length() > 0) {
/* 326 */           return serviceValue;
/*     */         }
/*     */       } 
/* 329 */     } catch (Exception ex) {
/* 330 */       return null;
/*     */     } 
/* 332 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isJRE11() {
/*     */     try {
/* 342 */       Class<?> c = Class.forName("java.security.AccessController");
/*     */ 
/*     */ 
/*     */       
/* 346 */       return false;
/* 347 */     } catch (Exception exception) {
/*     */ 
/*     */       
/* 350 */       return true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ClassLoader getContextClassLoader() {
/* 360 */     return isJRE11() ? null : 
/*     */ 
/*     */       
/* 363 */       AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>() {
/*     */           public Object run() {
/* 365 */             ClassLoader classLoader = null;
/*     */             
/*     */             try {
/* 368 */               classLoader = Thread.currentThread().getContextClassLoader();
/* 369 */             } catch (SecurityException securityException) {}
/*     */             
/* 371 */             return classLoader;
/*     */           }
/*     */         });
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
/*     */   private static String getSystemProperty(final String name) {
/* 385 */     return isJRE11() ? 
/* 386 */       System.getProperty(name) : 
/* 387 */       AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */           public Object run() {
/* 389 */             return System.getProperty(name);
/*     */           }
/*     */         });
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
/*     */   private static InputStream getResourceAsStream(final ClassLoader classLoader, final String name) {
/* 405 */     if (isJRE11()) {
/*     */       InputStream ris;
/* 407 */       if (classLoader == null) {
/* 408 */         ris = ClassLoader.getSystemResourceAsStream(name);
/*     */       } else {
/* 410 */         ris = classLoader.getResourceAsStream(name);
/*     */       } 
/* 412 */       return ris;
/*     */     } 
/* 414 */     return 
/* 415 */       AccessController.<InputStream>doPrivileged(new PrivilegedAction<InputStream>() {
/*     */           public Object run() {
/*     */             InputStream ris;
/* 418 */             if (classLoader == null) {
/*     */               
/* 420 */               ris = ClassLoader.getSystemResourceAsStream(name);
/*     */             } else {
/* 422 */               ris = classLoader.getResourceAsStream(name);
/*     */             } 
/* 424 */             return ris;
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\w3c\dom\bootstrap\DOMImplementationRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */