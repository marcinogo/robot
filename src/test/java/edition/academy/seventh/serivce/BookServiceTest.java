package edition.academy.seventh.serivce;

import edition.academy.seventh.database.model.DTBook;
import edition.academy.seventh.repository.BookRepository;
import org.mockito.invocation.Invocation;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

@Test
public class BookServiceTest {

  @Test
  public void should_addAllBooksByBookRepository_when_booksAdding() {
    // Given
    BookRepository bookRepository = mock(BookRepository.class);
    DTBook book = mock(DTBook.class);

    BookService bookService = new BookService(bookRepository);
    List<DTBook> books = new ArrayList<>();

    // When
    books.add(book);
    books.add(book);
    bookService.addBooksToDataBase(books);

    Collection<Invocation> invocations = mockingDetails(bookRepository).getInvocations();
    long validationCalls =
        invocations.stream()
            .filter(invocation -> invocation.toString().contains("addBooksToDataBase"))
            .count();

    // Then
    assertEquals(validationCalls, 1);
  }

  @Test
  public void should_getAllBooksByBookRepository_when_booksGetting() {
    // Given
    BookRepository bookRepository = mock(BookRepository.class);
    DTBook book = mock(DTBook.class);

    BookService bookService = new BookService(bookRepository);

    // When
    when(bookRepository.getBooksFromDataBase()).thenReturn(List.of(book));

    bookService.getBooksFromDataBase();

    Collection<Invocation> invocations = mockingDetails(bookRepository).getInvocations();
    long validationCalls =
        invocations.stream()
            .filter(invocation -> invocation.toString().contains("getBooksFromDataBase"))
            .count();

    // Then
    assertEquals(validationCalls, 1);
  }
}
