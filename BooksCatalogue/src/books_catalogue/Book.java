package books_catalogue;

import java.io.Serializable;

/**
 * <code>Book</code> class for keeping Book details.
 *
 * @author Victoria Pascal
 */
public class Book implements Serializable {
    /**
     * Book's title
     */
    private String title;
    /**
     * Book's author(s)
     */
    private String authors;
    /**
     * Book's price
     */
    private double price;
    /**
     * Book's ISBN
     */
    private String isbn;
    /**
     * Book's genre code
     */
    private String genre;
    /**
     * Book's year of publication
     */
    private int year;

    /**
     * Copy constructor
     *
     * @param anotherBook <code>Book</code> object to copy
     */
    public Book(Book anotherBook) {
        title = anotherBook.title;
        authors = anotherBook.authors;
        price = anotherBook.price;
        isbn = anotherBook.isbn;
        genre = anotherBook.genre;
        year = anotherBook.year;
    }

    /**
     * Parameterized constructor
     *
     * @param title book's title
     * @param authors book's author(s)
     * @param price book's price
     * @param isbn book's ISBN
     * @param genre book's genre code
     * @param year book's year of publication
     */
    public Book(String title, String authors, double price, String isbn, String genre, int year) {
        this.title = title;
        this.authors = authors;
        this.price = price;
        this.isbn = isbn;
        this.genre = genre;
        this.year = year;
    }

    /**
     * Returns book's title
     *
     * @return book's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets book's title
     *
     * @param title book's title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns book's author(s)
     *
     * @return book's author(s)
     */
    public String getAuthors() {
        return authors;
    }

    /**
     * Sets book's author(s)
     *
     * @param authors book's author(s)
     */
    public void setAuthors(String authors) {
        this.authors = authors;
    }

    /**
     * Returns book's price
     *
     * @return book's price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets book's price
     *
     * @param price book's price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns book's ISBN
     *
     * @return book's ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets book's ISBN
     *
     * @param isbn book's ISBN
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Returns book's genre code
     *
     * @return book's genre code
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Sets book's genre code
     *
     * @param genre book's genre code
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Returns book's year of publication
     *
     * @return book's year of publication
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets book's year of publication
     *
     * @param year book's year of publication
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Compares passed object to this <code>Book</code> object.
     *
     * @param obj object to compare
     * @return whether passed object is equal to this <code>Book</code> object.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return Double.compare(book.price, price) == 0
                && year == book.year
                && title.equals(book.title)
                && authors.equals(book.authors)
                && isbn.equals(book.isbn)
                && genre.equals(book.genre);
    }

    /**
     * Returns a string with the details of the Book:
     * title, author(s), price, ISBN, genre, and year.
     *
     * @return string with the details of the object.
     */
    @Override
    public String toString() {
        return "Book details:" +
                "\n=====================" +
                "\nTitle: " + title +
                "\nAuthor(s): " + authors +
                "\nPrice: " + price +
                "\nISBN: " + isbn +
                "\nGenre: " + genre +
                "\nYear: " + year;
    }

    /**
     * Returns a cloned instance of this <code>Book</code> object.
     *
     * @return cloned <code>Book</code> object
     */
    @Override
    public Book clone() {
        return new Book(this);
    }
}
