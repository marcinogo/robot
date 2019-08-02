package edition.academy.seventh.database.query;

import edition.academy.seventh.database.model.BookDto;

/**
 * @author krzysztof.kramarz
 */
public interface QueryRequest<T extends BookDto> {

    T get();

}
