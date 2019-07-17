package edition.academy.seventh.serivce;

import edition.academy.seventh.database.model.Book;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author Bartosz Kupajski
 */
@Test
public class PwnScrappingTestIT {

    public void should_scrapAtLeastOneBook_forGivenUrl(){
        // Given
        PromotionProvider promotionScrapping = new PwnScrapper();

        // When
        List<Book> books = promotionScrapping.getPromotions();

        // Then
        Assert.assertTrue(books.size()>0);
    }

}
