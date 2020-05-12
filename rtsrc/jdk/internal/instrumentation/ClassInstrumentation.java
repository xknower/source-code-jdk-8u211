/*     */ package jdk.internal.instrumentation;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FilePermission;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permission;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.PropertyPermission;
/*     */ import jdk.internal.org.objectweb.asm.ClassReader;
/*     */ import jdk.internal.org.objectweb.asm.ClassWriter;
/*     */ import jdk.internal.org.objectweb.asm.tree.ClassNode;
/*     */ import jdk.internal.org.objectweb.asm.util.CheckClassAdapter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ClassInstrumentation
/*     */ {
/*     */   private final Class<?> instrumentor;
/*     */   private final Logger logger;
/*     */   private final String targetName;
/*     */   private final String instrumentorName;
/*     */   private byte[] newBytes;
/*     */   private final ClassReader targetClassReader;
/*     */   private final ClassReader instrClassReader;
/*     */   
/*  49 */   private static final String JAVA_HOME = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */       {
/*     */         public String run() {
/*  52 */           return System.getProperty("java.home");
/*     */         }
/*     */       },  (AccessControlContext)null, new Permission[] { new PropertyPermission("java.home", "read") });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassInstrumentation(Class<?> paramClass, String paramString, byte[] paramArrayOfbyte, Logger paramLogger) throws ClassNotFoundException, IOException {
/*  69 */     this.instrumentorName = paramClass.getName();
/*  70 */     this.targetName = paramString;
/*  71 */     this.instrumentor = paramClass;
/*  72 */     this.logger = paramLogger;
/*     */     
/*  74 */     this.targetClassReader = new ClassReader(paramArrayOfbyte);
/*  75 */     this.instrClassReader = new ClassReader(getInstrumentationInputStream(this.instrumentorName));
/*     */     
/*  77 */     instrument();
/*     */     
/*  79 */     saveGeneratedInstrumentation();
/*     */   }
/*     */   
/*     */   private InputStream getInstrumentationInputStream(final String instrumentorName) throws IOException {
/*     */     try {
/*  84 */       return AccessController.<InputStream>doPrivileged(new PrivilegedExceptionAction<InputStream>()
/*     */           {
/*     */             public InputStream run() throws IOException
/*     */             {
/*  88 */               return Tracer.class.getResourceAsStream("/" + instrumentorName
/*  89 */                   .replace(".", "/") + ".class");
/*     */             }
/*     */           }(AccessControlContext)null, new Permission[] { new FilePermission(JAVA_HOME + File.separator + "-", "read") });
/*     */     
/*     */     }
/*  94 */     catch (PrivilegedActionException privilegedActionException) {
/*  95 */       Exception exception = privilegedActionException.getException();
/*  96 */       if (exception instanceof IOException) {
/*  97 */         throw (IOException)exception;
/*     */       }
/*  99 */       throw (RuntimeException)exception;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void instrument() throws IOException, ClassNotFoundException {
/* 107 */     ArrayList<Method> arrayList = new ArrayList();
/* 108 */     for (Method method : this.instrumentor.getDeclaredMethods()) {
/* 109 */       InstrumentationMethod instrumentationMethod = method.<InstrumentationMethod>getAnnotation(InstrumentationMethod.class);
/* 110 */       if (instrumentationMethod != null) {
/* 111 */         arrayList.add(method);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     MaxLocalsTracker maxLocalsTracker = new MaxLocalsTracker();
/* 120 */     this.instrClassReader.accept(maxLocalsTracker, 0);
/*     */ 
/*     */ 
/*     */     
/* 124 */     ClassNode classNode = new ClassNode();
/* 125 */     Inliner inliner = new Inliner(327680, classNode, this.instrumentorName, this.targetClassReader, arrayList, maxLocalsTracker, this.logger);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 133 */     this.instrClassReader.accept(inliner, 8);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     ClassWriter classWriter = new ClassWriter(2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     MethodMergeAdapter methodMergeAdapter = new MethodMergeAdapter(classWriter, classNode, arrayList, this.instrumentor.<TypeMapping>getAnnotationsByType(TypeMapping.class), this.logger);
/*     */     
/* 146 */     this.targetClassReader.accept(methodMergeAdapter, 8);
/*     */     
/* 148 */     this.newBytes = classWriter.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getNewBytes() {
/* 158 */     return (byte[])this.newBytes.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   private void saveGeneratedInstrumentation() {
/* 163 */     boolean bool = ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*     */         {
/*     */           public Boolean run() {
/* 166 */             return Boolean.valueOf(Boolean.getBoolean("jfr.savegenerated"));
/*     */           }
/*     */         })).booleanValue();
/*     */     
/* 170 */     if (bool) {
/*     */       try {
/* 172 */         writeGeneratedDebugInstrumentation();
/* 173 */       } catch (IOException|ClassNotFoundException iOException) {
/* 174 */         this.logger.info("Unable to create debug instrumentation");
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void writeGeneratedDebugInstrumentation() throws IOException, ClassNotFoundException {
/* 180 */     try (FileOutputStream null = new FileOutputStream(this.targetName + ".class")) {
/* 181 */       fileOutputStream.write(this.newBytes);
/*     */     } 
/*     */     
/* 184 */     try(FileWriter null = new FileWriter(this.targetName + ".asm"); 
/* 185 */         PrintWriter null = new PrintWriter(fileWriter)) {
/* 186 */       ClassReader classReader = new ClassReader(getNewBytes());
/* 187 */       CheckClassAdapter.verify(classReader, true, printWriter);
/*     */     } 
/*     */     
/* 190 */     this.logger.info("Instrumented code saved to " + this.targetName + ".class and .asm");
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\internal\instrumentation\ClassInstrumentation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */