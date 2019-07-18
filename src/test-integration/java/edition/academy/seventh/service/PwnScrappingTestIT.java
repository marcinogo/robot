package edition.academy.seventh.service;

import edition.academy.seventh.database.model.Book;
import java.util.List;

import edition.academy.seventh.service.scrapper.PwnScrapper;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

@Test
public class PwnScrappingTestIT {

    public void should_scrapAtLeastOneBook_forGivenUrl() {
        // Given
        PromotionProvider promotionScrapping = new PwnScrapper();

        // When
        List<Book> books = promotionScrapping.getPromotions();

        // Then
        assertTrue(books.size() > 0);
    }
}
