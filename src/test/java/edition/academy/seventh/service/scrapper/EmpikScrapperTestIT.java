package edition.academy.seventh.service.scrapper;

import edition.academy.seventh.database.model.DtoBook;
import java.util.List;

import edition.academy.seventh.service.PromotionProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

@Test
public class EmpikScrapperTestIT {

  public void should_scrapAtLeastOneBook_forGivenUrl() {
    // Given
    PromotionProvider promotionScrapping = new EmpikScrapper();

    // When
    List<DtoBook> books = promotionScrapping.getPromotions();

    // Then
    assertTrue(books.size() > 0);
  }
}
