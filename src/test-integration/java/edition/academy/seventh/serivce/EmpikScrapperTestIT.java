package edition.academy.seventh.serivce;

import edition.academy.seventh.database.model.Book;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Kacper Staszek
 *
 * Integration test of EmpikScrapper class.
 */
@Test
public class EmpikScrapperTestIT {

  public void should_scrapAtLeastOneBook_forGivenUrl() {
    // Given
    PromotionProvider promotionScrapping = new EmpikScrapper();

    // When
    List<Book> books = promotionScrapping.getPromotions();

    // Then
    Assert.assertTrue(books.size()>0);
  }
}