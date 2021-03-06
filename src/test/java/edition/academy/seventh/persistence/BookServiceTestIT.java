package edition.academy.seventh.persistence;

import edition.academy.seventh.persistence.response.BookDto;
import edition.academy.seventh.persistence.response.BookstoreBookDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class BookServiceTestIT {

  @Autowired private BookService bookService;

  @ParameterizedTest(name = "{index} => bookDtos: {0}")
  @MethodSource(
      "edition.academy.seventh.persistence.DataProviderForBookServiceTestIT#dataForAddingBookDtos")
  void should_returnOneMoreBook_when_addOneBook(List<BookDto> bookDtos) {
    // Given
    List<BookDto> booksBeforeAdding = bookService.getBooksFromDatabase();

    // When
    bookService.addBooksToDatabase(bookDtos);
    List<BookDto> booksAfterAdding = bookService.getBooksFromDatabase();
    int expectedNumberOfBooksAfterAdding = booksBeforeAdding.size() + 1;

    // Then
    assertEquals(expectedNumberOfBooksAfterAdding, booksAfterAdding.size());
  }

  @Test
  void should_returnNull_whenGivenHrefDoNotExists() {
    // Given

    // When
    BookstoreBookDto bookstoreBookDtoByHref = bookService.getBookstoreBookDtoById(-1l);

    // Then
    assertNull(bookstoreBookDtoByHref);
  }

  @ParameterizedTest(name = "{index} => bookDtos: {0}")
  @MethodSource(
      "edition.academy.seventh.persistence.DataProviderForBookServiceTestIT#dataForGettingBookDtoByHref")
  void should_returnBookFromDatabase_when_givenProperHref(List<BookDto> bookDtos) {
    // Given
    bookService.addBooksToDatabase(bookDtos);

    // When
    BookstoreBookDto bookstoreBookDtoByHref =
        bookService.getBookstoreBookDtoById(bookDtos.get(0).getId());

    // Then
    assertNotNull(bookstoreBookDtoByHref);
  }
}
