package edition.academy.seventh.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class BookstoreBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bookstore_id")
    private Bookstore bookstore;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumns({
            @JoinColumn(name = "title"),
            @JoinColumn(name = "author")
    })
     private Book book;

    @Column(name = "retailPrice")
    private int retailPrice;

    @Column(name = "promotionalPrice")
    private int promotionalPrice;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "hrefAndImage_id")
    private HrefAndImage hrefAndImage;

    public BookstoreBook(Bookstore bookstore, Book book, int retailPrice, int promotionalPrice, LocalDate date, HrefAndImage hrefAndImage) {
        this.bookstore = bookstore;
        this.book = book;
        this.retailPrice = retailPrice;
        this.promotionalPrice =promotionalPrice;
        this.date = date;
        this.hrefAndImage = hrefAndImage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Bookstore getBookstore() {
        return bookstore;
    }

    public void setBookstore(Bookstore bookstore) {
        this.bookstore = bookstore;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(int retailPrice) {
        this.retailPrice = retailPrice;
    }

    public int getPromotionalPrice() {
        return promotionalPrice;
    }

    public void setPromotionalPrice(int promotionalPrice) {
        this.promotionalPrice = promotionalPrice;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public HrefAndImage getHrefAndImage() {
        return hrefAndImage;
    }

    public void setHrefAndImage(HrefAndImage hrefAndImage) {
        this.hrefAndImage = hrefAndImage;
    }
}
