package edition.academy.seventh.repository;

import edition.academy.seventh.database.model.DTBook;
import edition.academy.seventh.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides parsing {@link DTBook} into {@link BookstoreBook} and {@code List<BookstoreBook>} into
 * {@code List<DTBook>}.
 *
 * @author Agnieszka Trzewik
 */
class BookParser {

  static BookstoreBook parseDTBookIntoModel(DTBook dtBook) {
    BookClass book = new BookClass(dtBook.getSubtitle(), new BookId(dtBook.getTitle(), dtBook.getAuthors()));
    Bookstore bookstore = new Bookstore(dtBook.getBookstore());
    HrefAndImage hrefAndImage = new HrefAndImage(dtBook.getHref(), dtBook.getImage());

    return new BookstoreBook(
        bookstore, book, dtBook.getPrice(), dtBook.getPromoPrice(), LocalDate.now(), hrefAndImage);
  }

  static List<DTBook> parseBookstoreBookListIntoDTBookList(List<BookstoreBook> bookstoreBooks) {
    return bookstoreBooks.stream()
        .map(
            bookstoreBook ->
                new DTBook(
                    bookstoreBook.getBook().getBookId().getTitle(),
                    bookstoreBook.getBook().getSubtitle(),
                    bookstoreBook.getBook().getBookId().getAuthor(),
                    bookstoreBook.getBookstore().getName(),
                    bookstoreBook.getRetailPrice(),
                    bookstoreBook.getPromotionalPrice(),
                    bookstoreBook.getHrefAndImage().getHyperLink(),
                    bookstoreBook.getHrefAndImage().getImage()))
        .collect(Collectors.toList());
  }
}
