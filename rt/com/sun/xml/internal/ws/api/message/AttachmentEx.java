package com.sun.xml.internal.ws.api.message;

import com.sun.istack.internal.NotNull;
import java.util.Iterator;

public interface AttachmentEx extends Attachment {
  @NotNull
  Iterator<MimeHeader> getMimeHeaders();
  
  public static interface MimeHeader {
    String getName();
    
    String getValue();
  }
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\message\AttachmentEx.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */