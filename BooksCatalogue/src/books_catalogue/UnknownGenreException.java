package books_catalogue;

/**
 * Exception class for unknown genre syntax errors.
 *
 * @author Victoria Pascal
 */
public class UnknownGenreException extends SyntaxException {
    /**
     * Default constructor with predefined error message.
     */
    public UnknownGenreException() {
        super("Error: invalid genre");
    }
}
