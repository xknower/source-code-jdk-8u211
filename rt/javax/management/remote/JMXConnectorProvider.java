package javax.management.remote;

import java.io.IOException;
import java.util.Map;

public interface JMXConnectorProvider {
  JMXConnector newJMXConnector(JMXServiceURL paramJMXServiceURL, Map<String, ?> paramMap) throws IOException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\management\remote\JMXConnectorProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */