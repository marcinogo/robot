package edition.academy.seventh.scrapping;

import edition.academy.seventh.database.model.BookDto;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertTrue;

/** @author Kacper Staszek */
@Test
public class PwnScrappingTestIT {

  @Test
  public void should_scrapAtLeastOneBook_forGivenUrl() {
    // Given
    PromotionProvider promotionScrapping = new ScrapperConfiguration().pwnPromotionProvider();

    // When
    List<BookDto> books = promotionScrapping.getPromotions();

    // Then
    assertTrue(books.size() > 0);
  }
}
