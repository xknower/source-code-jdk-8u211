/*     */ package com.sun.xml.internal.ws.util;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class QNameMap<V>
/*     */ {
/*     */   private static final int DEFAULT_INITIAL_CAPACITY = 16;
/*     */   private static final int MAXIMUM_CAPACITY = 1073741824;
/*  64 */   transient Entry<V>[] table = (Entry<V>[])new Entry[16];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   transient int size;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int threshold;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final float DEFAULT_LOAD_FACTOR = 0.75F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   private Set<Entry<V>> entrySet = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Iterable<V> views;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(String namespaceUri, String localname, V value) {
/* 108 */     assert localname != null;
/* 109 */     assert namespaceUri != null;
/*     */     
/* 111 */     int hash = hash(localname);
/* 112 */     int i = indexFor(hash, this.table.length);
/*     */     
/* 114 */     for (Entry<V> e = this.table[i]; e != null; e = e.next) {
/* 115 */       if (e.hash == hash && localname.equals(e.localName) && namespaceUri.equals(e.nsUri)) {
/* 116 */         e.value = value;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 121 */     addEntry(hash, namespaceUri, localname, value, i);
/*     */   }
/*     */ 
/*     */   
/*     */   public void put(QName name, V value) {
/* 126 */     put(name.getNamespaceURI(), name.getLocalPart(), value);
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
/*     */   public V get(@NotNull String nsUri, String localPart) {
/* 140 */     Entry<V> e = getEntry(nsUri, localPart);
/* 141 */     if (e == null) return null; 
/* 142 */     return e.value;
/*     */   }
/*     */   
/*     */   public V get(QName name) {
/* 146 */     return get(name.getNamespaceURI(), name.getLocalPart());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 155 */     return this.size;
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
/*     */   public QNameMap<V> putAll(QNameMap<? extends V> map) {
/* 167 */     int numKeysToBeAdded = map.size();
/* 168 */     if (numKeysToBeAdded == 0) {
/* 169 */       return this;
/*     */     }
/*     */     
/* 172 */     if (numKeysToBeAdded > this.threshold) {
/* 173 */       int targetCapacity = numKeysToBeAdded;
/* 174 */       if (targetCapacity > 1073741824)
/* 175 */         targetCapacity = 1073741824; 
/* 176 */       int newCapacity = this.table.length;
/* 177 */       while (newCapacity < targetCapacity)
/* 178 */         newCapacity <<= 1; 
/* 179 */       if (newCapacity > this.table.length) {
/* 180 */         resize(newCapacity);
/*     */       }
/*     */     } 
/* 183 */     for (Entry<? extends V> e : map.entrySet())
/* 184 */       put(e.nsUri, e.localName, e.getValue()); 
/* 185 */     return this;
/*     */   }
/*     */   
/*     */   public QNameMap<V> putAll(Map<QName, ? extends V> map) {
/* 189 */     for (Map.Entry<QName, ? extends V> e : map.entrySet()) {
/* 190 */       QName qn = e.getKey();
/* 191 */       put(qn.getNamespaceURI(), qn.getLocalPart(), e.getValue());
/*     */     } 
/* 193 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int hash(String x) {
/* 202 */     int h = x.hashCode();
/*     */     
/* 204 */     h += h << 9 ^ 0xFFFFFFFF;
/* 205 */     h ^= h >>> 14;
/* 206 */     h += h << 4;
/* 207 */     h ^= h >>> 10;
/* 208 */     return h;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexFor(int h, int length) {
/* 215 */     return h & length - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addEntry(int hash, String nsUri, String localName, V value, int bucketIndex) {
/* 225 */     Entry<V> e = this.table[bucketIndex];
/* 226 */     this.table[bucketIndex] = new Entry<>(hash, nsUri, localName, value, e);
/* 227 */     if (this.size++ >= this.threshold) {
/* 228 */       resize(2 * this.table.length);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void resize(int newCapacity) {
/* 238 */     Entry<V>[] arrayOfEntry = this.table;
/* 239 */     int oldCapacity = arrayOfEntry.length;
/* 240 */     if (oldCapacity == 1073741824) {
/* 241 */       this.threshold = Integer.MAX_VALUE;
/*     */       
/*     */       return;
/*     */     } 
/* 245 */     Entry[] newTable = new Entry[newCapacity];
/* 246 */     transfer((Entry<V>[])newTable);
/* 247 */     this.table = (Entry<V>[])newTable;
/* 248 */     this.threshold = newCapacity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void transfer(Entry<V>[] newTable) {
/* 255 */     Entry<V>[] src = this.table;
/* 256 */     int newCapacity = newTable.length;
/* 257 */     for (int j = 0; j < src.length; j++) {
/* 258 */       Entry<V> e = src[j];
/* 259 */       if (e != null) {
/* 260 */         src[j] = null;
/*     */         do {
/* 262 */           Entry<V> next = e.next;
/* 263 */           int i = indexFor(e.hash, newCapacity);
/* 264 */           e.next = newTable[i];
/* 265 */           newTable[i] = e;
/* 266 */           e = next;
/* 267 */         } while (e != null);
/*     */       } 
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
/*     */   public Entry<V> getOne() {
/* 280 */     for (Entry<V> e : this.table) {
/* 281 */       if (e != null)
/* 282 */         return e; 
/*     */     } 
/* 284 */     return null;
/*     */   }
/*     */   
/*     */   public Collection<QName> keySet() {
/* 288 */     Set<QName> r = new HashSet<>();
/* 289 */     for (Entry<V> e : entrySet()) {
/* 290 */       r.add(e.createQName());
/*     */     }
/* 292 */     return r;
/*     */   }
/*     */   
/*     */   public Iterable<V> values() {
/* 296 */     return this.views;
/*     */   }
/*     */   public QNameMap() {
/* 299 */     this.views = new Iterable<V>() {
/*     */         public Iterator<V> iterator() {
/* 301 */           return new QNameMap.ValueIterator();
/*     */         }
/*     */       };
/*     */     this.threshold = 12;
/*     */     this.table = (Entry<V>[])new Entry[16];
/*     */   }
/*     */   private abstract class HashIterator<E> implements Iterator<E> { QNameMap.Entry<V> next;
/*     */     
/*     */     HashIterator() {
/* 310 */       QNameMap.Entry[] arrayOfEntry = QNameMap.this.table;
/* 311 */       int i = arrayOfEntry.length;
/* 312 */       QNameMap.Entry<V> n = null;
/* 313 */       if (QNameMap.this.size != 0) {
/* 314 */         while (i > 0 && (n = arrayOfEntry[--i]) == null);
/*     */       }
/*     */       
/* 317 */       this.next = n;
/* 318 */       this.index = i;
/*     */     }
/*     */     int index;
/*     */     public boolean hasNext() {
/* 322 */       return (this.next != null);
/*     */     }
/*     */     
/*     */     QNameMap.Entry<V> nextEntry() {
/* 326 */       QNameMap.Entry<V> e = this.next;
/* 327 */       if (e == null) {
/* 328 */         throw new NoSuchElementException();
/*     */       }
/* 330 */       QNameMap.Entry<V> n = e.next;
/* 331 */       QNameMap.Entry[] arrayOfEntry = QNameMap.this.table;
/* 332 */       int i = this.index;
/* 333 */       while (n == null && i > 0)
/* 334 */         n = arrayOfEntry[--i]; 
/* 335 */       this.index = i;
/* 336 */       this.next = n;
/* 337 */       return e;
/*     */     }
/*     */     
/*     */     public void remove() {
/* 341 */       throw new UnsupportedOperationException();
/*     */     } }
/*     */   
/*     */   private class ValueIterator extends HashIterator<V> { private ValueIterator() {}
/*     */     
/*     */     public V next() {
/* 347 */       return (nextEntry()).value;
/*     */     } }
/*     */ 
/*     */   
/*     */   public boolean containsKey(@NotNull String nsUri, String localName) {
/* 352 */     return (getEntry(nsUri, localName) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 360 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Entry<V>
/*     */   {
/*     */     public final String nsUri;
/*     */     
/*     */     public final String localName;
/*     */     
/*     */     V value;
/*     */     
/*     */     final int hash;
/*     */     
/*     */     Entry<V> next;
/*     */ 
/*     */     
/*     */     Entry(int h, String nsUri, String localName, V v, Entry<V> n) {
/* 379 */       this.value = v;
/* 380 */       this.next = n;
/* 381 */       this.nsUri = nsUri;
/* 382 */       this.localName = localName;
/* 383 */       this.hash = h;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public QName createQName() {
/* 390 */       return new QName(this.nsUri, this.localName);
/*     */     }
/*     */     
/*     */     public V getValue() {
/* 394 */       return this.value;
/*     */     }
/*     */     
/*     */     public V setValue(V newValue) {
/* 398 */       V oldValue = this.value;
/* 399 */       this.value = newValue;
/* 400 */       return oldValue;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 404 */       if (!(o instanceof Entry))
/* 405 */         return false; 
/* 406 */       Entry e = (Entry)o;
/* 407 */       String k1 = this.nsUri;
/* 408 */       String k2 = e.nsUri;
/* 409 */       String k3 = this.localName;
/* 410 */       String k4 = e.localName;
/* 411 */       if (k1.equals(k2) && k3.equals(k4)) {
/* 412 */         Object v1 = getValue();
/* 413 */         Object v2 = e.getValue();
/* 414 */         if (v1 == v2 || (v1 != null && v1.equals(v2)))
/* 415 */           return true; 
/*     */       } 
/* 417 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 421 */       return this.localName.hashCode() ^ ((this.value == null) ? 0 : this.value
/* 422 */         .hashCode());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 426 */       return '"' + this.nsUri + "\",\"" + this.localName + "\"=" + getValue();
/*     */     }
/*     */   }
/*     */   
/*     */   public Set<Entry<V>> entrySet() {
/* 431 */     Set<Entry<V>> es = this.entrySet;
/* 432 */     return (es != null) ? es : (this.entrySet = new EntrySet());
/*     */   }
/*     */   
/*     */   private Iterator<Entry<V>> newEntryIterator() {
/* 436 */     return new EntryIterator();
/*     */   }
/*     */   private class EntryIterator extends HashIterator<Entry<V>> { private EntryIterator() {}
/*     */     
/*     */     public QNameMap.Entry<V> next() {
/* 441 */       return nextEntry();
/*     */     } }
/*     */   private class EntrySet extends AbstractSet<Entry<V>> { private EntrySet() {}
/*     */     
/*     */     public Iterator<QNameMap.Entry<V>> iterator() {
/* 446 */       return QNameMap.this.newEntryIterator();
/*     */     }
/*     */     public boolean contains(Object o) {
/* 449 */       if (!(o instanceof QNameMap.Entry))
/* 450 */         return false; 
/* 451 */       QNameMap.Entry<V> e = (QNameMap.Entry<V>)o;
/* 452 */       QNameMap.Entry<V> candidate = QNameMap.this.getEntry(e.nsUri, e.localName);
/* 453 */       return (candidate != null && candidate.equals(e));
/*     */     }
/*     */     public boolean remove(Object o) {
/* 456 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     public int size() {
/* 459 */       return QNameMap.this.size;
/*     */     } }
/*     */ 
/*     */   
/*     */   private Entry<V> getEntry(@NotNull String nsUri, String localName) {
/* 464 */     int hash = hash(localName);
/* 465 */     int i = indexFor(hash, this.table.length);
/* 466 */     Entry<V> e = this.table[i];
/* 467 */     while (e != null && (!localName.equals(e.localName) || !nsUri.equals(e.nsUri)))
/* 468 */       e = e.next; 
/* 469 */     return e;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 473 */     StringBuilder buf = new StringBuilder();
/* 474 */     buf.append('{');
/*     */     
/* 476 */     for (Entry<V> e : entrySet()) {
/* 477 */       if (buf.length() > 1)
/* 478 */         buf.append(','); 
/* 479 */       buf.append('[');
/* 480 */       buf.append(e);
/* 481 */       buf.append(']');
/*     */     } 
/*     */     
/* 484 */     buf.append('}');
/* 485 */     return buf.toString();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\w\\util\QNameMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */