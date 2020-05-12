/*     */ package javax.management;
/*     */ 
/*     */ import com.sun.jmx.mbeanserver.GetPropertyAction;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamField;
/*     */ import java.security.AccessController;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class NumericValueExp
/*     */   extends QueryEval
/*     */   implements ValueExp
/*     */ {
/*     */   private static final long oldSerialVersionUID = -6227876276058904000L;
/*     */   private static final long newSerialVersionUID = -4679739485102359104L;
/*  64 */   private static final ObjectStreamField[] oldSerialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("longVal", long.class), new ObjectStreamField("doubleVal", double.class), new ObjectStreamField("valIsLong", boolean.class) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   private static final ObjectStreamField[] newSerialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("val", Number.class) };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final ObjectStreamField[] serialPersistentFields;
/*     */ 
/*     */ 
/*     */   
/*  86 */   private Number val = Double.valueOf(0.0D);
/*     */   private static boolean compat = false;
/*     */   
/*     */   static {
/*     */     try {
/*  91 */       GetPropertyAction getPropertyAction = new GetPropertyAction("jmx.serial.form");
/*  92 */       String str = AccessController.<String>doPrivileged(getPropertyAction);
/*  93 */       compat = (str != null && str.equals("1.0"));
/*  94 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/*  97 */     if (compat) {
/*  98 */       serialPersistentFields = oldSerialPersistentFields;
/*  99 */       serialVersionUID = -6227876276058904000L;
/*     */     } else {
/* 101 */       serialPersistentFields = newSerialPersistentFields;
/* 102 */       serialVersionUID = -4679739485102359104L;
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
/*     */   NumericValueExp(Number paramNumber) {
/* 118 */     this.val = paramNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double doubleValue() {
/* 125 */     if (this.val instanceof Long || this.val instanceof Integer)
/*     */     {
/* 127 */       return this.val.longValue();
/*     */     }
/* 129 */     return this.val.doubleValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long longValue() {
/* 136 */     if (this.val instanceof Long || this.val instanceof Integer)
/*     */     {
/* 138 */       return this.val.longValue();
/*     */     }
/* 140 */     return (long)this.val.doubleValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLong() {
/* 147 */     return (this.val instanceof Long || this.val instanceof Integer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 154 */     if (this.val == null)
/* 155 */       return "null"; 
/* 156 */     if (this.val instanceof Long || this.val instanceof Integer)
/*     */     {
/* 158 */       return Long.toString(this.val.longValue());
/*     */     }
/* 160 */     double d = this.val.doubleValue();
/* 161 */     if (Double.isInfinite(d))
/* 162 */       return (d > 0.0D) ? "(1.0 / 0.0)" : "(-1.0 / 0.0)"; 
/* 163 */     if (Double.isNaN(d))
/* 164 */       return "(0.0 / 0.0)"; 
/* 165 */     return Double.toString(d);
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
/*     */   public ValueExp apply(ObjectName paramObjectName) throws BadStringOperationException, BadBinaryOpValueExpException, BadAttributeValueExpException, InvalidApplicationException {
/* 183 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 191 */     if (compat) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 198 */       ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/* 199 */       double d = getField.get("doubleVal", 0.0D);
/* 200 */       if (getField.defaulted("doubleVal"))
/*     */       {
/* 202 */         throw new NullPointerException("doubleVal");
/*     */       }
/* 204 */       long l = getField.get("longVal", 0L);
/* 205 */       if (getField.defaulted("longVal"))
/*     */       {
/* 207 */         throw new NullPointerException("longVal");
/*     */       }
/* 209 */       boolean bool = getField.get("valIsLong", false);
/* 210 */       if (getField.defaulted("valIsLong"))
/*     */       {
/* 212 */         throw new NullPointerException("valIsLong");
/*     */       }
/* 214 */       if (bool)
/*     */       {
/* 216 */         this.val = Long.valueOf(l);
/*     */       }
/*     */       else
/*     */       {
/* 220 */         this.val = Double.valueOf(d);
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 227 */       paramObjectInputStream.defaultReadObject();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 237 */     if (compat) {
/*     */ 
/*     */ 
/*     */       
/* 241 */       ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 242 */       putField.put("doubleVal", doubleValue());
/* 243 */       putField.put("longVal", longValue());
/* 244 */       putField.put("valIsLong", isLong());
/* 245 */       paramObjectOutputStream.writeFields();
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 251 */       paramObjectOutputStream.defaultWriteObject();
/*     */     } 
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void setMBeanServer(MBeanServer paramMBeanServer) {
/* 257 */     super.setMBeanServer(paramMBeanServer);
/*     */   }
/*     */   
/*     */   public NumericValueExp() {}
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\management\NumericValueExp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */