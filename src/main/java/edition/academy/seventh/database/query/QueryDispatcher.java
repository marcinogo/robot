package edition.academy.seventh.database.query;

import edition.academy.seventh.database.model.BookDto;

/**
 * @author krzysztof.kramarz
 */
public interface QueryDispatcher<T extends BookDto> {

   T execute(QueryRequest<T> queryRequest);
}
