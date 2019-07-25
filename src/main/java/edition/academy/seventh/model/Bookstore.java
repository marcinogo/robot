package edition.academy.seventh.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "bookstore")
@Data
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
}
