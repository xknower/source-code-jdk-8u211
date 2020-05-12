/*      */ package sun.security.krb5;
/*      */ 
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.net.InetAddress;
/*      */ import java.net.UnknownHostException;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import sun.net.dns.ResolverConfiguration;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ import sun.security.krb5.internal.Krb5;
/*      */ import sun.security.krb5.internal.crypto.EType;
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
/*      */ public class Config
/*      */ {
/*   63 */   private static Config singleton = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   68 */   private Hashtable<String, Object> stanzaTable = new Hashtable<>();
/*      */   
/*   70 */   private static boolean DEBUG = Krb5.DEBUG;
/*      */ 
/*      */   
/*      */   private static final int BASE16_0 = 1;
/*      */ 
/*      */   
/*      */   private static final int BASE16_1 = 16;
/*      */ 
/*      */   
/*      */   private static final int BASE16_2 = 256;
/*      */ 
/*      */   
/*      */   private static final int BASE16_3 = 4096;
/*      */ 
/*      */   
/*      */   private final String defaultRealm;
/*      */ 
/*      */   
/*      */   private final String defaultKDC;
/*      */ 
/*      */ 
/*      */   
/*      */   private static native String getWindowsDirectory(boolean paramBoolean);
/*      */ 
/*      */ 
/*      */   
/*      */   public static synchronized Config getInstance() throws KrbException {
/*   97 */     if (singleton == null) {
/*   98 */       singleton = new Config();
/*      */     }
/*  100 */     return singleton;
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
/*      */   public static synchronized void refresh() throws KrbException {
/*  116 */     singleton = new Config();
/*  117 */     KdcComm.initStatic();
/*  118 */     EType.initStatic();
/*  119 */     Checksum.initStatic();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isMacosLionOrBetter() {
/*  125 */     String str1 = getProperty("os.name");
/*  126 */     if (!str1.contains("OS X")) {
/*  127 */       return false;
/*      */     }
/*      */     
/*  130 */     String str2 = getProperty("os.version");
/*  131 */     String[] arrayOfString = str2.split("\\.");
/*      */ 
/*      */     
/*  134 */     if (!arrayOfString[0].equals("10")) return false; 
/*  135 */     if (arrayOfString.length < 2) return false;
/*      */ 
/*      */     
/*      */     try {
/*  139 */       int i = Integer.parseInt(arrayOfString[1]);
/*  140 */       if (i >= 7) return true; 
/*  141 */     } catch (NumberFormatException numberFormatException) {}
/*      */ 
/*      */ 
/*      */     
/*  145 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Config() throws KrbException {
/*  155 */     String str = getProperty("java.security.krb5.kdc");
/*  156 */     if (str != null) {
/*      */       
/*  158 */       this.defaultKDC = str.replace(':', ' ');
/*      */     } else {
/*  160 */       this.defaultKDC = null;
/*      */     } 
/*  162 */     this.defaultRealm = getProperty("java.security.krb5.realm");
/*  163 */     if ((this.defaultKDC == null && this.defaultRealm != null) || (this.defaultRealm == null && this.defaultKDC != null))
/*      */     {
/*  165 */       throw new KrbException("System property java.security.krb5.kdc and java.security.krb5.realm both must be set or neither must be set.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  174 */       String str1 = getJavaFileName();
/*  175 */       if (str1 != null) {
/*  176 */         List<String> list = loadConfigFile(str1);
/*  177 */         this.stanzaTable = parseStanzaTable(list);
/*  178 */         if (DEBUG) {
/*  179 */           System.out.println("Loaded from Java config");
/*      */         }
/*      */       } else {
/*  182 */         boolean bool = false;
/*  183 */         if (isMacosLionOrBetter()) {
/*      */           try {
/*  185 */             this.stanzaTable = SCDynamicStoreConfig.getConfig();
/*  186 */             if (DEBUG) {
/*  187 */               System.out.println("Loaded from SCDynamicStoreConfig");
/*      */             }
/*  189 */             bool = true;
/*  190 */           } catch (IOException iOException) {}
/*      */         }
/*      */ 
/*      */         
/*  194 */         if (!bool) {
/*  195 */           str1 = getNativeFileName();
/*  196 */           List<String> list = loadConfigFile(str1);
/*  197 */           this.stanzaTable = parseStanzaTable(list);
/*  198 */           if (DEBUG) {
/*  199 */             System.out.println("Loaded from native config");
/*      */           }
/*      */         } 
/*      */       } 
/*  203 */     } catch (IOException iOException) {}
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
/*      */   public String get(String... paramVarArgs) {
/*  229 */     Vector<String> vector = getString0(paramVarArgs);
/*  230 */     if (vector == null) return null; 
/*  231 */     return vector.lastElement();
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
/*      */   private Boolean getBooleanObject(String... paramVarArgs) {
/*  245 */     String str = get(paramVarArgs);
/*  246 */     if (str == null) {
/*  247 */       return null;
/*      */     }
/*  249 */     switch (str.toLowerCase(Locale.US)) { case "yes":
/*      */       case "true":
/*  251 */         return Boolean.TRUE;
/*      */       case "no": case "false":
/*  253 */         return Boolean.FALSE; }
/*      */     
/*  255 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAll(String... paramVarArgs) {
/*  265 */     Vector<String> vector = getString0(paramVarArgs);
/*  266 */     if (vector == null) return null; 
/*  267 */     StringBuilder stringBuilder = new StringBuilder();
/*  268 */     boolean bool = true;
/*  269 */     for (String str : vector) {
/*  270 */       if (bool) {
/*  271 */         stringBuilder.append(str);
/*  272 */         bool = false; continue;
/*      */       } 
/*  274 */       stringBuilder.append(' ').append(str);
/*      */     } 
/*      */     
/*  277 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean exists(String... paramVarArgs) {
/*  286 */     return (get0(paramVarArgs) != null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Vector<String> getString0(String... paramVarArgs) {
/*      */     try {
/*  293 */       return (Vector<String>)get0(paramVarArgs);
/*  294 */     } catch (ClassCastException classCastException) {
/*  295 */       throw new IllegalArgumentException(classCastException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object get0(String... paramVarArgs) {
/*  304 */     Hashtable<String, Object> hashtable = this.stanzaTable;
/*      */     try {
/*  306 */       for (String str : paramVarArgs) {
/*  307 */         hashtable = (Hashtable<String, Object>)hashtable.get(str);
/*  308 */         if (hashtable == null) return null; 
/*      */       } 
/*  310 */       return hashtable;
/*  311 */     } catch (ClassCastException classCastException) {
/*  312 */       throw new IllegalArgumentException(classCastException);
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
/*      */   public int getIntValue(String... paramVarArgs) {
/*  325 */     String str = get(paramVarArgs);
/*  326 */     int i = Integer.MIN_VALUE;
/*  327 */     if (str != null) {
/*      */       try {
/*  329 */         i = parseIntValue(str);
/*  330 */       } catch (NumberFormatException numberFormatException) {
/*  331 */         if (DEBUG) {
/*  332 */           System.out.println("Exception in getting value of " + 
/*  333 */               Arrays.toString((Object[])paramVarArgs) + " " + numberFormatException
/*  334 */               .getMessage());
/*  335 */           System.out.println("Setting " + Arrays.toString((Object[])paramVarArgs) + " to minimum value");
/*      */         } 
/*      */         
/*  338 */         i = Integer.MIN_VALUE;
/*      */       } 
/*      */     }
/*  341 */     return i;
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
/*      */   public boolean getBooleanValue(String... paramVarArgs) {
/*  353 */     String str = get(paramVarArgs);
/*  354 */     if (str != null && str.equalsIgnoreCase("true")) {
/*  355 */       return true;
/*      */     }
/*  357 */     return false;
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
/*      */   private int parseIntValue(String paramString) throws NumberFormatException {
/*  373 */     int i = 0;
/*  374 */     if (paramString.startsWith("+")) {
/*  375 */       String str = paramString.substring(1);
/*  376 */       return Integer.parseInt(str);
/*  377 */     }  if (paramString.startsWith("0x")) {
/*  378 */       String str = paramString.substring(2);
/*  379 */       char[] arrayOfChar = str.toCharArray();
/*  380 */       if (arrayOfChar.length > 8) {
/*  381 */         throw new NumberFormatException();
/*      */       }
/*  383 */       for (byte b = 0; b < arrayOfChar.length; b++) {
/*  384 */         int j = arrayOfChar.length - b - 1;
/*  385 */         switch (arrayOfChar[b]) {
/*      */           case '0':
/*  387 */             i += false;
/*      */             break;
/*      */           case '1':
/*  390 */             i += 1 * getBase(j);
/*      */             break;
/*      */           case '2':
/*  393 */             i += 2 * getBase(j);
/*      */             break;
/*      */           case '3':
/*  396 */             i += 3 * getBase(j);
/*      */             break;
/*      */           case '4':
/*  399 */             i += 4 * getBase(j);
/*      */             break;
/*      */           case '5':
/*  402 */             i += 5 * getBase(j);
/*      */             break;
/*      */           case '6':
/*  405 */             i += 6 * getBase(j);
/*      */             break;
/*      */           case '7':
/*  408 */             i += 7 * getBase(j);
/*      */             break;
/*      */           case '8':
/*  411 */             i += 8 * getBase(j);
/*      */             break;
/*      */           case '9':
/*  414 */             i += 9 * getBase(j);
/*      */             break;
/*      */           case 'A':
/*      */           case 'a':
/*  418 */             i += 10 * getBase(j);
/*      */             break;
/*      */           case 'B':
/*      */           case 'b':
/*  422 */             i += 11 * getBase(j);
/*      */             break;
/*      */           case 'C':
/*      */           case 'c':
/*  426 */             i += 12 * getBase(j);
/*      */             break;
/*      */           case 'D':
/*      */           case 'd':
/*  430 */             i += 13 * getBase(j);
/*      */             break;
/*      */           case 'E':
/*      */           case 'e':
/*  434 */             i += 14 * getBase(j);
/*      */             break;
/*      */           case 'F':
/*      */           case 'f':
/*  438 */             i += 15 * getBase(j);
/*      */             break;
/*      */           default:
/*  441 */             throw new NumberFormatException("Invalid numerical format");
/*      */         } 
/*      */       
/*      */       } 
/*  445 */       if (i < 0) {
/*  446 */         throw new NumberFormatException("Data overflow.");
/*      */       }
/*      */     } else {
/*  449 */       i = Integer.parseInt(paramString);
/*      */     } 
/*  451 */     return i;
/*      */   }
/*      */   
/*      */   private int getBase(int paramInt) {
/*  455 */     int i = 16;
/*  456 */     switch (paramInt)
/*      */     { case 0:
/*  458 */         i = 1;
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
/*  474 */         return i;case 1: i = 16; return i;case 2: i = 256; return i;case 3: i = 4096; return i; }  for (byte b = 1; b < paramInt; b++) i *= 16;  return i;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private List<String> loadConfigFile(final String fileName) throws IOException, KrbException {
/*      */     try {
/*  516 */       ArrayList<String> arrayList = new ArrayList();
/*  517 */       try (BufferedReader null = new BufferedReader(new InputStreamReader(
/*  518 */               AccessController.<InputStream>doPrivileged((PrivilegedExceptionAction)new PrivilegedExceptionAction<FileInputStream>()
/*      */                 {
/*      */                   public FileInputStream run() throws IOException {
/*  521 */                     return new FileInputStream(fileName);
/*      */                   }
/*      */                 })))) {
/*      */         
/*  525 */         String str2 = null; String str1;
/*  526 */         while ((str1 = bufferedReader.readLine()) != null) {
/*  527 */           str1 = str1.trim();
/*  528 */           if (str1.isEmpty() || str1.startsWith("#") || str1.startsWith(";")) {
/*      */             continue;
/*      */           }
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
/*  549 */           if (str1.startsWith("[")) {
/*  550 */             if (!str1.endsWith("]")) {
/*  551 */               throw new KrbException("Illegal config content:" + str1);
/*      */             }
/*      */             
/*  554 */             if (str2 != null) {
/*  555 */               arrayList.add(str2);
/*  556 */               arrayList.add("}");
/*      */             } 
/*      */             
/*  559 */             String str = str1.substring(1, str1.length() - 1).trim();
/*  560 */             if (str.isEmpty()) {
/*  561 */               throw new KrbException("Illegal config content:" + str1);
/*      */             }
/*      */             
/*  564 */             str2 = str + " = {"; continue;
/*  565 */           }  if (str1.startsWith("{")) {
/*  566 */             if (str2 == null) {
/*  567 */               throw new KrbException("Config file should not start with \"{\"");
/*      */             }
/*      */             
/*  570 */             str2 = str2 + " {";
/*  571 */             if (str1.length() > 1) {
/*      */               
/*  573 */               arrayList.add(str2);
/*  574 */               str2 = str1.substring(1).trim();
/*      */             } 
/*      */             continue;
/*      */           } 
/*  578 */           if (str2 != null) {
/*  579 */             arrayList.add(str2);
/*  580 */             str2 = str1;
/*      */           } 
/*      */         } 
/*      */         
/*  584 */         if (str2 != null) {
/*  585 */           arrayList.add(str2);
/*  586 */           arrayList.add("}");
/*      */         } 
/*      */       } 
/*  589 */       return arrayList;
/*  590 */     } catch (PrivilegedActionException privilegedActionException) {
/*  591 */       throw (IOException)privilegedActionException.getException();
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
/*      */   private Hashtable<String, Object> parseStanzaTable(List<String> paramList) throws KrbException {
/*      */     Hashtable<Object, Object> hashtable;
/*  619 */     Hashtable<String, Object> hashtable1 = this.stanzaTable;
/*  620 */     for (String str1 : paramList) {
/*      */       Vector<String> vector;
/*      */ 
/*      */ 
/*      */       
/*  625 */       if (str1.equals("}")) {
/*      */         
/*  627 */         hashtable1 = (Hashtable<String, Object>)hashtable1.remove(" PARENT ");
/*  628 */         if (hashtable1 == null)
/*  629 */           throw new KrbException("Unmatched close brace"); 
/*      */         continue;
/*      */       } 
/*  632 */       int i = str1.indexOf('=');
/*  633 */       if (i < 0) {
/*  634 */         throw new KrbException("Illegal config content:" + str1);
/*      */       }
/*  636 */       String str2 = str1.substring(0, i).trim();
/*  637 */       String str3 = trimmed(str1.substring(i + 1));
/*  638 */       if (str3.equals("{")) {
/*      */         
/*  640 */         if (hashtable1 == this.stanzaTable) {
/*  641 */           str2 = str2.toLowerCase(Locale.US);
/*      */         }
/*  643 */         Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/*  644 */         hashtable1.put(str2, hashtable2);
/*      */ 
/*      */         
/*  647 */         hashtable2.put(" PARENT ", hashtable1);
/*  648 */         hashtable = hashtable2;
/*      */         continue;
/*      */       } 
/*  651 */       if (hashtable.containsKey(str2)) {
/*  652 */         Object object = hashtable.get(str2);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  657 */         if (!(object instanceof Vector)) {
/*  658 */           throw new KrbException("Key " + str2 + "used for both value and section");
/*      */         }
/*      */         
/*  661 */         vector = (Vector)hashtable.get(str2);
/*      */       } else {
/*  663 */         vector = new Vector();
/*  664 */         hashtable.put(str2, vector);
/*      */       } 
/*  666 */       vector.add(str3);
/*      */     } 
/*      */ 
/*      */     
/*  670 */     if (hashtable != this.stanzaTable) {
/*  671 */       throw new KrbException("Not closed");
/*      */     }
/*  673 */     return (Hashtable)hashtable;
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
/*      */   private String getJavaFileName() {
/*  687 */     String str = getProperty("java.security.krb5.conf");
/*  688 */     if (str == null) {
/*  689 */       str = getProperty("java.home") + File.separator + "lib" + File.separator + "security" + File.separator + "krb5.conf";
/*      */ 
/*      */       
/*  692 */       if (!fileExists(str)) {
/*  693 */         str = null;
/*      */       }
/*      */     } 
/*  696 */     if (DEBUG) {
/*  697 */       System.out.println("Java config name: " + str);
/*      */     }
/*  699 */     return str;
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
/*      */   private String getNativeFileName() {
/*  720 */     String str1 = null;
/*  721 */     String str2 = getProperty("os.name");
/*  722 */     if (str2.startsWith("Windows")) {
/*      */       try {
/*  724 */         Credentials.ensureLoaded();
/*  725 */       } catch (Exception exception) {}
/*      */ 
/*      */       
/*  728 */       if (Credentials.alreadyLoaded) {
/*  729 */         String str = getWindowsDirectory(false);
/*  730 */         if (str != null) {
/*  731 */           if (str.endsWith("\\")) {
/*  732 */             str = str + "krb5.ini";
/*      */           } else {
/*  734 */             str = str + "\\krb5.ini";
/*      */           } 
/*  736 */           if (fileExists(str)) {
/*  737 */             str1 = str;
/*      */           }
/*      */         } 
/*  740 */         if (str1 == null) {
/*  741 */           str = getWindowsDirectory(true);
/*  742 */           if (str != null) {
/*  743 */             if (str.endsWith("\\")) {
/*  744 */               str = str + "krb5.ini";
/*      */             } else {
/*  746 */               str = str + "\\krb5.ini";
/*      */             } 
/*  748 */             str1 = str;
/*      */           } 
/*      */         } 
/*      */       } 
/*  752 */       if (str1 == null) {
/*  753 */         str1 = "c:\\winnt\\krb5.ini";
/*      */       }
/*  755 */     } else if (str2.startsWith("SunOS")) {
/*  756 */       str1 = "/etc/krb5/krb5.conf";
/*  757 */     } else if (str2.contains("OS X")) {
/*  758 */       str1 = findMacosConfigFile();
/*      */     } else {
/*  760 */       str1 = "/etc/krb5.conf";
/*      */     } 
/*  762 */     if (DEBUG) {
/*  763 */       System.out.println("Native config name: " + str1);
/*      */     }
/*  765 */     return str1;
/*      */   }
/*      */   
/*      */   private static String getProperty(String paramString) {
/*  769 */     return AccessController.<String>doPrivileged(new GetPropertyAction(paramString));
/*      */   }
/*      */ 
/*      */   
/*      */   private String findMacosConfigFile() {
/*  774 */     String str1 = getProperty("user.home");
/*      */     
/*  776 */     String str2 = str1 + "/Library/Preferences/edu.mit.Kerberos";
/*      */     
/*  778 */     if (fileExists(str2)) {
/*  779 */       return str2;
/*      */     }
/*      */     
/*  782 */     if (fileExists("/Library/Preferences/edu.mit.Kerberos")) {
/*  783 */       return "/Library/Preferences/edu.mit.Kerberos";
/*      */     }
/*      */     
/*  786 */     return "/etc/krb5.conf";
/*      */   }
/*      */   
/*      */   private static String trimmed(String paramString) {
/*  790 */     paramString = paramString.trim();
/*  791 */     if (paramString.length() >= 2 && ((paramString
/*  792 */       .charAt(0) == '"' && paramString.charAt(paramString.length() - 1) == '"') || (paramString
/*  793 */       .charAt(0) == '\'' && paramString.charAt(paramString.length() - 1) == '\''))) {
/*  794 */       paramString = paramString.substring(1, paramString.length() - 1).trim();
/*      */     }
/*  796 */     return paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void listTable() {
/*  804 */     System.out.println(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] defaultEtype(String paramString) throws KrbException {
/*      */     int[] arrayOfInt;
/*  814 */     String str = get(new String[] { "libdefaults", paramString });
/*      */     
/*  816 */     if (str == null) {
/*  817 */       if (DEBUG) {
/*  818 */         System.out.println("Using builtin default etypes for " + paramString);
/*      */       }
/*      */       
/*  821 */       arrayOfInt = EType.getBuiltInDefaults();
/*      */     } else {
/*  823 */       String str1 = " ";
/*      */       int i;
/*  825 */       for (i = 0; i < str.length(); i++) {
/*  826 */         if (str.substring(i, i + 1).equals(",")) {
/*      */ 
/*      */           
/*  829 */           str1 = ",";
/*      */           break;
/*      */         } 
/*      */       } 
/*  833 */       StringTokenizer stringTokenizer = new StringTokenizer(str, str1);
/*  834 */       i = stringTokenizer.countTokens();
/*  835 */       ArrayList<Integer> arrayList = new ArrayList(i);
/*      */       byte b;
/*  837 */       for (b = 0; b < i; b++) {
/*  838 */         int j = getType(stringTokenizer.nextToken());
/*  839 */         if (j != -1 && EType.isSupported(j)) {
/*  840 */           arrayList.add(Integer.valueOf(j));
/*      */         }
/*      */       } 
/*  843 */       if (arrayList.isEmpty()) {
/*  844 */         throw new KrbException("no supported default etypes for " + paramString);
/*      */       }
/*      */       
/*  847 */       arrayOfInt = new int[arrayList.size()];
/*  848 */       for (b = 0; b < arrayOfInt.length; b++) {
/*  849 */         arrayOfInt[b] = ((Integer)arrayList.get(b)).intValue();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  854 */     if (DEBUG) {
/*  855 */       System.out.print("default etypes for " + paramString + ":");
/*  856 */       for (byte b = 0; b < arrayOfInt.length; b++) {
/*  857 */         System.out.print(" " + arrayOfInt[b]);
/*      */       }
/*  859 */       System.out.println(".");
/*      */     } 
/*  861 */     return arrayOfInt;
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
/*      */   public static int getType(String paramString) {
/*  876 */     short s = -1;
/*  877 */     if (paramString == null) {
/*  878 */       return s;
/*      */     }
/*  880 */     if (paramString.startsWith("d") || paramString.startsWith("D")) {
/*  881 */       if (paramString.equalsIgnoreCase("des-cbc-crc")) {
/*  882 */         s = 1;
/*  883 */       } else if (paramString.equalsIgnoreCase("des-cbc-md5")) {
/*  884 */         s = 3;
/*  885 */       } else if (paramString.equalsIgnoreCase("des-mac")) {
/*  886 */         s = 4;
/*  887 */       } else if (paramString.equalsIgnoreCase("des-mac-k")) {
/*  888 */         s = 5;
/*  889 */       } else if (paramString.equalsIgnoreCase("des-cbc-md4")) {
/*  890 */         s = 2;
/*  891 */       } else if (paramString.equalsIgnoreCase("des3-cbc-sha1") || paramString
/*  892 */         .equalsIgnoreCase("des3-hmac-sha1") || paramString
/*  893 */         .equalsIgnoreCase("des3-cbc-sha1-kd") || paramString
/*  894 */         .equalsIgnoreCase("des3-cbc-hmac-sha1-kd")) {
/*  895 */         s = 16;
/*      */       } 
/*  897 */     } else if (paramString.startsWith("a") || paramString.startsWith("A")) {
/*      */       
/*  899 */       if (paramString.equalsIgnoreCase("aes128-cts") || paramString
/*  900 */         .equalsIgnoreCase("aes128-cts-hmac-sha1-96")) {
/*  901 */         s = 17;
/*  902 */       } else if (paramString.equalsIgnoreCase("aes256-cts") || paramString
/*  903 */         .equalsIgnoreCase("aes256-cts-hmac-sha1-96")) {
/*  904 */         s = 18;
/*      */       }
/*  906 */       else if (paramString.equalsIgnoreCase("arcfour-hmac") || paramString
/*  907 */         .equalsIgnoreCase("arcfour-hmac-md5")) {
/*  908 */         s = 23;
/*      */       }
/*      */     
/*  911 */     } else if (paramString.equalsIgnoreCase("rc4-hmac")) {
/*  912 */       s = 23;
/*  913 */     } else if (paramString.equalsIgnoreCase("CRC32")) {
/*  914 */       s = 1;
/*  915 */     } else if (paramString.startsWith("r") || paramString.startsWith("R")) {
/*  916 */       if (paramString.equalsIgnoreCase("rsa-md5")) {
/*  917 */         s = 7;
/*  918 */       } else if (paramString.equalsIgnoreCase("rsa-md5-des")) {
/*  919 */         s = 8;
/*      */       } 
/*  921 */     } else if (paramString.equalsIgnoreCase("hmac-sha1-des3-kd")) {
/*  922 */       s = 12;
/*  923 */     } else if (paramString.equalsIgnoreCase("hmac-sha1-96-aes128")) {
/*  924 */       s = 15;
/*  925 */     } else if (paramString.equalsIgnoreCase("hmac-sha1-96-aes256")) {
/*  926 */       s = 16;
/*  927 */     } else if (paramString.equalsIgnoreCase("hmac-md5-rc4") || paramString
/*  928 */       .equalsIgnoreCase("hmac-md5-arcfour") || paramString
/*  929 */       .equalsIgnoreCase("hmac-md5-enc")) {
/*  930 */       s = -138;
/*  931 */     } else if (paramString.equalsIgnoreCase("NULL")) {
/*  932 */       s = 0;
/*      */     } 
/*      */     
/*  935 */     return s;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetDefaultRealm(String paramString) {
/*  945 */     if (DEBUG) {
/*  946 */       System.out.println(">>> Config try resetting default kdc " + paramString);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean useAddresses() {
/*  955 */     boolean bool = false;
/*      */     
/*  957 */     String str = get(new String[] { "libdefaults", "no_addresses" });
/*  958 */     bool = (str != null && str.equalsIgnoreCase("false")) ? true : false;
/*  959 */     if (!bool) {
/*      */       
/*  961 */       str = get(new String[] { "libdefaults", "noaddresses" });
/*  962 */       bool = (str != null && str.equalsIgnoreCase("false")) ? true : false;
/*      */     } 
/*  964 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean useDNS(String paramString, boolean paramBoolean) {
/*  971 */     Boolean bool = getBooleanObject(new String[] { "libdefaults", paramString });
/*  972 */     if (bool != null) {
/*  973 */       return bool.booleanValue();
/*      */     }
/*  975 */     bool = getBooleanObject(new String[] { "libdefaults", "dns_fallback" });
/*  976 */     if (bool != null) {
/*  977 */       return bool.booleanValue();
/*      */     }
/*  979 */     return paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean useDNS_KDC() {
/*  986 */     return useDNS("dns_lookup_kdc", true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean useDNS_Realm() {
/*  993 */     return useDNS("dns_lookup_realm", false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDefaultRealm() throws KrbException {
/* 1002 */     if (this.defaultRealm != null) {
/* 1003 */       return this.defaultRealm;
/*      */     }
/* 1005 */     KrbException krbException = null;
/* 1006 */     String str = get(new String[] { "libdefaults", "default_realm" });
/* 1007 */     if (str == null && useDNS_Realm()) {
/*      */       
/*      */       try {
/* 1010 */         str = getRealmFromDNS();
/* 1011 */       } catch (KrbException krbException1) {
/* 1012 */         krbException = krbException1;
/*      */       } 
/*      */     }
/* 1015 */     if (str == null) {
/* 1016 */       str = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*      */           {
/*      */             public String run()
/*      */             {
/* 1020 */               String str = System.getProperty("os.name");
/* 1021 */               if (str.startsWith("Windows")) {
/* 1022 */                 return System.getenv("USERDNSDOMAIN");
/*      */               }
/* 1024 */               return null;
/*      */             }
/*      */           });
/*      */     }
/* 1028 */     if (str == null) {
/* 1029 */       KrbException krbException1 = new KrbException("Cannot locate default realm");
/* 1030 */       if (krbException != null) {
/* 1031 */         krbException1.initCause(krbException);
/*      */       }
/* 1033 */       throw krbException1;
/*      */     } 
/* 1035 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getKDCList(String paramString) throws KrbException {
/* 1046 */     if (paramString == null) {
/* 1047 */       paramString = getDefaultRealm();
/*      */     }
/* 1049 */     if (paramString.equalsIgnoreCase(this.defaultRealm)) {
/* 1050 */       return this.defaultKDC;
/*      */     }
/* 1052 */     KrbException krbException = null;
/* 1053 */     String str = getAll(new String[] { "realms", paramString, "kdc" });
/* 1054 */     if (str == null && useDNS_KDC()) {
/*      */       
/*      */       try {
/* 1057 */         str = getKDCFromDNS(paramString);
/* 1058 */       } catch (KrbException krbException1) {
/* 1059 */         krbException = krbException1;
/*      */       } 
/*      */     }
/* 1062 */     if (str == null) {
/* 1063 */       str = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*      */           {
/*      */             public String run()
/*      */             {
/* 1067 */               String str = System.getProperty("os.name");
/* 1068 */               if (str.startsWith("Windows")) {
/* 1069 */                 String str1 = System.getenv("LOGONSERVER");
/* 1070 */                 if (str1 != null && str1
/* 1071 */                   .startsWith("\\\\")) {
/* 1072 */                   str1 = str1.substring(2);
/*      */                 }
/* 1074 */                 return str1;
/*      */               } 
/* 1076 */               return null;
/*      */             }
/*      */           });
/*      */     }
/* 1080 */     if (str == null) {
/* 1081 */       if (this.defaultKDC != null) {
/* 1082 */         return this.defaultKDC;
/*      */       }
/* 1084 */       KrbException krbException1 = new KrbException("Cannot locate KDC");
/* 1085 */       if (krbException != null) {
/* 1086 */         krbException1.initCause(krbException);
/*      */       }
/* 1088 */       throw krbException1;
/*      */     } 
/* 1090 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getRealmFromDNS() throws KrbException {
/* 1100 */     String str1 = null;
/* 1101 */     String str2 = null;
/*      */     try {
/* 1103 */       str2 = InetAddress.getLocalHost().getCanonicalHostName();
/* 1104 */     } catch (UnknownHostException unknownHostException) {
/*      */       
/* 1106 */       KrbException krbException = new KrbException(60, "Unable to locate Kerberos realm: " + unknownHostException.getMessage());
/* 1107 */       krbException.initCause(unknownHostException);
/* 1108 */       throw krbException;
/*      */     } 
/*      */     
/* 1111 */     String str3 = PrincipalName.mapHostToRealm(str2);
/* 1112 */     if (str3 == null) {
/*      */       
/* 1114 */       List<String> list = ResolverConfiguration.open().searchlist();
/* 1115 */       for (String str : list) {
/* 1116 */         str1 = checkRealm(str);
/* 1117 */         if (str1 != null) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     } else {
/* 1122 */       str1 = checkRealm(str3);
/*      */     } 
/* 1124 */     if (str1 == null) {
/* 1125 */       throw new KrbException(60, "Unable to locate Kerberos realm");
/*      */     }
/*      */     
/* 1128 */     return str1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String checkRealm(String paramString) {
/* 1136 */     if (DEBUG) {
/* 1137 */       System.out.println("getRealmFromDNS: trying " + paramString);
/*      */     }
/* 1139 */     String[] arrayOfString = null;
/* 1140 */     String str = paramString;
/* 1141 */     while (arrayOfString == null && str != null) {
/*      */       
/* 1143 */       arrayOfString = KrbServiceLocator.getKerberosService(str);
/* 1144 */       str = Realm.parseRealmComponent(str);
/*      */     } 
/*      */     
/* 1147 */     if (arrayOfString != null) {
/* 1148 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 1149 */         if (arrayOfString[b].equalsIgnoreCase(paramString)) {
/* 1150 */           return arrayOfString[b];
/*      */         }
/*      */       } 
/*      */     }
/* 1154 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getKDCFromDNS(String paramString) throws KrbException {
/* 1165 */     String str = "";
/* 1166 */     String[] arrayOfString = null;
/*      */     
/* 1168 */     if (DEBUG) {
/* 1169 */       System.out.println("getKDCFromDNS using UDP");
/*      */     }
/* 1171 */     arrayOfString = KrbServiceLocator.getKerberosService(paramString, "_udp");
/* 1172 */     if (arrayOfString == null) {
/*      */       
/* 1174 */       if (DEBUG) {
/* 1175 */         System.out.println("getKDCFromDNS using TCP");
/*      */       }
/* 1177 */       arrayOfString = KrbServiceLocator.getKerberosService(paramString, "_tcp");
/*      */     } 
/* 1179 */     if (arrayOfString == null)
/*      */     {
/* 1181 */       throw new KrbException(60, "Unable to locate KDC for realm " + paramString);
/*      */     }
/*      */     
/* 1184 */     if (arrayOfString.length == 0) {
/* 1185 */       return null;
/*      */     }
/* 1187 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 1188 */       str = str + arrayOfString[b].trim() + " ";
/*      */     }
/* 1190 */     str = str.trim();
/* 1191 */     if (str.equals("")) {
/* 1192 */       return null;
/*      */     }
/* 1194 */     return str;
/*      */   }
/*      */   
/*      */   private boolean fileExists(String paramString) {
/* 1198 */     return ((Boolean)AccessController.<Boolean>doPrivileged(new FileExistsAction(paramString))).booleanValue();
/*      */   }
/*      */ 
/*      */   
/*      */   static class FileExistsAction
/*      */     implements PrivilegedAction<Boolean>
/*      */   {
/*      */     private String fileName;
/*      */     
/*      */     public FileExistsAction(String param1String) {
/* 1208 */       this.fileName = param1String;
/*      */     }
/*      */     
/*      */     public Boolean run() {
/* 1212 */       return Boolean.valueOf((new File(this.fileName)).exists());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1231 */     StringBuffer stringBuffer = new StringBuffer();
/* 1232 */     toStringInternal("", this.stanzaTable, stringBuffer);
/* 1233 */     return stringBuffer.toString();
/*      */   }
/*      */   
/*      */   private static void toStringInternal(String paramString, Object paramObject, StringBuffer paramStringBuffer) {
/* 1237 */     if (paramObject instanceof String) {
/*      */       
/* 1239 */       paramStringBuffer.append(paramObject).append('\n');
/* 1240 */     } else if (paramObject instanceof Hashtable) {
/*      */       
/* 1242 */       Hashtable hashtable = (Hashtable)paramObject;
/* 1243 */       paramStringBuffer.append("{\n");
/* 1244 */       for (Object object : hashtable.keySet()) {
/*      */         
/* 1246 */         paramStringBuffer.append(paramString).append("    ").append(object).append(" = ");
/*      */         
/* 1248 */         toStringInternal(paramString + "    ", hashtable.get(object), paramStringBuffer);
/*      */       } 
/* 1250 */       paramStringBuffer.append(paramString).append("}\n");
/* 1251 */     } else if (paramObject instanceof Vector) {
/*      */       
/* 1253 */       Vector vector = (Vector)paramObject;
/* 1254 */       paramStringBuffer.append("[");
/* 1255 */       boolean bool = true;
/* 1256 */       for (Object object : vector.toArray()) {
/* 1257 */         if (!bool) paramStringBuffer.append(","); 
/* 1258 */         paramStringBuffer.append(object);
/* 1259 */         bool = false;
/*      */       } 
/* 1261 */       paramStringBuffer.append("]\n");
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\Config.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */