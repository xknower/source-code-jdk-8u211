/*     */ package sun.awt.windows;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.RenderingHints;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.java2d.windows.WindowsFlags;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class WDesktopProperties
/*     */ {
/*  57 */   private static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.windows.WDesktopProperties");
/*     */   private static final String PREFIX = "win.";
/*     */   private static final String FILE_PREFIX = "awt.file.";
/*     */   private static final String PROP_NAMES = "win.propNames";
/*     */   private long pData;
/*     */   private WToolkit wToolkit;
/*     */   
/*     */   static {
/*  65 */     initIDs();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  70 */   private HashMap<String, Object> map = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isWindowsProperty(String paramString) {
/*  78 */     return (paramString.startsWith("win.") || paramString.startsWith("awt.file.") || paramString
/*  79 */       .equals("awt.font.desktophints"));
/*     */   }
/*     */   
/*     */   WDesktopProperties(WToolkit paramWToolkit) {
/*  83 */     this.wToolkit = paramWToolkit;
/*  84 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] getKeyNames() {
/*  93 */     Object[] arrayOfObject = this.map.keySet().toArray();
/*  94 */     String[] arrayOfString = new String[arrayOfObject.length];
/*     */     
/*  96 */     for (byte b = 0; b < arrayOfObject.length; b++) {
/*  97 */       arrayOfString[b] = arrayOfObject[b].toString();
/*     */     }
/*  99 */     Arrays.sort((Object[])arrayOfString);
/* 100 */     return arrayOfString;
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
/*     */   private synchronized void setBooleanProperty(String paramString, boolean paramBoolean) {
/* 113 */     assert paramString != null;
/* 114 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 115 */       log.fine(paramString + "=" + String.valueOf(paramBoolean));
/*     */     }
/* 117 */     this.map.put(paramString, Boolean.valueOf(paramBoolean));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void setIntegerProperty(String paramString, int paramInt) {
/* 124 */     assert paramString != null;
/* 125 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 126 */       log.fine(paramString + "=" + String.valueOf(paramInt));
/*     */     }
/* 128 */     this.map.put(paramString, Integer.valueOf(paramInt));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void setStringProperty(String paramString1, String paramString2) {
/* 135 */     assert paramString1 != null;
/* 136 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 137 */       log.fine(paramString1 + "=" + paramString2);
/*     */     }
/* 139 */     this.map.put(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void setColorProperty(String paramString, int paramInt1, int paramInt2, int paramInt3) {
/* 146 */     assert paramString != null && paramInt1 <= 255 && paramInt2 <= 255 && paramInt3 <= 255;
/* 147 */     Color color = new Color(paramInt1, paramInt2, paramInt3);
/* 148 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 149 */       log.fine(paramString + "=" + color);
/*     */     }
/* 151 */     this.map.put(paramString, color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 157 */   static HashMap<String, String> fontNameMap = new HashMap<>(); static {
/* 158 */     fontNameMap.put("Courier", "Monospaced");
/* 159 */     fontNameMap.put("MS Serif", "Microsoft Serif");
/* 160 */     fontNameMap.put("MS Sans Serif", "Microsoft Sans Serif");
/* 161 */     fontNameMap.put("Terminal", "Dialog");
/* 162 */     fontNameMap.put("FixedSys", "Monospaced");
/* 163 */     fontNameMap.put("System", "Dialog");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void setFontProperty(String paramString1, String paramString2, int paramInt1, int paramInt2) {
/* 169 */     assert paramString1 != null && paramInt1 <= 3 && paramInt2 >= 0;
/*     */     
/* 171 */     String str1 = fontNameMap.get(paramString2);
/* 172 */     if (str1 != null) {
/* 173 */       paramString2 = str1;
/*     */     }
/* 175 */     Font font = new Font(paramString2, paramInt1, paramInt2);
/* 176 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 177 */       log.fine(paramString1 + "=" + font);
/*     */     }
/* 179 */     this.map.put(paramString1, font);
/*     */     
/* 181 */     String str2 = paramString1 + ".height";
/* 182 */     Integer integer = Integer.valueOf(paramInt2);
/* 183 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 184 */       log.fine(str2 + "=" + integer);
/*     */     }
/* 186 */     this.map.put(str2, integer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void setSoundProperty(String paramString1, String paramString2) {
/* 193 */     assert paramString1 != null && paramString2 != null;
/*     */     
/* 195 */     WinPlaySound winPlaySound = new WinPlaySound(paramString2);
/* 196 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 197 */       log.fine(paramString1 + "=" + winPlaySound);
/*     */     }
/* 199 */     this.map.put(paramString1, winPlaySound);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   class WinPlaySound
/*     */     implements Runnable
/*     */   {
/*     */     String winEventName;
/*     */ 
/*     */     
/*     */     WinPlaySound(String param1String) {
/* 211 */       this.winEventName = param1String;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 216 */       WDesktopProperties.this.playWindowsSound(this.winEventName);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 220 */       return "WinPlaySound(" + this.winEventName + ")";
/*     */     }
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 224 */       if (param1Object == this) {
/* 225 */         return true;
/*     */       }
/*     */       try {
/* 228 */         return this.winEventName.equals(((WinPlaySound)param1Object).winEventName);
/* 229 */       } catch (Exception exception) {
/* 230 */         return false;
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 235 */       return this.winEventName.hashCode();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized Map<String, Object> getProperties() {
/* 245 */     ThemeReader.flush();
/*     */ 
/*     */     
/* 248 */     this.map = new HashMap<>();
/* 249 */     getWindowsParameters();
/* 250 */     this.map.put("awt.font.desktophints", SunToolkit.getDesktopFontHints());
/* 251 */     this.map.put("win.propNames", getKeyNames());
/*     */ 
/*     */     
/* 254 */     this.map.put("DnD.Autoscroll.cursorHysteresis", this.map.get("win.drag.x"));
/*     */     
/* 256 */     return (Map<String, Object>)this.map.clone();
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
/*     */   synchronized RenderingHints getDesktopAAHints() {
/* 272 */     Object object = RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT;
/* 273 */     Integer integer = null;
/*     */     
/* 275 */     Boolean bool = (Boolean)this.map.get("win.text.fontSmoothingOn");
/*     */     
/* 277 */     if (bool != null && bool.equals(Boolean.TRUE)) {
/* 278 */       Integer integer1 = (Integer)this.map.get("win.text.fontSmoothingType");
/*     */ 
/*     */ 
/*     */       
/* 282 */       if (integer1 == null || integer1.intValue() <= 1 || integer1
/* 283 */         .intValue() > 2) {
/* 284 */         object = RenderingHints.VALUE_TEXT_ANTIALIAS_GASP;
/*     */       }
/* 286 */       else if (WindowsFlags.isAutoScaling()) {
/*     */         
/* 288 */         object = RenderingHints.VALUE_TEXT_ANTIALIAS_ON;
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 294 */         Integer integer2 = (Integer)this.map.get("win.text.fontSmoothingOrientation");
/*     */         
/* 296 */         if (integer2 == null || integer2.intValue() != 0) {
/* 297 */           object = RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB;
/*     */         } else {
/* 299 */           object = RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HBGR;
/*     */         } 
/*     */ 
/*     */         
/* 303 */         integer = (Integer)this.map.get("win.text.fontSmoothingContrast");
/* 304 */         if (integer == null) {
/* 305 */           integer = Integer.valueOf(140);
/*     */         }
/*     */         else {
/*     */           
/* 309 */           integer = Integer.valueOf(integer.intValue() / 10);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 315 */     RenderingHints renderingHints = new RenderingHints(null);
/* 316 */     renderingHints.put(RenderingHints.KEY_TEXT_ANTIALIASING, object);
/* 317 */     if (integer != null) {
/* 318 */       renderingHints.put(RenderingHints.KEY_TEXT_LCD_CONTRAST, integer);
/*     */     }
/* 320 */     return renderingHints;
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   private native void init();
/*     */   
/*     */   private native void getWindowsParameters();
/*     */   
/*     */   private native void playWindowsSound(String paramString);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WDesktopProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */