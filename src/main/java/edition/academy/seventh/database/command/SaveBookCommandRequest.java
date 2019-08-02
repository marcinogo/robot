package edition.academy.seventh.database.command;

import edition.academy.seventh.database.model.BookDto;
import org.springframework.beans.factory.annotation.Autowired;

/** @author krzysztof.kramarz */
class SaveBookCommandRequest implements CommandRequest<BookDto> {

  private final BookDto bookDto;

  @Autowired
  public SaveBookCommandRequest(BookDto bookDto) {
    this.bookDto = bookDto;
  }

  @Override
  public void accept(BookDto command) {
//    entityManager.persist(bookDto);
    // wywołaj dao i zapisz książkę
  }

    public BookDto getBookDto() {
        return bookDto;
    }
}
