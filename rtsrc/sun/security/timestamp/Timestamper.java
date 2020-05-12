package sun.security.timestamp;

import java.io.IOException;

public interface Timestamper {
  TSResponse generateTimestamp(TSRequest paramTSRequest) throws IOException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\timestamp\Timestamper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */