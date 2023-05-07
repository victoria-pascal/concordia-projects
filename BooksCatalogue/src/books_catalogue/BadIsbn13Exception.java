package books_catalogue;

/**
 * Exception class for ISBN-13 semantic errors.
 *
 * @author Victoria Pascal
 */
public class BadIsbn13Exception extends SemanticException {
    /**
     * Default constructor with predefined error message.
     */
    public BadIsbn13Exception() {
        super("Error: invalid ISBN-13");
    }
}