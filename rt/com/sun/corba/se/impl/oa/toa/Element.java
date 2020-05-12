/*     */ package com.sun.corba.se.impl.oa.toa;
/*     */ 
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Element
/*     */ {
/* 156 */   Object servant = null;
/* 157 */   Object servantData = null;
/* 158 */   int index = -1;
/* 159 */   int counter = 0;
/*     */   
/*     */   boolean valid = false;
/*     */ 
/*     */   
/*     */   Element(int paramInt, Object paramObject) {
/* 165 */     this.servant = paramObject;
/* 166 */     this.index = paramInt;
/*     */   }
/*     */ 
/*     */   
/*     */   byte[] getKey(Object paramObject1, Object paramObject2) {
/* 171 */     this.servant = paramObject1;
/* 172 */     this.servantData = paramObject2;
/* 173 */     this.valid = true;
/*     */     
/* 175 */     return toBytes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   byte[] toBytes() {
/* 182 */     byte[] arrayOfByte = new byte[8];
/* 183 */     ORBUtility.intToBytes(this.index, arrayOfByte, 0);
/* 184 */     ORBUtility.intToBytes(this.counter, arrayOfByte, 4);
/*     */     
/* 186 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   
/*     */   void delete(Element paramElement) {
/* 191 */     if (!this.valid)
/*     */       return; 
/* 193 */     this.counter++;
/* 194 */     this.servantData = null;
/* 195 */     this.valid = false;
/*     */ 
/*     */     
/* 198 */     this.servant = paramElement;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 203 */     return "Element[" + this.index + ", " + this.counter + "]";
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\oa\toa\Element.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */