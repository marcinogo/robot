package edition.academy.seventh.persistence;

import edition.academy.seventh.persistence.response.BookDto;
import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.stream.Stream;

class DataProviderForBookServiceTestIT {

  static Stream<Arguments> dataForAddingBookDtos() {
    return Stream.of(
            Collections.singletonList(
                new BookDto(
                    "Title",
                    "Subtitle",
                    "Authors",
                    "zł",
                    new BigDecimal(50.00),
                    new BigDecimal(40.00),
                    "image",
                    "href",
                    "Bookstore")),
            Collections.singletonList(
                new BookDto(
                    "Title1",
                    "Subtitle1",
                    "Authors1",
                    "zł",
                    new BigDecimal(50.00),
                    new BigDecimal(40.00),
                    "image1",
                    "href1",
                    "Bookstore1")),
            Collections.singletonList(
                new BookDto(
                    "Title2",
                    "Subtitle2",
                    "Authors2",
                    "zł",
                    new BigDecimal(50.00),
                    new BigDecimal(40.00),
                    "image2",
                    "href2",
                    "Bookstore2")),
            Collections.singletonList(
                new BookDto(
                    "Title3",
                    "Subtitle3",
                    "Authors3",
                    "zł",
                    new BigDecimal(50.00),
                    new BigDecimal(40.00),
                    "image3",
                    "href3",
                    "Bookstore3")),
            Collections.singletonList(
                new BookDto(
                    "Title4",
                    "Subtitle4",
                    "Authors4",
                    "zł",
                    new BigDecimal(50.00),
                    new BigDecimal(40.00),
                    "image4",
                    "href4",
                    "Bookstore4")),
            Collections.singletonList(
                new BookDto(
                    "Title5",
                    "Subtitle5",
                    "Authors5",
                    "zł",
                    new BigDecimal(50.00),
                    new BigDecimal(40.00),
                    "image5",
                    "href5",
                    "Bookstore5")))
        .map(Arguments::of);
  }

  static Stream<Arguments> dataForGettingBookDtoByHref() {
    return Stream.of(
            Collections.singletonList(
                new BookDto(
                    "Title12",
                    "Subtitle",
                    "Authors",
                    "zł",
                    new BigDecimal(50.00),
                    new BigDecimal(40.00),
                    "image",
                    "href",
                    "Bookstore")),
            Collections.singletonList(
                new BookDto(
                    "Title12",
                    "Subtitle1",
                    "Authors1",
                    "zł",
                    new BigDecimal(50.00),
                    new BigDecimal(40.00),
                    "image1",
                    "href1",
                    "Bookstore1")),
            Collections.singletonList(
                new BookDto(
                    "Title22",
                    "Subtitle2",
                    "Authors2",
                    "zł",
                    new BigDecimal(50.00),
                    new BigDecimal(40.00),
                    "image2",
                    "href2",
                    "Bookstore2")),
            Collections.singletonList(
                new BookDto(
                    "Title32",
                    "Subtitle3",
                    "Authors3",
                    "zł",
                    new BigDecimal(50.00),
                    new BigDecimal(40.00),
                    "image3",
                    "href3",
                    "Bookstore3")),
            Collections.singletonList(
                new BookDto(
                    "Title42",
                    "Subtitle4",
                    "Authors4",
                    "zł",
                    new BigDecimal(50.00),
                    new BigDecimal(40.00),
                    "image4",
                    "href4",
                    "Bookstore4")),
            Collections.singletonList(
                new BookDto(
                    "Title52",
                    "Subtitle5",
                    "Authors5",
                    "zł",
                    new BigDecimal(50.00),
                    new BigDecimal(40.00),
                    "image5",
                    "href5",
                    "Bookstore5")))
        .map(Arguments::of);
  }
}
