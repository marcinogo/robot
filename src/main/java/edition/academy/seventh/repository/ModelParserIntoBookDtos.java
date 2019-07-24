package edition.academy.seventh.repository;

import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.model.BookstoreBook;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Parses data about books with last update price information from database into {@link BookDto}s.
 *
 * @author Agnieszka Trzewik
 */
@Service
class ModelParserIntoBookDtos {

  List<BookDto> parseBookstoreBooksIntoBookDtos(List<BookstoreBook> bookstoreBooks) {
    return bookstoreBooks.stream()
        .map(
            bookstoreBook ->
                new BookDto(
                    bookstoreBook.getBook().getBookId().getTitle(),
                    bookstoreBook.getBook().getSubtitle(),
                    bookstoreBook.getBook().getBookId().getAuthor(),
                    bookstoreBook.getLastElementOfPriceHistories().getRetailPrice(),
                    bookstoreBook.getLastElementOfPriceHistories().getPromotionalPrice(),
                    bookstoreBook.getImageLink(),
                    bookstoreBook.getHyperLink(),
                    bookstoreBook.getBookstore().getName()))
        .collect(Collectors.toList());
  }
}
