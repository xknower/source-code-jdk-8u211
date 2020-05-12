package javax.security.auth.login;

public abstract class ConfigurationSpi {
  protected abstract AppConfigurationEntry[] engineGetAppConfigurationEntry(String paramString);
  
  protected void engineRefresh() {}
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\security\auth\login\ConfigurationSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */