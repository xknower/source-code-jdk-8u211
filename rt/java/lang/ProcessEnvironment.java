/*     */ package java.lang;
/*     */ 
/*     */ import java.util.AbstractCollection;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ProcessEnvironment
/*     */   extends HashMap<String, String>
/*     */ {
/*     */   private static final long serialVersionUID = -8017839552603542824L;
/*     */   static final int MIN_NAME_LENGTH = 1;
/*     */   
/*     */   private static String validateName(String paramString) {
/*  76 */     if (paramString.indexOf('=', 1) != -1 || paramString
/*  77 */       .indexOf(false) != -1) {
/*  78 */       throw new IllegalArgumentException("Invalid environment variable name: \"" + paramString + "\"");
/*     */     }
/*  80 */     return paramString;
/*     */   }
/*     */   
/*     */   private static String validateValue(String paramString) {
/*  84 */     if (paramString.indexOf(false) != -1) {
/*  85 */       throw new IllegalArgumentException("Invalid environment variable value: \"" + paramString + "\"");
/*     */     }
/*  87 */     return paramString;
/*     */   }
/*     */   
/*     */   private static String nonNullString(Object paramObject) {
/*  91 */     if (paramObject == null)
/*  92 */       throw new NullPointerException(); 
/*  93 */     return (String)paramObject;
/*     */   }
/*     */   
/*     */   public String put(String paramString1, String paramString2) {
/*  97 */     return super.put(validateName(paramString1), validateValue(paramString2));
/*     */   }
/*     */   
/*     */   public String get(Object paramObject) {
/* 101 */     return super.get(nonNullString(paramObject));
/*     */   }
/*     */   
/*     */   public boolean containsKey(Object paramObject) {
/* 105 */     return super.containsKey(nonNullString(paramObject));
/*     */   }
/*     */   
/*     */   public boolean containsValue(Object paramObject) {
/* 109 */     return super.containsValue(nonNullString(paramObject));
/*     */   }
/*     */   
/*     */   public String remove(Object paramObject) {
/* 113 */     return super.remove(nonNullString(paramObject));
/*     */   }
/*     */   
/*     */   private static class CheckedEntry implements Map.Entry<String, String>
/*     */   {
/*     */     private final Map.Entry<String, String> e;
/*     */     
/* 120 */     public CheckedEntry(Map.Entry<String, String> param1Entry) { this.e = param1Entry; }
/* 121 */     public String getKey() { return this.e.getKey(); } public String getValue() {
/* 122 */       return this.e.getValue();
/*     */     } public String setValue(String param1String) {
/* 124 */       return this.e.setValue(ProcessEnvironment.validateValue(param1String));
/*     */     }
/* 126 */     public String toString() { return getKey() + "=" + getValue(); }
/* 127 */     public boolean equals(Object param1Object) { return this.e.equals(param1Object); } public int hashCode() {
/* 128 */       return this.e.hashCode();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class CheckedEntrySet extends AbstractSet<Map.Entry<String, String>> {
/*     */     private final Set<Map.Entry<String, String>> s;
/*     */     
/* 135 */     public CheckedEntrySet(Set<Map.Entry<String, String>> param1Set) { this.s = param1Set; }
/* 136 */     public int size() { return this.s.size(); }
/* 137 */     public boolean isEmpty() { return this.s.isEmpty(); } public void clear() {
/* 138 */       this.s.clear();
/*     */     } public Iterator<Map.Entry<String, String>> iterator() {
/* 140 */       return new Iterator<Map.Entry<String, String>>() {
/* 141 */           Iterator<Map.Entry<String, String>> i = ProcessEnvironment.CheckedEntrySet.this.s.iterator(); public boolean hasNext() {
/* 142 */             return this.i.hasNext();
/*     */           } public Map.Entry<String, String> next() {
/* 144 */             return new ProcessEnvironment.CheckedEntry(this.i.next());
/*     */           } public void remove() {
/* 146 */             this.i.remove();
/*     */           }
/*     */         };
/*     */     }
/*     */     private static Map.Entry<String, String> checkedEntry(Object param1Object) {
/* 151 */       Map.Entry<String, String> entry = (Map.Entry)param1Object;
/* 152 */       ProcessEnvironment.nonNullString(entry.getKey());
/* 153 */       ProcessEnvironment.nonNullString(entry.getValue());
/* 154 */       return entry;
/*     */     }
/* 156 */     public boolean contains(Object param1Object) { return this.s.contains(checkedEntry(param1Object)); } public boolean remove(Object param1Object) {
/* 157 */       return this.s.remove(checkedEntry(param1Object));
/*     */     } }
/*     */   private static class CheckedValues extends AbstractCollection<String> {
/*     */     private final Collection<String> c;
/*     */     
/* 162 */     public CheckedValues(Collection<String> param1Collection) { this.c = param1Collection; }
/* 163 */     public int size() { return this.c.size(); }
/* 164 */     public boolean isEmpty() { return this.c.isEmpty(); }
/* 165 */     public void clear() { this.c.clear(); }
/* 166 */     public Iterator<String> iterator() { return this.c.iterator(); }
/* 167 */     public boolean contains(Object param1Object) { return this.c.contains(ProcessEnvironment.nonNullString(param1Object)); } public boolean remove(Object param1Object) {
/* 168 */       return this.c.remove(ProcessEnvironment.nonNullString(param1Object));
/*     */     }
/*     */   }
/*     */   private static class CheckedKeySet extends AbstractSet<String> { private final Set<String> s;
/*     */     
/* 173 */     public CheckedKeySet(Set<String> param1Set) { this.s = param1Set; }
/* 174 */     public int size() { return this.s.size(); }
/* 175 */     public boolean isEmpty() { return this.s.isEmpty(); }
/* 176 */     public void clear() { this.s.clear(); }
/* 177 */     public Iterator<String> iterator() { return this.s.iterator(); }
/* 178 */     public boolean contains(Object param1Object) { return this.s.contains(ProcessEnvironment.nonNullString(param1Object)); } public boolean remove(Object param1Object) {
/* 179 */       return this.s.remove(ProcessEnvironment.nonNullString(param1Object));
/*     */     } }
/*     */   
/*     */   public Set<String> keySet() {
/* 183 */     return new CheckedKeySet(super.keySet());
/*     */   }
/*     */   
/*     */   public Collection<String> values() {
/* 187 */     return new CheckedValues(super.values());
/*     */   }
/*     */   
/*     */   public Set<Map.Entry<String, String>> entrySet() {
/* 191 */     return new CheckedEntrySet(super.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class NameComparator
/*     */     implements Comparator<String>
/*     */   {
/*     */     private NameComparator() {}
/*     */ 
/*     */     
/*     */     public int compare(String param1String1, String param1String2) {
/* 202 */       int i = param1String1.length();
/* 203 */       int j = param1String2.length();
/* 204 */       int k = Math.min(i, j);
/* 205 */       for (byte b = 0; b < k; b++) {
/* 206 */         char c1 = param1String1.charAt(b);
/* 207 */         char c2 = param1String2.charAt(b);
/* 208 */         if (c1 != c2) {
/* 209 */           c1 = Character.toUpperCase(c1);
/* 210 */           c2 = Character.toUpperCase(c2);
/* 211 */           if (c1 != c2)
/*     */           {
/* 213 */             return c1 - c2; } 
/*     */         } 
/*     */       } 
/* 216 */       return i - j;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class EntryComparator implements Comparator<Map.Entry<String, String>> {
/*     */     private EntryComparator() {}
/*     */     
/*     */     public int compare(Map.Entry<String, String> param1Entry1, Map.Entry<String, String> param1Entry2) {
/* 224 */       return ProcessEnvironment.nameComparator.compare(param1Entry1.getKey(), param1Entry2.getKey());
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
/* 238 */   private static final NameComparator nameComparator = new NameComparator();
/* 239 */   private static final EntryComparator entryComparator = new EntryComparator();
/* 240 */   private static final ProcessEnvironment theEnvironment = new ProcessEnvironment();
/*     */   
/* 242 */   private static final Map<String, String> theUnmodifiableEnvironment = Collections.unmodifiableMap(theEnvironment);
/*     */   static {
/* 244 */     String str = environmentBlock();
/*     */     
/* 246 */     int i = 0; int j, k;
/* 247 */     for (; (j = str.indexOf(false, i)) != -1 && (
/*     */       
/* 249 */       k = str.indexOf('=', i + 1)) != -1; 
/* 250 */       i = j + 1) {
/*     */       
/* 252 */       if (k < j)
/* 253 */         theEnvironment.put(str.substring(i, k), str
/* 254 */             .substring(k + 1, j)); 
/*     */     } 
/*     */   }
/* 257 */   private static final Map<String, String> theCaseInsensitiveEnvironment = new TreeMap<>(nameComparator); static {
/* 258 */     theCaseInsensitiveEnvironment.putAll(theEnvironment);
/*     */   }
/*     */ 
/*     */   
/*     */   private ProcessEnvironment() {}
/*     */ 
/*     */   
/*     */   private ProcessEnvironment(int paramInt) {
/* 266 */     super(paramInt);
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
/*     */   static String getenv(String paramString) {
/* 279 */     return theCaseInsensitiveEnvironment.get(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   static Map<String, String> getenv() {
/* 284 */     return theUnmodifiableEnvironment;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static Map<String, String> environment() {
/* 290 */     return (Map<String, String>)theEnvironment.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   static Map<String, String> emptyEnvironment(int paramInt) {
/* 295 */     return new ProcessEnvironment(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String toEnvironmentBlock() {
/* 303 */     ArrayList<Map.Entry<String, String>> arrayList = new ArrayList<>(entrySet());
/* 304 */     Collections.sort(arrayList, entryComparator);
/*     */     
/* 306 */     StringBuilder stringBuilder = new StringBuilder(size() * 30);
/* 307 */     int i = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 314 */     for (Map.Entry<String, String> entry : arrayList) {
/* 315 */       String str1 = (String)entry.getKey();
/* 316 */       String str2 = (String)entry.getValue();
/* 317 */       if (i < 0 && (i = nameComparator.compare(str1, "SystemRoot")) > 0)
/*     */       {
/* 319 */         addToEnvIfSet(stringBuilder, "SystemRoot");
/*     */       }
/* 321 */       addToEnv(stringBuilder, str1, str2);
/*     */     } 
/* 323 */     if (i < 0)
/*     */     {
/* 325 */       addToEnvIfSet(stringBuilder, "SystemRoot");
/*     */     }
/* 327 */     if (stringBuilder.length() == 0)
/*     */     {
/* 329 */       stringBuilder.append(false);
/*     */     }
/*     */     
/* 332 */     stringBuilder.append(false);
/* 333 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addToEnvIfSet(StringBuilder paramStringBuilder, String paramString) {
/* 338 */     String str = getenv(paramString);
/* 339 */     if (str != null)
/* 340 */       addToEnv(paramStringBuilder, paramString, str); 
/*     */   }
/*     */   
/*     */   private static void addToEnv(StringBuilder paramStringBuilder, String paramString1, String paramString2) {
/* 344 */     paramStringBuilder.append(paramString1).append('=').append(paramString2).append(false);
/*     */   }
/*     */   
/*     */   static String toEnvironmentBlock(Map<String, String> paramMap) {
/* 348 */     return (paramMap == null) ? null : ((ProcessEnvironment)paramMap)
/* 349 */       .toEnvironmentBlock();
/*     */   }
/*     */   
/*     */   private static native String environmentBlock();
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\lang\ProcessEnvironment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */