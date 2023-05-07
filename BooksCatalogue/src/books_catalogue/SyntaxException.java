package books_catalogue;

/**
 * Helper generic syntax exception class.
 *
 * @author Victoria Pascal
 */
public class SyntaxException extends Exception {
    /**
     * Parameterized constructor with error message parameter.
     *
     * @param message error message
     */
    public SyntaxException(String message) {
        super(message);
    }
}
