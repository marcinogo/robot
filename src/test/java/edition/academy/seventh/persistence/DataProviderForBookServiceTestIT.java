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
                    0l,
                    "Title",
                    "Subtitle",
                    "Authors",
                    "zł",
                    BigDecimal.valueOf(50),
                    BigDecimal.valueOf(40),
                    "image",
                    "href",
                    "Bookstore")),
            Collections.singletonList(
                new BookDto(
                    1l,
                    "Title1",
                    "Subtitle1",
                    "Authors1",
                    "zł",
                    BigDecimal.valueOf(50),
                    BigDecimal.valueOf(40),
                    "image1",
                    "href1",
                    "Bookstore1")),
            Collections.singletonList(
                new BookDto(
                    2l,
                    "Title2",
                    "Subtitle2",
                    "Authors2",
                    "zł",
                    BigDecimal.valueOf(50),
                    BigDecimal.valueOf(40),
                    "image2",
                    "href2",
                    "Bookstore2")),
            Collections.singletonList(
                new BookDto(
                    3l,
                    "Title3",
                    "Subtitle3",
                    "Authors3",
                    "zł",
                    BigDecimal.valueOf(50),
                    BigDecimal.valueOf(40),
                    "image3",
                    "href3",
                    "Bookstore3")),
            Collections.singletonList(
                new BookDto(
                    4l,
                    "Title4",
                    "Subtitle4",
                    "Authors4",
                    "zł",
                    BigDecimal.valueOf(50),
                    BigDecimal.valueOf(40),
                    "image4",
                    "href4",
                    "Bookstore4")),
            Collections.singletonList(
                new BookDto(
                    5l,
                    "Title5",
                    "Subtitle5",
                    "Authors5",
                    "zł",
                    BigDecimal.valueOf(50),
                    BigDecimal.valueOf(40),
                    "image5",
                    "href5",
                    "Bookstore5")))
        .map(Arguments::of);
  }

  static Stream<Arguments> dataForGettingBookDtoByHref() {
    return Stream.of(
            Collections.singletonList(
                new BookDto(
                    6l,
                    "Title12",
                    "Subtitle",
                    "Authors",
                    "zł",
                    BigDecimal.valueOf(50),
                    BigDecimal.valueOf(40),
                    "image",
                    "href",
                    "Bookstore")),
            Collections.singletonList(
                new BookDto(
                    7l,
                    "Title12",
                    "Subtitle1",
                    "Authors1",
                    "zł",
                    BigDecimal.valueOf(50),
                    BigDecimal.valueOf(40),
                    "image1",
                    "href1",
                    "Bookstore1")),
            Collections.singletonList(
                new BookDto(
                    8l,
                    "Title22",
                    "Subtitle2",
                    "Authors2",
                    "zł",
                    BigDecimal.valueOf(50),
                    BigDecimal.valueOf(40),
                    "image2",
                    "href2",
                    "Bookstore2")),
            Collections.singletonList(
                new BookDto(
                    9l,
                    "Title32",
                    "Subtitle3",
                    "Authors3",
                    "zł",
                    BigDecimal.valueOf(50),
                    BigDecimal.valueOf(40),
                    "image3",
                    "href3",
                    "Bookstore3")),
            Collections.singletonList(
                new BookDto(
                    10l,
                    "Title42",
                    "Subtitle4",
                    "Authors4",
                    "zł",
                    BigDecimal.valueOf(50),
                    BigDecimal.valueOf(40),
                    "image4",
                    "href4",
                    "Bookstore4")),
            Collections.singletonList(
                new BookDto(
                    11l,
                    "Title52",
                    "Subtitle5",
                    "Authors5",
                    "zł",
                    BigDecimal.valueOf(50),
                    BigDecimal.valueOf(40),
                    "image5",
                    "href5",
                    "Bookstore5")))
        .map(Arguments::of);
  }
}
