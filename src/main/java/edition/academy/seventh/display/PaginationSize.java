package edition.academy.seventh.display;

public enum PaginationSize {
    TWO(2), TWENTY(20), FIFTY(50), HUNDRED(100);

    long value;

    PaginationSize(int value) {
        this.value = value;
    }
}
