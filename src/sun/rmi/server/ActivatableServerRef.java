/*    */ package sun.rmi.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.NotSerializableException;
/*    */ import java.io.ObjectOutput;
/*    */ import java.rmi.activation.ActivationID;
/*    */ import java.rmi.server.RMIClientSocketFactory;
/*    */ import java.rmi.server.RMIServerSocketFactory;
/*    */ import java.rmi.server.RemoteRef;
/*    */ import sun.rmi.transport.LiveRef;
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
/*    */ 
/*    */ 
/*    */ public class ActivatableServerRef
/*    */   extends UnicastServerRef2
/*    */ {
/*    */   private static final long serialVersionUID = 2002967993223003793L;
/*    */   private ActivationID id;
/*    */   
/*    */   public ActivatableServerRef(ActivationID paramActivationID, int paramInt) {
/* 53 */     this(paramActivationID, paramInt, null, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ActivatableServerRef(ActivationID paramActivationID, int paramInt, RMIClientSocketFactory paramRMIClientSocketFactory, RMIServerSocketFactory paramRMIServerSocketFactory) {
/* 64 */     super(new LiveRef(paramInt, paramRMIClientSocketFactory, paramRMIServerSocketFactory));
/* 65 */     this.id = paramActivationID;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getRefClass(ObjectOutput paramObjectOutput) {
/* 73 */     return "ActivatableServerRef";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected RemoteRef getClientRef() {
/* 83 */     return new ActivatableRef(this.id, new UnicastRef2(this.ref));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeExternal(ObjectOutput paramObjectOutput) throws IOException {
/* 90 */     throw new NotSerializableException("ActivatableServerRef not serializable");
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\rmi\server\ActivatableServerRef.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */