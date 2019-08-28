package edition.academy.seventh.persistence;

import edition.academy.seventh.persistence.model.BookstoreBook;
import edition.academy.seventh.persistence.response.BookDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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
              BigDecimal promotionalPrice =
                  bookstoreBook.getLastElementOfPriceHistories().getPromotionalPrice();

              return new BookDto(
                  bookstoreBook.getId(),
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
