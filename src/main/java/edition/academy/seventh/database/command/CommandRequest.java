package edition.academy.seventh.database.command;

/** @author krzysztof.kramarz */
// TODO czy ma sens?
public interface CommandRequest<T> {
//metoda może sie nazywać run
  void accept(T command);
}
