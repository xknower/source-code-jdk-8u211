/*    */ package sun.misc;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.net.URL;
/*    */ import sun.net.www.ParseUtil;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FileURLMapper
/*    */ {
/*    */   URL url;
/*    */   String file;
/*    */   
/*    */   public FileURLMapper(URL paramURL) {
/* 45 */     this.url = paramURL;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPath() {
/* 54 */     if (this.file != null) {
/* 55 */       return this.file;
/*    */     }
/* 57 */     String str1 = this.url.getHost();
/* 58 */     if (str1 != null && !str1.equals("") && 
/* 59 */       !"localhost".equalsIgnoreCase(str1)) {
/* 60 */       String str3 = this.url.getFile();
/* 61 */       String str4 = str1 + ParseUtil.decode(this.url.getFile());
/* 62 */       this.file = "\\\\" + str4.replace('/', '\\');
/* 63 */       return this.file;
/*    */     } 
/* 65 */     String str2 = this.url.getFile().replace('/', '\\');
/* 66 */     this.file = ParseUtil.decode(str2);
/* 67 */     return this.file;
/*    */   }
/*    */   
/*    */   public boolean exists() {
/* 71 */     String str = getPath();
/* 72 */     File file = new File(str);
/* 73 */     return file.exists();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\misc\FileURLMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */