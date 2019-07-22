package edition.academy.seventh.service.scrapper;

import static org.testng.Assert.assertTrue;

import edition.academy.seventh.database.model.DtoBook;
import edition.academy.seventh.service.PromotionProvider;
import java.util.List;
import org.testng.annotations.Test;

/**
 * @author Kacper Staszek
 */
@Test
public class PwnScrappingTestIT {

  @Test
  public void should_scrapAtLeastOneBook_forGivenUrl() {
    // Given
    PromotionProvider promotionScrapping = new ScrapperConfiguration().pwnPromotionProvider();

    // When
    List<DtoBook> books = promotionScrapping.getPromotions();

    // Then
    assertTrue(books.size() > 0);
  }
}