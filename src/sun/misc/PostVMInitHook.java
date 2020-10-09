/*    */ package sun.misc;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ import jdk.internal.util.EnvUtils;
/*    */ import sun.usagetracker.UsageTrackerClient;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PostVMInitHook
/*    */ {
/*    */   private static final String ORCL_UT_CONFIG_FILE_NAME = "usagetracker.properties";
/*    */   
/*    */   public static void run() {
/* 26 */     trackJavaUsage();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void trackJavaUsage() {
/* 33 */     String str = System.getProperty("os.name");
/* 34 */     if (getConfigFilePrivileged() != null || str
/* 35 */       .toLowerCase().startsWith("win") || str
/* 36 */       .contains("OS X")) {
/* 37 */       UsageTrackerClient usageTrackerClient = new UsageTrackerClient();
/* 38 */       usageTrackerClient.run("VM start", System.getProperty("sun.java.command"));
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static File getConfigFilePrivileged() {
/* 51 */     File file = null;
/* 52 */     String[] arrayOfString = new String[3];
/* 53 */     arrayOfString[0] = System.getProperty("com.oracle.usagetracker.config.file");
/* 54 */     arrayOfString[1] = getOSSpecificConfigFilePath();
/* 55 */     arrayOfString[2] = System.getProperty("java.home") + File.separator + "conf" + File.separator + "management" + File.separator + "usagetracker.properties";
/*    */ 
/*    */     
/* 58 */     for (String str : arrayOfString) {
/* 59 */       if (str != null) {
/* 60 */         file = AccessController.<File>doPrivileged(new PrivilegedAction<File>() {
/*    */               public File run() {
/* 62 */                 File file = new File(path);
/* 63 */                 return file.exists() ? file : null;
/*    */               }
/*    */             });
/* 66 */         if (file != null) {
/*    */           break;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 72 */     return file;
/*    */   }
/*    */   
/*    */   private static String getOSSpecificConfigFilePath() {
/* 76 */     String str = System.getProperty("os.name");
/* 77 */     if (str != null) {
/* 78 */       str = str.toLowerCase();
/* 79 */       if (str.startsWith("sunos"))
/* 80 */         return "/etc/oracle/java/usagetracker.properties"; 
/* 81 */       if (str.startsWith("mac"))
/* 82 */         return "/Library/Application Support/Oracle/Java/usagetracker.properties"; 
/* 83 */       if (str.startsWith("win")) {
/* 84 */         String str1 = getEnvPrivileged("ProgramData");
/* 85 */         return (str1 == null) ? null : (str1 + "\\Oracle\\Java\\" + "usagetracker.properties");
/*    */       } 
/* 87 */       if (str.startsWith("linux")) {
/* 88 */         return "/etc/oracle/java/usagetracker.properties";
/*    */       }
/*    */     } 
/* 91 */     return null;
/*    */   }
/*    */   
/*    */   private static String getEnvPrivileged(final String envName) {
/* 95 */     return AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*    */           public String run() {
/* 97 */             return EnvUtils.getEnvVar(envName);
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\misc\PostVMInitHook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */