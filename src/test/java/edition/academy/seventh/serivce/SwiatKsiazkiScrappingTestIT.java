package edition.academy.seventh.serivce;

import edition.academy.seventh.database.model.Book;
import java.io.IOException;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Kacper Staszek
 *
 * Integration test of SwiatKsiazkiScrapping class.
 */
@Test
public class SwiatKsiazkiScrappingTestIT {

  public void checkIfScrappingGeneratesBooks()
      throws IOException {
    IPromotionScrapping promotionScrapping = new SwiatKsiazkiScrapping();
    List<Book> books = promotionScrapping.scrapPromotion();
    Assert.assertTrue(books.size() > 1);
  }
}