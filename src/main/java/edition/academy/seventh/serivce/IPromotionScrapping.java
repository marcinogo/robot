package edition.academy.seventh.serivce;

import edition.academy.seventh.database.model.Book;

import java.io.IOException;
import java.util.List;

/**
 *
 * Provide API to get books on sale from given bookstore by scrapping.
 *
 * @author Bartosz Kupajski
 *
 *
 */
public interface IPromotionScrapping {

  /**
   * @return list of books ready to save in DataBase
   */
  List<Book> scrapPromotion();
}
