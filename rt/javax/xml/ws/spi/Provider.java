/*     */ package javax.xml.ws.spi;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.Endpoint;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ import javax.xml.ws.Service;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.wsaddressing.W3CEndpointReference;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Provider
/*     */ {
/*     */   public static final String JAXWSPROVIDER_PROPERTY = "javax.xml.ws.spi.Provider";
/*     */   static final String DEFAULT_JAXWSPROVIDER = "com.sun.xml.internal.ws.spi.ProviderImpl";
/*     */   private static final Method loadMethod;
/*     */   private static final Method iteratorMethod;
/*     */   
/*     */   static {
/*  71 */     Method tLoadMethod = null;
/*  72 */     Method tIteratorMethod = null;
/*     */     try {
/*  74 */       Class<?> clazz = Class.forName("java.util.ServiceLoader");
/*  75 */       tLoadMethod = clazz.getMethod("load", new Class[] { Class.class });
/*  76 */       tIteratorMethod = clazz.getMethod("iterator", new Class[0]);
/*  77 */     } catch (ClassNotFoundException classNotFoundException) {
/*     */     
/*  79 */     } catch (NoSuchMethodException noSuchMethodException) {}
/*     */ 
/*     */     
/*  82 */     loadMethod = tLoadMethod;
/*  83 */     iteratorMethod = tIteratorMethod;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Provider provider() {
/*     */     try {
/* 125 */       Object provider = getProviderUsingServiceLoader();
/* 126 */       if (provider == null) {
/* 127 */         provider = FactoryFinder.find("javax.xml.ws.spi.Provider", "com.sun.xml.internal.ws.spi.ProviderImpl");
/*     */       }
/* 129 */       if (!(provider instanceof Provider)) {
/* 130 */         Class<Provider> pClass = Provider.class;
/* 131 */         String classnameAsResource = pClass.getName().replace('.', '/') + ".class";
/* 132 */         ClassLoader loader = pClass.getClassLoader();
/* 133 */         if (loader == null) {
/* 134 */           loader = ClassLoader.getSystemClassLoader();
/*     */         }
/* 136 */         URL targetTypeURL = loader.getResource(classnameAsResource);
/* 137 */         throw new LinkageError("ClassCastException: attempting to cast" + provider
/* 138 */             .getClass().getClassLoader().getResource(classnameAsResource) + "to" + targetTypeURL
/* 139 */             .toString());
/*     */       } 
/* 141 */       return (Provider)provider;
/* 142 */     } catch (WebServiceException ex) {
/* 143 */       throw ex;
/* 144 */     } catch (Exception ex) {
/* 145 */       throw new WebServiceException("Unable to createEndpointReference Provider", ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static Provider getProviderUsingServiceLoader() {
/* 151 */     if (loadMethod != null) {
/*     */       Object loader; Iterator<Provider> it;
/*     */       try {
/* 154 */         loader = loadMethod.invoke(null, new Object[] { Provider.class });
/* 155 */       } catch (Exception e) {
/* 156 */         throw new WebServiceException("Cannot invoke java.util.ServiceLoader#load()", e);
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 161 */         it = (Iterator<Provider>)iteratorMethod.invoke(loader, new Object[0]);
/* 162 */       } catch (Exception e) {
/* 163 */         throw new WebServiceException("Cannot invoke java.util.ServiceLoader#iterator()", e);
/*     */       } 
/* 165 */       return it.hasNext() ? it.next() : null;
/*     */     } 
/* 167 */     return null;
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
/*     */   
/*     */   public ServiceDelegate createServiceDelegate(URL wsdlDocumentLocation, QName serviceName, Class<? extends Service> serviceClass, WebServiceFeature... features) {
/* 202 */     throw new UnsupportedOperationException("JAX-WS 2.2 implementation must override this default behaviour.");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public W3CEndpointReference createW3CEndpointReference(String address, QName interfaceName, QName serviceName, QName portName, List<Element> metadata, String wsdlDocumentLocation, List<Element> referenceParameters, List<Element> elements, Map<QName, String> attributes) {
/* 449 */     throw new UnsupportedOperationException("JAX-WS 2.2 implementation must override this default behaviour.");
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
/*     */   public Endpoint createAndPublishEndpoint(String address, Object implementor, WebServiceFeature... features) {
/* 474 */     throw new UnsupportedOperationException("JAX-WS 2.2 implementation must override this default behaviour.");
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
/*     */   public Endpoint createEndpoint(String bindingId, Object implementor, WebServiceFeature... features) {
/* 495 */     throw new UnsupportedOperationException("JAX-WS 2.2 implementation must override this default behaviour.");
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
/*     */   public Endpoint createEndpoint(String bindingId, Class<?> implementorClass, Invoker invoker, WebServiceFeature... features) {
/* 518 */     throw new UnsupportedOperationException("JAX-WS 2.2 implementation must override this default behaviour.");
/*     */   }
/*     */   
/*     */   public abstract ServiceDelegate createServiceDelegate(URL paramURL, QName paramQName, Class<? extends Service> paramClass);
/*     */   
/*     */   public abstract Endpoint createEndpoint(String paramString, Object paramObject);
/*     */   
/*     */   public abstract Endpoint createAndPublishEndpoint(String paramString, Object paramObject);
/*     */   
/*     */   public abstract EndpointReference readEndpointReference(Source paramSource);
/*     */   
/*     */   public abstract <T> T getPort(EndpointReference paramEndpointReference, Class<T> paramClass, WebServiceFeature... paramVarArgs);
/*     */   
/*     */   public abstract W3CEndpointReference createW3CEndpointReference(String paramString1, QName paramQName1, QName paramQName2, List<Element> paramList1, String paramString2, List<Element> paramList2);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\ws\spi\Provider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */