/*     */ package com.sun.corba.se.spi.ior.iiop;
/*     */ 
/*     */ import com.sun.corba.se.impl.ior.iiop.AlternateIIOPAddressComponentImpl;
/*     */ import com.sun.corba.se.impl.ior.iiop.CodeSetsComponentImpl;
/*     */ import com.sun.corba.se.impl.ior.iiop.IIOPAddressImpl;
/*     */ import com.sun.corba.se.impl.ior.iiop.IIOPProfileImpl;
/*     */ import com.sun.corba.se.impl.ior.iiop.IIOPProfileTemplateImpl;
/*     */ import com.sun.corba.se.impl.ior.iiop.JavaCodebaseComponentImpl;
/*     */ import com.sun.corba.se.impl.ior.iiop.JavaSerializationComponent;
/*     */ import com.sun.corba.se.impl.ior.iiop.MaxStreamFormatVersionComponentImpl;
/*     */ import com.sun.corba.se.impl.ior.iiop.ORBTypeComponentImpl;
/*     */ import com.sun.corba.se.impl.ior.iiop.RequestPartitioningComponentImpl;
/*     */ import com.sun.corba.se.spi.ior.EncapsulationFactoryBase;
/*     */ import com.sun.corba.se.spi.ior.Identifiable;
/*     */ import com.sun.corba.se.spi.ior.IdentifiableFactory;
/*     */ import com.sun.corba.se.spi.ior.ObjectId;
/*     */ import com.sun.corba.se.spi.ior.ObjectKeyTemplate;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import org.omg.CORBA_2_3.portable.InputStream;
/*     */ import org.omg.IOP.TaggedProfile;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class IIOPFactories
/*     */ {
/*     */   public static IdentifiableFactory makeRequestPartitioningComponentFactory() {
/*  74 */     return new EncapsulationFactoryBase(1398099457)
/*     */       {
/*     */         public Identifiable readContents(InputStream param1InputStream) {
/*  77 */           int i = param1InputStream.read_ulong();
/*  78 */           return new RequestPartitioningComponentImpl(i);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RequestPartitioningComponent makeRequestPartitioningComponent(int paramInt) {
/*  88 */     return new RequestPartitioningComponentImpl(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public static IdentifiableFactory makeAlternateIIOPAddressComponentFactory() {
/*  93 */     return new EncapsulationFactoryBase(3)
/*     */       {
/*     */         public Identifiable readContents(InputStream param1InputStream) {
/*  96 */           IIOPAddressImpl iIOPAddressImpl = new IIOPAddressImpl(param1InputStream);
/*  97 */           return new AlternateIIOPAddressComponentImpl(iIOPAddressImpl);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AlternateIIOPAddressComponent makeAlternateIIOPAddressComponent(IIOPAddress paramIIOPAddress) {
/* 107 */     return new AlternateIIOPAddressComponentImpl(paramIIOPAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   public static IdentifiableFactory makeCodeSetsComponentFactory() {
/* 112 */     return new EncapsulationFactoryBase(1)
/*     */       {
/*     */         public Identifiable readContents(InputStream param1InputStream) {
/* 115 */           return new CodeSetsComponentImpl(param1InputStream);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static CodeSetsComponent makeCodeSetsComponent(ORB paramORB) {
/* 122 */     return new CodeSetsComponentImpl(paramORB);
/*     */   }
/*     */ 
/*     */   
/*     */   public static IdentifiableFactory makeJavaCodebaseComponentFactory() {
/* 127 */     return new EncapsulationFactoryBase(25)
/*     */       {
/*     */         public Identifiable readContents(InputStream param1InputStream) {
/* 130 */           String str = param1InputStream.read_string();
/* 131 */           return new JavaCodebaseComponentImpl(str);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JavaCodebaseComponent makeJavaCodebaseComponent(String paramString) {
/* 140 */     return new JavaCodebaseComponentImpl(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public static IdentifiableFactory makeORBTypeComponentFactory() {
/* 145 */     return new EncapsulationFactoryBase(0)
/*     */       {
/*     */         public Identifiable readContents(InputStream param1InputStream) {
/* 148 */           int i = param1InputStream.read_ulong();
/* 149 */           return new ORBTypeComponentImpl(i);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ORBTypeComponent makeORBTypeComponent(int paramInt) {
/* 157 */     return new ORBTypeComponentImpl(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public static IdentifiableFactory makeMaxStreamFormatVersionComponentFactory() {
/* 162 */     return new EncapsulationFactoryBase(38)
/*     */       {
/*     */         public Identifiable readContents(InputStream param1InputStream) {
/* 165 */           byte b = param1InputStream.read_octet();
/* 166 */           return new MaxStreamFormatVersionComponentImpl(b);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static MaxStreamFormatVersionComponent makeMaxStreamFormatVersionComponent() {
/* 174 */     return new MaxStreamFormatVersionComponentImpl();
/*     */   }
/*     */   
/*     */   public static IdentifiableFactory makeJavaSerializationComponentFactory() {
/* 178 */     return new EncapsulationFactoryBase(1398099458)
/*     */       {
/*     */         public Identifiable readContents(InputStream param1InputStream) {
/* 181 */           byte b = param1InputStream.read_octet();
/* 182 */           return new JavaSerializationComponent(b);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static JavaSerializationComponent makeJavaSerializationComponent() {
/* 189 */     return JavaSerializationComponent.singleton();
/*     */   }
/*     */ 
/*     */   
/*     */   public static IdentifiableFactory makeIIOPProfileFactory() {
/* 194 */     return new EncapsulationFactoryBase(0)
/*     */       {
/*     */         public Identifiable readContents(InputStream param1InputStream) {
/* 197 */           return new IIOPProfileImpl(param1InputStream);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IIOPProfile makeIIOPProfile(ORB paramORB, ObjectKeyTemplate paramObjectKeyTemplate, ObjectId paramObjectId, IIOPProfileTemplate paramIIOPProfileTemplate) {
/* 206 */     return new IIOPProfileImpl(paramORB, paramObjectKeyTemplate, paramObjectId, paramIIOPProfileTemplate);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static IIOPProfile makeIIOPProfile(ORB paramORB, TaggedProfile paramTaggedProfile) {
/* 212 */     return new IIOPProfileImpl(paramORB, paramTaggedProfile);
/*     */   }
/*     */ 
/*     */   
/*     */   public static IdentifiableFactory makeIIOPProfileTemplateFactory() {
/* 217 */     return new EncapsulationFactoryBase(0)
/*     */       {
/*     */         public Identifiable readContents(InputStream param1InputStream) {
/* 220 */           return new IIOPProfileTemplateImpl(param1InputStream);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IIOPProfileTemplate makeIIOPProfileTemplate(ORB paramORB, GIOPVersion paramGIOPVersion, IIOPAddress paramIIOPAddress) {
/* 229 */     return new IIOPProfileTemplateImpl(paramORB, paramGIOPVersion, paramIIOPAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   public static IIOPAddress makeIIOPAddress(ORB paramORB, String paramString, int paramInt) {
/* 234 */     return new IIOPAddressImpl(paramORB, paramString, paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public static IIOPAddress makeIIOPAddress(InputStream paramInputStream) {
/* 239 */     return new IIOPAddressImpl(paramInputStream);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\ior\iiop\IIOPFactories.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */