package java.beans.beancontext;

import java.util.EventListener;

public interface BeanContextMembershipListener extends EventListener {
  void childrenAdded(BeanContextMembershipEvent paramBeanContextMembershipEvent);
  
  void childrenRemoved(BeanContextMembershipEvent paramBeanContextMembershipEvent);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\beans\beancontext\BeanContextMembershipListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */