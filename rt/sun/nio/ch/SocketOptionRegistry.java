/*    */ package sun.nio.ch;
/*    */ 
/*    */ import java.net.ProtocolFamily;
/*    */ import java.net.SocketOption;
/*    */ import java.net.StandardProtocolFamily;
/*    */ import java.net.StandardSocketOptions;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ class SocketOptionRegistry
/*    */ {
/*    */   private static class RegistryKey
/*    */   {
/*    */     private final SocketOption<?> name;
/*    */     private final ProtocolFamily family;
/*    */     
/*    */     RegistryKey(SocketOption<?> param1SocketOption, ProtocolFamily param1ProtocolFamily) {
/* 41 */       this.name = param1SocketOption;
/* 42 */       this.family = param1ProtocolFamily;
/*    */     }
/*    */     public int hashCode() {
/* 45 */       return this.name.hashCode() + this.family.hashCode();
/*    */     }
/*    */     public boolean equals(Object param1Object) {
/* 48 */       if (param1Object == null) return false; 
/* 49 */       if (!(param1Object instanceof RegistryKey)) return false; 
/* 50 */       RegistryKey registryKey = (RegistryKey)param1Object;
/* 51 */       if (this.name != registryKey.name) return false; 
/* 52 */       if (this.family != registryKey.family) return false; 
/* 53 */       return true;
/*    */     }
/*    */   }
/*    */   
/* 57 */   private static class LazyInitialization { static final Map<SocketOptionRegistry.RegistryKey, OptionKey> options = options();
/*    */     private static Map<SocketOptionRegistry.RegistryKey, OptionKey> options() {
/* 59 */       HashMap<Object, Object> hashMap = new HashMap<>();
/*    */       
/* 61 */       hashMap.put(new SocketOptionRegistry.RegistryKey(StandardSocketOptions.SO_BROADCAST, Net.UNSPEC), new OptionKey(65535, 32));
/* 62 */       hashMap.put(new SocketOptionRegistry.RegistryKey(StandardSocketOptions.SO_KEEPALIVE, Net.UNSPEC), new OptionKey(65535, 8));
/* 63 */       hashMap.put(new SocketOptionRegistry.RegistryKey(StandardSocketOptions.SO_LINGER, Net.UNSPEC), new OptionKey(65535, 128));
/* 64 */       hashMap.put(new SocketOptionRegistry.RegistryKey(StandardSocketOptions.SO_SNDBUF, Net.UNSPEC), new OptionKey(65535, 4097));
/* 65 */       hashMap.put(new SocketOptionRegistry.RegistryKey(StandardSocketOptions.SO_RCVBUF, Net.UNSPEC), new OptionKey(65535, 4098));
/* 66 */       hashMap.put(new SocketOptionRegistry.RegistryKey(StandardSocketOptions.SO_REUSEADDR, Net.UNSPEC), new OptionKey(65535, 4));
/* 67 */       hashMap.put(new SocketOptionRegistry.RegistryKey(StandardSocketOptions.TCP_NODELAY, Net.UNSPEC), new OptionKey(6, 1));
/* 68 */       hashMap.put(new SocketOptionRegistry.RegistryKey(StandardSocketOptions.IP_TOS, StandardProtocolFamily.INET), new OptionKey(0, 3));
/* 69 */       hashMap.put(new SocketOptionRegistry.RegistryKey(StandardSocketOptions.IP_MULTICAST_IF, StandardProtocolFamily.INET), new OptionKey(0, 9));
/* 70 */       hashMap.put(new SocketOptionRegistry.RegistryKey(StandardSocketOptions.IP_MULTICAST_TTL, StandardProtocolFamily.INET), new OptionKey(0, 10));
/* 71 */       hashMap.put(new SocketOptionRegistry.RegistryKey(StandardSocketOptions.IP_MULTICAST_LOOP, StandardProtocolFamily.INET), new OptionKey(0, 11));
/* 72 */       hashMap.put(new SocketOptionRegistry.RegistryKey(StandardSocketOptions.IP_TOS, StandardProtocolFamily.INET6), new OptionKey(41, 39));
/* 73 */       hashMap.put(new SocketOptionRegistry.RegistryKey(StandardSocketOptions.IP_MULTICAST_IF, StandardProtocolFamily.INET6), new OptionKey(41, 9));
/* 74 */       hashMap.put(new SocketOptionRegistry.RegistryKey(StandardSocketOptions.IP_MULTICAST_TTL, StandardProtocolFamily.INET6), new OptionKey(41, 10));
/* 75 */       hashMap.put(new SocketOptionRegistry.RegistryKey(StandardSocketOptions.IP_MULTICAST_LOOP, StandardProtocolFamily.INET6), new OptionKey(41, 11));
/* 76 */       hashMap.put(new SocketOptionRegistry.RegistryKey(ExtendedSocketOption.SO_OOBINLINE, Net.UNSPEC), new OptionKey(65535, 256));
/* 77 */       return (Map)hashMap;
/*    */     } }
/*    */   
/*    */   public static OptionKey findOption(SocketOption<?> paramSocketOption, ProtocolFamily paramProtocolFamily) {
/* 81 */     RegistryKey registryKey = new RegistryKey(paramSocketOption, paramProtocolFamily);
/* 82 */     return LazyInitialization.options.get(registryKey);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\ch\SocketOptionRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */