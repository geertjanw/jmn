/*
 * Composition.java
 *
 * Created on 4 augustus 2006, 1:38
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.musician.api;

/**
 *
 * @author Pierre Matthijs
 */
public class Composition {
    
    private String title        = "Untitled";
    private double tempo        = 60;
    private int    volume       = 100;
    private String keySignature = "";
    private int    rythmCount   = 4;
    private int    rythmUnit    = 4;
    
    /** Creates a new instance of Composition */
    public Composition() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getTempo() {
        return tempo;
    }

    public void setTempo(double tempo) {
        this.tempo = tempo;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getKeySignature() {
        return keySignature;
    }

    public void setKeySignature(String keySignature) {
        this.keySignature = keySignature;
    }

    public int getRythmCount() {
        return rythmCount;
    }

    public void setRythmCount(int rythmCount) {
        this.rythmCount = rythmCount;
    }

    public int getRythmUnit() {
        return rythmUnit;
    }

    public void setRythmUnit(int rythmUnit) {
        this.rythmUnit = rythmUnit;
    }
    
}
