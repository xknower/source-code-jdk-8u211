/*     */ package com.sun.activation.registries;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
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
/*     */ public class MailcapFile
/*     */ {
/*  39 */   private Map type_hash = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  44 */   private Map fallback_hash = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   private Map native_commands = new HashMap<>();
/*     */   
/*     */   private static boolean addReverse = false;
/*     */   
/*     */   static {
/*     */     try {
/*  56 */       addReverse = Boolean.getBoolean("javax.activation.addreverse");
/*  57 */     } catch (Throwable throwable) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MailcapFile(String new_fname) throws IOException {
/*  68 */     if (LogSupport.isLoggable())
/*  69 */       LogSupport.log("new MailcapFile: file " + new_fname); 
/*  70 */     FileReader reader = null;
/*     */     try {
/*  72 */       reader = new FileReader(new_fname);
/*  73 */       parse(new BufferedReader(reader));
/*     */     } finally {
/*  75 */       if (reader != null) {
/*     */         try {
/*  77 */           reader.close();
/*  78 */         } catch (IOException iOException) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MailcapFile(InputStream is) throws IOException {
/*  89 */     if (LogSupport.isLoggable())
/*  90 */       LogSupport.log("new MailcapFile: InputStream"); 
/*  91 */     parse(new BufferedReader(new InputStreamReader(is, "iso-8859-1")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MailcapFile() {
/*  98 */     if (LogSupport.isLoggable()) {
/*  99 */       LogSupport.log("new MailcapFile: default");
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
/*     */   public Map getMailcapList(String mime_type) {
/* 111 */     Map search_result = null;
/* 112 */     Map wildcard_result = null;
/*     */ 
/*     */     
/* 115 */     search_result = (Map)this.type_hash.get(mime_type);
/*     */ 
/*     */     
/* 118 */     int separator = mime_type.indexOf('/');
/* 119 */     String subtype = mime_type.substring(separator + 1);
/* 120 */     if (!subtype.equals("*")) {
/* 121 */       String type = mime_type.substring(0, separator + 1) + "*";
/* 122 */       wildcard_result = (Map)this.type_hash.get(type);
/*     */       
/* 124 */       if (wildcard_result != null)
/* 125 */         if (search_result != null) {
/*     */           
/* 127 */           search_result = mergeResults(search_result, wildcard_result);
/*     */         } else {
/* 129 */           search_result = wildcard_result;
/*     */         }  
/*     */     } 
/* 132 */     return search_result;
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
/*     */   public Map getMailcapFallbackList(String mime_type) {
/* 144 */     Map search_result = null;
/* 145 */     Map wildcard_result = null;
/*     */ 
/*     */     
/* 148 */     search_result = (Map)this.fallback_hash.get(mime_type);
/*     */ 
/*     */     
/* 151 */     int separator = mime_type.indexOf('/');
/* 152 */     String subtype = mime_type.substring(separator + 1);
/* 153 */     if (!subtype.equals("*")) {
/* 154 */       String type = mime_type.substring(0, separator + 1) + "*";
/* 155 */       wildcard_result = (Map)this.fallback_hash.get(type);
/*     */       
/* 157 */       if (wildcard_result != null)
/* 158 */         if (search_result != null) {
/*     */           
/* 160 */           search_result = mergeResults(search_result, wildcard_result);
/*     */         } else {
/* 162 */           search_result = wildcard_result;
/*     */         }  
/*     */     } 
/* 165 */     return search_result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getMimeTypes() {
/* 172 */     Set types = new HashSet(this.type_hash.keySet());
/* 173 */     types.addAll(this.fallback_hash.keySet());
/* 174 */     types.addAll(this.native_commands.keySet());
/* 175 */     String[] mts = new String[types.size()];
/* 176 */     mts = (String[])types.toArray((Object[])mts);
/* 177 */     return mts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getNativeCommands(String mime_type) {
/* 184 */     String[] cmds = null;
/*     */     
/* 186 */     List v = (List)this.native_commands.get(mime_type.toLowerCase(Locale.ENGLISH));
/* 187 */     if (v != null) {
/* 188 */       cmds = new String[v.size()];
/* 189 */       cmds = (String[])v.toArray((Object[])cmds);
/*     */     } 
/* 191 */     return cmds;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map mergeResults(Map<?, ?> first, Map second) {
/* 201 */     Iterator<String> verb_enum = second.keySet().iterator();
/* 202 */     Map<Object, Object> clonedHash = new HashMap<>(first);
/*     */ 
/*     */     
/* 205 */     while (verb_enum.hasNext()) {
/* 206 */       String verb = verb_enum.next();
/* 207 */       List<?> cmdVector = (List)clonedHash.get(verb);
/* 208 */       if (cmdVector == null) {
/* 209 */         clonedHash.put(verb, second.get(verb));
/*     */         continue;
/*     */       } 
/* 212 */       List<?> oldV = (List)second.get(verb);
/* 213 */       cmdVector = new ArrayList(cmdVector);
/* 214 */       cmdVector.addAll(oldV);
/* 215 */       clonedHash.put(verb, cmdVector);
/*     */     } 
/*     */     
/* 218 */     return clonedHash;
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
/*     */   public void appendToMailcap(String mail_cap) {
/* 232 */     if (LogSupport.isLoggable())
/* 233 */       LogSupport.log("appendToMailcap: " + mail_cap); 
/*     */     try {
/* 235 */       parse(new StringReader(mail_cap));
/* 236 */     } catch (IOException iOException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parse(Reader reader) throws IOException {
/* 245 */     BufferedReader buf_reader = new BufferedReader(reader);
/* 246 */     String line = null;
/* 247 */     String continued = null;
/*     */     
/* 249 */     while ((line = buf_reader.readLine()) != null) {
/*     */ 
/*     */       
/* 252 */       line = line.trim();
/*     */       
/*     */       try {
/* 255 */         if (line.charAt(0) == '#')
/*     */           continue; 
/* 257 */         if (line.charAt(line.length() - 1) == '\\') {
/* 258 */           if (continued != null) {
/* 259 */             continued = continued + line.substring(0, line.length() - 1); continue;
/*     */           } 
/* 261 */           continued = line.substring(0, line.length() - 1); continue;
/* 262 */         }  if (continued != null) {
/*     */           
/* 264 */           continued = continued + line;
/*     */           
/*     */           try {
/* 267 */             parseLine(continued);
/* 268 */           } catch (MailcapParseException mailcapParseException) {}
/*     */ 
/*     */           
/* 271 */           continued = null;
/*     */           
/*     */           continue;
/*     */         } 
/*     */         try {
/* 276 */           parseLine(line);
/*     */         }
/* 278 */         catch (MailcapParseException mailcapParseException) {}
/*     */ 
/*     */       
/*     */       }
/* 282 */       catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {}
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
/*     */   protected void parseLine(String mailcapEntry) throws MailcapParseException, IOException {
/* 294 */     MailcapTokenizer tokenizer = new MailcapTokenizer(mailcapEntry);
/* 295 */     tokenizer.setIsAutoquoting(false);
/*     */     
/* 297 */     if (LogSupport.isLoggable()) {
/* 298 */       LogSupport.log("parse: " + mailcapEntry);
/*     */     }
/* 300 */     int currentToken = tokenizer.nextToken();
/* 301 */     if (currentToken != 2) {
/* 302 */       reportParseError(2, currentToken, tokenizer
/* 303 */           .getCurrentTokenValue());
/*     */     }
/*     */     
/* 306 */     String primaryType = tokenizer.getCurrentTokenValue().toLowerCase(Locale.ENGLISH);
/* 307 */     String subType = "*";
/*     */ 
/*     */ 
/*     */     
/* 311 */     currentToken = tokenizer.nextToken();
/* 312 */     if (currentToken != 47 && currentToken != 59)
/*     */     {
/* 314 */       reportParseError(47, 59, currentToken, tokenizer
/*     */           
/* 316 */           .getCurrentTokenValue());
/*     */     }
/*     */ 
/*     */     
/* 320 */     if (currentToken == 47) {
/*     */       
/* 322 */       currentToken = tokenizer.nextToken();
/* 323 */       if (currentToken != 2) {
/* 324 */         reportParseError(2, currentToken, tokenizer
/* 325 */             .getCurrentTokenValue());
/*     */       }
/*     */       
/* 328 */       subType = tokenizer.getCurrentTokenValue().toLowerCase(Locale.ENGLISH);
/*     */ 
/*     */       
/* 331 */       currentToken = tokenizer.nextToken();
/*     */     } 
/*     */     
/* 334 */     String mimeType = primaryType + "/" + subType;
/*     */     
/* 336 */     if (LogSupport.isLoggable()) {
/* 337 */       LogSupport.log("  Type: " + mimeType);
/*     */     }
/*     */     
/* 340 */     Map<Object, Object> commands = new LinkedHashMap<>();
/*     */ 
/*     */     
/* 343 */     if (currentToken != 59) {
/* 344 */       reportParseError(59, currentToken, tokenizer
/* 345 */           .getCurrentTokenValue());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 350 */     tokenizer.setIsAutoquoting(true);
/* 351 */     currentToken = tokenizer.nextToken();
/* 352 */     tokenizer.setIsAutoquoting(false);
/* 353 */     if (currentToken != 2 && currentToken != 59)
/*     */     {
/* 355 */       reportParseError(2, 59, currentToken, tokenizer
/*     */           
/* 357 */           .getCurrentTokenValue());
/*     */     }
/*     */     
/* 360 */     if (currentToken == 2) {
/*     */ 
/*     */       
/* 363 */       List<String> v = (List)this.native_commands.get(mimeType);
/* 364 */       if (v == null) {
/* 365 */         v = new ArrayList();
/* 366 */         v.add(mailcapEntry);
/* 367 */         this.native_commands.put(mimeType, v);
/*     */       } else {
/*     */         
/* 370 */         v.add(mailcapEntry);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 375 */     if (currentToken != 59) {
/* 376 */       currentToken = tokenizer.nextToken();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 381 */     if (currentToken == 59) {
/* 382 */       boolean isFallback = false;
/*     */ 
/*     */ 
/*     */       
/*     */       do {
/* 387 */         currentToken = tokenizer.nextToken();
/* 388 */         if (currentToken != 2) {
/* 389 */           reportParseError(2, currentToken, tokenizer
/* 390 */               .getCurrentTokenValue());
/*     */         }
/*     */         
/* 393 */         String paramName = tokenizer.getCurrentTokenValue().toLowerCase(Locale.ENGLISH);
/*     */ 
/*     */         
/* 396 */         currentToken = tokenizer.nextToken();
/* 397 */         if (currentToken != 61 && currentToken != 59 && currentToken != 5)
/*     */         {
/*     */           
/* 400 */           reportParseError(61, 59, 5, currentToken, tokenizer
/*     */ 
/*     */               
/* 403 */               .getCurrentTokenValue());
/*     */         }
/*     */ 
/*     */         
/* 407 */         if (currentToken != 61) {
/*     */           continue;
/*     */         }
/*     */         
/* 411 */         tokenizer.setIsAutoquoting(true);
/* 412 */         currentToken = tokenizer.nextToken();
/* 413 */         tokenizer.setIsAutoquoting(false);
/* 414 */         if (currentToken != 2) {
/* 415 */           reportParseError(2, currentToken, tokenizer
/* 416 */               .getCurrentTokenValue());
/*     */         }
/*     */         
/* 419 */         String paramValue = tokenizer.getCurrentTokenValue();
/*     */ 
/*     */         
/* 422 */         if (paramName.startsWith("x-java-")) {
/* 423 */           String commandName = paramName.substring(7);
/*     */ 
/*     */           
/* 426 */           if (commandName.equals("fallback-entry") && paramValue
/* 427 */             .equalsIgnoreCase("true")) {
/* 428 */             isFallback = true;
/*     */           }
/*     */           else {
/*     */             
/* 432 */             if (LogSupport.isLoggable()) {
/* 433 */               LogSupport.log("    Command: " + commandName + ", Class: " + paramValue);
/*     */             }
/* 435 */             List<String> classes = (List)commands.get(commandName);
/* 436 */             if (classes == null) {
/* 437 */               classes = new ArrayList();
/* 438 */               commands.put(commandName, classes);
/*     */             } 
/* 440 */             if (addReverse) {
/* 441 */               classes.add(0, paramValue);
/*     */             } else {
/* 443 */               classes.add(paramValue);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 448 */         currentToken = tokenizer.nextToken();
/*     */       }
/* 450 */       while (currentToken == 59);
/*     */       
/* 452 */       Map<String, Map<Object, Object>> masterHash = isFallback ? this.fallback_hash : this.type_hash;
/*     */       
/* 454 */       Map<String, List> curcommands = (Map)masterHash.get(mimeType);
/* 455 */       if (curcommands == null) {
/* 456 */         masterHash.put(mimeType, commands);
/*     */       } else {
/* 458 */         if (LogSupport.isLoggable()) {
/* 459 */           LogSupport.log("Merging commands for type " + mimeType);
/*     */         }
/*     */         
/* 462 */         Iterator<String> cn = curcommands.keySet().iterator();
/* 463 */         while (cn.hasNext()) {
/* 464 */           String cmdName = cn.next();
/* 465 */           List<String> ccv = (List)curcommands.get(cmdName);
/* 466 */           List cv = (List)commands.get(cmdName);
/* 467 */           if (cv == null) {
/*     */             continue;
/*     */           }
/* 470 */           Iterator<String> cvn = cv.iterator();
/* 471 */           while (cvn.hasNext()) {
/* 472 */             String clazz = cvn.next();
/* 473 */             if (!ccv.contains(clazz)) {
/* 474 */               if (addReverse) {
/* 475 */                 ccv.add(0, clazz); continue;
/*     */               } 
/* 477 */               ccv.add(clazz);
/*     */             } 
/*     */           } 
/*     */         } 
/* 481 */         cn = commands.keySet().iterator();
/* 482 */         while (cn.hasNext()) {
/* 483 */           String cmdName = cn.next();
/* 484 */           if (curcommands.containsKey(cmdName))
/*     */             continue; 
/* 486 */           List cv = (List)commands.get(cmdName);
/* 487 */           curcommands.put(cmdName, cv);
/*     */         } 
/*     */       } 
/* 490 */     } else if (currentToken != 5) {
/* 491 */       reportParseError(5, 59, currentToken, tokenizer
/*     */           
/* 493 */           .getCurrentTokenValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected static void reportParseError(int expectedToken, int actualToken, String actualTokenValue) throws MailcapParseException {
/* 499 */     throw new MailcapParseException("Encountered a " + 
/* 500 */         MailcapTokenizer.nameForToken(actualToken) + " token (" + actualTokenValue + ") while expecting a " + 
/*     */         
/* 502 */         MailcapTokenizer.nameForToken(expectedToken) + " token.");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void reportParseError(int expectedToken, int otherExpectedToken, int actualToken, String actualTokenValue) throws MailcapParseException {
/* 508 */     throw new MailcapParseException("Encountered a " + 
/* 509 */         MailcapTokenizer.nameForToken(actualToken) + " token (" + actualTokenValue + ") while expecting a " + 
/*     */         
/* 511 */         MailcapTokenizer.nameForToken(expectedToken) + " or a " + 
/* 512 */         MailcapTokenizer.nameForToken(otherExpectedToken) + " token.");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void reportParseError(int expectedToken, int otherExpectedToken, int anotherExpectedToken, int actualToken, String actualTokenValue) throws MailcapParseException {
/* 518 */     if (LogSupport.isLoggable())
/* 519 */       LogSupport.log("PARSE ERROR: Encountered a " + 
/* 520 */           MailcapTokenizer.nameForToken(actualToken) + " token (" + actualTokenValue + ") while expecting a " + 
/*     */           
/* 522 */           MailcapTokenizer.nameForToken(expectedToken) + ", a " + 
/* 523 */           MailcapTokenizer.nameForToken(otherExpectedToken) + ", or a " + 
/* 524 */           MailcapTokenizer.nameForToken(anotherExpectedToken) + " token."); 
/* 525 */     throw new MailcapParseException("Encountered a " + 
/* 526 */         MailcapTokenizer.nameForToken(actualToken) + " token (" + actualTokenValue + ") while expecting a " + 
/*     */         
/* 528 */         MailcapTokenizer.nameForToken(expectedToken) + ", a " + 
/* 529 */         MailcapTokenizer.nameForToken(otherExpectedToken) + ", or a " + 
/* 530 */         MailcapTokenizer.nameForToken(anotherExpectedToken) + " token.");
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\activation\registries\MailcapFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */