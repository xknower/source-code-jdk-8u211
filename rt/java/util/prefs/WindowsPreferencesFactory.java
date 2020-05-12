/*    */ package java.util.prefs;
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
/*    */ 
/*    */ class WindowsPreferencesFactory
/*    */   implements PreferencesFactory
/*    */ {
/*    */   public Preferences userRoot() {
/* 42 */     return WindowsPreferences.getUserRoot();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Preferences systemRoot() {
/* 49 */     return WindowsPreferences.getSystemRoot();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\prefs\WindowsPreferencesFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */