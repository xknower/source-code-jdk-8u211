/*     */ package com.sun.xml.internal.ws.model;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.bind.v2.model.annotation.AnnotationReader;
/*     */ import com.sun.xml.internal.bind.v2.model.nav.Navigator;
/*     */ import com.sun.xml.internal.ws.spi.db.BindingHelper;
/*     */ import com.sun.xml.internal.ws.util.StringUtils;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import java.util.logging.Logger;
/*     */ import javax.jws.WebParam;
/*     */ import javax.jws.WebResult;
/*     */ import javax.xml.bind.annotation.XmlAttachmentRef;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlList;
/*     */ import javax.xml.bind.annotation.XmlMimeType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
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
/*     */ public abstract class AbstractWrapperBeanGenerator<T, C, M, A extends Comparable>
/*     */ {
/*  58 */   private static final Logger LOGGER = Logger.getLogger(AbstractWrapperBeanGenerator.class.getName());
/*     */   
/*     */   private static final String RETURN = "return";
/*     */   
/*     */   private static final String EMTPY_NAMESPACE_ID = "";
/*  63 */   private static final Class[] jaxbAnns = new Class[] { XmlAttachmentRef.class, XmlMimeType.class, XmlJavaTypeAdapter.class, XmlList.class, XmlElement.class };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private static final Set<String> skipProperties = new HashSet<>();
/*     */   static {
/*  70 */     skipProperties.add("getCause");
/*  71 */     skipProperties.add("getLocalizedMessage");
/*  72 */     skipProperties.add("getClass");
/*  73 */     skipProperties.add("getStackTrace");
/*  74 */     skipProperties.add("getSuppressed");
/*     */   }
/*     */ 
/*     */   
/*     */   private final AnnotationReader<T, C, ?, M> annReader;
/*     */   private final Navigator<T, C, ?, M> nav;
/*     */   private final BeanMemberFactory<T, A> factory;
/*     */   
/*     */   protected AbstractWrapperBeanGenerator(AnnotationReader<T, C, ?, M> annReader, Navigator<T, C, ?, M> nav, BeanMemberFactory<T, A> factory) {
/*  83 */     this.annReader = annReader;
/*  84 */     this.nav = nav;
/*  85 */     this.factory = factory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Annotation> collectJAXBAnnotations(M method) {
/*  94 */     List<Annotation> jaxbAnnotation = new ArrayList<>();
/*  95 */     for (Class<Annotation> jaxbClass : jaxbAnns) {
/*  96 */       Annotation ann = this.annReader.getMethodAnnotation(jaxbClass, method, null);
/*  97 */       if (ann != null) {
/*  98 */         jaxbAnnotation.add(ann);
/*     */       }
/*     */     } 
/* 101 */     return jaxbAnnotation;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<Annotation> collectJAXBAnnotations(M method, int paramIndex) {
/* 106 */     List<Annotation> jaxbAnnotation = new ArrayList<>();
/* 107 */     for (Class<Annotation> jaxbClass : jaxbAnns) {
/* 108 */       Annotation ann = this.annReader.getMethodParameterAnnotation(jaxbClass, method, paramIndex, null);
/* 109 */       if (ann != null) {
/* 110 */         jaxbAnnotation.add(ann);
/*     */       }
/*     */     } 
/* 113 */     return jaxbAnnotation;
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
/*     */   public List<A> collectRequestBeanMembers(M method) {
/* 138 */     List<A> requestMembers = new ArrayList<>();
/* 139 */     int paramIndex = -1;
/*     */     
/* 141 */     for (T param : this.nav.getMethodParameters(method)) {
/* 142 */       paramIndex++;
/* 143 */       WebParam webParam = this.annReader.<WebParam>getMethodParameterAnnotation(WebParam.class, method, paramIndex, null);
/* 144 */       if (webParam == null || (!webParam.header() && !webParam.mode().equals(WebParam.Mode.OUT))) {
/*     */ 
/*     */         
/* 147 */         T holderType = getHolderValueType(param);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 153 */         T paramType = (holderType != null) ? holderType : getSafeType(param);
/*     */         
/* 155 */         String paramName = (webParam != null && webParam.name().length() > 0) ? webParam.name() : ("arg" + paramIndex);
/*     */         
/* 157 */         String paramNamespace = (webParam != null && webParam.targetNamespace().length() > 0) ? webParam.targetNamespace() : "";
/*     */ 
/*     */         
/* 160 */         List<Annotation> jaxbAnnotation = collectJAXBAnnotations(method, paramIndex);
/*     */ 
/*     */         
/* 163 */         processXmlElement(jaxbAnnotation, paramName, paramNamespace, paramType);
/* 164 */         Comparable comparable = (Comparable)this.factory.createWrapperBeanMember(paramType, 
/* 165 */             getPropertyName(paramName), jaxbAnnotation);
/* 166 */         requestMembers.add((A)comparable);
/*     */       } 
/* 168 */     }  return requestMembers;
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
/*     */   public List<A> collectResponseBeanMembers(M method) {
/* 182 */     List<A> responseMembers = new ArrayList<>();
/*     */ 
/*     */     
/* 185 */     String responseElementName = "return";
/* 186 */     String responseNamespace = "";
/* 187 */     boolean isResultHeader = false;
/* 188 */     WebResult webResult = this.annReader.<WebResult>getMethodAnnotation(WebResult.class, method, null);
/* 189 */     if (webResult != null) {
/* 190 */       if (webResult.name().length() > 0) {
/* 191 */         responseElementName = webResult.name();
/*     */       }
/* 193 */       if (webResult.targetNamespace().length() > 0) {
/* 194 */         responseNamespace = webResult.targetNamespace();
/*     */       }
/* 196 */       isResultHeader = webResult.header();
/*     */     } 
/* 198 */     T returnType = getSafeType(this.nav.getReturnType(method));
/* 199 */     if (!isVoidType(returnType) && !isResultHeader) {
/* 200 */       List<Annotation> jaxbRespAnnotations = collectJAXBAnnotations(method);
/* 201 */       processXmlElement(jaxbRespAnnotations, responseElementName, responseNamespace, returnType);
/* 202 */       responseMembers.add(this.factory.createWrapperBeanMember(returnType, getPropertyName(responseElementName), jaxbRespAnnotations));
/*     */     } 
/*     */ 
/*     */     
/* 206 */     int paramIndex = -1;
/* 207 */     for (T param : this.nav.getMethodParameters(method)) {
/* 208 */       paramIndex++;
/*     */       
/* 210 */       T paramType = getHolderValueType(param);
/* 211 */       WebParam webParam = this.annReader.<WebParam>getMethodParameterAnnotation(WebParam.class, method, paramIndex, null);
/* 212 */       if (paramType != null && (webParam == null || !webParam.header())) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 217 */         String paramName = (webParam != null && webParam.name().length() > 0) ? webParam.name() : ("arg" + paramIndex);
/*     */         
/* 219 */         String paramNamespace = (webParam != null && webParam.targetNamespace().length() > 0) ? webParam.targetNamespace() : "";
/* 220 */         List<Annotation> jaxbAnnotation = collectJAXBAnnotations(method, paramIndex);
/* 221 */         processXmlElement(jaxbAnnotation, paramName, paramNamespace, paramType);
/* 222 */         Comparable comparable = (Comparable)this.factory.createWrapperBeanMember(paramType, 
/* 223 */             getPropertyName(paramName), jaxbAnnotation);
/* 224 */         responseMembers.add((A)comparable);
/*     */       } 
/*     */     } 
/* 227 */     return responseMembers;
/*     */   }
/*     */   
/*     */   private void processXmlElement(List<Annotation> jaxb, String elemName, String elemNS, T type) {
/* 231 */     XmlElement elemAnn = null;
/* 232 */     for (Annotation a : jaxb) {
/* 233 */       if (a.annotationType() == XmlElement.class) {
/* 234 */         elemAnn = (XmlElement)a;
/* 235 */         jaxb.remove(a);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 240 */     String name = (elemAnn != null && !elemAnn.name().equals("##default")) ? elemAnn.name() : elemName;
/*     */ 
/*     */     
/* 243 */     String ns = (elemAnn != null && !elemAnn.namespace().equals("##default")) ? elemAnn.namespace() : elemNS;
/*     */ 
/*     */     
/* 246 */     boolean nillable = (this.nav.isArray(type) || (elemAnn != null && elemAnn.nillable()));
/*     */     
/* 248 */     boolean required = (elemAnn != null && elemAnn.required());
/* 249 */     XmlElementHandler handler = new XmlElementHandler(name, ns, nillable, required);
/* 250 */     XmlElement elem = (XmlElement)Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { XmlElement.class }, handler);
/* 251 */     jaxb.add(elem);
/*     */   }
/*     */   
/*     */   public static interface BeanMemberFactory<T, A> {
/*     */     A createWrapperBeanMember(T param1T, String param1String, List<Annotation> param1List); }
/*     */   
/*     */   private static class XmlElementHandler implements InvocationHandler { private String name;
/*     */     private String namespace;
/*     */     private boolean nillable;
/*     */     private boolean required;
/*     */     
/*     */     XmlElementHandler(String name, String namespace, boolean nillable, boolean required) {
/* 263 */       this.name = name;
/* 264 */       this.namespace = namespace;
/* 265 */       this.nillable = nillable;
/* 266 */       this.required = required;
/*     */     }
/*     */     
/*     */     public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/* 270 */       String methodName = method.getName();
/* 271 */       if (methodName.equals("name"))
/* 272 */         return this.name; 
/* 273 */       if (methodName.equals("namespace"))
/* 274 */         return this.namespace; 
/* 275 */       if (methodName.equals("nillable"))
/* 276 */         return Boolean.valueOf(this.nillable); 
/* 277 */       if (methodName.equals("required")) {
/* 278 */         return Boolean.valueOf(this.required);
/*     */       }
/* 280 */       throw new WebServiceException("Not handling " + methodName);
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<A> collectExceptionBeanMembers(C exception) {
/* 301 */     return collectExceptionBeanMembers(exception, true);
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
/*     */   public Collection<A> collectExceptionBeanMembers(C exception, boolean decapitalize) {
/* 322 */     TreeMap<String, A> fields = new TreeMap<>();
/* 323 */     getExceptionProperties(exception, fields, decapitalize);
/*     */ 
/*     */     
/* 326 */     XmlType xmlType = this.annReader.<XmlType>getClassAnnotation(XmlType.class, exception, null);
/* 327 */     if (xmlType != null) {
/* 328 */       String[] propOrder = xmlType.propOrder();
/*     */       
/* 330 */       if (propOrder.length > 0 && propOrder[0].length() != 0) {
/* 331 */         List<A> list = new ArrayList<>();
/* 332 */         for (String prop : propOrder) {
/* 333 */           Comparable comparable = (Comparable)fields.get(prop);
/* 334 */           if (comparable != null) {
/* 335 */             list.add((A)comparable);
/*     */           } else {
/* 337 */             throw new WebServiceException("Exception " + exception + " has @XmlType and its propOrder contains unknown property " + prop);
/*     */           } 
/*     */         } 
/*     */         
/* 341 */         return list;
/*     */       } 
/*     */     } 
/*     */     
/* 345 */     return fields.values();
/*     */   }
/*     */ 
/*     */   
/*     */   private void getExceptionProperties(C exception, TreeMap<String, A> fields, boolean decapitalize) {
/* 350 */     C sc = this.nav.getSuperClass(exception);
/* 351 */     if (sc != null) {
/* 352 */       getExceptionProperties(sc, fields, decapitalize);
/*     */     }
/* 354 */     Collection<? extends M> methods = this.nav.getDeclaredMethods(exception);
/*     */     
/* 356 */     for (M method : methods) {
/*     */ 
/*     */ 
/*     */       
/* 360 */       if (!this.nav.isPublicMethod(method) || (this.nav
/* 361 */         .isStaticMethod(method) && this.nav.isFinalMethod(method))) {
/*     */         continue;
/*     */       }
/*     */       
/* 365 */       if (!this.nav.isPublicMethod(method)) {
/*     */         continue;
/*     */       }
/*     */       
/* 369 */       String name = this.nav.getMethodName(method);
/*     */       
/* 371 */       if ((!name.startsWith("get") && !name.startsWith("is")) || skipProperties.contains(name) || name
/* 372 */         .equals("get") || name.equals("is")) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 377 */       T returnType = getSafeType(this.nav.getReturnType(method));
/* 378 */       if ((this.nav.getMethodParameters(method)).length == 0) {
/* 379 */         String fieldName = name.startsWith("get") ? name.substring(3) : name.substring(2);
/* 380 */         if (decapitalize) fieldName = StringUtils.decapitalize(fieldName); 
/* 381 */         fields.put(fieldName, this.factory.createWrapperBeanMember(returnType, fieldName, Collections.emptyList()));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getPropertyName(String name) {
/* 393 */     String propertyName = BindingHelper.mangleNameToVariableName(name);
/*     */ 
/*     */     
/* 396 */     return getJavaReservedVarialbeName(propertyName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private static String getJavaReservedVarialbeName(@NotNull String name) {
/* 405 */     String reservedName = reservedWords.get(name);
/* 406 */     return (reservedName == null) ? name : reservedName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 412 */   private static final Map<String, String> reservedWords = new HashMap<>(); static {
/* 413 */     reservedWords.put("abstract", "_abstract");
/* 414 */     reservedWords.put("assert", "_assert");
/* 415 */     reservedWords.put("boolean", "_boolean");
/* 416 */     reservedWords.put("break", "_break");
/* 417 */     reservedWords.put("byte", "_byte");
/* 418 */     reservedWords.put("case", "_case");
/* 419 */     reservedWords.put("catch", "_catch");
/* 420 */     reservedWords.put("char", "_char");
/* 421 */     reservedWords.put("class", "_class");
/* 422 */     reservedWords.put("const", "_const");
/* 423 */     reservedWords.put("continue", "_continue");
/* 424 */     reservedWords.put("default", "_default");
/* 425 */     reservedWords.put("do", "_do");
/* 426 */     reservedWords.put("double", "_double");
/* 427 */     reservedWords.put("else", "_else");
/* 428 */     reservedWords.put("extends", "_extends");
/* 429 */     reservedWords.put("false", "_false");
/* 430 */     reservedWords.put("final", "_final");
/* 431 */     reservedWords.put("finally", "_finally");
/* 432 */     reservedWords.put("float", "_float");
/* 433 */     reservedWords.put("for", "_for");
/* 434 */     reservedWords.put("goto", "_goto");
/* 435 */     reservedWords.put("if", "_if");
/* 436 */     reservedWords.put("implements", "_implements");
/* 437 */     reservedWords.put("import", "_import");
/* 438 */     reservedWords.put("instanceof", "_instanceof");
/* 439 */     reservedWords.put("int", "_int");
/* 440 */     reservedWords.put("interface", "_interface");
/* 441 */     reservedWords.put("long", "_long");
/* 442 */     reservedWords.put("native", "_native");
/* 443 */     reservedWords.put("new", "_new");
/* 444 */     reservedWords.put("null", "_null");
/* 445 */     reservedWords.put("package", "_package");
/* 446 */     reservedWords.put("private", "_private");
/* 447 */     reservedWords.put("protected", "_protected");
/* 448 */     reservedWords.put("public", "_public");
/* 449 */     reservedWords.put("return", "_return");
/* 450 */     reservedWords.put("short", "_short");
/* 451 */     reservedWords.put("static", "_static");
/* 452 */     reservedWords.put("strictfp", "_strictfp");
/* 453 */     reservedWords.put("super", "_super");
/* 454 */     reservedWords.put("switch", "_switch");
/* 455 */     reservedWords.put("synchronized", "_synchronized");
/* 456 */     reservedWords.put("this", "_this");
/* 457 */     reservedWords.put("throw", "_throw");
/* 458 */     reservedWords.put("throws", "_throws");
/* 459 */     reservedWords.put("transient", "_transient");
/* 460 */     reservedWords.put("true", "_true");
/* 461 */     reservedWords.put("try", "_try");
/* 462 */     reservedWords.put("void", "_void");
/* 463 */     reservedWords.put("volatile", "_volatile");
/* 464 */     reservedWords.put("while", "_while");
/* 465 */     reservedWords.put("enum", "_enum");
/*     */   }
/*     */   
/*     */   protected abstract T getSafeType(T paramT);
/*     */   
/*     */   protected abstract T getHolderValueType(T paramT);
/*     */   
/*     */   protected abstract boolean isVoidType(T paramT);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\model\AbstractWrapperBeanGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */