package edition.academy.seventh.service;

/**
 * This exception will be thrown by the {@link PromotionProviderManager} getPromotionalPrice()
 * method when none of providers were found nor detected.
 *
 * @author Kamil Rojek
 */
public class ProvidersNotFoundException extends Exception {

  /**
   * Constructs a <code>ProvidersNotFoundException</code> with the specified detail message. The
   * string <code>message</code> can be retrieved later by the <code>
   * {@link java.lang.Throwable#getMessage}</code> method of class <code>java.lang.Throwable</code>.
   *
   * @param message the detail message.
   */
  ProvidersNotFoundException(String message) {
    super(message);
  }
}
