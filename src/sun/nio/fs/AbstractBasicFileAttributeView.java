/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.file.attribute.BasicFileAttributeView;
/*     */ import java.nio.file.attribute.BasicFileAttributes;
/*     */ import java.nio.file.attribute.FileTime;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractBasicFileAttributeView
/*     */   implements BasicFileAttributeView, DynamicFileAttributeView
/*     */ {
/*     */   private static final String SIZE_NAME = "size";
/*     */   private static final String CREATION_TIME_NAME = "creationTime";
/*     */   private static final String LAST_ACCESS_TIME_NAME = "lastAccessTime";
/*     */   private static final String LAST_MODIFIED_TIME_NAME = "lastModifiedTime";
/*     */   private static final String FILE_KEY_NAME = "fileKey";
/*     */   private static final String IS_DIRECTORY_NAME = "isDirectory";
/*     */   private static final String IS_REGULAR_FILE_NAME = "isRegularFile";
/*     */   private static final String IS_SYMBOLIC_LINK_NAME = "isSymbolicLink";
/*     */   private static final String IS_OTHER_NAME = "isOther";
/*  51 */   static final Set<String> basicAttributeNames = Util.newSet(new String[] { "size", "creationTime", "lastAccessTime", "lastModifiedTime", "fileKey", "isDirectory", "isRegularFile", "isSymbolicLink", "isOther" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String name() {
/*  65 */     return "basic";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttribute(String paramString, Object paramObject) throws IOException {
/*  72 */     if (paramString.equals("lastModifiedTime")) {
/*  73 */       setTimes((FileTime)paramObject, null, null);
/*     */       return;
/*     */     } 
/*  76 */     if (paramString.equals("lastAccessTime")) {
/*  77 */       setTimes(null, (FileTime)paramObject, null);
/*     */       return;
/*     */     } 
/*  80 */     if (paramString.equals("creationTime")) {
/*  81 */       setTimes(null, null, (FileTime)paramObject);
/*     */       return;
/*     */     } 
/*  84 */     throw new IllegalArgumentException("'" + name() + ":" + paramString + "' not recognized");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class AttributesBuilder
/*     */   {
/*  92 */     private Set<String> names = new HashSet<>();
/*  93 */     private Map<String, Object> map = new HashMap<>();
/*     */     private boolean copyAll;
/*     */     
/*     */     private AttributesBuilder(Set<String> param1Set, String[] param1ArrayOfString) {
/*  97 */       for (String str : param1ArrayOfString) {
/*  98 */         if (str.equals("*")) {
/*  99 */           this.copyAll = true;
/*     */         } else {
/* 101 */           if (!param1Set.contains(str))
/* 102 */             throw new IllegalArgumentException("'" + str + "' not recognized"); 
/* 103 */           this.names.add(str);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static AttributesBuilder create(Set<String> param1Set, String[] param1ArrayOfString) {
/* 112 */       return new AttributesBuilder(param1Set, param1ArrayOfString);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean match(String param1String) {
/* 119 */       return (this.copyAll || this.names.contains(param1String));
/*     */     }
/*     */     
/*     */     void add(String param1String, Object param1Object) {
/* 123 */       this.map.put(param1String, param1Object);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Map<String, Object> unmodifiableMap() {
/* 131 */       return Collections.unmodifiableMap(this.map);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void addRequestedBasicAttributes(BasicFileAttributes paramBasicFileAttributes, AttributesBuilder paramAttributesBuilder) {
/* 142 */     if (paramAttributesBuilder.match("size"))
/* 143 */       paramAttributesBuilder.add("size", Long.valueOf(paramBasicFileAttributes.size())); 
/* 144 */     if (paramAttributesBuilder.match("creationTime"))
/* 145 */       paramAttributesBuilder.add("creationTime", paramBasicFileAttributes.creationTime()); 
/* 146 */     if (paramAttributesBuilder.match("lastAccessTime"))
/* 147 */       paramAttributesBuilder.add("lastAccessTime", paramBasicFileAttributes.lastAccessTime()); 
/* 148 */     if (paramAttributesBuilder.match("lastModifiedTime"))
/* 149 */       paramAttributesBuilder.add("lastModifiedTime", paramBasicFileAttributes.lastModifiedTime()); 
/* 150 */     if (paramAttributesBuilder.match("fileKey"))
/* 151 */       paramAttributesBuilder.add("fileKey", paramBasicFileAttributes.fileKey()); 
/* 152 */     if (paramAttributesBuilder.match("isDirectory"))
/* 153 */       paramAttributesBuilder.add("isDirectory", Boolean.valueOf(paramBasicFileAttributes.isDirectory())); 
/* 154 */     if (paramAttributesBuilder.match("isRegularFile"))
/* 155 */       paramAttributesBuilder.add("isRegularFile", Boolean.valueOf(paramBasicFileAttributes.isRegularFile())); 
/* 156 */     if (paramAttributesBuilder.match("isSymbolicLink"))
/* 157 */       paramAttributesBuilder.add("isSymbolicLink", Boolean.valueOf(paramBasicFileAttributes.isSymbolicLink())); 
/* 158 */     if (paramAttributesBuilder.match("isOther")) {
/* 159 */       paramAttributesBuilder.add("isOther", Boolean.valueOf(paramBasicFileAttributes.isOther()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Object> readAttributes(String[] paramArrayOfString) throws IOException {
/* 167 */     AttributesBuilder attributesBuilder = AttributesBuilder.create(basicAttributeNames, paramArrayOfString);
/* 168 */     addRequestedBasicAttributes(readAttributes(), attributesBuilder);
/* 169 */     return attributesBuilder.unmodifiableMap();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\fs\AbstractBasicFileAttributeView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */