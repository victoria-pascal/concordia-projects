package books_catalogue;

/**
 * Exception class for too many fields syntax errors.
 *
 * @author Victoria Pascal
 */
public class TooManyFieldsException extends SyntaxException {
    /**
     * Default constructor with predefined error message.
     */
    public TooManyFieldsException() {
        super("Error: too many fields");
    }
}
