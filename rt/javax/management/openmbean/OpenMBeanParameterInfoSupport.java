/*     */ package javax.management.openmbean;
/*     */ 
/*     */ import java.util.Set;
/*     */ import javax.management.Descriptor;
/*     */ import javax.management.ImmutableDescriptor;
/*     */ import javax.management.MBeanParameterInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OpenMBeanParameterInfoSupport
/*     */   extends MBeanParameterInfo
/*     */   implements OpenMBeanParameterInfo
/*     */ {
/*     */   static final long serialVersionUID = -7235016873758443122L;
/*     */   private OpenType<?> openType;
/*  66 */   private Object defaultValue = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   private Set<?> legalValues = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private Comparable<?> minValue = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   private Comparable<?> maxValue = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   private transient Integer myHashCode = null;
/*  88 */   private transient String myToString = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenMBeanParameterInfoSupport(String paramString1, String paramString2, OpenType<?> paramOpenType) {
/* 110 */     this(paramString1, paramString2, paramOpenType, (Descriptor)null);
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
/*     */   public OpenMBeanParameterInfoSupport(String paramString1, String paramString2, OpenType<?> paramOpenType, Descriptor paramDescriptor) {
/* 150 */     super(paramString1, (paramOpenType == null) ? null : paramOpenType
/* 151 */         .getClassName(), paramString2, 
/*     */         
/* 153 */         ImmutableDescriptor.union(new Descriptor[] {
/* 154 */             paramDescriptor, (paramOpenType == null) ? null : paramOpenType.getDescriptor()
/*     */           }));
/*     */ 
/*     */     
/* 158 */     this.openType = paramOpenType;
/*     */     
/* 160 */     paramDescriptor = getDescriptor();
/* 161 */     this.defaultValue = OpenMBeanAttributeInfoSupport.valueFrom(paramDescriptor, "defaultValue", paramOpenType);
/* 162 */     this.legalValues = OpenMBeanAttributeInfoSupport.valuesFrom(paramDescriptor, "legalValues", paramOpenType);
/* 163 */     this.minValue = OpenMBeanAttributeInfoSupport.comparableValueFrom(paramDescriptor, "minValue", paramOpenType);
/* 164 */     this.maxValue = OpenMBeanAttributeInfoSupport.comparableValueFrom(paramDescriptor, "maxValue", paramOpenType);
/*     */     
/*     */     try {
/* 167 */       OpenMBeanAttributeInfoSupport.check(this);
/* 168 */     } catch (OpenDataException openDataException) {
/* 169 */       throw new IllegalArgumentException(openDataException.getMessage(), openDataException);
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
/*     */   public <T> OpenMBeanParameterInfoSupport(String paramString1, String paramString2, OpenType<T> paramOpenType, T paramT) throws OpenDataException {
/* 209 */     this(paramString1, paramString2, paramOpenType, paramT, (T[])null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> OpenMBeanParameterInfoSupport(String paramString1, String paramString2, OpenType<T> paramOpenType, T paramT, T[] paramArrayOfT) throws OpenDataException {
/* 264 */     this(paramString1, paramString2, paramOpenType, paramT, paramArrayOfT, (Comparable<T>)null, (Comparable<T>)null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> OpenMBeanParameterInfoSupport(String paramString1, String paramString2, OpenType<T> paramOpenType, T paramT, Comparable<T> paramComparable1, Comparable<T> paramComparable2) throws OpenDataException {
/* 326 */     this(paramString1, paramString2, paramOpenType, paramT, (T[])null, paramComparable1, paramComparable2);
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
/*     */   private <T> OpenMBeanParameterInfoSupport(String paramString1, String paramString2, OpenType<T> paramOpenType, T paramT, T[] paramArrayOfT, Comparable<T> paramComparable1, Comparable<T> paramComparable2) throws OpenDataException {
/* 338 */     super(paramString1, (paramOpenType == null) ? null : paramOpenType
/* 339 */         .getClassName(), paramString2, 
/*     */         
/* 341 */         OpenMBeanAttributeInfoSupport.makeDescriptor(paramOpenType, paramT, paramArrayOfT, paramComparable1, paramComparable2));
/*     */ 
/*     */     
/* 344 */     this.openType = paramOpenType;
/*     */     
/* 346 */     Descriptor descriptor = getDescriptor();
/* 347 */     this.defaultValue = paramT;
/* 348 */     this.minValue = paramComparable1;
/* 349 */     this.maxValue = paramComparable2;
/*     */ 
/*     */     
/* 352 */     this.legalValues = (Set)descriptor.getFieldValue("legalValues");
/*     */     
/* 354 */     OpenMBeanAttributeInfoSupport.check(this);
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
/*     */   private Object readResolve() {
/* 366 */     if ((getDescriptor().getFieldNames()).length == 0) {
/*     */ 
/*     */       
/* 369 */       OpenType openType = OpenMBeanAttributeInfoSupport.<OpenType>cast(this.openType);
/* 370 */       Set set = OpenMBeanAttributeInfoSupport.<Set>cast(this.legalValues);
/* 371 */       Comparable comparable1 = OpenMBeanAttributeInfoSupport.<Comparable>cast(this.minValue);
/* 372 */       Comparable comparable2 = OpenMBeanAttributeInfoSupport.<Comparable>cast(this.maxValue);
/* 373 */       return new OpenMBeanParameterInfoSupport(this.name, this.description, this.openType, 
/*     */           
/* 375 */           OpenMBeanAttributeInfoSupport.makeDescriptor(openType, this.defaultValue, set, comparable1, comparable2));
/*     */     } 
/*     */     
/* 378 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenType<?> getOpenType() {
/* 386 */     return this.openType;
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
/*     */   public Object getDefaultValue() {
/* 402 */     return this.defaultValue;
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
/*     */   public Set<?> getLegalValues() {
/* 419 */     return this.legalValues;
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
/*     */   public Comparable<?> getMinValue() {
/* 432 */     return this.minValue;
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
/*     */   public Comparable<?> getMaxValue() {
/* 445 */     return this.maxValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasDefaultValue() {
/* 456 */     return (this.defaultValue != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasLegalValues() {
/* 467 */     return (this.legalValues != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasMinValue() {
/* 478 */     return (this.minValue != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasMaxValue() {
/* 489 */     return (this.maxValue != null);
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
/*     */   public boolean isValue(Object paramObject) {
/* 505 */     return OpenMBeanAttributeInfoSupport.isValue(this, paramObject);
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
/*     */   public boolean equals(Object paramObject) {
/* 544 */     if (!(paramObject instanceof OpenMBeanParameterInfo)) {
/* 545 */       return false;
/*     */     }
/* 547 */     OpenMBeanParameterInfo openMBeanParameterInfo = (OpenMBeanParameterInfo)paramObject;
/*     */     
/* 549 */     return OpenMBeanAttributeInfoSupport.equal(this, openMBeanParameterInfo);
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
/*     */   public int hashCode() {
/* 587 */     if (this.myHashCode == null) {
/* 588 */       this.myHashCode = Integer.valueOf(OpenMBeanAttributeInfoSupport.hashCode(this));
/*     */     }
/*     */ 
/*     */     
/* 592 */     return this.myHashCode.intValue();
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
/*     */   public String toString() {
/* 618 */     if (this.myToString == null) {
/* 619 */       this.myToString = OpenMBeanAttributeInfoSupport.toString(this);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 624 */     return this.myToString;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\management\openmbean\OpenMBeanParameterInfoSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */