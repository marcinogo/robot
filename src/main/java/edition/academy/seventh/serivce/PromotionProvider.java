package edition.academy.seventh.serivce;

import edition.academy.seventh.database.model.Book;
import java.util.List;

/**
 *
 * Provide API to get books on sale from given bookstore by scrapping.
 *
 * @author Bartosz Kupajski
 *
 */
public interface PromotionProvider {

  /**
   * Retrieve books on sales from bookstore website.
   *
   * @return list of books ready to save in DataBase
   */
  List<Book> getPromotions();

}
