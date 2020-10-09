/*     */ package sun.security.krb5.internal.tools;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.krb5.PrincipalName;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Ktab
/*     */ {
/*     */   KeyTab table;
/*     */   char action;
/*     */   String name;
/*     */   String principal;
/*     */   boolean showEType;
/*     */   boolean showTime;
/*  61 */   int etype = -1;
/*  62 */   char[] password = null;
/*     */   
/*     */   boolean forced = false;
/*     */   boolean append = false;
/*  66 */   int vDel = -1;
/*  67 */   int vAdd = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] paramArrayOfString) {
/*  74 */     Ktab ktab = new Ktab();
/*  75 */     if (paramArrayOfString.length == 1 && paramArrayOfString[0].equalsIgnoreCase("-help")) {
/*  76 */       ktab.printHelp(); return;
/*     */     } 
/*  78 */     if (paramArrayOfString == null || paramArrayOfString.length == 0) {
/*  79 */       ktab.action = 'l';
/*     */     } else {
/*  81 */       ktab.processArgs(paramArrayOfString);
/*     */     } 
/*  83 */     ktab.table = KeyTab.getInstance(ktab.name);
/*  84 */     if (ktab.table.isMissing() && ktab.action != 'a') {
/*  85 */       if (ktab.name == null) {
/*  86 */         System.out.println("No default key table exists.");
/*     */       } else {
/*  88 */         System.out.println("Key table " + ktab.name + " does not exist.");
/*     */       } 
/*     */       
/*  91 */       System.exit(-1);
/*     */     } 
/*  93 */     if (!ktab.table.isValid()) {
/*  94 */       if (ktab.name == null) {
/*  95 */         System.out.println("The format of the default key table  is incorrect.");
/*     */       } else {
/*     */         
/*  98 */         System.out.println("The format of key table " + ktab.name + " is incorrect.");
/*     */       } 
/*     */       
/* 101 */       System.exit(-1);
/*     */     } 
/* 103 */     switch (ktab.action) {
/*     */       case 'l':
/* 105 */         ktab.listKt();
/*     */         return;
/*     */       case 'a':
/* 108 */         ktab.addEntry();
/*     */         return;
/*     */       case 'd':
/* 111 */         ktab.deleteEntry();
/*     */         return;
/*     */     } 
/* 114 */     ktab.error(new String[] { "A command must be provided" });
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void processArgs(String[] paramArrayOfString) {
/* 139 */     boolean bool = false;
/* 140 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 141 */       if (paramArrayOfString[b].startsWith("-")) {
/* 142 */         switch (paramArrayOfString[b].toLowerCase(Locale.US)) {
/*     */ 
/*     */           
/*     */           case "-l":
/* 146 */             this.action = 'l';
/*     */             break;
/*     */           case "-a":
/* 149 */             this.action = 'a';
/* 150 */             if (++b >= paramArrayOfString.length || paramArrayOfString[b].startsWith("-")) {
/* 151 */               error(new String[] { "A principal name must be specified after -a" });
/*     */             }
/* 153 */             this.principal = paramArrayOfString[b];
/*     */             break;
/*     */           case "-d":
/* 156 */             this.action = 'd';
/* 157 */             if (++b >= paramArrayOfString.length || paramArrayOfString[b].startsWith("-")) {
/* 158 */               error(new String[] { "A principal name must be specified after -d" });
/*     */             }
/* 160 */             this.principal = paramArrayOfString[b];
/*     */             break;
/*     */ 
/*     */           
/*     */           case "-e":
/* 165 */             if (this.action == 'l') {
/* 166 */               this.showEType = true; break;
/* 167 */             }  if (this.action == 'd') {
/* 168 */               if (++b >= paramArrayOfString.length || paramArrayOfString[b].startsWith("-")) {
/* 169 */                 error(new String[] { "An etype must be specified after -e" });
/*     */               }
/*     */               try {
/* 172 */                 this.etype = Integer.parseInt(paramArrayOfString[b]);
/* 173 */                 if (this.etype <= 0) {
/* 174 */                   throw new NumberFormatException();
/*     */                 }
/* 176 */               } catch (NumberFormatException numberFormatException) {
/* 177 */                 error(new String[] { paramArrayOfString[b] + " is not a valid etype" });
/*     */               }  break;
/*     */             } 
/* 180 */             error(new String[] { paramArrayOfString[b] + " is not valid after -" + this.action });
/*     */             break;
/*     */           
/*     */           case "-n":
/* 184 */             if (++b >= paramArrayOfString.length || paramArrayOfString[b].startsWith("-")) {
/* 185 */               error(new String[] { "A KVNO must be specified after -n" });
/*     */             }
/*     */             try {
/* 188 */               this.vAdd = Integer.parseInt(paramArrayOfString[b]);
/* 189 */               if (this.vAdd < 0) {
/* 190 */                 throw new NumberFormatException();
/*     */               }
/* 192 */             } catch (NumberFormatException numberFormatException) {
/* 193 */               error(new String[] { paramArrayOfString[b] + " is not a valid KVNO" });
/*     */             } 
/*     */             break;
/*     */           case "-k":
/* 197 */             if (++b >= paramArrayOfString.length || paramArrayOfString[b].startsWith("-")) {
/* 198 */               error(new String[] { "A keytab name must be specified after -k" });
/*     */             }
/* 200 */             if (paramArrayOfString[b].length() >= 5 && paramArrayOfString[b]
/* 201 */               .substring(0, 5).equalsIgnoreCase("FILE:")) {
/* 202 */               this.name = paramArrayOfString[b].substring(5); break;
/*     */             } 
/* 204 */             this.name = paramArrayOfString[b];
/*     */             break;
/*     */           
/*     */           case "-t":
/* 208 */             this.showTime = true;
/*     */             break;
/*     */           case "-f":
/* 211 */             this.forced = true;
/*     */             break;
/*     */           case "-append":
/* 214 */             this.append = true;
/*     */             break;
/*     */           default:
/* 217 */             error(new String[] { "Unknown command: " + paramArrayOfString[b] });
/*     */             break;
/*     */         } 
/*     */       } else {
/* 221 */         if (bool) {
/* 222 */           error(new String[] { "Useless extra argument " + paramArrayOfString[b] });
/*     */         }
/* 224 */         if (this.action == 'a') {
/* 225 */           this.password = paramArrayOfString[b].toCharArray();
/* 226 */         } else if (this.action == 'd') {
/* 227 */           switch (paramArrayOfString[b]) { case "all":
/* 228 */               this.vDel = -1; break;
/* 229 */             case "old": this.vDel = -2; break;
/*     */             default:
/*     */               try {
/* 232 */                 this.vDel = Integer.parseInt(paramArrayOfString[b]);
/* 233 */                 if (this.vDel < 0) {
/* 234 */                   throw new NumberFormatException();
/*     */                 }
/* 236 */               } catch (NumberFormatException numberFormatException) {
/* 237 */                 error(new String[] { paramArrayOfString[b] + " is not a valid KVNO" });
/*     */               } 
/*     */               break; }
/*     */         
/*     */         } else {
/* 242 */           error(new String[] { "Useless extra argument " + paramArrayOfString[b] });
/*     */         } 
/* 244 */         bool = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addEntry() {
/* 255 */     PrincipalName principalName = null;
/*     */     try {
/* 257 */       principalName = new PrincipalName(this.principal);
/* 258 */     } catch (KrbException krbException) {
/* 259 */       System.err.println("Failed to add " + this.principal + " to keytab.");
/*     */       
/* 261 */       krbException.printStackTrace();
/* 262 */       System.exit(-1);
/*     */     } 
/* 264 */     if (this.password == null) {
/*     */       try {
/* 266 */         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
/*     */         
/* 268 */         System.out.print("Password for " + principalName.toString() + ":");
/* 269 */         System.out.flush();
/* 270 */         this.password = bufferedReader.readLine().toCharArray();
/* 271 */       } catch (IOException iOException) {
/* 272 */         System.err.println("Failed to read the password.");
/* 273 */         iOException.printStackTrace();
/* 274 */         System.exit(-1);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 280 */       this.table.addEntry(principalName, this.password, this.vAdd, this.append);
/* 281 */       Arrays.fill(this.password, '0');
/*     */       
/* 283 */       this.table.save();
/* 284 */       System.out.println("Done!");
/* 285 */       System.out.println("Service key for " + this.principal + " is saved in " + this.table
/* 286 */           .tabName());
/*     */     }
/* 288 */     catch (KrbException krbException) {
/* 289 */       System.err.println("Failed to add " + this.principal + " to keytab.");
/* 290 */       krbException.printStackTrace();
/* 291 */       System.exit(-1);
/* 292 */     } catch (IOException iOException) {
/* 293 */       System.err.println("Failed to save new entry.");
/* 294 */       iOException.printStackTrace();
/* 295 */       System.exit(-1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void listKt() {
/* 303 */     System.out.println("Keytab name: " + this.table.tabName());
/* 304 */     KeyTabEntry[] arrayOfKeyTabEntry = this.table.getEntries();
/* 305 */     if (arrayOfKeyTabEntry != null && arrayOfKeyTabEntry.length > 0) {
/* 306 */       String[][] arrayOfString = new String[arrayOfKeyTabEntry.length + 1][this.showTime ? 3 : 2];
/* 307 */       byte b1 = 0;
/* 308 */       arrayOfString[0][b1++] = "KVNO";
/* 309 */       if (this.showTime) arrayOfString[0][b1++] = "Timestamp"; 
/* 310 */       arrayOfString[0][b1++] = "Principal";
/* 311 */       for (byte b2 = 0; b2 < arrayOfKeyTabEntry.length; b2++) {
/* 312 */         b1 = 0;
/* 313 */         arrayOfString[b2 + 1][b1++] = arrayOfKeyTabEntry[b2].getKey()
/* 314 */           .getKeyVersionNumber().toString();
/* 315 */         if (this.showTime) arrayOfString[b2 + 1][b1++] = 
/* 316 */             DateFormat.getDateTimeInstance(3, 3)
/* 317 */             .format(new Date(arrayOfKeyTabEntry[b2]
/* 318 */                 .getTimeStamp().getTime())); 
/* 319 */         String str = arrayOfKeyTabEntry[b2].getService().toString();
/* 320 */         if (this.showEType) {
/* 321 */           int i = arrayOfKeyTabEntry[b2].getKey().getEType();
/* 322 */           arrayOfString[b2 + 1][b1++] = str + " (" + i + ":" + 
/* 323 */             EType.toString(i) + ")";
/*     */         } else {
/* 325 */           arrayOfString[b2 + 1][b1++] = str;
/*     */         } 
/*     */       } 
/* 328 */       int[] arrayOfInt = new int[b1]; byte b3;
/* 329 */       for (b3 = 0; b3 < b1; b3++) {
/* 330 */         for (byte b = 0; b <= arrayOfKeyTabEntry.length; b++) {
/* 331 */           if (arrayOfString[b][b3].length() > arrayOfInt[b3]) {
/* 332 */             arrayOfInt[b3] = arrayOfString[b][b3].length();
/*     */           }
/*     */         } 
/* 335 */         if (b3 != 0) arrayOfInt[b3] = -arrayOfInt[b3]; 
/*     */       } 
/* 337 */       for (b3 = 0; b3 < b1; b3++) {
/* 338 */         System.out.printf("%" + arrayOfInt[b3] + "s ", new Object[] { arrayOfString[0][b3] });
/*     */       } 
/* 340 */       System.out.println();
/* 341 */       for (b3 = 0; b3 < b1; b3++) {
/* 342 */         for (byte b = 0; b < Math.abs(arrayOfInt[b3]); ) { System.out.print("-"); b++; }
/* 343 */          System.out.print(" ");
/*     */       } 
/* 345 */       System.out.println();
/* 346 */       for (b3 = 0; b3 < arrayOfKeyTabEntry.length; b3++) {
/* 347 */         for (byte b = 0; b < b1; b++) {
/* 348 */           System.out.printf("%" + arrayOfInt[b] + "s ", new Object[] { arrayOfString[b3 + 1][b] });
/*     */         } 
/* 350 */         System.out.println();
/*     */       } 
/*     */     } else {
/* 353 */       System.out.println("0 entry.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void deleteEntry() {
/* 361 */     PrincipalName principalName = null;
/*     */     try {
/* 363 */       principalName = new PrincipalName(this.principal);
/* 364 */       if (!this.forced) {
/*     */         
/* 366 */         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
/*     */         
/* 368 */         System.out.print("Are you sure you want to delete service key(s) for " + principalName
/* 369 */             .toString() + " (" + ((this.etype == -1) ? "all etypes" : ("etype=" + this.etype)) + ", " + ((this.vDel == -1) ? "all kvno" : ((this.vDel == -2) ? "old kvno" : ("kvno=" + this.vDel))) + ") in " + this.table
/*     */ 
/*     */             
/* 372 */             .tabName() + "? (Y/[N]): ");
/*     */         
/* 374 */         System.out.flush();
/* 375 */         String str = bufferedReader.readLine();
/* 376 */         if (!str.equalsIgnoreCase("Y") && 
/* 377 */           !str.equalsIgnoreCase("Yes"))
/*     */         {
/*     */           
/* 380 */           System.exit(0);
/*     */         }
/*     */       } 
/* 383 */     } catch (KrbException krbException) {
/* 384 */       System.err.println("Error occurred while deleting the entry. Deletion failed.");
/*     */       
/* 386 */       krbException.printStackTrace();
/* 387 */       System.exit(-1);
/* 388 */     } catch (IOException iOException) {
/* 389 */       System.err.println("Error occurred while deleting the entry.  Deletion failed.");
/*     */       
/* 391 */       iOException.printStackTrace();
/* 392 */       System.exit(-1);
/*     */     } 
/*     */     
/* 395 */     int i = this.table.deleteEntries(principalName, this.etype, this.vDel);
/*     */     
/* 397 */     if (i == 0) {
/* 398 */       System.err.println("No matched entry in the keytab. Deletion fails.");
/*     */       
/* 400 */       System.exit(-1);
/*     */     } else {
/*     */       try {
/* 403 */         this.table.save();
/* 404 */       } catch (IOException iOException) {
/* 405 */         System.err.println("Error occurs while saving the keytab. Deletion fails.");
/*     */         
/* 407 */         iOException.printStackTrace();
/* 408 */         System.exit(-1);
/*     */       } 
/* 410 */       System.out.println("Done! " + i + " entries removed.");
/*     */     } 
/*     */   }
/*     */   
/*     */   void error(String... paramVarArgs) {
/* 415 */     for (String str : paramVarArgs) {
/* 416 */       System.out.println("Error: " + str + ".");
/*     */     }
/* 418 */     printHelp();
/* 419 */     System.exit(-1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void printHelp() {
/* 425 */     System.out.println("\nUsage: ktab <commands> <options>");
/* 426 */     System.out.println();
/* 427 */     System.out.println("Available commands:");
/* 428 */     System.out.println();
/* 429 */     System.out.println("-l [-e] [-t]\n    list the keytab name and entries. -e with etype, -t with timestamp.");
/*     */     
/* 431 */     System.out.println("-a <principal name> [<password>] [-n <kvno>] [-append]\n    add new key entries to the keytab for the given principal name with\n    optional <password>. If a <kvno> is specified, new keys' Key Version\n    Numbers equal to the value, otherwise, automatically incrementing\n    the Key Version Numbers. If -append is specified, new keys are\n    appended to the keytab, otherwise, old keys for the\n    same principal are removed.");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 438 */     System.out.println("-d <principal name> [-f] [-e <etype>] [<kvno> | all | old]\n    delete key entries from the keytab for the specified principal. If\n    <kvno> is specified, delete keys whose Key Version Numbers match\n    kvno. If \"all\" is specified, delete all keys. If \"old\" is specified,\n    delete all keys except those with the highest kvno. Default action\n    is \"all\". If <etype> is specified, only keys of this encryption type\n    are deleted. <etype> should be specified as the numberic value etype\n    defined in RFC 3961, section 8. A prompt to confirm the deletion is\n    displayed unless -f is specified.");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 447 */     System.out.println();
/* 448 */     System.out.println("Common option(s):");
/* 449 */     System.out.println();
/* 450 */     System.out.println("-k <keytab name>\n    specify keytab name and path with prefix FILE:");
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\tools\Ktab.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */