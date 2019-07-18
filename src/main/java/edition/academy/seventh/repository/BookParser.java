package edition.academy.seventh.repository;

import edition.academy.seventh.database.model.DTBook;
import edition.academy.seventh.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/** @author Agnieszka Trzewik */
public class BookParser {

  static BookstoreBook parseDTBookIntoModel(DTBook book) {
    Book book2 = new Book(book.getSubtitle(), new BookId(book.getTitle(), book.getAuthors()));
    Bookstore bookstore = new Bookstore(book.getBookstore());
    HrefAndImage hrefAndImage = new HrefAndImage(book.getHref(), book.getImage());

    return new BookstoreBook(
        bookstore, book2, book.getPrice(), book.getPromoPrice(), LocalDate.now(), hrefAndImage);
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
