/*     */ package jdk.internal.instrumentation;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import jdk.internal.org.objectweb.asm.ClassVisitor;
/*     */ import jdk.internal.org.objectweb.asm.Handle;
/*     */ import jdk.internal.org.objectweb.asm.MethodVisitor;
/*     */ import jdk.internal.org.objectweb.asm.Type;
/*     */ import jdk.internal.org.objectweb.asm.commons.RemappingMethodAdapter;
/*     */ import jdk.internal.org.objectweb.asm.commons.SimpleRemapper;
/*     */ import jdk.internal.org.objectweb.asm.tree.ClassNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.MethodNode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class MethodMergeAdapter
/*     */   extends ClassVisitor
/*     */ {
/*     */   private final ClassNode cn;
/*     */   private final List<Method> methodFilter;
/*     */   private final Map<String, String> typeMap;
/*     */   private final Logger logger;
/*     */   
/*     */   public MethodMergeAdapter(ClassVisitor paramClassVisitor, ClassNode paramClassNode, List<Method> paramList, TypeMapping[] paramArrayOfTypeMapping, Logger paramLogger) {
/*  47 */     super(327680, paramClassVisitor);
/*  48 */     this.cn = paramClassNode;
/*  49 */     this.methodFilter = paramList;
/*  50 */     this.logger = paramLogger;
/*     */     
/*  52 */     this.typeMap = new HashMap<>();
/*  53 */     for (TypeMapping typeMapping : paramArrayOfTypeMapping) {
/*  54 */       this.typeMap.put(typeMapping.from().replace('.', '/'), typeMapping.to().replace('.', '/'));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void visit(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
/*  60 */     super.visit(paramInt1, paramInt2, paramString1, paramString2, paramString3, paramArrayOfString);
/*  61 */     this.typeMap.put(this.cn.name, paramString1);
/*     */   }
/*     */ 
/*     */   
/*     */   public MethodVisitor visitMethod(int paramInt, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
/*  66 */     if (methodInFilter(paramString1, paramString2)) {
/*     */       
/*  68 */       this.logger.trace("Deleting " + paramString1 + paramString2);
/*  69 */       return null;
/*     */     } 
/*  71 */     return super.visitMethod(paramInt, paramString1, paramString2, paramString3, paramArrayOfString);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitEnd() {
/*  76 */     SimpleRemapper simpleRemapper = new SimpleRemapper(this.typeMap);
/*  77 */     LinkedList<MethodNode> linkedList = new LinkedList();
/*  78 */     for (MethodNode methodNode : this.cn.methods) {
/*     */       
/*  80 */       if (methodInFilter(methodNode.name, methodNode.desc)) {
/*  81 */         linkedList.add(methodNode);
/*     */       }
/*     */     } 
/*     */     
/*  85 */     while (!linkedList.isEmpty()) {
/*  86 */       MethodNode methodNode = linkedList.remove(0);
/*  87 */       this.logger.trace("Copying method: " + methodNode.name + methodNode.desc);
/*  88 */       this.logger.trace("   with mapper: " + this.typeMap);
/*     */       
/*  90 */       String[] arrayOfString = methodNode.exceptions.<String>toArray(new String[0]);
/*  91 */       MethodVisitor methodVisitor = this.cv.visitMethod(methodNode.access, methodNode.name, methodNode.desc, methodNode.signature, arrayOfString);
/*     */       
/*  93 */       methodNode.instructions.resetLabels();
/*  94 */       methodNode.accept(new RemappingMethodAdapter(methodNode.access, methodNode.desc, methodVisitor, simpleRemapper));
/*     */ 
/*     */       
/*  97 */       findMethodsReferencedByInvokeDynamic(methodNode, linkedList);
/*     */     } 
/*  99 */     super.visitEnd();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void findMethodsReferencedByInvokeDynamic(final MethodNode mn, final List<MethodNode> toCopy) {
/* 107 */     mn.accept(new MethodVisitor(327680)
/*     */         {
/*     */           public void visitInvokeDynamicInsn(String param1String1, String param1String2, Handle param1Handle, Object... param1VarArgs)
/*     */           {
/* 111 */             for (Object object : param1VarArgs) {
/* 112 */               if (object instanceof Handle) {
/* 113 */                 Handle handle = (Handle)object;
/* 114 */                 MethodNode methodNode = MethodMergeAdapter.findMethod(MethodMergeAdapter.this.cn, handle);
/*     */                 
/* 116 */                 if (methodNode == null) {
/* 117 */                   MethodMergeAdapter.this.logger.error("Could not find method " + handle
/* 118 */                       .getName() + handle.getDesc() + " referenced from an invokedynamic in " + mn.name + mn.desc + " while processing class " + 
/*     */ 
/*     */                       
/* 121 */                       MethodMergeAdapter.this.cn.name);
/*     */                 }
/* 123 */                 MethodMergeAdapter.this.logger.trace("Adding method referenced from invokedynamic " + methodNode.name + methodNode.desc + " to the list of methods to be copied from " + 
/*     */ 
/*     */ 
/*     */                     
/* 127 */                     MethodMergeAdapter.this.cn.name);
/* 128 */                 toCopy.add(methodNode);
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private static MethodNode findMethod(ClassNode paramClassNode, Handle paramHandle) {
/* 136 */     for (MethodNode methodNode : paramClassNode.methods) {
/* 137 */       if (methodNode.name.equals(paramHandle.getName()) && methodNode.desc.equals(paramHandle.getDesc())) {
/* 138 */         return methodNode;
/*     */       }
/*     */     } 
/* 141 */     return null;
/*     */   }
/*     */   
/*     */   private boolean methodInFilter(String paramString1, String paramString2) {
/* 145 */     for (Method method : this.methodFilter) {
/* 146 */       if (method.getName().equals(paramString1) && Type.getMethodDescriptor(method).equals(paramString2)) {
/* 147 */         return true;
/*     */       }
/*     */     } 
/* 150 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\internal\instrumentation\MethodMergeAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */