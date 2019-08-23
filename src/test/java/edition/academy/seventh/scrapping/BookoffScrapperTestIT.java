package edition.academy.seventh.scrapping;

import edition.academy.seventh.persistence.response.BookDto;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertTrue;

@Test
public class BookoffScrapperTestIT {

  @Test
  public void should_scrapAtLeastOneBook_forGivenUrl() {
    // Given
    PromotionProvider promotionScrapping = new ScrapperConfiguration().bookoffPromotionProvider();

    // When
    List<BookDto> books = promotionScrapping.getPromotions();

    // Then
    assertTrue(books.size() > 0);
  }
}
