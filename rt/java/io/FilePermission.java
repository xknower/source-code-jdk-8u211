/*     */ package java.io;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.Permission;
/*     */ import java.security.PermissionCollection;
/*     */ import java.security.PrivilegedAction;
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
/*     */ public final class FilePermission
/*     */   extends Permission
/*     */   implements Serializable
/*     */ {
/*     */   private static final int EXECUTE = 1;
/*     */   private static final int WRITE = 2;
/*     */   private static final int READ = 4;
/*     */   private static final int DELETE = 8;
/*     */   private static final int READLINK = 16;
/*     */   private static final int ALL = 31;
/*     */   private static final int NONE = 0;
/*     */   private transient int mask;
/*     */   private transient boolean directory;
/*     */   private transient boolean recursive;
/*     */   private String actions;
/*     */   private transient String cpath;
/*     */   private static final char RECURSIVE_CHAR = '-';
/*     */   private static final char WILD_CHAR = '*';
/*     */   private static final long serialVersionUID = 7930732926638008763L;
/*     */   
/*     */   private void init(int paramInt) {
/* 184 */     if ((paramInt & 0x1F) != paramInt) {
/* 185 */       throw new IllegalArgumentException("invalid actions mask");
/*     */     }
/* 187 */     if (paramInt == 0) {
/* 188 */       throw new IllegalArgumentException("invalid actions mask");
/*     */     }
/* 190 */     if ((this.cpath = getName()) == null) {
/* 191 */       throw new NullPointerException("name can't be null");
/*     */     }
/* 193 */     this.mask = paramInt;
/*     */     
/* 195 */     if (this.cpath.equals("<<ALL FILES>>")) {
/* 196 */       this.directory = true;
/* 197 */       this.recursive = true;
/* 198 */       this.cpath = "";
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 203 */     this.cpath = AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */           public String run() {
/*     */             try {
/* 206 */               String str = FilePermission.this.cpath;
/* 207 */               if (FilePermission.this.cpath.endsWith("*")) {
/*     */ 
/*     */ 
/*     */                 
/* 211 */                 str = str.substring(0, str.length() - 1) + "-";
/* 212 */                 str = (new File(str)).getCanonicalPath();
/* 213 */                 return str.substring(0, str.length() - 1) + "*";
/*     */               } 
/* 215 */               return (new File(str)).getCanonicalPath();
/*     */             }
/* 217 */             catch (IOException iOException) {
/* 218 */               return FilePermission.this.cpath;
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 223 */     int i = this.cpath.length();
/* 224 */     boolean bool = (i > 0) ? this.cpath.charAt(i - 1) : false;
/*     */     
/* 226 */     if (bool == 45 && this.cpath
/* 227 */       .charAt(i - 2) == File.separatorChar) {
/* 228 */       this.directory = true;
/* 229 */       this.recursive = true;
/* 230 */       this.cpath = this.cpath.substring(0, --i);
/* 231 */     } else if (bool == 42 && this.cpath
/* 232 */       .charAt(i - 2) == File.separatorChar) {
/* 233 */       this.directory = true;
/*     */       
/* 235 */       this.cpath = this.cpath.substring(0, --i);
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
/*     */   public FilePermission(String paramString1, String paramString2) {
/* 276 */     super(paramString1);
/* 277 */     init(getMask(paramString2));
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
/*     */   FilePermission(String paramString, int paramInt) {
/* 293 */     super(paramString);
/* 294 */     init(paramInt);
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
/*     */   public boolean implies(Permission paramPermission) {
/* 318 */     if (!(paramPermission instanceof FilePermission)) {
/* 319 */       return false;
/*     */     }
/* 321 */     FilePermission filePermission = (FilePermission)paramPermission;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 326 */     return ((this.mask & filePermission.mask) == filePermission.mask && impliesIgnoreMask(filePermission));
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
/*     */   boolean impliesIgnoreMask(FilePermission paramFilePermission) {
/* 338 */     if (this.directory) {
/* 339 */       if (this.recursive) {
/*     */ 
/*     */         
/* 342 */         if (paramFilePermission.directory) {
/* 343 */           return (paramFilePermission.cpath.length() >= this.cpath.length() && paramFilePermission.cpath
/* 344 */             .startsWith(this.cpath));
/*     */         }
/* 346 */         return (paramFilePermission.cpath.length() > this.cpath.length() && paramFilePermission.cpath
/* 347 */           .startsWith(this.cpath));
/*     */       } 
/*     */       
/* 350 */       if (paramFilePermission.directory) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 355 */         if (paramFilePermission.recursive) {
/* 356 */           return false;
/*     */         }
/* 358 */         return this.cpath.equals(paramFilePermission.cpath);
/*     */       } 
/* 360 */       int i = paramFilePermission.cpath.lastIndexOf(File.separatorChar);
/* 361 */       if (i == -1) {
/* 362 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 366 */       return (this.cpath.length() == i + 1 && this.cpath
/* 367 */         .regionMatches(0, paramFilePermission.cpath, 0, i + 1));
/*     */     } 
/*     */ 
/*     */     
/* 371 */     if (paramFilePermission.directory)
/*     */     {
/*     */       
/* 374 */       return false;
/*     */     }
/* 376 */     return this.cpath.equals(paramFilePermission.cpath);
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
/*     */   public boolean equals(Object paramObject) {
/* 390 */     if (paramObject == this) {
/* 391 */       return true;
/*     */     }
/* 393 */     if (!(paramObject instanceof FilePermission)) {
/* 394 */       return false;
/*     */     }
/* 396 */     FilePermission filePermission = (FilePermission)paramObject;
/*     */     
/* 398 */     return (this.mask == filePermission.mask && this.cpath
/* 399 */       .equals(filePermission.cpath) && this.directory == filePermission.directory && this.recursive == filePermission.recursive);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 410 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getMask(String paramString) {
/* 420 */     int i = 0;
/*     */ 
/*     */     
/* 423 */     if (paramString == null) {
/* 424 */       return i;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 429 */     if (paramString == "read")
/* 430 */       return 4; 
/* 431 */     if (paramString == "write")
/* 432 */       return 2; 
/* 433 */     if (paramString == "execute")
/* 434 */       return 1; 
/* 435 */     if (paramString == "delete")
/* 436 */       return 8; 
/* 437 */     if (paramString == "readlink") {
/* 438 */       return 16;
/*     */     }
/*     */     
/* 441 */     char[] arrayOfChar = paramString.toCharArray();
/*     */     
/* 443 */     int j = arrayOfChar.length - 1;
/* 444 */     if (j < 0) {
/* 445 */       return i;
/*     */     }
/* 447 */     while (j != -1) {
/*     */       byte b;
/*     */       
/*     */       char c;
/* 451 */       while (j != -1 && ((c = arrayOfChar[j]) == ' ' || c == '\r' || c == '\n' || c == '\f' || c == '\t'))
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 456 */         j--;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 461 */       if (j >= 3 && (arrayOfChar[j - 3] == 'r' || arrayOfChar[j - 3] == 'R') && (arrayOfChar[j - 2] == 'e' || arrayOfChar[j - 2] == 'E') && (arrayOfChar[j - 1] == 'a' || arrayOfChar[j - 1] == 'A') && (arrayOfChar[j] == 'd' || arrayOfChar[j] == 'D')) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 466 */         b = 4;
/* 467 */         i |= 0x4;
/*     */       }
/* 469 */       else if (j >= 4 && (arrayOfChar[j - 4] == 'w' || arrayOfChar[j - 4] == 'W') && (arrayOfChar[j - 3] == 'r' || arrayOfChar[j - 3] == 'R') && (arrayOfChar[j - 2] == 'i' || arrayOfChar[j - 2] == 'I') && (arrayOfChar[j - 1] == 't' || arrayOfChar[j - 1] == 'T') && (arrayOfChar[j] == 'e' || arrayOfChar[j] == 'E')) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 475 */         b = 5;
/* 476 */         i |= 0x2;
/*     */       }
/* 478 */       else if (j >= 6 && (arrayOfChar[j - 6] == 'e' || arrayOfChar[j - 6] == 'E') && (arrayOfChar[j - 5] == 'x' || arrayOfChar[j - 5] == 'X') && (arrayOfChar[j - 4] == 'e' || arrayOfChar[j - 4] == 'E') && (arrayOfChar[j - 3] == 'c' || arrayOfChar[j - 3] == 'C') && (arrayOfChar[j - 2] == 'u' || arrayOfChar[j - 2] == 'U') && (arrayOfChar[j - 1] == 't' || arrayOfChar[j - 1] == 'T') && (arrayOfChar[j] == 'e' || arrayOfChar[j] == 'E')) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 486 */         b = 7;
/* 487 */         i |= 0x1;
/*     */       }
/* 489 */       else if (j >= 5 && (arrayOfChar[j - 5] == 'd' || arrayOfChar[j - 5] == 'D') && (arrayOfChar[j - 4] == 'e' || arrayOfChar[j - 4] == 'E') && (arrayOfChar[j - 3] == 'l' || arrayOfChar[j - 3] == 'L') && (arrayOfChar[j - 2] == 'e' || arrayOfChar[j - 2] == 'E') && (arrayOfChar[j - 1] == 't' || arrayOfChar[j - 1] == 'T') && (arrayOfChar[j] == 'e' || arrayOfChar[j] == 'E')) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 496 */         b = 6;
/* 497 */         i |= 0x8;
/*     */       }
/* 499 */       else if (j >= 7 && (arrayOfChar[j - 7] == 'r' || arrayOfChar[j - 7] == 'R') && (arrayOfChar[j - 6] == 'e' || arrayOfChar[j - 6] == 'E') && (arrayOfChar[j - 5] == 'a' || arrayOfChar[j - 5] == 'A') && (arrayOfChar[j - 4] == 'd' || arrayOfChar[j - 4] == 'D') && (arrayOfChar[j - 3] == 'l' || arrayOfChar[j - 3] == 'L') && (arrayOfChar[j - 2] == 'i' || arrayOfChar[j - 2] == 'I') && (arrayOfChar[j - 1] == 'n' || arrayOfChar[j - 1] == 'N') && (arrayOfChar[j] == 'k' || arrayOfChar[j] == 'K')) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 508 */         b = 8;
/* 509 */         i |= 0x10;
/*     */       }
/*     */       else {
/*     */         
/* 513 */         throw new IllegalArgumentException("invalid permission: " + paramString);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 519 */       boolean bool = false;
/* 520 */       while (j >= b && !bool) {
/* 521 */         switch (arrayOfChar[j - b]) {
/*     */           case ',':
/* 523 */             bool = true; break;
/*     */           case '\t': case '\n': case '\f':
/*     */           case '\r':
/*     */           case ' ':
/*     */             break;
/*     */           default:
/* 529 */             throw new IllegalArgumentException("invalid permission: " + paramString);
/*     */         } 
/*     */         
/* 532 */         j--;
/*     */       } 
/*     */ 
/*     */       
/* 536 */       j -= b;
/*     */     } 
/*     */     
/* 539 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getMask() {
/* 548 */     return this.mask;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getActions(int paramInt) {
/* 559 */     StringBuilder stringBuilder = new StringBuilder();
/* 560 */     boolean bool = false;
/*     */     
/* 562 */     if ((paramInt & 0x4) == 4) {
/* 563 */       bool = true;
/* 564 */       stringBuilder.append("read");
/*     */     } 
/*     */     
/* 567 */     if ((paramInt & 0x2) == 2) {
/* 568 */       if (bool) { stringBuilder.append(','); }
/* 569 */       else { bool = true; }
/* 570 */        stringBuilder.append("write");
/*     */     } 
/*     */     
/* 573 */     if ((paramInt & 0x1) == 1) {
/* 574 */       if (bool) { stringBuilder.append(','); }
/* 575 */       else { bool = true; }
/* 576 */        stringBuilder.append("execute");
/*     */     } 
/*     */     
/* 579 */     if ((paramInt & 0x8) == 8) {
/* 580 */       if (bool) { stringBuilder.append(','); }
/* 581 */       else { bool = true; }
/* 582 */        stringBuilder.append("delete");
/*     */     } 
/*     */     
/* 585 */     if ((paramInt & 0x10) == 16) {
/* 586 */       if (bool) { stringBuilder.append(','); }
/* 587 */       else { bool = true; }
/* 588 */        stringBuilder.append("readlink");
/*     */     } 
/*     */     
/* 591 */     return stringBuilder.toString();
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
/*     */   public String getActions() {
/* 604 */     if (this.actions == null) {
/* 605 */       this.actions = getActions(this.mask);
/*     */     }
/* 607 */     return this.actions;
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
/*     */   public PermissionCollection newPermissionCollection() {
/* 642 */     return new FilePermissionCollection();
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 655 */     if (this.actions == null)
/* 656 */       getActions(); 
/* 657 */     paramObjectOutputStream.defaultWriteObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 668 */     paramObjectInputStream.defaultReadObject();
/* 669 */     init(getMask(this.actions));
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\io\FilePermission.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */