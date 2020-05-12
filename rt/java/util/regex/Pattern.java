/*      */ package java.util.regex;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Serializable;
/*      */ import java.text.Normalizer;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Spliterators;
/*      */ import java.util.function.Predicate;
/*      */ import java.util.stream.Stream;
/*      */ import java.util.stream.StreamSupport;
/*      */ import sun.text.Normalizer;
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
/*      */ public final class Pattern
/*      */   implements Serializable
/*      */ {
/*      */   public static final int UNIX_LINES = 1;
/*      */   public static final int CASE_INSENSITIVE = 2;
/*      */   public static final int COMMENTS = 4;
/*      */   public static final int MULTILINE = 8;
/*      */   public static final int LITERAL = 16;
/*      */   public static final int DOTALL = 32;
/*      */   public static final int UNICODE_CASE = 64;
/*      */   public static final int CANON_EQ = 128;
/*      */   public static final int UNICODE_CHARACTER_CLASS = 256;
/*      */   private static final long serialVersionUID = 5073258162644648461L;
/*      */   private String pattern;
/*      */   private int flags;
/*      */   private volatile transient boolean compiled = false;
/*      */   private transient String normalizedPattern;
/*      */   transient Node root;
/*      */   transient Node matchRoot;
/*      */   transient int[] buffer;
/*      */   volatile transient Map<String, Integer> namedGroups;
/*      */   transient GroupHead[] groupNodes;
/*      */   private transient int[] temp;
/*      */   transient int capturingGroupCount;
/*      */   transient int localCount;
/*      */   private transient int cursor;
/*      */   private transient int patternLength;
/*      */   private transient boolean hasSupplementary;
/*      */   static final int MAX_REPS = 2147483647;
/*      */   static final int GREEDY = 0;
/*      */   static final int LAZY = 1;
/*      */   static final int POSSESSIVE = 2;
/*      */   static final int INDEPENDENT = 3;
/*      */   
/*      */   public static Pattern compile(String paramString) {
/* 1028 */     return new Pattern(paramString, 0);
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
/*      */   public static Pattern compile(String paramString, int paramInt) {
/* 1054 */     return new Pattern(paramString, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String pattern() {
/* 1063 */     return this.pattern;
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
/*      */   public String toString() {
/* 1075 */     return this.pattern;
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
/*      */   public Matcher matcher(CharSequence paramCharSequence) {
/* 1087 */     if (!this.compiled)
/* 1088 */       synchronized (this) {
/* 1089 */         if (!this.compiled) {
/* 1090 */           compile();
/*      */         }
/*      */       }  
/* 1093 */     return new Matcher(this, paramCharSequence);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int flags() {
/* 1103 */     return this.flags;
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
/*      */   public static boolean matches(String paramString, CharSequence paramCharSequence) {
/* 1133 */     Pattern pattern = compile(paramString);
/* 1134 */     Matcher matcher = pattern.matcher(paramCharSequence);
/* 1135 */     return matcher.matches();
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
/*      */   public String[] split(CharSequence paramCharSequence, int paramInt) {
/* 1203 */     int i = 0;
/* 1204 */     boolean bool = (paramInt > 0) ? true : false;
/* 1205 */     ArrayList<String> arrayList = new ArrayList();
/* 1206 */     Matcher matcher = matcher(paramCharSequence);
/*      */ 
/*      */     
/* 1209 */     while (matcher.find()) {
/* 1210 */       if (!bool || arrayList.size() < paramInt - 1) {
/* 1211 */         if (!i && i == matcher.start() && matcher.start() == matcher.end()) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */         
/* 1216 */         String str = paramCharSequence.subSequence(i, matcher.start()).toString();
/* 1217 */         arrayList.add(str);
/* 1218 */         i = matcher.end(); continue;
/* 1219 */       }  if (arrayList.size() == paramInt - 1) {
/*      */         
/* 1221 */         String str = paramCharSequence.subSequence(i, paramCharSequence.length()).toString();
/* 1222 */         arrayList.add(str);
/* 1223 */         i = matcher.end();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1228 */     if (i == 0) {
/* 1229 */       return new String[] { paramCharSequence.toString() };
/*      */     }
/*      */     
/* 1232 */     if (!bool || arrayList.size() < paramInt) {
/* 1233 */       arrayList.add(paramCharSequence.subSequence(i, paramCharSequence.length()).toString());
/*      */     }
/*      */     
/* 1236 */     int j = arrayList.size();
/* 1237 */     if (paramInt == 0)
/* 1238 */       while (j > 0 && ((String)arrayList.get(j - 1)).equals(""))
/* 1239 */         j--;  
/* 1240 */     String[] arrayOfString = new String[j];
/* 1241 */     return (String[])arrayList.subList(0, j).toArray((Object[])arrayOfString);
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
/*      */   public String[] split(CharSequence paramCharSequence) {
/* 1273 */     return split(paramCharSequence, 0);
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
/*      */   public static String quote(String paramString) {
/* 1291 */     int i = paramString.indexOf("\\E");
/* 1292 */     if (i == -1) {
/* 1293 */       return "\\Q" + paramString + "\\E";
/*      */     }
/* 1295 */     StringBuilder stringBuilder = new StringBuilder(paramString.length() * 2);
/* 1296 */     stringBuilder.append("\\Q");
/* 1297 */     i = 0;
/* 1298 */     int j = 0;
/* 1299 */     while ((i = paramString.indexOf("\\E", j)) != -1) {
/* 1300 */       stringBuilder.append(paramString.substring(j, i));
/* 1301 */       j = i + 2;
/* 1302 */       stringBuilder.append("\\E\\\\E\\Q");
/*      */     } 
/* 1304 */     stringBuilder.append(paramString.substring(j, paramString.length()));
/* 1305 */     stringBuilder.append("\\E");
/* 1306 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1317 */     paramObjectInputStream.defaultReadObject();
/*      */ 
/*      */     
/* 1320 */     this.capturingGroupCount = 1;
/* 1321 */     this.localCount = 0;
/*      */ 
/*      */     
/* 1324 */     this.compiled = false;
/* 1325 */     if (this.pattern.length() == 0) {
/* 1326 */       this.root = new Start(lastAccept);
/* 1327 */       this.matchRoot = lastAccept;
/* 1328 */       this.compiled = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Pattern(String paramString, int paramInt) {
/* 1339 */     this.pattern = paramString;
/* 1340 */     this.flags = paramInt;
/*      */ 
/*      */     
/* 1343 */     if ((this.flags & 0x100) != 0) {
/* 1344 */       this.flags |= 0x40;
/*      */     }
/*      */     
/* 1347 */     this.capturingGroupCount = 1;
/* 1348 */     this.localCount = 0;
/*      */     
/* 1350 */     if (this.pattern.length() > 0) {
/* 1351 */       compile();
/*      */     } else {
/* 1353 */       this.root = new Start(lastAccept);
/* 1354 */       this.matchRoot = lastAccept;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void normalize() {
/* 1363 */     boolean bool = false;
/* 1364 */     int i = -1;
/*      */ 
/*      */     
/* 1367 */     this.normalizedPattern = Normalizer.normalize(this.pattern, Normalizer.Form.NFD);
/* 1368 */     this.patternLength = this.normalizedPattern.length();
/*      */ 
/*      */     
/* 1371 */     StringBuilder stringBuilder = new StringBuilder(this.patternLength); int j;
/* 1372 */     for (j = 0; j < this.patternLength; ) {
/* 1373 */       int k = this.normalizedPattern.codePointAt(j);
/*      */       
/* 1375 */       if (Character.getType(k) == 6 && i != -1) {
/*      */         
/* 1377 */         StringBuilder stringBuilder1 = new StringBuilder();
/* 1378 */         stringBuilder1.appendCodePoint(i);
/* 1379 */         stringBuilder1.appendCodePoint(k);
/* 1380 */         while (Character.getType(k) == 6) {
/* 1381 */           j += Character.charCount(k);
/* 1382 */           if (j >= this.patternLength)
/*      */             break; 
/* 1384 */           k = this.normalizedPattern.codePointAt(j);
/* 1385 */           stringBuilder1.appendCodePoint(k);
/*      */         } 
/* 1387 */         String str = produceEquivalentAlternation(stringBuilder1
/* 1388 */             .toString());
/* 1389 */         stringBuilder.setLength(stringBuilder.length() - Character.charCount(i));
/* 1390 */         stringBuilder.append("(?:").append(str).append(")");
/* 1391 */       } else if (k == 91 && i != 92) {
/* 1392 */         j = normalizeCharClass(stringBuilder, j);
/*      */       } else {
/* 1394 */         stringBuilder.appendCodePoint(k);
/*      */       } 
/* 1396 */       i = k;
/* 1397 */       j += Character.charCount(k);
/*      */     } 
/* 1399 */     this.normalizedPattern = stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int normalizeCharClass(StringBuilder paramStringBuilder, int paramInt) {
/*      */     String str;
/* 1408 */     StringBuilder stringBuilder1 = new StringBuilder();
/* 1409 */     StringBuilder stringBuilder2 = null;
/* 1410 */     int i = -1;
/*      */ 
/*      */     
/* 1413 */     paramInt++;
/* 1414 */     if (paramInt == this.normalizedPattern.length())
/* 1415 */       throw error("Unclosed character class"); 
/* 1416 */     stringBuilder1.append("[");
/*      */     while (true) {
/* 1418 */       int j = this.normalizedPattern.codePointAt(paramInt);
/*      */ 
/*      */       
/* 1421 */       if (j == 93 && i != 92) {
/* 1422 */         stringBuilder1.append((char)j); break;
/*      */       } 
/* 1424 */       if (Character.getType(j) == 6) {
/* 1425 */         StringBuilder stringBuilder = new StringBuilder();
/* 1426 */         stringBuilder.appendCodePoint(i);
/* 1427 */         while (Character.getType(j) == 6) {
/* 1428 */           stringBuilder.appendCodePoint(j);
/* 1429 */           paramInt += Character.charCount(j);
/* 1430 */           if (paramInt >= this.normalizedPattern.length())
/*      */             break; 
/* 1432 */           j = this.normalizedPattern.codePointAt(paramInt);
/*      */         } 
/* 1434 */         String str1 = produceEquivalentAlternation(stringBuilder
/* 1435 */             .toString());
/*      */         
/* 1437 */         stringBuilder1.setLength(stringBuilder1.length() - Character.charCount(i));
/* 1438 */         if (stringBuilder2 == null)
/* 1439 */           stringBuilder2 = new StringBuilder(); 
/* 1440 */         stringBuilder2.append('|');
/* 1441 */         stringBuilder2.append(str1);
/*      */       } else {
/* 1443 */         stringBuilder1.appendCodePoint(j);
/* 1444 */         paramInt++;
/*      */       } 
/* 1446 */       if (paramInt == this.normalizedPattern.length())
/* 1447 */         throw error("Unclosed character class"); 
/* 1448 */       i = j;
/*      */     } 
/*      */     
/* 1451 */     if (stringBuilder2 != null) {
/* 1452 */       str = "(?:" + stringBuilder1.toString() + stringBuilder2.toString() + ")";
/*      */     } else {
/* 1454 */       str = stringBuilder1.toString();
/*      */     } 
/*      */     
/* 1457 */     paramStringBuilder.append(str);
/* 1458 */     return paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String produceEquivalentAlternation(String paramString) {
/* 1467 */     int i = countChars(paramString, 0, 1);
/* 1468 */     if (paramString.length() == i)
/*      */     {
/* 1470 */       return paramString;
/*      */     }
/* 1472 */     String str1 = paramString.substring(0, i);
/* 1473 */     String str2 = paramString.substring(i);
/*      */     
/* 1475 */     String[] arrayOfString = producePermutations(str2);
/* 1476 */     StringBuilder stringBuilder = new StringBuilder(paramString);
/*      */ 
/*      */     
/* 1479 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 1480 */       String str = str1 + arrayOfString[b];
/* 1481 */       if (b > 0)
/* 1482 */         stringBuilder.append("|" + str); 
/* 1483 */       str = composeOneStep(str);
/* 1484 */       if (str != null)
/* 1485 */         stringBuilder.append("|" + produceEquivalentAlternation(str)); 
/*      */     } 
/* 1487 */     return stringBuilder.toString();
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
/*      */   private String[] producePermutations(String paramString) {
/* 1500 */     if (paramString.length() == countChars(paramString, 0, 1)) {
/* 1501 */       return new String[] { paramString };
/*      */     }
/* 1503 */     if (paramString.length() == countChars(paramString, 0, 2)) {
/* 1504 */       int n = Character.codePointAt(paramString, 0);
/* 1505 */       int i1 = Character.codePointAt(paramString, Character.charCount(n));
/* 1506 */       if (getClass(i1) == getClass(n)) {
/* 1507 */         return new String[] { paramString };
/*      */       }
/* 1509 */       String[] arrayOfString = new String[2];
/* 1510 */       arrayOfString[0] = paramString;
/* 1511 */       StringBuilder stringBuilder = new StringBuilder(2);
/* 1512 */       stringBuilder.appendCodePoint(i1);
/* 1513 */       stringBuilder.appendCodePoint(n);
/* 1514 */       arrayOfString[1] = stringBuilder.toString();
/* 1515 */       return arrayOfString;
/*      */     } 
/*      */     
/* 1518 */     int i = 1;
/* 1519 */     int j = countCodePoints(paramString);
/* 1520 */     for (byte b1 = 1; b1 < j; b1++) {
/* 1521 */       i *= b1 + 1;
/*      */     }
/* 1523 */     String[] arrayOfString1 = new String[i];
/*      */     
/* 1525 */     int[] arrayOfInt = new int[j]; byte b2; int k;
/* 1526 */     for (b2 = 0, k = 0; b2 < j; b2++) {
/* 1527 */       int n = Character.codePointAt(paramString, k);
/* 1528 */       arrayOfInt[b2] = getClass(n);
/* 1529 */       k += Character.charCount(n);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1534 */     b2 = 0;
/*      */     
/*      */     int m;
/* 1537 */     for (byte b3 = 0; b3 < j; b3++, m += k) {
/* 1538 */       k = countChars(paramString, m, 1);
/* 1539 */       boolean bool = false;
/* 1540 */       for (int n = b3 - 1; n >= 0; n--) {
/* 1541 */         if (arrayOfInt[n] == arrayOfInt[b3]) {
/*      */           break label43;
/*      */         }
/*      */       } 
/* 1545 */       StringBuilder stringBuilder = new StringBuilder(paramString);
/* 1546 */       String str1 = stringBuilder.delete(m, m + k).toString();
/* 1547 */       String[] arrayOfString = producePermutations(str1);
/*      */       
/* 1549 */       String str2 = paramString.substring(m, m + k); byte b;
/* 1550 */       label43: for (b = 0; b < arrayOfString.length; b++)
/* 1551 */         arrayOfString1[b2++] = str2 + arrayOfString[b]; 
/*      */     } 
/* 1553 */     String[] arrayOfString2 = new String[b2];
/* 1554 */     for (m = 0; m < b2; m++)
/* 1555 */       arrayOfString2[m] = arrayOfString1[m]; 
/* 1556 */     return arrayOfString2;
/*      */   }
/*      */   
/*      */   private int getClass(int paramInt) {
/* 1560 */     return Normalizer.getCombiningClass(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String composeOneStep(String paramString) {
/* 1571 */     int i = countChars(paramString, 0, 2);
/* 1572 */     String str1 = paramString.substring(0, i);
/* 1573 */     String str2 = Normalizer.normalize(str1, Normalizer.Form.NFC);
/*      */     
/* 1575 */     if (str2.equals(str1)) {
/* 1576 */       return null;
/*      */     }
/* 1578 */     String str3 = paramString.substring(i);
/* 1579 */     return str2 + str3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void RemoveQEQuoting() {
/* 1588 */     int i = this.patternLength;
/* 1589 */     byte b1 = 0;
/* 1590 */     while (b1 < i - 1) {
/* 1591 */       if (this.temp[b1] != 92) {
/* 1592 */         b1++; continue;
/* 1593 */       }  if (this.temp[b1 + 1] != 81) {
/* 1594 */         b1 += 2;
/*      */       }
/*      */     } 
/*      */     
/* 1598 */     if (b1 >= i - 1)
/*      */       return; 
/* 1600 */     byte b2 = b1;
/* 1601 */     b1 += 2;
/* 1602 */     int[] arrayOfInt = new int[b2 + 3 * (i - b1) + 2];
/* 1603 */     System.arraycopy(this.temp, 0, arrayOfInt, 0, b2);
/*      */     
/* 1605 */     boolean bool1 = true;
/* 1606 */     boolean bool2 = true;
/* 1607 */     while (b1 < i) {
/* 1608 */       int j = this.temp[b1++];
/* 1609 */       if (!ASCII.isAscii(j) || ASCII.isAlpha(j)) {
/* 1610 */         arrayOfInt[b2++] = j;
/* 1611 */       } else if (ASCII.isDigit(j)) {
/* 1612 */         if (bool2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1618 */           arrayOfInt[b2++] = 92;
/* 1619 */           arrayOfInt[b2++] = 120;
/* 1620 */           arrayOfInt[b2++] = 51;
/*      */         } 
/* 1622 */         arrayOfInt[b2++] = j;
/* 1623 */       } else if (j != 92) {
/* 1624 */         if (bool1) arrayOfInt[b2++] = 92; 
/* 1625 */         arrayOfInt[b2++] = j;
/* 1626 */       } else if (bool1) {
/* 1627 */         if (this.temp[b1] == 69) {
/* 1628 */           b1++;
/* 1629 */           bool1 = false;
/*      */         } else {
/* 1631 */           arrayOfInt[b2++] = 92;
/* 1632 */           arrayOfInt[b2++] = 92;
/*      */         } 
/*      */       } else {
/* 1635 */         if (this.temp[b1] == 81) {
/* 1636 */           b1++;
/* 1637 */           bool1 = true;
/* 1638 */           bool2 = true;
/*      */           continue;
/*      */         } 
/* 1641 */         arrayOfInt[b2++] = j;
/* 1642 */         if (b1 != i) {
/* 1643 */           arrayOfInt[b2++] = this.temp[b1++];
/*      */         }
/*      */       } 
/*      */       
/* 1647 */       bool2 = false;
/*      */     } 
/*      */     
/* 1650 */     this.patternLength = b2;
/* 1651 */     this.temp = Arrays.copyOf(arrayOfInt, b2 + 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void compile() {
/* 1660 */     if (has(128) && !has(16)) {
/* 1661 */       normalize();
/*      */     } else {
/* 1663 */       this.normalizedPattern = this.pattern;
/*      */     } 
/* 1665 */     this.patternLength = this.normalizedPattern.length();
/*      */ 
/*      */ 
/*      */     
/* 1669 */     this.temp = new int[this.patternLength + 2];
/*      */     
/* 1671 */     this.hasSupplementary = false;
/* 1672 */     byte b = 0;
/*      */     
/* 1674 */     for (int i = 0; i < this.patternLength; i += Character.charCount(j)) {
/* 1675 */       int j = this.normalizedPattern.codePointAt(i);
/* 1676 */       if (isSupplementary(j)) {
/* 1677 */         this.hasSupplementary = true;
/*      */       }
/* 1679 */       this.temp[b++] = j;
/*      */     } 
/*      */     
/* 1682 */     this.patternLength = b;
/*      */     
/* 1684 */     if (!has(16)) {
/* 1685 */       RemoveQEQuoting();
/*      */     }
/*      */     
/* 1688 */     this.buffer = new int[32];
/* 1689 */     this.groupNodes = new GroupHead[10];
/* 1690 */     this.namedGroups = null;
/*      */     
/* 1692 */     if (has(16)) {
/*      */       
/* 1694 */       this.matchRoot = newSlice(this.temp, this.patternLength, this.hasSupplementary);
/* 1695 */       this.matchRoot.next = lastAccept;
/*      */     } else {
/*      */       
/* 1698 */       this.matchRoot = expr(lastAccept);
/*      */       
/* 1700 */       if (this.patternLength != this.cursor) {
/* 1701 */         if (peek() == 41) {
/* 1702 */           throw error("Unmatched closing ')'");
/*      */         }
/* 1704 */         throw error("Unexpected internal error");
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1710 */     if (this.matchRoot instanceof Slice) {
/* 1711 */       this.root = BnM.optimize(this.matchRoot);
/* 1712 */       if (this.root == this.matchRoot) {
/* 1713 */         this.root = this.hasSupplementary ? new StartS(this.matchRoot) : new Start(this.matchRoot);
/*      */       }
/* 1715 */     } else if (this.matchRoot instanceof Begin || this.matchRoot instanceof First) {
/* 1716 */       this.root = this.matchRoot;
/*      */     } else {
/* 1718 */       this.root = this.hasSupplementary ? new StartS(this.matchRoot) : new Start(this.matchRoot);
/*      */     } 
/*      */ 
/*      */     
/* 1722 */     this.temp = null;
/* 1723 */     this.buffer = null;
/* 1724 */     this.groupNodes = null;
/* 1725 */     this.patternLength = 0;
/* 1726 */     this.compiled = true;
/*      */   }
/*      */   
/*      */   Map<String, Integer> namedGroups() {
/* 1730 */     if (this.namedGroups == null)
/* 1731 */       this.namedGroups = new HashMap<>(2); 
/* 1732 */     return this.namedGroups;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void printObjectTree(Node paramNode) {
/* 1739 */     while (paramNode != null) {
/* 1740 */       if (paramNode instanceof Prolog)
/* 1741 */       { System.out.println(paramNode);
/* 1742 */         printObjectTree(((Prolog)paramNode).loop);
/* 1743 */         System.out.println("**** end contents prolog loop"); }
/* 1744 */       else if (paramNode instanceof Loop)
/* 1745 */       { System.out.println(paramNode);
/* 1746 */         printObjectTree(((Loop)paramNode).body);
/* 1747 */         System.out.println("**** end contents Loop body"); }
/* 1748 */       else if (paramNode instanceof Curly)
/* 1749 */       { System.out.println(paramNode);
/* 1750 */         printObjectTree(((Curly)paramNode).atom);
/* 1751 */         System.out.println("**** end contents Curly body"); }
/* 1752 */       else if (paramNode instanceof GroupCurly)
/* 1753 */       { System.out.println(paramNode);
/* 1754 */         printObjectTree(((GroupCurly)paramNode).atom);
/* 1755 */         System.out.println("**** end contents GroupCurly body"); }
/* 1756 */       else { if (paramNode instanceof GroupTail) {
/* 1757 */           System.out.println(paramNode);
/* 1758 */           System.out.println("Tail next is " + paramNode.next);
/*      */           return;
/*      */         } 
/* 1761 */         System.out.println(paramNode); }
/*      */       
/* 1763 */       paramNode = paramNode.next;
/* 1764 */       if (paramNode != null)
/* 1765 */         System.out.println("->next:"); 
/* 1766 */       if (paramNode == accept) {
/* 1767 */         System.out.println("Accept Node");
/* 1768 */         paramNode = null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   static final class TreeInfo
/*      */   {
/*      */     int minLength;
/*      */     
/*      */     int maxLength;
/*      */     
/*      */     boolean maxValid;
/*      */     boolean deterministic;
/*      */     
/*      */     TreeInfo() {
/* 1784 */       reset();
/*      */     }
/*      */     void reset() {
/* 1787 */       this.minLength = 0;
/* 1788 */       this.maxLength = 0;
/* 1789 */       this.maxValid = true;
/* 1790 */       this.deterministic = true;
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
/*      */   private boolean has(int paramInt) {
/* 1804 */     return ((this.flags & paramInt) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void accept(int paramInt, String paramString) {
/* 1811 */     int i = this.temp[this.cursor++];
/* 1812 */     if (has(4))
/* 1813 */       i = parsePastWhitespace(i); 
/* 1814 */     if (paramInt != i) {
/* 1815 */       throw error(paramString);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void mark(int paramInt) {
/* 1823 */     this.temp[this.patternLength] = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int peek() {
/* 1830 */     int i = this.temp[this.cursor];
/* 1831 */     if (has(4))
/* 1832 */       i = peekPastWhitespace(i); 
/* 1833 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int read() {
/* 1840 */     int i = this.temp[this.cursor++];
/* 1841 */     if (has(4))
/* 1842 */       i = parsePastWhitespace(i); 
/* 1843 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int readEscaped() {
/* 1851 */     return this.temp[this.cursor++];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int next() {
/* 1859 */     int i = this.temp[++this.cursor];
/* 1860 */     if (has(4))
/* 1861 */       i = peekPastWhitespace(i); 
/* 1862 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int nextEscaped() {
/* 1870 */     return this.temp[++this.cursor];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int peekPastWhitespace(int paramInt) {
/* 1878 */     while (ASCII.isSpace(paramInt) || paramInt == 35) {
/* 1879 */       while (ASCII.isSpace(paramInt))
/* 1880 */         paramInt = this.temp[++this.cursor]; 
/* 1881 */       if (paramInt == 35) {
/* 1882 */         paramInt = peekPastLine();
/*      */       }
/*      */     } 
/* 1885 */     return paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int parsePastWhitespace(int paramInt) {
/* 1892 */     while (ASCII.isSpace(paramInt) || paramInt == 35) {
/* 1893 */       while (ASCII.isSpace(paramInt))
/* 1894 */         paramInt = this.temp[this.cursor++]; 
/* 1895 */       if (paramInt == 35)
/* 1896 */         paramInt = parsePastLine(); 
/*      */     } 
/* 1898 */     return paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int parsePastLine() {
/* 1905 */     int i = this.temp[this.cursor++];
/* 1906 */     while (i != 0 && !isLineSeparator(i))
/* 1907 */       i = this.temp[this.cursor++]; 
/* 1908 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int peekPastLine() {
/* 1915 */     int i = this.temp[++this.cursor];
/* 1916 */     while (i != 0 && !isLineSeparator(i))
/* 1917 */       i = this.temp[++this.cursor]; 
/* 1918 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isLineSeparator(int paramInt) {
/* 1925 */     if (has(1)) {
/* 1926 */       return (paramInt == 10);
/*      */     }
/* 1928 */     return (paramInt == 10 || paramInt == 13 || (paramInt | 0x1) == 8233 || paramInt == 133);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int skip() {
/* 1939 */     int i = this.cursor;
/* 1940 */     int j = this.temp[i + 1];
/* 1941 */     this.cursor = i + 2;
/* 1942 */     return j;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void unread() {
/* 1949 */     this.cursor--;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PatternSyntaxException error(String paramString) {
/* 1957 */     return new PatternSyntaxException(paramString, this.normalizedPattern, this.cursor - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean findSupplementary(int paramInt1, int paramInt2) {
/* 1965 */     for (int i = paramInt1; i < paramInt2; i++) {
/* 1966 */       if (isSupplementary(this.temp[i]))
/* 1967 */         return true; 
/*      */     } 
/* 1969 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean isSupplementary(int paramInt) {
/* 1977 */     return (paramInt >= 65536 || 
/* 1978 */       Character.isSurrogate((char)paramInt));
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
/*      */   private Node expr(Node paramNode) {
/* 1992 */     Node node1 = null;
/* 1993 */     Node node2 = null;
/* 1994 */     Node node3 = null;
/* 1995 */     BranchConn branchConn = null;
/*      */     
/*      */     while (true) {
/* 1998 */       Node node4 = sequence(paramNode);
/* 1999 */       Node node5 = this.root;
/* 2000 */       if (node1 == null) {
/* 2001 */         node1 = node4;
/* 2002 */         node2 = node5;
/*      */       } else {
/*      */         
/* 2005 */         if (branchConn == null) {
/* 2006 */           branchConn = new BranchConn();
/* 2007 */           branchConn.next = paramNode;
/*      */         } 
/* 2009 */         if (node4 == paramNode) {
/*      */ 
/*      */ 
/*      */           
/* 2013 */           node4 = null;
/*      */         } else {
/*      */           
/* 2016 */           node5.next = branchConn;
/*      */         } 
/* 2018 */         if (node1 == node3) {
/* 2019 */           node3.add(node4);
/*      */         } else {
/* 2021 */           if (node1 == paramNode) {
/* 2022 */             node1 = null;
/*      */           }
/*      */           else {
/*      */             
/* 2026 */             node2.next = branchConn;
/*      */           } 
/* 2028 */           node1 = node3 = new Branch(node1, node4, branchConn);
/*      */         } 
/*      */       } 
/* 2031 */       if (peek() != 124) {
/* 2032 */         return node1;
/*      */       }
/* 2034 */       next();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Node sequence(Node paramNode) {
/* 2043 */     Node node1 = null;
/* 2044 */     Node node2 = null;
/* 2045 */     Node node3 = null;
/*      */     
/*      */     while (true) {
/* 2048 */       int i = peek();
/* 2049 */       switch (i) {
/*      */ 
/*      */         
/*      */         case 40:
/* 2053 */           node3 = group0();
/*      */           
/* 2055 */           if (node3 == null)
/*      */             continue; 
/* 2057 */           if (node1 == null) {
/* 2058 */             node1 = node3;
/*      */           } else {
/* 2060 */             node2.next = node3;
/*      */           } 
/* 2062 */           node2 = this.root;
/*      */           continue;
/*      */         case 91:
/* 2065 */           node3 = clazz(true);
/*      */           break;
/*      */         case 92:
/* 2068 */           i = nextEscaped();
/* 2069 */           if (i == 112 || i == 80) {
/* 2070 */             boolean bool1 = true;
/* 2071 */             boolean bool2 = (i == 80) ? true : false;
/* 2072 */             i = next();
/* 2073 */             if (i != 123) {
/* 2074 */               unread();
/*      */             } else {
/* 2076 */               bool1 = false;
/*      */             } 
/* 2078 */             node3 = family(bool1, bool2); break;
/*      */           } 
/* 2080 */           unread();
/* 2081 */           node3 = atom();
/*      */           break;
/*      */         
/*      */         case 94:
/* 2085 */           next();
/* 2086 */           if (has(8)) {
/* 2087 */             if (has(1)) {
/* 2088 */               node3 = new UnixCaret(); break;
/*      */             } 
/* 2090 */             node3 = new Caret(); break;
/*      */           } 
/* 2092 */           node3 = new Begin();
/*      */           break;
/*      */         
/*      */         case 36:
/* 2096 */           next();
/* 2097 */           if (has(1)) {
/* 2098 */             node3 = new UnixDollar(has(8)); break;
/*      */           } 
/* 2100 */           node3 = new Dollar(has(8));
/*      */           break;
/*      */         case 46:
/* 2103 */           next();
/* 2104 */           if (has(32)) {
/* 2105 */             node3 = new All(); break;
/*      */           } 
/* 2107 */           if (has(1)) {
/* 2108 */             node3 = new UnixDot(); break;
/*      */           } 
/* 2110 */           node3 = new Dot();
/*      */           break;
/*      */         
/*      */         case 41:
/*      */         case 124:
/*      */           break;
/*      */         
/*      */         case 93:
/*      */         case 125:
/* 2119 */           node3 = atom();
/*      */           break;
/*      */         case 42:
/*      */         case 43:
/*      */         case 63:
/* 2124 */           next();
/* 2125 */           throw error("Dangling meta character '" + (char)i + "'");
/*      */         case 0:
/* 2127 */           if (this.cursor >= this.patternLength) {
/*      */             break;
/*      */           }
/*      */         
/*      */         default:
/* 2132 */           node3 = atom();
/*      */           break;
/*      */       } 
/*      */       
/* 2136 */       node3 = closure(node3);
/*      */       
/* 2138 */       if (node1 == null) {
/* 2139 */         node1 = node2 = node3; continue;
/*      */       } 
/* 2141 */       node2.next = node3;
/* 2142 */       node2 = node3;
/*      */     } 
/*      */     
/* 2145 */     if (node1 == null) {
/* 2146 */       return paramNode;
/*      */     }
/* 2148 */     node2.next = paramNode;
/* 2149 */     this.root = node2;
/* 2150 */     return node1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Node atom() {
/* 2158 */     byte b = 0;
/* 2159 */     int i = -1;
/* 2160 */     boolean bool = false;
/* 2161 */     int j = peek();
/*      */     while (true) {
/* 2163 */       switch (j) {
/*      */         case 42:
/*      */         case 43:
/*      */         case 63:
/*      */         case 123:
/* 2168 */           if (b > 1) {
/* 2169 */             this.cursor = i;
/* 2170 */             b--;
/*      */           } 
/*      */           break;
/*      */         case 36:
/*      */         case 40:
/*      */         case 41:
/*      */         case 46:
/*      */         case 91:
/*      */         case 94:
/*      */         case 124:
/*      */           break;
/*      */         case 92:
/* 2182 */           j = nextEscaped();
/* 2183 */           if (j == 112 || j == 80) {
/* 2184 */             if (b > 0) {
/* 2185 */               unread();
/*      */               break;
/*      */             } 
/* 2188 */             boolean bool1 = (j == 80) ? true : false;
/* 2189 */             boolean bool2 = true;
/* 2190 */             j = next();
/* 2191 */             if (j != 123) {
/* 2192 */               unread();
/*      */             } else {
/* 2194 */               bool2 = false;
/* 2195 */             }  return family(bool2, bool1);
/*      */           } 
/*      */           
/* 2198 */           unread();
/* 2199 */           i = this.cursor;
/* 2200 */           j = escape(false, (b == 0), false);
/* 2201 */           if (j >= 0) {
/* 2202 */             append(j, b);
/* 2203 */             b++;
/* 2204 */             if (isSupplementary(j)) {
/* 2205 */               bool = true;
/*      */             }
/* 2207 */             j = peek(); continue;
/*      */           } 
/* 2209 */           if (b == 0) {
/* 2210 */             return this.root;
/*      */           }
/*      */           
/* 2213 */           this.cursor = i;
/*      */           break;
/*      */         case 0:
/* 2216 */           if (this.cursor >= this.patternLength) {
/*      */             break;
/*      */           }
/*      */           break;
/*      */       } 
/* 2221 */       i = this.cursor;
/* 2222 */       append(j, b);
/* 2223 */       b++;
/* 2224 */       if (isSupplementary(j)) {
/* 2225 */         bool = true;
/*      */       }
/* 2227 */       j = next();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2232 */     if (b == 1) {
/* 2233 */       return newSingle(this.buffer[0]);
/*      */     }
/* 2235 */     return newSlice(this.buffer, b, bool);
/*      */   }
/*      */ 
/*      */   
/*      */   private void append(int paramInt1, int paramInt2) {
/* 2240 */     if (paramInt2 >= this.buffer.length) {
/* 2241 */       int[] arrayOfInt = new int[paramInt2 + paramInt2];
/* 2242 */       System.arraycopy(this.buffer, 0, arrayOfInt, 0, paramInt2);
/* 2243 */       this.buffer = arrayOfInt;
/*      */     } 
/* 2245 */     this.buffer[paramInt2] = paramInt1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Node ref(int paramInt) {
/* 2255 */     boolean bool = false;
/* 2256 */     while (!bool) {
/* 2257 */       int j, i = peek();
/* 2258 */       switch (i) {
/*      */         case 48:
/*      */         case 49:
/*      */         case 50:
/*      */         case 51:
/*      */         case 52:
/*      */         case 53:
/*      */         case 54:
/*      */         case 55:
/*      */         case 56:
/*      */         case 57:
/* 2269 */           j = paramInt * 10 + i - 48;
/*      */ 
/*      */           
/* 2272 */           if (this.capturingGroupCount - 1 < j) {
/* 2273 */             bool = true;
/*      */             continue;
/*      */           } 
/* 2276 */           paramInt = j;
/* 2277 */           read();
/*      */           continue;
/*      */       } 
/* 2280 */       bool = true;
/*      */     } 
/*      */ 
/*      */     
/* 2284 */     if (has(2)) {
/* 2285 */       return new CIBackRef(paramInt, has(64));
/*      */     }
/* 2287 */     return new BackRef(paramInt);
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
/*      */   private int escape(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
/* 2299 */     int i = skip();
/* 2300 */     switch (i) {
/*      */       case 48:
/* 2302 */         return o();
/*      */       case 49:
/*      */       case 50:
/*      */       case 51:
/*      */       case 52:
/*      */       case 53:
/*      */       case 54:
/*      */       case 55:
/*      */       case 56:
/*      */       case 57:
/* 2312 */         if (!paramBoolean1)
/* 2313 */         { if (paramBoolean2) {
/* 2314 */             this.root = ref(i - 48);
/*      */           }
/* 2316 */           return -1; } 
/*      */       case 65:
/* 2318 */         if (!paramBoolean1)
/* 2319 */         { if (paramBoolean2) this.root = new Begin(); 
/* 2320 */           return -1; } 
/*      */       case 66:
/* 2322 */         if (!paramBoolean1) {
/* 2323 */           if (paramBoolean2) this.root = new Bound(Bound.NONE, has(256)); 
/* 2324 */           return -1;
/*      */         } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 67:
/* 2473 */         throw error("Illegal/unsupported escape sequence");case 68: if (paramBoolean2) this.root = has(256) ? (new Utype(UnicodeProp.DIGIT)).complement() : (new Ctype(1024)).complement();  return -1;case 69: case 70: throw error("Illegal/unsupported escape sequence");case 71: if (!paramBoolean1) { if (paramBoolean2) this.root = new LastMatch();  return -1; } case 72: if (paramBoolean2) this.root = (new HorizWS()).complement();  return -1;case 73: case 74: case 75: case 76: case 77: case 78: case 79: case 80: case 81: throw error("Illegal/unsupported escape sequence");case 82: if (!paramBoolean1) { if (paramBoolean2) this.root = new LineEnding();  return -1; } case 83: if (paramBoolean2) this.root = has(256) ? (new Utype(UnicodeProp.WHITE_SPACE)).complement() : (new Ctype(2048)).complement();  return -1;case 84: case 85: throw error("Illegal/unsupported escape sequence");case 86: if (paramBoolean2) this.root = (new VertWS()).complement();  return -1;case 87: if (paramBoolean2) this.root = has(256) ? (new Utype(UnicodeProp.WORD)).complement() : (new Ctype(67328)).complement();  return -1;case 88: case 89: throw error("Illegal/unsupported escape sequence");case 90: if (!paramBoolean1) { if (paramBoolean2) if (has(1)) { this.root = new UnixDollar(false); } else { this.root = new Dollar(false); }   return -1; } case 97: return 7;case 98: if (!paramBoolean1) { if (paramBoolean2) this.root = new Bound(Bound.BOTH, has(256));  return -1; } case 99: return c();case 100: if (paramBoolean2) this.root = has(256) ? new Utype(UnicodeProp.DIGIT) : new Ctype(1024);  return -1;case 101: return 27;case 102: return 12;case 103: throw error("Illegal/unsupported escape sequence");case 104: if (paramBoolean2) this.root = new HorizWS();  return -1;case 105: case 106: throw error("Illegal/unsupported escape sequence");case 107: if (!paramBoolean1) { if (read() != 60) throw error("\\k is not followed by '<' for named capturing group");  String str = groupname(read()); if (!namedGroups().containsKey(str)) throw error("(named capturing group <" + str + "> does not exit");  if (paramBoolean2) if (has(2)) { this.root = new CIBackRef(((Integer)namedGroups().get(str)).intValue(), has(64)); } else { this.root = new BackRef(((Integer)namedGroups().get(str)).intValue()); }   return -1; } case 108: case 109: throw error("Illegal/unsupported escape sequence");case 110: return 10;case 111: case 112: case 113: throw error("Illegal/unsupported escape sequence");case 114: return 13;case 115: if (paramBoolean2) this.root = has(256) ? new Utype(UnicodeProp.WHITE_SPACE) : new Ctype(2048);  return -1;case 116: return 9;case 117: return u();case 118: if (paramBoolean3) return 11;  if (paramBoolean2) this.root = new VertWS();  return -1;case 119: if (paramBoolean2) this.root = has(256) ? new Utype(UnicodeProp.WORD) : new Ctype(67328);  return -1;case 120: return x();case 121: throw error("Illegal/unsupported escape sequence");
/*      */       case 122:
/*      */         if (!paramBoolean1) {
/*      */           if (paramBoolean2)
/*      */             this.root = new End(); 
/*      */           return -1;
/*      */         } 
/*      */     } 
/*      */     return i;
/*      */   }
/*      */   private CharProperty clazz(boolean paramBoolean) {
/* 2484 */     CharProperty charProperty1 = null;
/* 2485 */     CharProperty charProperty2 = null;
/* 2486 */     BitClass bitClass = new BitClass();
/* 2487 */     boolean bool1 = true;
/* 2488 */     boolean bool2 = true;
/* 2489 */     int i = next();
/*      */     while (true) {
/* 2491 */       switch (i) {
/*      */         
/*      */         case 94:
/* 2494 */           if (!bool2 || 
/* 2495 */             this.temp[this.cursor - 1] != 91)
/*      */             break; 
/* 2497 */           i = next();
/* 2498 */           bool1 = !bool1 ? true : false;
/*      */           continue;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 91:
/* 2505 */           bool2 = false;
/* 2506 */           charProperty2 = clazz(true);
/* 2507 */           if (charProperty1 == null) {
/* 2508 */             charProperty1 = charProperty2;
/*      */           } else {
/* 2510 */             charProperty1 = union(charProperty1, charProperty2);
/* 2511 */           }  i = peek();
/*      */           continue;
/*      */         case 38:
/* 2514 */           bool2 = false;
/* 2515 */           i = next();
/* 2516 */           if (i == 38) {
/* 2517 */             i = next();
/* 2518 */             CharProperty charProperty = null;
/* 2519 */             while (i != 93 && i != 38) {
/* 2520 */               if (i == 91)
/* 2521 */               { if (charProperty == null) {
/* 2522 */                   charProperty = clazz(true);
/*      */                 } else {
/* 2524 */                   charProperty = union(charProperty, clazz(true));
/*      */                 }  }
/* 2526 */               else { unread();
/* 2527 */                 charProperty = clazz(false); }
/*      */               
/* 2529 */               i = peek();
/*      */             } 
/* 2531 */             if (charProperty != null)
/* 2532 */               charProperty2 = charProperty; 
/* 2533 */             if (charProperty1 == null) {
/* 2534 */               if (charProperty == null) {
/* 2535 */                 throw error("Bad class syntax");
/*      */               }
/* 2537 */               charProperty1 = charProperty; continue;
/*      */             } 
/* 2539 */             charProperty1 = intersection(charProperty1, charProperty2);
/*      */             
/*      */             continue;
/*      */           } 
/* 2543 */           unread();
/*      */           break;
/*      */ 
/*      */         
/*      */         case 0:
/* 2548 */           bool2 = false;
/* 2549 */           if (this.cursor >= this.patternLength)
/* 2550 */             throw error("Unclosed character class"); 
/*      */           break;
/*      */         case 93:
/* 2553 */           bool2 = false;
/* 2554 */           if (charProperty1 != null) {
/* 2555 */             if (paramBoolean)
/* 2556 */               next(); 
/* 2557 */             return charProperty1;
/*      */           } 
/*      */           break;
/*      */         default:
/* 2561 */           bool2 = false;
/*      */           break;
/*      */       } 
/* 2564 */       charProperty2 = range(bitClass);
/* 2565 */       if (bool1) {
/* 2566 */         if (charProperty1 == null) {
/* 2567 */           charProperty1 = charProperty2;
/*      */         }
/* 2569 */         else if (charProperty1 != charProperty2) {
/* 2570 */           charProperty1 = union(charProperty1, charProperty2);
/*      */         }
/*      */       
/* 2573 */       } else if (charProperty1 == null) {
/* 2574 */         charProperty1 = charProperty2.complement();
/*      */       }
/* 2576 */       else if (charProperty1 != charProperty2) {
/* 2577 */         charProperty1 = setDifference(charProperty1, charProperty2);
/*      */       } 
/*      */       
/* 2580 */       i = peek();
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
/*      */   private CharProperty bitsOrSingle(BitClass paramBitClass, int paramInt) {
/* 2603 */     if (paramInt < 256 && (
/* 2604 */       !has(2) || !has(64) || (paramInt != 255 && paramInt != 181 && paramInt != 73 && paramInt != 105 && paramInt != 83 && paramInt != 115 && paramInt != 75 && paramInt != 107 && paramInt != 197 && paramInt != 229)))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2610 */       return paramBitClass.add(paramInt, flags()); } 
/* 2611 */     return newSingle(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private CharProperty range(BitClass paramBitClass) {
/* 2619 */     int i = peek();
/* 2620 */     if (i == 92) {
/* 2621 */       i = nextEscaped();
/* 2622 */       if (i == 112 || i == 80) {
/* 2623 */         boolean bool1 = (i == 80) ? true : false;
/* 2624 */         boolean bool2 = true;
/*      */         
/* 2626 */         i = next();
/* 2627 */         if (i != 123) {
/* 2628 */           unread();
/*      */         } else {
/* 2630 */           bool2 = false;
/* 2631 */         }  return family(bool2, bool1);
/*      */       } 
/* 2633 */       boolean bool = (this.temp[this.cursor + 1] == 45) ? true : false;
/* 2634 */       unread();
/* 2635 */       i = escape(true, true, bool);
/* 2636 */       if (i == -1) {
/* 2637 */         return (CharProperty)this.root;
/*      */       }
/*      */     } else {
/* 2640 */       next();
/*      */     } 
/* 2642 */     if (i >= 0) {
/* 2643 */       if (peek() == 45) {
/* 2644 */         int j = this.temp[this.cursor + 1];
/* 2645 */         if (j == 91) {
/* 2646 */           return bitsOrSingle(paramBitClass, i);
/*      */         }
/* 2648 */         if (j != 93) {
/* 2649 */           next();
/* 2650 */           int k = peek();
/* 2651 */           if (k == 92) {
/* 2652 */             k = escape(true, false, true);
/*      */           } else {
/* 2654 */             next();
/*      */           } 
/* 2656 */           if (k < i) {
/* 2657 */             throw error("Illegal character range");
/*      */           }
/* 2659 */           if (has(2)) {
/* 2660 */             return caseInsensitiveRangeFor(i, k);
/*      */           }
/* 2662 */           return rangeFor(i, k);
/*      */         } 
/*      */       } 
/* 2665 */       return bitsOrSingle(paramBitClass, i);
/*      */     } 
/* 2667 */     throw error("Unexpected character '" + (char)i + "'");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private CharProperty family(boolean paramBoolean1, boolean paramBoolean2) {
/*      */     String str;
/* 2676 */     next();
/*      */     
/* 2678 */     CharProperty charProperty = null;
/*      */     
/* 2680 */     if (paramBoolean1) {
/* 2681 */       int j = this.temp[this.cursor];
/* 2682 */       if (!Character.isSupplementaryCodePoint(j)) {
/* 2683 */         str = String.valueOf((char)j);
/*      */       } else {
/* 2685 */         str = new String(this.temp, this.cursor, 1);
/*      */       } 
/* 2687 */       read();
/*      */     } else {
/* 2689 */       int j = this.cursor;
/* 2690 */       mark(125);
/* 2691 */       while (read() != 125);
/*      */       
/* 2693 */       mark(0);
/* 2694 */       int k = this.cursor;
/* 2695 */       if (k > this.patternLength)
/* 2696 */         throw error("Unclosed character family"); 
/* 2697 */       if (j + 1 >= k)
/* 2698 */         throw error("Empty character family"); 
/* 2699 */       str = new String(this.temp, j, k - j - 1);
/*      */     } 
/*      */     
/* 2702 */     int i = str.indexOf('=');
/* 2703 */     if (i != -1) {
/*      */       
/* 2705 */       String str1 = str.substring(i + 1);
/* 2706 */       str = str.substring(0, i).toLowerCase(Locale.ENGLISH);
/* 2707 */       if ("sc".equals(str) || "script".equals(str)) {
/* 2708 */         charProperty = unicodeScriptPropertyFor(str1);
/* 2709 */       } else if ("blk".equals(str) || "block".equals(str)) {
/* 2710 */         charProperty = unicodeBlockPropertyFor(str1);
/* 2711 */       } else if ("gc".equals(str) || "general_category".equals(str)) {
/* 2712 */         charProperty = charPropertyNodeFor(str1);
/*      */       } else {
/* 2714 */         throw error("Unknown Unicode property {name=<" + str + ">, value=<" + str1 + ">}");
/*      */       }
/*      */     
/*      */     }
/* 2718 */     else if (str.startsWith("In")) {
/*      */       
/* 2720 */       charProperty = unicodeBlockPropertyFor(str.substring(2));
/* 2721 */     } else if (str.startsWith("Is")) {
/*      */       
/* 2723 */       str = str.substring(2);
/* 2724 */       UnicodeProp unicodeProp = UnicodeProp.forName(str);
/* 2725 */       if (unicodeProp != null)
/* 2726 */         charProperty = new Utype(unicodeProp); 
/* 2727 */       if (charProperty == null)
/* 2728 */         charProperty = CharPropertyNames.charPropertyFor(str); 
/* 2729 */       if (charProperty == null)
/* 2730 */         charProperty = unicodeScriptPropertyFor(str); 
/*      */     } else {
/* 2732 */       if (has(256)) {
/* 2733 */         UnicodeProp unicodeProp = UnicodeProp.forPOSIXName(str);
/* 2734 */         if (unicodeProp != null)
/* 2735 */           charProperty = new Utype(unicodeProp); 
/*      */       } 
/* 2737 */       if (charProperty == null) {
/* 2738 */         charProperty = charPropertyNodeFor(str);
/*      */       }
/*      */     } 
/* 2741 */     if (paramBoolean2) {
/* 2742 */       if (charProperty instanceof Category || charProperty instanceof Block)
/* 2743 */         this.hasSupplementary = true; 
/* 2744 */       charProperty = charProperty.complement();
/*      */     } 
/* 2746 */     return charProperty;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private CharProperty unicodeScriptPropertyFor(String paramString) {
/*      */     Character.UnicodeScript unicodeScript;
/*      */     try {
/* 2757 */       unicodeScript = Character.UnicodeScript.forName(paramString);
/* 2758 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 2759 */       throw error("Unknown character script name {" + paramString + "}");
/*      */     } 
/* 2761 */     return new Script(unicodeScript);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private CharProperty unicodeBlockPropertyFor(String paramString) {
/*      */     Character.UnicodeBlock unicodeBlock;
/*      */     try {
/* 2770 */       unicodeBlock = Character.UnicodeBlock.forName(paramString);
/* 2771 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 2772 */       throw error("Unknown character block name {" + paramString + "}");
/*      */     } 
/* 2774 */     return new Block(unicodeBlock);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private CharProperty charPropertyNodeFor(String paramString) {
/* 2781 */     CharProperty charProperty = CharPropertyNames.charPropertyFor(paramString);
/* 2782 */     if (charProperty == null)
/* 2783 */       throw error("Unknown character property name {" + paramString + "}"); 
/* 2784 */     return charProperty;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String groupname(int paramInt) {
/* 2792 */     StringBuilder stringBuilder = new StringBuilder();
/* 2793 */     stringBuilder.append(Character.toChars(paramInt));
/* 2794 */     while (ASCII.isLower(paramInt = read()) || ASCII.isUpper(paramInt) || 
/* 2795 */       ASCII.isDigit(paramInt)) {
/* 2796 */       stringBuilder.append(Character.toChars(paramInt));
/*      */     }
/* 2798 */     if (stringBuilder.length() == 0)
/* 2799 */       throw error("named capturing group has 0 length name"); 
/* 2800 */     if (paramInt != 62)
/* 2801 */       throw error("named capturing group is missing trailing '>'"); 
/* 2802 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Node group0() {
/* 2811 */     boolean bool = false;
/* 2812 */     Node node1 = null;
/* 2813 */     Node node2 = null;
/* 2814 */     int i = this.flags;
/* 2815 */     this.root = null;
/* 2816 */     int j = next();
/* 2817 */     if (j == 63) {
/* 2818 */       int k; TreeInfo treeInfo; boolean bool1; j = skip();
/* 2819 */       switch (j) {
/*      */         case 58:
/* 2821 */           node1 = createGroup(true);
/* 2822 */           node2 = this.root;
/* 2823 */           node1.next = expr(node2);
/*      */           break;
/*      */         case 33:
/*      */         case 61:
/* 2827 */           node1 = createGroup(true);
/* 2828 */           node2 = this.root;
/* 2829 */           node1.next = expr(node2);
/* 2830 */           if (j == 61) {
/* 2831 */             node1 = node2 = new Pos(node1); break;
/*      */           } 
/* 2833 */           node1 = node2 = new Neg(node1);
/*      */           break;
/*      */         
/*      */         case 62:
/* 2837 */           node1 = createGroup(true);
/* 2838 */           node2 = this.root;
/* 2839 */           node1.next = expr(node2);
/* 2840 */           node1 = node2 = new Ques(node1, 3);
/*      */           break;
/*      */         case 60:
/* 2843 */           j = read();
/* 2844 */           if (ASCII.isLower(j) || ASCII.isUpper(j)) {
/*      */             
/* 2846 */             String str = groupname(j);
/* 2847 */             if (namedGroups().containsKey(str)) {
/* 2848 */               throw error("Named capturing group <" + str + "> is already defined");
/*      */             }
/* 2850 */             bool = true;
/* 2851 */             node1 = createGroup(false);
/* 2852 */             node2 = this.root;
/* 2853 */             namedGroups().put(str, Integer.valueOf(this.capturingGroupCount - 1));
/* 2854 */             node1.next = expr(node2);
/*      */             break;
/*      */           } 
/* 2857 */           k = this.cursor;
/* 2858 */           node1 = createGroup(true);
/* 2859 */           node2 = this.root;
/* 2860 */           node1.next = expr(node2);
/* 2861 */           node2.next = lookbehindEnd;
/* 2862 */           treeInfo = new TreeInfo();
/* 2863 */           node1.study(treeInfo);
/* 2864 */           if (!treeInfo.maxValid) {
/* 2865 */             throw error("Look-behind group does not have an obvious maximum length");
/*      */           }
/*      */           
/* 2868 */           bool1 = findSupplementary(k, this.patternLength);
/* 2869 */           if (j == 61) {
/* 2870 */             node1 = node2 = bool1 ? new BehindS(node1, treeInfo.maxLength, treeInfo.minLength) : new Behind(node1, treeInfo.maxLength, treeInfo.minLength);
/*      */             
/*      */             break;
/*      */           } 
/*      */           
/* 2875 */           if (j == 33) {
/* 2876 */             node1 = node2 = bool1 ? new NotBehindS(node1, treeInfo.maxLength, treeInfo.minLength) : new NotBehind(node1, treeInfo.maxLength, treeInfo.minLength);
/*      */ 
/*      */             
/*      */             break;
/*      */           } 
/*      */           
/* 2882 */           throw error("Unknown look-behind group");
/*      */ 
/*      */         
/*      */         case 36:
/*      */         case 64:
/* 2887 */           throw error("Unknown group type");
/*      */         default:
/* 2889 */           unread();
/* 2890 */           addFlag();
/* 2891 */           j = read();
/* 2892 */           if (j == 41) {
/* 2893 */             return null;
/*      */           }
/* 2895 */           if (j != 58) {
/* 2896 */             throw error("Unknown inline modifier");
/*      */           }
/* 2898 */           node1 = createGroup(true);
/* 2899 */           node2 = this.root;
/* 2900 */           node1.next = expr(node2);
/*      */           break;
/*      */       } 
/*      */     } else {
/* 2904 */       bool = true;
/* 2905 */       node1 = createGroup(false);
/* 2906 */       node2 = this.root;
/* 2907 */       node1.next = expr(node2);
/*      */     } 
/*      */     
/* 2910 */     accept(41, "Unclosed group");
/* 2911 */     this.flags = i;
/*      */ 
/*      */     
/* 2914 */     Node node3 = closure(node1);
/* 2915 */     if (node3 == node1) {
/* 2916 */       this.root = node2;
/* 2917 */       return node3;
/*      */     } 
/* 2919 */     if (node1 == node2) {
/* 2920 */       this.root = node3;
/* 2921 */       return node3;
/*      */     } 
/*      */     
/* 2924 */     if (node3 instanceof Ques) {
/* 2925 */       Ques ques = (Ques)node3;
/* 2926 */       if (ques.type == 2) {
/* 2927 */         this.root = node3;
/* 2928 */         return node3;
/*      */       } 
/* 2930 */       node2.next = new BranchConn();
/* 2931 */       node2 = node2.next;
/* 2932 */       if (ques.type == 0) {
/* 2933 */         node1 = new Branch(node1, null, node2);
/*      */       } else {
/* 2935 */         node1 = new Branch(null, node1, node2);
/*      */       } 
/* 2937 */       this.root = node2;
/* 2938 */       return node1;
/* 2939 */     }  if (node3 instanceof Curly) {
/* 2940 */       Loop loop; Curly curly = (Curly)node3;
/* 2941 */       if (curly.type == 2) {
/* 2942 */         this.root = node3;
/* 2943 */         return node3;
/*      */       } 
/*      */       
/* 2946 */       TreeInfo treeInfo = new TreeInfo();
/* 2947 */       if (node1.study(treeInfo)) {
/* 2948 */         GroupTail groupTail = (GroupTail)node2;
/* 2949 */         node1 = this.root = new GroupCurly(node1.next, curly.cmin, curly.cmax, curly.type, ((GroupTail)node2).localIndex, ((GroupTail)node2).groupIndex, bool);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2954 */         return node1;
/*      */       } 
/* 2956 */       int k = ((GroupHead)node1).localIndex;
/*      */       
/* 2958 */       if (curly.type == 0) {
/* 2959 */         loop = new Loop(this.localCount, k);
/*      */       } else {
/* 2961 */         loop = new LazyLoop(this.localCount, k);
/* 2962 */       }  Prolog prolog = new Prolog(loop);
/* 2963 */       this.localCount++;
/* 2964 */       loop.cmin = curly.cmin;
/* 2965 */       loop.cmax = curly.cmax;
/* 2966 */       loop.body = node1;
/* 2967 */       node2.next = loop;
/* 2968 */       this.root = loop;
/* 2969 */       return prolog;
/*      */     } 
/*      */     
/* 2972 */     throw error("Internal logic error");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Node createGroup(boolean paramBoolean) {
/* 2981 */     int i = this.localCount++;
/* 2982 */     int j = 0;
/* 2983 */     if (!paramBoolean)
/* 2984 */       j = this.capturingGroupCount++; 
/* 2985 */     GroupHead groupHead = new GroupHead(i);
/* 2986 */     this.root = new GroupTail(i, j);
/* 2987 */     if (!paramBoolean && j < 10)
/* 2988 */       this.groupNodes[j] = groupHead; 
/* 2989 */     return groupHead;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addFlag() {
/* 2997 */     int i = peek();
/*      */     while (true) {
/* 2999 */       switch (i) {
/*      */         case 105:
/* 3001 */           this.flags |= 0x2;
/*      */           break;
/*      */         case 109:
/* 3004 */           this.flags |= 0x8;
/*      */           break;
/*      */         case 115:
/* 3007 */           this.flags |= 0x20;
/*      */           break;
/*      */         case 100:
/* 3010 */           this.flags |= 0x1;
/*      */           break;
/*      */         case 117:
/* 3013 */           this.flags |= 0x40;
/*      */           break;
/*      */         case 99:
/* 3016 */           this.flags |= 0x80;
/*      */           break;
/*      */         case 120:
/* 3019 */           this.flags |= 0x4;
/*      */           break;
/*      */         case 85:
/* 3022 */           this.flags |= 0x140;
/*      */           break;
/*      */         case 45:
/* 3025 */           i = next();
/* 3026 */           subFlag();
/*      */         default:
/*      */           return;
/*      */       } 
/* 3030 */       i = next();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void subFlag() {
/* 3040 */     int i = peek();
/*      */     while (true) {
/* 3042 */       switch (i) {
/*      */         case 105:
/* 3044 */           this.flags &= 0xFFFFFFFD;
/*      */           break;
/*      */         case 109:
/* 3047 */           this.flags &= 0xFFFFFFF7;
/*      */           break;
/*      */         case 115:
/* 3050 */           this.flags &= 0xFFFFFFDF;
/*      */           break;
/*      */         case 100:
/* 3053 */           this.flags &= 0xFFFFFFFE;
/*      */           break;
/*      */         case 117:
/* 3056 */           this.flags &= 0xFFFFFFBF;
/*      */           break;
/*      */         case 99:
/* 3059 */           this.flags &= 0xFFFFFF7F;
/*      */           break;
/*      */         case 120:
/* 3062 */           this.flags &= 0xFFFFFFFB;
/*      */           break;
/*      */         case 85:
/* 3065 */           this.flags &= 0xFFFFFEBF;
/*      */         default:
/*      */           return;
/*      */       } 
/* 3069 */       i = next();
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
/*      */   private Node closure(Node paramNode) {
/* 3090 */     int i = peek();
/* 3091 */     switch (i) {
/*      */       case 63:
/* 3093 */         i = next();
/* 3094 */         if (i == 63) {
/* 3095 */           next();
/* 3096 */           return new Ques(paramNode, 1);
/* 3097 */         }  if (i == 43) {
/* 3098 */           next();
/* 3099 */           return new Ques(paramNode, 2);
/*      */         } 
/* 3101 */         return new Ques(paramNode, 0);
/*      */       case 42:
/* 3103 */         i = next();
/* 3104 */         if (i == 63) {
/* 3105 */           next();
/* 3106 */           return new Curly(paramNode, 0, 2147483647, 1);
/* 3107 */         }  if (i == 43) {
/* 3108 */           next();
/* 3109 */           return new Curly(paramNode, 0, 2147483647, 2);
/*      */         } 
/* 3111 */         return new Curly(paramNode, 0, 2147483647, 0);
/*      */       case 43:
/* 3113 */         i = next();
/* 3114 */         if (i == 63) {
/* 3115 */           next();
/* 3116 */           return new Curly(paramNode, 1, 2147483647, 1);
/* 3117 */         }  if (i == 43) {
/* 3118 */           next();
/* 3119 */           return new Curly(paramNode, 1, 2147483647, 2);
/*      */         } 
/* 3121 */         return new Curly(paramNode, 1, 2147483647, 0);
/*      */       case 123:
/* 3123 */         i = this.temp[this.cursor + 1];
/* 3124 */         if (ASCII.isDigit(i)) {
/* 3125 */           Curly curly; skip();
/* 3126 */           int j = 0;
/*      */           do {
/* 3128 */             j = j * 10 + i - 48;
/* 3129 */           } while (ASCII.isDigit(i = read()));
/* 3130 */           int k = j;
/* 3131 */           if (i == 44) {
/* 3132 */             i = read();
/* 3133 */             k = Integer.MAX_VALUE;
/* 3134 */             if (i != 125) {
/* 3135 */               k = 0;
/* 3136 */               while (ASCII.isDigit(i)) {
/* 3137 */                 k = k * 10 + i - 48;
/* 3138 */                 i = read();
/*      */               } 
/*      */             } 
/*      */           } 
/* 3142 */           if (i != 125)
/* 3143 */             throw error("Unclosed counted closure"); 
/* 3144 */           if ((j | k | k - j) < 0) {
/* 3145 */             throw error("Illegal repetition range");
/*      */           }
/* 3147 */           i = peek();
/* 3148 */           if (i == 63) {
/* 3149 */             next();
/* 3150 */             curly = new Curly(paramNode, j, k, 1);
/* 3151 */           } else if (i == 43) {
/* 3152 */             next();
/* 3153 */             curly = new Curly(paramNode, j, k, 2);
/*      */           } else {
/* 3155 */             curly = new Curly(paramNode, j, k, 0);
/*      */           } 
/* 3157 */           return curly;
/*      */         } 
/* 3159 */         throw error("Illegal repetition");
/*      */     } 
/*      */     
/* 3162 */     return paramNode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int c() {
/* 3170 */     if (this.cursor < this.patternLength) {
/* 3171 */       return read() ^ 0x40;
/*      */     }
/* 3173 */     throw error("Illegal control escape sequence");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int o() {
/* 3180 */     int i = read();
/* 3181 */     if ((i - 48 | 55 - i) >= 0) {
/* 3182 */       int j = read();
/* 3183 */       if ((j - 48 | 55 - j) >= 0) {
/* 3184 */         int k = read();
/* 3185 */         if ((k - 48 | 55 - k) >= 0 && (i - 48 | 51 - i) >= 0) {
/* 3186 */           return (i - 48) * 64 + (j - 48) * 8 + k - 48;
/*      */         }
/* 3188 */         unread();
/* 3189 */         return (i - 48) * 8 + j - 48;
/*      */       } 
/* 3191 */       unread();
/* 3192 */       return i - 48;
/*      */     } 
/* 3194 */     throw error("Illegal octal escape sequence");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int x() {
/* 3201 */     int i = read();
/* 3202 */     if (ASCII.isHexDigit(i)) {
/* 3203 */       int j = read();
/* 3204 */       if (ASCII.isHexDigit(j)) {
/* 3205 */         return ASCII.toDigit(i) * 16 + ASCII.toDigit(j);
/*      */       }
/* 3207 */     } else if (i == 123 && ASCII.isHexDigit(peek())) {
/* 3208 */       int j = 0;
/* 3209 */       while (ASCII.isHexDigit(i = read())) {
/* 3210 */         j = (j << 4) + ASCII.toDigit(i);
/* 3211 */         if (j > 1114111)
/* 3212 */           throw error("Hexadecimal codepoint is too big"); 
/*      */       } 
/* 3214 */       if (i != 125)
/* 3215 */         throw error("Unclosed hexadecimal escape sequence"); 
/* 3216 */       return j;
/*      */     } 
/* 3218 */     throw error("Illegal hexadecimal escape sequence");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int cursor() {
/* 3225 */     return this.cursor;
/*      */   }
/*      */   
/*      */   private void setcursor(int paramInt) {
/* 3229 */     this.cursor = paramInt;
/*      */   }
/*      */   
/*      */   private int uxxxx() {
/* 3233 */     int i = 0;
/* 3234 */     for (byte b = 0; b < 4; b++) {
/* 3235 */       int j = read();
/* 3236 */       if (!ASCII.isHexDigit(j)) {
/* 3237 */         throw error("Illegal Unicode escape sequence");
/*      */       }
/* 3239 */       i = i * 16 + ASCII.toDigit(j);
/*      */     } 
/* 3241 */     return i;
/*      */   }
/*      */   
/*      */   private int u() {
/* 3245 */     int i = uxxxx();
/* 3246 */     if (Character.isHighSurrogate((char)i)) {
/* 3247 */       int j = cursor();
/* 3248 */       if (read() == 92 && read() == 117) {
/* 3249 */         int k = uxxxx();
/* 3250 */         if (Character.isLowSurrogate((char)k))
/* 3251 */           return Character.toCodePoint((char)i, (char)k); 
/*      */       } 
/* 3253 */       setcursor(j);
/*      */     } 
/* 3255 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int countChars(CharSequence paramCharSequence, int paramInt1, int paramInt2) {
/* 3265 */     if (paramInt2 == 1 && !Character.isHighSurrogate(paramCharSequence.charAt(paramInt1))) {
/* 3266 */       assert paramInt1 >= 0 && paramInt1 < paramCharSequence.length();
/* 3267 */       return 1;
/*      */     } 
/* 3269 */     int i = paramCharSequence.length();
/* 3270 */     int j = paramInt1;
/* 3271 */     if (paramInt2 >= 0) {
/* 3272 */       assert paramInt1 >= 0 && paramInt1 < i;
/* 3273 */       for (byte b1 = 0; j < i && b1 < paramInt2; b1++) {
/* 3274 */         if (Character.isHighSurrogate(paramCharSequence.charAt(j++)) && 
/* 3275 */           j < i && Character.isLowSurrogate(paramCharSequence.charAt(j))) {
/* 3276 */           j++;
/*      */         }
/*      */       } 
/*      */       
/* 3280 */       return j - paramInt1;
/*      */     } 
/*      */     
/* 3283 */     assert paramInt1 >= 0 && paramInt1 <= i;
/* 3284 */     if (paramInt1 == 0) {
/* 3285 */       return 0;
/*      */     }
/* 3287 */     int k = -paramInt2;
/* 3288 */     for (byte b = 0; j > 0 && b < k; b++) {
/* 3289 */       if (Character.isLowSurrogate(paramCharSequence.charAt(--j)) && 
/* 3290 */         j > 0 && Character.isHighSurrogate(paramCharSequence.charAt(j - 1))) {
/* 3291 */         j--;
/*      */       }
/*      */     } 
/*      */     
/* 3295 */     return paramInt1 - j;
/*      */   }
/*      */   
/*      */   private static final int countCodePoints(CharSequence paramCharSequence) {
/* 3299 */     int i = paramCharSequence.length();
/* 3300 */     byte b1 = 0;
/* 3301 */     for (byte b2 = 0; b2 < i; ) {
/* 3302 */       b1++;
/* 3303 */       if (Character.isHighSurrogate(paramCharSequence.charAt(b2++)) && 
/* 3304 */         b2 < i && Character.isLowSurrogate(paramCharSequence.charAt(b2))) {
/* 3305 */         b2++;
/*      */       }
/*      */     } 
/*      */     
/* 3309 */     return b1;
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class BitClass
/*      */     extends BmpCharProperty
/*      */   {
/*      */     final boolean[] bits;
/*      */     
/*      */     BitClass() {
/* 3319 */       this.bits = new boolean[256]; } private BitClass(boolean[] param1ArrayOfboolean) {
/* 3320 */       this.bits = param1ArrayOfboolean;
/*      */     } BitClass add(int param1Int1, int param1Int2) {
/* 3322 */       assert param1Int1 >= 0 && param1Int1 <= 255;
/* 3323 */       if ((param1Int2 & 0x2) != 0) {
/* 3324 */         if (ASCII.isAscii(param1Int1)) {
/* 3325 */           this.bits[ASCII.toUpper(param1Int1)] = true;
/* 3326 */           this.bits[ASCII.toLower(param1Int1)] = true;
/* 3327 */         } else if ((param1Int2 & 0x40) != 0) {
/* 3328 */           this.bits[Character.toLowerCase(param1Int1)] = true;
/* 3329 */           this.bits[Character.toUpperCase(param1Int1)] = true;
/*      */         } 
/*      */       }
/* 3332 */       this.bits[param1Int1] = true;
/* 3333 */       return this;
/*      */     }
/*      */     boolean isSatisfiedBy(int param1Int) {
/* 3336 */       return (param1Int < 256 && this.bits[param1Int]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private CharProperty newSingle(int paramInt) {
/* 3344 */     if (has(2))
/*      */     {
/* 3346 */       if (has(64)) {
/* 3347 */         int j = Character.toUpperCase(paramInt);
/* 3348 */         int i = Character.toLowerCase(j);
/* 3349 */         if (j != i)
/* 3350 */           return new SingleU(i); 
/* 3351 */       } else if (ASCII.isAscii(paramInt)) {
/* 3352 */         int i = ASCII.toLower(paramInt);
/* 3353 */         int j = ASCII.toUpper(paramInt);
/* 3354 */         if (i != j)
/* 3355 */           return new SingleI(i, j); 
/*      */       } 
/*      */     }
/* 3358 */     if (isSupplementary(paramInt))
/* 3359 */       return new SingleS(paramInt); 
/* 3360 */     return new Single(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Node newSlice(int[] paramArrayOfint, int paramInt, boolean paramBoolean) {
/* 3367 */     int[] arrayOfInt = new int[paramInt];
/* 3368 */     if (has(2)) {
/* 3369 */       if (has(64)) {
/* 3370 */         for (byte b2 = 0; b2 < paramInt; b2++) {
/* 3371 */           arrayOfInt[b2] = Character.toLowerCase(
/* 3372 */               Character.toUpperCase(paramArrayOfint[b2]));
/*      */         }
/* 3374 */         return paramBoolean ? new SliceUS(arrayOfInt) : new SliceU(arrayOfInt);
/*      */       } 
/* 3376 */       for (byte b1 = 0; b1 < paramInt; b1++) {
/* 3377 */         arrayOfInt[b1] = ASCII.toLower(paramArrayOfint[b1]);
/*      */       }
/* 3379 */       return paramBoolean ? new SliceIS(arrayOfInt) : new SliceI(arrayOfInt);
/*      */     } 
/* 3381 */     for (byte b = 0; b < paramInt; b++) {
/* 3382 */       arrayOfInt[b] = paramArrayOfint[b];
/*      */     }
/* 3384 */     return paramBoolean ? new SliceS(arrayOfInt) : new Slice(arrayOfInt);
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
/*      */   static class Node
/*      */   {
/* 3403 */     Node next = Pattern.accept;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3409 */       param1Matcher.last = param1Int;
/* 3410 */       param1Matcher.groups[0] = param1Matcher.first;
/* 3411 */       param1Matcher.groups[1] = param1Matcher.last;
/* 3412 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 3418 */       if (this.next != null) {
/* 3419 */         return this.next.study(param1TreeInfo);
/*      */       }
/* 3421 */       return param1TreeInfo.deterministic;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class LastNode
/*      */     extends Node
/*      */   {
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3433 */       if (param1Matcher.acceptMode == 1 && param1Int != param1Matcher.to)
/* 3434 */         return false; 
/* 3435 */       param1Matcher.last = param1Int;
/* 3436 */       param1Matcher.groups[0] = param1Matcher.first;
/* 3437 */       param1Matcher.groups[1] = param1Matcher.last;
/* 3438 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class Start
/*      */     extends Node
/*      */   {
/*      */     int minLength;
/*      */ 
/*      */     
/*      */     Start(Pattern.Node param1Node) {
/* 3451 */       this.next = param1Node;
/* 3452 */       Pattern.TreeInfo treeInfo = new Pattern.TreeInfo();
/* 3453 */       this.next.study(treeInfo);
/* 3454 */       this.minLength = treeInfo.minLength;
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3457 */       if (param1Int > param1Matcher.to - this.minLength) {
/* 3458 */         param1Matcher.hitEnd = true;
/* 3459 */         return false;
/*      */       } 
/* 3461 */       int i = param1Matcher.to - this.minLength;
/* 3462 */       for (; param1Int <= i; param1Int++) {
/* 3463 */         if (this.next.match(param1Matcher, param1Int, param1CharSequence)) {
/* 3464 */           param1Matcher.first = param1Int;
/* 3465 */           param1Matcher.groups[0] = param1Matcher.first;
/* 3466 */           param1Matcher.groups[1] = param1Matcher.last;
/* 3467 */           return true;
/*      */         } 
/*      */       } 
/* 3470 */       param1Matcher.hitEnd = true;
/* 3471 */       return false;
/*      */     }
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 3474 */       this.next.study(param1TreeInfo);
/* 3475 */       param1TreeInfo.maxValid = false;
/* 3476 */       param1TreeInfo.deterministic = false;
/* 3477 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class StartS
/*      */     extends Start
/*      */   {
/*      */     StartS(Pattern.Node param1Node) {
/* 3486 */       super(param1Node);
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3489 */       if (param1Int > param1Matcher.to - this.minLength) {
/* 3490 */         param1Matcher.hitEnd = true;
/* 3491 */         return false;
/*      */       } 
/* 3493 */       int i = param1Matcher.to - this.minLength;
/* 3494 */       while (param1Int <= i) {
/*      */         
/* 3496 */         if (this.next.match(param1Matcher, param1Int, param1CharSequence)) {
/* 3497 */           param1Matcher.first = param1Int;
/* 3498 */           param1Matcher.groups[0] = param1Matcher.first;
/* 3499 */           param1Matcher.groups[1] = param1Matcher.last;
/* 3500 */           return true;
/*      */         } 
/* 3502 */         if (param1Int == i) {
/*      */           break;
/*      */         }
/*      */         
/* 3506 */         if (Character.isHighSurrogate(param1CharSequence.charAt(param1Int++)) && 
/* 3507 */           param1Int < param1CharSequence.length() && 
/* 3508 */           Character.isLowSurrogate(param1CharSequence.charAt(param1Int))) {
/* 3509 */           param1Int++;
/*      */         }
/*      */       } 
/*      */       
/* 3513 */       param1Matcher.hitEnd = true;
/* 3514 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class Begin
/*      */     extends Node
/*      */   {
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3525 */       byte b = param1Matcher.anchoringBounds ? param1Matcher.from : 0;
/*      */       
/* 3527 */       if (param1Int == b && this.next.match(param1Matcher, param1Int, param1CharSequence)) {
/* 3528 */         param1Matcher.first = param1Int;
/* 3529 */         param1Matcher.groups[0] = param1Int;
/* 3530 */         param1Matcher.groups[1] = param1Matcher.last;
/* 3531 */         return true;
/*      */       } 
/* 3533 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class End
/*      */     extends Node
/*      */   {
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3545 */       int i = param1Matcher.anchoringBounds ? param1Matcher.to : param1Matcher.getTextLength();
/* 3546 */       if (param1Int == i) {
/* 3547 */         param1Matcher.hitEnd = true;
/* 3548 */         return this.next.match(param1Matcher, param1Int, param1CharSequence);
/*      */       } 
/* 3550 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class Caret
/*      */     extends Node
/*      */   {
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3560 */       int i = param1Matcher.from;
/* 3561 */       int j = param1Matcher.to;
/* 3562 */       if (!param1Matcher.anchoringBounds) {
/* 3563 */         i = 0;
/* 3564 */         j = param1Matcher.getTextLength();
/*      */       } 
/*      */       
/* 3567 */       if (param1Int == j) {
/* 3568 */         param1Matcher.hitEnd = true;
/* 3569 */         return false;
/*      */       } 
/* 3571 */       if (param1Int > i) {
/* 3572 */         char c = param1CharSequence.charAt(param1Int - 1);
/* 3573 */         if (c != '\n' && c != '\r' && (c | 0x1) != 8233 && c != '')
/*      */         {
/*      */           
/* 3576 */           return false;
/*      */         }
/*      */         
/* 3579 */         if (c == '\r' && param1CharSequence.charAt(param1Int) == '\n')
/* 3580 */           return false; 
/*      */       } 
/* 3582 */       return this.next.match(param1Matcher, param1Int, param1CharSequence);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class UnixCaret
/*      */     extends Node
/*      */   {
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3591 */       int i = param1Matcher.from;
/* 3592 */       int j = param1Matcher.to;
/* 3593 */       if (!param1Matcher.anchoringBounds) {
/* 3594 */         i = 0;
/* 3595 */         j = param1Matcher.getTextLength();
/*      */       } 
/*      */       
/* 3598 */       if (param1Int == j) {
/* 3599 */         param1Matcher.hitEnd = true;
/* 3600 */         return false;
/*      */       } 
/* 3602 */       if (param1Int > i) {
/* 3603 */         char c = param1CharSequence.charAt(param1Int - 1);
/* 3604 */         if (c != '\n') {
/* 3605 */           return false;
/*      */         }
/*      */       } 
/* 3608 */       return this.next.match(param1Matcher, param1Int, param1CharSequence);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class LastMatch
/*      */     extends Node
/*      */   {
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3618 */       if (param1Int != param1Matcher.oldLast)
/* 3619 */         return false; 
/* 3620 */       return this.next.match(param1Matcher, param1Int, param1CharSequence);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class Dollar
/*      */     extends Node
/*      */   {
/*      */     boolean multiline;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Dollar(boolean param1Boolean) {
/* 3640 */       this.multiline = param1Boolean;
/*      */     }
/*      */     
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3644 */       int i = param1Matcher.anchoringBounds ? param1Matcher.to : param1Matcher.getTextLength();
/* 3645 */       if (!this.multiline) {
/* 3646 */         if (param1Int < i - 2)
/* 3647 */           return false; 
/* 3648 */         if (param1Int == i - 2) {
/* 3649 */           char c = param1CharSequence.charAt(param1Int);
/* 3650 */           if (c != '\r')
/* 3651 */             return false; 
/* 3652 */           c = param1CharSequence.charAt(param1Int + 1);
/* 3653 */           if (c != '\n') {
/* 3654 */             return false;
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3665 */       if (param1Int < i) {
/* 3666 */         char c = param1CharSequence.charAt(param1Int);
/* 3667 */         if (c == '\n') {
/*      */           
/* 3669 */           if (param1Int > 0 && param1CharSequence.charAt(param1Int - 1) == '\r')
/* 3670 */             return false; 
/* 3671 */           if (this.multiline)
/* 3672 */             return this.next.match(param1Matcher, param1Int, param1CharSequence); 
/* 3673 */         } else if (c == '\r' || c == '' || (c | 0x1) == 8233) {
/*      */           
/* 3675 */           if (this.multiline)
/* 3676 */             return this.next.match(param1Matcher, param1Int, param1CharSequence); 
/*      */         } else {
/* 3678 */           return false;
/*      */         } 
/*      */       } 
/*      */       
/* 3682 */       param1Matcher.hitEnd = true;
/*      */ 
/*      */       
/* 3685 */       param1Matcher.requireEnd = true;
/* 3686 */       return this.next.match(param1Matcher, param1Int, param1CharSequence);
/*      */     }
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 3689 */       this.next.study(param1TreeInfo);
/* 3690 */       return param1TreeInfo.deterministic;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class UnixDollar
/*      */     extends Node
/*      */   {
/*      */     boolean multiline;
/*      */     
/*      */     UnixDollar(boolean param1Boolean) {
/* 3701 */       this.multiline = param1Boolean;
/*      */     }
/*      */     
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3705 */       int i = param1Matcher.anchoringBounds ? param1Matcher.to : param1Matcher.getTextLength();
/* 3706 */       if (param1Int < i) {
/* 3707 */         char c = param1CharSequence.charAt(param1Int);
/* 3708 */         if (c == '\n') {
/*      */ 
/*      */           
/* 3711 */           if (!this.multiline && param1Int != i - 1) {
/* 3712 */             return false;
/*      */           }
/*      */           
/* 3715 */           if (this.multiline)
/* 3716 */             return this.next.match(param1Matcher, param1Int, param1CharSequence); 
/*      */         } else {
/* 3718 */           return false;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 3723 */       param1Matcher.hitEnd = true;
/*      */ 
/*      */       
/* 3726 */       param1Matcher.requireEnd = true;
/* 3727 */       return this.next.match(param1Matcher, param1Int, param1CharSequence);
/*      */     }
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 3730 */       this.next.study(param1TreeInfo);
/* 3731 */       return param1TreeInfo.deterministic;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class LineEnding
/*      */     extends Node
/*      */   {
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3741 */       if (param1Int < param1Matcher.to) {
/* 3742 */         char c = param1CharSequence.charAt(param1Int);
/* 3743 */         if (c == '\n' || c == '\013' || c == '\f' || c == '' || c == ' ' || c == ' ')
/*      */         {
/* 3745 */           return this.next.match(param1Matcher, param1Int + 1, param1CharSequence); } 
/* 3746 */         if (c == '\r') {
/* 3747 */           param1Int++;
/* 3748 */           if (param1Int < param1Matcher.to && param1CharSequence.charAt(param1Int) == '\n')
/* 3749 */             param1Int++; 
/* 3750 */           return this.next.match(param1Matcher, param1Int, param1CharSequence);
/*      */         } 
/*      */       } else {
/* 3753 */         param1Matcher.hitEnd = true;
/*      */       } 
/* 3755 */       return false;
/*      */     }
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 3758 */       param1TreeInfo.minLength++;
/* 3759 */       param1TreeInfo.maxLength += 2;
/* 3760 */       return this.next.study(param1TreeInfo);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static abstract class CharProperty
/*      */     extends Node
/*      */   {
/*      */     private CharProperty() {}
/*      */     
/*      */     CharProperty complement() {
/* 3771 */       return new CharProperty() {
/*      */           boolean isSatisfiedBy(int param2Int) {
/* 3773 */             return !Pattern.CharProperty.this.isSatisfiedBy(param2Int); }
/*      */         };
/*      */     } boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3776 */       if (param1Int < param1Matcher.to) {
/* 3777 */         int i = Character.codePointAt(param1CharSequence, param1Int);
/* 3778 */         return (isSatisfiedBy(i) && this.next
/* 3779 */           .match(param1Matcher, param1Int + Character.charCount(i), param1CharSequence));
/*      */       } 
/* 3781 */       param1Matcher.hitEnd = true;
/* 3782 */       return false;
/*      */     }
/*      */     
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 3786 */       param1TreeInfo.minLength++;
/* 3787 */       param1TreeInfo.maxLength++;
/* 3788 */       return this.next.study(param1TreeInfo);
/*      */     }
/*      */     
/*      */     abstract boolean isSatisfiedBy(int param1Int);
/*      */   }
/*      */   
/*      */   private static abstract class BmpCharProperty extends CharProperty {
/*      */     private BmpCharProperty() {}
/*      */     
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3798 */       if (param1Int < param1Matcher.to) {
/* 3799 */         return (isSatisfiedBy(param1CharSequence.charAt(param1Int)) && this.next
/* 3800 */           .match(param1Matcher, param1Int + 1, param1CharSequence));
/*      */       }
/* 3802 */       param1Matcher.hitEnd = true;
/* 3803 */       return false;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class SingleS
/*      */     extends CharProperty
/*      */   {
/*      */     final int c;
/*      */     
/*      */     SingleS(int param1Int) {
/* 3813 */       this.c = param1Int;
/*      */     } boolean isSatisfiedBy(int param1Int) {
/* 3815 */       return (param1Int == this.c);
/*      */     }
/*      */   }
/*      */   
/*      */   static final class Single
/*      */     extends BmpCharProperty {
/*      */     final int c;
/*      */     
/*      */     Single(int param1Int) {
/* 3824 */       this.c = param1Int;
/*      */     } boolean isSatisfiedBy(int param1Int) {
/* 3826 */       return (param1Int == this.c);
/*      */     }
/*      */   }
/*      */   
/*      */   static final class SingleI
/*      */     extends BmpCharProperty
/*      */   {
/*      */     final int lower;
/*      */     final int upper;
/*      */     
/*      */     SingleI(int param1Int1, int param1Int2) {
/* 3837 */       this.lower = param1Int1;
/* 3838 */       this.upper = param1Int2;
/*      */     }
/*      */     boolean isSatisfiedBy(int param1Int) {
/* 3841 */       return (param1Int == this.lower || param1Int == this.upper);
/*      */     }
/*      */   }
/*      */   
/*      */   static final class SingleU
/*      */     extends CharProperty
/*      */   {
/*      */     final int lower;
/*      */     
/*      */     SingleU(int param1Int) {
/* 3851 */       this.lower = param1Int;
/*      */     }
/*      */     boolean isSatisfiedBy(int param1Int) {
/* 3854 */       return (this.lower == param1Int || this.lower == 
/* 3855 */         Character.toLowerCase(Character.toUpperCase(param1Int)));
/*      */     }
/*      */   }
/*      */   
/*      */   static final class Block
/*      */     extends CharProperty
/*      */   {
/*      */     final Character.UnicodeBlock block;
/*      */     
/*      */     Block(Character.UnicodeBlock param1UnicodeBlock) {
/* 3865 */       this.block = param1UnicodeBlock;
/*      */     }
/*      */     boolean isSatisfiedBy(int param1Int) {
/* 3868 */       return (this.block == Character.UnicodeBlock.of(param1Int));
/*      */     }
/*      */   }
/*      */   
/*      */   static final class Script
/*      */     extends CharProperty
/*      */   {
/*      */     final Character.UnicodeScript script;
/*      */     
/*      */     Script(Character.UnicodeScript param1UnicodeScript) {
/* 3878 */       this.script = param1UnicodeScript;
/*      */     }
/*      */     boolean isSatisfiedBy(int param1Int) {
/* 3881 */       return (this.script == Character.UnicodeScript.of(param1Int));
/*      */     }
/*      */   }
/*      */   
/*      */   static final class Category
/*      */     extends CharProperty {
/*      */     final int typeMask;
/*      */     
/*      */     Category(int param1Int) {
/* 3890 */       this.typeMask = param1Int;
/*      */     } boolean isSatisfiedBy(int param1Int) {
/* 3892 */       return ((this.typeMask & 1 << Character.getType(param1Int)) != 0);
/*      */     }
/*      */   }
/*      */   
/*      */   static final class Utype
/*      */     extends CharProperty {
/*      */     final UnicodeProp uprop;
/*      */     
/*      */     Utype(UnicodeProp param1UnicodeProp) {
/* 3901 */       this.uprop = param1UnicodeProp;
/*      */     } boolean isSatisfiedBy(int param1Int) {
/* 3903 */       return this.uprop.is(param1Int);
/*      */     }
/*      */   }
/*      */   
/*      */   static final class Ctype
/*      */     extends BmpCharProperty {
/*      */     final int ctype;
/*      */     
/*      */     Ctype(int param1Int) {
/* 3912 */       this.ctype = param1Int;
/*      */     } boolean isSatisfiedBy(int param1Int) {
/* 3914 */       return (param1Int < 128 && ASCII.isType(param1Int, this.ctype));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class VertWS
/*      */     extends BmpCharProperty
/*      */   {
/*      */     boolean isSatisfiedBy(int param1Int) {
/* 3923 */       return ((param1Int >= 10 && param1Int <= 13) || param1Int == 133 || param1Int == 8232 || param1Int == 8233);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class HorizWS
/*      */     extends BmpCharProperty
/*      */   {
/*      */     boolean isSatisfiedBy(int param1Int) {
/* 3933 */       return (param1Int == 9 || param1Int == 32 || param1Int == 160 || param1Int == 5760 || param1Int == 6158 || (param1Int >= 8192 && param1Int <= 8202) || param1Int == 8239 || param1Int == 8287 || param1Int == 12288);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class SliceNode
/*      */     extends Node
/*      */   {
/*      */     int[] buffer;
/*      */ 
/*      */     
/*      */     SliceNode(int[] param1ArrayOfint) {
/* 3946 */       this.buffer = param1ArrayOfint;
/*      */     }
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 3949 */       param1TreeInfo.minLength += this.buffer.length;
/* 3950 */       param1TreeInfo.maxLength += this.buffer.length;
/* 3951 */       return this.next.study(param1TreeInfo);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class Slice
/*      */     extends SliceNode
/*      */   {
/*      */     Slice(int[] param1ArrayOfint) {
/* 3961 */       super(param1ArrayOfint);
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3964 */       int[] arrayOfInt = this.buffer;
/* 3965 */       int i = arrayOfInt.length;
/* 3966 */       for (byte b = 0; b < i; b++) {
/* 3967 */         if (param1Int + b >= param1Matcher.to) {
/* 3968 */           param1Matcher.hitEnd = true;
/* 3969 */           return false;
/*      */         } 
/* 3971 */         if (arrayOfInt[b] != param1CharSequence.charAt(param1Int + b))
/* 3972 */           return false; 
/*      */       } 
/* 3974 */       return this.next.match(param1Matcher, param1Int + i, param1CharSequence);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class SliceI
/*      */     extends SliceNode
/*      */   {
/*      */     SliceI(int[] param1ArrayOfint) {
/* 3984 */       super(param1ArrayOfint);
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3987 */       int[] arrayOfInt = this.buffer;
/* 3988 */       int i = arrayOfInt.length;
/* 3989 */       for (byte b = 0; b < i; b++) {
/* 3990 */         if (param1Int + b >= param1Matcher.to) {
/* 3991 */           param1Matcher.hitEnd = true;
/* 3992 */           return false;
/*      */         } 
/* 3994 */         char c = param1CharSequence.charAt(param1Int + b);
/* 3995 */         if (arrayOfInt[b] != c && arrayOfInt[b] != 
/* 3996 */           ASCII.toLower(c))
/* 3997 */           return false; 
/*      */       } 
/* 3999 */       return this.next.match(param1Matcher, param1Int + i, param1CharSequence);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class SliceU
/*      */     extends SliceNode
/*      */   {
/*      */     SliceU(int[] param1ArrayOfint) {
/* 4009 */       super(param1ArrayOfint);
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4012 */       int[] arrayOfInt = this.buffer;
/* 4013 */       int i = arrayOfInt.length;
/* 4014 */       for (byte b = 0; b < i; b++) {
/* 4015 */         if (param1Int + b >= param1Matcher.to) {
/* 4016 */           param1Matcher.hitEnd = true;
/* 4017 */           return false;
/*      */         } 
/* 4019 */         char c = param1CharSequence.charAt(param1Int + b);
/* 4020 */         if (arrayOfInt[b] != c && arrayOfInt[b] != 
/* 4021 */           Character.toLowerCase(Character.toUpperCase(c)))
/* 4022 */           return false; 
/*      */       } 
/* 4024 */       return this.next.match(param1Matcher, param1Int + i, param1CharSequence);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class SliceS
/*      */     extends SliceNode
/*      */   {
/*      */     SliceS(int[] param1ArrayOfint) {
/* 4034 */       super(param1ArrayOfint);
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4037 */       int[] arrayOfInt = this.buffer;
/* 4038 */       int i = param1Int;
/* 4039 */       for (byte b = 0; b < arrayOfInt.length; b++) {
/* 4040 */         if (i >= param1Matcher.to) {
/* 4041 */           param1Matcher.hitEnd = true;
/* 4042 */           return false;
/*      */         } 
/* 4044 */         int j = Character.codePointAt(param1CharSequence, i);
/* 4045 */         if (arrayOfInt[b] != j)
/* 4046 */           return false; 
/* 4047 */         i += Character.charCount(j);
/* 4048 */         if (i > param1Matcher.to) {
/* 4049 */           param1Matcher.hitEnd = true;
/* 4050 */           return false;
/*      */         } 
/*      */       } 
/* 4053 */       return this.next.match(param1Matcher, i, param1CharSequence);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class SliceIS
/*      */     extends SliceNode
/*      */   {
/*      */     SliceIS(int[] param1ArrayOfint) {
/* 4063 */       super(param1ArrayOfint);
/*      */     }
/*      */     int toLower(int param1Int) {
/* 4066 */       return ASCII.toLower(param1Int);
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4069 */       int[] arrayOfInt = this.buffer;
/* 4070 */       int i = param1Int;
/* 4071 */       for (byte b = 0; b < arrayOfInt.length; b++) {
/* 4072 */         if (i >= param1Matcher.to) {
/* 4073 */           param1Matcher.hitEnd = true;
/* 4074 */           return false;
/*      */         } 
/* 4076 */         int j = Character.codePointAt(param1CharSequence, i);
/* 4077 */         if (arrayOfInt[b] != j && arrayOfInt[b] != toLower(j))
/* 4078 */           return false; 
/* 4079 */         i += Character.charCount(j);
/* 4080 */         if (i > param1Matcher.to) {
/* 4081 */           param1Matcher.hitEnd = true;
/* 4082 */           return false;
/*      */         } 
/*      */       } 
/* 4085 */       return this.next.match(param1Matcher, i, param1CharSequence);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class SliceUS
/*      */     extends SliceIS
/*      */   {
/*      */     SliceUS(int[] param1ArrayOfint) {
/* 4095 */       super(param1ArrayOfint);
/*      */     }
/*      */     int toLower(int param1Int) {
/* 4098 */       return Character.toLowerCase(Character.toUpperCase(param1Int));
/*      */     }
/*      */   }
/*      */   
/*      */   private static boolean inRange(int paramInt1, int paramInt2, int paramInt3) {
/* 4103 */     return (paramInt1 <= paramInt2 && paramInt2 <= paramInt3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static CharProperty rangeFor(final int lower, final int upper) {
/* 4111 */     return new CharProperty() {
/*      */         boolean isSatisfiedBy(int param1Int) {
/* 4113 */           return Pattern.inRange(lower, param1Int, upper);
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private CharProperty caseInsensitiveRangeFor(final int lower, final int upper) {
/* 4122 */     if (has(64))
/* 4123 */       return new CharProperty()
/*      */         {
/* 4125 */           boolean isSatisfiedBy(int param1Int) { if (Pattern.inRange(lower, param1Int, upper))
/* 4126 */               return true; 
/* 4127 */             int i = Character.toUpperCase(param1Int);
/* 4128 */             return (Pattern.inRange(lower, i, upper) || Pattern
/* 4129 */               .inRange(lower, Character.toLowerCase(i), upper)); } }; 
/* 4130 */     return new CharProperty() {
/*      */         boolean isSatisfiedBy(int param1Int) {
/* 4132 */           return (Pattern.inRange(lower, param1Int, upper) || (
/* 4133 */             ASCII.isAscii(param1Int) && (Pattern
/* 4134 */             .inRange(lower, ASCII.toUpper(param1Int), upper) || Pattern
/* 4135 */             .inRange(lower, ASCII.toLower(param1Int), upper))));
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   static final class All
/*      */     extends CharProperty
/*      */   {
/*      */     boolean isSatisfiedBy(int param1Int) {
/* 4145 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class Dot
/*      */     extends CharProperty
/*      */   {
/*      */     boolean isSatisfiedBy(int param1Int) {
/* 4154 */       return (param1Int != 10 && param1Int != 13 && (param1Int | 0x1) != 8233 && param1Int != 133);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class UnixDot
/*      */     extends CharProperty
/*      */   {
/*      */     boolean isSatisfiedBy(int param1Int) {
/* 4166 */       return (param1Int != 10);
/*      */     }
/*      */   }
/*      */   
/*      */   static final class Ques
/*      */     extends Node
/*      */   {
/*      */     Pattern.Node atom;
/*      */     int type;
/*      */     
/*      */     Ques(Pattern.Node param1Node, int param1Int) {
/* 4177 */       this.atom = param1Node;
/* 4178 */       this.type = param1Int;
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4181 */       switch (this.type) {
/*      */         case 0:
/* 4183 */           return ((this.atom.match(param1Matcher, param1Int, param1CharSequence) && this.next.match(param1Matcher, param1Matcher.last, param1CharSequence)) || this.next
/* 4184 */             .match(param1Matcher, param1Int, param1CharSequence));
/*      */         case 1:
/* 4186 */           return (this.next.match(param1Matcher, param1Int, param1CharSequence) || (this.atom
/* 4187 */             .match(param1Matcher, param1Int, param1CharSequence) && this.next.match(param1Matcher, param1Matcher.last, param1CharSequence)));
/*      */         case 2:
/* 4189 */           if (this.atom.match(param1Matcher, param1Int, param1CharSequence)) param1Int = param1Matcher.last; 
/* 4190 */           return this.next.match(param1Matcher, param1Int, param1CharSequence);
/*      */       } 
/* 4192 */       return (this.atom.match(param1Matcher, param1Int, param1CharSequence) && this.next.match(param1Matcher, param1Matcher.last, param1CharSequence));
/*      */     }
/*      */     
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 4196 */       if (this.type != 3) {
/* 4197 */         int i = param1TreeInfo.minLength;
/* 4198 */         this.atom.study(param1TreeInfo);
/* 4199 */         param1TreeInfo.minLength = i;
/* 4200 */         param1TreeInfo.deterministic = false;
/* 4201 */         return this.next.study(param1TreeInfo);
/*      */       } 
/* 4203 */       this.atom.study(param1TreeInfo);
/* 4204 */       return this.next.study(param1TreeInfo);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class Curly
/*      */     extends Node
/*      */   {
/*      */     Pattern.Node atom;
/*      */     
/*      */     int type;
/*      */     
/*      */     int cmin;
/*      */     
/*      */     int cmax;
/*      */     
/*      */     Curly(Pattern.Node param1Node, int param1Int1, int param1Int2, int param1Int3) {
/* 4221 */       this.atom = param1Node;
/* 4222 */       this.type = param1Int3;
/* 4223 */       this.cmin = param1Int1;
/* 4224 */       this.cmax = param1Int2;
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/*      */       byte b;
/* 4228 */       for (b = 0; b < this.cmin; b++) {
/* 4229 */         if (this.atom.match(param1Matcher, param1Int, param1CharSequence)) {
/* 4230 */           param1Int = param1Matcher.last;
/*      */         } else {
/*      */           
/* 4233 */           return false;
/*      */         } 
/* 4235 */       }  if (this.type == 0)
/* 4236 */         return match0(param1Matcher, param1Int, b, param1CharSequence); 
/* 4237 */       if (this.type == 1) {
/* 4238 */         return match1(param1Matcher, param1Int, b, param1CharSequence);
/*      */       }
/* 4240 */       return match2(param1Matcher, param1Int, b, param1CharSequence);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     boolean match0(Matcher param1Matcher, int param1Int1, int param1Int2, CharSequence param1CharSequence) {
/* 4246 */       if (param1Int2 >= this.cmax)
/*      */       {
/*      */         
/* 4249 */         return this.next.match(param1Matcher, param1Int1, param1CharSequence);
/*      */       }
/* 4251 */       int i = param1Int2;
/* 4252 */       if (this.atom.match(param1Matcher, param1Int1, param1CharSequence)) {
/*      */         
/* 4254 */         int j = param1Matcher.last - param1Int1;
/* 4255 */         if (j != 0) {
/*      */ 
/*      */           
/* 4258 */           param1Int1 = param1Matcher.last;
/* 4259 */           param1Int2++;
/*      */           
/* 4261 */           while (param1Int2 < this.cmax && 
/* 4262 */             this.atom.match(param1Matcher, param1Int1, param1CharSequence)) {
/*      */             
/* 4264 */             if (param1Int1 + j != param1Matcher.last) {
/* 4265 */               if (match0(param1Matcher, param1Matcher.last, param1Int2 + 1, param1CharSequence))
/* 4266 */                 return true; 
/*      */               break;
/*      */             } 
/* 4269 */             param1Int1 += j;
/* 4270 */             param1Int2++;
/*      */           } 
/*      */           
/* 4273 */           while (param1Int2 >= i) {
/* 4274 */             if (this.next.match(param1Matcher, param1Int1, param1CharSequence))
/* 4275 */               return true; 
/* 4276 */             param1Int1 -= j;
/* 4277 */             param1Int2--;
/*      */           } 
/* 4279 */           return false;
/*      */         } 
/* 4281 */       }  return this.next.match(param1Matcher, param1Int1, param1CharSequence);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean match1(Matcher param1Matcher, int param1Int1, int param1Int2, CharSequence param1CharSequence) {
/*      */       while (true) {
/* 4289 */         if (this.next.match(param1Matcher, param1Int1, param1CharSequence)) {
/* 4290 */           return true;
/*      */         }
/* 4292 */         if (param1Int2 >= this.cmax) {
/* 4293 */           return false;
/*      */         }
/* 4295 */         if (!this.atom.match(param1Matcher, param1Int1, param1CharSequence)) {
/* 4296 */           return false;
/*      */         }
/* 4298 */         if (param1Int1 == param1Matcher.last) {
/* 4299 */           return false;
/*      */         }
/* 4301 */         param1Int1 = param1Matcher.last;
/* 4302 */         param1Int2++;
/*      */       } 
/*      */     }
/*      */     boolean match2(Matcher param1Matcher, int param1Int1, int param1Int2, CharSequence param1CharSequence) {
/* 4306 */       for (; param1Int2 < this.cmax && 
/* 4307 */         this.atom.match(param1Matcher, param1Int1, param1CharSequence); param1Int2++) {
/*      */         
/* 4309 */         if (param1Int1 == param1Matcher.last)
/*      */           break; 
/* 4311 */         param1Int1 = param1Matcher.last;
/*      */       } 
/* 4313 */       return this.next.match(param1Matcher, param1Int1, param1CharSequence);
/*      */     }
/*      */     
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 4317 */       int i = param1TreeInfo.minLength;
/* 4318 */       int j = param1TreeInfo.maxLength;
/* 4319 */       boolean bool1 = param1TreeInfo.maxValid;
/* 4320 */       boolean bool2 = param1TreeInfo.deterministic;
/* 4321 */       param1TreeInfo.reset();
/*      */       
/* 4323 */       this.atom.study(param1TreeInfo);
/*      */       
/* 4325 */       int k = param1TreeInfo.minLength * this.cmin + i;
/* 4326 */       if (k < i) {
/* 4327 */         k = 268435455;
/*      */       }
/* 4329 */       param1TreeInfo.minLength = k;
/*      */       
/* 4331 */       if (bool1 & param1TreeInfo.maxValid) {
/* 4332 */         k = param1TreeInfo.maxLength * this.cmax + j;
/* 4333 */         param1TreeInfo.maxLength = k;
/* 4334 */         if (k < j) {
/* 4335 */           param1TreeInfo.maxValid = false;
/*      */         }
/*      */       } else {
/* 4338 */         param1TreeInfo.maxValid = false;
/*      */       } 
/*      */       
/* 4341 */       if (param1TreeInfo.deterministic && this.cmin == this.cmax) {
/* 4342 */         param1TreeInfo.deterministic = bool2;
/*      */       } else {
/* 4344 */         param1TreeInfo.deterministic = false;
/* 4345 */       }  return this.next.study(param1TreeInfo);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class GroupCurly
/*      */     extends Node
/*      */   {
/*      */     Pattern.Node atom;
/*      */     
/*      */     int type;
/*      */     
/*      */     int cmin;
/*      */     
/*      */     int cmax;
/*      */     
/*      */     int localIndex;
/*      */     
/*      */     int groupIndex;
/*      */     
/*      */     boolean capture;
/*      */     
/*      */     GroupCurly(Pattern.Node param1Node, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, boolean param1Boolean) {
/* 4368 */       this.atom = param1Node;
/* 4369 */       this.type = param1Int3;
/* 4370 */       this.cmin = param1Int1;
/* 4371 */       this.cmax = param1Int2;
/* 4372 */       this.localIndex = param1Int4;
/* 4373 */       this.groupIndex = param1Int5;
/* 4374 */       this.capture = param1Boolean;
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4377 */       int[] arrayOfInt1 = param1Matcher.groups;
/* 4378 */       int[] arrayOfInt2 = param1Matcher.locals;
/* 4379 */       int i = arrayOfInt2[this.localIndex];
/* 4380 */       int j = 0;
/* 4381 */       int k = 0;
/*      */       
/* 4383 */       if (this.capture) {
/* 4384 */         j = arrayOfInt1[this.groupIndex];
/* 4385 */         k = arrayOfInt1[this.groupIndex + 1];
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 4390 */       arrayOfInt2[this.localIndex] = -1;
/*      */       
/* 4392 */       boolean bool = true;
/* 4393 */       for (byte b = 0; b < this.cmin; b++) {
/* 4394 */         if (this.atom.match(param1Matcher, param1Int, param1CharSequence)) {
/* 4395 */           if (this.capture) {
/* 4396 */             arrayOfInt1[this.groupIndex] = param1Int;
/* 4397 */             arrayOfInt1[this.groupIndex + 1] = param1Matcher.last;
/*      */           } 
/* 4399 */           param1Int = param1Matcher.last;
/*      */         } else {
/* 4401 */           bool = false;
/*      */           break;
/*      */         } 
/*      */       } 
/* 4405 */       if (bool) {
/* 4406 */         if (this.type == 0) {
/* 4407 */           bool = match0(param1Matcher, param1Int, this.cmin, param1CharSequence);
/* 4408 */         } else if (this.type == 1) {
/* 4409 */           bool = match1(param1Matcher, param1Int, this.cmin, param1CharSequence);
/*      */         } else {
/* 4411 */           bool = match2(param1Matcher, param1Int, this.cmin, param1CharSequence);
/*      */         } 
/*      */       }
/* 4414 */       if (!bool) {
/* 4415 */         arrayOfInt2[this.localIndex] = i;
/* 4416 */         if (this.capture) {
/* 4417 */           arrayOfInt1[this.groupIndex] = j;
/* 4418 */           arrayOfInt1[this.groupIndex + 1] = k;
/*      */         } 
/*      */       } 
/* 4421 */       return bool;
/*      */     }
/*      */ 
/*      */     
/*      */     boolean match0(Matcher param1Matcher, int param1Int1, int param1Int2, CharSequence param1CharSequence) {
/* 4426 */       int i = param1Int2;
/* 4427 */       int[] arrayOfInt = param1Matcher.groups;
/* 4428 */       int j = 0;
/* 4429 */       int k = 0;
/* 4430 */       if (this.capture) {
/* 4431 */         j = arrayOfInt[this.groupIndex];
/* 4432 */         k = arrayOfInt[this.groupIndex + 1];
/*      */       } 
/*      */       
/* 4435 */       if (param1Int2 < this.cmax)
/*      */       {
/* 4437 */         if (this.atom.match(param1Matcher, param1Int1, param1CharSequence)) {
/*      */           
/* 4439 */           int m = param1Matcher.last - param1Int1;
/* 4440 */           if (m <= 0) {
/* 4441 */             if (this.capture) {
/* 4442 */               arrayOfInt[this.groupIndex] = param1Int1;
/* 4443 */               arrayOfInt[this.groupIndex + 1] = param1Int1 + m;
/*      */             } 
/* 4445 */             param1Int1 += m;
/*      */           } else {
/*      */             
/*      */             while (true) {
/* 4449 */               if (this.capture) {
/* 4450 */                 arrayOfInt[this.groupIndex] = param1Int1;
/* 4451 */                 arrayOfInt[this.groupIndex + 1] = param1Int1 + m;
/*      */               } 
/* 4453 */               param1Int1 += m;
/* 4454 */               if (++param1Int2 >= this.cmax)
/*      */                 break; 
/* 4456 */               if (!this.atom.match(param1Matcher, param1Int1, param1CharSequence))
/*      */                 break; 
/* 4458 */               if (param1Int1 + m != param1Matcher.last) {
/* 4459 */                 if (match0(param1Matcher, param1Int1, param1Int2, param1CharSequence))
/* 4460 */                   return true; 
/*      */                 break;
/*      */               } 
/*      */             } 
/* 4464 */             while (param1Int2 > i) {
/* 4465 */               if (this.next.match(param1Matcher, param1Int1, param1CharSequence)) {
/* 4466 */                 if (this.capture) {
/* 4467 */                   arrayOfInt[this.groupIndex + 1] = param1Int1;
/* 4468 */                   arrayOfInt[this.groupIndex] = param1Int1 - m;
/*      */                 } 
/* 4470 */                 return true;
/*      */               } 
/*      */               
/* 4473 */               param1Int1 -= m;
/* 4474 */               if (this.capture) {
/* 4475 */                 arrayOfInt[this.groupIndex + 1] = param1Int1;
/* 4476 */                 arrayOfInt[this.groupIndex] = param1Int1 - m;
/*      */               } 
/* 4478 */               param1Int2--;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       }
/* 4483 */       if (this.capture) {
/* 4484 */         arrayOfInt[this.groupIndex] = j;
/* 4485 */         arrayOfInt[this.groupIndex + 1] = k;
/*      */       } 
/* 4487 */       return this.next.match(param1Matcher, param1Int1, param1CharSequence);
/*      */     }
/*      */     
/*      */     boolean match1(Matcher param1Matcher, int param1Int1, int param1Int2, CharSequence param1CharSequence) {
/*      */       while (true) {
/* 4492 */         if (this.next.match(param1Matcher, param1Int1, param1CharSequence))
/* 4493 */           return true; 
/* 4494 */         if (param1Int2 >= this.cmax)
/* 4495 */           return false; 
/* 4496 */         if (!this.atom.match(param1Matcher, param1Int1, param1CharSequence))
/* 4497 */           return false; 
/* 4498 */         if (param1Int1 == param1Matcher.last)
/* 4499 */           return false; 
/* 4500 */         if (this.capture) {
/* 4501 */           param1Matcher.groups[this.groupIndex] = param1Int1;
/* 4502 */           param1Matcher.groups[this.groupIndex + 1] = param1Matcher.last;
/*      */         } 
/* 4504 */         param1Int1 = param1Matcher.last;
/* 4505 */         param1Int2++;
/*      */       } 
/*      */     }
/*      */     
/*      */     boolean match2(Matcher param1Matcher, int param1Int1, int param1Int2, CharSequence param1CharSequence) {
/* 4510 */       for (; param1Int2 < this.cmax && 
/* 4511 */         this.atom.match(param1Matcher, param1Int1, param1CharSequence); param1Int2++) {
/*      */ 
/*      */         
/* 4514 */         if (this.capture) {
/* 4515 */           param1Matcher.groups[this.groupIndex] = param1Int1;
/* 4516 */           param1Matcher.groups[this.groupIndex + 1] = param1Matcher.last;
/*      */         } 
/* 4518 */         if (param1Int1 == param1Matcher.last) {
/*      */           break;
/*      */         }
/* 4521 */         param1Int1 = param1Matcher.last;
/*      */       } 
/* 4523 */       return this.next.match(param1Matcher, param1Int1, param1CharSequence);
/*      */     }
/*      */     
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 4527 */       int i = param1TreeInfo.minLength;
/* 4528 */       int j = param1TreeInfo.maxLength;
/* 4529 */       boolean bool1 = param1TreeInfo.maxValid;
/* 4530 */       boolean bool2 = param1TreeInfo.deterministic;
/* 4531 */       param1TreeInfo.reset();
/*      */       
/* 4533 */       this.atom.study(param1TreeInfo);
/*      */       
/* 4535 */       int k = param1TreeInfo.minLength * this.cmin + i;
/* 4536 */       if (k < i) {
/* 4537 */         k = 268435455;
/*      */       }
/* 4539 */       param1TreeInfo.minLength = k;
/*      */       
/* 4541 */       if (bool1 & param1TreeInfo.maxValid) {
/* 4542 */         k = param1TreeInfo.maxLength * this.cmax + j;
/* 4543 */         param1TreeInfo.maxLength = k;
/* 4544 */         if (k < j) {
/* 4545 */           param1TreeInfo.maxValid = false;
/*      */         }
/*      */       } else {
/* 4548 */         param1TreeInfo.maxValid = false;
/*      */       } 
/*      */       
/* 4551 */       if (param1TreeInfo.deterministic && this.cmin == this.cmax) {
/* 4552 */         param1TreeInfo.deterministic = bool2;
/*      */       } else {
/* 4554 */         param1TreeInfo.deterministic = false;
/*      */       } 
/* 4556 */       return this.next.study(param1TreeInfo);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class BranchConn
/*      */     extends Node
/*      */   {
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4570 */       return this.next.match(param1Matcher, param1Int, param1CharSequence);
/*      */     }
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 4573 */       return param1TreeInfo.deterministic;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class Branch
/*      */     extends Node
/*      */   {
/* 4583 */     Pattern.Node[] atoms = new Pattern.Node[2];
/* 4584 */     int size = 2; Pattern.Node conn;
/*      */     
/*      */     Branch(Pattern.Node param1Node1, Pattern.Node param1Node2, Pattern.Node param1Node3) {
/* 4587 */       this.conn = param1Node3;
/* 4588 */       this.atoms[0] = param1Node1;
/* 4589 */       this.atoms[1] = param1Node2;
/*      */     }
/*      */     
/*      */     void add(Pattern.Node param1Node) {
/* 4593 */       if (this.size >= this.atoms.length) {
/* 4594 */         Pattern.Node[] arrayOfNode = new Pattern.Node[this.atoms.length * 2];
/* 4595 */         System.arraycopy(this.atoms, 0, arrayOfNode, 0, this.atoms.length);
/* 4596 */         this.atoms = arrayOfNode;
/*      */       } 
/* 4598 */       this.atoms[this.size++] = param1Node;
/*      */     }
/*      */     
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4602 */       for (byte b = 0; b < this.size; b++) {
/* 4603 */         if (this.atoms[b] == null) {
/* 4604 */           if (this.conn.next.match(param1Matcher, param1Int, param1CharSequence))
/* 4605 */             return true; 
/* 4606 */         } else if (this.atoms[b].match(param1Matcher, param1Int, param1CharSequence)) {
/* 4607 */           return true;
/*      */         } 
/*      */       } 
/* 4610 */       return false;
/*      */     }
/*      */     
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 4614 */       int i = param1TreeInfo.minLength;
/* 4615 */       int j = param1TreeInfo.maxLength;
/* 4616 */       boolean bool = param1TreeInfo.maxValid;
/*      */       
/* 4618 */       int k = Integer.MAX_VALUE;
/* 4619 */       int m = -1;
/* 4620 */       for (byte b = 0; b < this.size; b++) {
/* 4621 */         param1TreeInfo.reset();
/* 4622 */         if (this.atoms[b] != null)
/* 4623 */           this.atoms[b].study(param1TreeInfo); 
/* 4624 */         k = Math.min(k, param1TreeInfo.minLength);
/* 4625 */         m = Math.max(m, param1TreeInfo.maxLength);
/* 4626 */         bool &= param1TreeInfo.maxValid;
/*      */       } 
/*      */       
/* 4629 */       i += k;
/* 4630 */       j += m;
/*      */       
/* 4632 */       param1TreeInfo.reset();
/* 4633 */       this.conn.next.study(param1TreeInfo);
/*      */       
/* 4635 */       param1TreeInfo.minLength += i;
/* 4636 */       param1TreeInfo.maxLength += j;
/* 4637 */       param1TreeInfo.maxValid &= bool;
/* 4638 */       param1TreeInfo.deterministic = false;
/* 4639 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class GroupHead
/*      */     extends Node
/*      */   {
/*      */     int localIndex;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     GroupHead(int param1Int) {
/* 4655 */       this.localIndex = param1Int;
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4658 */       int i = param1Matcher.locals[this.localIndex];
/* 4659 */       param1Matcher.locals[this.localIndex] = param1Int;
/* 4660 */       boolean bool = this.next.match(param1Matcher, param1Int, param1CharSequence);
/* 4661 */       param1Matcher.locals[this.localIndex] = i;
/* 4662 */       return bool;
/*      */     }
/*      */     boolean matchRef(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4665 */       int i = param1Matcher.locals[this.localIndex];
/* 4666 */       param1Matcher.locals[this.localIndex] = param1Int ^ 0xFFFFFFFF;
/* 4667 */       boolean bool = this.next.match(param1Matcher, param1Int, param1CharSequence);
/* 4668 */       param1Matcher.locals[this.localIndex] = i;
/* 4669 */       return bool;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class GroupRef
/*      */     extends Node
/*      */   {
/*      */     Pattern.GroupHead head;
/*      */ 
/*      */     
/*      */     GroupRef(Pattern.GroupHead param1GroupHead) {
/* 4681 */       this.head = param1GroupHead;
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4684 */       return (this.head.matchRef(param1Matcher, param1Int, param1CharSequence) && this.next
/* 4685 */         .match(param1Matcher, param1Matcher.last, param1CharSequence));
/*      */     }
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 4688 */       param1TreeInfo.maxValid = false;
/* 4689 */       param1TreeInfo.deterministic = false;
/* 4690 */       return this.next.study(param1TreeInfo);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class GroupTail
/*      */     extends Node
/*      */   {
/*      */     int localIndex;
/*      */ 
/*      */     
/*      */     int groupIndex;
/*      */ 
/*      */     
/*      */     GroupTail(int param1Int1, int param1Int2) {
/* 4706 */       this.localIndex = param1Int1;
/* 4707 */       this.groupIndex = param1Int2 + param1Int2;
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4710 */       int i = param1Matcher.locals[this.localIndex];
/* 4711 */       if (i >= 0) {
/*      */ 
/*      */         
/* 4714 */         int j = param1Matcher.groups[this.groupIndex];
/* 4715 */         int k = param1Matcher.groups[this.groupIndex + 1];
/*      */         
/* 4717 */         param1Matcher.groups[this.groupIndex] = i;
/* 4718 */         param1Matcher.groups[this.groupIndex + 1] = param1Int;
/* 4719 */         if (this.next.match(param1Matcher, param1Int, param1CharSequence)) {
/* 4720 */           return true;
/*      */         }
/* 4722 */         param1Matcher.groups[this.groupIndex] = j;
/* 4723 */         param1Matcher.groups[this.groupIndex + 1] = k;
/* 4724 */         return false;
/*      */       } 
/*      */ 
/*      */       
/* 4728 */       param1Matcher.last = param1Int;
/* 4729 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class Prolog
/*      */     extends Node
/*      */   {
/*      */     Pattern.Loop loop;
/*      */     
/*      */     Prolog(Pattern.Loop param1Loop) {
/* 4740 */       this.loop = param1Loop;
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4743 */       return this.loop.matchInit(param1Matcher, param1Int, param1CharSequence);
/*      */     }
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 4746 */       return this.loop.study(param1TreeInfo);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class Loop
/*      */     extends Node
/*      */   {
/*      */     Pattern.Node body;
/*      */     
/*      */     int countIndex;
/*      */     int beginIndex;
/*      */     int cmin;
/*      */     int cmax;
/*      */     
/*      */     Loop(int param1Int1, int param1Int2) {
/* 4762 */       this.countIndex = param1Int1;
/* 4763 */       this.beginIndex = param1Int2;
/*      */     }
/*      */     
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4767 */       if (param1Int > param1Matcher.locals[this.beginIndex]) {
/* 4768 */         int i = param1Matcher.locals[this.countIndex];
/*      */ 
/*      */ 
/*      */         
/* 4772 */         if (i < this.cmin) {
/* 4773 */           param1Matcher.locals[this.countIndex] = i + 1;
/* 4774 */           boolean bool = this.body.match(param1Matcher, param1Int, param1CharSequence);
/*      */ 
/*      */           
/* 4777 */           if (!bool) {
/* 4778 */             param1Matcher.locals[this.countIndex] = i;
/*      */           }
/*      */           
/* 4781 */           return bool;
/*      */         } 
/*      */ 
/*      */         
/* 4785 */         if (i < this.cmax) {
/* 4786 */           param1Matcher.locals[this.countIndex] = i + 1;
/* 4787 */           boolean bool = this.body.match(param1Matcher, param1Int, param1CharSequence);
/*      */ 
/*      */           
/* 4790 */           if (!bool) {
/* 4791 */             param1Matcher.locals[this.countIndex] = i;
/*      */           } else {
/* 4793 */             return true;
/*      */           } 
/*      */         } 
/* 4796 */       }  return this.next.match(param1Matcher, param1Int, param1CharSequence);
/*      */     }
/*      */     boolean matchInit(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4799 */       int i = param1Matcher.locals[this.countIndex];
/* 4800 */       boolean bool = false;
/* 4801 */       if (0 < this.cmin) {
/* 4802 */         param1Matcher.locals[this.countIndex] = 1;
/* 4803 */         bool = this.body.match(param1Matcher, param1Int, param1CharSequence);
/* 4804 */       } else if (0 < this.cmax) {
/* 4805 */         param1Matcher.locals[this.countIndex] = 1;
/* 4806 */         bool = this.body.match(param1Matcher, param1Int, param1CharSequence);
/* 4807 */         if (!bool)
/* 4808 */           bool = this.next.match(param1Matcher, param1Int, param1CharSequence); 
/*      */       } else {
/* 4810 */         bool = this.next.match(param1Matcher, param1Int, param1CharSequence);
/*      */       } 
/* 4812 */       param1Matcher.locals[this.countIndex] = i;
/* 4813 */       return bool;
/*      */     }
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 4816 */       param1TreeInfo.maxValid = false;
/* 4817 */       param1TreeInfo.deterministic = false;
/* 4818 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class LazyLoop
/*      */     extends Loop
/*      */   {
/*      */     LazyLoop(int param1Int1, int param1Int2) {
/* 4830 */       super(param1Int1, param1Int2);
/*      */     }
/*      */     
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4834 */       if (param1Int > param1Matcher.locals[this.beginIndex]) {
/* 4835 */         int i = param1Matcher.locals[this.countIndex];
/* 4836 */         if (i < this.cmin) {
/* 4837 */           param1Matcher.locals[this.countIndex] = i + 1;
/* 4838 */           boolean bool = this.body.match(param1Matcher, param1Int, param1CharSequence);
/*      */ 
/*      */           
/* 4841 */           if (!bool)
/* 4842 */             param1Matcher.locals[this.countIndex] = i; 
/* 4843 */           return bool;
/*      */         } 
/* 4845 */         if (this.next.match(param1Matcher, param1Int, param1CharSequence))
/* 4846 */           return true; 
/* 4847 */         if (i < this.cmax) {
/* 4848 */           param1Matcher.locals[this.countIndex] = i + 1;
/* 4849 */           boolean bool = this.body.match(param1Matcher, param1Int, param1CharSequence);
/*      */ 
/*      */           
/* 4852 */           if (!bool)
/* 4853 */             param1Matcher.locals[this.countIndex] = i; 
/* 4854 */           return bool;
/*      */         } 
/* 4856 */         return false;
/*      */       } 
/* 4858 */       return this.next.match(param1Matcher, param1Int, param1CharSequence);
/*      */     }
/*      */     boolean matchInit(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4861 */       int i = param1Matcher.locals[this.countIndex];
/* 4862 */       boolean bool = false;
/* 4863 */       if (0 < this.cmin) {
/* 4864 */         param1Matcher.locals[this.countIndex] = 1;
/* 4865 */         bool = this.body.match(param1Matcher, param1Int, param1CharSequence);
/* 4866 */       } else if (this.next.match(param1Matcher, param1Int, param1CharSequence)) {
/* 4867 */         bool = true;
/* 4868 */       } else if (0 < this.cmax) {
/* 4869 */         param1Matcher.locals[this.countIndex] = 1;
/* 4870 */         bool = this.body.match(param1Matcher, param1Int, param1CharSequence);
/*      */       } 
/* 4872 */       param1Matcher.locals[this.countIndex] = i;
/* 4873 */       return bool;
/*      */     }
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 4876 */       param1TreeInfo.maxValid = false;
/* 4877 */       param1TreeInfo.deterministic = false;
/* 4878 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class BackRef
/*      */     extends Node
/*      */   {
/*      */     int groupIndex;
/*      */ 
/*      */     
/*      */     BackRef(int param1Int) {
/* 4890 */       this.groupIndex = param1Int + param1Int;
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4893 */       int i = param1Matcher.groups[this.groupIndex];
/* 4894 */       int j = param1Matcher.groups[this.groupIndex + 1];
/*      */       
/* 4896 */       int k = j - i;
/*      */       
/* 4898 */       if (i < 0) {
/* 4899 */         return false;
/*      */       }
/*      */       
/* 4902 */       if (param1Int + k > param1Matcher.to) {
/* 4903 */         param1Matcher.hitEnd = true;
/* 4904 */         return false;
/*      */       } 
/*      */ 
/*      */       
/* 4908 */       for (byte b = 0; b < k; b++) {
/* 4909 */         if (param1CharSequence.charAt(param1Int + b) != param1CharSequence.charAt(i + b))
/* 4910 */           return false; 
/*      */       } 
/* 4912 */       return this.next.match(param1Matcher, param1Int + k, param1CharSequence);
/*      */     }
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 4915 */       param1TreeInfo.maxValid = false;
/* 4916 */       return this.next.study(param1TreeInfo);
/*      */     }
/*      */   }
/*      */   
/*      */   static class CIBackRef extends Node {
/*      */     int groupIndex;
/*      */     boolean doUnicodeCase;
/*      */     
/*      */     CIBackRef(int param1Int, boolean param1Boolean) {
/* 4925 */       this.groupIndex = param1Int + param1Int;
/* 4926 */       this.doUnicodeCase = param1Boolean;
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4929 */       int i = param1Matcher.groups[this.groupIndex];
/* 4930 */       int j = param1Matcher.groups[this.groupIndex + 1];
/*      */       
/* 4932 */       int k = j - i;
/*      */ 
/*      */       
/* 4935 */       if (i < 0) {
/* 4936 */         return false;
/*      */       }
/*      */       
/* 4939 */       if (param1Int + k > param1Matcher.to) {
/* 4940 */         param1Matcher.hitEnd = true;
/* 4941 */         return false;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 4946 */       int m = param1Int;
/* 4947 */       for (byte b = 0; b < k; b++) {
/* 4948 */         int n = Character.codePointAt(param1CharSequence, m);
/* 4949 */         int i1 = Character.codePointAt(param1CharSequence, i);
/* 4950 */         if (n != i1) {
/* 4951 */           if (this.doUnicodeCase) {
/* 4952 */             int i2 = Character.toUpperCase(n);
/* 4953 */             int i3 = Character.toUpperCase(i1);
/* 4954 */             if (i2 != i3 && 
/* 4955 */               Character.toLowerCase(i2) != 
/* 4956 */               Character.toLowerCase(i3)) {
/* 4957 */               return false;
/*      */             }
/* 4959 */           } else if (ASCII.toLower(n) != ASCII.toLower(i1)) {
/* 4960 */             return false;
/*      */           } 
/*      */         }
/* 4963 */         m += Character.charCount(n);
/* 4964 */         i += Character.charCount(i1);
/*      */       } 
/*      */       
/* 4967 */       return this.next.match(param1Matcher, param1Int + k, param1CharSequence);
/*      */     }
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 4970 */       param1TreeInfo.maxValid = false;
/* 4971 */       return this.next.study(param1TreeInfo);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class First
/*      */     extends Node
/*      */   {
/*      */     Pattern.Node atom;
/*      */ 
/*      */     
/*      */     First(Pattern.Node param1Node) {
/* 4984 */       this.atom = Pattern.BnM.optimize(param1Node);
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4987 */       if (this.atom instanceof Pattern.BnM) {
/* 4988 */         return (this.atom.match(param1Matcher, param1Int, param1CharSequence) && this.next
/* 4989 */           .match(param1Matcher, param1Matcher.last, param1CharSequence));
/*      */       }
/*      */       while (true) {
/* 4992 */         if (param1Int > param1Matcher.to) {
/* 4993 */           param1Matcher.hitEnd = true;
/* 4994 */           return false;
/*      */         } 
/* 4996 */         if (this.atom.match(param1Matcher, param1Int, param1CharSequence)) {
/* 4997 */           return this.next.match(param1Matcher, param1Matcher.last, param1CharSequence);
/*      */         }
/* 4999 */         param1Int += Pattern.countChars(param1CharSequence, param1Int, 1);
/* 5000 */         param1Matcher.first++;
/*      */       } 
/*      */     }
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 5004 */       this.atom.study(param1TreeInfo);
/* 5005 */       param1TreeInfo.maxValid = false;
/* 5006 */       param1TreeInfo.deterministic = false;
/* 5007 */       return this.next.study(param1TreeInfo);
/*      */     } }
/*      */   static final class Conditional extends Node { Pattern.Node cond;
/*      */     Pattern.Node yes;
/*      */     Pattern.Node not;
/*      */     
/*      */     Conditional(Pattern.Node param1Node1, Pattern.Node param1Node2, Pattern.Node param1Node3) {
/* 5014 */       this.cond = param1Node1;
/* 5015 */       this.yes = param1Node2;
/* 5016 */       this.not = param1Node3;
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 5019 */       if (this.cond.match(param1Matcher, param1Int, param1CharSequence)) {
/* 5020 */         return this.yes.match(param1Matcher, param1Int, param1CharSequence);
/*      */       }
/* 5022 */       return this.not.match(param1Matcher, param1Int, param1CharSequence);
/*      */     }
/*      */     
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 5026 */       int i = param1TreeInfo.minLength;
/* 5027 */       int j = param1TreeInfo.maxLength;
/* 5028 */       boolean bool1 = param1TreeInfo.maxValid;
/* 5029 */       param1TreeInfo.reset();
/* 5030 */       this.yes.study(param1TreeInfo);
/*      */       
/* 5032 */       int k = param1TreeInfo.minLength;
/* 5033 */       int m = param1TreeInfo.maxLength;
/* 5034 */       boolean bool2 = param1TreeInfo.maxValid;
/* 5035 */       param1TreeInfo.reset();
/* 5036 */       this.not.study(param1TreeInfo);
/*      */       
/* 5038 */       param1TreeInfo.minLength = i + Math.min(k, param1TreeInfo.minLength);
/* 5039 */       param1TreeInfo.maxLength = j + Math.max(m, param1TreeInfo.maxLength);
/* 5040 */       param1TreeInfo.maxValid = bool1 & bool2 & param1TreeInfo.maxValid;
/* 5041 */       param1TreeInfo.deterministic = false;
/* 5042 */       return this.next.study(param1TreeInfo);
/*      */     } }
/*      */ 
/*      */   
/*      */   static final class Pos
/*      */     extends Node
/*      */   {
/*      */     Pattern.Node cond;
/*      */     
/*      */     Pos(Pattern.Node param1Node) {
/* 5052 */       this.cond = param1Node;
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 5055 */       int i = param1Matcher.to;
/* 5056 */       boolean bool = false;
/*      */ 
/*      */       
/* 5059 */       if (param1Matcher.transparentBounds)
/* 5060 */         param1Matcher.to = param1Matcher.getTextLength(); 
/*      */       try {
/* 5062 */         bool = this.cond.match(param1Matcher, param1Int, param1CharSequence);
/*      */       } finally {
/*      */         
/* 5065 */         param1Matcher.to = i;
/*      */       } 
/* 5067 */       return (bool && this.next.match(param1Matcher, param1Int, param1CharSequence));
/*      */     }
/*      */   }
/*      */   
/*      */   static final class Neg
/*      */     extends Node
/*      */   {
/*      */     Pattern.Node cond;
/*      */     
/*      */     Neg(Pattern.Node param1Node) {
/* 5077 */       this.cond = param1Node;
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 5080 */       int i = param1Matcher.to;
/* 5081 */       boolean bool = false;
/*      */ 
/*      */       
/* 5084 */       if (param1Matcher.transparentBounds)
/* 5085 */         param1Matcher.to = param1Matcher.getTextLength(); 
/*      */       try {
/* 5087 */         if (param1Int < param1Matcher.to) {
/* 5088 */           bool = !this.cond.match(param1Matcher, param1Int, param1CharSequence) ? true : false;
/*      */         }
/*      */         else {
/*      */           
/* 5092 */           param1Matcher.requireEnd = true;
/* 5093 */           bool = !this.cond.match(param1Matcher, param1Int, param1CharSequence) ? true : false;
/*      */         } 
/*      */       } finally {
/*      */         
/* 5097 */         param1Matcher.to = i;
/*      */       } 
/* 5099 */       return (bool && this.next.match(param1Matcher, param1Int, param1CharSequence));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 5107 */   static Node lookbehindEnd = new Node() {
/*      */       boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 5109 */         return (param1Int == param1Matcher.lookbehindTo);
/*      */       }
/*      */     };
/*      */   
/*      */   static class Behind
/*      */     extends Node {
/*      */     Pattern.Node cond;
/*      */     int rmax;
/*      */     int rmin;
/*      */     
/*      */     Behind(Pattern.Node param1Node, int param1Int1, int param1Int2) {
/* 5120 */       this.cond = param1Node;
/* 5121 */       this.rmax = param1Int1;
/* 5122 */       this.rmin = param1Int2;
/*      */     }
/*      */     
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 5126 */       int i = param1Matcher.from;
/* 5127 */       boolean bool = false;
/* 5128 */       boolean bool1 = !param1Matcher.transparentBounds ? param1Matcher.from : false;
/*      */       
/* 5130 */       int j = Math.max(param1Int - this.rmax, bool1);
/*      */       
/* 5132 */       int k = param1Matcher.lookbehindTo;
/* 5133 */       param1Matcher.lookbehindTo = param1Int;
/*      */       
/* 5135 */       if (param1Matcher.transparentBounds)
/* 5136 */         param1Matcher.from = 0; 
/* 5137 */       for (int m = param1Int - this.rmin; !bool && m >= j; m--) {
/* 5138 */         bool = this.cond.match(param1Matcher, m, param1CharSequence);
/*      */       }
/* 5140 */       param1Matcher.from = i;
/* 5141 */       param1Matcher.lookbehindTo = k;
/* 5142 */       return (bool && this.next.match(param1Matcher, param1Int, param1CharSequence));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class BehindS
/*      */     extends Behind
/*      */   {
/*      */     BehindS(Pattern.Node param1Node, int param1Int1, int param1Int2) {
/* 5152 */       super(param1Node, param1Int1, param1Int2);
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 5155 */       int i = Pattern.countChars(param1CharSequence, param1Int, -this.rmax);
/* 5156 */       int j = Pattern.countChars(param1CharSequence, param1Int, -this.rmin);
/* 5157 */       int k = param1Matcher.from;
/* 5158 */       boolean bool = !param1Matcher.transparentBounds ? param1Matcher.from : false;
/*      */       
/* 5160 */       boolean bool1 = false;
/* 5161 */       int m = Math.max(param1Int - i, bool);
/*      */       
/* 5163 */       int n = param1Matcher.lookbehindTo;
/* 5164 */       param1Matcher.lookbehindTo = param1Int;
/*      */       
/* 5166 */       if (param1Matcher.transparentBounds) {
/* 5167 */         param1Matcher.from = 0;
/*      */       }
/* 5169 */       int i1 = param1Int - j;
/* 5170 */       for (; !bool1 && i1 >= m; 
/* 5171 */         i1 -= (i1 > m) ? Pattern.countChars(param1CharSequence, i1, -1) : 1) {
/* 5172 */         bool1 = this.cond.match(param1Matcher, i1, param1CharSequence);
/*      */       }
/* 5174 */       param1Matcher.from = k;
/* 5175 */       param1Matcher.lookbehindTo = n;
/* 5176 */       return (bool1 && this.next.match(param1Matcher, param1Int, param1CharSequence));
/*      */     }
/*      */   }
/*      */   
/*      */   static class NotBehind
/*      */     extends Node {
/*      */     Pattern.Node cond;
/*      */     int rmax;
/*      */     int rmin;
/*      */     
/*      */     NotBehind(Pattern.Node param1Node, int param1Int1, int param1Int2) {
/* 5187 */       this.cond = param1Node;
/* 5188 */       this.rmax = param1Int1;
/* 5189 */       this.rmin = param1Int2;
/*      */     }
/*      */     
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 5193 */       int i = param1Matcher.lookbehindTo;
/* 5194 */       int j = param1Matcher.from;
/* 5195 */       boolean bool = false;
/* 5196 */       boolean bool1 = !param1Matcher.transparentBounds ? param1Matcher.from : false;
/*      */       
/* 5198 */       int k = Math.max(param1Int - this.rmax, bool1);
/* 5199 */       param1Matcher.lookbehindTo = param1Int;
/*      */       
/* 5201 */       if (param1Matcher.transparentBounds)
/* 5202 */         param1Matcher.from = 0; 
/* 5203 */       for (int m = param1Int - this.rmin; !bool && m >= k; m--) {
/* 5204 */         bool = this.cond.match(param1Matcher, m, param1CharSequence);
/*      */       }
/*      */       
/* 5207 */       param1Matcher.from = j;
/* 5208 */       param1Matcher.lookbehindTo = i;
/* 5209 */       return (!bool && this.next.match(param1Matcher, param1Int, param1CharSequence));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class NotBehindS
/*      */     extends NotBehind
/*      */   {
/*      */     NotBehindS(Pattern.Node param1Node, int param1Int1, int param1Int2) {
/* 5219 */       super(param1Node, param1Int1, param1Int2);
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 5222 */       int i = Pattern.countChars(param1CharSequence, param1Int, -this.rmax);
/* 5223 */       int j = Pattern.countChars(param1CharSequence, param1Int, -this.rmin);
/* 5224 */       int k = param1Matcher.from;
/* 5225 */       int m = param1Matcher.lookbehindTo;
/* 5226 */       boolean bool = false;
/* 5227 */       boolean bool1 = !param1Matcher.transparentBounds ? param1Matcher.from : false;
/*      */       
/* 5229 */       int n = Math.max(param1Int - i, bool1);
/* 5230 */       param1Matcher.lookbehindTo = param1Int;
/*      */       
/* 5232 */       if (param1Matcher.transparentBounds)
/* 5233 */         param1Matcher.from = 0; 
/* 5234 */       int i1 = param1Int - j;
/* 5235 */       for (; !bool && i1 >= n; 
/* 5236 */         i1 -= (i1 > n) ? Pattern.countChars(param1CharSequence, i1, -1) : 1) {
/* 5237 */         bool = this.cond.match(param1Matcher, i1, param1CharSequence);
/*      */       }
/*      */       
/* 5240 */       param1Matcher.from = k;
/* 5241 */       param1Matcher.lookbehindTo = m;
/* 5242 */       return (!bool && this.next.match(param1Matcher, param1Int, param1CharSequence));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static CharProperty union(final CharProperty lhs, final CharProperty rhs) {
/* 5251 */     return new CharProperty() {
/*      */         boolean isSatisfiedBy(int param1Int) {
/* 5253 */           return (lhs.isSatisfiedBy(param1Int) || rhs.isSatisfiedBy(param1Int));
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static CharProperty intersection(final CharProperty lhs, final CharProperty rhs) {
/* 5261 */     return new CharProperty() {
/*      */         boolean isSatisfiedBy(int param1Int) {
/* 5263 */           return (lhs.isSatisfiedBy(param1Int) && rhs.isSatisfiedBy(param1Int));
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static CharProperty setDifference(final CharProperty lhs, final CharProperty rhs) {
/* 5271 */     return new CharProperty() {
/*      */         boolean isSatisfiedBy(int param1Int) {
/* 5273 */           return (!rhs.isSatisfiedBy(param1Int) && lhs.isSatisfiedBy(param1Int));
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class Bound
/*      */     extends Node
/*      */   {
/* 5284 */     static int LEFT = 1;
/* 5285 */     static int RIGHT = 2;
/* 5286 */     static int BOTH = 3;
/* 5287 */     static int NONE = 4; int type;
/*      */     boolean useUWORD;
/*      */     
/*      */     Bound(int param1Int, boolean param1Boolean) {
/* 5291 */       this.type = param1Int;
/* 5292 */       this.useUWORD = param1Boolean;
/*      */     }
/*      */     
/*      */     boolean isWord(int param1Int) {
/* 5296 */       return this.useUWORD ? UnicodeProp.WORD.is(param1Int) : ((param1Int == 95 || 
/* 5297 */         Character.isLetterOrDigit(param1Int)));
/*      */     }
/*      */ 
/*      */     
/*      */     int check(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 5302 */       boolean bool1 = false;
/* 5303 */       int i = param1Matcher.from;
/* 5304 */       int j = param1Matcher.to;
/* 5305 */       if (param1Matcher.transparentBounds) {
/* 5306 */         i = 0;
/* 5307 */         j = param1Matcher.getTextLength();
/*      */       } 
/* 5309 */       if (param1Int > i) {
/* 5310 */         int k = Character.codePointBefore(param1CharSequence, param1Int);
/*      */ 
/*      */         
/* 5313 */         bool1 = (isWord(k) || (Character.getType(k) == 6 && Pattern.hasBaseCharacter(param1Matcher, param1Int - 1, param1CharSequence))) ? true : false;
/*      */       } 
/* 5315 */       boolean bool2 = false;
/* 5316 */       if (param1Int < j) {
/* 5317 */         int k = Character.codePointAt(param1CharSequence, param1Int);
/*      */ 
/*      */         
/* 5320 */         bool2 = (isWord(k) || (Character.getType(k) == 6 && Pattern.hasBaseCharacter(param1Matcher, param1Int, param1CharSequence))) ? true : false;
/*      */       } else {
/*      */         
/* 5323 */         param1Matcher.hitEnd = true;
/*      */         
/* 5325 */         param1Matcher.requireEnd = true;
/*      */       } 
/* 5327 */       return ((bool1 ^ bool2) != 0) ? (bool2 ? LEFT : RIGHT) : NONE;
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 5330 */       return ((check(param1Matcher, param1Int, param1CharSequence) & this.type) > 0 && this.next
/* 5331 */         .match(param1Matcher, param1Int, param1CharSequence));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean hasBaseCharacter(Matcher paramMatcher, int paramInt, CharSequence paramCharSequence) {
/* 5342 */     byte b = !paramMatcher.transparentBounds ? paramMatcher.from : 0;
/*      */     
/* 5344 */     for (int i = paramInt; i >= b; ) {
/* 5345 */       int j = Character.codePointAt(paramCharSequence, i);
/* 5346 */       if (Character.isLetterOrDigit(j))
/* 5347 */         return true; 
/* 5348 */       if (Character.getType(j) == 6) {
/*      */         i--; continue;
/* 5350 */       }  return false;
/*      */     } 
/* 5352 */     return false;
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
/*      */   static class BnM
/*      */     extends Node
/*      */   {
/*      */     int[] buffer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int[] lastOcc;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int[] optoSft;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static Pattern.Node optimize(Pattern.Node param1Node) {
/* 5396 */       if (!(param1Node instanceof Pattern.Slice)) {
/* 5397 */         return param1Node;
/*      */       }
/*      */       
/* 5400 */       int[] arrayOfInt1 = ((Pattern.Slice)param1Node).buffer;
/* 5401 */       int i = arrayOfInt1.length;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5406 */       if (i < 4) {
/* 5407 */         return param1Node;
/*      */       }
/*      */       
/* 5410 */       int[] arrayOfInt2 = new int[128];
/* 5411 */       int[] arrayOfInt3 = new int[i];
/*      */       
/*      */       int j;
/*      */       
/* 5415 */       for (j = 0; j < i; j++) {
/* 5416 */         arrayOfInt2[arrayOfInt1[j] & 0x7F] = j + 1;
/*      */       }
/*      */ 
/*      */       
/* 5420 */       for (j = i; j > 0; j--) {
/*      */         
/* 5422 */         int k = i - 1; while (true) { if (k >= j) {
/*      */             
/* 5424 */             if (arrayOfInt1[k] == arrayOfInt1[k - j]) {
/*      */               
/* 5426 */               arrayOfInt3[k - 1] = j;
/*      */ 
/*      */               
/*      */               k--;
/*      */             } 
/*      */ 
/*      */             
/*      */             break;
/*      */           } 
/*      */           
/* 5436 */           while (k > 0)
/* 5437 */             arrayOfInt3[--k] = j; 
/*      */           break; }
/*      */       
/*      */       } 
/* 5441 */       arrayOfInt3[i - 1] = 1;
/* 5442 */       if (param1Node instanceof Pattern.SliceS)
/* 5443 */         return new Pattern.BnMS(arrayOfInt1, arrayOfInt2, arrayOfInt3, param1Node.next); 
/* 5444 */       return new BnM(arrayOfInt1, arrayOfInt2, arrayOfInt3, param1Node.next);
/*      */     }
/*      */     BnM(int[] param1ArrayOfint1, int[] param1ArrayOfint2, int[] param1ArrayOfint3, Pattern.Node param1Node) {
/* 5447 */       this.buffer = param1ArrayOfint1;
/* 5448 */       this.lastOcc = param1ArrayOfint2;
/* 5449 */       this.optoSft = param1ArrayOfint3;
/* 5450 */       this.next = param1Node;
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 5453 */       int[] arrayOfInt = this.buffer;
/* 5454 */       int i = arrayOfInt.length;
/* 5455 */       int j = param1Matcher.to - i;
/*      */ 
/*      */       
/* 5458 */       label19: while (param1Int <= j) {
/*      */         
/* 5460 */         for (int k = i - 1; k >= 0; k--) {
/* 5461 */           char c = param1CharSequence.charAt(param1Int + k);
/* 5462 */           if (c != arrayOfInt[k]) {
/*      */ 
/*      */             
/* 5465 */             param1Int += Math.max(k + 1 - this.lastOcc[c & 0x7F], this.optoSft[k]);
/*      */             
/*      */             continue label19;
/*      */           } 
/*      */         } 
/* 5470 */         param1Matcher.first = param1Int;
/* 5471 */         boolean bool = this.next.match(param1Matcher, param1Int + i, param1CharSequence);
/* 5472 */         if (bool) {
/* 5473 */           param1Matcher.first = param1Int;
/* 5474 */           param1Matcher.groups[0] = param1Matcher.first;
/* 5475 */           param1Matcher.groups[1] = param1Matcher.last;
/* 5476 */           return true;
/*      */         } 
/* 5478 */         param1Int++;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 5483 */       param1Matcher.hitEnd = true;
/* 5484 */       return false;
/*      */     }
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 5487 */       param1TreeInfo.minLength += this.buffer.length;
/* 5488 */       param1TreeInfo.maxValid = false;
/* 5489 */       return this.next.study(param1TreeInfo);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class BnMS
/*      */     extends BnM
/*      */   {
/*      */     int lengthInChars;
/*      */ 
/*      */     
/*      */     BnMS(int[] param1ArrayOfint1, int[] param1ArrayOfint2, int[] param1ArrayOfint3, Pattern.Node param1Node) {
/* 5501 */       super(param1ArrayOfint1, param1ArrayOfint2, param1ArrayOfint3, param1Node);
/* 5502 */       for (byte b = 0; b < this.buffer.length; b++)
/* 5503 */         this.lengthInChars += Character.charCount(this.buffer[b]); 
/*      */     }
/*      */     
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 5507 */       int[] arrayOfInt = this.buffer;
/* 5508 */       int i = arrayOfInt.length;
/* 5509 */       int j = param1Matcher.to - this.lengthInChars;
/*      */ 
/*      */       
/* 5512 */       label19: while (param1Int <= j) {
/*      */ 
/*      */         
/* 5515 */         int k = Pattern.countChars(param1CharSequence, param1Int, i), m = i - 1;
/* 5516 */         for (; k > 0; k -= Character.charCount(n), m--) {
/* 5517 */           int n = Character.codePointBefore(param1CharSequence, param1Int + k);
/* 5518 */           if (n != arrayOfInt[m]) {
/*      */ 
/*      */             
/* 5521 */             int i1 = Math.max(m + 1 - this.lastOcc[n & 0x7F], this.optoSft[m]);
/* 5522 */             param1Int += Pattern.countChars(param1CharSequence, param1Int, i1);
/*      */             
/*      */             continue label19;
/*      */           } 
/*      */         } 
/* 5527 */         param1Matcher.first = param1Int;
/* 5528 */         boolean bool = this.next.match(param1Matcher, param1Int + this.lengthInChars, param1CharSequence);
/* 5529 */         if (bool) {
/* 5530 */           param1Matcher.first = param1Int;
/* 5531 */           param1Matcher.groups[0] = param1Matcher.first;
/* 5532 */           param1Matcher.groups[1] = param1Matcher.last;
/* 5533 */           return true;
/*      */         } 
/* 5535 */         param1Int += Pattern.countChars(param1CharSequence, param1Int, 1);
/*      */       } 
/* 5537 */       param1Matcher.hitEnd = true;
/* 5538 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 5548 */   static Node accept = new Node();
/*      */   
/* 5550 */   static Node lastAccept = new LastNode();
/*      */   
/*      */   private static class CharPropertyNames
/*      */   {
/*      */     static Pattern.CharProperty charPropertyFor(String param1String) {
/* 5555 */       CharPropertyFactory charPropertyFactory = map.get(param1String);
/* 5556 */       return (charPropertyFactory == null) ? null : charPropertyFactory.make();
/*      */     }
/*      */     
/*      */     private static abstract class CharPropertyFactory {
/*      */       private CharPropertyFactory() {}
/*      */       
/*      */       abstract Pattern.CharProperty make(); }
/*      */     
/*      */     private static void defCategory(String param1String, final int typeMask) {
/* 5565 */       map.put(param1String, new CharPropertyFactory() { Pattern.CharProperty make() {
/* 5566 */               return new Pattern.Category(typeMask);
/*      */             } }
/*      */         );
/*      */     }
/*      */     private static void defRange(String param1String, final int lower, final int upper) {
/* 5571 */       map.put(param1String, new CharPropertyFactory() { Pattern.CharProperty make() {
/* 5572 */               return Pattern.rangeFor(lower, upper);
/*      */             } }
/*      */         );
/*      */     }
/*      */     private static void defCtype(String param1String, final int ctype) {
/* 5577 */       map.put(param1String, new CharPropertyFactory() { Pattern.CharProperty make() {
/* 5578 */               return new Pattern.Ctype(ctype);
/*      */             } }
/*      */         );
/*      */     }
/*      */     private static abstract class CloneableProperty extends Pattern.CharProperty implements Cloneable { private CloneableProperty() {}
/*      */       
/*      */       public CloneableProperty clone() {
/*      */         try {
/* 5586 */           return (CloneableProperty)super.clone();
/* 5587 */         } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 5588 */           throw new AssertionError(cloneNotSupportedException);
/*      */         } 
/*      */       } }
/*      */ 
/*      */ 
/*      */     
/*      */     private static void defClone(String param1String, final CloneableProperty p) {
/* 5595 */       map.put(param1String, new CharPropertyFactory() { Pattern.CharProperty make() {
/* 5596 */               return p.clone();
/*      */             } }
/*      */         );
/* 5599 */     } private static final HashMap<String, CharPropertyFactory> map = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static {
/* 5605 */       defCategory("Cn", 1);
/* 5606 */       defCategory("Lu", 2);
/* 5607 */       defCategory("Ll", 4);
/* 5608 */       defCategory("Lt", 8);
/* 5609 */       defCategory("Lm", 16);
/* 5610 */       defCategory("Lo", 32);
/* 5611 */       defCategory("Mn", 64);
/* 5612 */       defCategory("Me", 128);
/* 5613 */       defCategory("Mc", 256);
/* 5614 */       defCategory("Nd", 512);
/* 5615 */       defCategory("Nl", 1024);
/* 5616 */       defCategory("No", 2048);
/* 5617 */       defCategory("Zs", 4096);
/* 5618 */       defCategory("Zl", 8192);
/* 5619 */       defCategory("Zp", 16384);
/* 5620 */       defCategory("Cc", 32768);
/* 5621 */       defCategory("Cf", 65536);
/* 5622 */       defCategory("Co", 262144);
/* 5623 */       defCategory("Cs", 524288);
/* 5624 */       defCategory("Pd", 1048576);
/* 5625 */       defCategory("Ps", 2097152);
/* 5626 */       defCategory("Pe", 4194304);
/* 5627 */       defCategory("Pc", 8388608);
/* 5628 */       defCategory("Po", 16777216);
/* 5629 */       defCategory("Sm", 33554432);
/* 5630 */       defCategory("Sc", 67108864);
/* 5631 */       defCategory("Sk", 134217728);
/* 5632 */       defCategory("So", 268435456);
/* 5633 */       defCategory("Pi", 536870912);
/* 5634 */       defCategory("Pf", 1073741824);
/* 5635 */       defCategory("L", 62);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5640 */       defCategory("M", 448);
/*      */ 
/*      */       
/* 5643 */       defCategory("N", 3584);
/*      */ 
/*      */       
/* 5646 */       defCategory("Z", 28672);
/*      */ 
/*      */       
/* 5649 */       defCategory("C", 884736);
/*      */ 
/*      */ 
/*      */       
/* 5653 */       defCategory("P", 1643118592);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5660 */       defCategory("S", 503316480);
/*      */ 
/*      */ 
/*      */       
/* 5664 */       defCategory("LC", 14);
/*      */ 
/*      */       
/* 5667 */       defCategory("LD", 574);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5673 */       defRange("L1", 0, 255);
/* 5674 */       map.put("all", new CharPropertyFactory() { Pattern.CharProperty make() {
/* 5675 */               return new Pattern.All();
/*      */             } }
/*      */         );
/*      */       
/* 5679 */       defRange("ASCII", 0, 127);
/* 5680 */       defCtype("Alnum", 1792);
/* 5681 */       defCtype("Alpha", 768);
/* 5682 */       defCtype("Blank", 16384);
/* 5683 */       defCtype("Cntrl", 8192);
/* 5684 */       defRange("Digit", 48, 57);
/* 5685 */       defCtype("Graph", 5888);
/* 5686 */       defRange("Lower", 97, 122);
/* 5687 */       defRange("Print", 32, 126);
/* 5688 */       defCtype("Punct", 4096);
/* 5689 */       defCtype("Space", 2048);
/* 5690 */       defRange("Upper", 65, 90);
/* 5691 */       defCtype("XDigit", 32768);
/*      */ 
/*      */       
/* 5694 */       defClone("javaLowerCase", new CloneableProperty()
/*      */           {
/* 5696 */             boolean isSatisfiedBy(int param2Int) { return Character.isLowerCase(param2Int); } });
/* 5697 */       defClone("javaUpperCase", new CloneableProperty()
/*      */           {
/* 5699 */             boolean isSatisfiedBy(int param2Int) { return Character.isUpperCase(param2Int); } });
/* 5700 */       defClone("javaAlphabetic", new CloneableProperty()
/*      */           {
/* 5702 */             boolean isSatisfiedBy(int param2Int) { return Character.isAlphabetic(param2Int); } });
/* 5703 */       defClone("javaIdeographic", new CloneableProperty()
/*      */           {
/* 5705 */             boolean isSatisfiedBy(int param2Int) { return Character.isIdeographic(param2Int); } });
/* 5706 */       defClone("javaTitleCase", new CloneableProperty()
/*      */           {
/* 5708 */             boolean isSatisfiedBy(int param2Int) { return Character.isTitleCase(param2Int); } });
/* 5709 */       defClone("javaDigit", new CloneableProperty()
/*      */           {
/* 5711 */             boolean isSatisfiedBy(int param2Int) { return Character.isDigit(param2Int); } });
/* 5712 */       defClone("javaDefined", new CloneableProperty()
/*      */           {
/* 5714 */             boolean isSatisfiedBy(int param2Int) { return Character.isDefined(param2Int); } });
/* 5715 */       defClone("javaLetter", new CloneableProperty()
/*      */           {
/* 5717 */             boolean isSatisfiedBy(int param2Int) { return Character.isLetter(param2Int); } });
/* 5718 */       defClone("javaLetterOrDigit", new CloneableProperty()
/*      */           {
/* 5720 */             boolean isSatisfiedBy(int param2Int) { return Character.isLetterOrDigit(param2Int); } });
/* 5721 */       defClone("javaJavaIdentifierStart", new CloneableProperty()
/*      */           {
/* 5723 */             boolean isSatisfiedBy(int param2Int) { return Character.isJavaIdentifierStart(param2Int); } });
/* 5724 */       defClone("javaJavaIdentifierPart", new CloneableProperty()
/*      */           {
/* 5726 */             boolean isSatisfiedBy(int param2Int) { return Character.isJavaIdentifierPart(param2Int); } });
/* 5727 */       defClone("javaUnicodeIdentifierStart", new CloneableProperty()
/*      */           {
/* 5729 */             boolean isSatisfiedBy(int param2Int) { return Character.isUnicodeIdentifierStart(param2Int); } });
/* 5730 */       defClone("javaUnicodeIdentifierPart", new CloneableProperty()
/*      */           {
/* 5732 */             boolean isSatisfiedBy(int param2Int) { return Character.isUnicodeIdentifierPart(param2Int); } });
/* 5733 */       defClone("javaIdentifierIgnorable", new CloneableProperty()
/*      */           {
/* 5735 */             boolean isSatisfiedBy(int param2Int) { return Character.isIdentifierIgnorable(param2Int); } });
/* 5736 */       defClone("javaSpaceChar", new CloneableProperty()
/*      */           {
/* 5738 */             boolean isSatisfiedBy(int param2Int) { return Character.isSpaceChar(param2Int); } });
/* 5739 */       defClone("javaWhitespace", new CloneableProperty()
/*      */           {
/* 5741 */             boolean isSatisfiedBy(int param2Int) { return Character.isWhitespace(param2Int); } });
/* 5742 */       defClone("javaISOControl", new CloneableProperty()
/*      */           {
/* 5744 */             boolean isSatisfiedBy(int param2Int) { return Character.isISOControl(param2Int); } });
/* 5745 */       defClone("javaMirrored", new CloneableProperty() {
/*      */             boolean isSatisfiedBy(int param2Int) {
/* 5747 */               return Character.isMirrored(param2Int);
/*      */             }
/*      */           });
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Predicate<String> asPredicate() {
/* 5758 */     return paramString -> matcher(paramString).find();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stream<String> splitAsStream(final CharSequence input) {
/*      */     class MatcherIterator
/*      */       implements Iterator<String>
/*      */     {
/*      */       private final Matcher matcher;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private int current;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private String nextElement;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private int emptyElementCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       MatcherIterator() {
/* 5805 */         this.matcher = Pattern.this.matcher(input);
/*      */       }
/*      */       
/*      */       public String next() {
/* 5809 */         if (!hasNext()) {
/* 5810 */           throw new NoSuchElementException();
/*      */         }
/* 5812 */         if (this.emptyElementCount == 0) {
/* 5813 */           String str = this.nextElement;
/* 5814 */           this.nextElement = null;
/* 5815 */           return str;
/*      */         } 
/* 5817 */         this.emptyElementCount--;
/* 5818 */         return "";
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean hasNext() {
/* 5823 */         if (this.nextElement != null || this.emptyElementCount > 0) {
/* 5824 */           return true;
/*      */         }
/* 5826 */         if (this.current == input.length()) {
/* 5827 */           return false;
/*      */         }
/*      */ 
/*      */         
/* 5831 */         while (this.matcher.find()) {
/* 5832 */           this.nextElement = input.subSequence(this.current, this.matcher.start()).toString();
/* 5833 */           this.current = this.matcher.end();
/* 5834 */           if (!this.nextElement.isEmpty())
/* 5835 */             return true; 
/* 5836 */           if (this.current > 0)
/*      */           {
/* 5838 */             this.emptyElementCount++;
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 5843 */         this.nextElement = input.subSequence(this.current, input.length()).toString();
/* 5844 */         this.current = input.length();
/* 5845 */         if (!this.nextElement.isEmpty()) {
/* 5846 */           return true;
/*      */         }
/*      */         
/* 5849 */         this.emptyElementCount = 0;
/* 5850 */         this.nextElement = null;
/* 5851 */         return false;
/*      */       }
/*      */     };
/*      */     
/* 5855 */     return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new MatcherIterator(), 272), false);
/*      */   }
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\regex\Pattern.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */