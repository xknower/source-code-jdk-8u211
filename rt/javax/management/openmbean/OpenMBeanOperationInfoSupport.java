/*     */ package javax.management.openmbean;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import javax.management.Descriptor;
/*     */ import javax.management.ImmutableDescriptor;
/*     */ import javax.management.MBeanOperationInfo;
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
/*     */ public class OpenMBeanOperationInfoSupport
/*     */   extends MBeanOperationInfo
/*     */   implements OpenMBeanOperationInfo
/*     */ {
/*     */   static final long serialVersionUID = 4996859732565369366L;
/*     */   private OpenType<?> returnOpenType;
/*  63 */   private transient Integer myHashCode = null;
/*  64 */   private transient String myToString = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenMBeanOperationInfoSupport(String paramString1, String paramString2, OpenMBeanParameterInfo[] paramArrayOfOpenMBeanParameterInfo, OpenType<?> paramOpenType, int paramInt) {
/* 104 */     this(paramString1, paramString2, paramArrayOfOpenMBeanParameterInfo, paramOpenType, paramInt, (Descriptor)null);
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
/*     */   public OpenMBeanOperationInfoSupport(String paramString1, String paramString2, OpenMBeanParameterInfo[] paramArrayOfOpenMBeanParameterInfo, OpenType<?> paramOpenType, int paramInt, Descriptor paramDescriptor) {
/* 151 */     super(paramString1, paramString2, 
/*     */         
/* 153 */         arrayCopyCast(paramArrayOfOpenMBeanParameterInfo), (paramOpenType == null) ? null : paramOpenType
/*     */ 
/*     */         
/* 156 */         .getClassName(), paramInt, 
/*     */         
/* 158 */         ImmutableDescriptor.union(new Descriptor[] {
/*     */ 
/*     */             
/* 161 */             paramDescriptor, (paramOpenType == null) ? null : paramOpenType.getDescriptor()
/*     */           }));
/*     */ 
/*     */ 
/*     */     
/* 166 */     if (paramString1 == null || paramString1.trim().equals("")) {
/* 167 */       throw new IllegalArgumentException("Argument name cannot be null or empty");
/*     */     }
/*     */     
/* 170 */     if (paramString2 == null || paramString2.trim().equals("")) {
/* 171 */       throw new IllegalArgumentException("Argument description cannot be null or empty");
/*     */     }
/*     */     
/* 174 */     if (paramOpenType == null) {
/* 175 */       throw new IllegalArgumentException("Argument returnOpenType cannot be null");
/*     */     }
/*     */ 
/*     */     
/* 179 */     if (paramInt != 1 && paramInt != 2 && paramInt != 0 && paramInt != 3)
/*     */     {
/* 181 */       throw new IllegalArgumentException("Argument impact can only be one of ACTION, ACTION_INFO, INFO, or UNKNOWN: " + paramInt);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 186 */     this.returnOpenType = paramOpenType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static MBeanParameterInfo[] arrayCopyCast(OpenMBeanParameterInfo[] paramArrayOfOpenMBeanParameterInfo) {
/* 195 */     if (paramArrayOfOpenMBeanParameterInfo == null) {
/* 196 */       return null;
/*     */     }
/* 198 */     MBeanParameterInfo[] arrayOfMBeanParameterInfo = new MBeanParameterInfo[paramArrayOfOpenMBeanParameterInfo.length];
/* 199 */     System.arraycopy(paramArrayOfOpenMBeanParameterInfo, 0, arrayOfMBeanParameterInfo, 0, paramArrayOfOpenMBeanParameterInfo.length);
/*     */     
/* 201 */     return arrayOfMBeanParameterInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static OpenMBeanParameterInfo[] arrayCopyCast(MBeanParameterInfo[] paramArrayOfMBeanParameterInfo) {
/* 209 */     if (paramArrayOfMBeanParameterInfo == null) {
/* 210 */       return null;
/*     */     }
/* 212 */     OpenMBeanParameterInfo[] arrayOfOpenMBeanParameterInfo = new OpenMBeanParameterInfo[paramArrayOfMBeanParameterInfo.length];
/* 213 */     System.arraycopy(paramArrayOfMBeanParameterInfo, 0, arrayOfOpenMBeanParameterInfo, 0, paramArrayOfMBeanParameterInfo.length);
/*     */     
/* 215 */     return arrayOfOpenMBeanParameterInfo;
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
/*     */   public OpenType<?> getReturnOpenType() {
/* 231 */     return this.returnOpenType;
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
/*     */     OpenMBeanOperationInfo openMBeanOperationInfo;
/* 271 */     if (paramObject == null) {
/* 272 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 279 */       openMBeanOperationInfo = (OpenMBeanOperationInfo)paramObject;
/* 280 */     } catch (ClassCastException classCastException) {
/* 281 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 289 */     if (!getName().equals(openMBeanOperationInfo.getName())) {
/* 290 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 294 */     if (!Arrays.equals((Object[])getSignature(), (Object[])openMBeanOperationInfo.getSignature())) {
/* 295 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 299 */     if (!getReturnOpenType().equals(openMBeanOperationInfo.getReturnOpenType())) {
/* 300 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 304 */     if (getImpact() != openMBeanOperationInfo.getImpact()) {
/* 305 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 310 */     return true;
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
/*     */   public int hashCode() {
/* 349 */     if (this.myHashCode == null) {
/* 350 */       int i = 0;
/* 351 */       i += getName().hashCode();
/* 352 */       i += Arrays.<MBeanParameterInfo>asList(getSignature()).hashCode();
/* 353 */       i += getReturnOpenType().hashCode();
/* 354 */       i += getImpact();
/* 355 */       this.myHashCode = Integer.valueOf(i);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 360 */     return this.myHashCode.intValue();
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
/* 386 */     if (this.myToString == null) {
/* 387 */       this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 400 */         .myToString = getClass().getName() + "(name=" + getName() + ",signature=" + Arrays.<MBeanParameterInfo>asList(getSignature()).toString() + ",return=" + getReturnOpenType().toString() + ",impact=" + getImpact() + ",descriptor=" + getDescriptor() + ")";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 406 */     return this.myToString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() {
/* 417 */     if ((getDescriptor().getFieldNames()).length == 0)
/*     */     {
/*     */       
/* 420 */       return new OpenMBeanOperationInfoSupport(this.name, this.description, 
/* 421 */           arrayCopyCast(getSignature()), this.returnOpenType, 
/* 422 */           getImpact());
/*     */     }
/* 424 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\management\openmbean\OpenMBeanOperationInfoSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */