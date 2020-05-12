/*      */ package java.lang;
/*      */ 
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.Console;
/*      */ import java.io.FileDescriptor;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.lang.reflect.Executable;
/*      */ import java.nio.channels.Channel;
/*      */ import java.nio.channels.spi.SelectorProvider;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.PropertyPermission;
/*      */ import sun.misc.JavaLangAccess;
/*      */ import sun.misc.SharedSecrets;
/*      */ import sun.misc.VM;
/*      */ import sun.misc.Version;
/*      */ import sun.nio.ch.Interruptible;
/*      */ import sun.reflect.CallerSensitive;
/*      */ import sun.reflect.ConstantPool;
/*      */ import sun.reflect.Reflection;
/*      */ import sun.reflect.annotation.AnnotationType;
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
/*      */ public final class System
/*      */ {
/*      */   static {
/*   70 */     registerNatives();
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
/*   83 */   public static final InputStream in = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  110 */   public static final PrintStream out = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  124 */   public static final PrintStream err = null;
/*      */ 
/*      */ 
/*      */   
/*  128 */   private static volatile SecurityManager security = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setIn(InputStream paramInputStream) {
/*  151 */     checkIO();
/*  152 */     setIn0(paramInputStream);
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
/*      */   public static void setOut(PrintStream paramPrintStream) {
/*  175 */     checkIO();
/*  176 */     setOut0(paramPrintStream);
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
/*      */   public static void setErr(PrintStream paramPrintStream) {
/*  199 */     checkIO();
/*  200 */     setErr0(paramPrintStream);
/*      */   }
/*      */   
/*  203 */   private static volatile Console cons = null;
/*      */ 
/*      */   
/*      */   private static Properties props;
/*      */ 
/*      */   
/*      */   private static String lineSeparator;
/*      */ 
/*      */   
/*      */   public static Console console() {
/*  213 */     if (cons == null) {
/*  214 */       synchronized (System.class) {
/*  215 */         cons = SharedSecrets.getJavaIOAccess().console();
/*      */       } 
/*      */     }
/*  218 */     return cons;
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
/*      */   public static Channel inheritedChannel() throws IOException {
/*  247 */     return SelectorProvider.provider().inheritedChannel();
/*      */   }
/*      */   
/*      */   private static void checkIO() {
/*  251 */     SecurityManager securityManager = getSecurityManager();
/*  252 */     if (securityManager != null) {
/*  253 */       securityManager.checkPermission(new RuntimePermission("setIO"));
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
/*      */   public static void setSecurityManager(SecurityManager paramSecurityManager) {
/*      */     try {
/*  287 */       paramSecurityManager.checkPackageAccess("java.lang");
/*  288 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/*  291 */     setSecurityManager0(paramSecurityManager);
/*      */   }
/*      */ 
/*      */   
/*      */   private static synchronized void setSecurityManager0(final SecurityManager s) {
/*  296 */     SecurityManager securityManager = getSecurityManager();
/*  297 */     if (securityManager != null)
/*      */     {
/*      */       
/*  300 */       securityManager.checkPermission(new RuntimePermission("setSecurityManager"));
/*      */     }
/*      */ 
/*      */     
/*  304 */     if (s != null && s.getClass().getClassLoader() != null)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  313 */       AccessController.doPrivileged(new PrivilegedAction() {
/*      */             public Object run() {
/*  315 */               s.getClass().getProtectionDomain()
/*  316 */                 .implies(SecurityConstants.ALL_PERMISSION);
/*  317 */               return null;
/*      */             }
/*      */           });
/*      */     }
/*      */     
/*  322 */     security = s;
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
/*      */   public static SecurityManager getSecurityManager() {
/*  334 */     return security;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Properties getProperties() {
/*  628 */     SecurityManager securityManager = getSecurityManager();
/*  629 */     if (securityManager != null) {
/*  630 */       securityManager.checkPropertiesAccess();
/*      */     }
/*      */     
/*  633 */     return props;
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
/*      */   public static String lineSeparator() {
/*  648 */     return lineSeparator;
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
/*      */   public static void setProperties(Properties paramProperties) {
/*  676 */     SecurityManager securityManager = getSecurityManager();
/*  677 */     if (securityManager != null) {
/*  678 */       securityManager.checkPropertiesAccess();
/*      */     }
/*  680 */     if (paramProperties == null) {
/*  681 */       paramProperties = new Properties();
/*  682 */       initProperties(paramProperties);
/*      */     } 
/*  684 */     props = paramProperties;
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
/*      */   public static String getProperty(String paramString) {
/*  714 */     checkKey(paramString);
/*  715 */     SecurityManager securityManager = getSecurityManager();
/*  716 */     if (securityManager != null) {
/*  717 */       securityManager.checkPropertyAccess(paramString);
/*      */     }
/*      */     
/*  720 */     return props.getProperty(paramString);
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
/*      */   public static String getProperty(String paramString1, String paramString2) {
/*  750 */     checkKey(paramString1);
/*  751 */     SecurityManager securityManager = getSecurityManager();
/*  752 */     if (securityManager != null) {
/*  753 */       securityManager.checkPropertyAccess(paramString1);
/*      */     }
/*      */     
/*  756 */     return props.getProperty(paramString1, paramString2);
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
/*      */   public static String setProperty(String paramString1, String paramString2) {
/*  789 */     checkKey(paramString1);
/*  790 */     SecurityManager securityManager = getSecurityManager();
/*  791 */     if (securityManager != null) {
/*  792 */       securityManager.checkPermission(new PropertyPermission(paramString1, "write"));
/*      */     }
/*      */ 
/*      */     
/*  796 */     return (String)props.setProperty(paramString1, paramString2);
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
/*      */   public static String clearProperty(String paramString) {
/*  827 */     checkKey(paramString);
/*  828 */     SecurityManager securityManager = getSecurityManager();
/*  829 */     if (securityManager != null) {
/*  830 */       securityManager.checkPermission(new PropertyPermission(paramString, "write"));
/*      */     }
/*      */     
/*  833 */     return (String)props.remove(paramString);
/*      */   }
/*      */   
/*      */   private static void checkKey(String paramString) {
/*  837 */     if (paramString == null) {
/*  838 */       throw new NullPointerException("key can't be null");
/*      */     }
/*  840 */     if (paramString.equals("")) {
/*  841 */       throw new IllegalArgumentException("key can't be empty");
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
/*      */   public static String getenv(String paramString) {
/*  892 */     SecurityManager securityManager = getSecurityManager();
/*  893 */     if (securityManager != null) {
/*  894 */       securityManager.checkPermission(new RuntimePermission("getenv." + paramString));
/*      */     }
/*      */     
/*  897 */     return ProcessEnvironment.getenv(paramString);
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
/*      */   public static Map<String, String> getenv() {
/*  942 */     SecurityManager securityManager = getSecurityManager();
/*  943 */     if (securityManager != null) {
/*  944 */       securityManager.checkPermission(new RuntimePermission("getenv.*"));
/*      */     }
/*      */     
/*  947 */     return ProcessEnvironment.getenv();
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
/*      */   public static void exit(int paramInt) {
/*  971 */     Runtime.getRuntime().exit(paramInt);
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
/*      */   public static void gc() {
/*  993 */     Runtime.getRuntime().gc();
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
/*      */   public static void runFinalization() {
/* 1015 */     Runtime.getRuntime().runFinalization();
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
/*      */   @Deprecated
/*      */   public static void runFinalizersOnExit(boolean paramBoolean) {
/* 1045 */     Runtime.runFinalizersOnExit(paramBoolean);
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
/*      */   @CallerSensitive
/*      */   public static void load(String paramString) {
/* 1086 */     Runtime.getRuntime().load0(Reflection.getCallerClass(), paramString);
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
/*      */   @CallerSensitive
/*      */   public static void loadLibrary(String paramString) {
/* 1122 */     Runtime.getRuntime().loadLibrary0(Reflection.getCallerClass(), paramString);
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
/*      */   private static PrintStream newPrintStream(FileOutputStream paramFileOutputStream, String paramString) {
/* 1143 */     if (paramString != null) {
/*      */       try {
/* 1145 */         return new PrintStream(new BufferedOutputStream(paramFileOutputStream, 128), true, paramString);
/* 1146 */       } catch (UnsupportedEncodingException unsupportedEncodingException) {}
/*      */     }
/* 1148 */     return new PrintStream(new BufferedOutputStream(paramFileOutputStream, 128), true);
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
/*      */   private static void initializeSystemClass() {
/* 1165 */     props = new Properties();
/* 1166 */     initProperties(props);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1182 */     VM.saveAndRemoveProperties(props);
/*      */ 
/*      */     
/* 1185 */     lineSeparator = props.getProperty("line.separator");
/* 1186 */     Version.init();
/*      */     
/* 1188 */     FileInputStream fileInputStream = new FileInputStream(FileDescriptor.in);
/* 1189 */     FileOutputStream fileOutputStream1 = new FileOutputStream(FileDescriptor.out);
/* 1190 */     FileOutputStream fileOutputStream2 = new FileOutputStream(FileDescriptor.err);
/* 1191 */     setIn0(new BufferedInputStream(fileInputStream));
/* 1192 */     setOut0(newPrintStream(fileOutputStream1, props.getProperty("sun.stdout.encoding")));
/* 1193 */     setErr0(newPrintStream(fileOutputStream2, props.getProperty("sun.stderr.encoding")));
/*      */ 
/*      */ 
/*      */     
/* 1197 */     loadLibrary("zip");
/*      */ 
/*      */     
/* 1200 */     Terminator.setup();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1206 */     VM.initializeOSEnvironment();
/*      */ 
/*      */ 
/*      */     
/* 1210 */     Thread thread = Thread.currentThread();
/* 1211 */     thread.getThreadGroup().add(thread);
/*      */ 
/*      */     
/* 1214 */     setJavaLangAccess();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1220 */     VM.booted();
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setJavaLangAccess() {
/* 1225 */     SharedSecrets.setJavaLangAccess(new JavaLangAccess() {
/*      */           public ConstantPool getConstantPool(Class<?> param1Class) {
/* 1227 */             return param1Class.getConstantPool();
/*      */           }
/*      */           public boolean casAnnotationType(Class<?> param1Class, AnnotationType param1AnnotationType1, AnnotationType param1AnnotationType2) {
/* 1230 */             return param1Class.casAnnotationType(param1AnnotationType1, param1AnnotationType2);
/*      */           }
/*      */           public AnnotationType getAnnotationType(Class<?> param1Class) {
/* 1233 */             return param1Class.getAnnotationType();
/*      */           }
/*      */           public Map<Class<? extends Annotation>, Annotation> getDeclaredAnnotationMap(Class<?> param1Class) {
/* 1236 */             return param1Class.getDeclaredAnnotationMap();
/*      */           }
/*      */           public byte[] getRawClassAnnotations(Class<?> param1Class) {
/* 1239 */             return param1Class.getRawAnnotations();
/*      */           }
/*      */           public byte[] getRawClassTypeAnnotations(Class<?> param1Class) {
/* 1242 */             return param1Class.getRawTypeAnnotations();
/*      */           }
/*      */           public byte[] getRawExecutableTypeAnnotations(Executable param1Executable) {
/* 1245 */             return Class.getExecutableTypeAnnotationBytes(param1Executable);
/*      */           }
/*      */           
/*      */           public <E extends Enum<E>> E[] getEnumConstantsShared(Class<E> param1Class) {
/* 1249 */             return param1Class.getEnumConstantsShared();
/*      */           }
/*      */           public void blockedOn(Thread param1Thread, Interruptible param1Interruptible) {
/* 1252 */             param1Thread.blockedOn(param1Interruptible);
/*      */           }
/*      */           public void registerShutdownHook(int param1Int, boolean param1Boolean, Runnable param1Runnable) {
/* 1255 */             Shutdown.add(param1Int, param1Boolean, param1Runnable);
/*      */           }
/*      */           public int getStackTraceDepth(Throwable param1Throwable) {
/* 1258 */             return param1Throwable.getStackTraceDepth();
/*      */           }
/*      */           public StackTraceElement getStackTraceElement(Throwable param1Throwable, int param1Int) {
/* 1261 */             return param1Throwable.getStackTraceElement(param1Int);
/*      */           }
/*      */           public String newStringUnsafe(char[] param1ArrayOfchar) {
/* 1264 */             return new String(param1ArrayOfchar, true);
/*      */           }
/*      */           public Thread newThreadWithAcc(Runnable param1Runnable, AccessControlContext param1AccessControlContext) {
/* 1267 */             return new Thread(param1Runnable, param1AccessControlContext);
/*      */           }
/*      */           public void invokeFinalize(Object param1Object) throws Throwable {
/* 1270 */             param1Object.finalize();
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   private static native void registerNatives();
/*      */   
/*      */   private static native void setIn0(InputStream paramInputStream);
/*      */   
/*      */   private static native void setOut0(PrintStream paramPrintStream);
/*      */   
/*      */   private static native void setErr0(PrintStream paramPrintStream);
/*      */   
/*      */   public static native long currentTimeMillis();
/*      */   
/*      */   public static native long nanoTime();
/*      */   
/*      */   public static native void arraycopy(Object paramObject1, int paramInt1, Object paramObject2, int paramInt2, int paramInt3);
/*      */   
/*      */   public static native int identityHashCode(Object paramObject);
/*      */   
/*      */   private static native Properties initProperties(Properties paramProperties);
/*      */   
/*      */   public static native String mapLibraryName(String paramString);
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\lang\System.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */