package booklist;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

/**
 * This is the Driver class of the application.
 * It is an interactive program that allows one to read a file with Book records,
 * identify book records with invalid year and save them into an ArrayList, then into a separate file,
 * store the correct book records in a LinkedList,
 * navigate through the list and change it.
 *
 * @author Victoria Pascal
 */
public class Driver {
    /**
     * Constant indicating the index of the title field in the book record (and in the array with its fields)
     */
    public static final int TITLE_INDEX = 0;
    /**
     * Constant indicating the index of the author field in the book record (and in the array with its fields)
     */
    public static final int AUTHOR_INDEX = 1;
    /**
     * Constant indicating the index of the price field in the book record (and in the array with its fields)
     */
    public static final int PRICE_INDEX = 2;
    /**
     * Constant indicating the index of the ISBN field in the book record (and in the array with its fields)
     */
    public static final int ISBN_INDEX = 3;
    /**
     * Constant indicating the index of the genre field in the book record (and in the array with its fields)
     */
    public static final int GENRE_INDEX = 4;
    /**
     * Constant indicating the index of the year field in the book record (and in the array with its fields)
     */
    public static final int YEAR_INDEX = 5;
    /**
     * Constant indicating the cutoff year from which any book records
     * with this year or any higher will be considered invalid.
     */
    public static final int CUTOFF_YEAR = 2024;
    /**
     * Constant indicating the input integer corresponding to the exit option
     */
    public static final int EXIT = 8;
    /**
     * Constant indicating the input integer corresponding to the option for storing records by year
     */
    public static final int STORE_RECORDS_BY_YEAR = 1;
    /**
     * Constant indicating the input integer corresponding to the option for deleting consecutive repeated records
     */
    public static final int DEL_CONSECUTIVE_REPEATED_RECORDS = 2;
    /**
     * Constant indicating the input integer corresponding to the option for extracting the list with the records of an author
     */
    public static final int EXTRACT_AUTH_LIST = 3;
    /**
     * Constant indicating the input integer corresponding to the option for inserting a Node with a Book before another Node
     */
    public static final int INSERT_BEFORE = 4;
    /**
     * Constant indicating the input integer corresponding to the option for inserting a Node with a Book between two other Nodes
     */
    public static final int INSERT_BETWEEN = 5;
    /**
     * Constant indicating the input integer corresponding to the option for swapping two Nodes with Books
     */
    public static final int SWAP = 6;
    /**
     * Constant indicating the input integer corresponding to the option for committing the contents of the BookList
     */
    public static final int COMMIT = 7;
    /**
     * A global Scanner for reading user input
     */
    public static Scanner in = new Scanner(System.in);

    /**
     * Default constructor
     */
    public Driver() {}

    /**
     * Main method of the application
     *
     * @param args application arguments
     */
    public static void main(String[] args) {

        ArrayList<Book> arrLst = new ArrayList<Book>();
        BookList bkLst = new BookList();

        try {
            Scanner scan = new Scanner(new FileInputStream("Books.txt"));

            // Using a loop to read each record line of the Books.txt file and validate it,
            // then initializing the arrLst and bkLst with the data of the corresponding records.
            while (scan.hasNextLine()) {
                String record = scan.nextLine();
                String[] bookRecordFields = record.split(",");
                String title = bookRecordFields[TITLE_INDEX].trim()
                        .substring(1, bookRecordFields[TITLE_INDEX].length() - 1).trim();
                String author = bookRecordFields[AUTHOR_INDEX].trim();
                double price = Double.parseDouble(bookRecordFields[PRICE_INDEX].trim());
                long isbn = Long.parseLong(bookRecordFields[ISBN_INDEX].trim());
                String genre = bookRecordFields[GENRE_INDEX].trim();
                int year = Integer.parseInt(bookRecordFields[YEAR_INDEX].trim());

                // Validating the year
                if (year >= CUTOFF_YEAR) {
                    // If the year is invalid creating a Book and adding it to the arrLst
                    arrLst.add(new Book(title, author, price, isbn, genre, year));
                }
                else {
                    // If the year is valid creating a Book and adding it to the bkLst
                    bkLst.addToEnd(new Book(title, author, price, isbn, genre, year));
                }
            }
            scan.close();

            // Creating the YearErr file and writing to it records with invalid year
            // if any have been detected and added to the arrLst before
            if (arrLst.size() > 0) {
                PrintWriter pw = new PrintWriter(new FileOutputStream("YearErr.txt"));
                for (Book book : arrLst) {
                    pw.println(book);
                }
                System.out.println("YearError File Created");
                pw.close();
            }
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Here are the contents of the list\n" +
                           "=================================");
        bkLst.displayContent();

        // Using a loop to display an interactive menu for the user
        while (true) {
            System.out.println("Tell me what you want to do? Let's Chat since this is trending now! Here are the options:\n" +
                    "\t1) Give me a year # and I would extract all records of that year and store them in a file for that year;\n" +
                    "\t2) Ask me to delete all consecutive repeated records;\n" +
                    "\t3) Give me an author name and I will create a new list with the records of this author and display them;\n" +
                    "\t4) Give me an ISBN number and a Book object, and I will insert Node with the book before the record with this ISBN;\n" +
                    "\t5) Give me 2 ISBN numbers and a Book object, and I will insert a Node between them, if I find them!\n" +
                    "\t6) Give me 2 ISBN numbers and I will swap them in the list for rearrangement of records; of course if they exist!\n" +
                    "\t7) Tell me to COMMIT! Your command is my wish. I will commit your list to a file called Updated_Books;\n" +
                    "\t8) Tell me to STOP TALKING. Remember, if you do not commit, I will not!");

                System.out.print("Enter Your Selection: ");

                // Reading and validating user input, then matching it with any of the available options
                try {
                    int option = Integer.parseInt(in.nextLine());
                    System.out.println();

                    if (option == EXIT) {
                        bkLst.displayContent();
                        System.out.println("Exiting the program.");
                        break;
                    }
                    else if (option == STORE_RECORDS_BY_YEAR) {
                        System.out.print("Please enter a year less than " + CUTOFF_YEAR + " to store the records of the books from that year in a file: ");
                        try {
                            int year = Integer.parseInt(in.nextLine());

                            if (year >= CUTOFF_YEAR) {
                                System.out.println("Invalid input. Year should be below " + CUTOFF_YEAR + ".");
                            }
                            else {
                                bkLst.storeRecordsByYear(year);
                                System.out.println("The records of the books from the year " + year +
                                        " were stored in a file if any matches were found.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Year should be a valid integer less than " + CUTOFF_YEAR + ".");
                        }
                        bkLst.displayContent();
                    }
                    else if (option == DEL_CONSECUTIVE_REPEATED_RECORDS) {
                        System.out.println("Here are the contents of the list after removing consecutive duplicates\n" +
                                           "=======================================================================");
                        bkLst.delConsecutiveRepeatedRecords();
                        bkLst.displayContent();
                    }
                    else if (option == EXTRACT_AUTH_LIST) {
                        System.out.print("Please enter the name of the author to create an extracted list: ");
                        String author = in.nextLine();
                        System.out.println("Here are the contents of the author list for " + author + ".\n" +
                                           "===========================================================");
                        bkLst.extractAuthList(author).displayContent();
                    }
                    else if (option == INSERT_BEFORE) {
                        System.out.print("Enter ISBN and the details of the Book (title, author, price, ISBN, genre, year), all separated by commas," +
                                " to insert before the record with the given ISBN: ");
                        try {
                            String inpStr = in.nextLine();
                            String[] inputData = inpStr.split(",");
                            long isbn = Long.parseLong(inputData[0].trim());
                            String title = inputData[TITLE_INDEX + 1].trim();
                            String author = inputData[AUTHOR_INDEX + 1].trim();
                            double price = Double.parseDouble(inputData[PRICE_INDEX + 1].trim());
                            long newBookIsbn = Long.parseLong(inputData[ISBN_INDEX + 1].trim());
                            String genre = inputData[GENRE_INDEX + 1].trim();
                            int year = Integer.parseInt(inputData[YEAR_INDEX + 1].trim());

                            if (year >= CUTOFF_YEAR) {
                                System.out.println("Invalid year. Year should be below " + CUTOFF_YEAR + ".");
                                bkLst.displayContent();
                                continue;
                            }

                            if (title.charAt(0) == '"' && title.charAt(title.length() - 1) == '"') {
                                title = title.substring(1, title.length() - 1);
                            }

                            if (bkLst.insertBefore(isbn, new Book(title, author, price, newBookIsbn, genre, year))) {
                                System.out.println("The record with ISBN " + isbn + " was found and a new record was added before it.\n" +
                                        "Here are the contents of the list after it was inserted:");
                            }
                            else {
                                System.out.println("The record with ISBN " + isbn +
                                        " was not found and, thus, insertion failed. The list remained the same.");
                            }
                        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                            System.out.println("Invalid input. Please try again.");
                        }
                        bkLst.displayContent();
                    }
                    else if (option == INSERT_BETWEEN) {
                        System.out.print("Enter two ISBN and the details of the Book (title, author, price, ISBN, genre, year separated by a comma) " +
                                "to insert between the records with the given ISBNs: ");
                        try {
                            String[] inputData = in.nextLine().split(",");
                            long isbn1 = Long.parseLong(inputData[0].trim());
                            long isbn2 = Long.parseLong(inputData[1].trim());
                            String title = inputData[TITLE_INDEX + 2].trim();
                            String author = inputData[AUTHOR_INDEX + 2].trim();
                            double price = Double.parseDouble(inputData[PRICE_INDEX + 2].trim());
                            long newBookIsbn = Long.parseLong(inputData[ISBN_INDEX + 2].trim());
                            String genre = inputData[GENRE_INDEX + 2].trim();
                            int year = Integer.parseInt(inputData[YEAR_INDEX + 2].trim());

                            if (year >= CUTOFF_YEAR) {
                                System.out.println("Invalid year. Year should be below " + CUTOFF_YEAR + ".");
                                bkLst.displayContent();
                                continue;
                            }

                            if (title.charAt(0) == '"' && title.charAt(title.length() - 1) == '"') {
                                title = title.substring(1, title.length() - 1);
                            }

                            if (bkLst.insertBetween(isbn1, isbn2, new Book(title, author, price, newBookIsbn, genre, year))) {
                                System.out.println("The records with ISBNs " + isbn1 + " and " + isbn2 + " were found, " +
                                        " and a new record was added between them.\n" +
                                        "Here are the contents of the list after it was inserted:");
                            }
                            else {
                                System.out.println("One or both records with ISBNs " + isbn1 + " and " + isbn2 +
                                        " was/were not found and, thus, insertion failed. The list remained the same.");
                            }
                        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                            System.out.println("Invalid input. Please try again.");
                        }
                        bkLst.displayContent();
                    }
                    else if (option == SWAP) {
                        System.out.print("Enter two space separated ISBNs to swap their locations: ");
                        try {
                            long isbn1 = Long.parseLong(in.next());
                            long isbn2 = Long.parseLong(in.next());
                            in.nextLine();

                            if (bkLst.swap(isbn1, isbn2)) {
                                System.out.println("Records with ISBNs " + isbn1 + " and " + isbn2 + " were found and swapped.\n" +
                                        "Here are the contents of the list after rearranging the records:");
                            }
                            else {
                                System.out.println("One or both records with ISBNs " + isbn1 + " and " + isbn2 +
                                        " was/were not found and, thus, couldn't be swapped. The list remained the same.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please try again.");
                        }
                        bkLst.displayContent();
                    }
                    else if (option == COMMIT) {
                        bkLst.commit();
                        System.out.println("The contents of the Booklist were stored in the file Update_Books.txt.");
                        bkLst.displayContent();
                    }
                    else {
                        System.out.print("The entered option doesn't match any of the available options. Please enter a valid option: ");
                        bkLst.displayContent();
                    }
                }
                catch (NumberFormatException ex) {
                    System.out.println("Invalid input. Please enter a valid option as an integer.");
                    bkLst.displayContent();
                }
        }
    }
}