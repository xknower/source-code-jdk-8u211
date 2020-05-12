/*    */ package com.sun.corba.se.impl.resolver;
/*    */ 
/*    */ import com.sun.corba.se.spi.orbutil.closure.Closure;
/*    */ import com.sun.corba.se.spi.resolver.LocalResolver;
/*    */ import com.sun.corba.se.spi.resolver.Resolver;
/*    */ import java.util.Set;
/*    */ import org.omg.CORBA.Object;
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
/*    */ public class SplitLocalResolverImpl
/*    */   implements LocalResolver
/*    */ {
/*    */   private Resolver resolver;
/*    */   private LocalResolver localResolver;
/*    */   
/*    */   public SplitLocalResolverImpl(Resolver paramResolver, LocalResolver paramLocalResolver) {
/* 41 */     this.resolver = paramResolver;
/* 42 */     this.localResolver = paramLocalResolver;
/*    */   }
/*    */ 
/*    */   
/*    */   public void register(String paramString, Closure paramClosure) {
/* 47 */     this.localResolver.register(paramString, paramClosure);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object resolve(String paramString) {
/* 52 */     return this.resolver.resolve(paramString);
/*    */   }
/*    */ 
/*    */   
/*    */   public Set list() {
/* 57 */     return this.resolver.list();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\resolver\SplitLocalResolverImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */