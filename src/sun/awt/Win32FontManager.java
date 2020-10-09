/*     */ package sun.awt;
/*     */ 
/*     */ import java.awt.FontFormatException;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.io.File;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.StringTokenizer;
/*     */ import sun.awt.windows.WFontConfiguration;
/*     */ import sun.font.SunFontManager;
/*     */ import sun.font.TrueTypeFont;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Win32FontManager
/*     */   extends SunFontManager
/*     */ {
/*     */   private static TrueTypeFont eudcFont;
/*     */   
/*     */   static {
/*  56 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */         {
/*     */           public Object run() {
/*  59 */             String str = Win32FontManager.getEUDCFontFile();
/*  60 */             if (str != null) {
/*     */               
/*     */               try {
/*     */ 
/*     */                 
/*  65 */                 Win32FontManager.eudcFont = new TrueTypeFont(str, null, 0, true, false);
/*     */               }
/*  67 */               catch (FontFormatException fontFormatException) {}
/*     */             }
/*     */             
/*  70 */             return null;
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
/*     */   public TrueTypeFont getEUDCFont() {
/*  85 */     return eudcFont;
/*     */   }
/*     */ 
/*     */   
/*     */   public Win32FontManager() {
/*  90 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */         {
/*     */ 
/*     */ 
/*     */           
/*     */           public Object run()
/*     */           {
/*  97 */             Win32FontManager.this.registerJREFontsWithPlatform(SunFontManager.jreFontDirName);
/*  98 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean useAbsoluteFontFileNames() {
/* 108 */     return false;
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
/*     */   protected void registerFontFile(String paramString, String[] paramArrayOfString, int paramInt, boolean paramBoolean) {
/*     */     boolean bool1;
/* 121 */     if (this.registeredFontFiles.contains(paramString)) {
/*     */       return;
/*     */     }
/* 124 */     this.registeredFontFiles.add(paramString);
/*     */ 
/*     */     
/* 127 */     if (getTrueTypeFilter().accept(null, paramString)) {
/* 128 */       bool1 = false;
/* 129 */     } else if (getType1Filter().accept(null, paramString)) {
/* 130 */       bool1 = true;
/*     */     } else {
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 136 */     if (this.fontPath == null) {
/* 137 */       this.fontPath = getPlatformFontPath(noType1Font);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     String str = jreFontDirName + File.pathSeparator + this.fontPath;
/* 145 */     StringTokenizer stringTokenizer = new StringTokenizer(str, File.pathSeparator);
/*     */ 
/*     */     
/* 148 */     boolean bool2 = false;
/*     */     try {
/* 150 */       while (!bool2 && stringTokenizer.hasMoreTokens()) {
/* 151 */         String str1 = stringTokenizer.nextToken();
/* 152 */         boolean bool = str1.equals(jreFontDirName);
/* 153 */         File file = new File(str1, paramString);
/* 154 */         if (file.canRead()) {
/* 155 */           bool2 = true;
/* 156 */           String str2 = file.getAbsolutePath();
/* 157 */           if (paramBoolean) {
/* 158 */             registerDeferredFont(paramString, str2, paramArrayOfString, bool1, bool, paramInt);
/*     */             
/*     */             break;
/*     */           } 
/*     */           
/* 163 */           registerFontFile(str2, paramArrayOfString, bool1, bool, paramInt);
/*     */ 
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 170 */     } catch (NoSuchElementException noSuchElementException) {
/* 171 */       System.err.println(noSuchElementException);
/*     */     } 
/* 173 */     if (!bool2) {
/* 174 */       addToMissingFontFileList(paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected FontConfiguration createFontConfiguration() {
/* 181 */     WFontConfiguration wFontConfiguration = new WFontConfiguration(this);
/* 182 */     wFontConfiguration.init();
/* 183 */     return wFontConfiguration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontConfiguration createFontConfiguration(boolean paramBoolean1, boolean paramBoolean2) {
/* 190 */     return new WFontConfiguration(this, paramBoolean1, paramBoolean2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void populateFontFileNameMap(HashMap<String, String> paramHashMap1, HashMap<String, String> paramHashMap2, HashMap<String, ArrayList<String>> paramHashMap, Locale paramLocale) {
/* 201 */     populateFontFileNameMap0(paramHashMap1, paramHashMap2, paramHashMap, paramLocale);
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
/*     */   protected String[] getDefaultPlatformFont() {
/* 217 */     String[] arrayOfString1 = new String[2];
/* 218 */     arrayOfString1[0] = "Arial";
/* 219 */     arrayOfString1[1] = "c:\\windows\\fonts";
/* 220 */     final String[] dirs = getPlatformFontDirs(true);
/* 221 */     if (arrayOfString2.length > 1) {
/*     */       
/* 223 */       String str = AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */             public Object run() {
/* 225 */               for (byte b = 0; b < dirs.length; b++) {
/* 226 */                 String str = dirs[b] + File.separator + "arial.ttf";
/*     */                 
/* 228 */                 File file = new File(str);
/* 229 */                 if (file.exists()) {
/* 230 */                   return dirs[b];
/*     */                 }
/*     */               } 
/* 233 */               return null;
/*     */             }
/*     */           });
/* 236 */       if (str != null) {
/* 237 */         arrayOfString1[1] = str;
/*     */       }
/*     */     } else {
/* 240 */       arrayOfString1[1] = arrayOfString2[0];
/*     */     } 
/* 242 */     arrayOfString1[1] = arrayOfString1[1] + File.separator + "arial.ttf";
/* 243 */     return arrayOfString1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 251 */   static String fontsForPrinting = null;
/*     */   protected void registerJREFontsWithPlatform(String paramString) {
/* 253 */     fontsForPrinting = paramString;
/*     */   }
/*     */   
/*     */   public static void registerJREFontsForPrinting() {
/*     */     final String pathName;
/* 258 */     synchronized (Win32GraphicsEnvironment.class) {
/* 259 */       GraphicsEnvironment.getLocalGraphicsEnvironment();
/* 260 */       if (fontsForPrinting == null) {
/*     */         return;
/*     */       }
/* 263 */       str = fontsForPrinting;
/* 264 */       fontsForPrinting = null;
/*     */     } 
/* 266 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */         {
/*     */           public Object run() {
/* 269 */             File file = new File(pathName);
/* 270 */             String[] arrayOfString = file.list(SunFontManager.getInstance()
/* 271 */                 .getTrueTypeFilter());
/* 272 */             if (arrayOfString == null) {
/* 273 */               return null;
/*     */             }
/* 275 */             for (byte b = 0; b < arrayOfString.length; b++) {
/* 276 */               File file1 = new File(file, arrayOfString[b]);
/* 277 */               Win32FontManager.registerFontWithPlatform(file1.getAbsolutePath());
/*     */             } 
/* 279 */             return null;
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
/*     */   public HashMap<String, SunFontManager.FamilyDescription> populateHardcodedFileNameMap() {
/* 293 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 302 */     SunFontManager.FamilyDescription familyDescription = new SunFontManager.FamilyDescription();
/* 303 */     familyDescription.familyName = "Segoe UI";
/* 304 */     familyDescription.plainFullName = "Segoe UI";
/* 305 */     familyDescription.plainFileName = "segoeui.ttf";
/* 306 */     familyDescription.boldFullName = "Segoe UI Bold";
/* 307 */     familyDescription.boldFileName = "segoeuib.ttf";
/* 308 */     familyDescription.italicFullName = "Segoe UI Italic";
/* 309 */     familyDescription.italicFileName = "segoeuii.ttf";
/* 310 */     familyDescription.boldItalicFullName = "Segoe UI Bold Italic";
/* 311 */     familyDescription.boldItalicFileName = "segoeuiz.ttf";
/* 312 */     hashMap.put("segoe", familyDescription);
/*     */     
/* 314 */     familyDescription = new SunFontManager.FamilyDescription();
/* 315 */     familyDescription.familyName = "Tahoma";
/* 316 */     familyDescription.plainFullName = "Tahoma";
/* 317 */     familyDescription.plainFileName = "tahoma.ttf";
/* 318 */     familyDescription.boldFullName = "Tahoma Bold";
/* 319 */     familyDescription.boldFileName = "tahomabd.ttf";
/* 320 */     hashMap.put("tahoma", familyDescription);
/*     */     
/* 322 */     familyDescription = new SunFontManager.FamilyDescription();
/* 323 */     familyDescription.familyName = "Verdana";
/* 324 */     familyDescription.plainFullName = "Verdana";
/* 325 */     familyDescription.plainFileName = "verdana.TTF";
/* 326 */     familyDescription.boldFullName = "Verdana Bold";
/* 327 */     familyDescription.boldFileName = "verdanab.TTF";
/* 328 */     familyDescription.italicFullName = "Verdana Italic";
/* 329 */     familyDescription.italicFileName = "verdanai.TTF";
/* 330 */     familyDescription.boldItalicFullName = "Verdana Bold Italic";
/* 331 */     familyDescription.boldItalicFileName = "verdanaz.TTF";
/* 332 */     hashMap.put("verdana", familyDescription);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 337 */     familyDescription = new SunFontManager.FamilyDescription();
/* 338 */     familyDescription.familyName = "Arial";
/* 339 */     familyDescription.plainFullName = "Arial";
/* 340 */     familyDescription.plainFileName = "ARIAL.TTF";
/* 341 */     familyDescription.boldFullName = "Arial Bold";
/* 342 */     familyDescription.boldFileName = "ARIALBD.TTF";
/* 343 */     familyDescription.italicFullName = "Arial Italic";
/* 344 */     familyDescription.italicFileName = "ARIALI.TTF";
/* 345 */     familyDescription.boldItalicFullName = "Arial Bold Italic";
/* 346 */     familyDescription.boldItalicFileName = "ARIALBI.TTF";
/* 347 */     hashMap.put("arial", familyDescription);
/*     */     
/* 349 */     familyDescription = new SunFontManager.FamilyDescription();
/* 350 */     familyDescription.familyName = "Symbol";
/* 351 */     familyDescription.plainFullName = "Symbol";
/* 352 */     familyDescription.plainFileName = "Symbol.TTF";
/* 353 */     hashMap.put("symbol", familyDescription);
/*     */     
/* 355 */     familyDescription = new SunFontManager.FamilyDescription();
/* 356 */     familyDescription.familyName = "WingDings";
/* 357 */     familyDescription.plainFullName = "WingDings";
/* 358 */     familyDescription.plainFileName = "WINGDING.TTF";
/* 359 */     hashMap.put("wingdings", familyDescription);
/*     */     
/* 361 */     return (HashMap)hashMap;
/*     */   }
/*     */   
/*     */   private static native String getEUDCFontFile();
/*     */   
/*     */   private static native void populateFontFileNameMap0(HashMap<String, String> paramHashMap1, HashMap<String, String> paramHashMap2, HashMap<String, ArrayList<String>> paramHashMap, Locale paramLocale);
/*     */   
/*     */   protected synchronized native String getFontPath(boolean paramBoolean);
/*     */   
/*     */   protected static native void registerFontWithPlatform(String paramString);
/*     */   
/*     */   protected static native void deRegisterFontWithPlatform(String paramString);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\Win32FontManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */