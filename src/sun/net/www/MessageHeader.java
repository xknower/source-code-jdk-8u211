/*     */ package sun.net.www;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.StringJoiner;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MessageHeader
/*     */ {
/*     */   private String[] keys;
/*     */   private String[] values;
/*     */   private int nkeys;
/*     */   
/*     */   public MessageHeader() {
/*  50 */     grow();
/*     */   }
/*     */   
/*     */   public MessageHeader(InputStream paramInputStream) throws IOException {
/*  54 */     parseHeader(paramInputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String getHeaderNamesInList() {
/*  61 */     StringJoiner stringJoiner = new StringJoiner(",");
/*  62 */     for (byte b = 0; b < this.nkeys; b++) {
/*  63 */       stringJoiner.add(this.keys[b]);
/*     */     }
/*  65 */     return stringJoiner.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void reset() {
/*  72 */     this.keys = null;
/*  73 */     this.values = null;
/*  74 */     this.nkeys = 0;
/*  75 */     grow();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String findValue(String paramString) {
/*  85 */     if (paramString == null)
/*  86 */     { for (int i = this.nkeys; --i >= 0;) {
/*  87 */         if (this.keys[i] == null)
/*  88 */           return this.values[i]; 
/*     */       }  }
/*  90 */     else { for (int i = this.nkeys; --i >= 0;) {
/*  91 */         if (paramString.equalsIgnoreCase(this.keys[i]))
/*  92 */           return this.values[i]; 
/*     */       }  }
/*  94 */      return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized int getKey(String paramString) {
/*  99 */     for (int i = this.nkeys; --i >= 0;) {
/* 100 */       if (this.keys[i] == paramString || (paramString != null && paramString
/* 101 */         .equalsIgnoreCase(this.keys[i])))
/* 102 */         return i; 
/* 103 */     }  return -1;
/*     */   }
/*     */   
/*     */   public synchronized String getKey(int paramInt) {
/* 107 */     if (paramInt < 0 || paramInt >= this.nkeys) return null; 
/* 108 */     return this.keys[paramInt];
/*     */   }
/*     */   
/*     */   public synchronized String getValue(int paramInt) {
/* 112 */     if (paramInt < 0 || paramInt >= this.nkeys) return null; 
/* 113 */     return this.values[paramInt];
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
/*     */   public synchronized String findNextValue(String paramString1, String paramString2) {
/* 128 */     boolean bool = false;
/* 129 */     if (paramString1 == null)
/* 130 */     { for (int i = this.nkeys; --i >= 0;) {
/* 131 */         if (this.keys[i] == null)
/* 132 */         { if (bool)
/* 133 */             return this.values[i]; 
/* 134 */           if (this.values[i] == paramString2)
/* 135 */             bool = true;  } 
/*     */       }  }
/* 137 */     else { for (int i = this.nkeys; --i >= 0;)
/* 138 */       { if (paramString1.equalsIgnoreCase(this.keys[i]))
/* 139 */         { if (bool)
/* 140 */             return this.values[i]; 
/* 141 */           if (this.values[i] == paramString2)
/* 142 */             bool = true;  }  }  }
/* 143 */      return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean filterNTLMResponses(String paramString) {
/* 152 */     boolean bool = false; byte b;
/* 153 */     for (b = 0; b < this.nkeys; b++) {
/* 154 */       if (paramString.equalsIgnoreCase(this.keys[b]) && this.values[b] != null && this.values[b]
/* 155 */         .length() > 5 && this.values[b]
/* 156 */         .substring(0, 5).equalsIgnoreCase("NTLM ")) {
/* 157 */         bool = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 161 */     if (bool) {
/* 162 */       b = 0;
/* 163 */       for (byte b1 = 0; b1 < this.nkeys; b1++) {
/* 164 */         if (!paramString.equalsIgnoreCase(this.keys[b1]) || (
/* 165 */           !"Negotiate".equalsIgnoreCase(this.values[b1]) && 
/* 166 */           !"Kerberos".equalsIgnoreCase(this.values[b1]))) {
/*     */ 
/*     */           
/* 169 */           if (b1 != b) {
/* 170 */             this.keys[b] = this.keys[b1];
/* 171 */             this.values[b] = this.values[b1];
/*     */           } 
/* 173 */           b++;
/*     */         } 
/* 175 */       }  if (b != this.nkeys) {
/* 176 */         this.nkeys = b;
/* 177 */         return true;
/*     */       } 
/*     */     } 
/* 180 */     return false;
/*     */   }
/*     */   
/*     */   class HeaderIterator implements Iterator<String> {
/* 184 */     int index = 0;
/* 185 */     int next = -1;
/*     */     String key;
/*     */     boolean haveNext = false;
/*     */     Object lock;
/*     */     
/*     */     public HeaderIterator(String param1String, Object param1Object) {
/* 191 */       this.key = param1String;
/* 192 */       this.lock = param1Object;
/*     */     }
/*     */     public boolean hasNext() {
/* 195 */       synchronized (this.lock) {
/* 196 */         if (this.haveNext) {
/* 197 */           return true;
/*     */         }
/* 199 */         while (this.index < MessageHeader.this.nkeys) {
/* 200 */           if (this.key.equalsIgnoreCase(MessageHeader.this.keys[this.index])) {
/* 201 */             this.haveNext = true;
/* 202 */             this.next = this.index++;
/* 203 */             return true;
/*     */           } 
/* 205 */           this.index++;
/*     */         } 
/* 207 */         return false;
/*     */       } 
/*     */     }
/*     */     public String next() {
/* 211 */       synchronized (this.lock) {
/* 212 */         if (this.haveNext) {
/* 213 */           this.haveNext = false;
/* 214 */           return MessageHeader.this.values[this.next];
/*     */         } 
/* 216 */         if (hasNext()) {
/* 217 */           return next();
/*     */         }
/* 219 */         throw new NoSuchElementException("No more elements");
/*     */       } 
/*     */     }
/*     */     
/*     */     public void remove() {
/* 224 */       throw new UnsupportedOperationException("remove not allowed");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<String> multiValueIterator(String paramString) {
/* 233 */     return new HeaderIterator(paramString, this);
/*     */   }
/*     */   
/*     */   public synchronized Map<String, List<String>> getHeaders() {
/* 237 */     return getHeaders(null);
/*     */   }
/*     */   
/*     */   public synchronized Map<String, List<String>> getHeaders(String[] paramArrayOfString) {
/* 241 */     return filterAndAddHeaders(paramArrayOfString, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized Map<String, List<String>> filterAndAddHeaders(String[] paramArrayOfString, Map<String, List<String>> paramMap) {
/* 246 */     boolean bool = false;
/* 247 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 248 */     for (int i = this.nkeys; --i >= 0; ) {
/* 249 */       if (paramArrayOfString != null)
/*     */       {
/*     */         
/* 252 */         for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 253 */           if (paramArrayOfString[b] != null && paramArrayOfString[b]
/* 254 */             .equalsIgnoreCase(this.keys[i])) {
/* 255 */             bool = true;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/* 260 */       if (!bool) {
/* 261 */         List<String> list = (List)hashMap.get(this.keys[i]);
/* 262 */         if (list == null) {
/* 263 */           list = new ArrayList();
/* 264 */           hashMap.put(this.keys[i], list);
/*     */         } 
/* 266 */         list.add(this.values[i]);
/*     */         continue;
/*     */       } 
/* 269 */       bool = false;
/*     */     } 
/*     */ 
/*     */     
/* 273 */     if (paramMap != null) {
/* 274 */       for (Map.Entry<String, List<String>> entry : paramMap.entrySet()) {
/* 275 */         List list = (List)hashMap.get(entry.getKey());
/* 276 */         if (list == null) {
/* 277 */           list = new ArrayList();
/* 278 */           hashMap.put(entry.getKey(), list);
/*     */         } 
/* 280 */         list.addAll((Collection)entry.getValue());
/*     */       } 
/*     */     }
/*     */     
/* 284 */     for (String str : hashMap.keySet()) {
/* 285 */       hashMap.put(str, Collections.unmodifiableList((List)hashMap.get(str)));
/*     */     }
/*     */     
/* 288 */     return (Map)Collections.unmodifiableMap(hashMap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void print(PrintStream paramPrintStream) {
/* 295 */     for (byte b = 0; b < this.nkeys; b++) {
/* 296 */       if (this.keys[b] != null) {
/* 297 */         paramPrintStream.print(this.keys[b] + ((this.values[b] != null) ? (": " + this.values[b]) : "") + "\r\n");
/*     */       }
/*     */     } 
/* 300 */     paramPrintStream.print("\r\n");
/* 301 */     paramPrintStream.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void add(String paramString1, String paramString2) {
/* 307 */     grow();
/* 308 */     this.keys[this.nkeys] = paramString1;
/* 309 */     this.values[this.nkeys] = paramString2;
/* 310 */     this.nkeys++;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void prepend(String paramString1, String paramString2) {
/* 316 */     grow();
/* 317 */     for (int i = this.nkeys; i > 0; i--) {
/* 318 */       this.keys[i] = this.keys[i - 1];
/* 319 */       this.values[i] = this.values[i - 1];
/*     */     } 
/* 321 */     this.keys[0] = paramString1;
/* 322 */     this.values[0] = paramString2;
/* 323 */     this.nkeys++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void set(int paramInt, String paramString1, String paramString2) {
/* 332 */     grow();
/* 333 */     if (paramInt < 0)
/*     */       return; 
/* 335 */     if (paramInt >= this.nkeys) {
/* 336 */       add(paramString1, paramString2);
/*     */     } else {
/* 338 */       this.keys[paramInt] = paramString1;
/* 339 */       this.values[paramInt] = paramString2;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void grow() {
/* 347 */     if (this.keys == null || this.nkeys >= this.keys.length) {
/* 348 */       String[] arrayOfString1 = new String[this.nkeys + 4];
/* 349 */       String[] arrayOfString2 = new String[this.nkeys + 4];
/* 350 */       if (this.keys != null)
/* 351 */         System.arraycopy(this.keys, 0, arrayOfString1, 0, this.nkeys); 
/* 352 */       if (this.values != null)
/* 353 */         System.arraycopy(this.values, 0, arrayOfString2, 0, this.nkeys); 
/* 354 */       this.keys = arrayOfString1;
/* 355 */       this.values = arrayOfString2;
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
/*     */   public synchronized void remove(String paramString) {
/* 367 */     if (paramString == null) {
/* 368 */       for (byte b = 0; b < this.nkeys; b++) {
/* 369 */         while (this.keys[b] == null && b < this.nkeys) {
/* 370 */           for (byte b1 = b; b1 < this.nkeys - 1; b1++) {
/* 371 */             this.keys[b1] = this.keys[b1 + 1];
/* 372 */             this.values[b1] = this.values[b1 + 1];
/*     */           } 
/* 374 */           this.nkeys--;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 378 */       for (byte b = 0; b < this.nkeys; b++) {
/* 379 */         while (paramString.equalsIgnoreCase(this.keys[b]) && b < this.nkeys) {
/* 380 */           for (byte b1 = b; b1 < this.nkeys - 1; b1++) {
/* 381 */             this.keys[b1] = this.keys[b1 + 1];
/* 382 */             this.values[b1] = this.values[b1 + 1];
/*     */           } 
/* 384 */           this.nkeys--;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void set(String paramString1, String paramString2) {
/* 395 */     for (int i = this.nkeys; --i >= 0;) {
/* 396 */       if (paramString1.equalsIgnoreCase(this.keys[i])) {
/* 397 */         this.values[i] = paramString2; return;
/*     */       } 
/*     */     } 
/* 400 */     add(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setIfNotSet(String paramString1, String paramString2) {
/* 408 */     if (findValue(paramString1) == null) {
/* 409 */       add(paramString1, paramString2);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String canonicalID(String paramString) {
/* 416 */     if (paramString == null)
/* 417 */       return ""; 
/* 418 */     byte b = 0;
/* 419 */     int i = paramString.length();
/* 420 */     boolean bool = false;
/*     */     char c;
/* 422 */     while (b < i && ((c = paramString.charAt(b)) == '<' || c <= ' ')) {
/*     */       
/* 424 */       b++;
/* 425 */       bool = true;
/*     */     } 
/* 427 */     while (b < i && ((c = paramString.charAt(i - 1)) == '>' || c <= ' ')) {
/*     */       
/* 429 */       i--;
/* 430 */       bool = true;
/*     */     } 
/* 432 */     return bool ? paramString.substring(b, i) : paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public void parseHeader(InputStream paramInputStream) throws IOException {
/* 437 */     synchronized (this) {
/* 438 */       this.nkeys = 0;
/*     */     } 
/* 440 */     mergeHeader(paramInputStream);
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
/*     */   public void mergeHeader(InputStream paramInputStream) throws IOException {
/*     */     // Byte code:
/*     */     //   0: aload_1
/*     */     //   1: ifnonnull -> 5
/*     */     //   4: return
/*     */     //   5: bipush #10
/*     */     //   7: newarray char
/*     */     //   9: astore_2
/*     */     //   10: aload_1
/*     */     //   11: invokevirtual read : ()I
/*     */     //   14: istore_3
/*     */     //   15: iload_3
/*     */     //   16: bipush #10
/*     */     //   18: if_icmpeq -> 381
/*     */     //   21: iload_3
/*     */     //   22: bipush #13
/*     */     //   24: if_icmpeq -> 381
/*     */     //   27: iload_3
/*     */     //   28: iflt -> 381
/*     */     //   31: iconst_0
/*     */     //   32: istore #4
/*     */     //   34: iconst_m1
/*     */     //   35: istore #5
/*     */     //   37: iload_3
/*     */     //   38: bipush #32
/*     */     //   40: if_icmple -> 47
/*     */     //   43: iconst_1
/*     */     //   44: goto -> 48
/*     */     //   47: iconst_0
/*     */     //   48: istore #7
/*     */     //   50: aload_2
/*     */     //   51: iload #4
/*     */     //   53: iinc #4, 1
/*     */     //   56: iload_3
/*     */     //   57: i2c
/*     */     //   58: castore
/*     */     //   59: aload_1
/*     */     //   60: invokevirtual read : ()I
/*     */     //   63: dup
/*     */     //   64: istore #6
/*     */     //   66: iflt -> 250
/*     */     //   69: iload #6
/*     */     //   71: lookupswitch default -> 209, 9 -> 140, 10 -> 150, 13 -> 150, 32 -> 144, 58 -> 120
/*     */     //   120: iload #7
/*     */     //   122: ifeq -> 134
/*     */     //   125: iload #4
/*     */     //   127: ifle -> 134
/*     */     //   130: iload #4
/*     */     //   132: istore #5
/*     */     //   134: iconst_0
/*     */     //   135: istore #7
/*     */     //   137: goto -> 209
/*     */     //   140: bipush #32
/*     */     //   142: istore #6
/*     */     //   144: iconst_0
/*     */     //   145: istore #7
/*     */     //   147: goto -> 209
/*     */     //   150: aload_1
/*     */     //   151: invokevirtual read : ()I
/*     */     //   154: istore_3
/*     */     //   155: iload #6
/*     */     //   157: bipush #13
/*     */     //   159: if_icmpne -> 184
/*     */     //   162: iload_3
/*     */     //   163: bipush #10
/*     */     //   165: if_icmpne -> 184
/*     */     //   168: aload_1
/*     */     //   169: invokevirtual read : ()I
/*     */     //   172: istore_3
/*     */     //   173: iload_3
/*     */     //   174: bipush #13
/*     */     //   176: if_icmpne -> 184
/*     */     //   179: aload_1
/*     */     //   180: invokevirtual read : ()I
/*     */     //   183: istore_3
/*     */     //   184: iload_3
/*     */     //   185: bipush #10
/*     */     //   187: if_icmpeq -> 252
/*     */     //   190: iload_3
/*     */     //   191: bipush #13
/*     */     //   193: if_icmpeq -> 252
/*     */     //   196: iload_3
/*     */     //   197: bipush #32
/*     */     //   199: if_icmple -> 205
/*     */     //   202: goto -> 252
/*     */     //   205: bipush #32
/*     */     //   207: istore #6
/*     */     //   209: iload #4
/*     */     //   211: aload_2
/*     */     //   212: arraylength
/*     */     //   213: if_icmplt -> 237
/*     */     //   216: aload_2
/*     */     //   217: arraylength
/*     */     //   218: iconst_2
/*     */     //   219: imul
/*     */     //   220: newarray char
/*     */     //   222: astore #8
/*     */     //   224: aload_2
/*     */     //   225: iconst_0
/*     */     //   226: aload #8
/*     */     //   228: iconst_0
/*     */     //   229: iload #4
/*     */     //   231: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
/*     */     //   234: aload #8
/*     */     //   236: astore_2
/*     */     //   237: aload_2
/*     */     //   238: iload #4
/*     */     //   240: iinc #4, 1
/*     */     //   243: iload #6
/*     */     //   245: i2c
/*     */     //   246: castore
/*     */     //   247: goto -> 59
/*     */     //   250: iconst_m1
/*     */     //   251: istore_3
/*     */     //   252: iload #4
/*     */     //   254: ifle -> 274
/*     */     //   257: aload_2
/*     */     //   258: iload #4
/*     */     //   260: iconst_1
/*     */     //   261: isub
/*     */     //   262: caload
/*     */     //   263: bipush #32
/*     */     //   265: if_icmpgt -> 274
/*     */     //   268: iinc #4, -1
/*     */     //   271: goto -> 252
/*     */     //   274: iload #5
/*     */     //   276: ifgt -> 288
/*     */     //   279: aconst_null
/*     */     //   280: astore #8
/*     */     //   282: iconst_0
/*     */     //   283: istore #5
/*     */     //   285: goto -> 338
/*     */     //   288: aload_2
/*     */     //   289: iconst_0
/*     */     //   290: iload #5
/*     */     //   292: invokestatic copyValueOf : ([CII)Ljava/lang/String;
/*     */     //   295: astore #8
/*     */     //   297: iload #5
/*     */     //   299: iload #4
/*     */     //   301: if_icmpge -> 316
/*     */     //   304: aload_2
/*     */     //   305: iload #5
/*     */     //   307: caload
/*     */     //   308: bipush #58
/*     */     //   310: if_icmpne -> 316
/*     */     //   313: iinc #5, 1
/*     */     //   316: iload #5
/*     */     //   318: iload #4
/*     */     //   320: if_icmpge -> 338
/*     */     //   323: aload_2
/*     */     //   324: iload #5
/*     */     //   326: caload
/*     */     //   327: bipush #32
/*     */     //   329: if_icmpgt -> 338
/*     */     //   332: iinc #5, 1
/*     */     //   335: goto -> 316
/*     */     //   338: iload #5
/*     */     //   340: iload #4
/*     */     //   342: if_icmplt -> 357
/*     */     //   345: new java/lang/String
/*     */     //   348: dup
/*     */     //   349: invokespecial <init> : ()V
/*     */     //   352: astore #9
/*     */     //   354: goto -> 370
/*     */     //   357: aload_2
/*     */     //   358: iload #5
/*     */     //   360: iload #4
/*     */     //   362: iload #5
/*     */     //   364: isub
/*     */     //   365: invokestatic copyValueOf : ([CII)Ljava/lang/String;
/*     */     //   368: astore #9
/*     */     //   370: aload_0
/*     */     //   371: aload #8
/*     */     //   373: aload #9
/*     */     //   375: invokevirtual add : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   378: goto -> 15
/*     */     //   381: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #446	-> 0
/*     */     //   #447	-> 4
/*     */     //   #448	-> 5
/*     */     //   #449	-> 10
/*     */     //   #450	-> 15
/*     */     //   #451	-> 31
/*     */     //   #452	-> 34
/*     */     //   #454	-> 37
/*     */     //   #455	-> 50
/*     */     //   #457	-> 59
/*     */     //   #458	-> 69
/*     */     //   #460	-> 120
/*     */     //   #461	-> 130
/*     */     //   #462	-> 134
/*     */     //   #463	-> 137
/*     */     //   #465	-> 140
/*     */     //   #468	-> 144
/*     */     //   #469	-> 147
/*     */     //   #472	-> 150
/*     */     //   #473	-> 155
/*     */     //   #474	-> 168
/*     */     //   #475	-> 173
/*     */     //   #476	-> 179
/*     */     //   #478	-> 184
/*     */     //   #479	-> 202
/*     */     //   #481	-> 205
/*     */     //   #484	-> 209
/*     */     //   #485	-> 216
/*     */     //   #486	-> 224
/*     */     //   #487	-> 234
/*     */     //   #489	-> 237
/*     */     //   #491	-> 250
/*     */     //   #493	-> 252
/*     */     //   #494	-> 268
/*     */     //   #496	-> 274
/*     */     //   #497	-> 279
/*     */     //   #498	-> 282
/*     */     //   #500	-> 288
/*     */     //   #501	-> 297
/*     */     //   #502	-> 313
/*     */     //   #503	-> 316
/*     */     //   #504	-> 332
/*     */     //   #507	-> 338
/*     */     //   #508	-> 345
/*     */     //   #510	-> 357
/*     */     //   #511	-> 370
/*     */     //   #512	-> 378
/*     */     //   #513	-> 381
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
/*     */   public synchronized String toString() {
/* 516 */     String str = super.toString() + this.nkeys + " pairs: ";
/* 517 */     for (byte b = 0; b < this.keys.length && b < this.nkeys; b++) {
/* 518 */       str = str + "{" + this.keys[b] + ": " + this.values[b] + "}";
/*     */     }
/* 520 */     return str;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\net\www\MessageHeader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */