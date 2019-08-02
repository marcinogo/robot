package edition.academy.seventh.database.command;

/** @author krzysztof.kramarz */
public interface CommandDispatcher<T> {

  void execute(CommandRequest<T> commandRequest);
}
