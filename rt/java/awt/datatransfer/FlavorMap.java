package java.awt.datatransfer;

import java.util.Map;

public interface FlavorMap {
  Map<DataFlavor, String> getNativesForFlavors(DataFlavor[] paramArrayOfDataFlavor);
  
  Map<String, DataFlavor> getFlavorsForNatives(String[] paramArrayOfString);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\datatransfer\FlavorMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */