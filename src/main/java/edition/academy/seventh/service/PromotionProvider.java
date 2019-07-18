package edition.academy.seventh.service;

import edition.academy.seventh.database.model.DTBook;

import java.util.List;

/**
 * Provides API to get books on sale from given bookstore by scrapping.
 *
 * @author Bartosz Kupajski
 */
public interface PromotionProvider {

  /**
   * Retrieves books on sales from bookstore website.
   *
   * @return list of books ready to save in DataBase
   */
  List<DTBook> getPromotions();
}
