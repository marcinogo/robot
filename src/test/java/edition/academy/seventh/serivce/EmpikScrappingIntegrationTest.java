package edition.academy.seventh.serivce;

import edition.academy.seventh.database.model.Book;
import java.io.IOException;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Kacper Staszek
 *
 * Integration test of EmpikScrapping class.
 */
@Test
public class EmpikScrappingIntegrationTest {

  private static final int EXPECTED_BOOKS_SIZE_AFTER_SCRAPPING = 1230;

  public void checkIfScrappingGeneratesBooks()
      throws IOException {
    IPromotionScrapping promotionScrapping = new EmpikScrapping();
    List<Book> books = promotionScrapping.scrapPromotion();
    Assert.assertEquals(books.size(), EXPECTED_BOOKS_SIZE_AFTER_SCRAPPING);
  }
}