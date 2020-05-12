package javax.naming.spi;

import java.util.Hashtable;
import javax.naming.NamingException;

public interface InitialContextFactoryBuilder {
  InitialContextFactory createInitialContextFactory(Hashtable<?, ?> paramHashtable) throws NamingException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\naming\spi\InitialContextFactoryBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */