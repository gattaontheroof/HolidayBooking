/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author agata
 */
public class InvalidRequestException extends Exception {
    String message;
    
    // user defined exception
    InvalidRequestException(String message) {
        this.message = message;
    }
}
