package edition.academy.seventh.service;

import edition.academy.seventh.database.model.Book;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class PwnScrappingTestIT {

    public void should_scrapAtLeastOneBook_forGivenUrl() {
        // Given
        PromotionProvider promotionScrapping = new PwnScrapper();

        // When
        List<Book> books = promotionScrapping.getPromotions();

        // Then
        Assert.assertTrue(books.size() > 0);
    }
}
