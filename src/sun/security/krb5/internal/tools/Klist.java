/*     */ package sun.security.krb5.internal.tools;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import sun.security.krb5.EncryptionKey;
/*     */ import sun.security.krb5.RealmException;
/*     */ import sun.security.krb5.internal.KerberosTime;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ import sun.security.krb5.internal.ccache.Credentials;
/*     */ import sun.security.krb5.internal.ccache.CredentialsCache;
/*     */ import sun.security.krb5.internal.crypto.EType;
/*     */ import sun.security.krb5.internal.ktab.KeyTab;
/*     */ import sun.security.krb5.internal.ktab.KeyTabEntry;
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
/*     */ public class Klist
/*     */ {
/*     */   Object target;
/*  51 */   char[] options = new char[4];
/*     */   
/*     */   String name;
/*     */   char action;
/*  55 */   private static boolean DEBUG = Krb5.DEBUG;
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
/*     */   public static void main(String[] paramArrayOfString) {
/*     */     KeyTab keyTab;
/*  76 */     Klist klist = new Klist();
/*  77 */     if (paramArrayOfString == null || paramArrayOfString.length == 0) {
/*  78 */       klist.action = 'c';
/*     */     } else {
/*  80 */       klist.processArgs(paramArrayOfString);
/*     */     } 
/*  82 */     switch (klist.action) {
/*     */       case 'c':
/*  84 */         if (klist.name == null) {
/*  85 */           klist.target = CredentialsCache.getInstance();
/*  86 */           klist.name = CredentialsCache.cacheName();
/*     */         } else {
/*  88 */           klist.target = CredentialsCache.getInstance(klist.name);
/*     */         } 
/*  90 */         if (klist.target != null) {
/*  91 */           klist.displayCache();
/*     */         } else {
/*  93 */           klist.displayMessage("Credentials cache");
/*  94 */           System.exit(-1);
/*     */         } 
/*     */         return;
/*     */       case 'k':
/*  98 */         keyTab = KeyTab.getInstance(klist.name);
/*  99 */         if (keyTab.isMissing()) {
/* 100 */           System.out.println("KeyTab " + klist.name + " not found.");
/* 101 */           System.exit(-1);
/* 102 */         } else if (!keyTab.isValid()) {
/* 103 */           System.out.println("KeyTab " + klist.name + " format not supported.");
/*     */           
/* 105 */           System.exit(-1);
/*     */         } 
/* 107 */         klist.target = keyTab;
/* 108 */         klist.name = keyTab.tabName();
/* 109 */         klist.displayTab();
/*     */         return;
/*     */     } 
/* 112 */     if (klist.name != null) {
/* 113 */       klist.printHelp();
/* 114 */       System.exit(-1);
/*     */     } else {
/* 116 */       klist.target = CredentialsCache.getInstance();
/* 117 */       klist.name = CredentialsCache.cacheName();
/* 118 */       if (klist.target != null) {
/* 119 */         klist.displayCache();
/*     */       } else {
/* 121 */         klist.displayMessage("Credentials cache");
/* 122 */         System.exit(-1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void processArgs(String[] paramArrayOfString) {
/* 133 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 134 */       if (paramArrayOfString[b].length() >= 2 && paramArrayOfString[b].startsWith("-")) {
/* 135 */         Character character = new Character(paramArrayOfString[b].charAt(1));
/* 136 */         switch (character.charValue()) {
/*     */           case 'c':
/* 138 */             this.action = 'c';
/*     */             break;
/*     */           case 'k':
/* 141 */             this.action = 'k';
/*     */             break;
/*     */           case 'a':
/* 144 */             this.options[2] = 'a';
/*     */             break;
/*     */           case 'n':
/* 147 */             this.options[3] = 'n';
/*     */             break;
/*     */           case 'f':
/* 150 */             this.options[1] = 'f';
/*     */             break;
/*     */           case 'e':
/* 153 */             this.options[0] = 'e';
/*     */             break;
/*     */           case 'K':
/* 156 */             this.options[1] = 'K';
/*     */             break;
/*     */           case 't':
/* 159 */             this.options[2] = 't';
/*     */             break;
/*     */           default:
/* 162 */             printHelp();
/* 163 */             System.exit(-1);
/*     */             break;
/*     */         } 
/*     */       
/* 167 */       } else if (!paramArrayOfString[b].startsWith("-") && b == paramArrayOfString.length - 1) {
/*     */         
/* 169 */         this.name = paramArrayOfString[b];
/* 170 */         Object object = null;
/*     */       } else {
/* 172 */         printHelp();
/* 173 */         System.exit(-1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void displayTab() {
/* 180 */     KeyTab keyTab = (KeyTab)this.target;
/* 181 */     KeyTabEntry[] arrayOfKeyTabEntry = keyTab.getEntries();
/* 182 */     if (arrayOfKeyTabEntry.length == 0) {
/* 183 */       System.out.println("\nKey tab: " + this.name + ",  0 entries found.\n");
/*     */     } else {
/*     */       
/* 186 */       if (arrayOfKeyTabEntry.length == 1) {
/* 187 */         System.out.println("\nKey tab: " + this.name + ", " + arrayOfKeyTabEntry.length + " entry found.\n");
/*     */       } else {
/*     */         
/* 190 */         System.out.println("\nKey tab: " + this.name + ", " + arrayOfKeyTabEntry.length + " entries found.\n");
/*     */       } 
/* 192 */       for (byte b = 0; b < arrayOfKeyTabEntry.length; b++) {
/* 193 */         System.out.println("[" + (b + 1) + "] Service principal: " + arrayOfKeyTabEntry[b]
/*     */             
/* 195 */             .getService().toString());
/* 196 */         System.out.println("\t KVNO: " + arrayOfKeyTabEntry[b]
/* 197 */             .getKey().getKeyVersionNumber());
/* 198 */         if (this.options[0] == 'e') {
/* 199 */           EncryptionKey encryptionKey = arrayOfKeyTabEntry[b].getKey();
/* 200 */           System.out.println("\t Key type: " + encryptionKey
/* 201 */               .getEType());
/*     */         } 
/* 203 */         if (this.options[1] == 'K') {
/* 204 */           EncryptionKey encryptionKey = arrayOfKeyTabEntry[b].getKey();
/* 205 */           System.out.println("\t Key: " + arrayOfKeyTabEntry[b]
/* 206 */               .getKeyString());
/*     */         } 
/* 208 */         if (this.options[2] == 't') {
/* 209 */           System.out.println("\t Time stamp: " + 
/* 210 */               format(arrayOfKeyTabEntry[b].getTimeStamp()));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   void displayCache() {
/* 217 */     CredentialsCache credentialsCache = (CredentialsCache)this.target;
/*     */     
/* 219 */     Credentials[] arrayOfCredentials = credentialsCache.getCredsList();
/* 220 */     if (arrayOfCredentials == null) {
/* 221 */       System.out.println("No credentials available in the cache " + this.name);
/*     */       
/* 223 */       System.exit(-1);
/*     */     } 
/* 225 */     System.out.println("\nCredentials cache: " + this.name);
/* 226 */     String str = credentialsCache.getPrimaryPrincipal().toString();
/* 227 */     int i = arrayOfCredentials.length;
/*     */     
/* 229 */     if (i == 1) {
/* 230 */       System.out.println("\nDefault principal: " + str + ", " + arrayOfCredentials.length + " entry found.\n");
/*     */     }
/*     */     else {
/*     */       
/* 234 */       System.out.println("\nDefault principal: " + str + ", " + arrayOfCredentials.length + " entries found.\n");
/*     */     } 
/*     */     
/* 237 */     if (arrayOfCredentials != null) {
/* 238 */       for (byte b = 0; b < arrayOfCredentials.length; b++) {
/*     */         try {
/*     */           String str1;
/*     */ 
/*     */ 
/*     */           
/* 244 */           if (arrayOfCredentials[b].getStartTime() != null) {
/* 245 */             str1 = format(arrayOfCredentials[b].getStartTime());
/*     */           } else {
/* 247 */             str1 = format(arrayOfCredentials[b].getAuthTime());
/*     */           } 
/* 249 */           String str2 = format(arrayOfCredentials[b].getEndTime());
/*     */           
/* 251 */           String str3 = arrayOfCredentials[b].getServicePrincipal().toString();
/* 252 */           System.out.println("[" + (b + 1) + "]  Service Principal:  " + str3);
/*     */ 
/*     */           
/* 255 */           System.out.println("     Valid starting:     " + str1);
/* 256 */           System.out.println("     Expires:            " + str2);
/* 257 */           if (arrayOfCredentials[b].getRenewTill() != null) {
/* 258 */             String str4 = format(arrayOfCredentials[b].getRenewTill());
/* 259 */             System.out.println("     Renew until:        " + str4);
/*     */           } 
/*     */           
/* 262 */           if (this.options[0] == 'e') {
/* 263 */             String str4 = EType.toString(arrayOfCredentials[b].getEType());
/* 264 */             String str5 = EType.toString(arrayOfCredentials[b].getTktEType());
/* 265 */             System.out.println("     EType (skey, tkt):  " + str4 + ", " + str5);
/*     */           } 
/*     */           
/* 268 */           if (this.options[1] == 'f') {
/* 269 */             System.out.println("     Flags:              " + arrayOfCredentials[b]
/* 270 */                 .getTicketFlags().toString());
/*     */           }
/* 272 */           if (this.options[2] == 'a') {
/* 273 */             boolean bool = true;
/*     */             
/* 275 */             InetAddress[] arrayOfInetAddress = arrayOfCredentials[b].setKrbCreds().getClientAddresses();
/* 276 */             if (arrayOfInetAddress != null) {
/* 277 */               for (InetAddress inetAddress : arrayOfInetAddress) {
/*     */                 String str4;
/* 279 */                 if (this.options[3] == 'n') {
/* 280 */                   str4 = inetAddress.getHostAddress();
/*     */                 } else {
/* 282 */                   str4 = inetAddress.getCanonicalHostName();
/*     */                 } 
/* 284 */                 System.out.println("     " + (bool ? "Addresses:" : "          ") + "       " + str4);
/*     */ 
/*     */                 
/* 287 */                 bool = false;
/*     */               } 
/*     */             } else {
/* 290 */               System.out.println("     [No host addresses info]");
/*     */             } 
/*     */           } 
/* 293 */         } catch (RealmException realmException) {
/* 294 */           System.out.println("Error reading principal from the entry.");
/*     */           
/* 296 */           if (DEBUG) {
/* 297 */             realmException.printStackTrace();
/*     */           }
/* 299 */           System.exit(-1);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 303 */       System.out.println("\nNo entries found.");
/*     */     } 
/*     */   }
/*     */   
/*     */   void displayMessage(String paramString) {
/* 308 */     if (this.name == null) {
/* 309 */       System.out.println("Default " + paramString + " not found.");
/*     */     } else {
/* 311 */       System.out.println(paramString + " " + this.name + " not found.");
/*     */     } 
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
/*     */   private String format(KerberosTime paramKerberosTime) {
/* 325 */     String str = paramKerberosTime.toDate().toString();
/* 326 */     return str.substring(4, 7) + " " + str.substring(8, 10) + ", " + str
/* 327 */       .substring(24) + " " + str
/* 328 */       .substring(11, 19);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void printHelp() {
/* 334 */     System.out.println("\nUsage: klist [[-c] [-f] [-e] [-a [-n]]] [-k [-t] [-K]] [name]");
/*     */     
/* 336 */     System.out.println("   name\t name of credentials cache or  keytab with the prefix. File-based cache or keytab's prefix is FILE:.");
/*     */ 
/*     */     
/* 339 */     System.out.println("   -c specifies that credential cache is to be listed");
/*     */     
/* 341 */     System.out.println("   -k specifies that key tab is to be listed");
/* 342 */     System.out.println("   options for credentials caches:");
/* 343 */     System.out.println("\t-f \t shows credentials flags");
/* 344 */     System.out.println("\t-e \t shows the encryption type");
/* 345 */     System.out.println("\t-a \t shows addresses");
/* 346 */     System.out.println("\t  -n \t   do not reverse-resolve addresses");
/* 347 */     System.out.println("   options for keytabs:");
/* 348 */     System.out.println("\t-t \t shows keytab entry timestamps");
/* 349 */     System.out.println("\t-K \t shows keytab entry key value");
/* 350 */     System.out.println("\t-e \t shows keytab entry key type");
/* 351 */     System.out.println("\nUsage: java sun.security.krb5.tools.Klist -help for help.");
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\tools\Klist.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */