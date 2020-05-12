/*     */ package javax.swing.text.rtf;
/*     */ 
/*     */ import java.util.Dictionary;
/*     */ import java.util.Enumeration;
/*     */ import javax.swing.text.AttributeSet;
/*     */ import javax.swing.text.MutableAttributeSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MockAttributeSet
/*     */   implements AttributeSet, MutableAttributeSet
/*     */ {
/*     */   public Dictionary<Object, Object> backing;
/*     */   
/*     */   public boolean isEmpty() {
/*  42 */     return this.backing.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAttributeCount() {
/*  47 */     return this.backing.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDefined(Object paramObject) {
/*  52 */     return (this.backing.get(paramObject) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEqual(AttributeSet paramAttributeSet) {
/*  57 */     throw new InternalError("MockAttributeSet: charade revealed!");
/*     */   }
/*     */ 
/*     */   
/*     */   public AttributeSet copyAttributes() {
/*  62 */     throw new InternalError("MockAttributeSet: charade revealed!");
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getAttribute(Object paramObject) {
/*  67 */     return this.backing.get(paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAttribute(Object paramObject1, Object paramObject2) {
/*  72 */     this.backing.put(paramObject1, paramObject2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAttributes(AttributeSet paramAttributeSet) {
/*  77 */     Enumeration<?> enumeration = paramAttributeSet.getAttributeNames();
/*  78 */     while (enumeration.hasMoreElements()) {
/*  79 */       Object object = enumeration.nextElement();
/*  80 */       this.backing.put(object, paramAttributeSet.getAttribute(object));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeAttribute(Object paramObject) {
/*  86 */     this.backing.remove(paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeAttributes(AttributeSet paramAttributeSet) {
/*  91 */     throw new InternalError("MockAttributeSet: charade revealed!");
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeAttributes(Enumeration<?> paramEnumeration) {
/*  96 */     throw new InternalError("MockAttributeSet: charade revealed!");
/*     */   }
/*     */ 
/*     */   
/*     */   public void setResolveParent(AttributeSet paramAttributeSet) {
/* 101 */     throw new InternalError("MockAttributeSet: charade revealed!");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration getAttributeNames() {
/* 107 */     return this.backing.keys();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsAttribute(Object paramObject1, Object paramObject2) {
/* 112 */     throw new InternalError("MockAttributeSet: charade revealed!");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsAttributes(AttributeSet paramAttributeSet) {
/* 117 */     throw new InternalError("MockAttributeSet: charade revealed!");
/*     */   }
/*     */ 
/*     */   
/*     */   public AttributeSet getResolveParent() {
/* 122 */     throw new InternalError("MockAttributeSet: charade revealed!");
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\text\rtf\MockAttributeSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */