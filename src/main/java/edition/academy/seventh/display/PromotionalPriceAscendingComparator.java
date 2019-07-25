package edition.academy.seventh.display;

import edition.academy.seventh.database.model.BookDto;

import java.util.Comparator;

/** @author Kamil Rojek */
public class PromotionalPriceAscendingComparator {

    static Comparator<BookDto> byPrice = Comparator.comparing(BookDto::getPromotionalPrice);
}
