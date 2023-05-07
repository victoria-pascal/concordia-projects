package books_catalogue;

import java.io.*;

/**
 * Helper <code>Genre</code> class
 * for grouping together all genre related data.
 *
 * @author Victoria Pascal
 */
public class Genre {
    /**
     * Genre name
     */
    private final String name;
    /**
     * Genre code
     */
    private final String code;
    /**
     * Path to generated files
     */
    private static final String pathToFiles = "files/out/";
    /**
     * Base file name used for file generation
     */
    private final String baseFileName;
    /**
     * Keeps valid book records read from catalogue files
     */
    private String[] bookRecords = new String[0];
    /**
     * Keeps <code>Book</code> objects parsed from CSV file and to be written to binary file.
     */
    private Book[] books = new Book[0];
    /**
     * Keeps <code>Book</code> objects from deserialized from binary file.
     */
    private Book[] deserializedBooks;

    /**
     * Parameterized constructor.
     *
     * @param name genre name
     * @param code genre code
     * @param baseFileName genre base file name used for file generation
     */
    public Genre(String name, String code, String baseFileName) {
        this.name = name;
        this.code = code;
        this.baseFileName = baseFileName;
    }

    /**
     * Returns genre name
     *
     * @return genre name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns genre code
     *
     * @return genre code
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns name of this genre's CSV file
     *
     * @return name of this genre's CSV file
     */
    public String getCsvFileName() {
        return baseFileName + ".csv";
    }

    /**
     * Returns path to this genre's CSV file
     *
     * @return path to this genre's CSV file
     */
    public String getCsvFilePath() {
        return pathToFiles + getCsvFileName();
    }

    /**
     * Returns name of this genre's binary file
     *
     * @return name of this genre's binary file
     */
    public String getSerFileName() {
        return getCsvFileName() + ".ser";
    }

    /**
     * Returns path to this genre's binary file
     *
     * @return path to this genre's binary file
     */
    public String getSerFilePath() {
        return pathToFiles + getSerFileName();
    }

    /**
     * Returns a copy of this genre's book records parsed from the CSV file
     *
     * @return book records
     */
    public String[] getBookRecords() {
        return bookRecords.clone();
    }

    /**
     * Returns a copy of this genre's <code>Book</code> objects created from book records
     *
     * @return <code>Book</code> objects from book records
     */
    public Book[] getBooks() {
        Book[] booksCopy = new Book[books.length];
        for (int i = 0; i < books.length; i++) {
           booksCopy[i] = books[i].clone();
        }
        return booksCopy;
    }

    /**
     * Returns a copy of this genre's <code>Book</code> objects deserialized from its binary file
     *
     * @return <code>Book</code> objects from binary file
     */
    public Book[] getDeserializedBooks() {
        if (deserializedBooks == null) {
            return new Book[0];
        }
        Book[] deserializedBooksCopy = new Book[deserializedBooks.length];
        for (int i = 0; i < deserializedBooks.length; i++) {
            deserializedBooksCopy[i] = deserializedBooks[i].clone();
        }
        return deserializedBooksCopy;
    }

    /**
     * Reads binary file and saves the array of <code>Book</code> objects
     *
     * @throws IOException if binary file doesn't exist or can't be read
     * @throws ClassNotFoundException if binary file contains objects of an unknown class
     */
    public void readBinaryFile() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(getSerFilePath()));
        deserializedBooks = (Book[]) ois.readObject();
        ois.close();
    }

    /**
     * Returns the number of deserialized books for this genre
     *
     * @return the number of deserialized books for this genre
     */
    public int getDeserializedBooksLength() {
        return deserializedBooks.length;
    }

    /**
     * Add a book record to the array of book records parsed from the catalogues
     *
     * @param newBookRecord book record
     */
    public void addBookRecords(String newBookRecord) {
            String[] oldBookRecords = bookRecords;
            bookRecords = new String[oldBookRecords.length + 1];
            for (int i = 0; i < oldBookRecords.length; i++) {
                bookRecords[i] = oldBookRecords[i];
            }
            bookRecords[bookRecords.length - 1] = newBookRecord;
    }

    /**
     * Write book records to CSV file
     *
     * @throws FileNotFoundException if problems with creating and writing to csv file
     */
    public void writeGenreRecordsToCsv() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(getCsvFilePath()));
        String[] bookRecordsToWrite = getBookRecords();
        for (int k = 0; k < bookRecordsToWrite.length; k++) {
            pw.println(bookRecordsToWrite[k]);
        }
        pw.close();
    }

    /**
     * Write book records to binary file
     *
     * @throws IOException if problems with creating and writing to binary file
     */
    public void writeBooksToBinaryFile() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getSerFilePath()));
        oos.writeObject(books);
        oos.close();
    }

    /**
     * Adding <code>Book</code> object to the array of books of this genre
     *
     * @param newBook <code>Book</code> object to be added
     */
    public void addBook(Book newBook) {
        Book[] oldBooks = books;
        books = new Book[oldBooks.length + 1];
        for (int i = 0; i < oldBooks.length; i++) {
            books[i] = oldBooks[i];
        }
        books[books.length - 1] = newBook.clone();
    }
}
