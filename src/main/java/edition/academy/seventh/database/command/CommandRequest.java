package edition.academy.seventh.database.command;

/** @author krzysztof.kramarz */
// TODO czy ma sens?
public interface CommandRequest<T> {

  void accept(T command);
}
