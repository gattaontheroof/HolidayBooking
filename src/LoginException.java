/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author agata
 */
public class LoginException extends Exception {
    String message;
    
    // user defined exception
    LoginException(String message) {
        this.message = message;
    }
}