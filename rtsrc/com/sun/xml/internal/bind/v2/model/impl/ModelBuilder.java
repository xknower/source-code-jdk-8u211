/*     */ package com.sun.xml.internal.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.xml.internal.bind.WhiteSpaceProcessor;
/*     */ import com.sun.xml.internal.bind.util.Which;
/*     */ import com.sun.xml.internal.bind.v2.model.annotation.AnnotationReader;
/*     */ import com.sun.xml.internal.bind.v2.model.annotation.ClassLocatable;
/*     */ import com.sun.xml.internal.bind.v2.model.annotation.Locatable;
/*     */ import com.sun.xml.internal.bind.v2.model.core.ErrorHandler;
/*     */ import com.sun.xml.internal.bind.v2.model.core.NonElement;
/*     */ import com.sun.xml.internal.bind.v2.model.core.PropertyInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.core.PropertyKind;
/*     */ import com.sun.xml.internal.bind.v2.model.core.Ref;
/*     */ import com.sun.xml.internal.bind.v2.model.core.RegistryInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.core.TypeInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.core.TypeInfoSet;
/*     */ import com.sun.xml.internal.bind.v2.model.nav.Navigator;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.internal.bind.v2.runtime.IllegalAnnotationException;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlRegistry;
/*     */ import javax.xml.bind.annotation.XmlSchema;
/*     */ import javax.xml.bind.annotation.XmlSeeAlso;
/*     */ import javax.xml.bind.annotation.XmlTransient;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelBuilder<T, C, F, M>
/*     */   implements ModelBuilderI<T, C, F, M>
/*     */ {
/*  89 */   private final Map<QName, TypeInfo> typeNames = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   final Map<String, RegistryInfoImpl<T, C, F, M>> registries = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   private final ErrorHandler proxyErrorHandler = new ErrorHandler() {
/*     */       public void error(IllegalAnnotationException e) {
/* 129 */         ModelBuilder.this.reportError(e);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelBuilder(AnnotationReader<T, C, F, M> reader, Navigator<T, C, F, M> navigator, Map<C, C> subclassReplacements, String defaultNamespaceRemap) {
/* 140 */     this.reader = reader;
/* 141 */     this.nav = navigator;
/* 142 */     this.subclassReplacements = subclassReplacements;
/* 143 */     if (defaultNamespaceRemap == null)
/* 144 */       defaultNamespaceRemap = ""; 
/* 145 */     this.defaultNsUri = defaultNamespaceRemap;
/* 146 */     reader.setErrorHandler(this.proxyErrorHandler);
/* 147 */     this.typeInfoSet = createTypeInfoSet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 156 */       XmlSchema s = null;
/* 157 */       s.location();
/* 158 */     } catch (NullPointerException nullPointerException) {
/*     */     
/* 160 */     } catch (NoSuchMethodError e) {
/*     */       Messages res;
/*     */       
/* 163 */       if (SecureLoader.getClassClassLoader(XmlSchema.class) == null) {
/* 164 */         res = Messages.INCOMPATIBLE_API_VERSION_MUSTANG;
/*     */       } else {
/* 166 */         res = Messages.INCOMPATIBLE_API_VERSION;
/*     */       } 
/*     */       
/* 169 */       throw new LinkageError(res.format(new Object[] {
/* 170 */               Which.which(XmlSchema.class), 
/* 171 */               Which.which(ModelBuilder.class)
/*     */             }));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 182 */       WhiteSpaceProcessor.isWhiteSpace("xyz");
/* 183 */     } catch (NoSuchMethodError e) {
/*     */       
/* 185 */       throw new LinkageError(Messages.RUNNING_WITH_1_0_RUNTIME.format(new Object[] {
/* 186 */               Which.which(WhiteSpaceProcessor.class), 
/* 187 */               Which.which(ModelBuilder.class)
/*     */             }));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 196 */   private static final Logger logger = Logger.getLogger(ModelBuilder.class.getName());
/*     */   final TypeInfoSetImpl<T, C, F, M> typeInfoSet;
/*     */   
/*     */   protected TypeInfoSetImpl<T, C, F, M> createTypeInfoSet() {
/* 200 */     return new TypeInfoSetImpl<>(this.nav, this.reader, BuiltinLeafInfoImpl.createLeaves(this.nav));
/*     */   }
/*     */   public final AnnotationReader<T, C, F, M> reader;
/*     */   public final Navigator<T, C, F, M> nav;
/*     */   public final String defaultNsUri;
/*     */   private final Map<C, C> subclassReplacements;
/*     */   private ErrorHandler errorHandler;
/*     */   private boolean hadError;
/*     */   public boolean hasSwaRef;
/*     */   private boolean linked;
/*     */   
/*     */   public NonElement<T, C> getClassInfo(C clazz, Locatable upstream) {
/* 212 */     return getClassInfo(clazz, false, upstream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NonElement<T, C> getClassInfo(C clazz, boolean searchForSuperClass, Locatable upstream) {
/* 221 */     assert clazz != null;
/* 222 */     NonElement<T, C> r = this.typeInfoSet.getClassInfo(clazz);
/* 223 */     if (r != null) {
/* 224 */       return r;
/*     */     }
/* 226 */     if (this.nav.isEnum(clazz)) {
/* 227 */       EnumLeafInfoImpl<T, C, F, M> li = createEnumLeafInfo(clazz, upstream);
/* 228 */       this.typeInfoSet.add(li);
/* 229 */       r = li;
/* 230 */       addTypeName(r);
/*     */     } else {
/* 232 */       boolean isReplaced = this.subclassReplacements.containsKey(clazz);
/* 233 */       if (isReplaced && !searchForSuperClass) {
/*     */         
/* 235 */         r = getClassInfo(this.subclassReplacements.get(clazz), upstream);
/*     */       }
/* 237 */       else if (this.reader.hasClassAnnotation(clazz, (Class)XmlTransient.class) || isReplaced) {
/*     */         
/* 239 */         r = getClassInfo(this.nav.getSuperClass(clazz), searchForSuperClass, new ClassLocatable<>(upstream, clazz, this.nav));
/*     */       } else {
/*     */         
/* 242 */         ClassInfoImpl<T, C, F, M> ci = createClassInfo(clazz, upstream);
/* 243 */         this.typeInfoSet.add(ci);
/*     */ 
/*     */         
/* 246 */         for (PropertyInfo<T, C> p : ci.getProperties()) {
/* 247 */           if (p.kind() == PropertyKind.REFERENCE) {
/*     */             
/* 249 */             addToRegistry(clazz, (Locatable)p);
/* 250 */             Class[] prmzdClasses = getParametrizedTypes(p);
/* 251 */             if (prmzdClasses != null) {
/* 252 */               for (Class prmzdClass : prmzdClasses) {
/* 253 */                 if (prmzdClass != clazz) {
/* 254 */                   addToRegistry((C)prmzdClass, (Locatable)p);
/*     */                 }
/*     */               } 
/*     */             }
/*     */           } 
/*     */           
/* 260 */           for (TypeInfo<T, C> typeInfo : p.ref());
/*     */         } 
/*     */         
/* 263 */         ci.getBaseClass();
/*     */         
/* 265 */         r = ci;
/* 266 */         addTypeName(r);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 272 */     XmlSeeAlso sa = this.reader.<XmlSeeAlso>getClassAnnotation(XmlSeeAlso.class, clazz, upstream);
/* 273 */     if (sa != null) {
/* 274 */       for (T t : this.reader.getClassArrayValue(sa, "value")) {
/* 275 */         getTypeInfo(t, (Locatable)sa);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 280 */     return r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addToRegistry(C clazz, Locatable p) {
/* 289 */     String pkg = this.nav.getPackageName(clazz);
/* 290 */     if (!this.registries.containsKey(pkg)) {
/*     */       
/* 292 */       C c = this.nav.loadObjectFactory(clazz, pkg);
/* 293 */       if (c != null) {
/* 294 */         addRegistry(c, p);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Class[] getParametrizedTypes(PropertyInfo p) {
/*     */     try {
/* 305 */       Type pType = ((RuntimePropertyInfo)p).getIndividualType();
/* 306 */       if (pType instanceof ParameterizedType) {
/* 307 */         ParameterizedType prmzdType = (ParameterizedType)pType;
/* 308 */         if (prmzdType.getRawType() == JAXBElement.class) {
/* 309 */           Type[] actualTypes = prmzdType.getActualTypeArguments();
/* 310 */           Class[] result = new Class[actualTypes.length];
/* 311 */           for (int i = 0; i < actualTypes.length; i++) {
/* 312 */             result[i] = (Class)actualTypes[i];
/*     */           }
/* 314 */           return result;
/*     */         } 
/*     */       } 
/* 317 */     } catch (Exception e) {
/* 318 */       logger.log(Level.FINE, "Error in ModelBuilder.getParametrizedTypes. " + e.getMessage());
/*     */     } 
/* 320 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addTypeName(NonElement<T, C> r) {
/* 327 */     QName t = r.getTypeName();
/* 328 */     if (t == null)
/*     */       return; 
/* 330 */     TypeInfo old = this.typeNames.put(t, r);
/* 331 */     if (old != null)
/*     */     {
/* 333 */       reportError(new IllegalAnnotationException(Messages.CONFLICTING_XML_TYPE_MAPPING
/* 334 */             .format(new Object[] { r.getTypeName() }, ), old, r));
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
/*     */   public NonElement<T, C> getTypeInfo(T t, Locatable upstream) {
/* 347 */     NonElement<T, C> r = this.typeInfoSet.getTypeInfo(t);
/* 348 */     if (r != null) return r;
/*     */     
/* 350 */     if (this.nav.isArray(t)) {
/*     */       
/* 352 */       ArrayInfoImpl<T, C, F, M> ai = createArrayInfo(upstream, t);
/* 353 */       addTypeName(ai);
/* 354 */       this.typeInfoSet.add(ai);
/* 355 */       return ai;
/*     */     } 
/*     */     
/* 358 */     C c = this.nav.asDecl(t);
/* 359 */     assert c != null : t.toString() + " must be a leaf, but we failed to recognize it.";
/* 360 */     return getClassInfo(c, upstream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NonElement<T, C> getTypeInfo(Ref<T, C> ref) {
/* 368 */     assert !ref.valueList;
/* 369 */     C c = this.nav.asDecl(ref.type);
/* 370 */     if (c != null && this.reader.getClassAnnotation(XmlRegistry.class, c, null) != null) {
/* 371 */       if (!this.registries.containsKey(this.nav.getPackageName(c)))
/* 372 */         addRegistry(c, null); 
/* 373 */       return null;
/*     */     } 
/* 375 */     return getTypeInfo(ref.type, null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected EnumLeafInfoImpl<T, C, F, M> createEnumLeafInfo(C clazz, Locatable upstream) {
/* 380 */     return new EnumLeafInfoImpl<>(this, upstream, clazz, this.nav.use(clazz));
/*     */   }
/*     */   
/*     */   protected ClassInfoImpl<T, C, F, M> createClassInfo(C clazz, Locatable upstream) {
/* 384 */     return new ClassInfoImpl<>(this, upstream, clazz);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ElementInfoImpl<T, C, F, M> createElementInfo(RegistryInfoImpl<T, C, F, M> registryInfo, M m) throws IllegalAnnotationException {
/* 389 */     return new ElementInfoImpl<>(this, registryInfo, m);
/*     */   }
/*     */   
/*     */   protected ArrayInfoImpl<T, C, F, M> createArrayInfo(Locatable upstream, T arrayType) {
/* 393 */     return new ArrayInfoImpl<>(this, upstream, arrayType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegistryInfo<T, C> addRegistry(C registryClass, Locatable upstream) {
/* 402 */     return new RegistryInfoImpl<>(this, upstream, registryClass);
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
/*     */   public RegistryInfo<T, C> getRegistry(String packageName) {
/* 414 */     return this.registries.get(packageName);
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
/*     */   public TypeInfoSet<T, C, F, M> link() {
/* 432 */     assert !this.linked;
/* 433 */     this.linked = true;
/*     */     
/* 435 */     for (ElementInfoImpl<T, C, F, M> ei : this.typeInfoSet.getAllElements()) {
/* 436 */       ei.link();
/*     */     }
/* 438 */     for (ClassInfoImpl ci : this.typeInfoSet.beans().values()) {
/* 439 */       ci.link();
/*     */     }
/* 441 */     for (EnumLeafInfoImpl li : this.typeInfoSet.enums().values()) {
/* 442 */       li.link();
/*     */     }
/* 444 */     if (this.hadError) {
/* 445 */       return null;
/*     */     }
/* 447 */     return this.typeInfoSet;
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
/*     */   public void setErrorHandler(ErrorHandler errorHandler) {
/* 463 */     this.errorHandler = errorHandler;
/*     */   }
/*     */   
/*     */   public final void reportError(IllegalAnnotationException e) {
/* 467 */     this.hadError = true;
/* 468 */     if (this.errorHandler != null)
/* 469 */       this.errorHandler.error(e); 
/*     */   }
/*     */   
/*     */   public boolean isReplaced(C sc) {
/* 473 */     return this.subclassReplacements.containsKey(sc);
/*     */   }
/*     */ 
/*     */   
/*     */   public Navigator<T, C, F, M> getNavigator() {
/* 478 */     return this.nav;
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotationReader<T, C, F, M> getReader() {
/* 483 */     return this.reader;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\model\impl\ModelBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */