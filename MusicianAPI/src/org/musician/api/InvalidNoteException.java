/*
 * InvalidNoteException.java
 *
 * Created on 28 juli 2006, 18:37
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.musician.api;

/**
 *
 * @author Pierre Matthijs
 */
public class InvalidNoteException extends java.lang.Exception {
    
    /**
     * Creates a new instance of <code>InvalidNoteException</code> without detail message.
     */
    public InvalidNoteException() {
    }
    
    
    /**
     * Constructs an instance of <code>InvalidNoteException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public InvalidNoteException(String msg) {
        super(msg);
    }
}
