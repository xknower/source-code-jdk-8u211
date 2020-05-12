/*     */ package com.sun.java.util.jar.pack;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ClassReader
/*     */ {
/*     */   int verbose;
/*     */   Package pkg;
/*     */   Package.Class cls;
/*     */   long inPos;
/*  57 */   long constantPoolLimit = -1L;
/*     */   DataInputStream in;
/*     */   Map<Attribute.Layout, Attribute> attrDefs;
/*     */   Map<Attribute.Layout, String> attrCommands;
/*  61 */   String unknownAttrCommand = "error"; boolean haveUnresolvedEntry;
/*     */   
/*     */   ClassReader(Package.Class paramClass, InputStream paramInputStream) throws IOException {
/*  64 */     this.pkg = paramClass.getPackage();
/*  65 */     this.cls = paramClass;
/*  66 */     this.verbose = this.pkg.verbose;
/*  67 */     this.in = new DataInputStream(new FilterInputStream(paramInputStream) {
/*     */           public int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/*  69 */             int i = super.read(param1ArrayOfbyte, param1Int1, param1Int2);
/*  70 */             if (i >= 0) ClassReader.this.inPos += i; 
/*  71 */             return i;
/*     */           }
/*     */           public int read() throws IOException {
/*  74 */             int i = super.read();
/*  75 */             if (i >= 0) ClassReader.this.inPos++; 
/*  76 */             return i;
/*     */           }
/*     */           public long skip(long param1Long) throws IOException {
/*  79 */             long l = super.skip(param1Long);
/*  80 */             if (l >= 0L) ClassReader.this.inPos += l; 
/*  81 */             return l;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void setAttrDefs(Map<Attribute.Layout, Attribute> paramMap) {
/*  87 */     this.attrDefs = paramMap;
/*     */   }
/*     */   
/*     */   public void setAttrCommands(Map<Attribute.Layout, String> paramMap) {
/*  91 */     this.attrCommands = paramMap;
/*     */   }
/*     */   
/*     */   private void skip(int paramInt, String paramString) throws IOException {
/*  95 */     Utils.log.warning("skipping " + paramInt + " bytes of " + paramString);
/*  96 */     long l = 0L;
/*  97 */     while (l < paramInt) {
/*  98 */       long l1 = this.in.skip(paramInt - l);
/*  99 */       assert l1 > 0L;
/* 100 */       l += l1;
/*     */     } 
/* 102 */     assert l == paramInt;
/*     */   }
/*     */   
/*     */   private int readUnsignedShort() throws IOException {
/* 106 */     return this.in.readUnsignedShort();
/*     */   }
/*     */   
/*     */   private int readInt() throws IOException {
/* 110 */     return this.in.readInt();
/*     */   }
/*     */ 
/*     */   
/*     */   private ConstantPool.Entry readRef() throws IOException {
/* 115 */     int i = this.in.readUnsignedShort();
/* 116 */     return (i == 0) ? null : this.cls.cpMap[i];
/*     */   }
/*     */   
/*     */   private ConstantPool.Entry readRef(byte paramByte) throws IOException {
/* 120 */     ConstantPool.Entry entry = readRef();
/* 121 */     assert !(entry instanceof UnresolvedEntry);
/* 122 */     checkTag(entry, paramByte);
/* 123 */     return entry;
/*     */   }
/*     */ 
/*     */   
/*     */   private ConstantPool.Entry checkTag(ConstantPool.Entry paramEntry, byte paramByte) throws ClassFormatException {
/* 128 */     if (paramEntry == null || !paramEntry.tagMatches(paramByte)) {
/* 129 */       String str1 = (this.inPos == this.constantPoolLimit) ? " in constant pool" : (" at pos: " + this.inPos);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 134 */       String str2 = (paramEntry == null) ? "null CP index" : ("type=" + ConstantPool.tagName(paramEntry.tag));
/* 135 */       throw new ClassFormatException("Bad constant, expected type=" + 
/* 136 */           ConstantPool.tagName(paramByte) + " got " + str2 + ", in File: " + this.cls.file.nameString + str1);
/*     */     } 
/*     */     
/* 139 */     return paramEntry;
/*     */   }
/*     */   private ConstantPool.Entry checkTag(ConstantPool.Entry paramEntry, byte paramByte, boolean paramBoolean) throws ClassFormatException {
/* 142 */     return (paramBoolean && paramEntry == null) ? null : checkTag(paramEntry, paramByte);
/*     */   }
/*     */   
/*     */   private ConstantPool.Entry readRefOrNull(byte paramByte) throws IOException {
/* 146 */     ConstantPool.Entry entry = readRef();
/* 147 */     checkTag(entry, paramByte, true);
/* 148 */     return entry;
/*     */   }
/*     */   
/*     */   private ConstantPool.Utf8Entry readUtf8Ref() throws IOException {
/* 152 */     return (ConstantPool.Utf8Entry)readRef((byte)1);
/*     */   }
/*     */   
/*     */   private ConstantPool.ClassEntry readClassRef() throws IOException {
/* 156 */     return (ConstantPool.ClassEntry)readRef((byte)7);
/*     */   }
/*     */   
/*     */   private ConstantPool.ClassEntry readClassRefOrNull() throws IOException {
/* 160 */     return (ConstantPool.ClassEntry)readRefOrNull((byte)7);
/*     */   }
/*     */ 
/*     */   
/*     */   private ConstantPool.SignatureEntry readSignatureRef() throws IOException {
/* 165 */     ConstantPool.Entry entry = readRef((byte)13);
/* 166 */     return (entry != null && entry.getTag() == 1) ? 
/* 167 */       ConstantPool.getSignatureEntry(entry.stringValue()) : (ConstantPool.SignatureEntry)entry;
/*     */   }
/*     */ 
/*     */   
/*     */   void read() throws IOException {
/* 172 */     boolean bool = false;
/*     */     try {
/* 174 */       readMagicNumbers();
/* 175 */       readConstantPool();
/* 176 */       readHeader();
/* 177 */       readMembers(false);
/* 178 */       readMembers(true);
/* 179 */       readAttributes(0, this.cls);
/* 180 */       fixUnresolvedEntries();
/* 181 */       this.cls.finishReading();
/* 182 */       assert 0 >= this.in.read(new byte[1]);
/* 183 */       bool = true;
/*     */     } finally {
/* 185 */       if (!bool && 
/* 186 */         this.verbose > 0) Utils.log.warning("Erroneous data at input offset " + this.inPos + " of " + this.cls.file);
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   void readMagicNumbers() throws IOException {
/* 192 */     this.cls.magic = this.in.readInt();
/* 193 */     if (this.cls.magic != -889275714) {
/* 194 */       throw new Attribute.FormatException("Bad magic number in class file " + 
/*     */           
/* 196 */           Integer.toHexString(this.cls.magic), 0, "magic-number", "pass");
/*     */     }
/* 198 */     short s1 = (short)readUnsignedShort();
/* 199 */     short s2 = (short)readUnsignedShort();
/* 200 */     this.cls.version = Package.Version.of(s2, s1);
/*     */ 
/*     */     
/* 203 */     String str = checkVersion(this.cls.version);
/* 204 */     if (str != null) {
/* 205 */       throw new Attribute.FormatException("classfile version too " + str + ": " + this.cls.version + " in " + this.cls.file, 0, "version", "pass");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String checkVersion(Package.Version paramVersion) {
/* 213 */     short s1 = paramVersion.major;
/* 214 */     short s2 = paramVersion.minor;
/* 215 */     if (s1 < this.pkg.minClassVersion.major || (s1 == this.pkg.minClassVersion.major && s2 < this.pkg.minClassVersion.minor))
/*     */     {
/*     */       
/* 218 */       return "small";
/*     */     }
/* 220 */     if (s1 > this.pkg.maxClassVersion.major || (s1 == this.pkg.maxClassVersion.major && s2 > this.pkg.maxClassVersion.minor))
/*     */     {
/*     */       
/* 223 */       return "large";
/*     */     }
/* 225 */     return null;
/*     */   }
/*     */   
/*     */   void readConstantPool() throws IOException {
/* 229 */     int i = this.in.readUnsignedShort();
/*     */ 
/*     */     
/* 232 */     int[] arrayOfInt = new int[i * 4];
/* 233 */     byte b1 = 0;
/*     */     
/* 235 */     ConstantPool.Entry[] arrayOfEntry = new ConstantPool.Entry[i];
/* 236 */     arrayOfEntry[0] = null; byte b2;
/* 237 */     for (b2 = 1; b2 < i; b2++) {
/*     */       
/* 239 */       byte b = this.in.readByte();
/* 240 */       switch (b) {
/*     */         case 1:
/* 242 */           arrayOfEntry[b2] = ConstantPool.getUtf8Entry(this.in.readUTF());
/*     */           break;
/*     */         
/*     */         case 3:
/* 246 */           arrayOfEntry[b2] = ConstantPool.getLiteralEntry(Integer.valueOf(this.in.readInt()));
/*     */           break;
/*     */ 
/*     */         
/*     */         case 4:
/* 251 */           arrayOfEntry[b2] = ConstantPool.getLiteralEntry(Float.valueOf(this.in.readFloat()));
/*     */           break;
/*     */ 
/*     */         
/*     */         case 5:
/* 256 */           arrayOfEntry[b2] = ConstantPool.getLiteralEntry(Long.valueOf(this.in.readLong()));
/* 257 */           arrayOfEntry[++b2] = null;
/*     */           break;
/*     */ 
/*     */         
/*     */         case 6:
/* 262 */           arrayOfEntry[b2] = ConstantPool.getLiteralEntry(Double.valueOf(this.in.readDouble()));
/* 263 */           arrayOfEntry[++b2] = null;
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case 7:
/*     */         case 8:
/*     */         case 16:
/* 271 */           arrayOfInt[b1++] = b2;
/* 272 */           arrayOfInt[b1++] = b;
/* 273 */           arrayOfInt[b1++] = this.in.readUnsignedShort();
/* 274 */           arrayOfInt[b1++] = -1;
/*     */           break;
/*     */         case 9:
/*     */         case 10:
/*     */         case 11:
/*     */         case 12:
/* 280 */           arrayOfInt[b1++] = b2;
/* 281 */           arrayOfInt[b1++] = b;
/* 282 */           arrayOfInt[b1++] = this.in.readUnsignedShort();
/* 283 */           arrayOfInt[b1++] = this.in.readUnsignedShort();
/*     */           break;
/*     */         case 18:
/* 286 */           arrayOfInt[b1++] = b2;
/* 287 */           arrayOfInt[b1++] = b;
/* 288 */           arrayOfInt[b1++] = 0xFFFFFFFF ^ this.in.readUnsignedShort();
/* 289 */           arrayOfInt[b1++] = this.in.readUnsignedShort();
/*     */           break;
/*     */         case 15:
/* 292 */           arrayOfInt[b1++] = b2;
/* 293 */           arrayOfInt[b1++] = b;
/* 294 */           arrayOfInt[b1++] = 0xFFFFFFFF ^ this.in.readUnsignedByte();
/* 295 */           arrayOfInt[b1++] = this.in.readUnsignedShort();
/*     */           break;
/*     */         default:
/* 298 */           throw new ClassFormatException("Bad constant pool tag " + b + " in File: " + this.cls.file.nameString + " at pos: " + this.inPos);
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/* 303 */     this.constantPoolLimit = this.inPos;
/*     */ 
/*     */     
/* 306 */     while (b1 > 0) {
/* 307 */       if (this.verbose > 3)
/* 308 */         Utils.log.fine("CP fixups [" + (b1 / 4) + "]"); 
/* 309 */       b2 = b1;
/* 310 */       b1 = 0;
/* 311 */       for (byte b = 0; b < b2; ) {
/* 312 */         ConstantPool.ClassEntry classEntry; ConstantPool.DescriptorEntry descriptorEntry1; ConstantPool.Utf8Entry utf8Entry1, utf8Entry2; byte b3; ConstantPool.MemberEntry memberEntry; ConstantPool.DescriptorEntry descriptorEntry2; int j = arrayOfInt[b++];
/* 313 */         int k = arrayOfInt[b++];
/* 314 */         int m = arrayOfInt[b++];
/* 315 */         int n = arrayOfInt[b++];
/* 316 */         if (this.verbose > 3)
/* 317 */           Utils.log.fine("  cp[" + j + "] = " + ConstantPool.tagName(k) + "{" + m + "," + n + "}"); 
/* 318 */         if ((m >= 0 && arrayOfEntry[m] == null) || (n >= 0 && arrayOfEntry[n] == null)) {
/*     */           
/* 320 */           arrayOfInt[b1++] = j;
/* 321 */           arrayOfInt[b1++] = k;
/* 322 */           arrayOfInt[b1++] = m;
/* 323 */           arrayOfInt[b1++] = n;
/*     */           continue;
/*     */         } 
/* 326 */         switch (k) {
/*     */           case 7:
/* 328 */             arrayOfEntry[j] = ConstantPool.getClassEntry(arrayOfEntry[m].stringValue());
/*     */             continue;
/*     */           case 8:
/* 331 */             arrayOfEntry[j] = ConstantPool.getStringEntry(arrayOfEntry[m].stringValue());
/*     */             continue;
/*     */           case 9:
/*     */           case 10:
/*     */           case 11:
/* 336 */             classEntry = (ConstantPool.ClassEntry)checkTag(arrayOfEntry[m], (byte)7);
/* 337 */             descriptorEntry1 = (ConstantPool.DescriptorEntry)checkTag(arrayOfEntry[n], (byte)12);
/* 338 */             arrayOfEntry[j] = ConstantPool.getMemberEntry((byte)k, classEntry, descriptorEntry1);
/*     */             continue;
/*     */           case 12:
/* 341 */             utf8Entry1 = (ConstantPool.Utf8Entry)checkTag(arrayOfEntry[m], (byte)1);
/* 342 */             utf8Entry2 = (ConstantPool.Utf8Entry)checkTag(arrayOfEntry[n], (byte)13);
/* 343 */             arrayOfEntry[j] = ConstantPool.getDescriptorEntry(utf8Entry1, utf8Entry2);
/*     */             continue;
/*     */           case 16:
/* 346 */             arrayOfEntry[j] = ConstantPool.getMethodTypeEntry((ConstantPool.Utf8Entry)checkTag(arrayOfEntry[m], (byte)13));
/*     */             continue;
/*     */           case 15:
/* 349 */             b3 = (byte)(0xFFFFFFFF ^ m);
/* 350 */             memberEntry = (ConstantPool.MemberEntry)checkTag(arrayOfEntry[n], (byte)52);
/* 351 */             arrayOfEntry[j] = ConstantPool.getMethodHandleEntry(b3, memberEntry);
/*     */             continue;
/*     */           case 18:
/* 354 */             descriptorEntry2 = (ConstantPool.DescriptorEntry)checkTag(arrayOfEntry[n], (byte)12);
/* 355 */             arrayOfEntry[j] = new UnresolvedEntry((byte)k, new Object[] { Integer.valueOf(0xFFFFFFFF ^ m), descriptorEntry2 });
/*     */             continue;
/*     */         } 
/*     */ 
/*     */         
/*     */         assert false;
/*     */       } 
/* 362 */       assert b1 < b2;
/*     */     } 
/*     */     
/* 365 */     this.cls.cpMap = arrayOfEntry;
/*     */   }
/*     */   
/*     */   private class UnresolvedEntry extends ConstantPool.Entry {
/*     */     final Object[] refsOrIndexes;
/*     */     
/*     */     UnresolvedEntry(byte param1Byte, Object... param1VarArgs) {
/* 372 */       super(param1Byte);
/* 373 */       this.refsOrIndexes = param1VarArgs;
/* 374 */       ClassReader.this.haveUnresolvedEntry = true; } ConstantPool.Entry resolve() {
/*     */       ConstantPool.BootstrapMethodEntry bootstrapMethodEntry;
/*     */       ConstantPool.DescriptorEntry descriptorEntry;
/* 377 */       Package.Class clazz = ClassReader.this.cls;
/*     */       
/* 379 */       switch (this.tag) {
/*     */         case 18:
/* 381 */           bootstrapMethodEntry = clazz.bootstrapMethods.get(((Integer)this.refsOrIndexes[0]).intValue());
/* 382 */           descriptorEntry = (ConstantPool.DescriptorEntry)this.refsOrIndexes[1];
/* 383 */           return ConstantPool.getInvokeDynamicEntry(bootstrapMethodEntry, descriptorEntry);
/*     */       } 
/*     */       
/* 386 */       throw new AssertionError();
/*     */     }
/*     */     
/*     */     private void unresolved() {
/* 390 */       throw new RuntimeException("unresolved entry has no string");
/* 391 */     } public int compareTo(Object param1Object) { unresolved(); return 0; }
/* 392 */     public boolean equals(Object param1Object) { unresolved(); return false; }
/* 393 */     protected int computeValueHash() { unresolved(); return 0; }
/* 394 */     public String stringValue() { unresolved(); return toString(); } public String toString() {
/* 395 */       return "(unresolved " + ConstantPool.tagName(this.tag) + ")";
/*     */     }
/*     */   }
/*     */   
/*     */   private void fixUnresolvedEntries() {
/* 400 */     if (!this.haveUnresolvedEntry)
/* 401 */       return;  ConstantPool.Entry[] arrayOfEntry = this.cls.getCPMap();
/* 402 */     for (byte b = 0; b < arrayOfEntry.length; b++) {
/* 403 */       ConstantPool.Entry entry = arrayOfEntry[b];
/* 404 */       if (entry instanceof UnresolvedEntry) {
/* 405 */         arrayOfEntry[b] = entry = ((UnresolvedEntry)entry).resolve();
/* 406 */         assert !(entry instanceof UnresolvedEntry);
/*     */       } 
/*     */     } 
/* 409 */     this.haveUnresolvedEntry = false;
/*     */   }
/*     */   
/*     */   void readHeader() throws IOException {
/* 413 */     this.cls.flags = readUnsignedShort();
/* 414 */     this.cls.thisClass = readClassRef();
/* 415 */     this.cls.superClass = readClassRefOrNull();
/* 416 */     int i = readUnsignedShort();
/* 417 */     this.cls.interfaces = new ConstantPool.ClassEntry[i];
/* 418 */     for (byte b = 0; b < i; b++) {
/* 419 */       this.cls.interfaces[b] = readClassRef();
/*     */     }
/*     */   }
/*     */   
/*     */   void readMembers(boolean paramBoolean) throws IOException {
/* 424 */     int i = readUnsignedShort();
/* 425 */     for (byte b = 0; b < i; b++)
/* 426 */       readMember(paramBoolean); 
/*     */   }
/*     */   
/*     */   void readMember(boolean paramBoolean) throws IOException {
/*     */     Package.Class.Method method;
/* 431 */     int i = readUnsignedShort();
/* 432 */     ConstantPool.Utf8Entry utf8Entry = readUtf8Ref();
/* 433 */     ConstantPool.SignatureEntry signatureEntry = readSignatureRef();
/* 434 */     ConstantPool.DescriptorEntry descriptorEntry = ConstantPool.getDescriptorEntry(utf8Entry, signatureEntry);
/*     */     
/* 436 */     if (!paramBoolean) {
/* 437 */       this.cls.getClass(); Package.Class.Field field = new Package.Class.Field(this.cls, i, descriptorEntry);
/*     */     } else {
/* 439 */       this.cls.getClass(); method = new Package.Class.Method(this.cls, i, descriptorEntry);
/* 440 */     }  readAttributes(!paramBoolean ? 1 : 2, method);
/*     */   }
/*     */   
/*     */   void readAttributes(int paramInt, Attribute.Holder paramHolder) throws IOException {
/* 444 */     int i = readUnsignedShort();
/* 445 */     if (i == 0)
/* 446 */       return;  if (this.verbose > 3)
/* 447 */       Utils.log.fine("readAttributes " + paramHolder + " [" + i + "]"); 
/* 448 */     for (byte b = 0; b < i; ) {
/* 449 */       String str = readUtf8Ref().stringValue();
/* 450 */       int j = readInt();
/*     */       
/* 452 */       if (this.attrCommands != null) {
/* 453 */         Attribute.Layout layout = Attribute.keyForLookup(paramInt, str);
/* 454 */         String str1 = this.attrCommands.get(layout);
/* 455 */         if (str1 != null) {
/* 456 */           Attribute attribute; boolean bool; String str2; long l; String str3, str4; switch (str1) {
/*     */             case "pass":
/* 458 */               str3 = "passing attribute bitwise in " + paramHolder;
/* 459 */               throw new Attribute.FormatException(str3, paramInt, str, str1);
/*     */             case "error":
/* 461 */               str4 = "attribute not allowed in " + paramHolder;
/* 462 */               throw new Attribute.FormatException(str4, paramInt, str, str1);
/*     */             case "strip":
/* 464 */               skip(j, str + " attribute in " + paramHolder);
/*     */               break;
/*     */ 
/*     */ 
/*     */             
/*     */             default:
/* 470 */               attribute = Attribute.lookup(Package.attrDefs, paramInt, str);
/* 471 */               if (this.verbose > 4 && attribute != null)
/* 472 */                 Utils.log.fine("pkg_attribute_lookup " + str + " = " + attribute); 
/* 473 */               if (attribute == null) {
/* 474 */                 attribute = Attribute.lookup(this.attrDefs, paramInt, str);
/* 475 */                 if (this.verbose > 4 && attribute != null)
/* 476 */                   Utils.log.fine("this " + str + " = " + attribute); 
/*     */               } 
/* 478 */               if (attribute == null) {
/* 479 */                 attribute = Attribute.lookup(null, paramInt, str);
/* 480 */                 if (this.verbose > 4 && attribute != null)
/* 481 */                   Utils.log.fine("null_attribute_lookup " + str + " = " + attribute); 
/*     */               } 
/* 483 */               if (attribute == null && j == 0)
/*     */               {
/*     */ 
/*     */                 
/* 487 */                 attribute = Attribute.find(paramInt, str, "");
/*     */               }
/*     */ 
/*     */               
/* 491 */               bool = (paramInt == 3 && (str.equals("StackMap") || str.equals("StackMapX"))) ? true : false;
/* 492 */               if (bool) {
/*     */                 
/* 494 */                 Code code = (Code)paramHolder;
/*     */                 
/* 496 */                 if (code.max_stack >= 65536 || code.max_locals >= 65536 || code
/*     */                   
/* 498 */                   .getLength() >= 65536 || str
/* 499 */                   .endsWith("X"))
/*     */                 {
/*     */                   
/* 502 */                   attribute = null;
/*     */                 }
/*     */               } 
/* 505 */               if (attribute == null) {
/* 506 */                 if (bool) {
/*     */                   
/* 508 */                   str2 = "unsupported StackMap variant in " + paramHolder;
/* 509 */                   throw new Attribute.FormatException(str2, paramInt, str, "pass");
/*     */                 } 
/* 511 */                 if ("strip".equals(this.unknownAttrCommand)) {
/*     */                   
/* 513 */                   skip(j, "unknown " + str + " attribute in " + paramHolder);
/*     */                   break;
/*     */                 } 
/* 516 */                 str2 = " is unknown attribute in class " + paramHolder;
/* 517 */                 throw new Attribute.FormatException(str2, paramInt, str, this.unknownAttrCommand);
/*     */               } 
/*     */ 
/*     */               
/* 521 */               l = this.inPos;
/* 522 */               if (attribute.layout() == Package.attrCodeEmpty) {
/*     */                 
/* 524 */                 Package.Class.Method method = (Package.Class.Method)paramHolder;
/* 525 */                 method.code = new Code(method);
/*     */                 try {
/* 527 */                   readCode(method.code);
/* 528 */                 } catch (FormatException formatException) {
/* 529 */                   String str5 = formatException.getMessage() + " in " + paramHolder;
/* 530 */                   throw new ClassFormatException(str5, formatException);
/*     */                 } 
/* 532 */                 assert j == this.inPos - l;
/*     */               } else {
/* 534 */                 if (attribute.layout() == Package.attrBootstrapMethodsEmpty) {
/* 535 */                   assert paramHolder == this.cls;
/* 536 */                   readBootstrapMethods(this.cls);
/* 537 */                   assert j == this.inPos - l;
/*     */                   break;
/*     */                 } 
/* 540 */                 if (attribute.layout() == Package.attrInnerClassesEmpty) {
/*     */                   
/* 542 */                   assert paramHolder == this.cls;
/* 543 */                   readInnerClasses(this.cls);
/* 544 */                   assert j == this.inPos - l;
/*     */                 }
/* 546 */                 else if (j > 0) {
/* 547 */                   byte[] arrayOfByte = new byte[j];
/* 548 */                   this.in.readFully(arrayOfByte);
/* 549 */                   attribute = attribute.addContent(arrayOfByte);
/*     */                 } 
/* 551 */               }  if (attribute.size() == 0 && !attribute.layout().isEmpty()) {
/* 552 */                 throw new ClassFormatException(str + ": attribute length cannot be zero, in " + paramHolder);
/*     */               }
/*     */               
/* 555 */               paramHolder.addAttribute(attribute);
/* 556 */               if (this.verbose > 2)
/* 557 */                 Utils.log.fine("read " + attribute);  break;
/*     */           } 
/*     */           b++;
/*     */         } 
/*     */       } 
/* 562 */     }  } void readCode(Code paramCode) throws IOException { paramCode.max_stack = readUnsignedShort();
/* 563 */     paramCode.max_locals = readUnsignedShort();
/* 564 */     paramCode.bytes = new byte[readInt()];
/* 565 */     this.in.readFully(paramCode.bytes);
/* 566 */     ConstantPool.Entry[] arrayOfEntry = this.cls.getCPMap();
/* 567 */     Instruction.opcodeChecker(paramCode.bytes, arrayOfEntry, this.cls.version);
/* 568 */     int i = readUnsignedShort();
/* 569 */     paramCode.setHandlerCount(i);
/* 570 */     for (byte b = 0; b < i; b++) {
/* 571 */       paramCode.handler_start[b] = readUnsignedShort();
/* 572 */       paramCode.handler_end[b] = readUnsignedShort();
/* 573 */       paramCode.handler_catch[b] = readUnsignedShort();
/* 574 */       paramCode.handler_class[b] = readClassRefOrNull();
/*     */     } 
/* 576 */     readAttributes(3, paramCode); }
/*     */ 
/*     */   
/*     */   void readBootstrapMethods(Package.Class paramClass) throws IOException {
/* 580 */     ConstantPool.BootstrapMethodEntry[] arrayOfBootstrapMethodEntry = new ConstantPool.BootstrapMethodEntry[readUnsignedShort()];
/* 581 */     for (byte b = 0; b < arrayOfBootstrapMethodEntry.length; b++) {
/* 582 */       ConstantPool.MethodHandleEntry methodHandleEntry = (ConstantPool.MethodHandleEntry)readRef((byte)15);
/* 583 */       ConstantPool.Entry[] arrayOfEntry = new ConstantPool.Entry[readUnsignedShort()];
/* 584 */       for (byte b1 = 0; b1 < arrayOfEntry.length; b1++) {
/* 585 */         arrayOfEntry[b1] = readRef((byte)51);
/*     */       }
/* 587 */       arrayOfBootstrapMethodEntry[b] = ConstantPool.getBootstrapMethodEntry(methodHandleEntry, arrayOfEntry);
/*     */     } 
/* 589 */     paramClass.setBootstrapMethods(Arrays.asList(arrayOfBootstrapMethodEntry));
/*     */   }
/*     */   
/*     */   void readInnerClasses(Package.Class paramClass) throws IOException {
/* 593 */     int i = readUnsignedShort();
/* 594 */     ArrayList<Package.InnerClass> arrayList = new ArrayList(i);
/* 595 */     for (byte b = 0; b < i; b++) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 600 */       Package.InnerClass innerClass = new Package.InnerClass(readClassRef(), readClassRefOrNull(), (ConstantPool.Utf8Entry)readRefOrNull((byte)1), readUnsignedShort());
/* 601 */       arrayList.add(innerClass);
/*     */     } 
/* 603 */     paramClass.innerClasses = arrayList;
/*     */   }
/*     */   
/*     */   static class ClassFormatException
/*     */     extends IOException {
/*     */     private static final long serialVersionUID = -3564121733989501833L;
/*     */     
/*     */     public ClassFormatException(String param1String) {
/* 611 */       super(param1String);
/*     */     }
/*     */     
/*     */     public ClassFormatException(String param1String, Throwable param1Throwable) {
/* 615 */       super(param1String, param1Throwable);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jav\\util\jar\pack\ClassReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */