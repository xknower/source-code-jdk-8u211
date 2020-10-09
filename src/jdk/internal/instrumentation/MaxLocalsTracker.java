/*    */ package jdk.internal.instrumentation;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import jdk.internal.org.objectweb.asm.ClassVisitor;
/*    */ import jdk.internal.org.objectweb.asm.MethodVisitor;
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
/*    */ final class MaxLocalsTracker
/*    */   extends ClassVisitor
/*    */ {
/* 20 */   private final HashMap<String, Integer> maxLocalsMap = new HashMap<>();
/*    */   
/*    */   public MaxLocalsTracker() {
/* 23 */     super(327680);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MethodVisitor visitMethod(int paramInt, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
/* 29 */     return new MaxLocalsMethodVisitor(key(paramString1, paramString2));
/*    */   }
/*    */   
/*    */   public int getMaxLocals(String paramString1, String paramString2) {
/* 33 */     Integer integer = this.maxLocalsMap.get(key(paramString1, paramString2));
/* 34 */     if (integer == null) {
/* 35 */       throw new IllegalArgumentException("No maxLocals could be found for " + paramString1 + paramString2);
/*    */     }
/* 37 */     return integer.intValue();
/*    */   }
/*    */   
/*    */   private static String key(String paramString1, String paramString2) {
/* 41 */     return paramString1 + paramString2;
/*    */   }
/*    */   
/*    */   private final class MaxLocalsMethodVisitor
/*    */     extends MethodVisitor {
/*    */     private String key;
/*    */     
/*    */     public MaxLocalsMethodVisitor(String param1String) {
/* 49 */       super(327680);
/* 50 */       this.key = param1String;
/*    */     }
/*    */ 
/*    */     
/*    */     public void visitMaxs(int param1Int1, int param1Int2) {
/* 55 */       MaxLocalsTracker.this.maxLocalsMap.put(this.key, Integer.valueOf(param1Int2));
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\internal\instrumentation\MaxLocalsTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */