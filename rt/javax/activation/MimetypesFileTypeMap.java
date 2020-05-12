/*     */ package javax.activation;
/*     */ 
/*     */ import com.sun.activation.registries.LogSupport;
/*     */ import com.sun.activation.registries.MimeTypeFile;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.Vector;
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
/*     */ public class MimetypesFileTypeMap
/*     */   extends FileTypeMap
/*     */ {
/*     */   private MimeTypeFile[] DB;
/*     */   private static final int PROG = 0;
/*  76 */   private static String defaultType = "application/octet-stream";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MimetypesFileTypeMap() {
/*  82 */     Vector<MimeTypeFile> dbv = new Vector(5);
/*  83 */     MimeTypeFile mf = null;
/*  84 */     dbv.addElement(null);
/*     */     
/*  86 */     LogSupport.log("MimetypesFileTypeMap: load HOME");
/*     */     try {
/*  88 */       String user_home = System.getProperty("user.home");
/*     */       
/*  90 */       if (user_home != null) {
/*  91 */         String path = user_home + File.separator + ".mime.types";
/*  92 */         mf = loadFile(path);
/*  93 */         if (mf != null)
/*  94 */           dbv.addElement(mf); 
/*     */       } 
/*  96 */     } catch (SecurityException securityException) {}
/*     */     
/*  98 */     LogSupport.log("MimetypesFileTypeMap: load SYS");
/*     */     
/*     */     try {
/* 101 */       String system_mimetypes = System.getProperty("java.home") + File.separator + "lib" + File.separator + "mime.types";
/*     */       
/* 103 */       mf = loadFile(system_mimetypes);
/* 104 */       if (mf != null)
/* 105 */         dbv.addElement(mf); 
/* 106 */     } catch (SecurityException securityException) {}
/*     */     
/* 108 */     LogSupport.log("MimetypesFileTypeMap: load JAR");
/*     */     
/* 110 */     loadAllResources(dbv, "META-INF/mime.types");
/*     */     
/* 112 */     LogSupport.log("MimetypesFileTypeMap: load DEF");
/* 113 */     mf = loadResource("/META-INF/mimetypes.default");
/*     */     
/* 115 */     if (mf != null) {
/* 116 */       dbv.addElement(mf);
/*     */     }
/* 118 */     this.DB = new MimeTypeFile[dbv.size()];
/* 119 */     dbv.copyInto((Object[])this.DB);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MimeTypeFile loadResource(String name) {
/* 126 */     InputStream clis = null;
/*     */     try {
/* 128 */       clis = SecuritySupport.getResourceAsStream(getClass(), name);
/* 129 */       if (clis != null) {
/* 130 */         MimeTypeFile mf = new MimeTypeFile(clis);
/* 131 */         if (LogSupport.isLoggable()) {
/* 132 */           LogSupport.log("MimetypesFileTypeMap: successfully loaded mime types file: " + name);
/*     */         }
/* 134 */         return mf;
/*     */       } 
/* 136 */       if (LogSupport.isLoggable()) {
/* 137 */         LogSupport.log("MimetypesFileTypeMap: not loading mime types file: " + name);
/*     */       }
/*     */     }
/* 140 */     catch (IOException e) {
/* 141 */       if (LogSupport.isLoggable())
/* 142 */         LogSupport.log("MimetypesFileTypeMap: can't load " + name, e); 
/* 143 */     } catch (SecurityException sex) {
/* 144 */       if (LogSupport.isLoggable())
/* 145 */         LogSupport.log("MimetypesFileTypeMap: can't load " + name, sex); 
/*     */     } finally {
/*     */       try {
/* 148 */         if (clis != null)
/* 149 */           clis.close(); 
/* 150 */       } catch (IOException iOException) {}
/*     */     } 
/* 152 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadAllResources(Vector<MimeTypeFile> v, String name) {
/* 159 */     boolean anyLoaded = false;
/*     */     try {
/*     */       URL[] urls;
/* 162 */       ClassLoader cld = null;
/*     */       
/* 164 */       cld = SecuritySupport.getContextClassLoader();
/* 165 */       if (cld == null)
/* 166 */         cld = getClass().getClassLoader(); 
/* 167 */       if (cld != null) {
/* 168 */         urls = SecuritySupport.getResources(cld, name);
/*     */       } else {
/* 170 */         urls = SecuritySupport.getSystemResources(name);
/* 171 */       }  if (urls != null) {
/* 172 */         if (LogSupport.isLoggable())
/* 173 */           LogSupport.log("MimetypesFileTypeMap: getResources"); 
/* 174 */         for (int i = 0; i < urls.length; i++) {
/* 175 */           URL url = urls[i];
/* 176 */           InputStream clis = null;
/* 177 */           if (LogSupport.isLoggable());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
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
/*     */       }
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
/*     */     }
/* 210 */     catch (Exception ex) {
/* 211 */       if (LogSupport.isLoggable()) {
/* 212 */         LogSupport.log("MimetypesFileTypeMap: can't load " + name, ex);
/*     */       }
/*     */     } 
/*     */     
/* 216 */     if (!anyLoaded) {
/* 217 */       LogSupport.log("MimetypesFileTypeMap: !anyLoaded");
/* 218 */       MimeTypeFile mf = loadResource("/" + name);
/* 219 */       if (mf != null) {
/* 220 */         v.addElement(mf);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private MimeTypeFile loadFile(String name) {
/* 228 */     MimeTypeFile mtf = null;
/*     */     
/*     */     try {
/* 231 */       mtf = new MimeTypeFile(name);
/* 232 */     } catch (IOException iOException) {}
/*     */ 
/*     */     
/* 235 */     return mtf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MimetypesFileTypeMap(String mimeTypeFileName) throws IOException {
/* 245 */     this();
/* 246 */     this.DB[0] = new MimeTypeFile(mimeTypeFileName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MimetypesFileTypeMap(InputStream is) {
/* 256 */     this();
/*     */     try {
/* 258 */       this.DB[0] = new MimeTypeFile(is);
/* 259 */     } catch (IOException iOException) {}
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
/*     */   public synchronized void addMimeTypes(String mime_types) {
/* 271 */     if (this.DB[0] == null) {
/* 272 */       this.DB[0] = new MimeTypeFile();
/*     */     }
/* 274 */     this.DB[0].appendToRegistry(mime_types);
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
/*     */   public String getContentType(File f) {
/* 286 */     return getContentType(f.getName());
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
/*     */   public synchronized String getContentType(String filename) {
/* 299 */     int dot_pos = filename.lastIndexOf(".");
/*     */     
/* 301 */     if (dot_pos < 0) {
/* 302 */       return defaultType;
/*     */     }
/* 304 */     String file_ext = filename.substring(dot_pos + 1);
/* 305 */     if (file_ext.length() == 0) {
/* 306 */       return defaultType;
/*     */     }
/* 308 */     for (int i = 0; i < this.DB.length; i++) {
/* 309 */       if (this.DB[i] != null) {
/*     */         
/* 311 */         String result = this.DB[i].getMIMETypeString(file_ext);
/* 312 */         if (result != null)
/* 313 */           return result; 
/*     */       } 
/* 315 */     }  return defaultType;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\activation\MimetypesFileTypeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */