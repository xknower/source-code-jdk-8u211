/*    */ package jdk.internal.instrumentation;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.List;
/*    */ import jdk.internal.org.objectweb.asm.ClassReader;
/*    */ import jdk.internal.org.objectweb.asm.ClassVisitor;
/*    */ import jdk.internal.org.objectweb.asm.MethodVisitor;
/*    */ import jdk.internal.org.objectweb.asm.Type;
/*    */ import jdk.internal.org.objectweb.asm.tree.ClassNode;
/*    */ import jdk.internal.org.objectweb.asm.tree.MethodNode;
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
/*    */ final class Inliner
/*    */   extends ClassVisitor
/*    */ {
/*    */   private final String instrumentationClassName;
/*    */   private final Logger logger;
/*    */   private final ClassNode targetClassNode;
/*    */   private final List<Method> instrumentationMethods;
/*    */   private final MaxLocalsTracker maxLocalsTracker;
/*    */   
/*    */   Inliner(int paramInt, ClassVisitor paramClassVisitor, String paramString, ClassReader paramClassReader, List<Method> paramList, MaxLocalsTracker paramMaxLocalsTracker, Logger paramLogger) {
/* 35 */     super(paramInt, paramClassVisitor);
/* 36 */     this.instrumentationClassName = paramString;
/* 37 */     this.instrumentationMethods = paramList;
/* 38 */     this.maxLocalsTracker = paramMaxLocalsTracker;
/* 39 */     this.logger = paramLogger;
/*    */     
/* 41 */     ClassNode classNode = new ClassNode(327680);
/* 42 */     paramClassReader.accept(classNode, 8);
/* 43 */     this.targetClassNode = classNode;
/*    */   }
/*    */ 
/*    */   
/*    */   public MethodVisitor visitMethod(int paramInt, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
/* 48 */     MethodVisitor methodVisitor = super.visitMethod(paramInt, paramString1, paramString2, paramString3, paramArrayOfString);
/*    */     
/* 50 */     if (isInstrumentationMethod(paramString1, paramString2)) {
/* 51 */       MethodNode methodNode = findTargetMethodNode(paramString1, paramString2);
/* 52 */       if (methodNode == null) {
/* 53 */         throw new IllegalArgumentException("Could not find the method to instrument in the target class");
/*    */       }
/* 55 */       if ((methodNode.access & 0x100) == 1) {
/* 56 */         throw new IllegalArgumentException("Cannot instrument native methods: " + this.targetClassNode.name + "." + methodNode.name + methodNode.desc);
/*    */       }
/*    */       
/* 59 */       this.logger.trace("Inliner processing method " + paramString1 + paramString2);
/*    */       
/* 61 */       return new MethodCallInliner(paramInt, paramString2, methodVisitor, methodNode, this.instrumentationClassName, this.maxLocalsTracker
/*    */ 
/*    */ 
/*    */ 
/*    */           
/* 66 */           .getMaxLocals(paramString1, paramString2), this.logger);
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 71 */     return methodVisitor;
/*    */   }
/*    */   
/*    */   private boolean isInstrumentationMethod(String paramString1, String paramString2) {
/* 75 */     for (Method method : this.instrumentationMethods) {
/* 76 */       if (method.getName().equals(paramString1) && Type.getMethodDescriptor(method).equals(paramString2)) {
/* 77 */         return true;
/*    */       }
/*    */     } 
/* 80 */     return false;
/*    */   }
/*    */   
/*    */   private MethodNode findTargetMethodNode(String paramString1, String paramString2) {
/* 84 */     for (MethodNode methodNode : this.targetClassNode.methods) {
/* 85 */       if (methodNode.desc.equals(paramString2) && methodNode.name.equals(paramString1)) {
/* 86 */         return methodNode;
/*    */       }
/*    */     } 
/* 89 */     throw new IllegalArgumentException("could not find MethodNode for " + paramString1 + paramString2);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\internal\instrumentation\Inliner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */