package java.nio.file;

import java.io.IOException;

public interface Watchable {
  WatchKey register(WatchService paramWatchService, WatchEvent.Kind<?>[] paramArrayOfKind, WatchEvent.Modifier... paramVarArgs) throws IOException;
  
  WatchKey register(WatchService paramWatchService, WatchEvent.Kind<?>... paramVarArgs) throws IOException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\nio\file\Watchable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */