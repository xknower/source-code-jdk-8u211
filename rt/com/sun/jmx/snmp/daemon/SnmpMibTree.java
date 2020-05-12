/*     */ package com.sun.jmx.snmp.daemon;
/*     */ 
/*     */ import com.sun.jmx.snmp.SnmpOid;
/*     */ import com.sun.jmx.snmp.agent.SnmpMibAgent;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SnmpMibTree
/*     */ {
/*  51 */   private SnmpMibAgent defaultAgent = null;
/*  52 */   private TreeNode root = new TreeNode(-1L, null, null);
/*     */ 
/*     */   
/*     */   public void setDefaultAgent(SnmpMibAgent paramSnmpMibAgent) {
/*  56 */     this.defaultAgent = paramSnmpMibAgent;
/*  57 */     this.root.agent = paramSnmpMibAgent;
/*     */   }
/*     */   
/*     */   public SnmpMibAgent getDefaultAgent() {
/*  61 */     return this.defaultAgent;
/*     */   }
/*     */   
/*     */   public void register(SnmpMibAgent paramSnmpMibAgent) {
/*  65 */     this.root.registerNode(paramSnmpMibAgent);
/*     */   }
/*     */   
/*     */   public void register(SnmpMibAgent paramSnmpMibAgent, long[] paramArrayOflong) {
/*  69 */     this.root.registerNode(paramArrayOflong, 0, paramSnmpMibAgent);
/*     */   }
/*     */   
/*     */   public SnmpMibAgent getAgentMib(SnmpOid paramSnmpOid) {
/*  73 */     TreeNode treeNode = this.root.retrieveMatchingBranch(paramSnmpOid.longValue(), 0);
/*  74 */     if (treeNode == null) {
/*  75 */       return this.defaultAgent;
/*     */     }
/*  77 */     if (treeNode.getAgentMib() == null) {
/*  78 */       return this.defaultAgent;
/*     */     }
/*  80 */     return treeNode.getAgentMib();
/*     */   }
/*     */   
/*     */   public void unregister(SnmpMibAgent paramSnmpMibAgent, SnmpOid[] paramArrayOfSnmpOid) {
/*  84 */     for (byte b = 0; b < paramArrayOfSnmpOid.length; b++) {
/*  85 */       long[] arrayOfLong = paramArrayOfSnmpOid[b].longValue();
/*  86 */       TreeNode treeNode = this.root.retrieveMatchingBranch(arrayOfLong, 0);
/*  87 */       if (treeNode != null)
/*     */       {
/*  89 */         treeNode.removeAgent(paramSnmpMibAgent);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void unregister(SnmpMibAgent paramSnmpMibAgent) {
/*  96 */     this.root.removeAgentFully(paramSnmpMibAgent);
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
/*     */   public void printTree() {
/* 109 */     this.root.printTree(">");
/*     */   }
/*     */   
/*     */   final class TreeNode {
/*     */     private Vector<TreeNode> children;
/*     */     private Vector<SnmpMibAgent> agents;
/*     */     private long nodeValue;
/*     */     private SnmpMibAgent agent;
/*     */     private TreeNode parent;
/*     */     
/*     */     void registerNode(SnmpMibAgent param1SnmpMibAgent) {
/* 120 */       long[] arrayOfLong = param1SnmpMibAgent.getRootOid();
/* 121 */       registerNode(arrayOfLong, 0, param1SnmpMibAgent);
/*     */     }
/*     */     
/*     */     TreeNode retrieveMatchingBranch(long[] param1ArrayOflong, int param1Int) {
/* 125 */       TreeNode treeNode1 = retrieveChild(param1ArrayOflong, param1Int);
/* 126 */       if (treeNode1 == null)
/* 127 */         return this; 
/* 128 */       if (this.children.isEmpty())
/*     */       {
/*     */         
/* 131 */         return treeNode1;
/*     */       }
/* 133 */       if (param1Int + 1 == param1ArrayOflong.length)
/*     */       {
/*     */         
/* 136 */         return treeNode1;
/*     */       }
/*     */       
/* 139 */       TreeNode treeNode2 = treeNode1.retrieveMatchingBranch(param1ArrayOflong, param1Int + 1);
/*     */ 
/*     */ 
/*     */       
/* 143 */       return (treeNode2.agent == null) ? this : treeNode2;
/*     */     }
/*     */     
/*     */     SnmpMibAgent getAgentMib() {
/* 147 */       return this.agent;
/*     */     }
/*     */ 
/*     */     
/*     */     public void printTree(String param1String) {
/* 152 */       StringBuilder stringBuilder = new StringBuilder();
/* 153 */       if (this.agents == null) {
/*     */         return;
/*     */       }
/*     */       Enumeration<SnmpMibAgent> enumeration;
/* 157 */       for (enumeration = this.agents.elements(); enumeration.hasMoreElements(); ) {
/* 158 */         SnmpMibAgent snmpMibAgent = enumeration.nextElement();
/* 159 */         if (snmpMibAgent == null) {
/* 160 */           stringBuilder.append("empty "); continue;
/*     */         } 
/* 162 */         stringBuilder.append(snmpMibAgent.getMibName()).append(" ");
/*     */       } 
/* 164 */       param1String = param1String + " ";
/* 165 */       if (this.children == null) {
/*     */         return;
/*     */       }
/* 168 */       for (enumeration = (Enumeration)this.children.elements(); enumeration.hasMoreElements(); ) {
/* 169 */         TreeNode treeNode = (TreeNode)enumeration.nextElement();
/* 170 */         treeNode.printTree(param1String);
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private TreeNode(long param1Long, SnmpMibAgent param1SnmpMibAgent, TreeNode param1TreeNode) {
/* 262 */       this.children = new Vector<>();
/* 263 */       this.agents = new Vector<>();
/*     */       this.nodeValue = param1Long;
/*     */       this.parent = param1TreeNode;
/*     */       this.agents.addElement(param1SnmpMibAgent);
/*     */     }
/*     */     
/*     */     private void removeAgentFully(SnmpMibAgent param1SnmpMibAgent) {
/*     */       Vector<TreeNode> vector = new Vector();
/*     */       Enumeration<TreeNode> enumeration = this.children.elements();
/*     */       while (enumeration.hasMoreElements()) {
/*     */         TreeNode treeNode = enumeration.nextElement();
/*     */         treeNode.removeAgentFully(param1SnmpMibAgent);
/*     */         if (treeNode.agents.isEmpty())
/*     */           vector.add(treeNode); 
/*     */       } 
/*     */       for (enumeration = vector.elements(); enumeration.hasMoreElements();)
/*     */         this.children.removeElement(enumeration.nextElement()); 
/*     */       removeAgent(param1SnmpMibAgent);
/*     */     }
/*     */     
/*     */     private void removeAgent(SnmpMibAgent param1SnmpMibAgent) {
/*     */       if (!this.agents.contains(param1SnmpMibAgent))
/*     */         return; 
/*     */       this.agents.removeElement(param1SnmpMibAgent);
/*     */       if (!this.agents.isEmpty())
/*     */         this.agent = this.agents.firstElement(); 
/*     */     }
/*     */     
/*     */     private void setAgent(SnmpMibAgent param1SnmpMibAgent) {
/*     */       this.agent = param1SnmpMibAgent;
/*     */     }
/*     */     
/*     */     private void registerNode(long[] param1ArrayOflong, int param1Int, SnmpMibAgent param1SnmpMibAgent) {
/*     */       if (param1Int >= param1ArrayOflong.length)
/*     */         return; 
/*     */       TreeNode treeNode = retrieveChild(param1ArrayOflong, param1Int);
/*     */       if (treeNode == null) {
/*     */         long l = param1ArrayOflong[param1Int];
/*     */         treeNode = new TreeNode(l, param1SnmpMibAgent, this);
/*     */         this.children.addElement(treeNode);
/*     */       } else if (!this.agents.contains(param1SnmpMibAgent)) {
/*     */         this.agents.addElement(param1SnmpMibAgent);
/*     */       } 
/*     */       if (param1Int == param1ArrayOflong.length - 1) {
/*     */         treeNode.setAgent(param1SnmpMibAgent);
/*     */       } else {
/*     */         treeNode.registerNode(param1ArrayOflong, param1Int + 1, param1SnmpMibAgent);
/*     */       } 
/*     */     }
/*     */     
/*     */     private TreeNode retrieveChild(long[] param1ArrayOflong, int param1Int) {
/*     */       long l = param1ArrayOflong[param1Int];
/*     */       for (Enumeration<TreeNode> enumeration = this.children.elements(); enumeration.hasMoreElements(); ) {
/*     */         TreeNode treeNode = enumeration.nextElement();
/*     */         if (treeNode.match(l))
/*     */           return treeNode; 
/*     */       } 
/*     */       return null;
/*     */     }
/*     */     
/*     */     private boolean match(long param1Long) {
/*     */       return (this.nodeValue == param1Long);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\daemon\SnmpMibTree.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */