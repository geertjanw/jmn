/*
 * Instrument.java
 *
 * Created on 25 juli 2006, 17:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.musician.api;

import java.io.Serializable;

/**
 *
 * @author Pierre Matthijs
 */
public class Instrument implements Serializable {
    
    private int    midiNumber;
    private String category;
    private String description;
    private String jfugueDescription;
    
    /** Creates a new instance of Instrument */
    public Instrument() {
    }
    
    public Instrument(String category, String description, String jfugueDescription, int midiNumber) {
        this.category = category;
        this.description = description;
        this.jfugueDescription = jfugueDescription;
        this.midiNumber = midiNumber;
    }

    public int getMidiNumber() {
        return midiNumber;
    }

    public void setMidiNumber(int midiNumber) {
        this.midiNumber = midiNumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJfugueDescription() {
        return jfugueDescription;
    }

    public void setJfugueDescription(String jfugueDescription) {
        this.jfugueDescription = jfugueDescription;
    }
    
}
