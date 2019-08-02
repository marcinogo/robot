package edition.academy.seventh.database.query;

import edition.academy.seventh.database.model.BookDto;

/** @author krzysztof.kramarz */
class QueryDispatcherImpl<T extends BookDto> implements QueryDispatcher<T> {

  @Override
  public T execute(QueryRequest<T> queryRequest) {
    return queryRequest.get();
  }
}
