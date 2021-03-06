package edition.academy.seventh.persistence;

import edition.academy.seventh.persistence.response.BookDto;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.Invocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

class BookServiceTest {

  @Test
  void methodAddBooksToDatabase_shouldBeInvokedOnce_when_listOfBooksPassedIsGreaterThanOne() {
    // Given
    BookRepository bookRepository = mock(BookRepository.class);
    BookDto book = mock(BookDto.class);

    BookService bookService = new BookService(bookRepository);
    List<BookDto> books = new ArrayList<>();

    // When
    books.add(book);
    books.add(book);
    bookService.addBooksToDatabase(books);

    Collection<Invocation> invocations = mockingDetails(bookRepository).getInvocations();
    long validationCalls =
        invocations.stream()
            .filter(invocation -> invocation.toString().contains("addBooksToDatabase"))
            .count();

    // Then
    assertEquals(validationCalls, 1);
  }

  @Test
  void methodGetAllBooksFromDatabase_shouldBeInvokedOnce_when_getBooksFromDatabaseIsCalled() {
    // Given
    BookRepository bookRepository = mock(BookRepository.class);
    BookDto book = mock(BookDto.class);

    BookService bookService = new BookService(bookRepository);

    // When
    when(bookRepository.getLatestBooksFromDatabase()).thenReturn(List.of(book));

    bookService.getBooksFromDatabase();

    Collection<Invocation> invocations = mockingDetails(bookRepository).getInvocations();
    long validationCalls =
        invocations.stream()
            .filter(invocation -> invocation.toString().contains("getLatestBooksFromDatabase"))
            .count();

    // Then
    assertEquals(validationCalls, 1);
  }
}
