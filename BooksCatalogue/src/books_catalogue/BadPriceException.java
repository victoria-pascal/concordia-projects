package books_catalogue;

/**
 * Exception class for invalid price semantic errors.
 *
 * @author Victoria Pascal
 */
public class BadPriceException extends SemanticException {
    /**
     * Default constructor with predefined error message.
     */
    public BadPriceException() {
        super("Error: invalid price");
    }
}