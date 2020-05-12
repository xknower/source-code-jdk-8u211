/*     */ package com.sun.xml.internal.ws.api.policy;
/*     */ 
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.server.Container;
/*     */ import com.sun.xml.internal.ws.policy.PolicyMap;
/*     */ import com.sun.xml.internal.ws.policy.PolicyMapMutator;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface PolicyResolver
/*     */ {
/*     */   PolicyMap resolve(ServerContext paramServerContext) throws WebServiceException;
/*     */   
/*     */   PolicyMap resolve(ClientContext paramClientContext) throws WebServiceException;
/*     */   
/*     */   public static class ServerContext
/*     */   {
/*     */     private final PolicyMap policyMap;
/*     */     private final Class endpointClass;
/*     */     private final Container container;
/*     */     private final boolean hasWsdl;
/*     */     private final Collection<PolicyMapMutator> mutators;
/*     */     
/*     */     public ServerContext(@Nullable PolicyMap policyMap, Container container, Class endpointClass, PolicyMapMutator... mutators) {
/*  94 */       this.policyMap = policyMap;
/*  95 */       this.endpointClass = endpointClass;
/*  96 */       this.container = container;
/*  97 */       this.hasWsdl = true;
/*  98 */       this.mutators = Arrays.asList(mutators);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ServerContext(@Nullable PolicyMap policyMap, Container container, Class endpointClass, boolean hasWsdl, PolicyMapMutator... mutators) {
/* 118 */       this.policyMap = policyMap;
/* 119 */       this.endpointClass = endpointClass;
/* 120 */       this.container = container;
/* 121 */       this.hasWsdl = hasWsdl;
/* 122 */       this.mutators = Arrays.asList(mutators);
/*     */     }
/*     */     @Nullable
/*     */     public PolicyMap getPolicyMap() {
/* 126 */       return this.policyMap;
/*     */     }
/*     */     @Nullable
/*     */     public Class getEndpointClass() {
/* 130 */       return this.endpointClass;
/*     */     }
/*     */     
/*     */     public Container getContainer() {
/* 134 */       return this.container;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasWsdl() {
/* 142 */       return this.hasWsdl;
/*     */     }
/*     */     
/*     */     public Collection<PolicyMapMutator> getMutators() {
/* 146 */       return this.mutators;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ClientContext
/*     */   {
/*     */     private PolicyMap policyMap;
/*     */ 
/*     */ 
/*     */     
/*     */     private Container container;
/*     */ 
/*     */ 
/*     */     
/*     */     public ClientContext(@Nullable PolicyMap policyMap, Container container) {
/* 164 */       this.policyMap = policyMap;
/* 165 */       this.container = container;
/*     */     }
/*     */     @Nullable
/*     */     public PolicyMap getPolicyMap() {
/* 169 */       return this.policyMap;
/*     */     }
/*     */     
/*     */     public Container getContainer() {
/* 173 */       return this.container;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\policy\PolicyResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */