package edition.academy.seventh.service;

import edition.academy.seventh.database.model.Book;
import java.util.List;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

@Test
public class EmpikScrapperTestIT {

  public void should_scrapAtLeastOneBook_forGivenUrl() {
    // Given
    PromotionProvider promotionScrapping = new EmpikScrapper();

    // When
    List<Book> books = promotionScrapping.getPromotions();

    // Then
    assertTrue(books.size() > 0);
  }
}
