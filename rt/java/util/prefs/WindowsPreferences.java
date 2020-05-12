/*      */ package java.util.prefs;
/*      */ 
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.util.StringTokenizer;
/*      */ import sun.util.logging.PlatformLogger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class WindowsPreferences
/*      */   extends AbstractPreferences
/*      */ {
/*      */   private static PlatformLogger logger;
/*   57 */   private static final byte[] WINDOWS_ROOT_PATH = stringToByteArray("Software\\JavaSoft\\Prefs");
/*      */   private static final int HKEY_CURRENT_USER = -2147483647;
/*      */   private static final int HKEY_LOCAL_MACHINE = -2147483646;
/*      */   private static final int USER_ROOT_NATIVE_HANDLE = -2147483647;
/*      */   private static final int SYSTEM_ROOT_NATIVE_HANDLE = -2147483646;
/*      */   private static final int MAX_WINDOWS_PATH_LENGTH = 256;
/*      */   private static volatile Preferences userRoot;
/*      */   private static volatile Preferences systemRoot;
/*      */   private static final int ERROR_SUCCESS = 0;
/*      */   private static final int ERROR_FILE_NOT_FOUND = 2;
/*      */   private static final int ERROR_ACCESS_DENIED = 5;
/*      */   private static final int NATIVE_HANDLE = 0;
/*      */   private static final int ERROR_CODE = 1;
/*      */   private static final int SUBKEYS_NUMBER = 0;
/*      */   private static final int VALUES_NUMBER = 2;
/*      */   private static final int MAX_KEY_LENGTH = 3;
/*      */   private static final int MAX_VALUE_NAME_LENGTH = 4;
/*      */   private static final int DISPOSITION = 2;
/*      */   private static final int REG_CREATED_NEW_KEY = 1;
/*      */   private static final int REG_OPENED_EXISTING_KEY = 2;
/*      */   private static final int NULL_NATIVE_HANDLE = 0;
/*      */   private static final int DELETE = 65536;
/*      */   private static final int KEY_QUERY_VALUE = 1;
/*      */   private static final int KEY_SET_VALUE = 2;
/*      */   private static final int KEY_CREATE_SUB_KEY = 4;
/*      */   private static final int KEY_ENUMERATE_SUB_KEYS = 8;
/*      */   private static final int KEY_READ = 131097;
/*      */   private static final int KEY_WRITE = 131078;
/*      */   private static final int KEY_ALL_ACCESS = 983103;
/*      */   
/*      */   static Preferences getUserRoot() {
/*   88 */     Preferences preferences = userRoot;
/*   89 */     if (preferences == null) {
/*   90 */       synchronized (WindowsPreferences.class) {
/*   91 */         preferences = userRoot;
/*   92 */         if (preferences == null) {
/*   93 */           preferences = new WindowsPreferences(-2147483647, WINDOWS_ROOT_PATH);
/*   94 */           userRoot = preferences;
/*      */         } 
/*      */       } 
/*      */     }
/*   98 */     return preferences;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Preferences getSystemRoot() {
/*  107 */     Preferences preferences = systemRoot;
/*  108 */     if (preferences == null) {
/*  109 */       synchronized (WindowsPreferences.class) {
/*  110 */         preferences = systemRoot;
/*  111 */         if (preferences == null) {
/*  112 */           preferences = new WindowsPreferences(-2147483646, WINDOWS_ROOT_PATH);
/*  113 */           systemRoot = preferences;
/*      */         } 
/*      */       } 
/*      */     }
/*  117 */     return preferences;
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
/*  151 */   private static int INIT_SLEEP_TIME = 50;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  156 */   private static int MAX_ATTEMPTS = 5;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isBackingStoreAvailable = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native int[] WindowsRegOpenKey(int paramInt1, byte[] paramArrayOfbyte, int paramInt2);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int[] WindowsRegOpenKey1(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) {
/*  173 */     int[] arrayOfInt = WindowsRegOpenKey(paramInt1, paramArrayOfbyte, paramInt2);
/*  174 */     if (arrayOfInt[1] == 0)
/*  175 */       return arrayOfInt; 
/*  176 */     if (arrayOfInt[1] == 2) {
/*  177 */       logger().warning("Trying to recreate Windows registry node " + 
/*  178 */           byteArrayToString(paramArrayOfbyte) + " at root 0x" + 
/*  179 */           Integer.toHexString(paramInt1) + ".");
/*      */       
/*  181 */       int i = WindowsRegCreateKeyEx(paramInt1, paramArrayOfbyte)[0];
/*  182 */       WindowsRegCloseKey(i);
/*  183 */       return WindowsRegOpenKey(paramInt1, paramArrayOfbyte, paramInt2);
/*  184 */     }  if (arrayOfInt[1] != 5) {
/*  185 */       long l = INIT_SLEEP_TIME;
/*  186 */       for (byte b = 0; b < MAX_ATTEMPTS; b++) {
/*      */         try {
/*  188 */           Thread.sleep(l);
/*  189 */         } catch (InterruptedException interruptedException) {
/*  190 */           return arrayOfInt;
/*      */         } 
/*  192 */         l *= 2L;
/*  193 */         arrayOfInt = WindowsRegOpenKey(paramInt1, paramArrayOfbyte, paramInt2);
/*  194 */         if (arrayOfInt[1] == 0) {
/*  195 */           return arrayOfInt;
/*      */         }
/*      */       } 
/*      */     } 
/*  199 */     return arrayOfInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native int WindowsRegCloseKey(int paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native int[] WindowsRegCreateKeyEx(int paramInt, byte[] paramArrayOfbyte);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int[] WindowsRegCreateKeyEx1(int paramInt, byte[] paramArrayOfbyte) {
/*  216 */     int[] arrayOfInt = WindowsRegCreateKeyEx(paramInt, paramArrayOfbyte);
/*  217 */     if (arrayOfInt[1] == 0) {
/*  218 */       return arrayOfInt;
/*      */     }
/*  220 */     long l = INIT_SLEEP_TIME;
/*  221 */     for (byte b = 0; b < MAX_ATTEMPTS; b++) {
/*      */       try {
/*  223 */         Thread.sleep(l);
/*  224 */       } catch (InterruptedException interruptedException) {
/*  225 */         return arrayOfInt;
/*      */       } 
/*  227 */       l *= 2L;
/*  228 */       arrayOfInt = WindowsRegCreateKeyEx(paramInt, paramArrayOfbyte);
/*  229 */       if (arrayOfInt[1] == 0) {
/*  230 */         return arrayOfInt;
/*      */       }
/*      */     } 
/*      */     
/*  234 */     return arrayOfInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native int WindowsRegDeleteKey(int paramInt, byte[] paramArrayOfbyte);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native int WindowsRegFlushKey(int paramInt);
/*      */ 
/*      */ 
/*      */   
/*      */   private static int WindowsRegFlushKey1(int paramInt) {
/*  250 */     int i = WindowsRegFlushKey(paramInt);
/*  251 */     if (i == 0) {
/*  252 */       return i;
/*      */     }
/*  254 */     long l = INIT_SLEEP_TIME;
/*  255 */     for (byte b = 0; b < MAX_ATTEMPTS; b++) {
/*      */       try {
/*  257 */         Thread.sleep(l);
/*  258 */       } catch (InterruptedException interruptedException) {
/*  259 */         return i;
/*      */       } 
/*  261 */       l *= 2L;
/*  262 */       i = WindowsRegFlushKey(paramInt);
/*  263 */       if (i == 0) {
/*  264 */         return i;
/*      */       }
/*      */     } 
/*      */     
/*  268 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native byte[] WindowsRegQueryValueEx(int paramInt, byte[] paramArrayOfbyte);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native int WindowsRegSetValueEx(int paramInt, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int WindowsRegSetValueEx1(int paramInt, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
/*  286 */     int i = WindowsRegSetValueEx(paramInt, paramArrayOfbyte1, paramArrayOfbyte2);
/*  287 */     if (i == 0) {
/*  288 */       return i;
/*      */     }
/*  290 */     long l = INIT_SLEEP_TIME;
/*  291 */     for (byte b = 0; b < MAX_ATTEMPTS; b++) {
/*      */       try {
/*  293 */         Thread.sleep(l);
/*  294 */       } catch (InterruptedException interruptedException) {
/*  295 */         return i;
/*      */       } 
/*  297 */       l *= 2L;
/*  298 */       i = WindowsRegSetValueEx(paramInt, paramArrayOfbyte1, paramArrayOfbyte2);
/*  299 */       if (i == 0) {
/*  300 */         return i;
/*      */       }
/*      */     } 
/*      */     
/*  304 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native int WindowsRegDeleteValue(int paramInt, byte[] paramArrayOfbyte);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native int[] WindowsRegQueryInfoKey(int paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int[] WindowsRegQueryInfoKey1(int paramInt) {
/*  321 */     int[] arrayOfInt = WindowsRegQueryInfoKey(paramInt);
/*  322 */     if (arrayOfInt[1] == 0) {
/*  323 */       return arrayOfInt;
/*      */     }
/*  325 */     long l = INIT_SLEEP_TIME;
/*  326 */     for (byte b = 0; b < MAX_ATTEMPTS; b++) {
/*      */       try {
/*  328 */         Thread.sleep(l);
/*  329 */       } catch (InterruptedException interruptedException) {
/*  330 */         return arrayOfInt;
/*      */       } 
/*  332 */       l *= 2L;
/*  333 */       arrayOfInt = WindowsRegQueryInfoKey(paramInt);
/*  334 */       if (arrayOfInt[1] == 0) {
/*  335 */         return arrayOfInt;
/*      */       }
/*      */     } 
/*      */     
/*  339 */     return arrayOfInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native byte[] WindowsRegEnumKeyEx(int paramInt1, int paramInt2, int paramInt3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static byte[] WindowsRegEnumKeyEx1(int paramInt1, int paramInt2, int paramInt3) {
/*  353 */     byte[] arrayOfByte = WindowsRegEnumKeyEx(paramInt1, paramInt2, paramInt3);
/*  354 */     if (arrayOfByte != null) {
/*  355 */       return arrayOfByte;
/*      */     }
/*  357 */     long l = INIT_SLEEP_TIME;
/*  358 */     for (byte b = 0; b < MAX_ATTEMPTS; b++) {
/*      */       try {
/*  360 */         Thread.sleep(l);
/*  361 */       } catch (InterruptedException interruptedException) {
/*  362 */         return arrayOfByte;
/*      */       } 
/*  364 */       l *= 2L;
/*  365 */       arrayOfByte = WindowsRegEnumKeyEx(paramInt1, paramInt2, paramInt3);
/*  366 */       if (arrayOfByte != null) {
/*  367 */         return arrayOfByte;
/*      */       }
/*      */     } 
/*      */     
/*  371 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native byte[] WindowsRegEnumValue(int paramInt1, int paramInt2, int paramInt3);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static byte[] WindowsRegEnumValue1(int paramInt1, int paramInt2, int paramInt3) {
/*  384 */     byte[] arrayOfByte = WindowsRegEnumValue(paramInt1, paramInt2, paramInt3);
/*      */     
/*  386 */     if (arrayOfByte != null) {
/*  387 */       return arrayOfByte;
/*      */     }
/*  389 */     long l = INIT_SLEEP_TIME;
/*  390 */     for (byte b = 0; b < MAX_ATTEMPTS; b++) {
/*      */       try {
/*  392 */         Thread.sleep(l);
/*  393 */       } catch (InterruptedException interruptedException) {
/*  394 */         return arrayOfByte;
/*      */       } 
/*  396 */       l *= 2L;
/*  397 */       arrayOfByte = WindowsRegEnumValue(paramInt1, paramInt2, paramInt3);
/*      */       
/*  399 */       if (arrayOfByte != null) {
/*  400 */         return arrayOfByte;
/*      */       }
/*      */     } 
/*      */     
/*  404 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private WindowsPreferences(WindowsPreferences paramWindowsPreferences, String paramString) {
/*  414 */     super(paramWindowsPreferences, paramString);
/*  415 */     int i = paramWindowsPreferences.openKey(4, 131097);
/*  416 */     if (i == 0) {
/*      */       
/*  418 */       this.isBackingStoreAvailable = false;
/*      */       
/*      */       return;
/*      */     } 
/*  422 */     int[] arrayOfInt = WindowsRegCreateKeyEx1(i, toWindowsName(paramString));
/*  423 */     if (arrayOfInt[1] != 0) {
/*  424 */       logger().warning("Could not create windows registry node " + 
/*  425 */           byteArrayToString(windowsAbsolutePath()) + " at root 0x" + 
/*  426 */           Integer.toHexString(rootNativeHandle()) + ". Windows RegCreateKeyEx(...) returned error code " + arrayOfInt[1] + ".");
/*      */ 
/*      */       
/*  429 */       this.isBackingStoreAvailable = false;
/*      */       return;
/*      */     } 
/*  432 */     this.newNode = (arrayOfInt[2] == 1);
/*  433 */     closeKey(i);
/*  434 */     closeKey(arrayOfInt[0]);
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
/*      */   private WindowsPreferences(int paramInt, byte[] paramArrayOfbyte) {
/*  446 */     super(null, "");
/*      */     
/*  448 */     int[] arrayOfInt = WindowsRegCreateKeyEx1(paramInt, paramArrayOfbyte);
/*  449 */     if (arrayOfInt[1] != 0) {
/*  450 */       logger().warning("Could not open/create prefs root node " + 
/*  451 */           byteArrayToString(windowsAbsolutePath()) + " at root 0x" + 
/*  452 */           Integer.toHexString(rootNativeHandle()) + ". Windows RegCreateKeyEx(...) returned error code " + arrayOfInt[1] + ".");
/*      */ 
/*      */       
/*  455 */       this.isBackingStoreAvailable = false;
/*      */       
/*      */       return;
/*      */     } 
/*  459 */     this.newNode = (arrayOfInt[2] == 1);
/*  460 */     closeKey(arrayOfInt[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] windowsAbsolutePath() {
/*  469 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/*  470 */     byteArrayOutputStream.write(WINDOWS_ROOT_PATH, 0, WINDOWS_ROOT_PATH.length - 1);
/*  471 */     StringTokenizer stringTokenizer = new StringTokenizer(absolutePath(), "/");
/*  472 */     while (stringTokenizer.hasMoreTokens()) {
/*  473 */       byteArrayOutputStream.write(92);
/*  474 */       String str = stringTokenizer.nextToken();
/*  475 */       byte[] arrayOfByte = toWindowsName(str);
/*  476 */       byteArrayOutputStream.write(arrayOfByte, 0, arrayOfByte.length - 1);
/*      */     } 
/*  478 */     byteArrayOutputStream.write(0);
/*  479 */     return byteArrayOutputStream.toByteArray();
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
/*      */   private int openKey(int paramInt) {
/*  492 */     return openKey(paramInt, paramInt);
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
/*      */   private int openKey(int paramInt1, int paramInt2) {
/*  506 */     return openKey(windowsAbsolutePath(), paramInt1, paramInt2);
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
/*      */   private int openKey(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*  524 */     if (paramArrayOfbyte.length <= 257) {
/*  525 */       int[] arrayOfInt = WindowsRegOpenKey1(rootNativeHandle(), paramArrayOfbyte, paramInt1);
/*      */       
/*  527 */       if (arrayOfInt[1] == 5 && paramInt2 != paramInt1) {
/*  528 */         arrayOfInt = WindowsRegOpenKey1(rootNativeHandle(), paramArrayOfbyte, paramInt2);
/*      */       }
/*      */       
/*  531 */       if (arrayOfInt[1] != 0) {
/*  532 */         logger().warning("Could not open windows registry node " + 
/*  533 */             byteArrayToString(windowsAbsolutePath()) + " at root 0x" + 
/*      */             
/*  535 */             Integer.toHexString(rootNativeHandle()) + ". Windows RegOpenKey(...) returned error code " + arrayOfInt[1] + ".");
/*      */ 
/*      */         
/*  538 */         arrayOfInt[0] = 0;
/*  539 */         if (arrayOfInt[1] == 5) {
/*  540 */           throw new SecurityException("Could not open windows registry node " + 
/*      */               
/*  542 */               byteArrayToString(windowsAbsolutePath()) + " at root 0x" + 
/*      */               
/*  544 */               Integer.toHexString(rootNativeHandle()) + ": Access denied");
/*      */         }
/*      */       } 
/*      */       
/*  548 */       return arrayOfInt[0];
/*      */     } 
/*  550 */     return openKey(rootNativeHandle(), paramArrayOfbyte, paramInt1, paramInt2);
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
/*      */   private int openKey(int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3) {
/*  570 */     if (paramArrayOfbyte.length <= 257) {
/*  571 */       int[] arrayOfInt = WindowsRegOpenKey1(paramInt1, paramArrayOfbyte, paramInt2);
/*      */       
/*  573 */       if (arrayOfInt[1] == 5 && paramInt3 != paramInt2) {
/*  574 */         arrayOfInt = WindowsRegOpenKey1(paramInt1, paramArrayOfbyte, paramInt3);
/*      */       }
/*      */       
/*  577 */       if (arrayOfInt[1] != 0) {
/*  578 */         logger().warning("Could not open windows registry node " + 
/*  579 */             byteArrayToString(windowsAbsolutePath()) + " at root 0x" + 
/*  580 */             Integer.toHexString(paramInt1) + ". Windows RegOpenKey(...) returned error code " + arrayOfInt[1] + ".");
/*      */ 
/*      */         
/*  583 */         arrayOfInt[0] = 0;
/*      */       } 
/*  585 */       return arrayOfInt[0];
/*      */     } 
/*  587 */     short s1 = -1;
/*      */     
/*  589 */     for (short s2 = 256; s2 > 0; s2--) {
/*  590 */       if (paramArrayOfbyte[s2] == 92) {
/*  591 */         s1 = s2;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*  596 */     byte[] arrayOfByte1 = new byte[s1 + 1];
/*  597 */     System.arraycopy(paramArrayOfbyte, 0, arrayOfByte1, 0, s1);
/*      */     
/*  599 */     arrayOfByte1[s1] = 0;
/*  600 */     byte[] arrayOfByte2 = new byte[paramArrayOfbyte.length - s1 - 1];
/*      */     
/*  602 */     System.arraycopy(paramArrayOfbyte, s1 + 1, arrayOfByte2, 0, arrayOfByte2.length);
/*      */     
/*  604 */     int i = openKey(paramInt1, arrayOfByte1, paramInt2, paramInt3);
/*      */     
/*  606 */     if (i == 0) {
/*  607 */       return 0;
/*      */     }
/*  609 */     int j = openKey(i, arrayOfByte2, paramInt2, paramInt3);
/*      */     
/*  611 */     closeKey(i);
/*  612 */     return j;
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
/*      */   private void closeKey(int paramInt) {
/*  625 */     int i = WindowsRegCloseKey(paramInt);
/*  626 */     if (i != 0) {
/*  627 */       logger().warning("Could not close windows registry node " + 
/*  628 */           byteArrayToString(windowsAbsolutePath()) + " at root 0x" + 
/*      */           
/*  630 */           Integer.toHexString(rootNativeHandle()) + ". Windows RegCloseKey(...) returned error code " + i + ".");
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
/*      */   protected void putSpi(String paramString1, String paramString2) {
/*  643 */     int i = openKey(2);
/*  644 */     if (i == 0) {
/*  645 */       this.isBackingStoreAvailable = false;
/*      */       return;
/*      */     } 
/*  648 */     int j = WindowsRegSetValueEx1(i, 
/*  649 */         toWindowsName(paramString1), toWindowsValueString(paramString2));
/*  650 */     if (j != 0) {
/*  651 */       logger().warning("Could not assign value to key " + 
/*  652 */           byteArrayToString(toWindowsName(paramString1)) + " at Windows registry node " + 
/*      */           
/*  654 */           byteArrayToString(windowsAbsolutePath()) + " at root 0x" + 
/*      */           
/*  656 */           Integer.toHexString(rootNativeHandle()) + ". Windows RegSetValueEx(...) returned error code " + j + ".");
/*      */ 
/*      */       
/*  659 */       this.isBackingStoreAvailable = false;
/*      */     } 
/*  661 */     closeKey(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getSpi(String paramString) {
/*  671 */     int i = openKey(1);
/*  672 */     if (i == 0) {
/*  673 */       return null;
/*      */     }
/*  675 */     byte[] arrayOfByte = WindowsRegQueryValueEx(i, 
/*  676 */         toWindowsName(paramString));
/*  677 */     if (arrayOfByte == null) {
/*  678 */       closeKey(i);
/*  679 */       return null;
/*      */     } 
/*  681 */     closeKey(i);
/*  682 */     return toJavaValueString(arrayOfByte);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void removeSpi(String paramString) {
/*  693 */     int i = openKey(2);
/*  694 */     if (i == 0) {
/*      */       return;
/*      */     }
/*      */     
/*  698 */     int j = WindowsRegDeleteValue(i, toWindowsName(paramString));
/*  699 */     if (j != 0 && j != 2) {
/*  700 */       logger().warning("Could not delete windows registry value " + 
/*  701 */           byteArrayToString(windowsAbsolutePath()) + "\\" + 
/*  702 */           toWindowsName(paramString) + " at root 0x" + 
/*  703 */           Integer.toHexString(rootNativeHandle()) + ". Windows RegDeleteValue(...) returned error code " + j + ".");
/*      */ 
/*      */       
/*  706 */       this.isBackingStoreAvailable = false;
/*      */     } 
/*  708 */     closeKey(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String[] keysSpi() throws BackingStoreException {
/*  719 */     int i = openKey(1);
/*  720 */     if (i == 0) {
/*  721 */       throw new BackingStoreException("Could not open windows registry node " + 
/*      */           
/*  723 */           byteArrayToString(windowsAbsolutePath()) + " at root 0x" + 
/*      */           
/*  725 */           Integer.toHexString(rootNativeHandle()) + ".");
/*      */     }
/*  727 */     int[] arrayOfInt = WindowsRegQueryInfoKey1(i);
/*  728 */     if (arrayOfInt[1] != 0) {
/*      */ 
/*      */ 
/*      */       
/*  732 */       String str = "Could not query windows registry node " + byteArrayToString(windowsAbsolutePath()) + " at root 0x" + Integer.toHexString(rootNativeHandle()) + ". Windows RegQueryInfoKeyEx(...) returned error code " + arrayOfInt[1] + ".";
/*      */ 
/*      */       
/*  735 */       logger().warning(str);
/*  736 */       throw new BackingStoreException(str);
/*      */     } 
/*  738 */     int j = arrayOfInt[4];
/*  739 */     int k = arrayOfInt[2];
/*  740 */     if (k == 0) {
/*  741 */       closeKey(i);
/*  742 */       return new String[0];
/*      */     } 
/*      */     
/*  745 */     String[] arrayOfString = new String[k];
/*  746 */     for (byte b = 0; b < k; b++) {
/*  747 */       byte[] arrayOfByte = WindowsRegEnumValue1(i, b, j + 1);
/*      */       
/*  749 */       if (arrayOfByte == null) {
/*      */ 
/*      */ 
/*      */         
/*  753 */         String str = "Could not enumerate value #" + b + "  of windows node " + byteArrayToString(windowsAbsolutePath()) + " at root 0x" + Integer.toHexString(rootNativeHandle()) + ".";
/*  754 */         logger().warning(str);
/*  755 */         throw new BackingStoreException(str);
/*      */       } 
/*  757 */       arrayOfString[b] = toJavaName(arrayOfByte);
/*      */     } 
/*  759 */     closeKey(i);
/*  760 */     return arrayOfString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String[] childrenNamesSpi() throws BackingStoreException {
/*  771 */     int i = openKey(9);
/*  772 */     if (i == 0) {
/*  773 */       throw new BackingStoreException("Could not open windows registry node " + 
/*      */           
/*  775 */           byteArrayToString(windowsAbsolutePath()) + " at root 0x" + 
/*      */           
/*  777 */           Integer.toHexString(rootNativeHandle()) + ".");
/*      */     }
/*      */     
/*  780 */     int[] arrayOfInt = WindowsRegQueryInfoKey1(i);
/*  781 */     if (arrayOfInt[1] != 0) {
/*      */ 
/*      */       
/*  784 */       String str = "Could not query windows registry node " + byteArrayToString(windowsAbsolutePath()) + " at root 0x" + Integer.toHexString(rootNativeHandle()) + ". Windows RegQueryInfoKeyEx(...) returned error code " + arrayOfInt[1] + ".";
/*      */ 
/*      */       
/*  787 */       logger().warning(str);
/*  788 */       throw new BackingStoreException(str);
/*      */     } 
/*  790 */     int j = arrayOfInt[3];
/*  791 */     int k = arrayOfInt[0];
/*  792 */     if (k == 0) {
/*  793 */       closeKey(i);
/*  794 */       return new String[0];
/*      */     } 
/*  796 */     String[] arrayOfString1 = new String[k];
/*  797 */     String[] arrayOfString2 = new String[k];
/*      */     
/*  799 */     for (byte b = 0; b < k; b++) {
/*  800 */       byte[] arrayOfByte = WindowsRegEnumKeyEx1(i, b, j + 1);
/*      */       
/*  802 */       if (arrayOfByte == null) {
/*      */ 
/*      */ 
/*      */         
/*  806 */         String str1 = "Could not enumerate key #" + b + "  of windows node " + byteArrayToString(windowsAbsolutePath()) + " at root 0x" + Integer.toHexString(rootNativeHandle()) + ". ";
/*  807 */         logger().warning(str1);
/*  808 */         throw new BackingStoreException(str1);
/*      */       } 
/*  810 */       String str = toJavaName(arrayOfByte);
/*  811 */       arrayOfString2[b] = str;
/*      */     } 
/*  813 */     closeKey(i);
/*  814 */     return arrayOfString2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void flush() throws BackingStoreException {
/*  825 */     if (isRemoved()) {
/*  826 */       this.parent.flush();
/*      */       return;
/*      */     } 
/*  829 */     if (!this.isBackingStoreAvailable) {
/*  830 */       throw new BackingStoreException("flush(): Backing store not available.");
/*      */     }
/*      */     
/*  833 */     int i = openKey(131097);
/*  834 */     if (i == 0) {
/*  835 */       throw new BackingStoreException("Could not open windows registry node " + 
/*      */           
/*  837 */           byteArrayToString(windowsAbsolutePath()) + " at root 0x" + 
/*      */           
/*  839 */           Integer.toHexString(rootNativeHandle()) + ".");
/*      */     }
/*  841 */     int j = WindowsRegFlushKey1(i);
/*  842 */     if (j != 0) {
/*      */ 
/*      */ 
/*      */       
/*  846 */       String str = "Could not flush windows registry node " + byteArrayToString(windowsAbsolutePath()) + " at root 0x" + Integer.toHexString(rootNativeHandle()) + ". Windows RegFlushKey(...) returned error code " + j + ".";
/*      */ 
/*      */       
/*  849 */       logger().warning(str);
/*  850 */       throw new BackingStoreException(str);
/*      */     } 
/*  852 */     closeKey(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sync() throws BackingStoreException {
/*  862 */     if (isRemoved())
/*  863 */       throw new IllegalStateException("Node has been removed"); 
/*  864 */     flush();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AbstractPreferences childSpi(String paramString) {
/*  875 */     return new WindowsPreferences(this, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeNodeSpi() throws BackingStoreException {
/*  886 */     int i = ((WindowsPreferences)parent()).openKey(65536);
/*  887 */     if (i == 0) {
/*  888 */       throw new BackingStoreException("Could not open parent windows registry node of " + 
/*      */           
/*  890 */           byteArrayToString(windowsAbsolutePath()) + " at root 0x" + 
/*      */           
/*  892 */           Integer.toHexString(rootNativeHandle()) + ".");
/*      */     }
/*      */     
/*  895 */     int j = WindowsRegDeleteKey(i, toWindowsName(name()));
/*  896 */     if (j != 0) {
/*      */ 
/*      */       
/*  899 */       String str = "Could not delete windows registry node " + byteArrayToString(windowsAbsolutePath()) + " at root 0x" + Integer.toHexString(rootNativeHandle()) + ". Windows RegDeleteKeyEx(...) returned error code " + j + ".";
/*      */ 
/*      */       
/*  902 */       logger().warning(str);
/*  903 */       throw new BackingStoreException(str);
/*      */     } 
/*  905 */     closeKey(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String toJavaName(byte[] paramArrayOfbyte) {
/*  916 */     String str = byteArrayToString(paramArrayOfbyte);
/*      */     
/*  918 */     if (str.length() > 1 && str
/*  919 */       .substring(0, 2).equals("/!")) {
/*  920 */       return toJavaAlt64Name(str);
/*      */     }
/*  922 */     StringBuilder stringBuilder = new StringBuilder();
/*      */ 
/*      */     
/*  925 */     for (byte b = 0; b < str.length(); b++) {
/*  926 */       char c; if ((c = str.charAt(b)) == '/') {
/*  927 */         char c1 = ' ';
/*  928 */         if (str.length() > b + 1 && (
/*  929 */           c1 = str.charAt(b + 1)) >= 'A' && c1 <= 'Z') {
/*      */           
/*  931 */           c = c1;
/*  932 */           b++;
/*  933 */         } else if (str.length() > b + 1 && c1 == '/') {
/*      */           
/*  935 */           c = '\\';
/*  936 */           b++;
/*      */         } 
/*  938 */       } else if (c == '\\') {
/*  939 */         c = '/';
/*      */       } 
/*  941 */       stringBuilder.append(c);
/*      */     } 
/*  943 */     return stringBuilder.toString();
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
/*      */   private static String toJavaAlt64Name(String paramString) {
/*  955 */     byte[] arrayOfByte = Base64.altBase64ToByteArray(paramString.substring(2));
/*  956 */     StringBuilder stringBuilder = new StringBuilder();
/*  957 */     for (byte b = 0; b < arrayOfByte.length; b++) {
/*  958 */       int i = arrayOfByte[b++] & 0xFF;
/*  959 */       int j = arrayOfByte[b] & 0xFF;
/*  960 */       stringBuilder.append((char)((i << 8) + j));
/*      */     } 
/*  962 */     return stringBuilder.toString();
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
/*      */   private static byte[] toWindowsName(String paramString) {
/*  986 */     StringBuilder stringBuilder = new StringBuilder();
/*  987 */     for (byte b = 0; b < paramString.length(); b++) {
/*  988 */       char c = paramString.charAt(b);
/*  989 */       if (c < ' ' || c > '')
/*      */       {
/*  991 */         return toWindowsAlt64Name(paramString);
/*      */       }
/*  993 */       if (c == '\\') {
/*  994 */         stringBuilder.append("//");
/*  995 */       } else if (c == '/') {
/*  996 */         stringBuilder.append('\\');
/*  997 */       } else if (c >= 'A' && c <= 'Z') {
/*  998 */         stringBuilder.append('/').append(c);
/*      */       } else {
/* 1000 */         stringBuilder.append(c);
/*      */       } 
/*      */     } 
/* 1003 */     return stringToByteArray(stringBuilder.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static byte[] toWindowsAlt64Name(String paramString) {
/* 1013 */     byte[] arrayOfByte = new byte[2 * paramString.length()];
/*      */     
/* 1015 */     byte b1 = 0;
/* 1016 */     for (byte b2 = 0; b2 < paramString.length(); b2++) {
/* 1017 */       char c = paramString.charAt(b2);
/* 1018 */       arrayOfByte[b1++] = (byte)(c >>> 8);
/* 1019 */       arrayOfByte[b1++] = (byte)c;
/*      */     } 
/*      */     
/* 1022 */     return stringToByteArray("/!" + 
/* 1023 */         Base64.byteArrayToAltBase64(arrayOfByte));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String toJavaValueString(byte[] paramArrayOfbyte) {
/* 1034 */     String str = byteArrayToString(paramArrayOfbyte);
/* 1035 */     StringBuilder stringBuilder = new StringBuilder();
/*      */     
/* 1037 */     for (byte b = 0; b < str.length(); b++) {
/* 1038 */       char c; if ((c = str.charAt(b)) == '/') {
/* 1039 */         char c1 = ' ';
/*      */         
/* 1041 */         if (str.length() > b + 1 && (
/* 1042 */           c1 = str.charAt(b + 1)) == 'u') {
/* 1043 */           if (str.length() < b + 6) {
/*      */             break;
/*      */           }
/* 1046 */           c = (char)Integer.parseInt(str
/* 1047 */               .substring(b + 2, b + 6), 16);
/* 1048 */           b += 5;
/*      */         
/*      */         }
/* 1051 */         else if (str.length() > b + 1 && str
/* 1052 */           .charAt(b + 1) >= 'A' && c1 <= 'Z') {
/*      */           
/* 1054 */           c = c1;
/* 1055 */           b++;
/* 1056 */         } else if (str.length() > b + 1 && c1 == '/') {
/*      */           
/* 1058 */           c = '\\';
/* 1059 */           b++;
/*      */         } 
/* 1061 */       } else if (c == '\\') {
/* 1062 */         c = '/';
/*      */       } 
/* 1064 */       stringBuilder.append(c);
/*      */     } 
/* 1066 */     return stringBuilder.toString();
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
/*      */   private static byte[] toWindowsValueString(String paramString) {
/* 1079 */     StringBuilder stringBuilder = new StringBuilder();
/* 1080 */     for (byte b = 0; b < paramString.length(); b++) {
/* 1081 */       char c = paramString.charAt(b);
/* 1082 */       if (c < ' ' || c > '') {
/*      */         
/* 1084 */         stringBuilder.append("/u");
/* 1085 */         String str = Integer.toHexString(paramString.charAt(b));
/* 1086 */         StringBuilder stringBuilder1 = new StringBuilder(str);
/* 1087 */         stringBuilder1.reverse();
/* 1088 */         int i = 4 - stringBuilder1.length(); byte b1;
/* 1089 */         for (b1 = 0; b1 < i; b1++) {
/* 1090 */           stringBuilder1.append('0');
/*      */         }
/* 1092 */         for (b1 = 0; b1 < 4; b1++) {
/* 1093 */           stringBuilder.append(stringBuilder1.charAt(3 - b1));
/*      */         }
/* 1095 */       } else if (c == '\\') {
/* 1096 */         stringBuilder.append("//");
/* 1097 */       } else if (c == '/') {
/* 1098 */         stringBuilder.append('\\');
/* 1099 */       } else if (c >= 'A' && c <= 'Z') {
/* 1100 */         stringBuilder.append('/').append(c);
/*      */       } else {
/* 1102 */         stringBuilder.append(c);
/*      */       } 
/*      */     } 
/* 1105 */     return stringToByteArray(stringBuilder.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int rootNativeHandle() {
/* 1112 */     return isUserNode() ? -2147483647 : -2147483646;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static byte[] stringToByteArray(String paramString) {
/* 1121 */     byte[] arrayOfByte = new byte[paramString.length() + 1];
/* 1122 */     for (byte b = 0; b < paramString.length(); b++) {
/* 1123 */       arrayOfByte[b] = (byte)paramString.charAt(b);
/*      */     }
/* 1125 */     arrayOfByte[paramString.length()] = 0;
/* 1126 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String byteArrayToString(byte[] paramArrayOfbyte) {
/* 1133 */     StringBuilder stringBuilder = new StringBuilder();
/* 1134 */     for (byte b = 0; b < paramArrayOfbyte.length - 1; b++) {
/* 1135 */       stringBuilder.append((char)paramArrayOfbyte[b]);
/*      */     }
/* 1137 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void flushSpi() throws BackingStoreException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void syncSpi() throws BackingStoreException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static synchronized PlatformLogger logger() {
/* 1155 */     if (logger == null) {
/* 1156 */       logger = PlatformLogger.getLogger("java.util.prefs");
/*      */     }
/* 1158 */     return logger;
/*      */   }
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\prefs\WindowsPreferences.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */