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

  public void should_scrapAtLeastOneBook_forGivenUrl()
      throws IOException {
    // Given
    IPromotionScrapping promotionScrapping = new EmpikScrapping();

    // When
    List<Book> books = promotionScrapping.scrapPromotion();

    // Then
    Assert.assertTrue(books.size()>0);
  }
}