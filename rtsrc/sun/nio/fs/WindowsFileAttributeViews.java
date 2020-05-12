/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.file.attribute.BasicFileAttributes;
/*     */ import java.nio.file.attribute.DosFileAttributeView;
/*     */ import java.nio.file.attribute.DosFileAttributes;
/*     */ import java.nio.file.attribute.FileTime;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WindowsFileAttributeViews
/*     */ {
/*     */   private static class Basic
/*     */     extends AbstractBasicFileAttributeView
/*     */   {
/*     */     final WindowsPath file;
/*     */     final boolean followLinks;
/*     */     
/*     */     Basic(WindowsPath param1WindowsPath, boolean param1Boolean) {
/*  43 */       this.file = param1WindowsPath;
/*  44 */       this.followLinks = param1Boolean;
/*     */     }
/*     */ 
/*     */     
/*     */     public WindowsFileAttributes readAttributes() throws IOException {
/*  49 */       this.file.checkRead();
/*     */       try {
/*  51 */         return WindowsFileAttributes.get(this.file, this.followLinks);
/*  52 */       } catch (WindowsException windowsException) {
/*  53 */         windowsException.rethrowAsIOException(this.file);
/*  54 */         return null;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private long adjustForFatEpoch(long param1Long) {
/*  64 */       if (param1Long != -1L && param1Long < 119600064000000000L) {
/*  65 */         return 119600064000000000L;
/*     */       }
/*  67 */       return param1Long;
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
/*     */     void setFileTimes(long param1Long1, long param1Long2, long param1Long3) throws IOException {
/*  79 */       long l = -1L;
/*     */       try {
/*  81 */         int i = 33554432;
/*  82 */         if (!this.followLinks && this.file.getFileSystem().supportsLinks()) {
/*  83 */           i |= 0x200000;
/*     */         }
/*  85 */         l = WindowsNativeDispatcher.CreateFile(this.file.getPathForWin32Calls(), 256, 7, 3, i);
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*  90 */       catch (WindowsException windowsException) {
/*  91 */         windowsException.rethrowAsIOException(this.file);
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/*  96 */         WindowsNativeDispatcher.SetFileTime(l, param1Long1, param1Long2, param1Long3);
/*     */ 
/*     */       
/*     */       }
/* 100 */       catch (WindowsException windowsException) {
/*     */ 
/*     */         
/* 103 */         if (this.followLinks && windowsException.lastError() == 87) {
/*     */           
/* 105 */           try { if (WindowsFileStore.create(this.file).type().equals("FAT")) {
/* 106 */               WindowsNativeDispatcher.SetFileTime(l, 
/* 107 */                   adjustForFatEpoch(param1Long1), 
/* 108 */                   adjustForFatEpoch(param1Long2), 
/* 109 */                   adjustForFatEpoch(param1Long3));
/*     */               
/* 111 */               windowsException = null;
/*     */             }  }
/* 113 */           catch (SecurityException securityException) {  }
/* 114 */           catch (WindowsException windowsException1) {  }
/* 115 */           catch (IOException iOException) {}
/*     */         }
/*     */ 
/*     */         
/* 119 */         if (windowsException != null)
/* 120 */           windowsException.rethrowAsIOException(this.file); 
/*     */       } finally {
/* 122 */         WindowsNativeDispatcher.CloseHandle(l);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setTimes(FileTime param1FileTime1, FileTime param1FileTime2, FileTime param1FileTime3) throws IOException {
/* 132 */       if (param1FileTime1 == null && param1FileTime2 == null && param1FileTime3 == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 140 */       this.file.checkWrite();
/*     */ 
/*     */ 
/*     */       
/* 144 */       long l1 = (param1FileTime3 == null) ? -1L : WindowsFileAttributes.toWindowsTime(param1FileTime3);
/*     */       
/* 146 */       long l2 = (param1FileTime2 == null) ? -1L : WindowsFileAttributes.toWindowsTime(param1FileTime2);
/*     */       
/* 148 */       long l3 = (param1FileTime1 == null) ? -1L : WindowsFileAttributes.toWindowsTime(param1FileTime1);
/* 149 */       setFileTimes(l1, l2, l3);
/*     */     }
/*     */   }
/*     */   
/*     */   static class Dos
/*     */     extends Basic
/*     */     implements DosFileAttributeView
/*     */   {
/*     */     private static final String READONLY_NAME = "readonly";
/*     */     private static final String ARCHIVE_NAME = "archive";
/*     */     private static final String SYSTEM_NAME = "system";
/*     */     private static final String HIDDEN_NAME = "hidden";
/*     */     private static final String ATTRIBUTES_NAME = "attributes";
/* 162 */     static final Set<String> dosAttributeNames = Util.newSet(basicAttributeNames, new String[] { "readonly", "archive", "system", "hidden", "attributes" });
/*     */ 
/*     */     
/*     */     Dos(WindowsPath param1WindowsPath, boolean param1Boolean) {
/* 166 */       super(param1WindowsPath, param1Boolean);
/*     */     }
/*     */ 
/*     */     
/*     */     public String name() {
/* 171 */       return "dos";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setAttribute(String param1String, Object param1Object) throws IOException {
/* 178 */       if (param1String.equals("readonly")) {
/* 179 */         setReadOnly(((Boolean)param1Object).booleanValue());
/*     */         return;
/*     */       } 
/* 182 */       if (param1String.equals("archive")) {
/* 183 */         setArchive(((Boolean)param1Object).booleanValue());
/*     */         return;
/*     */       } 
/* 186 */       if (param1String.equals("system")) {
/* 187 */         setSystem(((Boolean)param1Object).booleanValue());
/*     */         return;
/*     */       } 
/* 190 */       if (param1String.equals("hidden")) {
/* 191 */         setHidden(((Boolean)param1Object).booleanValue());
/*     */         return;
/*     */       } 
/* 194 */       super.setAttribute(param1String, param1Object);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Map<String, Object> readAttributes(String[] param1ArrayOfString) throws IOException {
/* 202 */       AbstractBasicFileAttributeView.AttributesBuilder attributesBuilder = AbstractBasicFileAttributeView.AttributesBuilder.create(dosAttributeNames, param1ArrayOfString);
/* 203 */       WindowsFileAttributes windowsFileAttributes = readAttributes();
/* 204 */       addRequestedBasicAttributes(windowsFileAttributes, attributesBuilder);
/* 205 */       if (attributesBuilder.match("readonly"))
/* 206 */         attributesBuilder.add("readonly", Boolean.valueOf(windowsFileAttributes.isReadOnly())); 
/* 207 */       if (attributesBuilder.match("archive"))
/* 208 */         attributesBuilder.add("archive", Boolean.valueOf(windowsFileAttributes.isArchive())); 
/* 209 */       if (attributesBuilder.match("system"))
/* 210 */         attributesBuilder.add("system", Boolean.valueOf(windowsFileAttributes.isSystem())); 
/* 211 */       if (attributesBuilder.match("hidden"))
/* 212 */         attributesBuilder.add("hidden", Boolean.valueOf(windowsFileAttributes.isHidden())); 
/* 213 */       if (attributesBuilder.match("attributes"))
/* 214 */         attributesBuilder.add("attributes", Integer.valueOf(windowsFileAttributes.attributes())); 
/* 215 */       return attributesBuilder.unmodifiableMap();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void updateAttributes(int param1Int, boolean param1Boolean) throws IOException {
/* 224 */       this.file.checkWrite();
/*     */ 
/*     */ 
/*     */       
/* 228 */       String str = WindowsLinkSupport.getFinalPath(this.file, this.followLinks);
/*     */       try {
/* 230 */         int i = WindowsNativeDispatcher.GetFileAttributes(str);
/* 231 */         int j = i;
/* 232 */         if (param1Boolean) {
/* 233 */           j |= param1Int;
/*     */         } else {
/* 235 */           j &= param1Int ^ 0xFFFFFFFF;
/*     */         } 
/* 237 */         if (j != i) {
/* 238 */           WindowsNativeDispatcher.SetFileAttributes(str, j);
/*     */         }
/* 240 */       } catch (WindowsException windowsException) {
/*     */         
/* 242 */         windowsException.rethrowAsIOException(this.file);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void setReadOnly(boolean param1Boolean) throws IOException {
/* 248 */       updateAttributes(1, param1Boolean);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setHidden(boolean param1Boolean) throws IOException {
/* 253 */       updateAttributes(2, param1Boolean);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setArchive(boolean param1Boolean) throws IOException {
/* 258 */       updateAttributes(32, param1Boolean);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setSystem(boolean param1Boolean) throws IOException {
/* 263 */       updateAttributes(4, param1Boolean);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void setAttributes(WindowsFileAttributes param1WindowsFileAttributes) throws IOException {
/* 272 */       int i = 0;
/* 273 */       if (param1WindowsFileAttributes.isReadOnly()) i |= 0x1; 
/* 274 */       if (param1WindowsFileAttributes.isHidden()) i |= 0x2; 
/* 275 */       if (param1WindowsFileAttributes.isArchive()) i |= 0x20; 
/* 276 */       if (param1WindowsFileAttributes.isSystem()) i |= 0x4; 
/* 277 */       updateAttributes(i, true);
/*     */ 
/*     */ 
/*     */       
/* 281 */       setFileTimes(
/* 282 */           WindowsFileAttributes.toWindowsTime(param1WindowsFileAttributes.creationTime()), 
/* 283 */           WindowsFileAttributes.toWindowsTime(param1WindowsFileAttributes.lastModifiedTime()), 
/* 284 */           WindowsFileAttributes.toWindowsTime(param1WindowsFileAttributes.lastAccessTime()));
/*     */     }
/*     */   }
/*     */   
/*     */   static Basic createBasicView(WindowsPath paramWindowsPath, boolean paramBoolean) {
/* 289 */     return new Basic(paramWindowsPath, paramBoolean);
/*     */   }
/*     */   
/*     */   static Dos createDosView(WindowsPath paramWindowsPath, boolean paramBoolean) {
/* 293 */     return new Dos(paramWindowsPath, paramBoolean);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\fs\WindowsFileAttributeViews.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */