/*    */ package sun.reflect;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Member;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConstantPool
/*    */ {
/*    */   private Object constantPoolOop;
/*    */   
/*    */   public int getSize() {
/* 36 */     return getSize0(this.constantPoolOop);
/* 37 */   } public Class<?> getClassAt(int paramInt) { return getClassAt0(this.constantPoolOop, paramInt); } public Class<?> getClassAtIfLoaded(int paramInt) {
/* 38 */     return getClassAtIfLoaded0(this.constantPoolOop, paramInt);
/*    */   }
/*    */   
/* 41 */   public Member getMethodAt(int paramInt) { return getMethodAt0(this.constantPoolOop, paramInt); }
/* 42 */   public Member getMethodAtIfLoaded(int paramInt) { return getMethodAtIfLoaded0(this.constantPoolOop, paramInt); }
/* 43 */   public Field getFieldAt(int paramInt) { return getFieldAt0(this.constantPoolOop, paramInt); } public Field getFieldAtIfLoaded(int paramInt) {
/* 44 */     return getFieldAtIfLoaded0(this.constantPoolOop, paramInt);
/*    */   }
/*    */   
/* 47 */   public String[] getMemberRefInfoAt(int paramInt) { return getMemberRefInfoAt0(this.constantPoolOop, paramInt); }
/* 48 */   public int getIntAt(int paramInt) { return getIntAt0(this.constantPoolOop, paramInt); }
/* 49 */   public long getLongAt(int paramInt) { return getLongAt0(this.constantPoolOop, paramInt); }
/* 50 */   public float getFloatAt(int paramInt) { return getFloatAt0(this.constantPoolOop, paramInt); }
/* 51 */   public double getDoubleAt(int paramInt) { return getDoubleAt0(this.constantPoolOop, paramInt); }
/* 52 */   public String getStringAt(int paramInt) { return getStringAt0(this.constantPoolOop, paramInt); } public String getUTF8At(int paramInt) {
/* 53 */     return getUTF8At0(this.constantPoolOop, paramInt);
/*    */   }
/*    */   private native int getSize0(Object paramObject);
/*    */   private native Class<?> getClassAt0(Object paramObject, int paramInt);
/*    */   private native Class<?> getClassAtIfLoaded0(Object paramObject, int paramInt);
/*    */   
/*    */   static {
/* 60 */     Reflection.registerFieldsToFilter(ConstantPool.class, new String[] { "constantPoolOop" });
/*    */   }
/*    */   
/*    */   private native Member getMethodAt0(Object paramObject, int paramInt);
/*    */   
/*    */   private native Member getMethodAtIfLoaded0(Object paramObject, int paramInt);
/*    */   
/*    */   private native Field getFieldAt0(Object paramObject, int paramInt);
/*    */   
/*    */   private native Field getFieldAtIfLoaded0(Object paramObject, int paramInt);
/*    */   
/*    */   private native String[] getMemberRefInfoAt0(Object paramObject, int paramInt);
/*    */   
/*    */   private native int getIntAt0(Object paramObject, int paramInt);
/*    */   
/*    */   private native long getLongAt0(Object paramObject, int paramInt);
/*    */   
/*    */   private native float getFloatAt0(Object paramObject, int paramInt);
/*    */   
/*    */   private native double getDoubleAt0(Object paramObject, int paramInt);
/*    */   
/*    */   private native String getStringAt0(Object paramObject, int paramInt);
/*    */   
/*    */   private native String getUTF8At0(Object paramObject, int paramInt);
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\reflect\ConstantPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */