package edition.academy.seventh.repository;

import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.model.BookstoreBook;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Parses books with last update price information from database to {@link BookDto}.
 *
 * @author Agnieszka Trzewik
 */
@Service
public class ModelParserIntoBookDtos {
  public static List<BookDto> parseBookstoreBooksIntoBookDtos(List<BookstoreBook> bookstoreBooks) {
    return bookstoreBooks.stream()
        .map(
            bookstoreBook -> {
              BigDecimal retailPrice =
                  bookstoreBook.getLastElementOfPriceHistories().getRetailPrice();
              BigDecimal promotionalPrice = bookstoreBook.getLastElementOfPriceHistories()
                  .getPromotionalPrice();


              return new BookDto(
                  bookstoreBook.getBook().getBookId().getTitle(),
                  bookstoreBook.getBook().getSubtitle(),
                  bookstoreBook.getBook().getBookId().getAuthor(),
                  bookstoreBook.getLastElementOfPriceHistories().getCurrency(),
                  retailPrice,
                  promotionalPrice,
                  bookstoreBook.getImageLink(),
                  bookstoreBook.getHyperlink(),
                  bookstoreBook.getBookstore().getName());
            })
        .collect(Collectors.toList());
  }
}
