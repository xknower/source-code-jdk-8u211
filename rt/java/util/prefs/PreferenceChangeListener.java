package java.util.prefs;

import java.util.EventListener;

@FunctionalInterface
public interface PreferenceChangeListener extends EventListener {
  void preferenceChange(PreferenceChangeEvent paramPreferenceChangeEvent);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\prefs\PreferenceChangeListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */