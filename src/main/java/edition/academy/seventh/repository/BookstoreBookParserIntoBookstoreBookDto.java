package edition.academy.seventh.repository;

import edition.academy.seventh.database.model.BookstoreBookDto;
import edition.academy.seventh.database.model.PriceAtTheMomentDto;
import edition.academy.seventh.model.BookstoreBook;
import edition.academy.seventh.model.PriceAtTheMoment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/** @author Agnieszka Trzewik */
@Service
class BookstoreBookParserIntoBookstoreBookDto {

  BookstoreBookDto parseBookstoreBookIntoBookstoreBookDto(BookstoreBook bookstoreBook) {

    List<PriceAtTheMoment> priceHistories = bookstoreBook.getPriceHistories();
    List<PriceAtTheMomentDto> priceAtTheMomentDtos =
        priceHistories.stream()
            .map(
                priceHistory ->
                    new PriceAtTheMomentDto(
                        priceHistory.getRetailPrice(),
                        priceHistory.getPromotionalPrice(),
                        priceHistory.getCurrency(),
                        priceHistory.getDate()))
            .collect(Collectors.toList());
    return new BookstoreBookDto(
        bookstoreBook.getBook().getBookId().getTitle(),
        bookstoreBook.getBook().getSubtitle(),
        bookstoreBook.getBook().getBookId().getAuthor(),
        bookstoreBook.getImageLink(),
        bookstoreBook.getHyperlink(),
        bookstoreBook.getBookstore().getName(),
        priceAtTheMomentDtos);
  }
}
