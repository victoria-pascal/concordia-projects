package books_catalogue;

/**
 * Exception class for missing field syntax errors.
 *
 * @author Victoria Pascal
 */
public class MissingFieldException extends SyntaxException {
    /**
     * Parameterized constructor with custom message with missing field name.
     *
     * @param missingFieldName name of missing field
     */
    public MissingFieldException(String missingFieldName) {
        super("Error: missing " + missingFieldName);
    }
}
