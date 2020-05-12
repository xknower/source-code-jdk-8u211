/*     */ package com.sun.beans.decoder;
/*     */ 
/*     */ import com.sun.beans.finder.ClassFinder;
/*     */ import java.beans.ExceptionListener;
/*     */ import java.io.IOException;
/*     */ import java.io.StringReader;
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.DefaultHandler;
/*     */ import sun.misc.SharedSecrets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DocumentHandler
/*     */   extends DefaultHandler
/*     */ {
/*  65 */   private final AccessControlContext acc = AccessController.getContext();
/*  66 */   private final Map<String, Class<? extends ElementHandler>> handlers = new HashMap<>();
/*  67 */   private final Map<String, Object> environment = new HashMap<>();
/*  68 */   private final List<Object> objects = new ArrayList();
/*     */   
/*     */   private Reference<ClassLoader> loader;
/*     */   
/*     */   private ExceptionListener listener;
/*     */   
/*     */   private Object owner;
/*     */   
/*     */   private ElementHandler handler;
/*     */ 
/*     */   
/*     */   public DocumentHandler() {
/*  80 */     setElementHandler("java", (Class)JavaElementHandler.class);
/*  81 */     setElementHandler("null", (Class)NullElementHandler.class);
/*  82 */     setElementHandler("array", (Class)ArrayElementHandler.class);
/*  83 */     setElementHandler("class", (Class)ClassElementHandler.class);
/*  84 */     setElementHandler("string", (Class)StringElementHandler.class);
/*  85 */     setElementHandler("object", (Class)ObjectElementHandler.class);
/*     */     
/*  87 */     setElementHandler("void", (Class)VoidElementHandler.class);
/*  88 */     setElementHandler("char", (Class)CharElementHandler.class);
/*  89 */     setElementHandler("byte", (Class)ByteElementHandler.class);
/*  90 */     setElementHandler("short", (Class)ShortElementHandler.class);
/*  91 */     setElementHandler("int", (Class)IntElementHandler.class);
/*  92 */     setElementHandler("long", (Class)LongElementHandler.class);
/*  93 */     setElementHandler("float", (Class)FloatElementHandler.class);
/*  94 */     setElementHandler("double", (Class)DoubleElementHandler.class);
/*  95 */     setElementHandler("boolean", (Class)BooleanElementHandler.class);
/*     */ 
/*     */     
/*  98 */     setElementHandler("new", (Class)NewElementHandler.class);
/*  99 */     setElementHandler("var", (Class)VarElementHandler.class);
/* 100 */     setElementHandler("true", (Class)TrueElementHandler.class);
/* 101 */     setElementHandler("false", (Class)FalseElementHandler.class);
/* 102 */     setElementHandler("field", (Class)FieldElementHandler.class);
/* 103 */     setElementHandler("method", (Class)MethodElementHandler.class);
/* 104 */     setElementHandler("property", (Class)PropertyElementHandler.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassLoader getClassLoader() {
/* 115 */     return (this.loader != null) ? this.loader
/* 116 */       .get() : null;
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
/*     */   public void setClassLoader(ClassLoader paramClassLoader) {
/* 128 */     this.loader = new WeakReference<>(paramClassLoader);
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
/*     */   public ExceptionListener getExceptionListener() {
/* 141 */     return this.listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExceptionListener(ExceptionListener paramExceptionListener) {
/* 152 */     this.listener = paramExceptionListener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getOwner() {
/* 161 */     return this.owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOwner(Object paramObject) {
/* 170 */     this.owner = paramObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<? extends ElementHandler> getElementHandler(String paramString) {
/* 180 */     Class<? extends ElementHandler> clazz = this.handlers.get(paramString);
/* 181 */     if (clazz == null) {
/* 182 */       throw new IllegalArgumentException("Unsupported element: " + paramString);
/*     */     }
/* 184 */     return clazz;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setElementHandler(String paramString, Class<? extends ElementHandler> paramClass) {
/* 194 */     this.handlers.put(paramString, paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasVariable(String paramString) {
/* 205 */     return this.environment.containsKey(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getVariable(String paramString) {
/* 215 */     if (!this.environment.containsKey(paramString)) {
/* 216 */       throw new IllegalArgumentException("Unbound variable: " + paramString);
/*     */     }
/* 218 */     return this.environment.get(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVariable(String paramString, Object paramObject) {
/* 228 */     this.environment.put(paramString, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] getObjects() {
/* 237 */     return this.objects.toArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addObject(Object paramObject) {
/* 246 */     this.objects.add(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputSource resolveEntity(String paramString1, String paramString2) {
/* 254 */     return new InputSource(new StringReader(""));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDocument() {
/* 262 */     this.objects.clear();
/* 263 */     this.handler = null;
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
/*     */   public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes) throws SAXException {
/* 281 */     ElementHandler elementHandler = this.handler;
/*     */     try {
/* 283 */       this.handler = getElementHandler(paramString3).newInstance();
/* 284 */       this.handler.setOwner(this);
/* 285 */       this.handler.setParent(elementHandler);
/*     */     }
/* 287 */     catch (Exception exception) {
/* 288 */       throw new SAXException(exception);
/*     */     } 
/* 290 */     for (byte b = 0; b < paramAttributes.getLength(); b++) {
/*     */       try {
/* 292 */         String str1 = paramAttributes.getQName(b);
/* 293 */         String str2 = paramAttributes.getValue(b);
/* 294 */         this.handler.addAttribute(str1, str2);
/*     */       }
/* 296 */       catch (RuntimeException runtimeException) {
/* 297 */         handleException(runtimeException);
/*     */       } 
/*     */     } 
/* 300 */     this.handler.startElement();
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
/*     */   public void endElement(String paramString1, String paramString2, String paramString3) {
/*     */     try {
/* 318 */       this.handler.endElement();
/*     */     }
/* 320 */     catch (RuntimeException runtimeException) {
/* 321 */       handleException(runtimeException);
/*     */     } finally {
/*     */       
/* 324 */       this.handler = this.handler.getParent();
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
/*     */   public void characters(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 337 */     if (this.handler != null) {
/*     */       try {
/* 339 */         while (0 < paramInt2--) {
/* 340 */           this.handler.addCharacter(paramArrayOfchar[paramInt1++]);
/*     */         }
/*     */       }
/* 343 */       catch (RuntimeException runtimeException) {
/* 344 */         handleException(runtimeException);
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
/*     */   public void handleException(Exception paramException) {
/* 356 */     if (this.listener == null) {
/* 357 */       throw new IllegalStateException(paramException);
/*     */     }
/* 359 */     this.listener.exceptionThrown(paramException);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parse(final InputSource input) {
/* 368 */     if (this.acc == null && null != System.getSecurityManager()) {
/* 369 */       throw new SecurityException("AccessControlContext is not set");
/*     */     }
/* 371 */     AccessControlContext accessControlContext = AccessController.getContext();
/* 372 */     SharedSecrets.getJavaSecurityAccess().doIntersectionPrivilege(new PrivilegedAction<Void>() {
/*     */           public Void run() {
/*     */             try {
/* 375 */               SAXParserFactory.newInstance().newSAXParser().parse(input, DocumentHandler.this);
/*     */             }
/* 377 */             catch (ParserConfigurationException parserConfigurationException) {
/* 378 */               DocumentHandler.this.handleException(parserConfigurationException);
/*     */             }
/* 380 */             catch (SAXException sAXException) {
/* 381 */               Exception exception = sAXException.getException();
/* 382 */               if (exception == null) {
/* 383 */                 exception = sAXException;
/*     */               }
/* 385 */               DocumentHandler.this.handleException(exception);
/*     */             }
/* 387 */             catch (IOException iOException) {
/* 388 */               DocumentHandler.this.handleException(iOException);
/*     */             } 
/* 390 */             return null;
/*     */           }
/*     */         }accessControlContext, this.acc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> findClass(String paramString) {
/*     */     try {
/* 404 */       return ClassFinder.resolveClass(paramString, getClassLoader());
/*     */     }
/* 406 */     catch (ClassNotFoundException classNotFoundException) {
/* 407 */       handleException(classNotFoundException);
/* 408 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\beans\decoder\DocumentHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */