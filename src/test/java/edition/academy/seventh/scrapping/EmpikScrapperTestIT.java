package edition.academy.seventh.scrapping;

import edition.academy.seventh.persistence.response.BookDto;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertTrue;

/** @author Kacper Staszek */
@Test
public class EmpikScrapperTestIT {

  @Test(invocationCount = 1000)
  public void should_scrapAtLeastOneBook_forGivenUrl() {
    // Given
    PromotionProvider promotionScrapping = new ScrapperConfiguration().empikPromotionProvider();

    // When
    List<BookDto> books = promotionScrapping.getPromotions();

    // Then
    assertTrue(books.size() > 0);
  }
}
