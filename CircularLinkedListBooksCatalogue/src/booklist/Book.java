package booklist;

/**
 * The <code>Book</code> class
 * for storing the details of the Books.
 *
 * @author Victoria Pascal
 */
public class Book {
    /**
     * Book's title
     */
    private String title;
    /**
     * Book's author
     */
    private String author;
    /**
     * Book's price
     */
    private double price;
    /**
     * Book's ISBN
     */
    private long isbn;
    /**
     * Book's genre code
     */
    private String genre;
    /**
     * Book's year of publication
     */
    private int year;

    /**
     * Parameterized constructor
     *
     * @param title book's title
     * @param author book's author
     * @param price book's price
     * @param isbn book's ISBN
     * @param genre book's genre code
     * @param year book's year of publication
     */
    public Book(String title, String author, double price, long isbn, String genre, int year) {
        this.title = title;
        this.author = author;
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
    public String getAuthor() {
        return author;
    }

    /**
     * Sets book's author(s)
     *
     * @param author book's author(s)
     */
    public void setAuthor(String author) {
        this.author = author;
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
    public long getIsbn() {
        return isbn;
    }

    /**
     * Sets book's ISBN
     *
     * @param isbn book's ISBN
     */
    public void setIsbn(long isbn) {
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
     * Returns a string with the details of the Book:
     * title, author, price, ISBN, genre, and year.
     *
     * @return string with the details of the object.
     */
    @Override
    public String toString() {
        return "\"" + title + "\", " + author + ", " + price + ", " + isbn + ", " + genre + ", " + year;
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
        return Double.compare(book.price, price) == 0 && isbn == book.isbn && year == book.year
                && title.equals(book.title) && author.equals(book.author) && genre.equals(book.genre);
    }
}