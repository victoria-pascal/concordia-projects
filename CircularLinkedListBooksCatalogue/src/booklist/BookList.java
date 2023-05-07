package booklist;

import java.io.*;
import java.util.ArrayList;

/**
 * The <code>BookList</code> class that allows the user to store the Books data
 * in a circular Linkedlist,
 * and manipulate its contents.
 *
 * @author Victoria Pascal
 */
public class BookList {
    /**
     * The inner <code>Node</code> class for linking Book objects in the circular Linkedlist.
     */
    private class Node {
        /**
         * Book object
         */
        private Book b;
        /**
         * Node to link this Node with another Node in the BookList
         */
        private Node next;

        /**
         * Parameterized constructor
         *
         * @param b    Book object
         * @param next Node to link this Node with another Node in the BookList
         */
        public Node(Book b, Node next) {
            this.b = b;
            this.next = next;
        }
    }

    /**
     * The head (start) of the BookList
     */
    private Node head;

    /**
     * The default constructor which initializes head to null
     */
    public BookList() {
        head = null;
    }

    /**
     * Adds the Book passed as an argument at the beginning of the BookList making it the new head
     *
     * @param b Book object for which a new Node holding it will be added at the beginning as the new head of BookList
     */
    public void addToStart(Book b) {
        Node newHead;
        if (head == null) {
            newHead = new Node(b, null);
            newHead.next = newHead;
        } else {
            newHead = new Node(b, head);
        }
        head = newHead;
    }

    /**
     * Finds all the book records based on a given year, and stores them in a file called yr.txt
     *
     * @param yr the year book records have to be found from
     */
    public void storeRecordsByYear(int yr) {
        if (head == null) return;
        ArrayList<Book> yearBooks = new ArrayList<>();
        Node current = head;
        do {
            if (current.b.getYear() == yr) {
                yearBooks.add(current.b);
            }
            current = current.next;
        } while (current != head);

        if (yearBooks.size() < 1) return;

        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(yr + ".txt"));
            for (Book book : yearBooks) {
                pw.println(book);
            }
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Searches for the first occurrence of a Node holding a Book object with the ISBN that equals to the passed isbn value.
     * If the Node is found, inserts a new Node in the list (holding Book b) before the found node. Otherwise, does nothing.
     *
     * @param isbn ISBN of the Book before whose Node a new Node will be inserted
     * @param b    Book for which a new Node holding it will be created and inserted
     * @return boolean value true if the Node was successfully created and inserted or false otherwise
     */
    public boolean insertBefore(long isbn, Book b) {
        if (head == null) return false;
        Node current = head;
        do {
            if (current.next.b.getIsbn() == isbn) {
                Node temp = current.next;
                current.next = new Node(b, temp);
                return true;
            }
            current = current.next;
        } while (current != head);
        return false;
    }

    /**
     * Searches for the first occurrence of the first two consecutive nodes holding Book objects with ISBN values equal to isbn1 and isbn2, respectively.
     * If found, the method inserts a new Node in the list (holding Book b) in between these two nodes. Otherwise, does nothing.
     *
     * @param isbn1 ISBN of the Book after whose Node a new Node will be inserted
     * @param isbn2 ISBN of the Book before whose Node a new Node will be inserted
     * @param b     Book for which a new Node holding it will be created and inserted
     * @return boolean value true if the Node was successfully created and inserted or false otherwise
     */
    public boolean insertBetween(long isbn1, long isbn2, Book b) {
        if (head == null) return false;
        Node current = head;
        do {
            if (current.b.getIsbn() == isbn1 && current.next.b.getIsbn() == isbn2) {
                Node temp = current.next;
                current.next = new Node(b, temp);
                return true;
            }
            current = current.next;
        } while (current != head);
        return false;
    }

    /**
     * Displays the records of the calling BookList object.
     */
    public void displayContent() {
        if (head == null) {
            System.out.println("The list is empty.");
            return;
        }
        Node current = head;
        do {
            System.out.println(current.b.toString() + " ==> ");
            current = current.next;
        } while (current != head);
        System.out.println("===> head");
    }

    /**
     * Finds all consecutive repeated nodes, each having the same Book record, and deletes them, if any.
     *
     * @return boolean value true if there are no duplicates in the BookList anymore,
     * otherwise or if the BookList was empty returns false.
     */
    public boolean delConsecutiveRepeatedRecords() {
        if (head == null) return false;
        Node current = head;
        do {
            while (current.b.equals(current.next.b)) {
                if (current.next == head) {
                    head = head.next;
                }
                current.next = current.next.next;
            }
            current = current.next;
        } while (current != head);
        return true;
    }

    /**
     * Adds a Node holding a Book object passed as an argument to the end of the BookList before the head
     *
     * @param b Book object for which a new Node holding it will be created and added
     */
    public void addToEnd(Book b) {
        if (head == null) {
            addToStart(b);
            return;
        }
        Node current = head;
        do {
            if (current.next == head) {
                current.next = new Node(b, head);
                return;
            }
            current = current.next;
        } while (current != head);
    }

    /**
     * Searches all records in the calling list for Book objects that have the author given to the method.
     * If found the method will create a new singular circular list that includes only the records of that author.
     *
     * @param aut name of the author whose books the method has to find
     * @return BookList that includes only the records of the specified author
     */
    public BookList extractAuthList(String aut) {
        if (head == null) return new BookList();
        Node current = head;
        BookList authList = new BookList();
        do {
            if (current.b.getAuthor().equals(aut)) {
                authList.addToEnd(current.b);
            }
            current = current.next;
        } while (current != head);
        return authList;
    }

    /**
     * Rearranges the records in the list by swapping the first pair of nodes
     * storing a Book record with isbn1 and a Book record with isbn2
     *
     * @param isbn1 ISBN of the first Book to be swapped
     * @param isbn2 ISBN of the second Book to be swapped
     * @return boolean value true if the records were successfully swapped or false otherwise.
     * If any of the passed ISBNs do not exist in the list, then nothing happens and the method returns false.
     */
    public boolean swap(long isbn1, long isbn2) {
        if (head == null) return false;
        Node swap1 = null, swap2 = null, prev1 = null, prev2 = null, previous = null;
        Node current = head;
        do {
            if (swap1 == null && current.b.getIsbn() == isbn1) {
                swap1 = current;
                prev1 = previous;
            }
            if (swap2 == null && current.b.getIsbn() == isbn2) {
                swap2 = current;
                prev2 = previous;
            }

            if (swap1 == head && current.next == head) {
                prev1 = current;
            }
            if (swap2 == head && current.next == head) {
                prev2 = current;
            }
            previous = current;
            current = current.next;
        } while ((swap1 == null || swap2 == null || prev1 == null || prev2 == null) && current != head);

        if (swap1 == null || swap2 == null || prev1 == null || prev2 == null) return false;

        prev1.next = swap2;
        prev2.next = swap1;
        Node temp = swap1.next;
        swap1.next = swap2.next;
        swap2.next = temp;

        if (swap1 == head) head = swap2;
        else if (swap2 == head) head = swap1;

        return true;
    }

    /**
     * Commits the contents of the list by storing its contents in a file called Update_Books.txt
     */
    public void commit() {
        try {
            Node current = head;
            PrintWriter pw = new PrintWriter(new FileOutputStream("Update_Books.txt"));
            do {
                pw.println(current.b);
                current = current.next;
            } while (current != head);
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}

