package edition.academy.seventh.service.scrapper;

import edition.academy.seventh.database.model.DTBook;
import edition.academy.seventh.service.PromotionProvider;

import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertTrue;

@Test
public class SwiatKsiazkiScrapperTestIT {
  public void should_scrapAtLeastOneBook_forGivenUrl() {
    // Given
    PromotionProvider promotionScrapping = new SwiatKsiazkiScrapper();

    // When
    List<DTBook> books = promotionScrapping.getPromotions();

    // Then
    assertTrue(books.size() > 0);
  }
}
