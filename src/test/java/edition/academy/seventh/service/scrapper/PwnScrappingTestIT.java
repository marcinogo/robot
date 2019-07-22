package edition.academy.seventh.service.scrapper;

import static org.testng.Assert.assertTrue;

import edition.academy.seventh.database.model.Book;
import edition.academy.seventh.service.PromotionProvider;
import java.util.List;
import org.testng.annotations.Test;

/**
 * @author Kacper Staszek
 */
@Test
public class PwnScrappingTestIT {

  public void should_scrapAtLeastOneBook_forGivenUrl() {
    // Given
    PromotionProvider promotionScrapping = new ScrapperConfiguration().pwnPromotionProvider();

    // When
    List<Book> books = promotionScrapping.getPromotions();

    // Then
    assertTrue(books.size() > 0);
  }
}