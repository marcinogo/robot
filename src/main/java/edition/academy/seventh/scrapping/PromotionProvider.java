package edition.academy.seventh.scrapping;

import edition.academy.seventh.persistence.response.BookDto;

import java.util.List;

/**
 * Provides API to get books on sale from given bookstore by scrapping.
 *
 * @author Bartosz Kupajski
 */
interface PromotionProvider {

  /**
   * Retrieves books on sales from bookstore website.
   *
   * @return list of books ready to saveUser in DataBase
   */
  List<BookDto> getPromotions();
}
