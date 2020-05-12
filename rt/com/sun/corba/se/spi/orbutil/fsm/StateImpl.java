/*    */ package com.sun.corba.se.spi.orbutil.fsm;
/*    */ 
/*    */ import com.sun.corba.se.impl.orbutil.fsm.GuardedAction;
/*    */ import com.sun.corba.se.impl.orbutil.fsm.NameBase;
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StateImpl
/*    */   extends NameBase
/*    */   implements State
/*    */ {
/*    */   private Action defaultAction;
/*    */   private State defaultNextState;
/*    */   private Map inputToGuardedActions;
/*    */   
/*    */   public StateImpl(String paramString) {
/* 48 */     super(paramString);
/* 49 */     this.defaultAction = null;
/* 50 */     this.inputToGuardedActions = new HashMap<>();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void preAction(FSM paramFSM) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void postAction(FSM paramFSM) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public State getDefaultNextState() {
/* 65 */     return this.defaultNextState;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setDefaultNextState(State paramState) {
/* 70 */     this.defaultNextState = paramState;
/*    */   }
/*    */ 
/*    */   
/*    */   public Action getDefaultAction() {
/* 75 */     return this.defaultAction;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setDefaultAction(Action paramAction) {
/* 80 */     this.defaultAction = paramAction;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addGuardedAction(Input paramInput, GuardedAction paramGuardedAction) {
/* 85 */     Set<GuardedAction> set = (Set)this.inputToGuardedActions.get(paramInput);
/* 86 */     if (set == null) {
/* 87 */       set = new HashSet();
/* 88 */       this.inputToGuardedActions.put(paramInput, set);
/*    */     } 
/*    */     
/* 91 */     set.add(paramGuardedAction);
/*    */   }
/*    */ 
/*    */   
/*    */   public Set getGuardedActions(Input paramInput) {
/* 96 */     return (Set)this.inputToGuardedActions.get(paramInput);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\orbutil\fsm\StateImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */