package edition.academy.seventh.database.command;

/** @author krzysztof.kramarz */
class CommandDispatcherImpl<T> implements CommandDispatcher<T> {


  @Override
  public void execute(CommandRequest<T> commandRequest) {
//    commandRequest.accept();
  }
}
