
/**
 *
 * @author agata
 */

public class InvalidApproverException extends Exception {
    String message;
    
    // user defined exception
    InvalidApproverException(String message) {
        this.message = message;
    }
}
