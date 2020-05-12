package javax.imageio.spi;

public interface RegisterableService {
  void onRegistration(ServiceRegistry paramServiceRegistry, Class<?> paramClass);
  
  void onDeregistration(ServiceRegistry paramServiceRegistry, Class<?> paramClass);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\imageio\spi\RegisterableService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */