package edition.academy.seventh.repository;

import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.model.Book;
import edition.academy.seventh.model.BookId;
import edition.academy.seventh.model.Bookstore;
import edition.academy.seventh.model.BookstoreBook;
import edition.academy.seventh.model.UrlResources;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Parses {@link BookDto} into {@link BookstoreBook} and {@code List<BookstoreBook>} into {@code
 * List<BookDto>}.
 *
 * @author Agnieszka Trzewik
 */
class BookParser {

  static BookstoreBook parseDTBookIntoModel(BookDto dtBook) {
    Book book = new Book(dtBook.getSubtitle(), new BookId(dtBook.getTitle(), dtBook.getAuthors()));
    UrlResources hrefAndImage = new UrlResources(dtBook.getHref(), dtBook.getImageLink());
    Bookstore bookstore = new Bookstore(dtBook.getBookstore());

    return new BookstoreBook(
        book,
        dtBook.getRetailPrice(),
        dtBook.getPromotionalPrice(),
        LocalDate.now(),
        hrefAndImage,
        bookstore);
  }

  static List<BookDto> parseBookstoreBookListIntoDTBookList(List<BookstoreBook> bookstoreBooks) {
    return bookstoreBooks.stream()
        .map(
            bookstoreBook ->
                new BookDto(
                    bookstoreBook.getBook().getBookId().getTitle(),
                    bookstoreBook.getBook().getSubtitle(),
                    bookstoreBook.getBook().getBookId().getAuthor(),
                    bookstoreBook.getRetailPrice(),
                    bookstoreBook.getPromotionalPrice(),
                    bookstoreBook.getUrlResources().getImageLink(),
                    bookstoreBook.getUrlResources().getHyperLink(),
                    bookstoreBook.getBookstore().getName()))
        .collect(Collectors.toList());
  }
}
