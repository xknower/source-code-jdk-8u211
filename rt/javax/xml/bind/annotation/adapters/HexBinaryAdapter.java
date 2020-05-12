/*    */ package javax.xml.bind.annotation.adapters;
/*    */ 
/*    */ import javax.xml.bind.DatatypeConverter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class HexBinaryAdapter
/*    */   extends XmlAdapter<String, byte[]>
/*    */ {
/*    */   public byte[] unmarshal(String s) {
/* 41 */     if (s == null) return null; 
/* 42 */     return DatatypeConverter.parseHexBinary(s);
/*    */   }
/*    */   
/*    */   public String marshal(byte[] bytes) {
/* 46 */     if (bytes == null) return null; 
/* 47 */     return DatatypeConverter.printHexBinary(bytes);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\bind\annotation\adapters\HexBinaryAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */