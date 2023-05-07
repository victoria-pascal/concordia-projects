package books_catalogue;

import java.io.*;
import java.util.Scanner;

/**
 * This is the driver class of the application.
 * It is an interactive program that allows one to parse
 * a catalogue of books, identify and remove invalid book records, and
 * navigate through book details, categorized by genre.
 *
 * @author Victoria Pascal
 */
public class Main {
    /**
     * Array of possible genres and their info
     */
    public static Genre[] genres = {
        new Genre("Cartoons & Comics Books", "CCB", "Cartoons_Comics_Books"),
        new Genre("Hobbies & Collectibles Books", "HCB", "Hobbies_Collectibles_Books"),
        new Genre("Movies & TV", "MTV", "Movies_TV"),
        new Genre("Music & Radio Books", "MRB", "Music_Radio_Books"),
        new Genre("Nostalgia & Eclectic Books", "NEB", "Nostalgia_Eclectic_Books"),
        new Genre("Old-Time Radio", "OTR", "Old_Time_Radio"),
        new Genre("Sports & Sports Memorabilia", "SSM", "Sports_Sports_Memorabilia"),
        new Genre("Trains, Planes & Automobiles", "TPA", "Trains_Planes_Automobiles"),
    };
    /**
     * Globally accessible <code>Scanner</code> for taking keyboard input from user
     */
    public static Scanner keyboard = new Scanner(System.in);
    /**
     * Stores the name of the file where the last error was encountered for error printing purposes
     */
    private static String lastErrInFile = null;

    /**
     * Default constructor
     */
    public Main() {}

    /**
     * Utility method for writing errors to error stream
     *
     * @param fileName name of the file where the faulty record is located
     * @param errorMessage error message to print
     * @param record faulty record
     */
    public static void printErr(String fileName, String errorMessage, String record) {
        if (!fileName.equals(lastErrInFile)) {
            System.err.println("syntax error in file: " + fileName + "\n====================");
            lastErrInFile = fileName;
        }
        System.err.println(errorMessage);
        System.err.println(record);
        System.err.println();
    }

    /**
     * Returns the field name based on index position in CSV record
     *
     * @param index position of the field in CSV record
     * @return name of field ("title", "author(s)", "price", "ISBN", "genre", or "year")
     * or null if no field with passed index exists
     */
    public static String fieldIndexToFieldName(int index) {
        String fieldName = null;
        if (index == 0) {
            fieldName = "title";
        }
        else if (index == 1) {
            fieldName = "author(s)";
        }
        else if (index == 2) {
            fieldName = "price";
        }
        else if (index == 3) {
            fieldName = "ISBN";
        }
        else if (index == 4) {
            fieldName = "genre";
        }
        else if (index == 5) {
            fieldName = "year";
        }
        return fieldName;
    }

    /**
     * Reads an initial books catalogue file with mixed CSV records of books.
     * Validates if records are syntactically valid.
     * Outputs errors and faulty records to the syntax errors file.
     * Saves the record to the array of records of the respective genre if it is valid.
     *
     * @param fileName name of book catalogue CSV file to read
     */
    public static void readBooksFile(String fileName) {
        String path = "files/in/" + fileName;
        try {
            // opening catalogue file for reading
            Scanner sc = new Scanner(new FileInputStream(path));

            String bookRecordLine = null;

            while (sc.hasNextLine()) {
                try {
                    // reading book record
                    bookRecordLine = sc.nextLine();

                    // splitting CSV record into fields
                    String[] bookRecordFields = bookRecordLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

                    // validating for syntax errors
                    if (bookRecordFields.length > 6) throw new TooManyFieldsException();
                    else if (bookRecordFields.length < 6) throw new TooFewFieldsException();
                    for (int j = 0; j < bookRecordFields.length; j++) {
                        if (bookRecordFields[j].isEmpty()) throw new MissingFieldException(fieldIndexToFieldName(j));
                    }

                    // validating for unknown genre code (also syntax error)
                    boolean foundGenre = false;
                    for (Genre genre : genres) {
                        if (bookRecordFields[4].equals(genre.getCode())) {
                            foundGenre = true;

                            // adding valid record to genre's book records array
                            genre.addBookRecords(bookRecordLine);
                            break;
                        }
                    }
                    if (!foundGenre) {
                        throw new UnknownGenreException();
                    }
                }
                catch (SyntaxException ex) {
                    printErr(fileName, ex.getMessage(), "Record: " + bookRecordLine);
                }
            }

            sc.close();
        }
        catch (FileNotFoundException ex) {
            System.out.println("Error: Missing file " + path);
        }
    }

    /**
     * Reads book catalogue files, validates syntax, partitions book records based on genre
     */
    public static void do_part1() {
        try {
            // opening file with names of book catalogue files to be read
            Scanner in = new Scanner(new FileInputStream("files/in/part1_input_file_names.txt"));

            // connecting error stream to syntax errors file
            PrintStream errStream = new PrintStream(new FileOutputStream("files/out/syntax_error_file.txt"));
            System.setErr(errStream);

            // reading file names of book catalogues
            int numberOfFiles = in.nextInt();
            in.nextLine();
            for (int i = 0; i < numberOfFiles; i++) {
                while (in.hasNextLine()) {
                    String fileName = in.nextLine();

                    // reading and validating book records from the book catalogue file
                    readBooksFile(fileName);
                }
            }

            // closing streams
            in.close();
            errStream.close();

            // writing validated CSV records to respective genre CSV files
            for (Genre genre : genres) {
                genre.writeGenreRecordsToCsv();
            }
        }
        catch (FileNotFoundException ex) {
            System.out.println("Error: Missing file\n" + ex.getMessage());
        }
    }

    /**
     * Validates if passed ISBN-10 string is valid
     *
     * @param isbn10 ISBN-10
     * @return whether the ISBN-10 is valid
     */
    public static boolean isValidIsbn10(String isbn10) {
        int sum = 0;
        for (int i = 0, factor = 10; i < isbn10.length(); i++, factor--) {
            char digit = isbn10.charAt(i);
            if (i == isbn10.length() - 1 && digit == 'X') {
                sum += 10;
            }
            else if (!Character.isDigit(digit)) return false;
            else {
                sum += factor * Character.digit(digit, 10);
            }
        }
        return sum % 11 == 0;
    }

    /**
     * Validates if passed ISBN-13 string is valid
     *
     * @param isbn13 ISBN-13
     * @return whether the ISBN-13 is valid
     */
    public static boolean isValidIsbn13(String isbn13) {
        int sum = 0;
        for (int i = 0; i < isbn13.length(); i++) {
            char digit = isbn13.charAt(i);
            if (!Character.isDigit(digit)) return false;
            if (i % 2 == 0) {
                sum += Character.digit(digit, 10);
            }
            else {
                sum += 3 * Character.digit(digit, 10);
            }
        }
        return sum % 10 == 0;
    }

    /**
     * Reads a genre's CSV file generated in part 1.
     * Validates if records are semantically valid.
     * Outputs errors and faulty records to the semantic errors file.
     * Creates <code>Book</code> objects from valid records.
     * Saves the <code>Book</code> objects to the array of books of the respective genre.
     *
     * @param genre the genre for which its CSV file is to be read and <code>Book</code> objects are to be created
     */
    public static void readGenreCsv(Genre genre) {
        try {
            // opening CSV file for reading
            Scanner sc = new Scanner(new FileInputStream(genre.getCsvFilePath()));

            String bookRecordLine = null;
            while (sc.hasNextLine()) {
                try {
                    // reading CSV record
                    bookRecordLine = sc.nextLine();

                    // splitting CSV record
                    String[] bookRecordFields = bookRecordLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

                    String title = bookRecordFields[0];
                    String authors = bookRecordFields[1];
                    double price = Double.parseDouble(bookRecordFields[2]);
                    String isbn = bookRecordFields[3];
                    String genreCode = bookRecordFields[4];
                    int year = Integer.parseInt(bookRecordFields[5]);

                    // removing quotation marks if title is wrapped in them
                    if (title.charAt(0) == '"' && title.charAt(title.length() - 1) == '"') {
                        title = title.substring(1, title.length() - 1);
                    }

                    // validating for semantic errors
                    if (price < 0) throw new BadPriceException();
                    else if (isbn.length() != 10 && isbn.length() != 13) throw new BadIsbnLengthException();
                    else if (isbn.length() == 10 && !isValidIsbn10(isbn)) throw new BadIsbn10Exception();
                    else if (isbn.length() == 13 && !isValidIsbn13(isbn)) throw new BadIsbn13Exception();
                    else if (year < 1995 || year > 2010) throw new BadYearException();

                    // creating and adding Book based on validated record
                    genre.addBook(new Book(title, authors, price, isbn, genreCode, year));
                }
                catch (SemanticException ex) {
                    printErr(genre.getCsvFileName(), ex.getMessage(), "Record: " + bookRecordLine);
                }
            }

            sc.close();
        }
        catch (FileNotFoundException ex) {
            System.out.println("Missing file: " + genre.getCsvFileName());
        }
    }

    /**
     * Reads the genre files, validates semantics,
     * creates arrays of Book objects from valid records,
     * then serializes the arrays of Book objects each into binary files.
     */
    public static void do_part2() {
        try {
            // connecting error stream to semantic errors file
            PrintStream errStream = new PrintStream(new FileOutputStream("files/out/semantic_error_file.txt"));
            System.setErr(errStream);

            for (Genre genre : genres) {
                // reading CSV file, validating for semantic errors and creating Book objects
                readGenreCsv(genre);
                try {
                    // writing Book objects to binary file
                    genre.writeBooksToBinaryFile();
                }
                catch (FileNotFoundException ex) {
                    System.out.println("Error: Missing file");
                }
                catch (IOException ex) {
                    System.out.println("Error: output problems");
                }
            }

            errStream.close();
        }
        catch (FileNotFoundException ex) {
            System.out.println("Error: Missing file");
        }
    }

    /**
     * Displays the details of <code>Book</code> objects of the file selected for viewing.
     *
     * @param books <code>Book</code> objects of the viewed file
     */
    public static void enterViewingSession(Book[] books) {
        int currentObjectIndex = 0;
        while (true) {
            System.out.print("Enter the number of records you want to view: ");

            try {
                int nbOfBooksToView = Integer.parseInt(keyboard.nextLine());
                System.out.println();

                if (nbOfBooksToView == 0) {
                    break;
                }
                else if (nbOfBooksToView > 0) {
                    int endIndex = currentObjectIndex + nbOfBooksToView - 1;
                    boolean eofReached = endIndex >= books.length;

                    if (eofReached) {
                        endIndex = books.length - 1;
                    }
                    for (int i = currentObjectIndex; i <= endIndex; i++) {
                        System.out.println(books[i] + "\n");
                    }

                    if (eofReached) {
                        System.out.println("EOF has been reached");
                    }

                    currentObjectIndex = endIndex;
                }
                else {
                    int startIndex = currentObjectIndex + nbOfBooksToView + 1;
                    if (startIndex < 0) {
                        System.out.println("BOF has been reached");
                        startIndex = 0;
                    }

                    for (int i = startIndex; i <= currentObjectIndex; i++) {
                        System.out.println(books[i] + "\n");
                    }

                    currentObjectIndex = startIndex;
                }
                System.out.println("The index of the current object is: " + currentObjectIndex);
            }
            catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    /**
     * Reads the binary files, deserializes the array objects in each file, and
     * provides an interactive menu to allow the user to navigate the arrays.
     */
    public static void do_part3() {
        for (Genre genre : genres) {
            try {
                genre.readBinaryFile();
            }
            catch (FileNotFoundException ex) {
                System.out.println("Missing file: " + genre.getSerFileName());
            }
            catch (ClassNotFoundException ex) {
                System.out.println("Error: casting to unknown class");
            }
            catch (IOException ex) {
                System.out.println("Error: input problems");
            }
        }

        Genre selectedGenre = genres[0];

        // entering the menu
        while (true) {
            // displaying main menu
            System.out.println("-----------------------------\n" +
                               "          Main Menu\n" +
                               "-----------------------------\n" +
                               " v View the selected file: " + selectedGenre.getSerFileName() + " (" +
                                selectedGenre.getDeserializedBooksLength() + " records)\n" +
                               " s Select a file to view\n" +
                               " x Exit\n" +
                               "-----------------------------\n");
            System.out.print("Enter Your Choice: ");

            // taking user input
            String input = keyboard.nextLine();

            // exit option
            if (input.trim().equalsIgnoreCase("x")) {
                break;
            }
            // viewing option
            else if (input.trim().equalsIgnoreCase("v")) {
                System.out.println("viewing " + selectedGenre.getSerFileName() + " (" +
                        selectedGenre.getDeserializedBooksLength() + " records)");

                enterViewingSession(selectedGenre.getDeserializedBooks());
            }
            // select file option
            else if (input.trim().equalsIgnoreCase("s")) {
                // entering sub menu
                while (true) {
                    try {
                        System.out.print(
                                "------------------------------\n" +
                                "        File Sub-Menu\n" +
                                "------------------------------\n");
                        for (int j = 0; j < genres.length; j++) {
                            System.out.printf(" %1s  %-35s %-15s\n",
                                    (j+1),
                                    genres[j].getSerFileName(),
                                    "(" + genres[j].getDeserializedBooksLength() + " records)");
                        }
                        System.out.println(" " + (genres.length + 1) + "  Exit\n" +
                                "------------------------------\n");
                        System.out.print("Enter Your Choice: ");

                        // taking user input
                        int selectedOption = Integer.parseInt(keyboard.nextLine());
                        // handling invalid option
                        if (selectedOption <= 0 || selectedOption > genres.length + 1) {
                            System.out.println("Invalid option. Please try again.");
                            continue;
                        }
                        // handling exit option
                        if (selectedOption == genres.length + 1) {
                            break;
                        }
                        // setting selected genre binary file to open
                        selectedGenre = genres[selectedOption - 1];
                        break;
                    }
                    catch (NumberFormatException ex) {
                        System.out.println("Invalid input. Please enter a valid integer.");
                    }
                }
            }
            else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Main method of the application
     *
     * @param args application arguments
     */
    public static void main(String[] args) {
        System.out.println("Welcome to Victoria's bookstore app!");
        do_part1();
        do_part2();
        do_part3();
        System.out.println("End of program.");
    }
}