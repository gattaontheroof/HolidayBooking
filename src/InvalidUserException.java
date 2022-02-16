
/**
 *
 * @author agata
 */
public class InvalidUserException extends Exception {
    String message;
    
    // user defined exception
    InvalidUserException(String message) {
        this.message = message;
    }
}
