package edition.academy.seventh.persistence.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "bookstore")
@Getter
@Setter
@NoArgsConstructor
public class Bookstore {

  @Id
  @Column(name = "name")
  private String name;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "bookstore")
  private List<BookstoreBook> bookstoreBooks = new ArrayList<>();

  public Bookstore(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Bookstore{" + "name='" + name + '\'' + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Bookstore bookstore = (Bookstore) o;
    return Objects.equals(name, bookstore.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
