package books_catalogue;

/**
 * Exception class for ISBN-10 semantic errors.
 *
 * @author Victoria Pascal
 */
public class BadIsbn10Exception extends SemanticException {
    /**
     * Default constructor with predefined error message.
     */
    public BadIsbn10Exception() {
        super("Error: invalid ISBN-10");
    }
}
