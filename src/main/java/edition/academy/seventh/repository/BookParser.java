package edition.academy.seventh.repository;

import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Parses {@link BookDto} into {@link BookstoreBook} and {@code List<BookstoreBook>} into {@code
 * List<BookDto>}.
 *
 * @author Agnieszka Trzewik
 */
class BookParser {

  static BookstoreBook parseDTBookIntoModel(BookDto bookDto) {
    Book book =
        new Book(bookDto.getSubtitle(), new BookId(bookDto.getTitle(), bookDto.getAuthors()));
    UrlResources urlResources = new UrlResources(bookDto.getHref(), bookDto.getImageLink());
    Bookstore bookstore = new Bookstore(bookDto.getBookstore());

    return new BookstoreBook(new BookstoreBookId(bookstore, book), urlResources);
  }

  static List<BookDto> parseBookstoreBookListIntoDTBookList(List<BookstoreBook> bookstoreBooks) {
    return bookstoreBooks.stream()
        .map(
            bookstoreBook ->
                new BookDto(
                    bookstoreBook.getBookstoreBookId().getBook().getBookId().getTitle(),
                    bookstoreBook.getBookstoreBookId().getBook().getSubtitle(),
                    bookstoreBook.getBookstoreBookId().getBook().getBookId().getAuthor(),
                    bookstoreBook.getLastElementOfPriceHistories().getRetailPrice(),
                    bookstoreBook.getLastElementOfPriceHistories().getPromotionalPrice(),
                    bookstoreBook.getUrlResources().getImageLink(),
                    bookstoreBook.getUrlResources().getHyperLink(),
                    bookstoreBook.getBookstoreBookId().getBookstore().getName()))
        .collect(Collectors.toList());
  }
}
