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
    Book book = new Book(dtBook.getSubtitle(), new BookId(dtBook.getTitle(), dtBook.getAuthors()));
    HrefAndImage hrefAndImage = new HrefAndImage(dtBook.getHref(), dtBook.getImageLink());
    Bookstore bookstore = new Bookstore(dtBook.getBookstore());

    return new BookstoreBook(
        book,
        dtBook.getRetailPrice(),
        dtBook.getPromotionalPrice(),
        LocalDate.now(),
        hrefAndImage,
        bookstore);
  }

  static List<DTBook> parseBookstoreBookListIntoDTBookList(List<BookstoreBook> bookstoreBooks) {
    return bookstoreBooks.stream()
        .map(
            bookstoreBook ->
                new DTBook(
                    bookstoreBook.getBook().getBookId().getTitle(),
                    bookstoreBook.getBook().getSubtitle(),
                    bookstoreBook.getBook().getBookId().getAuthor(),
                    bookstoreBook.getRetailPrice(),
                    bookstoreBook.getPromotionalPrice(),
                    bookstoreBook.getHrefAndImage().getImageLink(),
                    bookstoreBook.getHrefAndImage().getHyperLink(),
                    bookstoreBook.getBookstore().getName()))
        .collect(Collectors.toList());
  }
}
