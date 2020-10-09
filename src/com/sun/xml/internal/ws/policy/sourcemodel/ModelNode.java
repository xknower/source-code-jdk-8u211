/*     */ package com.sun.xml.internal.ws.policy.sourcemodel;
/*     */ 
/*     */ import com.sun.xml.internal.ws.policy.privateutil.LocalizationMessages;
/*     */ import com.sun.xml.internal.ws.policy.privateutil.PolicyLogger;
/*     */ import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
/*     */ import com.sun.xml.internal.ws.policy.sourcemodel.wspolicy.XmlToken;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
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
/*     */ public final class ModelNode
/*     */   implements Iterable<ModelNode>, Cloneable
/*     */ {
/*     */   private LinkedList<ModelNode> children;
/*     */   private Collection<ModelNode> unmodifiableViewOnContent;
/*     */   private final Type type;
/*     */   private ModelNode parentNode;
/*     */   private PolicySourceModel parentModel;
/*     */   private PolicyReferenceData referenceData;
/*  46 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(ModelNode.class);
/*     */   private PolicySourceModel referencedModel;
/*     */   private AssertionData nodeData;
/*     */   
/*     */   public enum Type
/*     */   {
/*  52 */     POLICY((String)XmlToken.Policy),
/*  53 */     ALL((String)XmlToken.All),
/*  54 */     EXACTLY_ONE((String)XmlToken.ExactlyOne),
/*  55 */     POLICY_REFERENCE((String)XmlToken.PolicyReference),
/*  56 */     ASSERTION((String)XmlToken.UNKNOWN),
/*  57 */     ASSERTION_PARAMETER_NODE((String)XmlToken.UNKNOWN);
/*     */     
/*     */     private XmlToken token;
/*     */     
/*     */     Type(XmlToken token) {
/*  62 */       this.token = token;
/*     */     }
/*     */     
/*     */     public XmlToken getXmlToken() {
/*  66 */       return this.token;
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
/*     */     private boolean isChildTypeSupported(Type childType) {
/*     */       // Byte code:
/*     */       //   0: getstatic com/sun/xml/internal/ws/policy/sourcemodel/ModelNode$1.$SwitchMap$com$sun$xml$internal$ws$policy$sourcemodel$ModelNode$Type : [I
/*     */       //   3: aload_0
/*     */       //   4: invokevirtual ordinal : ()I
/*     */       //   7: iaload
/*     */       //   8: tableswitch default -> 152, 1 -> 120, 2 -> 48, 3 -> 80, 4 -> 48, 5 -> 48, 6 -> 82
/*     */       //   48: getstatic com/sun/xml/internal/ws/policy/sourcemodel/ModelNode$1.$SwitchMap$com$sun$xml$internal$ws$policy$sourcemodel$ModelNode$Type : [I
/*     */       //   51: aload_1
/*     */       //   52: invokevirtual ordinal : ()I
/*     */       //   55: iaload
/*     */       //   56: lookupswitch default -> 78, 1 -> 76
/*     */       //   76: iconst_0
/*     */       //   77: ireturn
/*     */       //   78: iconst_1
/*     */       //   79: ireturn
/*     */       //   80: iconst_0
/*     */       //   81: ireturn
/*     */       //   82: getstatic com/sun/xml/internal/ws/policy/sourcemodel/ModelNode$1.$SwitchMap$com$sun$xml$internal$ws$policy$sourcemodel$ModelNode$Type : [I
/*     */       //   85: aload_1
/*     */       //   86: invokevirtual ordinal : ()I
/*     */       //   89: iaload
/*     */       //   90: tableswitch default -> 118, 1 -> 116, 2 -> 116, 3 -> 116
/*     */       //   116: iconst_1
/*     */       //   117: ireturn
/*     */       //   118: iconst_0
/*     */       //   119: ireturn
/*     */       //   120: getstatic com/sun/xml/internal/ws/policy/sourcemodel/ModelNode$1.$SwitchMap$com$sun$xml$internal$ws$policy$sourcemodel$ModelNode$Type : [I
/*     */       //   123: aload_1
/*     */       //   124: invokevirtual ordinal : ()I
/*     */       //   127: iaload
/*     */       //   128: lookupswitch default -> 150, 1 -> 148
/*     */       //   148: iconst_1
/*     */       //   149: ireturn
/*     */       //   150: iconst_0
/*     */       //   151: ireturn
/*     */       //   152: invokestatic access$000 : ()Lcom/sun/xml/internal/ws/policy/privateutil/PolicyLogger;
/*     */       //   155: new java/lang/IllegalStateException
/*     */       //   158: dup
/*     */       //   159: aload_0
/*     */       //   160: invokestatic WSP_0060_POLICY_ELEMENT_TYPE_UNKNOWN : (Ljava/lang/Object;)Ljava/lang/String;
/*     */       //   163: invokespecial <init> : (Ljava/lang/String;)V
/*     */       //   166: invokevirtual logSevereException : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
/*     */       //   169: checkcast java/lang/IllegalStateException
/*     */       //   172: athrow
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #77	-> 0
/*     */       //   #81	-> 48
/*     */       //   #83	-> 76
/*     */       //   #85	-> 78
/*     */       //   #88	-> 80
/*     */       //   #90	-> 82
/*     */       //   #94	-> 116
/*     */       //   #96	-> 118
/*     */       //   #99	-> 120
/*     */       //   #101	-> 148
/*     */       //   #103	-> 150
/*     */       //   #106	-> 152
/*     */       //   #107	-> 160
/*     */       //   #106	-> 166
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	173	0	this	Lcom/sun/xml/internal/ws/policy/sourcemodel/ModelNode$Type;
/*     */       //   0	173	1	childType	Lcom/sun/xml/internal/ws/policy/sourcemodel/ModelNode$Type;
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
/*     */   static ModelNode createRootPolicyNode(PolicySourceModel model) throws IllegalArgumentException {
/* 136 */     if (model == null) {
/* 137 */       throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.WSP_0039_POLICY_SRC_MODEL_INPUT_PARAMETER_MUST_NOT_BE_NULL()));
/*     */     }
/* 139 */     return new ModelNode(Type.POLICY, model);
/*     */   }
/*     */   
/*     */   private ModelNode(Type type, PolicySourceModel parentModel) {
/* 143 */     this.type = type;
/* 144 */     this.parentModel = parentModel;
/* 145 */     this.children = new LinkedList<>();
/* 146 */     this.unmodifiableViewOnContent = Collections.unmodifiableCollection(this.children);
/*     */   }
/*     */   
/*     */   private ModelNode(Type type, PolicySourceModel parentModel, AssertionData data) {
/* 150 */     this(type, parentModel);
/*     */     
/* 152 */     this.nodeData = data;
/*     */   }
/*     */   
/*     */   private ModelNode(PolicySourceModel parentModel, PolicyReferenceData data) {
/* 156 */     this(Type.POLICY_REFERENCE, parentModel);
/*     */     
/* 158 */     this.referenceData = data;
/*     */   }
/*     */   
/*     */   private void checkCreateChildOperationSupportForType(Type type) throws UnsupportedOperationException {
/* 162 */     if (!this.type.isChildTypeSupported(type)) {
/* 163 */       throw (UnsupportedOperationException)LOGGER.logSevereException(new UnsupportedOperationException(LocalizationMessages.WSP_0073_CREATE_CHILD_NODE_OPERATION_NOT_SUPPORTED(type, this.type)));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelNode createChildPolicyNode() {
/* 174 */     checkCreateChildOperationSupportForType(Type.POLICY);
/*     */     
/* 176 */     ModelNode node = new ModelNode(Type.POLICY, this.parentModel);
/* 177 */     addChild(node);
/*     */     
/* 179 */     return node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelNode createChildAllNode() {
/* 189 */     checkCreateChildOperationSupportForType(Type.ALL);
/*     */     
/* 191 */     ModelNode node = new ModelNode(Type.ALL, this.parentModel);
/* 192 */     addChild(node);
/*     */     
/* 194 */     return node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelNode createChildExactlyOneNode() {
/* 204 */     checkCreateChildOperationSupportForType(Type.EXACTLY_ONE);
/*     */     
/* 206 */     ModelNode node = new ModelNode(Type.EXACTLY_ONE, this.parentModel);
/* 207 */     addChild(node);
/*     */     
/* 209 */     return node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelNode createChildAssertionNode() {
/* 219 */     checkCreateChildOperationSupportForType(Type.ASSERTION);
/*     */     
/* 221 */     ModelNode node = new ModelNode(Type.ASSERTION, this.parentModel);
/* 222 */     addChild(node);
/*     */     
/* 224 */     return node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelNode createChildAssertionNode(AssertionData nodeData) {
/* 235 */     checkCreateChildOperationSupportForType(Type.ASSERTION);
/*     */     
/* 237 */     ModelNode node = new ModelNode(Type.ASSERTION, this.parentModel, nodeData);
/* 238 */     addChild(node);
/*     */     
/* 240 */     return node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelNode createChildAssertionParameterNode() {
/* 250 */     checkCreateChildOperationSupportForType(Type.ASSERTION_PARAMETER_NODE);
/*     */     
/* 252 */     ModelNode node = new ModelNode(Type.ASSERTION_PARAMETER_NODE, this.parentModel);
/* 253 */     addChild(node);
/*     */     
/* 255 */     return node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ModelNode createChildAssertionParameterNode(AssertionData nodeData) {
/* 266 */     checkCreateChildOperationSupportForType(Type.ASSERTION_PARAMETER_NODE);
/*     */     
/* 268 */     ModelNode node = new ModelNode(Type.ASSERTION_PARAMETER_NODE, this.parentModel, nodeData);
/* 269 */     addChild(node);
/*     */     
/* 271 */     return node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ModelNode createChildPolicyReferenceNode(PolicyReferenceData referenceData) {
/* 282 */     checkCreateChildOperationSupportForType(Type.POLICY_REFERENCE);
/*     */     
/* 284 */     ModelNode node = new ModelNode(this.parentModel, referenceData);
/* 285 */     this.parentModel.addNewPolicyReference(node);
/* 286 */     addChild(node);
/*     */     
/* 288 */     return node;
/*     */   }
/*     */   
/*     */   Collection<ModelNode> getChildren() {
/* 292 */     return this.unmodifiableViewOnContent;
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
/*     */   void setParentModel(PolicySourceModel model) throws IllegalAccessException {
/* 304 */     if (this.parentNode != null) {
/* 305 */       throw (IllegalAccessException)LOGGER.logSevereException(new IllegalAccessException(LocalizationMessages.WSP_0049_PARENT_MODEL_CAN_NOT_BE_CHANGED()));
/*     */     }
/*     */     
/* 308 */     updateParentModelReference(model);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateParentModelReference(PolicySourceModel model) {
/* 317 */     this.parentModel = model;
/*     */     
/* 319 */     for (ModelNode child : this.children) {
/* 320 */       child.updateParentModelReference(model);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolicySourceModel getParentModel() {
/* 330 */     return this.parentModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type getType() {
/* 339 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelNode getParentNode() {
/* 348 */     return this.parentNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AssertionData getNodeData() {
/* 359 */     return this.nodeData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PolicyReferenceData getPolicyReferenceData() {
/* 370 */     return this.referenceData;
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
/*     */   public AssertionData setOrReplaceNodeData(AssertionData newData) {
/* 387 */     if (!isDomainSpecific()) {
/* 388 */       throw (UnsupportedOperationException)LOGGER.logSevereException(new UnsupportedOperationException(LocalizationMessages.WSP_0051_OPERATION_NOT_SUPPORTED_FOR_THIS_BUT_ASSERTION_RELATED_NODE_TYPE(this.type)));
/*     */     }
/*     */     
/* 391 */     AssertionData oldData = this.nodeData;
/* 392 */     this.nodeData = newData;
/*     */     
/* 394 */     return oldData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isDomainSpecific() {
/* 405 */     return (this.type == Type.ASSERTION || this.type == Type.ASSERTION_PARAMETER_NODE);
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
/*     */   private boolean addChild(ModelNode child) {
/* 419 */     this.children.add(child);
/* 420 */     child.parentNode = this;
/*     */     
/* 422 */     return true;
/*     */   }
/*     */   
/*     */   void setReferencedModel(PolicySourceModel model) {
/* 426 */     if (this.type != Type.POLICY_REFERENCE) {
/* 427 */       throw (UnsupportedOperationException)LOGGER.logSevereException(new UnsupportedOperationException(LocalizationMessages.WSP_0050_OPERATION_NOT_SUPPORTED_FOR_THIS_BUT_POLICY_REFERENCE_NODE_TYPE(this.type)));
/*     */     }
/*     */     
/* 430 */     this.referencedModel = model;
/*     */   }
/*     */   
/*     */   PolicySourceModel getReferencedModel() {
/* 434 */     return this.referencedModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int childrenSize() {
/* 444 */     return this.children.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasChildren() {
/* 453 */     return !this.children.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<ModelNode> iterator() {
/* 462 */     return this.children.iterator();
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
/*     */   public boolean equals(Object obj) {
/* 475 */     if (this == obj) {
/* 476 */       return true;
/*     */     }
/*     */     
/* 479 */     if (!(obj instanceof ModelNode)) {
/* 480 */       return false;
/*     */     }
/*     */     
/* 483 */     boolean result = true;
/* 484 */     ModelNode that = (ModelNode)obj;
/*     */     
/* 486 */     result = (result && this.type.equals(that.type));
/*     */     
/* 488 */     result = (result && ((this.nodeData == null) ? (that.nodeData == null) : this.nodeData.equals(that.nodeData)));
/* 489 */     result = (result && ((this.children == null) ? (that.children == null) : this.children.equals(that.children)));
/*     */     
/* 491 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 499 */     int result = 17;
/*     */     
/* 501 */     result = 37 * result + this.type.hashCode();
/* 502 */     result = 37 * result + ((this.parentNode == null) ? 0 : this.parentNode.hashCode());
/* 503 */     result = 37 * result + ((this.nodeData == null) ? 0 : this.nodeData.hashCode());
/* 504 */     result = 37 * result + this.children.hashCode();
/*     */     
/* 506 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 517 */     return toString(0, new StringBuffer()).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuffer toString(int indentLevel, StringBuffer buffer) {
/* 528 */     String indent = PolicyUtils.Text.createIndent(indentLevel);
/* 529 */     String innerIndent = PolicyUtils.Text.createIndent(indentLevel + 1);
/*     */     
/* 531 */     buffer.append(indent).append(this.type).append(" {").append(PolicyUtils.Text.NEW_LINE);
/* 532 */     if (this.type == Type.ASSERTION) {
/* 533 */       if (this.nodeData == null) {
/* 534 */         buffer.append(innerIndent).append("no assertion data set");
/*     */       } else {
/* 536 */         this.nodeData.toString(indentLevel + 1, buffer);
/*     */       } 
/* 538 */       buffer.append(PolicyUtils.Text.NEW_LINE);
/* 539 */     } else if (this.type == Type.POLICY_REFERENCE) {
/* 540 */       if (this.referenceData == null) {
/* 541 */         buffer.append(innerIndent).append("no policy reference data set");
/*     */       } else {
/* 543 */         this.referenceData.toString(indentLevel + 1, buffer);
/*     */       } 
/* 545 */       buffer.append(PolicyUtils.Text.NEW_LINE);
/* 546 */     } else if (this.type == Type.ASSERTION_PARAMETER_NODE) {
/* 547 */       if (this.nodeData == null) {
/* 548 */         buffer.append(innerIndent).append("no parameter data set");
/*     */       } else {
/*     */         
/* 551 */         this.nodeData.toString(indentLevel + 1, buffer);
/*     */       } 
/* 553 */       buffer.append(PolicyUtils.Text.NEW_LINE);
/*     */     } 
/*     */     
/* 556 */     if (this.children.size() > 0) {
/* 557 */       for (ModelNode child : this.children) {
/* 558 */         child.toString(indentLevel + 1, buffer).append(PolicyUtils.Text.NEW_LINE);
/*     */       }
/*     */     } else {
/* 561 */       buffer.append(innerIndent).append("no child nodes").append(PolicyUtils.Text.NEW_LINE);
/*     */     } 
/*     */     
/* 564 */     buffer.append(indent).append('}');
/* 565 */     return buffer;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ModelNode clone() throws CloneNotSupportedException {
/* 570 */     ModelNode clone = (ModelNode)super.clone();
/*     */     
/* 572 */     if (this.nodeData != null) {
/* 573 */       clone.nodeData = this.nodeData.clone();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 578 */     if (this.referencedModel != null) {
/* 579 */       clone.referencedModel = this.referencedModel.clone();
/*     */     }
/*     */ 
/*     */     
/* 583 */     clone.children = new LinkedList<>();
/* 584 */     clone.unmodifiableViewOnContent = Collections.unmodifiableCollection(clone.children);
/*     */     
/* 586 */     for (ModelNode thisChild : this.children) {
/* 587 */       clone.addChild(thisChild.clone());
/*     */     }
/*     */     
/* 590 */     return clone;
/*     */   }
/*     */   
/*     */   PolicyReferenceData getReferenceData() {
/* 594 */     return this.referenceData;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\policy\sourcemodel\ModelNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */