package books_catalogue;

/**
 * Exception class for semantic errors of ISBNs with an invalid length.
 *
 * @author Victoria Pascal
 */
public class BadIsbnLengthException extends SemanticException {
    /**
     * Default constructor with predefined error message.
     */
    public BadIsbnLengthException() {
        super("Invalid ISBN length");
    }
}
