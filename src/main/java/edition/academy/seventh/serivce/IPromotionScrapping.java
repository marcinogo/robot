package edition.academy.seventh.serivce;

import edition.academy.seventh.database.model.Book;

import java.io.IOException;
import java.util.List;

/**
 * @author Bartosz Kupajski
 */
public interface IPromotionScrapping {

    List<Book> scrapPromotion() throws IOException;

}
