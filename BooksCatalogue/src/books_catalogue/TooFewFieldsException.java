package books_catalogue;

/**
 * Exception class for too few fields syntax errors.
 *
 * @author Victoria Pascal
 */
public class TooFewFieldsException extends SyntaxException {
    /**
     * Default constructor with predefined error message.
     */
    public TooFewFieldsException() {
        super("Error: too few fields");
    }
}
