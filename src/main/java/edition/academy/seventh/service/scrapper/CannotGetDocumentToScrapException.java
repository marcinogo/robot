package edition.academy.seventh.service.scrapper;

/**
 * @author Kacper Staszek
 */
class CannotGetDocumentToScrapException extends Exception {

  CannotGetDocumentToScrapException(String message) {
    super(message);
  }
}
