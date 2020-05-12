/*      */ package com.sun.org.apache.xml.internal.dtm.ref;
/*      */ 
/*      */ import com.sun.org.apache.xml.internal.dtm.Axis;
/*      */ import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
/*      */ import com.sun.org.apache.xml.internal.dtm.DTMAxisTraverser;
/*      */ import com.sun.org.apache.xml.internal.dtm.DTMException;
/*      */ import com.sun.org.apache.xml.internal.dtm.DTMManager;
/*      */ import com.sun.org.apache.xml.internal.dtm.DTMWSFilter;
/*      */ import com.sun.org.apache.xml.internal.res.XMLMessages;
/*      */ import com.sun.org.apache.xml.internal.utils.NodeVector;
/*      */ import com.sun.org.apache.xml.internal.utils.XMLStringFactory;
/*      */ import javax.xml.transform.Source;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class DTMDefaultBaseIterators
/*      */   extends DTMDefaultBaseTraversers
/*      */ {
/*      */   public DTMDefaultBaseIterators(DTMManager mgr, Source source, int dtmIdentity, DTMWSFilter whiteSpaceFilter, XMLStringFactory xstringfactory, boolean doIndexing) {
/*   59 */     super(mgr, source, dtmIdentity, whiteSpaceFilter, xstringfactory, doIndexing);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DTMDefaultBaseIterators(DTMManager mgr, Source source, int dtmIdentity, DTMWSFilter whiteSpaceFilter, XMLStringFactory xstringfactory, boolean doIndexing, int blocksize, boolean usePrevsib, boolean newNameTable) {
/*   87 */     super(mgr, source, dtmIdentity, whiteSpaceFilter, xstringfactory, doIndexing, blocksize, usePrevsib, newNameTable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DTMAxisIterator getTypedAxisIterator(int axis, int type) {
/*  105 */     DTMAxisIterator iterator = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  121 */     switch (axis) {
/*      */       
/*      */       case 13:
/*  124 */         iterator = new TypedSingletonIterator(type);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  170 */         return iterator;case 3: iterator = new TypedChildrenIterator(type); return iterator;case 10: return (new ParentIterator()).setNodeType(type);case 0: return new TypedAncestorIterator(type);case 1: return (new TypedAncestorIterator(type)).includeSelf();case 2: return new TypedAttributeIterator(type);case 4: iterator = new TypedDescendantIterator(type); return iterator;case 5: iterator = (new TypedDescendantIterator(type)).includeSelf(); return iterator;case 6: iterator = new TypedFollowingIterator(type); return iterator;case 11: iterator = new TypedPrecedingIterator(type); return iterator;case 7: iterator = new TypedFollowingSiblingIterator(type); return iterator;case 12: iterator = new TypedPrecedingSiblingIterator(type); return iterator;case 9: iterator = new TypedNamespaceIterator(type); return iterator;case 19: iterator = new TypedRootIterator(type); return iterator;
/*      */     } 
/*      */     throw new DTMException(XMLMessages.createXMLMessage("ER_TYPED_ITERATOR_AXIS_NOT_IMPLEMENTED", new Object[] { Axis.getNames(axis) }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DTMAxisIterator getAxisIterator(int axis) {
/*  186 */     DTMAxisIterator iterator = null;
/*      */     
/*  188 */     switch (axis) {
/*      */       
/*      */       case 13:
/*  191 */         iterator = new SingletonIterator();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  236 */         return iterator;case 3: iterator = new ChildrenIterator(); return iterator;case 10: return new ParentIterator();case 0: return new AncestorIterator();case 1: return (new AncestorIterator()).includeSelf();case 2: return new AttributeIterator();case 4: iterator = new DescendantIterator(); return iterator;case 5: iterator = (new DescendantIterator()).includeSelf(); return iterator;case 6: iterator = new FollowingIterator(); return iterator;case 11: iterator = new PrecedingIterator(); return iterator;case 7: iterator = new FollowingSiblingIterator(); return iterator;case 12: iterator = new PrecedingSiblingIterator(); return iterator;case 9: iterator = new NamespaceIterator(); return iterator;case 19: iterator = new RootIterator(); return iterator;
/*      */     } 
/*      */     throw new DTMException(XMLMessages.createXMLMessage("ER_ITERATOR_AXIS_NOT_IMPLEMENTED", new Object[] { Axis.getNames(axis) }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract class InternalAxisIteratorBase
/*      */     extends DTMAxisIteratorBase
/*      */   {
/*      */     protected int _currentNode;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setMark() {
/*  272 */       this._markedNode = this._currentNode;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void gotoMark() {
/*  282 */       this._currentNode = this._markedNode;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final class ChildrenIterator
/*      */     extends InternalAxisIteratorBase
/*      */   {
/*      */     public DTMAxisIterator setStartNode(int node) {
/*  308 */       if (node == 0)
/*  309 */         node = DTMDefaultBaseIterators.this.getDocument(); 
/*  310 */       if (this._isRestartable) {
/*      */         
/*  312 */         this._startNode = node;
/*  313 */         this
/*  314 */           ._currentNode = (node == -1) ? -1 : DTMDefaultBaseIterators.this._firstch(DTMDefaultBaseIterators.this.makeNodeIdentity(node));
/*      */         
/*  316 */         return resetPosition();
/*      */       } 
/*      */       
/*  319 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() {
/*  330 */       if (this._currentNode != -1) {
/*  331 */         int node = this._currentNode;
/*  332 */         this._currentNode = DTMDefaultBaseIterators.this._nextsib(node);
/*  333 */         return returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(node));
/*      */       } 
/*      */       
/*  336 */       return -1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final class ParentIterator
/*      */     extends InternalAxisIteratorBase
/*      */   {
/*      */     private int _nodeType;
/*      */ 
/*      */     
/*      */     public ParentIterator() {
/*  349 */       this._nodeType = -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DTMAxisIterator setStartNode(int node) {
/*  362 */       if (node == 0)
/*  363 */         node = DTMDefaultBaseIterators.this.getDocument(); 
/*  364 */       if (this._isRestartable) {
/*      */         
/*  366 */         this._startNode = node;
/*  367 */         this._currentNode = DTMDefaultBaseIterators.this.getParent(node);
/*      */         
/*  369 */         return resetPosition();
/*      */       } 
/*      */       
/*  372 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DTMAxisIterator setNodeType(int type) {
/*  388 */       this._nodeType = type;
/*      */       
/*  390 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() {
/*  401 */       int result = this._currentNode;
/*      */       
/*  403 */       if (this._nodeType >= 14) {
/*  404 */         if (this._nodeType != DTMDefaultBaseIterators.this.getExpandedTypeID(this._currentNode)) {
/*  405 */           result = -1;
/*      */         }
/*  407 */       } else if (this._nodeType != -1 && 
/*  408 */         this._nodeType != DTMDefaultBaseIterators.this.getNodeType(this._currentNode)) {
/*  409 */         result = -1;
/*      */       } 
/*      */ 
/*      */       
/*  413 */       this._currentNode = -1;
/*      */       
/*  415 */       return returnNode(result);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final class TypedChildrenIterator
/*      */     extends InternalAxisIteratorBase
/*      */   {
/*      */     private final int _nodeType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TypedChildrenIterator(int nodeType) {
/*  439 */       this._nodeType = nodeType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DTMAxisIterator setStartNode(int node) {
/*  453 */       if (node == 0)
/*  454 */         node = DTMDefaultBaseIterators.this.getDocument(); 
/*  455 */       if (this._isRestartable) {
/*      */         
/*  457 */         this._startNode = node;
/*  458 */         this
/*      */           
/*  460 */           ._currentNode = (node == -1) ? -1 : DTMDefaultBaseIterators.this._firstch(DTMDefaultBaseIterators.this.makeNodeIdentity(this._startNode));
/*      */         
/*  462 */         return resetPosition();
/*      */       } 
/*      */       
/*  465 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() {
/*  476 */       int node = this._currentNode;
/*      */       
/*  478 */       int nodeType = this._nodeType;
/*      */       
/*  480 */       if (nodeType >= 14) {
/*  481 */         while (node != -1 && DTMDefaultBaseIterators.this._exptype(node) != nodeType) {
/*  482 */           node = DTMDefaultBaseIterators.this._nextsib(node);
/*      */         }
/*      */       } else {
/*  485 */         while (node != -1) {
/*  486 */           int eType = DTMDefaultBaseIterators.this._exptype(node);
/*  487 */           if ((eType < 14) ? (
/*  488 */             eType == nodeType) : (
/*      */ 
/*      */             
/*  491 */             DTMDefaultBaseIterators.this.m_expandedNameTable.getType(eType) == nodeType)) {
/*      */             break;
/*      */           }
/*  494 */           node = DTMDefaultBaseIterators.this._nextsib(node);
/*      */         } 
/*      */       } 
/*      */       
/*  498 */       if (node == -1) {
/*  499 */         this._currentNode = -1;
/*  500 */         return -1;
/*      */       } 
/*  502 */       this._currentNode = DTMDefaultBaseIterators.this._nextsib(node);
/*  503 */       return returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(node));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final class NamespaceChildrenIterator
/*      */     extends InternalAxisIteratorBase
/*      */   {
/*      */     private final int _nsType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public NamespaceChildrenIterator(int type) {
/*  530 */       this._nsType = type;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DTMAxisIterator setStartNode(int node) {
/*  544 */       if (node == 0)
/*  545 */         node = DTMDefaultBaseIterators.this.getDocument(); 
/*  546 */       if (this._isRestartable) {
/*      */         
/*  548 */         this._startNode = node;
/*  549 */         this._currentNode = (node == -1) ? -1 : -2;
/*      */         
/*  551 */         return resetPosition();
/*      */       } 
/*      */       
/*  554 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() {
/*  564 */       if (this._currentNode != -1) {
/*      */ 
/*      */         
/*  567 */         int node = (-2 == this._currentNode) ? DTMDefaultBaseIterators.this._firstch(DTMDefaultBaseIterators.this.makeNodeIdentity(this._startNode)) : DTMDefaultBaseIterators.this._nextsib(this._currentNode);
/*  568 */         for (; node != -1; 
/*  569 */           node = DTMDefaultBaseIterators.this._nextsib(node)) {
/*  570 */           if (DTMDefaultBaseIterators.this.m_expandedNameTable.getNamespaceID(DTMDefaultBaseIterators.this._exptype(node)) == this._nsType) {
/*  571 */             this._currentNode = node;
/*      */             
/*  573 */             return returnNode(node);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  578 */       return -1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class NamespaceIterator
/*      */     extends InternalAxisIteratorBase
/*      */   {
/*      */     public DTMAxisIterator setStartNode(int node) {
/*  610 */       if (node == 0)
/*  611 */         node = DTMDefaultBaseIterators.this.getDocument(); 
/*  612 */       if (this._isRestartable) {
/*      */         
/*  614 */         this._startNode = node;
/*  615 */         this._currentNode = DTMDefaultBaseIterators.this.getFirstNamespaceNode(node, true);
/*      */         
/*  617 */         return resetPosition();
/*      */       } 
/*      */       
/*  620 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() {
/*  631 */       int node = this._currentNode;
/*      */       
/*  633 */       if (-1 != node) {
/*  634 */         this._currentNode = DTMDefaultBaseIterators.this.getNextNamespaceNode(this._startNode, node, true);
/*      */       }
/*  636 */       return returnNode(node);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class TypedNamespaceIterator
/*      */     extends NamespaceIterator
/*      */   {
/*      */     private final int _nodeType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TypedNamespaceIterator(int nodeType) {
/*  659 */       this._nodeType = nodeType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() {
/*  671 */       int node = this._currentNode;
/*  672 */       for (; node != -1; 
/*  673 */         node = DTMDefaultBaseIterators.this.getNextNamespaceNode(this._startNode, node, true)) {
/*  674 */         if (DTMDefaultBaseIterators.this.getExpandedTypeID(node) == this._nodeType || DTMDefaultBaseIterators.this
/*  675 */           .getNodeType(node) == this._nodeType || DTMDefaultBaseIterators.this
/*  676 */           .getNamespaceType(node) == this._nodeType) {
/*  677 */           this._currentNode = node;
/*      */           
/*  679 */           return returnNode(node);
/*      */         } 
/*      */       } 
/*      */       
/*  683 */       return this._currentNode = -1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class RootIterator
/*      */     extends InternalAxisIteratorBase
/*      */   {
/*      */     public DTMAxisIterator setStartNode(int node) {
/*  715 */       if (this._isRestartable) {
/*      */         
/*  717 */         this._startNode = DTMDefaultBaseIterators.this.getDocumentRoot(node);
/*  718 */         this._currentNode = -1;
/*      */         
/*  720 */         return resetPosition();
/*      */       } 
/*      */       
/*  723 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() {
/*  733 */       if (this._startNode == this._currentNode) {
/*  734 */         return -1;
/*      */       }
/*  736 */       this._currentNode = this._startNode;
/*      */       
/*  738 */       return returnNode(this._startNode);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class TypedRootIterator
/*      */     extends RootIterator
/*      */   {
/*      */     private final int _nodeType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TypedRootIterator(int nodeType) {
/*  760 */       this._nodeType = nodeType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() {
/*  770 */       if (this._startNode == this._currentNode) {
/*  771 */         return -1;
/*      */       }
/*  773 */       int nodeType = this._nodeType;
/*  774 */       int node = this._startNode;
/*  775 */       int expType = DTMDefaultBaseIterators.this.getExpandedTypeID(node);
/*      */       
/*  777 */       this._currentNode = node;
/*      */       
/*  779 */       if (nodeType >= 14) {
/*  780 */         if (nodeType == expType) {
/*  781 */           return returnNode(node);
/*      */         }
/*      */       }
/*  784 */       else if (expType < 14) {
/*  785 */         if (expType == nodeType) {
/*  786 */           return returnNode(node);
/*      */         }
/*      */       }
/*  789 */       else if (DTMDefaultBaseIterators.this.m_expandedNameTable.getType(expType) == nodeType) {
/*  790 */         return returnNode(node);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  795 */       return -1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final class NamespaceAttributeIterator
/*      */     extends InternalAxisIteratorBase
/*      */   {
/*      */     private final int _nsType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public NamespaceAttributeIterator(int nsType) {
/*  820 */       this._nsType = nsType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DTMAxisIterator setStartNode(int node) {
/*  834 */       if (node == 0)
/*  835 */         node = DTMDefaultBaseIterators.this.getDocument(); 
/*  836 */       if (this._isRestartable) {
/*      */         
/*  838 */         this._startNode = node;
/*  839 */         this._currentNode = DTMDefaultBaseIterators.this.getFirstNamespaceNode(node, false);
/*      */         
/*  841 */         return resetPosition();
/*      */       } 
/*      */       
/*  844 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() {
/*  855 */       int node = this._currentNode;
/*      */       
/*  857 */       if (-1 != node) {
/*  858 */         this._currentNode = DTMDefaultBaseIterators.this.getNextNamespaceNode(this._startNode, node, false);
/*      */       }
/*  860 */       return returnNode(node);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class FollowingSiblingIterator
/*      */     extends InternalAxisIteratorBase
/*      */   {
/*      */     public DTMAxisIterator setStartNode(int node) {
/*  881 */       if (node == 0)
/*  882 */         node = DTMDefaultBaseIterators.this.getDocument(); 
/*  883 */       if (this._isRestartable) {
/*      */         
/*  885 */         this._startNode = node;
/*  886 */         this._currentNode = DTMDefaultBaseIterators.this.makeNodeIdentity(node);
/*      */         
/*  888 */         return resetPosition();
/*      */       } 
/*      */       
/*  891 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() {
/*  901 */       this
/*  902 */         ._currentNode = (this._currentNode == -1) ? -1 : DTMDefaultBaseIterators.this._nextsib(this._currentNode);
/*  903 */       return returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(this._currentNode));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final class TypedFollowingSiblingIterator
/*      */     extends FollowingSiblingIterator
/*      */   {
/*      */     private final int _nodeType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TypedFollowingSiblingIterator(int type) {
/*  925 */       this._nodeType = type;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() {
/*  935 */       if (this._currentNode == -1) {
/*  936 */         return -1;
/*      */       }
/*      */       
/*  939 */       int node = this._currentNode;
/*      */       
/*  941 */       int nodeType = this._nodeType;
/*      */       
/*  943 */       if (nodeType >= 14) {
/*      */         do {
/*  945 */           node = DTMDefaultBaseIterators.this._nextsib(node);
/*  946 */         } while (node != -1 && DTMDefaultBaseIterators.this._exptype(node) != nodeType);
/*      */       } else {
/*  948 */         while ((node = DTMDefaultBaseIterators.this._nextsib(node)) != -1) {
/*  949 */           int eType = DTMDefaultBaseIterators.this._exptype(node);
/*  950 */           if ((eType < 14) ? (
/*  951 */             eType == nodeType) : (
/*      */ 
/*      */             
/*  954 */             DTMDefaultBaseIterators.this.m_expandedNameTable.getType(eType) == nodeType)) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  960 */       this._currentNode = node;
/*      */       
/*  962 */       return (this._currentNode == -1) ? -1 : 
/*      */         
/*  964 */         returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(this._currentNode));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final class AttributeIterator
/*      */     extends InternalAxisIteratorBase
/*      */   {
/*      */     public DTMAxisIterator setStartNode(int node) {
/*  987 */       if (node == 0)
/*  988 */         node = DTMDefaultBaseIterators.this.getDocument(); 
/*  989 */       if (this._isRestartable) {
/*      */         
/*  991 */         this._startNode = node;
/*  992 */         this._currentNode = DTMDefaultBaseIterators.this.getFirstAttributeIdentity(DTMDefaultBaseIterators.this.makeNodeIdentity(node));
/*      */         
/*  994 */         return resetPosition();
/*      */       } 
/*      */       
/*  997 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() {
/* 1008 */       int node = this._currentNode;
/*      */       
/* 1010 */       if (node != -1) {
/* 1011 */         this._currentNode = DTMDefaultBaseIterators.this.getNextAttributeIdentity(node);
/* 1012 */         return returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(node));
/*      */       } 
/*      */       
/* 1015 */       return -1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final class TypedAttributeIterator
/*      */     extends InternalAxisIteratorBase
/*      */   {
/*      */     private final int _nodeType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TypedAttributeIterator(int nodeType) {
/* 1036 */       this._nodeType = nodeType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DTMAxisIterator setStartNode(int node) {
/* 1051 */       if (this._isRestartable) {
/*      */         
/* 1053 */         this._startNode = node;
/*      */         
/* 1055 */         this._currentNode = DTMDefaultBaseIterators.this.getTypedAttribute(node, this._nodeType);
/*      */         
/* 1057 */         return resetPosition();
/*      */       } 
/*      */       
/* 1060 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() {
/* 1071 */       int node = this._currentNode;
/*      */ 
/*      */ 
/*      */       
/* 1075 */       this._currentNode = -1;
/*      */       
/* 1077 */       return returnNode(node);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class PrecedingSiblingIterator
/*      */     extends InternalAxisIteratorBase
/*      */   {
/*      */     protected int _startNodeID;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isReverse() {
/* 1099 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DTMAxisIterator setStartNode(int node) {
/* 1113 */       if (node == 0)
/* 1114 */         node = DTMDefaultBaseIterators.this.getDocument(); 
/* 1115 */       if (this._isRestartable) {
/*      */         
/* 1117 */         this._startNode = node;
/* 1118 */         node = this._startNodeID = DTMDefaultBaseIterators.this.makeNodeIdentity(node);
/*      */         
/* 1120 */         if (node == -1) {
/*      */           
/* 1122 */           this._currentNode = node;
/* 1123 */           return resetPosition();
/*      */         } 
/*      */         
/* 1126 */         int type = DTMDefaultBaseIterators.this.m_expandedNameTable.getType(DTMDefaultBaseIterators.this._exptype(node));
/* 1127 */         if (2 == type || 13 == type) {
/*      */ 
/*      */           
/* 1130 */           this._currentNode = node;
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1135 */           this._currentNode = DTMDefaultBaseIterators.this._parent(node);
/* 1136 */           if (-1 != this._currentNode) {
/* 1137 */             this._currentNode = DTMDefaultBaseIterators.this._firstch(this._currentNode);
/*      */           } else {
/* 1139 */             this._currentNode = node;
/*      */           } 
/*      */         } 
/* 1142 */         return resetPosition();
/*      */       } 
/*      */       
/* 1145 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() {
/* 1156 */       if (this._currentNode == this._startNodeID || this._currentNode == -1)
/*      */       {
/* 1158 */         return -1;
/*      */       }
/*      */ 
/*      */       
/* 1162 */       int node = this._currentNode;
/* 1163 */       this._currentNode = DTMDefaultBaseIterators.this._nextsib(node);
/*      */       
/* 1165 */       return returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(node));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final class TypedPrecedingSiblingIterator
/*      */     extends PrecedingSiblingIterator
/*      */   {
/*      */     private final int _nodeType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TypedPrecedingSiblingIterator(int type) {
/* 1189 */       this._nodeType = type;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() {
/* 1199 */       int node = this._currentNode;
/*      */ 
/*      */       
/* 1202 */       int nodeType = this._nodeType;
/* 1203 */       int startID = this._startNodeID;
/*      */       
/* 1205 */       if (nodeType >= 14) {
/* 1206 */         while (node != -1 && node != startID && DTMDefaultBaseIterators.this._exptype(node) != nodeType) {
/* 1207 */           node = DTMDefaultBaseIterators.this._nextsib(node);
/*      */         }
/*      */       } else {
/* 1210 */         while (node != -1 && node != startID) {
/* 1211 */           int expType = DTMDefaultBaseIterators.this._exptype(node);
/* 1212 */           if ((expType < 14) ? (
/* 1213 */             expType == nodeType) : (
/*      */ 
/*      */ 
/*      */             
/* 1217 */             DTMDefaultBaseIterators.this.m_expandedNameTable.getType(expType) == nodeType)) {
/*      */             break;
/*      */           }
/*      */           
/* 1221 */           node = DTMDefaultBaseIterators.this._nextsib(node);
/*      */         } 
/*      */       } 
/*      */       
/* 1225 */       if (node == -1 || node == this._startNodeID) {
/* 1226 */         this._currentNode = -1;
/* 1227 */         return -1;
/*      */       } 
/* 1229 */       this._currentNode = DTMDefaultBaseIterators.this._nextsib(node);
/* 1230 */       return returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(node));
/*      */     }
/*      */   }
/*      */   
/*      */   public class PrecedingIterator extends InternalAxisIteratorBase {
/*      */     private final int _maxAncestors = 8;
/*      */     protected int[] _stack;
/*      */     protected int _sp;
/*      */     protected int _oldsp;
/*      */     protected int _markedsp;
/*      */     protected int _markedNode;
/*      */     protected int _markedDescendant;
/*      */     
/*      */     public PrecedingIterator() {
/* 1244 */       this._maxAncestors = 8;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1250 */       this._stack = new int[8];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isReverse() {
/* 1266 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DTMAxisIterator cloneIterator() {
/* 1276 */       this._isRestartable = false;
/*      */ 
/*      */       
/*      */       try {
/* 1280 */         PrecedingIterator clone = (PrecedingIterator)clone();
/* 1281 */         int[] stackCopy = new int[this._stack.length];
/* 1282 */         System.arraycopy(this._stack, 0, stackCopy, 0, this._stack.length);
/*      */         
/* 1284 */         clone._stack = stackCopy;
/*      */ 
/*      */         
/* 1287 */         return clone;
/*      */       }
/* 1289 */       catch (CloneNotSupportedException e) {
/*      */         
/* 1291 */         throw new DTMException(XMLMessages.createXMLMessage("ER_ITERATOR_CLONE_NOT_SUPPORTED", null));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DTMAxisIterator setStartNode(int node) {
/* 1306 */       if (node == 0)
/* 1307 */         node = DTMDefaultBaseIterators.this.getDocument(); 
/* 1308 */       if (this._isRestartable) {
/*      */         
/* 1310 */         node = DTMDefaultBaseIterators.this.makeNodeIdentity(node);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1315 */         if (DTMDefaultBaseIterators.this._type(node) == 2) {
/* 1316 */           node = DTMDefaultBaseIterators.this._parent(node);
/*      */         }
/* 1318 */         this._startNode = node; int index;
/* 1319 */         this._stack[index = 0] = node;
/*      */ 
/*      */ 
/*      */         
/* 1323 */         int parent = node;
/* 1324 */         while ((parent = DTMDefaultBaseIterators.this._parent(parent)) != -1) {
/*      */           
/* 1326 */           if (++index == this._stack.length) {
/*      */             
/* 1328 */             int[] stack = new int[index + 4];
/* 1329 */             System.arraycopy(this._stack, 0, stack, 0, index);
/* 1330 */             this._stack = stack;
/*      */           } 
/* 1332 */           this._stack[index] = parent;
/*      */         } 
/* 1334 */         if (index > 0) {
/* 1335 */           index--;
/*      */         }
/* 1337 */         this._currentNode = this._stack[index];
/*      */         
/* 1339 */         this._oldsp = this._sp = index;
/*      */         
/* 1341 */         return resetPosition();
/*      */       } 
/*      */       
/* 1344 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() {
/* 1357 */       this._currentNode++;
/* 1358 */       for (; this._sp >= 0; 
/* 1359 */         this._currentNode++) {
/*      */         
/* 1361 */         if (this._currentNode < this._stack[this._sp]) {
/*      */           
/* 1363 */           if (DTMDefaultBaseIterators.this._type(this._currentNode) != 2 && DTMDefaultBaseIterators.this
/* 1364 */             ._type(this._currentNode) != 13) {
/* 1365 */             return returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(this._currentNode));
/*      */           }
/*      */         } else {
/* 1368 */           this._sp--;
/*      */         } 
/* 1370 */       }  return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DTMAxisIterator reset() {
/* 1384 */       this._sp = this._oldsp;
/*      */       
/* 1386 */       return resetPosition();
/*      */     }
/*      */     
/*      */     public void setMark() {
/* 1390 */       this._markedsp = this._sp;
/* 1391 */       this._markedNode = this._currentNode;
/* 1392 */       this._markedDescendant = this._stack[0];
/*      */     }
/*      */     
/*      */     public void gotoMark() {
/* 1396 */       this._sp = this._markedsp;
/* 1397 */       this._currentNode = this._markedNode;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final class TypedPrecedingIterator
/*      */     extends PrecedingIterator
/*      */   {
/*      */     private final int _nodeType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TypedPrecedingIterator(int type) {
/* 1420 */       this._nodeType = type;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() {
/* 1430 */       int node = this._currentNode;
/* 1431 */       int nodeType = this._nodeType;
/*      */       
/* 1433 */       if (nodeType >= 14) {
/*      */         while (true) {
/* 1435 */           node++;
/*      */           
/* 1437 */           if (this._sp < 0) {
/* 1438 */             node = -1; break;
/*      */           } 
/* 1440 */           if (node >= this._stack[this._sp]) {
/* 1441 */             if (--this._sp < 0) {
/* 1442 */               node = -1; break;
/*      */             }  continue;
/*      */           } 
/* 1445 */           if (DTMDefaultBaseIterators.this._exptype(node) == nodeType) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */       } else {
/*      */         
/*      */         while (true) {
/*      */           
/* 1453 */           node++;
/*      */           
/* 1455 */           if (this._sp < 0) {
/* 1456 */             node = -1; break;
/*      */           } 
/* 1458 */           if (node >= this._stack[this._sp]) {
/* 1459 */             if (--this._sp < 0) {
/* 1460 */               node = -1; break;
/*      */             } 
/*      */             continue;
/*      */           } 
/* 1464 */           int expType = DTMDefaultBaseIterators.this._exptype(node);
/* 1465 */           if ((expType < 14) ? (
/* 1466 */             expType == nodeType) : (
/*      */ 
/*      */ 
/*      */             
/* 1470 */             DTMDefaultBaseIterators.this.m_expandedNameTable.getType(expType) == nodeType)) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1478 */       this._currentNode = node;
/*      */       
/* 1480 */       return (node == -1) ? -1 : returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(node));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public class FollowingIterator
/*      */     extends InternalAxisIteratorBase
/*      */   {
/*      */     DTMAxisTraverser m_traverser;
/*      */ 
/*      */     
/*      */     public FollowingIterator() {
/* 1493 */       this.m_traverser = DTMDefaultBaseIterators.this.getAxisTraverser(6);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DTMAxisIterator setStartNode(int node) {
/* 1507 */       if (node == 0)
/* 1508 */         node = DTMDefaultBaseIterators.this.getDocument(); 
/* 1509 */       if (this._isRestartable) {
/*      */         
/* 1511 */         this._startNode = node;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1518 */         this._currentNode = this.m_traverser.first(node);
/*      */ 
/*      */         
/* 1521 */         return resetPosition();
/*      */       } 
/*      */       
/* 1524 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() {
/* 1535 */       int node = this._currentNode;
/*      */       
/* 1537 */       this._currentNode = this.m_traverser.next(this._startNode, this._currentNode);
/*      */       
/* 1539 */       return returnNode(node);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final class TypedFollowingIterator
/*      */     extends FollowingIterator
/*      */   {
/*      */     private final int _nodeType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TypedFollowingIterator(int type) {
/* 1560 */       this._nodeType = type;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() {
/*      */       int node;
/*      */       do {
/* 1574 */         node = this._currentNode;
/*      */         
/* 1576 */         this._currentNode = this.m_traverser.next(this._startNode, this._currentNode);
/*      */       
/*      */       }
/* 1579 */       while (node != -1 && DTMDefaultBaseIterators.this
/* 1580 */         .getExpandedTypeID(node) != this._nodeType && DTMDefaultBaseIterators.this.getNodeType(node) != this._nodeType);
/*      */       
/* 1582 */       return (node == -1) ? -1 : returnNode(node);
/*      */     } }
/*      */   
/*      */   public class AncestorIterator extends InternalAxisIteratorBase {
/*      */     NodeVector m_ancestors;
/*      */     int m_ancestorsPos;
/*      */     int m_markedPos;
/*      */     int m_realStartNode;
/*      */     
/*      */     public AncestorIterator() {
/* 1592 */       this.m_ancestors = new NodeVector();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getStartNode() {
/* 1610 */       return this.m_realStartNode;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final boolean isReverse() {
/* 1620 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DTMAxisIterator cloneIterator() {
/* 1630 */       this._isRestartable = false;
/*      */ 
/*      */       
/*      */       try {
/* 1634 */         AncestorIterator clone = (AncestorIterator)clone();
/*      */         
/* 1636 */         clone._startNode = this._startNode;
/*      */ 
/*      */         
/* 1639 */         return clone;
/*      */       }
/* 1641 */       catch (CloneNotSupportedException e) {
/*      */         
/* 1643 */         throw new DTMException(XMLMessages.createXMLMessage("ER_ITERATOR_CLONE_NOT_SUPPORTED", null));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DTMAxisIterator setStartNode(int node) {
/* 1658 */       if (node == 0)
/* 1659 */         node = DTMDefaultBaseIterators.this.getDocument(); 
/* 1660 */       this.m_realStartNode = node;
/*      */       
/* 1662 */       if (this._isRestartable) {
/*      */         
/* 1664 */         int nodeID = DTMDefaultBaseIterators.this.makeNodeIdentity(node);
/*      */         
/* 1666 */         if (!this._includeSelf && node != -1) {
/* 1667 */           nodeID = DTMDefaultBaseIterators.this._parent(nodeID);
/* 1668 */           node = DTMDefaultBaseIterators.this.makeNodeHandle(nodeID);
/*      */         } 
/*      */         
/* 1671 */         this._startNode = node;
/*      */         
/* 1673 */         while (nodeID != -1) {
/* 1674 */           this.m_ancestors.addElement(node);
/* 1675 */           nodeID = DTMDefaultBaseIterators.this._parent(nodeID);
/* 1676 */           node = DTMDefaultBaseIterators.this.makeNodeHandle(nodeID);
/*      */         } 
/* 1678 */         this.m_ancestorsPos = this.m_ancestors.size() - 1;
/*      */         
/* 1680 */         this
/* 1681 */           ._currentNode = (this.m_ancestorsPos >= 0) ? this.m_ancestors.elementAt(this.m_ancestorsPos) : -1;
/*      */ 
/*      */         
/* 1684 */         return resetPosition();
/*      */       } 
/*      */       
/* 1687 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DTMAxisIterator reset() {
/* 1699 */       this.m_ancestorsPos = this.m_ancestors.size() - 1;
/*      */       
/* 1701 */       this._currentNode = (this.m_ancestorsPos >= 0) ? this.m_ancestors.elementAt(this.m_ancestorsPos) : -1;
/*      */ 
/*      */       
/* 1704 */       return resetPosition();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() {
/* 1715 */       int next = this._currentNode;
/*      */       
/* 1717 */       int pos = --this.m_ancestorsPos;
/*      */       
/* 1719 */       this._currentNode = (pos >= 0) ? this.m_ancestors.elementAt(this.m_ancestorsPos) : -1;
/*      */ 
/*      */       
/* 1722 */       return returnNode(next);
/*      */     }
/*      */     
/*      */     public void setMark() {
/* 1726 */       this.m_markedPos = this.m_ancestorsPos;
/*      */     }
/*      */     
/*      */     public void gotoMark() {
/* 1730 */       this.m_ancestorsPos = this.m_markedPos;
/* 1731 */       this._currentNode = (this.m_ancestorsPos >= 0) ? this.m_ancestors.elementAt(this.m_ancestorsPos) : -1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final class TypedAncestorIterator
/*      */     extends AncestorIterator
/*      */   {
/*      */     private final int _nodeType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TypedAncestorIterator(int type) {
/* 1753 */       this._nodeType = type;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DTMAxisIterator setStartNode(int node) {
/* 1767 */       if (node == 0)
/* 1768 */         node = DTMDefaultBaseIterators.this.getDocument(); 
/* 1769 */       this.m_realStartNode = node;
/*      */       
/* 1771 */       if (this._isRestartable) {
/*      */         
/* 1773 */         int nodeID = DTMDefaultBaseIterators.this.makeNodeIdentity(node);
/* 1774 */         int nodeType = this._nodeType;
/*      */         
/* 1776 */         if (!this._includeSelf && node != -1) {
/* 1777 */           nodeID = DTMDefaultBaseIterators.this._parent(nodeID);
/*      */         }
/*      */         
/* 1780 */         this._startNode = node;
/*      */         
/* 1782 */         if (nodeType >= 14) {
/* 1783 */           while (nodeID != -1) {
/* 1784 */             int eType = DTMDefaultBaseIterators.this._exptype(nodeID);
/*      */             
/* 1786 */             if (eType == nodeType) {
/* 1787 */               this.m_ancestors.addElement(DTMDefaultBaseIterators.this.makeNodeHandle(nodeID));
/*      */             }
/* 1789 */             nodeID = DTMDefaultBaseIterators.this._parent(nodeID);
/*      */           } 
/*      */         } else {
/* 1792 */           while (nodeID != -1) {
/* 1793 */             int eType = DTMDefaultBaseIterators.this._exptype(nodeID);
/*      */             
/* 1795 */             if ((eType >= 14 && DTMDefaultBaseIterators.this.m_expandedNameTable
/* 1796 */               .getType(eType) == nodeType) || (eType < 14 && eType == nodeType))
/*      */             {
/* 1798 */               this.m_ancestors.addElement(DTMDefaultBaseIterators.this.makeNodeHandle(nodeID));
/*      */             }
/* 1800 */             nodeID = DTMDefaultBaseIterators.this._parent(nodeID);
/*      */           } 
/*      */         } 
/* 1803 */         this.m_ancestorsPos = this.m_ancestors.size() - 1;
/*      */         
/* 1805 */         this
/* 1806 */           ._currentNode = (this.m_ancestorsPos >= 0) ? this.m_ancestors.elementAt(this.m_ancestorsPos) : -1;
/*      */ 
/*      */         
/* 1809 */         return resetPosition();
/*      */       } 
/*      */       
/* 1812 */       return this;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class DescendantIterator
/*      */     extends InternalAxisIteratorBase
/*      */   {
/*      */     public DTMAxisIterator setStartNode(int node) {
/* 1833 */       if (node == 0)
/* 1834 */         node = DTMDefaultBaseIterators.this.getDocument(); 
/* 1835 */       if (this._isRestartable) {
/*      */         
/* 1837 */         node = DTMDefaultBaseIterators.this.makeNodeIdentity(node);
/* 1838 */         this._startNode = node;
/*      */         
/* 1840 */         if (this._includeSelf) {
/* 1841 */           node--;
/*      */         }
/* 1843 */         this._currentNode = node;
/*      */         
/* 1845 */         return resetPosition();
/*      */       } 
/*      */       
/* 1848 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean isDescendant(int identity) {
/* 1867 */       return (DTMDefaultBaseIterators.this._parent(identity) >= this._startNode || this._startNode == identity);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() {
/*      */       int type;
/* 1877 */       if (this._startNode == -1) {
/* 1878 */         return -1;
/*      */       }
/*      */       
/* 1881 */       if (this._includeSelf && this._currentNode + 1 == this._startNode) {
/* 1882 */         return returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(++this._currentNode));
/*      */       }
/* 1884 */       int node = this._currentNode;
/*      */ 
/*      */       
/*      */       do {
/* 1888 */         node++;
/* 1889 */         type = DTMDefaultBaseIterators.this._type(node);
/*      */         
/* 1891 */         if (-1 == type || !isDescendant(node)) {
/* 1892 */           this._currentNode = -1;
/* 1893 */           return -1;
/*      */         } 
/* 1895 */       } while (2 == type || 3 == type || 13 == type);
/*      */ 
/*      */       
/* 1898 */       this._currentNode = node;
/* 1899 */       return returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(node));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DTMAxisIterator reset() {
/* 1909 */       boolean temp = this._isRestartable;
/*      */       
/* 1911 */       this._isRestartable = true;
/*      */       
/* 1913 */       setStartNode(DTMDefaultBaseIterators.this.makeNodeHandle(this._startNode));
/*      */       
/* 1915 */       this._isRestartable = temp;
/*      */       
/* 1917 */       return this;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final class TypedDescendantIterator
/*      */     extends DescendantIterator
/*      */   {
/*      */     private final int _nodeType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TypedDescendantIterator(int nodeType) {
/* 1939 */       this._nodeType = nodeType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() {
/*      */       int type;
/* 1952 */       if (this._startNode == -1) {
/* 1953 */         return -1;
/*      */       }
/*      */       
/* 1956 */       int node = this._currentNode;
/*      */ 
/*      */       
/*      */       do {
/* 1960 */         node++;
/* 1961 */         type = DTMDefaultBaseIterators.this._type(node);
/*      */         
/* 1963 */         if (-1 == type || !isDescendant(node)) {
/* 1964 */           this._currentNode = -1;
/* 1965 */           return -1;
/*      */         }
/*      */       
/* 1968 */       } while (type != this._nodeType && DTMDefaultBaseIterators.this._exptype(node) != this._nodeType);
/*      */       
/* 1970 */       this._currentNode = node;
/* 1971 */       return returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(node));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class NthDescendantIterator
/*      */     extends DescendantIterator
/*      */   {
/*      */     int _pos;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public NthDescendantIterator(int pos) {
/* 1993 */       this._pos = pos;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() {
/*      */       int node;
/* 2007 */       while ((node = super.next()) != -1) {
/*      */         
/* 2009 */         node = DTMDefaultBaseIterators.this.makeNodeIdentity(node);
/*      */         
/* 2011 */         int parent = DTMDefaultBaseIterators.this._parent(node);
/* 2012 */         int child = DTMDefaultBaseIterators.this._firstch(parent);
/* 2013 */         int pos = 0;
/*      */ 
/*      */         
/*      */         do {
/* 2017 */           int type = DTMDefaultBaseIterators.this._type(child);
/*      */           
/* 2019 */           if (1 != type)
/* 2020 */             continue;  pos++;
/*      */         }
/* 2022 */         while (pos < this._pos && (child = DTMDefaultBaseIterators.this._nextsib(child)) != -1);
/*      */         
/* 2024 */         if (node == child) {
/* 2025 */           return node;
/*      */         }
/*      */       } 
/* 2028 */       return -1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class SingletonIterator
/*      */     extends InternalAxisIteratorBase
/*      */   {
/*      */     private boolean _isConstant;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SingletonIterator() {
/* 2047 */       this(-2147483648, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SingletonIterator(int node) {
/* 2058 */       this(node, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SingletonIterator(int node, boolean constant) {
/* 2070 */       this._currentNode = this._startNode = node;
/* 2071 */       this._isConstant = constant;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DTMAxisIterator setStartNode(int node) {
/* 2085 */       if (node == 0)
/* 2086 */         node = DTMDefaultBaseIterators.this.getDocument(); 
/* 2087 */       if (this._isConstant) {
/*      */         
/* 2089 */         this._currentNode = this._startNode;
/*      */         
/* 2091 */         return resetPosition();
/*      */       } 
/* 2093 */       if (this._isRestartable) {
/*      */         
/* 2095 */         this._currentNode = this._startNode = node;
/*      */         
/* 2097 */         return resetPosition();
/*      */       } 
/*      */       
/* 2100 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DTMAxisIterator reset() {
/* 2112 */       if (this._isConstant) {
/*      */         
/* 2114 */         this._currentNode = this._startNode;
/*      */         
/* 2116 */         return resetPosition();
/*      */       } 
/*      */ 
/*      */       
/* 2120 */       boolean temp = this._isRestartable;
/*      */       
/* 2122 */       this._isRestartable = true;
/*      */       
/* 2124 */       setStartNode(this._startNode);
/*      */       
/* 2126 */       this._isRestartable = temp;
/*      */ 
/*      */       
/* 2129 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() {
/* 2140 */       int result = this._currentNode;
/*      */       
/* 2142 */       this._currentNode = -1;
/*      */       
/* 2144 */       return returnNode(result);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final class TypedSingletonIterator
/*      */     extends SingletonIterator
/*      */   {
/*      */     private final int _nodeType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TypedSingletonIterator(int nodeType) {
/* 2165 */       this._nodeType = nodeType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() {
/* 2177 */       int result = this._currentNode;
/* 2178 */       int nodeType = this._nodeType;
/*      */       
/* 2180 */       this._currentNode = -1;
/*      */       
/* 2182 */       if (nodeType >= 14) {
/* 2183 */         if (DTMDefaultBaseIterators.this.getExpandedTypeID(result) == nodeType) {
/* 2184 */           return returnNode(result);
/*      */         }
/*      */       }
/* 2187 */       else if (DTMDefaultBaseIterators.this.getNodeType(result) == nodeType) {
/* 2188 */         return returnNode(result);
/*      */       } 
/*      */ 
/*      */       
/* 2192 */       return -1;
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xml\internal\dtm\ref\DTMDefaultBaseIterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */