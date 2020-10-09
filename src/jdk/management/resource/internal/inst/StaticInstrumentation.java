/*     */ package jdk.management.resource.internal.inst;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.file.CopyOption;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.StandardCopyOption;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.LinkedBlockingDeque;
/*     */ import java.util.jar.JarEntry;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.zip.ZipEntry;
/*     */ import jdk.internal.instrumentation.ClassInstrumentation;
/*     */ import jdk.internal.instrumentation.InstrumentationTarget;
/*     */ import jdk.internal.instrumentation.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StaticInstrumentation
/*     */ {
/*     */   public static void main(String[] paramArrayOfString) throws Exception {
/*  31 */     instrumentClassesForResourceManagement(new File(paramArrayOfString[0]), new File(paramArrayOfString[1]));
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
/*     */   public static void instrumentClassesForResourceManagement(File paramFile1, File paramFile2) throws Exception {
/*  48 */     if (!paramFile1.isDirectory()) {
/*  49 */       throw new Exception(paramFile1 + " is not a directory");
/*     */     }
/*  51 */     if (!paramFile2.isDirectory()) {
/*  52 */       throw new Exception(paramFile1 + " is not a directory");
/*     */     }
/*     */     
/*  55 */     InstrumentationLogger instrumentationLogger = new InstrumentationLogger();
/*  56 */     System.out.println();
/*  57 */     System.out.println("Reading from " + paramFile1);
/*  58 */     System.out.println("Output to " + paramFile2);
/*     */     
/*  60 */     Set<File> set = findAllJarFiles(paramFile1);
/*  61 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*     */     
/*  63 */     System.out.println();
/*  64 */     System.out.println("Searching for classes");
/*  65 */     byte b = 0;
/*  66 */     for (Class<?> clazz : InitInstrumentation.hooks) {
/*  67 */       String str = findTargetClassName(clazz);
/*  68 */       System.out.println(b + ":");
/*  69 */       b++;
/*  70 */       System.out.println("   Instrumentation: " + clazz
/*  71 */           .getName());
/*  72 */       System.out.println("   Target         : " + str);
/*     */       
/*  74 */       boolean bool = false;
/*  75 */       for (File file : set) {
/*  76 */         JarEntry jarEntry = getJarEntry(str, file);
/*  77 */         if (jarEntry != null) {
/*  78 */           System.out.println("   Found in jar  : " + file);
/*  79 */           if (jarEntry.getCodeSigners() != null) {
/*  80 */             throw new Exception("The target class '" + str + "' was found in a signed jar: " + file);
/*     */           }
/*     */ 
/*     */           
/*  84 */           addNewTask((HashMap)hashMap, file, clazz);
/*  85 */           bool = true;
/*     */           break;
/*     */         } 
/*     */       } 
/*  89 */       if (!bool) {
/*  90 */         throw new Exception("The target class '" + str + " was not found in any jar");
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  95 */     System.out.println();
/*  96 */     System.out.println("Instrumenting");
/*  97 */     for (File file1 : hashMap.keySet()) {
/*  98 */       File file2 = new File(paramFile2, file1.getName());
/*  99 */       Files.copy(file1.toPath(), file2.toPath(), new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
/*     */       
/* 101 */       System.out.println("   Jar     : " + file1);
/* 102 */       System.out.println("   Jar copy: " + file2);
/* 103 */       ArrayList<File> arrayList = new ArrayList();
/*     */       
/* 105 */       for (Class<?> clazz : (Iterable<Class<?>>)hashMap.get(file1)) {
/* 106 */         String str = findTargetClassName(clazz);
/* 107 */         System.out.println("      Class: " + str);
/* 108 */         byte[] arrayOfByte1 = findSourceBytesFor(str, file2);
/*     */ 
/*     */ 
/*     */         
/* 112 */         byte[] arrayOfByte2 = (new ClassInstrumentation(clazz, str, arrayOfByte1, instrumentationLogger)).getNewBytes();
/* 113 */         File file = createOutputFile(paramFile2, str);
/* 114 */         writeOutputClass(file, arrayOfByte2);
/* 115 */         arrayList.add(file);
/*     */       } 
/*     */       
/* 118 */       System.out.println("   Updating jar");
/* 119 */       updateJar(paramFile2, file2, arrayList);
/* 120 */       System.out.println();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void updateJar(File paramFile1, File paramFile2, List<File> paramList) throws InterruptedException, IOException {
/* 126 */     String str = System.getProperty("java.home") + File.separator + "bin" + File.separator + "jar";
/*     */ 
/*     */     
/* 129 */     ProcessBuilder processBuilder = new ProcessBuilder(new String[] { str, "uvf", paramFile2.getAbsolutePath() });
/* 130 */     for (File file : paramList) {
/*     */       
/* 132 */       String str1 = paramFile1.toPath().relativize(file.toPath()).toString();
/* 133 */       processBuilder.command().add(str1);
/*     */     } 
/* 135 */     processBuilder.directory(paramFile1);
/* 136 */     processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
/* 137 */     processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
/*     */     
/* 139 */     System.out.println("Executing: " + (String)processBuilder
/* 140 */         .command().stream().collect(Collectors.joining(" ")));
/*     */     
/* 142 */     Process process = processBuilder.start();
/* 143 */     process.waitFor();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addNewTask(HashMap<File, List<Class<?>>> paramHashMap, File paramFile, Class<?> paramClass) {
/* 148 */     List<Class<?>> list = paramHashMap.get(paramFile);
/* 149 */     if (list == null) {
/* 150 */       list = new ArrayList();
/* 151 */       paramHashMap.put(paramFile, list);
/*     */     } 
/* 153 */     list.add(paramClass);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Set<File> findAllJarFiles(File paramFile) throws IOException {
/* 158 */     HashSet<File> hashSet = new HashSet();
/* 159 */     LinkedBlockingDeque<File> linkedBlockingDeque = new LinkedBlockingDeque();
/* 160 */     linkedBlockingDeque.add(paramFile);
/*     */     File file;
/* 162 */     while ((file = linkedBlockingDeque.poll()) != null) {
/* 163 */       for (File file1 : file.listFiles()) {
/* 164 */         if (file1.isDirectory()) {
/* 165 */           linkedBlockingDeque.add(file1);
/* 166 */         } else if (file1.getName().endsWith(".jar")) {
/* 167 */           hashSet.add(file1);
/*     */         } 
/*     */       } 
/*     */     } 
/* 171 */     return hashSet;
/*     */   }
/*     */ 
/*     */   
/*     */   private static File createOutputFile(File paramFile, String paramString) {
/* 176 */     File file = new File(paramFile, paramString.replace(".", File.separator) + ".class");
/*     */     
/* 178 */     file.getParentFile().mkdirs();
/* 179 */     return file;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void writeOutputClass(File paramFile, byte[] paramArrayOfbyte) throws FileNotFoundException, IOException {
/* 184 */     try (FileOutputStream null = new FileOutputStream(paramFile)) {
/* 185 */       fileOutputStream.write(paramArrayOfbyte);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String findTargetClassName(Class<?> paramClass) {
/* 190 */     return ((InstrumentationTarget)paramClass.<InstrumentationTarget>getAnnotation(InstrumentationTarget.class))
/* 191 */       .value();
/*     */   }
/*     */ 
/*     */   
/*     */   private static JarEntry getJarEntry(String paramString, File paramFile) throws Exception {
/* 196 */     try (JarFile null = new JarFile(paramFile)) {
/* 197 */       String str = paramString.replace(".", "/") + ".class";
/* 198 */       JarEntry jarEntry = jarFile.getJarEntry(str);
/* 199 */       return jarEntry;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static byte[] findSourceBytesFor(String paramString, File paramFile) throws Exception {
/* 205 */     try (JarFile null = new JarFile(paramFile)) {
/* 206 */       String str = paramString.replace(".", "/") + ".class";
/* 207 */       ZipEntry zipEntry = jarFile.getEntry(str);
/* 208 */       if (zipEntry == null) {
/* 209 */         return null;
/*     */       }
/* 211 */       byte[] arrayOfByte = readBytes(jarFile.getInputStream(zipEntry));
/* 212 */       return arrayOfByte;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static byte[] readBytes(InputStream paramInputStream) throws IOException {
/* 217 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 218 */     byte[] arrayOfByte = new byte[1024];
/*     */     int i;
/* 220 */     while ((i = paramInputStream.read(arrayOfByte)) != -1) {
/* 221 */       byteArrayOutputStream.write(arrayOfByte, 0, i);
/*     */     }
/* 223 */     return byteArrayOutputStream.toByteArray();
/*     */   }
/*     */   
/*     */   static class InstrumentationLogger
/*     */     implements Logger
/*     */   {
/*     */     public void error(String param1String) {
/* 230 */       System.err.println("StaticInstrumentation error: " + param1String);
/*     */     }
/*     */ 
/*     */     
/*     */     public void warn(String param1String) {
/* 235 */       System.err.println("StaticInstrumentation warning: " + param1String);
/*     */     }
/*     */ 
/*     */     
/*     */     public void info(String param1String) {
/* 240 */       System.err.println("StaticInstrumentation info: " + param1String);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void debug(String param1String) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void trace(String param1String) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void error(String param1String, Throwable param1Throwable) {
/* 255 */       System.err.println("StaticInstrumentation error: " + param1String + ": " + param1Throwable);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\StaticInstrumentation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */