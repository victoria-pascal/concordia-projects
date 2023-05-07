package books_catalogue;

/**
 * Exception class for invalid year semantic errors.
 *
 * @author Victoria Pascal
 */
public class BadYearException extends SemanticException {
    /**
     * Default constructor with predefined error message.
     */
    public BadYearException() {
        super("Error: invalid year");
    }
}