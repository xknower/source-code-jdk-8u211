/*     */ package com.sun.org.apache.xpath.internal.axes;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.dtm.DTMAxisTraverser;
/*     */ import com.sun.org.apache.xpath.internal.VariableStack;
/*     */ import com.sun.org.apache.xpath.internal.XPathContext;
/*     */ import com.sun.org.apache.xpath.internal.compiler.Compiler;
/*     */ import com.sun.org.apache.xpath.internal.compiler.OpMap;
/*     */ import com.sun.org.apache.xpath.internal.objects.XObject;
/*     */ import com.sun.org.apache.xpath.internal.patterns.NodeTest;
/*     */ import com.sun.org.apache.xpath.internal.patterns.StepPattern;
/*     */ import javax.xml.transform.TransformerException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MatchPatternIterator
/*     */   extends LocPathIterator
/*     */ {
/*     */   static final long serialVersionUID = -5201153767396296474L;
/*     */   protected StepPattern m_pattern;
/*  52 */   protected int m_superAxis = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DTMAxisTraverser m_traverser;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final boolean DEBUG = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MatchPatternIterator(Compiler compiler, int opPos, int analysis) throws TransformerException {
/*  80 */     super(compiler, opPos, analysis, false);
/*     */     
/*  82 */     int firstStepPos = OpMap.getFirstChildPos(opPos);
/*     */     
/*  84 */     this.m_pattern = WalkerFactory.loadSteps(this, compiler, firstStepPos, 0);
/*     */     
/*  86 */     boolean fromRoot = false;
/*  87 */     boolean walkBack = false;
/*  88 */     boolean walkDescendants = false;
/*  89 */     boolean walkAttributes = false;
/*     */     
/*  91 */     if (0 != (analysis & 0x28000000))
/*     */     {
/*  93 */       fromRoot = true;
/*     */     }
/*  95 */     if (0 != (analysis & 0x5D86000))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 103 */       walkBack = true;
/*     */     }
/* 105 */     if (0 != (analysis & 0x70000))
/*     */     {
/*     */ 
/*     */       
/* 109 */       walkDescendants = true;
/*     */     }
/* 111 */     if (0 != (analysis & 0x208000))
/*     */     {
/* 113 */       walkAttributes = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 121 */     if (fromRoot || walkBack) {
/*     */       
/* 123 */       if (walkAttributes)
/*     */       {
/* 125 */         this.m_superAxis = 16;
/*     */       }
/*     */       else
/*     */       {
/* 129 */         this.m_superAxis = 17;
/*     */       }
/*     */     
/* 132 */     } else if (walkDescendants) {
/*     */       
/* 134 */       if (walkAttributes)
/*     */       {
/* 136 */         this.m_superAxis = 14;
/*     */       }
/*     */       else
/*     */       {
/* 140 */         this.m_superAxis = 5;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 145 */       this.m_superAxis = 16;
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
/*     */   public void setRoot(int context, Object environment) {
/* 164 */     super.setRoot(context, environment);
/* 165 */     this.m_traverser = this.m_cdtm.getAxisTraverser(this.m_superAxis);
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
/*     */   public void detach() {
/* 177 */     if (this.m_allowDetach) {
/*     */       
/* 179 */       this.m_traverser = null;
/*     */ 
/*     */       
/* 182 */       super.detach();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getNextNode() {
/* 192 */     this
/*     */       
/* 194 */       .m_lastFetched = (-1 == this.m_lastFetched) ? this.m_traverser.first(this.m_context) : this.m_traverser.next(this.m_context, this.m_lastFetched);
/* 195 */     return this.m_lastFetched;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int nextNode() {
/*     */     int next;
/*     */     VariableStack vars;
/*     */     int savedStart;
/* 207 */     if (this.m_foundLast) {
/* 208 */       return -1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 214 */     if (-1 != this.m_stackFrame) {
/*     */       
/* 216 */       vars = this.m_execContext.getVarStack();
/*     */ 
/*     */       
/* 219 */       savedStart = vars.getStackFrame();
/*     */       
/* 221 */       vars.setStackFrame(this.m_stackFrame);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 226 */       vars = null;
/* 227 */       savedStart = 0;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 237 */       next = getNextNode();
/*     */       
/* 239 */       if (-1 != next)
/*     */       
/* 241 */       { if (1 == acceptNode(next, this.m_execContext)) {
/*     */           break;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 249 */         if (next == -1)
/*     */           break;  continue; }  break;
/* 251 */     }  if (-1 != next)
/*     */     
/*     */     { 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 258 */       incrementCurrentPos();
/*     */       
/* 260 */       int i = next;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 271 */       if (-1 != this.m_stackFrame)
/*     */       {
/*     */         
/* 274 */         vars.setStackFrame(savedStart); }  return i; }  this.m_foundLast = true; byte b = -1; if (-1 != this.m_stackFrame) vars.setStackFrame(savedStart);
/*     */     
/*     */     return b;
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
/*     */   public short acceptNode(int n, XPathContext xctxt) {
/*     */     try {
/* 294 */       xctxt.pushCurrentNode(n);
/* 295 */       xctxt.pushIteratorRoot(this.m_context);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 306 */       XObject score = this.m_pattern.execute(xctxt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 316 */       return (score == NodeTest.SCORE_NONE) ? 3 : 1;
/*     */     
/*     */     }
/* 319 */     catch (TransformerException se) {
/*     */ 
/*     */ 
/*     */       
/* 323 */       throw new RuntimeException(se.getMessage());
/*     */     }
/*     */     finally {
/*     */       
/* 327 */       xctxt.popCurrentNode();
/* 328 */       xctxt.popIteratorRoot();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xpath\internal\axes\MatchPatternIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */