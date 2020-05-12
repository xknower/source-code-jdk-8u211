package javax.management.remote;

import javax.security.auth.Subject;

public interface JMXAuthenticator {
  Subject authenticate(Object paramObject);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\management\remote\JMXAuthenticator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */