/*     */ package com.sun.jmx.snmp.defaults;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultPaths
/*     */ {
/*     */   private static final String INSTALL_PATH_RESOURCE_NAME = "com/sun/jdmk/defaults/install.path";
/*     */   private static String etcDir;
/*     */   private static String tmpDir;
/*     */   private static String installDir;
/*     */   
/*     */   public static String getInstallDir() {
/*  63 */     if (installDir == null) {
/*  64 */       return useRessourceFile();
/*     */     }
/*  66 */     return installDir;
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
/*     */   public static String getInstallDir(String paramString) {
/*  80 */     if (installDir == null) {
/*  81 */       if (paramString == null) {
/*  82 */         return getInstallDir();
/*     */       }
/*  84 */       return getInstallDir() + File.separator + paramString;
/*     */     } 
/*     */     
/*  87 */     if (paramString == null) {
/*  88 */       return installDir;
/*     */     }
/*  90 */     return installDir + File.separator + paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setInstallDir(String paramString) {
/* 101 */     installDir = paramString;
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
/*     */   public static String getEtcDir() {
/* 115 */     if (etcDir == null) {
/* 116 */       return getInstallDir("etc");
/*     */     }
/* 118 */     return etcDir;
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
/*     */   public static String getEtcDir(String paramString) {
/* 134 */     if (etcDir == null) {
/* 135 */       if (paramString == null) {
/* 136 */         return getEtcDir();
/*     */       }
/* 138 */       return getEtcDir() + File.separator + paramString;
/*     */     } 
/*     */     
/* 141 */     if (paramString == null) {
/* 142 */       return etcDir;
/*     */     }
/* 144 */     return etcDir + File.separator + paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setEtcDir(String paramString) {
/* 155 */     etcDir = paramString;
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
/*     */   public static String getTmpDir() {
/* 169 */     if (tmpDir == null) {
/* 170 */       return getInstallDir("tmp");
/*     */     }
/* 172 */     return tmpDir;
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
/*     */   public static String getTmpDir(String paramString) {
/* 188 */     if (tmpDir == null) {
/* 189 */       if (paramString == null) {
/* 190 */         return getTmpDir();
/*     */       }
/* 192 */       return getTmpDir() + File.separator + paramString;
/*     */     } 
/*     */     
/* 195 */     if (paramString == null) {
/* 196 */       return tmpDir;
/*     */     }
/* 198 */     return tmpDir + File.separator + paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setTmpDir(String paramString) {
/* 209 */     tmpDir = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String useRessourceFile() {
/* 217 */     InputStream inputStream = null;
/* 218 */     BufferedReader bufferedReader = null;
/*     */ 
/*     */     
/* 221 */     try { inputStream = DefaultPaths.class.getClassLoader().getResourceAsStream("com/sun/jdmk/defaults/install.path");
/* 222 */       if (inputStream == null) return null; 
/* 223 */       bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
/* 224 */       installDir = bufferedReader.readLine(); }
/* 225 */     catch (Exception exception)
/*     */     
/*     */     { 
/*     */       try {
/* 229 */         if (inputStream != null) inputStream.close(); 
/* 230 */         if (bufferedReader != null) bufferedReader.close(); 
/* 231 */       } catch (Exception exception1) {} } finally { try { if (inputStream != null) inputStream.close();  if (bufferedReader != null) bufferedReader.close();  } catch (Exception exception) {} }
/*     */     
/* 233 */     return installDir;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\defaults\DefaultPaths.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */