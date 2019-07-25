package edition.academy.seventh.repository;

import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.model.*;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.testng.Assert.assertEquals;

@Test
public class ModelParserIntoBookDtosTest {

  @Test(
      dataProviderClass = DataProviderForBookParser.class,
      dataProvider = "dataProviderForBookParsing")
  public void should_createDTBookList_when_givenBookstoreBookList(
      String title,
      String subtitle,
      String author,
      BigDecimal retailPrice,
      BigDecimal promotionalPrice,
      String currency,
      String imageLink,
      String hyperLink,
      String bookstore) {

    // Given
    BookstoreBook bookstoreBook =
        new BookstoreBook(
            hyperLink,
            imageLink,
            new Bookstore(bookstore),
            new Book(new BookId(title, author), subtitle));
    PriceHistory priceHistory =
        new PriceHistory(
            bookstoreBook, retailPrice, promotionalPrice, currency, LocalDateTime.now());
    bookstoreBook.getPriceHistories().add(priceHistory);

    String convertedRetailPrice = String.join(" ", String.valueOf(retailPrice), currency);
    String convertedPromotionalPrice = String.join(" ", String.valueOf(promotionalPrice), currency);

    BookDto dtBook =
        new BookDto(
            title,
            subtitle,
            author,
            convertedRetailPrice,
            convertedPromotionalPrice,
            imageLink,
            hyperLink,
            bookstore);

    List<BookstoreBook> bookstoreBooks = List.of(bookstoreBook);

    List<BookDto> expectedDTBooks = List.of(dtBook);

    // When
    List<BookDto> dtBooks =
        new ModelParserIntoBookDtos().parseBookstoreBooksIntoBookDtos(bookstoreBooks);

    // Then
    assertEquals(dtBooks, expectedDTBooks);
  }
}
