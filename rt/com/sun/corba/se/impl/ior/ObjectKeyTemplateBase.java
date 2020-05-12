/*     */ package com.sun.corba.se.impl.ior;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.IORSystemException;
/*     */ import com.sun.corba.se.spi.ior.ObjectAdapterId;
/*     */ import com.sun.corba.se.spi.ior.ObjectId;
/*     */ import com.sun.corba.se.spi.ior.ObjectKeyTemplate;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.orb.ORBVersion;
/*     */ import com.sun.corba.se.spi.protocol.CorbaServerRequestDispatcher;
/*     */ import java.util.Iterator;
/*     */ import org.omg.CORBA_2_3.portable.InputStream;
/*     */ import org.omg.CORBA_2_3.portable.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ObjectKeyTemplateBase
/*     */   implements ObjectKeyTemplate
/*     */ {
/*     */   public static final String JIDL_ORB_ID = "";
/*  57 */   private static final String[] JIDL_OAID_STRINGS = new String[] { "TransientObjectAdapter" };
/*  58 */   public static final ObjectAdapterId JIDL_OAID = new ObjectAdapterIdArray(JIDL_OAID_STRINGS);
/*     */   
/*     */   private ORB orb;
/*     */   
/*     */   protected IORSystemException wrapper;
/*     */   
/*     */   private ORBVersion version;
/*     */   private int magic;
/*     */   private int scid;
/*     */   private int serverid;
/*     */   private String orbid;
/*     */   private ObjectAdapterId oaid;
/*     */   private byte[] adapterId;
/*     */   
/*     */   public byte[] getAdapterId() {
/*  73 */     return (byte[])this.adapterId.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] computeAdapterId() {
/*  79 */     ByteBuffer byteBuffer = new ByteBuffer();
/*     */     
/*  81 */     byteBuffer.append(getServerId());
/*  82 */     byteBuffer.append(this.orbid);
/*     */     
/*  84 */     byteBuffer.append(this.oaid.getNumLevels());
/*  85 */     Iterator<String> iterator = this.oaid.iterator();
/*  86 */     while (iterator.hasNext()) {
/*  87 */       String str = iterator.next();
/*  88 */       byteBuffer.append(str);
/*     */     } 
/*     */     
/*  91 */     byteBuffer.trimToSize();
/*     */     
/*  93 */     return byteBuffer.toArray();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectKeyTemplateBase(ORB paramORB, int paramInt1, int paramInt2, int paramInt3, String paramString, ObjectAdapterId paramObjectAdapterId) {
/*  99 */     this.orb = paramORB;
/* 100 */     this.wrapper = IORSystemException.get(paramORB, "oa.ior");
/*     */     
/* 102 */     this.magic = paramInt1;
/* 103 */     this.scid = paramInt2;
/* 104 */     this.serverid = paramInt3;
/* 105 */     this.orbid = paramString;
/* 106 */     this.oaid = paramObjectAdapterId;
/*     */     
/* 108 */     this.adapterId = computeAdapterId();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 113 */     if (!(paramObject instanceof ObjectKeyTemplateBase)) {
/* 114 */       return false;
/*     */     }
/* 116 */     ObjectKeyTemplateBase objectKeyTemplateBase = (ObjectKeyTemplateBase)paramObject;
/*     */     
/* 118 */     return (this.magic == objectKeyTemplateBase.magic && this.scid == objectKeyTemplateBase.scid && this.serverid == objectKeyTemplateBase.serverid && this.version
/* 119 */       .equals(objectKeyTemplateBase.version) && this.orbid
/* 120 */       .equals(objectKeyTemplateBase.orbid) && this.oaid.equals(objectKeyTemplateBase.oaid));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 125 */     int i = 17;
/* 126 */     i = 37 * i + this.magic;
/* 127 */     i = 37 * i + this.scid;
/* 128 */     i = 37 * i + this.serverid;
/* 129 */     i = 37 * i + this.version.hashCode();
/* 130 */     i = 37 * i + this.orbid.hashCode();
/* 131 */     i = 37 * i + this.oaid.hashCode();
/* 132 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSubcontractId() {
/* 137 */     return this.scid;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getServerId() {
/* 142 */     return this.serverid;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getORBId() {
/* 147 */     return this.orbid;
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectAdapterId getObjectAdapterId() {
/* 152 */     return this.oaid;
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ObjectId paramObjectId, OutputStream paramOutputStream) {
/* 157 */     writeTemplate(paramOutputStream);
/* 158 */     paramObjectId.write(paramOutputStream);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(OutputStream paramOutputStream) {
/* 163 */     writeTemplate(paramOutputStream);
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void writeTemplate(OutputStream paramOutputStream);
/*     */   
/*     */   protected int getMagic() {
/* 170 */     return this.magic;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setORBVersion(ORBVersion paramORBVersion) {
/* 177 */     this.version = paramORBVersion;
/*     */   }
/*     */ 
/*     */   
/*     */   public ORBVersion getORBVersion() {
/* 182 */     return this.version;
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte[] readObjectKey(InputStream paramInputStream) {
/* 187 */     int i = paramInputStream.read_long();
/* 188 */     byte[] arrayOfByte = new byte[i];
/* 189 */     paramInputStream.read_octet_array(arrayOfByte, 0, i);
/* 190 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   
/*     */   public CorbaServerRequestDispatcher getServerRequestDispatcher(ORB paramORB, ObjectId paramObjectId) {
/* 195 */     return paramORB.getRequestDispatcherRegistry().getServerRequestDispatcher(this.scid);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\ior\ObjectKeyTemplateBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */