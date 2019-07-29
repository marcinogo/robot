package edition.academy.seventh.service.scrapper;

import static org.testng.Assert.assertTrue;

import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.service.PromotionProvider;
import java.util.List;
import org.testng.annotations.Test;

@Test
public class TaniaKsiazkaScrapperTestIT {

  @Test
  public void should_scrapAtLeastOneBook_forGivenUrl() {
    // Given
    PromotionProvider promotionScrapping =
        new ScrapperConfiguration().taniaKsiazkaPromotionProvider();

    // When
    List<BookDto> books = promotionScrapping.getPromotions();

    // Then
    assertTrue(books.size() > 0);
  }

}