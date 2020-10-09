package com.sun.xml.internal.ws.api.server;

import com.sun.istack.internal.NotNull;
import com.sun.xml.internal.ws.api.Component;
import java.net.URI;

public interface BoundEndpoint extends Component {
  @NotNull
  WSEndpoint getEndpoint();
  
  @NotNull
  URI getAddress();
  
  @NotNull
  URI getAddress(String paramString);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\server\BoundEndpoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */