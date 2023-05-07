package books_catalogue;

/**
 * Helper generic semantic exception class.
 *
 * @author Victoria Pascal
 */
public class SemanticException extends Exception {
    /**
     * Parameterized constructor with error message parameter.
     *
     * @param message error message
     */
    public SemanticException(String message) {
        super(message);
    }
}
